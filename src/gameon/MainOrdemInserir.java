package gameon;

import gameon.metodosPagamento.Pix;
import gameon.models.Cliente;
import gameon.models.Endereco;
import gameon.models.Ordem;
import gameon.models.BO.OrdemBO;

public class MainOrdemInserir {

	public static void main(String[] args) {
		
		OrdemBO ordemBO = new OrdemBO();
		
		Ordem ordem = new Ordem();
		
		Pix pix = new Pix();
		Endereco endereco = new Endereco();
		endereco.setId(1);
		Cliente cliente = new Cliente();
		cliente.setAsaasCliente("cus_000007268042");
		endereco.setCliente(cliente);
		
		ordem.setMetodoPagamento(pix);
		ordem.setValorTotal(100);
		ordem.setEndereco(endereco);
		
		System.out.println("MP: " + ordem.getMetodoPagamento().descricao());
		
		if (ordemBO.inserir(ordem) != null) {
			System.out.println("Ordem inserida");
		} else {
			System.out.println("Erro ao inserir Ordem");
		}
		
	}

}
