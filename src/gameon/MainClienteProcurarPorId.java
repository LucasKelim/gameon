package gameon;

import gameon.models.BO.ClienteBO;
import gameon.models.DTO.Cliente;

public class MainClienteProcurarPorId {

	public static void main(String[] args) {
		
		ClienteBO clienteBO = new ClienteBO();
		Cliente cliente = new Cliente();
		
		cliente.setId(1);
		
		cliente = clienteBO.procurarPorId(cliente.getId());
		
		System.out.println(cliente);
		
	}

}
