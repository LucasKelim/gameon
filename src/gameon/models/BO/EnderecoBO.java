package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;

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
        return new Endereco();
    }
    
    private List<Endereco> toModelList(List<EnderecoDTO> enderecosDTO) {
    	List<Endereco> enderecos = new ArrayList<Endereco>();
    	
    	for (EnderecoDTO enderecoDTO : enderecosDTO) {
    		enderecos.add(toModel(enderecoDTO));
    	}
    	
    	return enderecos;
    }

    private EnderecoDTO toDTO(Endereco endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setCodigoPostal(endereco.getCep());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setClienteId(endereco.getCliente().getId());
        enderecoDTO.setCriadoEm(endereco.getCriadoEm());
        
        return enderecoDTO;
    }
}