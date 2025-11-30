package gameon.models.BO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameon.models.Cliente;
import gameon.models.DAO.ClienteDAO;
import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.ClienteDTO;
import gameon.models.DTO.UsuarioDTO;
import gameon.models.valuesobjects.Cpf;
import gameon.services.asaas.Asaas;

public class ClienteBO {
	
	public Cliente inserir(Cliente cliente) {
		
		validar(cliente);
		
		if (!existe(cliente)) {
	        UsuarioBO usuarioBO = new UsuarioBO();
			UsuarioDTO usuario = usuarioBO.inserir(cliente);
			
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
	
//	public Cliente inserir(Cliente cliente) {
//	    if (!existe(cliente)) {
//	        
//	        // --- TENTA INTEGRAR COM ASAAS, MAS NÃO TRAVA SE FALHAR ---
//	        String asaasId = "cus_00000FAKE"; // ID Falso padrão para testes
//	        try {
//	            // Se você tiver a classe Asaas, ele tenta. Se não tiver configurado, dá erro mas seguimos.
//	            // Map<String, Object> res = Asaas.inserir("customers", cliente.toAsaas());
//	            // if (res != null && res.containsKey("id")) {
//	            //    asaasId = res.get("id").toString();
//	            // }
//	            
//	            // COMENTEI A CHAMADA REAL ACIMA.
//	            // Para testar o banco, vamos apenas simular que deu certo:
//	            System.out.println("Aviso: Pulando integração Asaas (Modo Teste)");
//	            
//	        } catch (Exception e) {
//	            System.out.println("Erro ao conectar no Asaas, usando ID falso: " + e.getMessage());
//	        }
//	        
//	        cliente.setAsaasCliente(asaasId);
//	        // -----------------------------------------------------------
//
//	        UsuarioBO usuarioBO = new UsuarioBO();
//	        
//	        // Salva na tabela Usuario
//	        // ATENÇÃO: O usuarioBO.inserir(usuario) retorna o ID (int) ou o Objeto?
//	        // No seu código anterior, ele retornava um 'int'.
//	        // Se retornar int, mude para: int novoId = usuarioBO.inserir(cliente);
//	        // Se retornar Objeto, mantenha:
//	        Usuario usuarioSalvo = usuarioBO.inserir(cliente);
//	        
//	        if (usuarioSalvo == null) {
//	            System.out.println("Erro: Falha ao salvar Usuario (verifique o UsuarioDAO e o Console)");
//	            return null;
//	        }
//
//	        // Passa o ID gerado no Usuario para o Cliente (são a mesma chave)
//	        cliente.setId(usuarioSalvo.getId());
//	        
//	        ClienteDAO clienteDAO = new ClienteDAO();
//	        return clienteDAO.inserir(cliente);
//	    }
//	    
//	    return null;
//	}
	
	public ClienteDTO alterar(ClienteDTO cliente) {
		
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
	
	public boolean excluir(ClienteDTO cliente) {
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
	
    public ClienteDTO procurarPorId(int clienteId) {
        ClienteDAO clienteDAO = new ClienteDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        ClienteDTO cliente = clienteDAO.procurarPorId(clienteId);
        if (cliente == null) return null;

        UsuarioDTO usuario = usuarioDAO.procurarPorId(clienteId);
        if (usuario == null) return null;

        return cliente;
    }
    
    public ClienteDTO procurarPorEmail(String email){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        UsuarioDTO usuario = usuarioDAO.procurarPorEmail(email);
        if (usuario == null) return null;
        
        ClienteDTO cliente = procurarPorId(usuario.getId());
        
        return cliente;
    }
	
	public boolean existe(ClienteDTO cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.existe(cliente);
	}
	
	public List<ClienteDTO> pesquisarTodos() {
		ClienteDAO clienteDAO = new ClienteDAO();

		return clienteDAO.pesquisarTodos();
	}
	
	private String buildAsaasUrl(ClienteDTO cliente) {
		return "customers/" + cliente.getAsaasCliente();
	}
	
	private Map<String, Object> buildAsaasPayload(ClienteDTO cliente) {
        Map<String, Object> dadosAsaas = new HashMap<>();
        
        dadosAsaas.put("name", cliente.getNome());
        dadosAsaas.put("cpfCnpj", cliente.getCpf());
        
        return dadosAsaas;
    }
	
	private void validar(ClienteDTO cliente) {
	    try {
	        Cpf cpfVO = new Cpf(cliente.getCpf());
	        cliente.setCpf(cpfVO.getCpf()); 
	        
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException(e.getMessage());
	    }
	}
}
