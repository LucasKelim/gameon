package gameon.models;

import java.time.LocalDateTime;

import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class Admin extends Usuario {
	
	public Admin() {}
	
	public Admin(int id, String nome, Email email, Senha senha, LocalDateTime criadoEm) {
		super(id, nome, email, senha, criadoEm);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Admin [");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}	
}
