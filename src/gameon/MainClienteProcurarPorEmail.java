package gameon;

import gameon.models.Cliente;
import gameon.models.BO.ClienteBO;
import gameon.models.valuesobjects.Email;

public class MainClienteProcurarPorEmail {

	public static void main(String[] args) {
		
		ClienteBO clienteBO = new ClienteBO();
		Cliente cliente = new Cliente();
		
		cliente.setEmail(new Email("lucas2@gmail.com"));
		
		cliente = clienteBO.procurarPorEmail(cliente.getEmail().getEmail());
		
		System.out.println(cliente);
		
	}

}
