package gameon.models;

public class Pix {
	private String encodedImage;
	private String payload;
	private String expirationDate;
	private String description;
	
	public Pix() {}
	
	public Pix(String encodedImage, String payload, String expirationDate, String description) {
		setEncodedImage(encodedImage);
		setPayload(payload);
		setExpirationDate(expirationDate);
		setDescription(description);
	}
	
	public String getEncodedImage() {
		return encodedImage;
	}
	
	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pix [encodedImage=");
		builder.append(encodedImage);
		builder.append(", payload=");
		builder.append(payload);
		builder.append(", expirationDate=");
		builder.append(expirationDate);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
}
