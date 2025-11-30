package gameon.models;

import gameon.models.valuesobjects.Cpf;

public class Cliente extends Usuario {
	private Cpf cpf;
	private String telefone;
	private String asaasCliente;
	
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
