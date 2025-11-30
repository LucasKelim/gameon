package gameon.models;

public class Admin extends Usuario {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Admin [");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}	
}
