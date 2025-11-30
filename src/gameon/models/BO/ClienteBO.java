package gameon.models.BO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameon.models.Cliente;          
import gameon.models.Usuario;           
import gameon.models.DAO.ClienteDAO;    
import gameon.models.DTO.ClienteDTO;    
import gameon.models.valuesobjects.Cpf;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import gameon.services.asaas.Asaas;

public class ClienteBO {
	
	public Cliente inserir(Cliente cliente) {
	    validar(cliente);
	    
	    // 1. Verifica ANTES de criar o usuário para não gastar ID a toa
	    ClienteDAO clienteDAO = new ClienteDAO();
	    if (clienteDAO.cpfOuTelefoneExistem(cliente.getCpf().getCpf(), cliente.getTelefone())) {
	        System.out.println("Erro: CPF ou Telefone já cadastrados.");
	        return null;
	    }

	    // 2. Cria o Usuário
	    UsuarioBO usuarioBO = new UsuarioBO();
	    Usuario usuarioSalvo = usuarioBO.inserir(cliente);
	    
	    if (usuarioSalvo == null) {
	        return null; // Falhou no usuário (Email duplicado, etc)
	    }
	    
	    // Configura os dados do cliente com o ID gerado
	    cliente.setId(usuarioSalvo.getId());
	    cliente.setCriadoEm(usuarioSalvo.getCriadoEm());
	    cliente.setAsaasCliente("cus_TESTE_" + System.currentTimeMillis());

	    // 3. Tenta Salvar Cliente
	    ClienteDTO dto = converterParaDTO(cliente);
	    ClienteDTO dtoSalvo = clienteDAO.inserir(dto);
	    
	    if (dtoSalvo != null) {
	        return cliente; // Sucesso!
	    } else {
	        // FALHA CRÍTICA: O usuário foi criado, mas o cliente não.
	        // ROLLBACK: Apaga o usuário para não deixar lixo no banco.
	        System.out.println("Erro ao salvar Cliente. Desfazendo usuário...");
	        usuarioBO.excluir(usuarioSalvo); 
	        return null;
	    }
	}
	
	public Cliente alterar(Cliente cliente) {
		validar(cliente);
		
		// Atualiza Asaas
		try {
			// Asaas.alterar(buildAsaasUrl(cliente), buildAsaasPayload(cliente));
		} catch (Exception e) {
			System.out.println("Erro Asaas Update: " + e.getMessage());
		}
		
		// 1. Altera dados de Usuário
		UsuarioBO usuarioBO = new UsuarioBO();
		usuarioBO.alterar(cliente);
		
		// 2. Altera dados de Cliente
		ClienteDTO dto = converterParaDTO(cliente);
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.alterar(dto);
		
		return cliente;
	}
	
	public boolean excluir(Cliente cliente) {
		// Remove do Asaas
		try {
			// Asaas.excluir(buildAsaasUrl(cliente));
		} catch (Exception e) {
			System.out.println("Erro Asaas Delete: " + e.getMessage());
		}
		
		// Remove do Banco (Cliente e depois Usuário)
		ClienteDTO dto = new ClienteDTO();
		dto.setId(cliente.getId());
		
		ClienteDAO clienteDAO = new ClienteDAO();
		if (clienteDAO.excluir(dto.getId())) { // Passa o ID int
			UsuarioBO usuarioBO = new UsuarioBO();
			return usuarioBO.excluir(cliente);
		}
		
		return false;
	}
	
    public Cliente procurarPorId(int id) {
        ClienteDAO clienteDAO = new ClienteDAO();
        UsuarioBO usuarioBO = new UsuarioBO();

        // Busca dados específicos do cliente
        ClienteDTO dto = clienteDAO.procurarPorId(id);
        if (dto == null) return null;

        // Busca dados do usuário (Nome, Email...)
        Usuario usuario = usuarioBO.procurarPorId(id);
        if (usuario == null) return null;

        // Junta tudo num objeto só
        return mergeModel(usuario, dto);
    }
    
    // Método auxiliar importante: Junta dados de Usuario com dados de ClienteDTO
    private Cliente mergeModel(Usuario usuario, ClienteDTO dto) {
    	Cliente cliente = new Cliente();
    	// Dados de Usuario
    	cliente.setId(usuario.getId());
    	cliente.setNome(usuario.getNome());
    	cliente.setEmail(usuario.getEmail());
    	cliente.setSenha(usuario.getSenha());
    	cliente.setCriadoEm(usuario.getCriadoEm());
    	
    	// Dados de Cliente
    	cliente.setCpf(new Cpf(dto.getCpf()));
    	cliente.setTelefone(dto.getTelefone());
    	cliente.setAsaasCliente(dto.getAsaasCliente());
    	
    	return cliente;
    }
    
    public Cliente procurarPorEmail(String email){
        UsuarioBO usuarioBO = new UsuarioBO();
        Usuario usuario = usuarioBO.procurarPorEmail(email);
        
        if (usuario != null) {
        	return procurarPorId(usuario.getId());
        }
        
        return null;
    }
	
	public List<Cliente> pesquisarTodos() {
		ClienteDAO clienteDAO = new ClienteDAO();
		UsuarioBO usuarioBO = new UsuarioBO();
		
		List<ClienteDTO> listaDTO = clienteDAO.pesquisarTodos();
		List<Cliente> listaModel = new ArrayList<>();
		
		if (listaDTO != null) {
			for (ClienteDTO dto : listaDTO) {
				// Para cada cliente, busca os dados de usuário correspondente
				// Nota: Isso pode ser lento (N+1 queries). O ideal seria o DAO já fazer o JOIN.
				// Se o seu DAO já faz JOIN e retorna tudo no DTO, melhor ainda.
				Usuario usuario = usuarioBO.procurarPorId(dto.getId());
				if (usuario != null) {
					listaModel.add(mergeModel(usuario, dto));
				}
			}
		}

		return listaModel;
	}
	
	
	public boolean existe(ClienteDTO dto) {
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.existe(dto);
	}
	
	private void validar(Cliente cliente) {
		if (cliente == null) throw new IllegalArgumentException("Cliente inválido");
		
		// O CPF já é validado pelo próprio Objeto de Valor (Cpf) na construção.
		// Aqui validamos apenas se ele está presente.
		if (cliente.getCpf() == null) {
			throw new IllegalArgumentException("CPF é obrigatório");
		}
		
		if (cliente.getTelefone() == null || cliente.getTelefone().isEmpty()) {
			throw new IllegalArgumentException("Telefone é obrigatório");
		}
	}
	
	private ClienteDTO converterParaDTO(Cliente model) {
		ClienteDTO dto = new ClienteDTO();
		dto.setId(model.getId());
		dto.setCpf(model.getCpf().getCpf()); 
		dto.setTelefone(model.getTelefone());
		dto.setAsaasCliente(model.getAsaasCliente());
		return dto;
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
}