package gameon.models.BO;

import java.util.List;
import java.util.Map;

import gameon.models.DAO.ClienteDAO;
import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.Cliente;
import gameon.models.DTO.Usuario;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import gameon.services.asaas.Asaas;

public class ClienteBO {
	
	public Cliente inserir(Cliente cliente) {
		if (!existe(cliente)) {
			Map<String, Object> res = Asaas.inserir("customers", cliente.toAsaas());
			
	        if (res == null || !res.containsKey("id")) {
	            return null;
	        }
	        
	        String asaasCliente = res.get("id").toString();
	        cliente.setAsaasCliente(asaasCliente);

			UsuarioBO usuarioBO = new UsuarioBO();
			
			Usuario usuario = usuarioBO.inserir(cliente);
			cliente.setId(usuario.getId());
			
			ClienteDAO clienteDAO = new ClienteDAO();
			
			return clienteDAO.inserir(cliente);
		}
		
		return null;
	}
	
	public Cliente alterar(Cliente cliente) {
		Map<String, Object> res = Asaas.alterar(cliente.getAsaasUrl(), cliente.toAsaas());
		
        if (res == null || !res.containsKey("id")) {
            return null;
        }
        
		ClienteDAO clienteDAO = new ClienteDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		usuarioDAO.alterar(cliente);
		clienteDAO.alterar(cliente);
		
		return procurarPorId(cliente.getId());
	}
	
	public boolean excluir(Cliente cliente) {
		Map<String, Object> res = Asaas.excluir(cliente.getAsaasUrl());
		
        if (res == null || !res.containsKey("id")) {
            return false;
        }
        
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.excluir(cliente);
	}
	
    public Cliente procurarPorId(int clienteId) {
        ClienteDAO clienteDAO = new ClienteDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Cliente cli = clienteDAO.procurarPorId(clienteId);
        if (cli == null) return null;

        Usuario usuario = usuarioDAO.procurarPorId(clienteId);
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
