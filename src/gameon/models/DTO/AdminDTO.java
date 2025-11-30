package gameon.models.DTO;

public class AdminDTO {
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Admin [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}
