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
	
//	public Cliente inserir(Cliente cliente) {
//		if (!existe(cliente)) {
//			Map<String, Object> res = Asaas.inserir("customers", cliente.toAsaas());
//			
//	        if (res == null || !res.containsKey("id")) {
//	            return null;
//	        }
//	        
//	        String asaasCliente = res.get("id").toString();
//	        cliente.setAsaasCliente(asaasCliente);
//
//			UsuarioBO usuarioBO = new UsuarioBO();
//			
//			Usuario usuario = usuarioBO.inserir(cliente);
//			cliente.setId(usuario.getId());
//			
//			ClienteDAO clienteDAO = new ClienteDAO();
//			
//			return clienteDAO.inserir(cliente);
//		}
//		
//		return null;
//	}
	
	public Cliente inserir(Cliente cliente) {
	    if (!existe(cliente)) {
	        
	        // --- TENTA INTEGRAR COM ASAAS, MAS NÃO TRAVA SE FALHAR ---
	        String asaasId = "cus_00000FAKE"; // ID Falso padrão para testes
	        try {
	            // Se você tiver a classe Asaas, ele tenta. Se não tiver configurado, dá erro mas seguimos.
	            // Map<String, Object> res = Asaas.inserir("customers", cliente.toAsaas());
	            // if (res != null && res.containsKey("id")) {
	            //    asaasId = res.get("id").toString();
	            // }
	            
	            // COMENTEI A CHAMADA REAL ACIMA.
	            // Para testar o banco, vamos apenas simular que deu certo:
	            System.out.println("Aviso: Pulando integração Asaas (Modo Teste)");
	            
	        } catch (Exception e) {
	            System.out.println("Erro ao conectar no Asaas, usando ID falso: " + e.getMessage());
	        }
	        
	        cliente.setAsaasCliente(asaasId);
	        // -----------------------------------------------------------

	        UsuarioBO usuarioBO = new UsuarioBO();
	        
	        // Salva na tabela Usuario
	        // ATENÇÃO: O usuarioBO.inserir(usuario) retorna o ID (int) ou o Objeto?
	        // No seu código anterior, ele retornava um 'int'.
	        // Se retornar int, mude para: int novoId = usuarioBO.inserir(cliente);
	        // Se retornar Objeto, mantenha:
	        Usuario usuarioSalvo = usuarioBO.inserir(cliente);
	        
	        if (usuarioSalvo == null) {
	            System.out.println("Erro: Falha ao salvar Usuario (verifique o UsuarioDAO e o Console)");
	            return null;
	        }

	        // Passa o ID gerado no Usuario para o Cliente (são a mesma chave)
	        cliente.setId(usuarioSalvo.getId());
	        
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
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		if (clienteDAO.excluir(cliente)) {
			return usuarioDAO.excluir(cliente);
		}
		
		return false;
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
    
    public Cliente procurarPorEmail(String email){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        Usuario usuario = usuarioDAO.procurarPorEmail(email);
        if (usuario == null) return null;
        
        Cliente cliente = procurarPorId(usuario.getId());
        if (cliente == null) return null;

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

}
