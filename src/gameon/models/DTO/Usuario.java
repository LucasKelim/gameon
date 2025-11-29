package gameon.models.DTO;

import java.time.LocalDateTime;

import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class Usuario {
	private int id;
	private String nome;
	private Email email;
	private Senha senha;
	private LocalDateTime criadoEm;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email.getEmail();
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public String getSenha() {
		return senha.getSenha();
	}
	public void setSenha(Senha senha) {
		this.senha = senha;
	}
	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}
	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", email=");
		builder.append(email.getEmail());
		builder.append(", senha=");
		builder.append(senha.getSenha());
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
