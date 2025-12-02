package gameon.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carrinho {
	private Cliente cliente;
	private List<CarrinhoProduto> produtos = new ArrayList<>();
	
	public Carrinho() {}
	
	public Carrinho(Cliente cliente, List<CarrinhoProduto> produtos) {
		setCliente(cliente);
		setProdutos(produtos);
	}

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
	
	public void addProduto(CarrinhoProduto carrinhoProduto) {
		this.produtos.add(carrinhoProduto);
	}
	
	public void removePrduto(CarrinhoProduto carrinhoProduto) {
		this.produtos.remove(carrinhoProduto);
	}
	
	public double getValorTotal() {
		double total = 0.0;
		
		for (CarrinhoProduto cp : produtos) {
			total += cp.getProduto().getPreco() * cp.getQuantidade();
		}
		
		return total;
	}

	@Override
	public String toString() {
		return "Carrinho [cliente=" + cliente + ", produtos=" + produtos + "]";
	}
}