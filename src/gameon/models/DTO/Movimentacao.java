package gameon.models.DTO;

import java.time.LocalDateTime;

public class Movimentacao {
	private int id;
	private String movimentacao;
	private int quantidade;
	private int produtoId;
	private Produto produto;
	private LocalDateTime CriadoEm;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMovimentacao() {
		return movimentacao;
	}
	
	public void setMovimentacao(String movimentacao) {
		this.movimentacao = movimentacao;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public int getProdutoId() {
		return produtoId;
	}
	
	public void setProdutoId(int produtoId) {
		this.produtoId = produtoId;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public LocalDateTime getCriadoEm() {
		return CriadoEm;
	}
	
	public void setCriadoEm(LocalDateTime criadoEm) {
		CriadoEm = criadoEm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovimentacaoDTO [id=");
		builder.append(id);
		builder.append(", movimentacao=");
		builder.append(movimentacao);
		builder.append(", quantidade=");
		builder.append(quantidade);
		builder.append(", produto=");
		builder.append(produto);
		builder.append(", CriadoEm=");
		builder.append(CriadoEm);
		builder.append("]");
		return builder.toString();
	}
	
	
}
