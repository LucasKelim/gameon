package gameon.models.DTO;

import java.time.LocalDateTime;

public class CarrinhoProdutoDTO {
	private int id;
	private int quantidade;
	private int produtoId;
	private LocalDateTime criadoEm;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getProduto() {
		return produtoId;
	}
	public void setProduto(int produtoId) {
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
		builder.append("CarrinhoProduto [id=");
		builder.append(id);
		builder.append(", quantidade=");
		builder.append(quantidade);
		builder.append(", produtoId=");
		builder.append(produtoId);
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}
	
	
}
