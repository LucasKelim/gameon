package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.ClienteDAO;
import gameon.models.DTO.Cliente;

public class ClienteBO {
	
	public boolean inserir(Cliente cliente) {
		if (!existe(cliente)) {
			ClienteDAO clienteDAO = new ClienteDAO();
			
			return clienteDAO.inserir(cliente);
		}
		
		return false;
	}
	
	public boolean alterar(Cliente cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.alterar(cliente);
	}
	
	public boolean excluir(Cliente cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.excluir(cliente);
	}
	
    public Cliente procurarPorId(Cliente cliente){
    	ClienteDAO clienteDAO = new ClienteDAO();
    	
        return clienteDAO.procurarPorId(cliente);
    }
    
    public Cliente procurarPorEmail(Cliente cliente){
    	ClienteDAO clienteDAO = new ClienteDAO();
    	
        return clienteDAO.procurarPorEmail(cliente);
    }
	
	public boolean existe(Cliente cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.existe(cliente);
	}
	
	public List<Cliente> pesquisarTodos() {
		ClienteDAO clienteDAO = new ClienteDAO();

		return clienteDAO.pesquisarTodos();
	}

}
