package gameon.models.BO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameon.models.DAO.ClienteDAO;
import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.Cliente;
import gameon.models.DTO.Usuario;
import gameon.models.valuesobjects.Cpf;
import gameon.services.asaas.Asaas;

public class ClienteBO {
	
	public Cliente inserir(Cliente cliente) {
		
		validar(cliente);
		
		if (!existe(cliente)) {
	        UsuarioBO usuarioBO = new UsuarioBO();
			Usuario usuario = usuarioBO.inserir(cliente);
			
			cliente.setId(usuario.getId());
			
			Map<String, Object> res = Asaas.inserir("customers", buildAsaasPayload(cliente));
			
	        if (res == null || !res.containsKey("id")) {
	            return null;
	        }
	        
	        String asaasCliente = res.get("id").toString();
	        cliente.setAsaasCliente(asaasCliente);
			
			ClienteDAO clienteDAO = new ClienteDAO();
			
			return clienteDAO.inserir(cliente);
		}
		
		return null;
	}
	
	public Cliente alterar(Cliente cliente) {
		
		validar(cliente);
		
		Map<String, Object> res = Asaas.alterar(buildAsaasUrl(cliente), buildAsaasPayload(cliente));
		
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
		Map<String, Object> res = Asaas.excluir(buildAsaasUrl(cliente));
		
        if (res == null || !res.containsKey("id")) {
            return false;
        }
        
		ClienteDAO clienteDAO = new ClienteDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		if (clienteDAO.excluir(cliente.getId())) {
			return usuarioDAO.excluir(cliente.getId());
		}
		
		return false;
	}
	
    public Cliente procurarPorId(int clienteId) {
        ClienteDAO clienteDAO = new ClienteDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Cliente cliente = clienteDAO.procurarPorId(clienteId);
        if (cliente == null) return null;

        Usuario usuario = usuarioDAO.procurarPorId(clienteId);
        if (usuario == null) return null;

        cliente.setNome(usuario.getNome());
        cliente.setEmail(usuario.getEmail());
        cliente.setSenha(usuario.getSenha());
        cliente.setCriadoEm(usuario.getCriadoEm());

        return cliente;
    }
    
    public Cliente procurarPorEmail(String email){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        Usuario usuario = usuarioDAO.procurarPorEmail(email);
        if (usuario == null) return null;
        
        Cliente cliente = procurarPorId(usuario.getId());
        
        return cliente;
    }
	
	public boolean existe(Cliente cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.existe(cliente);
	}
	
	public List<Cliente> pesquisarTodos() {
		ClienteDAO clienteDAO = new ClienteDAO();

		return clienteDAO.pesquisarTodos();
	}
	
	private String buildAsaasUrl(Cliente cliente) {
		return "customers/" + cliente.getAsaasCliente();
	}
	
	private Map<String, Object> buildAsaasPayload(Cliente cliente) {
        Map<String, Object> dadosAsaas = new HashMap<>();
        
        dadosAsaas.put("name", cliente.getNome());
        dadosAsaas.put("cpfCnpj", cliente.getCpf());
        
        return dadosAsaas;
    }
	
	private void validar(Cliente cliente) {
	    try {
	        Cpf cpfVO = new Cpf(cliente.getCpf());
	        cliente.setCpf(cpfVO.getCpf()); 
	        
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException(e.getMessage());
	    }
	    
	    if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
	        throw new IllegalArgumentException("O nome não pode ser vazio.");
	    }
	}
}
