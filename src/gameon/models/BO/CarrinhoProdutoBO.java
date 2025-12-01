package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;

import gameon.models.CarrinhoProduto;
import gameon.models.Produto;
import gameon.models.Usuario;
import gameon.models.DAO.CarrinhoProdutoDAO;
import gameon.models.DTO.CarrinhoProdutoDTO;
import gameon.utils.SessaoUsuario;

public class CarrinhoProdutoBO {
	
	private CarrinhoProdutoDAO carrinhoProdutoDAO = new CarrinhoProdutoDAO();
	private ProdutoBO produtoBO = new ProdutoBO();

	public CarrinhoProduto inserir(CarrinhoProduto carrinhoProduto) {
		if (!existe(carrinhoProduto)) {
			CarrinhoProdutoDTO carrinhoProdutoDTO = toDTO(carrinhoProduto);
			
			carrinhoProdutoDTO = carrinhoProdutoDAO.inserir(carrinhoProdutoDTO);
			
			return toModel(carrinhoProdutoDTO);
		}
		
		return null;
	}
	
	public CarrinhoProduto alterar(CarrinhoProduto carrinhoProduto) {
		CarrinhoProdutoDTO carrinhoProdutoDTO = toDTO(carrinhoProduto);
		
		carrinhoProdutoDTO = carrinhoProdutoDAO.alterar(carrinhoProdutoDTO);
		
		return toModel(carrinhoProdutoDTO);
	}
	
	public boolean excluir(CarrinhoProduto carrinhoProduto) {
		CarrinhoProdutoDTO carrinhoProdutoDTO = toDTO(carrinhoProduto);
		
		return carrinhoProdutoDAO.excluir(carrinhoProdutoDTO.getProdutoId(), carrinhoProdutoDTO.getClienteId());
	}
	
    public CarrinhoProduto procurarPorId(int produtoId, int clienteId) {
    	CarrinhoProdutoDTO carrinhoProdutoDTO = carrinhoProdutoDAO.procurarPorId(produtoId, clienteId);
    	
        return toModel(carrinhoProdutoDTO);
    }
    
	public boolean existe(CarrinhoProduto carrinhoProduto) {
		CarrinhoProdutoDTO carrinhoProdutoDTO = toDTO(carrinhoProduto);
		
		return carrinhoProdutoDAO.existe(carrinhoProdutoDTO);
	}
	
	public List<CarrinhoProduto> pesquisarTodos() {
		List<CarrinhoProdutoDTO> carrinhoProdutosDTO = carrinhoProdutoDAO.pesquisarTodos();
		
		return toModelList(carrinhoProdutosDTO);
	}
	
    private CarrinhoProduto toModel(CarrinhoProdutoDTO carrinhoProdutoDTO) {
    	Produto produto = produtoBO.procurarPorId(carrinhoProdutoDTO.getProdutoId());
    	
    	CarrinhoProduto carrinhoProduto = new CarrinhoProduto(
    			produto,
    			carrinhoProdutoDTO.getQuantidade()
		);
    	
        return carrinhoProduto;
    }
    
    private List<CarrinhoProduto> toModelList(List<CarrinhoProdutoDTO> carrinhoProdutosDTO) {
    	List<CarrinhoProduto> carrinhoProdutos = new ArrayList<CarrinhoProduto>();
    	
    	for (CarrinhoProdutoDTO carrinhoProduto : carrinhoProdutosDTO) {
    		carrinhoProdutos.add(toModel(carrinhoProduto));
    	}
    	
    	return carrinhoProdutos;
    }
    
    private CarrinhoProdutoDTO toDTO(CarrinhoProduto carrinhoProduto) {
    	CarrinhoProdutoDTO carrinhoProdutosDTO = new CarrinhoProdutoDTO();
    	
    	Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
        
    	carrinhoProdutosDTO.setProdutoId(carrinhoProduto.getProduto().getId());
    	carrinhoProdutosDTO.setClienteId(usuario.getId());
    	carrinhoProdutosDTO.setQuantidade(carrinhoProduto.getQuantidade());
    	carrinhoProdutosDTO.setCriadoEm(carrinhoProduto.getCriadoEm());
        
        return carrinhoProdutosDTO;
    }
}
