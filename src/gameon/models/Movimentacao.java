package gameon.models;

import java.time.LocalDateTime;

import gameon.models.enums.TipoMovimentacao;

public class Movimentacao {
	private int id;
	private TipoMovimentacao tipo;
	private int quantidade;
	private Produto produto;
	private LocalDateTime criadoEm;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public TipoMovimentacao getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoMovimentacao tipo) {
		this.tipo = tipo;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
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
		builder.append("Movimentacao [id=");
		builder.append(id);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", quantidade=");
		builder.append(quantidade);
		builder.append(", ");
		builder.append(produto);
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}
}
