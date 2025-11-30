package gameon.models.DTO;

import java.time.LocalDateTime;

public class OrdemProdutoDTO {
	private int id;
	private int quantidade;
	private int ordemId;
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
	public int getOrdem() {
		return ordemId;
	}
	public void setOrdem(int ordemId) {
		this.ordemId = ordemId;
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
		builder.append("OrdemProduto [id=");
		builder.append(id);
		builder.append(", quantidade=");
		builder.append(quantidade);
		builder.append(", ordemId=");
		builder.append(ordemId);
		builder.append(", produtoId=");
		builder.append(produtoId);
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}
	
	
}
