package gameon.models.DTO;

import java.time.LocalDateTime;

public class OrdemDTO {
	private int id;
	private String status;
	private String metodoPagamento;
	private double valorTotal;
	private int enderecoId;
	private String asaasOrdem;
	private LocalDateTime criadoEm;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMetodoPagamento() {
		return metodoPagamento;
	}
	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public int getEnderecoId() {
		return enderecoId;
	}
	public void setEnderecoId(int enderecoId) {
		this.enderecoId = enderecoId;
	}
	public String getAsaasOrdem() {
		return asaasOrdem;
	}
	public void setAsaasOrdem(String assasOrdem) {
		this.asaasOrdem = assasOrdem;
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
		builder.append("Ordem [id=");
		builder.append(id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", metodoPagamento=");
		builder.append(metodoPagamento);
		builder.append(", valorTotal=");
		builder.append(valorTotal);
		builder.append(", enderecoId=");
		builder.append(enderecoId);
		builder.append(", assasOrdem=");
		builder.append(asaasOrdem);
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}
	
	
}
