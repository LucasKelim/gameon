package gameon.models.DTO;

import java.time.LocalDateTime;

public class CarrinhoProdutoDTO {
	private int quantidade;
	private int produtoId;
	private int clienteId;
	private LocalDateTime criadoEm;
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public int getClienteId() {
		return clienteId;
	}
	
	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}
	
	public int getProdutoId() {
		return produtoId;
	}
	
	public void setProdutoId(int produtoId) {
		this.produtoId = produtoId;
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
		builder.append("CarrinhoProdutoDTO [quantidade=");
		builder.append(quantidade);
		builder.append(", produtoId=");
		builder.append(produtoId);
		builder.append(", clienteId=");
		builder.append(clienteId);
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}	
}
