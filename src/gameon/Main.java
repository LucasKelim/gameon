package gameon;

import gameon.models.BO.ClienteBO;
import gameon.models.DTO.Cliente;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class Main {

	public static void main(String[] args) {
		
		ClienteBO clienteBO = new ClienteBO();
		Cliente cliente = new Cliente();
		cliente.setNome("Lucas");
		Email email = new Email("lucas2@gmail.com"); 
		cliente.setEmail(email);
		cliente.setCpf("105.712.719-10");
		Senha senha = new Senha("12345678");
		cliente.setSenha(senha);
		cliente.setTelefone("(47) 99117-3912");
		
		if (clienteBO.inserir(cliente) != null) {
			System.out.println("Inserido");
		} else {
			System.out.println("Erro ao inserir");
		}
		
		System.out.println(clienteBO.procurarPorId(cliente.getId()));
		
		cliente.setNome("Lucas Kelim");
		
		System.out.println(clienteBO.alterar(cliente));
		
		
//		if (clienteBO.excluir(cliente)) {
//			System.out.println("Excluido");
//		} else {
//			System.out.println("Erro ao excluir");
//		}
		
		System.out.println(clienteBO.procurarPorEmail("lucas2@gmail.com"));
	}

}
