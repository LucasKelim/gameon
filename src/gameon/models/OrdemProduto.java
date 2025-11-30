package gameon.models;

import java.time.LocalDateTime;

public class OrdemProduto {
	private Ordem ordem;
	private Produto produto;
	private int quantidade;
	private LocalDateTime criadoEm;
	
	public Ordem getOrdem() {
		return ordem;
	}
	
	public void setOrdem(Ordem ordem) {
		this.ordem = ordem;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
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
		builder.append("OrdemProduto [");
		builder.append(ordem);
		builder.append(", ");
		builder.append(produto);
		builder.append(", quantidade=");
		builder.append(quantidade);
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}
}
