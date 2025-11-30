package gameon.models;

public class Carrinho {
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Carrinho [cliente=");
		builder.append(cliente);
		builder.append("]");
		return builder.toString();
	}
}
