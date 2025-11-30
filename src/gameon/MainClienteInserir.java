package gameon;

import gameon.models.Cliente;
import gameon.models.BO.ClienteBO;
import gameon.models.valuesobjects.Cpf;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class MainClienteInserir {

	public static void main(String[] args) {
		
		ClienteBO clienteBO = new ClienteBO();
		Cliente cliente = new Cliente();
		cliente.setNome("Lucas");
		cliente.setEmail(new Email("lucas@gmail.com"));
		cliente.setCpf(new Cpf("105.712.719-10"));
		cliente.setSenha(new Senha("12345678"));
		cliente.setTelefone("(47) 99117-3912");
		
		if (clienteBO.inserir(cliente) != null) {
			System.out.println("Inserido");
		} else {
			System.out.println("Erro ao inserir");
		}
	}

}
