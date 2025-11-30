package gameon;

import gameon.models.BO.ClienteBO;
import gameon.models.DTO.Cliente;

public class MainClienteProcurarPorEmail {

	public static void main(String[] args) {
		
		ClienteBO clienteBO = new ClienteBO();
		Cliente cliente = new Cliente();
		
		cliente.setEmail("lucas2@gmail.com");
		
		cliente = clienteBO.procurarPorEmail(cliente.getEmail());
		
		System.out.println(cliente);
		
	}

}
