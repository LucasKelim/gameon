package gameon.models;

import java.time.LocalDateTime;

import gameon.models.enums.OrdemStatus;
import gameon.models.interfaces.MetodoPagamento;

public class Ordem {
	private int id;
	private OrdemStatus status;
	private MetodoPagamento metodoPagamento;
	private double valorTotal;
	private Endereco endereco;
	private String asaasOrdem;
	private LocalDateTime criadoEm;
	
	public Ordem() {}
	
	public Ordem(int id, OrdemStatus status, MetodoPagamento metodoPagamento, double valorTotal, Endereco endereco, String asaasOrdem, LocalDateTime criadoEm) {
		setId(id);
		setStatus(status);
		setMetodoPagamento(metodoPagamento);
		setValorTotal(valorTotal);
		setEndereco(endereco);
		setAsaasOrdem(asaasOrdem);
		setCriadoEm(criadoEm);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public OrdemStatus getStatus() {
		return status;
	}
	public void setStatus(OrdemStatus status) {
		this.status = status;
	}
	
	public MetodoPagamento getMetodoPagamento() {
		return metodoPagamento;
	}
	
	public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}
	
	public double getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String getAsaasOrdem() {
		return asaasOrdem;
	}
	
	public void setAsaasOrdem(String asaasOrdem) {
		this.asaasOrdem = asaasOrdem;
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
		builder.append("Ordem [id=");
		builder.append(id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", metodoPagamento=");
		builder.append(metodoPagamento);
		builder.append(", valorTotal=");
		builder.append(valorTotal);
		builder.append(", ");
		builder.append(endereco);
		builder.append(", asaasOrdem=");
		builder.append(asaasOrdem);
		builder.append(", criadoEm=");
		builder.append(criadoEm);
		builder.append("]");
		return builder.toString();
	}
}
