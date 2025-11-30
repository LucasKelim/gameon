package gameon;

import gameon.models.BO.ClienteBO;
import gameon.models.DTO.ClienteDTO;

public class MainClienteAlterar {

	public static void main(String[] args) {
		
		ClienteBO clienteBO = new ClienteBO();
		ClienteDTO cliente = new ClienteDTO();
		cliente.setId(1);
		cliente.setNome("Lucas Kelim");
		
		if (clienteBO.alterar(cliente) != null) {
			System.out.println("Alterado");
		} else {
			System.out.println("Erro ao alterar");
		}
		
		
	}

}
