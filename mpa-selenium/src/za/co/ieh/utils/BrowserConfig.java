package za.co.ieh.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

public class BrowserConfig {

	private static final String DRIVER_HOME = "D:\\Documents\\Udemy\\Selenium Course Content\\Drivers";
	
	private static final String FIREFOX_DRIVER = "webdriver.gecko.driver";
	private static final String FIREFOX_DRIVER_LOCATION = DRIVER_HOME + "\\geckodriver-v0.27.0-win64\\geckodriver.exe";
	
	private static final String CHROME_DRIVER = "webdriver.chrome.driver";
	private static final String CHROME_DRIVER_LOCATION = DRIVER_HOME + "\\chromedriver_win32\\chromedriver.exe";
	
	private static final String EDGE_DRIVER = "webdriver.edge.driver";
	private static final String EDGE_DRIVER_LOCATION = DRIVER_HOME + "\\edgedriver_win64\\msedgedriver.exe";
	
	WebDriver driver;
	
	public BrowserConfig(String browserType) {
		switch (browserType) {
		case BrowserType.CHROME:
			useChrome();
			break;
		case BrowserType.FIREFOX:
			useFirefox();
			break;
		case BrowserType.EDGE:
			useEdge();
			break;

		default:
			useChrome();
			break;
		}	
		
	}
	
	public WebDriver getWebDriver() {
		return this.driver;
	}
	
	public void setWebDriver(WebDriver webDriver) {
		this.driver = webDriver;
	}
	
	private void useChrome() {
		System.setProperty(CHROME_DRIVER, CHROME_DRIVER_LOCATION);
		setWebDriver(new ChromeDriver());
	}
	
	private void useFirefox() {
		System.setProperty(FIREFOX_DRIVER, FIREFOX_DRIVER_LOCATION);
		setWebDriver(new FirefoxDriver());
	}
	
	private void useEdge() {
		System.setProperty(EDGE_DRIVER, EDGE_DRIVER_LOCATION);
		setWebDriver(new EdgeDriver());
	}
}
