package gameon.models;

import java.time.LocalDateTime;

import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class Usuario {
	private int id;
	private String nome;
	private Email email;
	private Senha senha;
	private LocalDateTime criadoEm;
	
	public Usuario() {}
	
	public Usuario(int id, String nome, Email email, Senha senha, LocalDateTime criadoEm) {
		setId(id);
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setCriadoEm(criadoEm);
	}
	
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
	
	public Email getEmail() {
		return email;
	}
	
	public void setEmail(Email email) {
		this.email = email;
	}
	
	public Senha getSenha() {
		return senha;
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
		builder.append(email);
		builder.append(", senha=");
		builder.append(senha);
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}
}
