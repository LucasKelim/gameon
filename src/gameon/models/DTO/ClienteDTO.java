package gameon.models.DTO;

public class ClienteDTO {
	private int id;
	private String cpf;
	private String telefone;
	private String asaasCliente;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
		builder.append("Cliente [id=");
		builder.append(id);
		builder.append(", cpf=");
		builder.append(cpf);
		builder.append(", telefone=");
		builder.append(telefone);
		builder.append(", asaasCliente=");
		builder.append(asaasCliente);
		builder.append("]");
		return builder.toString();
	}
}
