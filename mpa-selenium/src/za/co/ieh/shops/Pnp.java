package za.co.ieh.shops;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import za.co.ieh.utils.BrowserConfig;

public class Pnp {

	public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException, IOException {

		/**
		 * argument1: mpa:url.category1
		 */
		String args1 = args[0];
		System.out.println("argument1: " + args1);
		
		Date date = new Date();
		Date startTime = date;
		System.out.println("START: " + date.toString());
		System.out.println("---------------------");
		
		BrowserConfig browser = new BrowserConfig(BrowserType.CHROME);
		WebDriver driver = browser.getWebDriver();
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		List<String> urls = getUrls(args1);
		//urls.stream().forEach(url->System.out.println(url));
		int count = urls.size();
		System.out.println("Number of urls found: " + count);
		System.out.println("---------------------");
		for(String url:urls) {
			System.out.println(url);
			driver.get(url);
			
			while(true) {
				try {
					
					
					
					/**
					 * WHEN: "Oh dear, we're fresh out of results" IS DISPLAYED
					 * THEN: No products will be displayed, hence we would then break the WHILE LOOP
					 * 
					 */
					if(driver.findElements(By.cssSelector("div[class='header'] h3")).size()>0)
						break;
					
					/**
					 * WHEN: "Mop up on aisle three! We're attending to a spill but should be back online shortly. Please try again in a few minutes" IS DISPLAYED
					 * THEN: site is probably down 
					 * RETRY in 2 minutes, if still down, break        
					 * 
					 */
					//
					if(driver.findElements(By.cssSelector("div[class='deliveryBannerContent']")).size()>0) {
						System.out.println("ERROR: " + "Mop up on aisle three! We're attending to a spill but should be back online shortly. Please try again in a few minutes");
						System.out.println("RETRYING IN 2 minutes");
						Thread.sleep(120000L); //sleeps for 2 minutes and tries again
						driver.get(url);
						if(driver.findElements(By.cssSelector("div[class='deliveryBannerContent']")).size()>0) {
							System.out.println("ERROR ON RETRY: " + "LOG AN ISSUE!");
							break;
						}
							
					}
				
					/**
					 * WHEN pagination controls are not displayed on website, wait 30 seconds and exception(break;) from catch block
					 */
					Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
							.withTimeout(Duration.ofSeconds(30))
							.pollingEvery(Duration.ofSeconds(2))
							.ignoring(NoSuchElementException.class);
				
				
					/*WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
				
						public WebElement apply(WebDriver driver) {
					
							//if pagination exist and 72 items to be displayed are not selected, select 72
							if(driver.findElements(By.cssSelector("div[class*='pagination-item'] div:nth-child(2)")).size()>0) {
								if(!driver.findElement(By.cssSelector("div[class*='pagination-item'] div:nth-child(2)")).getText().contains("72")) {
									driver.findElement(By.cssSelector("div[class*='pagination-item'] div:nth-child(2)")).click();
									driver.findElement(By.cssSelector("li[class='last']")).click();
								}
								System.out.println(driver.findElement(By.cssSelector("div[class*='pagination-item']")).getText());
								return driver.findElement(By.cssSelector("div[class*='pagination-item']"));
							} else {
								return null;
							}
						}
					});*/

				
					/*WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					     public WebElement apply(WebDriver driver) {
					       
					    	 if(driver.findElements(By.cssSelector("[class$='item-name']")).size()>0) {
					    	 
					    		 return driver.findElement(By.cssSelector("[class$='item-name']"));
					    	 } else {
					    		 return null;
					    	 }
					     }
					   });*/
					if(!(driver.findElements(By.cssSelector("[class$='item-name']")).size()>0)) {
						Thread.sleep(2000L);
					}
					
					List<WebElement> products = driver.findElements(By.cssSelector("[class='productCarouselItemContainer']"));
					
					
					//Iterate through list of product WebElements and write product name and current price to file
					try {
						products.stream().forEach(product->{
							
							;
						
							String priceString = "";
							try {
								priceString = product.findElement(By.cssSelector("[class*='currentPrice']")).getText();
							} catch (Exception e) {
								// TODO: handle exception
								priceString = product.findElement(By.cssSelector("[class*='normalPrice']")).getText();
							}
							
							priceString = priceString.replace("R", "");
							priceString = priceString.replace(",", "");
							priceString = priceString.trim();
							
							String itemName = product.findElement(By.cssSelector("[class*='item-name']")).getText();
							
							
							double currentPrice = Double.parseDouble(priceString.isEmpty() ? "0" : priceString) / 100;
							System.out.println(itemName + "," + currentPrice);
							
							//Try writing the product name and price to the mpa.product table
							try {
								writeProductToDB(url, itemName, currentPrice);
							} catch (ClassNotFoundException | SQLException e) {
								// TODO Auto-generated catch block
								//System.out.println("----BEGIN-ERROR------");
								//e.printStackTrace();
								System.out.println("Error(" + e.getMessage() + ") on + " + url);
								//System.out.println("------END-ERROR------");
								
								//Takes only a screen shot of a WebElement
								/*File srcFile = ((TakesScreenshot)product).getScreenshotAs(OutputType.FILE);
								SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
								System.out.println(srcFile.getName());
								try {
									FileUtils.copyFileToDirectory(srcFile, new File("C://work//run//" + args1 + "//" + format1.format(date) + "//"));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}*/
							}
						});
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("Error(" + e.getMessage() + ") on + " + url);
						//Takes only a screen shot of a WebElement
						File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
						System.out.println(srcFile.getName());
						FileUtils.copyFileToDirectory(srcFile, new File("C://work//run//" + args1 + "//" + format1.format(date) + "//"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
					//e.printStackTrace();
					//Takes only a screen shot of a WebElement
					File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					System.out.println(srcFile.getName());
					FileUtils.copyFileToDirectory(srcFile, new File("C://work//run//" + args1 + "//" + format1.format(date) + "//"));
					break;
				}
				
				try {
					WebElement pagination = driver.findElement(By.cssSelector("li[class*='pagination-next']"));
					if(pagination.getAttribute("class").contains("disabled"))
						break;
					
					driver.findElement(By.cssSelector("li[class*='pagination-next']")).click();
				} catch (Exception e) {
					// TODO: handle exception
					break;
				}							
			}		
			driver.manage().deleteAllCookies();
		}	
		System.out.println("---------------------");
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
	    Date firstDate = startTime;
	    Date secondDate = new Date();
	 
	    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
	    long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	 
		System.out.println("TOTAL TIME on category1(" + args1 + ": " + diff + "sec");		
	}
	
	private static void writeProductToDB(String url, String itemName, double currentPrice) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
		String dbUrl = "jdbc:mysql://localhost:3306/mpa";
		
		//Database Username
		String username = "mealuser";
		
		//Database Password
		String password = "mealpassword";
		
		//Load mysql jdbc driver		
   	    Class.forName("com.mysql.jdbc.Driver");
   	    
   	    //Create Connection to DB		
    	Connection con = DriverManager.getConnection(dbUrl,username,password);
    	
    	// create a sql date object so we can use it in our INSERT statement
        Calendar calendar = Calendar.getInstance();
        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
		
		//Query to Execute		
		//String query = "insert into mpa.product values(now(), '" + url + "', '" + itemName + "', " + currentPrice + ");";
		String query = " insert into product (date, url, name, price) values (?, ?, ?, ?)";
    	
    	// create the mysql insert preparedstatement
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setDate (1, startDate);
        preparedStmt.setString (2, url);
        preparedStmt.setString(3, itemName);
        preparedStmt.setDouble(4, currentPrice);

        // execute the preparedstatement
        preparedStmt.execute();
        
		// closing DB Connection		
		con.close();	
		
	}

	private static List<String> getUrls(String args1) throws SQLException, ClassNotFoundException {
		
		List<String> urls = new ArrayList<String>();
		
		//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
		String dbUrl = "jdbc:mysql://localhost:3306/mpa";
		
		//Database Username
		String username = "root";
		
		//Database Password
		String password = "";
		
		//Query to Execute		
		//String query = "select *  from mpa.url;";
		/*String query = "select * from mpa.url WHERE category1  = 'Food-Cupboard';";*/
		Date date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String query = "SELECT url " + 
					   "FROM mpa.url " + 
				       "WHERE url NOT IN ( SELECT url FROM mpa.product WHERE date = '" + format1.format(date) + 
				       "') AND execute = '1' ";
		if(args1.equals("all"))
			query = query + ";";
		else
			query = query + "AND category1 = '" + args1 + "';";
		
		System.out.println(query);
		//Load mysql jdbc driver		
   	    Class.forName("com.mysql.jdbc.Driver");
   	    
   	    //Create Connection to DB		
    	Connection con = DriverManager.getConnection(dbUrl,username,password);
    	
    	//Create Statement Object		
 	   	Statement stmt = con.createStatement();
 	   	
 	   	//Execute the SQL Query. Store results in ResultSet		
 		ResultSet rs= stmt.executeQuery(query);
 		
 		// While Loop to iterate through all data and print results		
		while (rs.next()){
	        		String url = rs.getString(1);								        
                    System. out.println(url);		
                    urls.add(url);
            }		
		
		// closing DB Connection		
		con.close();	

		return urls;
	}
	

}
