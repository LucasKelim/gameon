package gameon.models;

import java.time.LocalDateTime;

import gameon.models.valuesobjects.Cpf;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class Cliente extends Usuario {
	private Cpf cpf;
	private String telefone;
	private String asaasCliente;
	
	public Cliente() {}
	
	public Cliente(int id, String nome, Email email, Senha senha, Cpf cpf, String telefone, String asaasCliente, LocalDateTime criadoEm) {
		super(id, nome, email, senha, criadoEm);
		setCpf(cpf);
		setTelefone(telefone);
		setAsaasCliente(asaasCliente);
	}
	
	public Cpf getCpf() {
		return cpf;
	}
	
	public void setCpf(Cpf cpf) {
		this.cpf = cpf;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getAsaasCliente() {
		return asaasCliente;
	}
	
	public void setAsaasCliente(String asaasCliente) {
		this.asaasCliente = asaasCliente;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente [cpf=");
		builder.append(cpf);
		builder.append(", telefone=");
		builder.append(telefone);
		builder.append(", asaasCliente=");
		builder.append(asaasCliente);
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
