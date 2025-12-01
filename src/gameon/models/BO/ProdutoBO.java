package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;

import gameon.models.Admin;
import gameon.models.Produto;
import gameon.models.DAO.ProdutoDAO;
import gameon.models.DTO.ProdutoDTO;

public class ProdutoBO {
	
	private ProdutoDAO produtoDAO = new ProdutoDAO();
	private AdminBO adminBO = new AdminBO();
	
	public Produto inserir(Produto produto) {
		if (!existe(produto)) {
			ProdutoDTO produtoDTO = toDTO(produto);
			
			produtoDTO = produtoDAO.inserir(produtoDTO);
			
			return toModel(produtoDTO);
		}
		
		return null;
	}
	
	public Produto alterar(Produto produto) {
		ProdutoDTO produtoDTO = toDTO(produto);
		
		produtoDTO = produtoDAO.alterar(produtoDTO);
		
		return toModel(produtoDTO);
	}
	
	public boolean excluir(Produto produto) {
		ProdutoDTO produtoDTO = toDTO(produto);
		
		return produtoDAO.excluir(produtoDTO.getId());
	}
	
    public Produto procurarPorId(int produtoId) {
    	ProdutoDTO produtoDTO = produtoDAO.procurarPorId(produtoId);
    	
        return toModel(produtoDTO);
    }
    
    public Produto procurarPorNome(String produtoNome) {
    	ProdutoDTO produtoDTO = produtoDAO.procurarPorNome(produtoNome); 
    	
        return toModel(produtoDTO);
    }
	
	public boolean existe(Produto produto) {
		ProdutoDTO produtoDTO = toDTO(produto);
		
		return produtoDAO.existe(produtoDTO);
	}
	
	public List<Produto> pesquisarTodos() {
		List<ProdutoDTO> produtosDTO = produtoDAO.pesquisarTodos();
		
		return toModelList(produtosDTO);
	}
	
	private Produto toModel(ProdutoDTO produtoDTO) {
		Admin admin = adminBO.procurarPorId(produtoDTO.getAdminId());
		
		Produto produto = new Produto();
		
		produto.setId(produtoDTO.getId());
		produto.setNome(produtoDTO.getNome());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setPreco(produtoDTO.getPreco());
		produto.setEstoque(produtoDTO.getEstoque());
		produto.setStatus(produtoDTO.getStatus());
		produto.setAdmin(admin);
		produto.setCriadoEm(produtoDTO.getCriadoEm());
		
        return produto;
    }
	    
    private List<Produto> toModelList(List<ProdutoDTO> produtosDTO) {
    	List<Produto> produtos = new ArrayList<Produto>();
    	
    	for (ProdutoDTO produtoDTO : produtosDTO) {
    		produtos.add(toModel(produtoDTO));
    	}
    	
    	return produtos;
    }

    private ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setEstoque(produto.getEstoque());
        produtoDTO.setStatus(produto.isStatus());
        produtoDTO.setAdminId(4);
        produtoDTO.setCriadoEm(produto.getCriadoEm());
        
        return produtoDTO;
    }
}
