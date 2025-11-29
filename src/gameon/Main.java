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
		Email email = new Email("lucas1@gmail.com"); 
		cliente.setEmail(email);
		cliente.setCpf("105.712.719-11");
		cliente.setAsaasCliente("cus_124");
		Senha senha = new Senha("12345678");
		cliente.setSenha(senha);
		cliente.setTelefone("(47) 99117-3910");
		
		if (clienteBO.inserir(cliente) != null) {
			System.out.println("Inserido");
		} else {
			System.out.println("Erro ao inserir");
		}
		
		cliente = clienteBO.procurarPorId(cliente.getId());
		System.out.println(cliente);
		
		cliente.setNome("Lucas Kelim");
		cliente = clienteBO.alterar(cliente);
		System.out.println(cliente);
	}

}
