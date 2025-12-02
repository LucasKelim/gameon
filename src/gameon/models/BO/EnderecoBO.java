package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;

import gameon.models.Cliente;
import gameon.models.Endereco;
import gameon.models.DAO.EnderecoDAO;
import gameon.models.DTO.EnderecoDTO;

public class EnderecoBO {
	
	private EnderecoDAO enderecoDAO = new EnderecoDAO();

    public Endereco inserir(Endereco endereco) {
        if (!existe(endereco)) {
        	EnderecoDTO enderecoDTO = toDTO(endereco);
        	
        	enderecoDTO = enderecoDAO.inserir(enderecoDTO);
        	
        	return toModel(enderecoDTO);
        }
        
        return null;
    }
    
    public Endereco alterar(Endereco endereco) {
        EnderecoDTO enderecoDTO = toDTO(endereco);
        
        enderecoDTO = enderecoDAO.alterar(enderecoDTO);
        
        return toModel(enderecoDTO);
    }
    
    public boolean excluir(Endereco endereco) {
        EnderecoDTO enderecoDTO = toDTO(endereco);
        
        return enderecoDAO.excluir(enderecoDTO.getId());
    }
    
    public Endereco procurarPorId(int enderecoId){
    	EnderecoDTO enderecoDTO = enderecoDAO.procurarPorId(enderecoId);
    	
        return toModel(enderecoDTO);
    }
    
    public List<Endereco> pesquisarTodos() {
        List<EnderecoDTO> enderecosDTO = enderecoDAO.pesquisarTodos();
        
        return toModelList(enderecosDTO);
    }
    
    public boolean existe(Endereco endereco) {
    	EnderecoDTO enderecoDTO = toDTO(endereco);
    	
    	return enderecoDAO.existe(enderecoDTO);
    }
    
    private Endereco toModel(EnderecoDTO enderecoDTO) {
        if (enderecoDTO == null) return null;
        
        // Precisamos do ClienteBO para buscar o cliente
        ClienteBO clienteBO = new ClienteBO();
        Cliente cliente = clienteBO.procurarPorId(enderecoDTO.getClienteId());
        
        if (cliente == null) {
            System.out.println("ERRO: Cliente não encontrado para endereço ID: " + enderecoDTO.getId());
            return null;
        }
        
        return new Endereco(
            enderecoDTO.getId(),
            enderecoDTO.getLogradouro(),
            enderecoDTO.getNumero(),
            enderecoDTO.getBairro(),
            enderecoDTO.getCidade(),
            enderecoDTO.getCodigoPostal(),
            enderecoDTO.getEstado(),
            cliente,
            enderecoDTO.getCriadoEm()
        );
    }
    
    private List<Endereco> toModelList(List<EnderecoDTO> enderecosDTO) {
    	List<Endereco> enderecos = new ArrayList<Endereco>();
    	
    	for (EnderecoDTO enderecoDTO : enderecosDTO) {
    		enderecos.add(toModel(enderecoDTO));
    	}
    	
    	return enderecos;
    }

    private EnderecoDTO toDTO(Endereco endereco) {
        System.out.println("DEBUG: Convertendo Endereco para DTO...");
        
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setCidade(endereco.getCidade());
        
        // IMPORTANTE: cep do modelo -> codigoPostal do DTO
        enderecoDTO.setCodigoPostal(endereco.getCep());
        
        enderecoDTO.setEstado(endereco.getEstado());
        
        if (endereco.getCliente() != null) {
            enderecoDTO.setClienteId(endereco.getCliente().getId());
            System.out.println("DEBUG: Cliente ID: " + endereco.getCliente().getId());
        } else {
            System.out.println("ERRO: Endereco.getCliente() é null!");
            return null;
        }
        
        enderecoDTO.setCriadoEm(endereco.getCriadoEm());
        
        System.out.println("DEBUG: DTO criado: " + enderecoDTO);
        return enderecoDTO;
    }
    
    public List<Endereco> listarPorCliente(int clienteId) {
        List<EnderecoDTO> enderecosDTO = enderecoDAO.procurarPorClienteId(clienteId);
        if (enderecosDTO == null) return new ArrayList<>();
        return toModelList(enderecosDTO);
    }
}