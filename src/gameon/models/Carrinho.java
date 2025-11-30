package gameon.models;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
	private Cliente cliente;
	private List<CarrinhoProduto> produtos = new ArrayList<>();

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<CarrinhoProduto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<CarrinhoProduto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public String toString() {
		return "Carrinho [cliente=" + cliente + ", produtos=" + produtos + "]";
	}
}