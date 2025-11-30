package gameon;

import gameon.models.BO.ClienteBO;
import gameon.models.DTO.Cliente;

public class MainClienteInserir {

	public static void main(String[] args) {
		
		ClienteBO clienteBO = new ClienteBO();
		Cliente cliente = new Cliente();
		cliente.setNome("Lucas");
		cliente.setEmail("lucas2@gmail.com");
		cliente.setCpf("105.712.719-10");
		cliente.setSenha("12345678");
		cliente.setTelefone("(47) 99117-3912");
		
		if (clienteBO.inserir(cliente) != null) {
			System.out.println("Inserido");
		} else {
			System.out.println("Erro ao inserir");
		}
	}

}
