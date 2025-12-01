package gameon.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class CarrinhoProduto {
	private Produto produto;
	private int quantidade;
	private LocalDateTime criadoEm;
	
	public CarrinhoProduto() {}
	
	public CarrinhoProduto(Produto produto, int quantidade) {
		setProduto(produto);
		setQuantidade(quantidade);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarrinhoProduto that = (CarrinhoProduto) o;

        return Objects.equals(this.produto.getId(), that.produto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto.getId());
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarrinhoProduto [");
		builder.append(produto);
		builder.append(", quantidade=");
		builder.append(quantidade);
		builder.append("]");
		return builder.toString();
	}
}
