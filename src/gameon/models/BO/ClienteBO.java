package gameon.models.BO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameon.models.Cliente;
import gameon.models.Usuario;
import gameon.models.DAO.ClienteDAO;
import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.ClienteDTO;
import gameon.models.DTO.UsuarioDTO;
import gameon.models.valuesobjects.Cpf;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import gameon.services.asaas.Asaas;

public class ClienteBO {
	
	private ClienteDAO clienteDAO = new ClienteDAO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	public Cliente inserir(Cliente cliente) {
		ClienteDTO clienteDTO = toClienteDTO(cliente);
		
		if (!existe(cliente)) {
	        UsuarioBO usuarioBO = new UsuarioBO();
			Usuario usuario = usuarioBO.inserir(cliente);
			
			clienteDTO.setId(usuario.getId());
			
			Map<String, Object> res = Asaas.inserir(
				"customers", 
				buildAsaasPayload(cliente)
			);
			
	        if (res == null || !res.containsKey("id")) {
	            return null;
	        }
	        
	        String asaasCliente = res.get("id").toString();
	        clienteDTO.setAsaasCliente(asaasCliente);
	        
			clienteDTO = clienteDAO.inserir(clienteDTO);
			
			return toModel(clienteDTO);
		}
		
		return null;
	}
	
	public Cliente alterar(Cliente cliente) {
		
		Map<String, Object> res = Asaas.alterar(
			buildAsaasUrl(cliente), 
			buildAsaasPayload(cliente)
		);
		
        if (res == null || !res.containsKey("id")) {
            return null;
        }
        
        UsuarioDTO usuarioDTO = toUsuarioDTO(cliente);
        usuarioDAO.alterar(usuarioDTO);
        
        ClienteDTO clienteDTO = toClienteDTO(cliente);		
		clienteDAO.alterar(clienteDTO);
		
		return cliente;
	}
	
	public boolean excluir(Cliente cliente) {
		
		Map<String, Object> res = Asaas.excluir(
			buildAsaasUrl(cliente)
		);
		
        if (res == null || !res.containsKey("id")) {
            return false;
        }
        
		UsuarioDTO usuarioDTO = toUsuarioDTO(cliente);
		ClienteDTO clienteDTO = toClienteDTO(cliente);
		
		if (clienteDAO.excluir(clienteDTO.getId())) {
			return usuarioDAO.excluir(usuarioDTO.getId());
		}
		
		return false;
	}
	
    public Cliente procurarPorId(int clienteId) {
        ClienteDTO cliente = clienteDAO.procurarPorId(clienteId);
        if (cliente == null) return null;

        return toModel(cliente);
    }
    
    public Cliente procurarPorEmail(String email) {
        UsuarioDTO usuario = usuarioDAO.procurarPorEmail(email);
        if (usuario == null) return null;
        
        Cliente cliente = procurarPorId(usuario.getId());
        
        return cliente;
    }
	
	public boolean existe(Cliente cliente) {
		ClienteDTO clienteDTO = toClienteDTO(cliente);
		
		return clienteDAO.existe(clienteDTO);
	}
	
	public List<Cliente> pesquisarTodos() {
		List<ClienteDTO> clientesDTO = clienteDAO.pesquisarTodos();
		
		return toModelList(clientesDTO);
	}
	
	private String buildAsaasUrl(Cliente cliente) {
		return "customers/" + cliente.getAsaasCliente();
	}
	
	private Map<String, Object> buildAsaasPayload(Cliente cliente) {
        Map<String, Object> dadosAsaas = new HashMap<>();
        
        dadosAsaas.put("name", cliente.getNome());
        dadosAsaas.put("cpfCnpj", cliente.getCpf().getCpf());
        
        return dadosAsaas;
    }
	
    private Cliente toModel(ClienteDTO clienteDTO) {
    	
    	UsuarioDTO usuarioDTO = usuarioDAO.procurarPorId(clienteDTO.getId());
    	
    	if (usuarioDTO == null) return null;
    	
    	Cliente cliente = new Cliente(
    		usuarioDTO.getId(),
    		usuarioDTO.getNome(),
    		new Email(usuarioDTO.getEmail()),
    		new Senha(usuarioDTO.getSenha()),
    		new Cpf(clienteDTO.getCpf()),
    		clienteDTO.getTelefone(),
    		clienteDTO.getAsaasCliente(),
    		usuarioDTO.getCriadoEm()
		);
    	
        return cliente;
    }
    
    private List<Cliente> toModelList(List<ClienteDTO> clientesDTO) {
    	List<Cliente> clientes = new ArrayList<Cliente>();
    	
    	for (ClienteDTO clienteDTO : clientesDTO) {
    		clientes.add(toModel(clienteDTO));
    	}
    	
    	return clientes;
    }
    
    private UsuarioDTO toUsuarioDTO(Cliente cliente) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        
        usuarioDTO.setId(cliente.getId());
        usuarioDTO.setNome(cliente.getNome());
        usuarioDTO.setEmail(cliente.getEmail().getEmail());
        usuarioDTO.setSenha(cliente.getSenha().getSenha());
        usuarioDTO.setCriadoEm(cliente.getCriadoEm());
        
        return usuarioDTO;
    }

    private ClienteDTO toClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        
        clienteDTO.setId(cliente.getId());
        clienteDTO.setCpf(cliente.getCpf().getCpf());
        clienteDTO.setTelefone(cliente.getTelefone());
        clienteDTO.setAsaasCliente(cliente.getAsaasCliente());
        
        return clienteDTO;
    }
}