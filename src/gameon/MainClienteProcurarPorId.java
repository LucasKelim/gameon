package gameon;

import gameon.models.BO.ClienteBO;
import gameon.models.DTO.ClienteDTO;

public class MainClienteProcurarPorId {

	public static void main(String[] args) {
		
		ClienteBO clienteBO = new ClienteBO();
		ClienteDTO cliente = new ClienteDTO();
		
		cliente.setId(1);
		
		cliente = clienteBO.procurarPorId(cliente.getId());
		
		System.out.println(cliente);
		
	}

}
