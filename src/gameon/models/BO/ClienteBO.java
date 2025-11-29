package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.ClienteDAO;
import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.Cliente;
import gameon.models.DTO.Usuario;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class ClienteBO {
	
	public Cliente inserir(Cliente cliente) {
		if (!existe(cliente)) {
			UsuarioBO usuarioBO = new UsuarioBO();
			
			Usuario usuario = usuarioBO.inserir(cliente);
			cliente.setId(usuario.getId());
			
			ClienteDAO clienteDAO = new ClienteDAO();
			
			return clienteDAO.inserir(cliente);
		}
		
		return null;
	}
	
	public Cliente alterar(Cliente cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.alterar(cliente);
	}
	
	public boolean excluir(Cliente cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.excluir(cliente);
	}
	
    public Cliente procurarPorId(Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Cliente cli = clienteDAO.procurarPorId(cliente);
        if (cli == null) return null;

        Usuario usuario = usuarioDAO.procurarPorId(cliente);
        if (usuario == null) return null;

        cli.setNome(usuario.getNome());
        cli.setEmail(new Email(usuario.getEmail()));
        cli.setSenha(new Senha(usuario.getSenha()));
        cli.setCriadoEm(usuario.getCriadoEm());

        return cli;
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
