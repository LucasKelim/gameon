package gameon.models;

public class CarrinhoProduto {
	private Produto produto;
	private Carrinho carrinho;
	private int quantidade;
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Carrinho getCarrinho() {
		return carrinho;
	}
	
	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarrinhoProduto [");
		builder.append(produto);
		builder.append(", ");
		builder.append(carrinho);
		builder.append(", quantidade=");
		builder.append(quantidade);
		builder.append("]");
		return builder.toString();
	}
}
