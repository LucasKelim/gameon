package gameon.models.DTO;

public class Cliente extends Usuario {
	private String cpf;
	private String telefone;
	private String asaasCliente;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
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
		builder.append(", ");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
