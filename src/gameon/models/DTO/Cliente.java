package gameon.models.DTO;

import java.util.HashMap;
import java.util.Map;

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
	
	public Map<String, Object> toAsaas() {
		Map<String, Object> dadosAsaas = new HashMap<>();
		
		dadosAsaas.put("name", getNome());
		dadosAsaas.put("cpfCnpj", getCpf());
		
		return dadosAsaas;
	}
	
	public String getAsaasUrl() {
		return "customers/" + getAsaasCliente();
	}
	
}
