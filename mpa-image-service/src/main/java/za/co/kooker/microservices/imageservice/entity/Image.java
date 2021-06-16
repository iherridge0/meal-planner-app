package za.co.kooker.microservices.imageservice.entity;

public class Image {

	private String url;

	protected Image() {

	}

	public Image(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
