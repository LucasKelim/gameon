package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.ProdutoDAO;
import gameon.models.DTO.ProdutoDTO;

public class ProdutoBO {
	
	public Produto inserir(Produto produto) {
		if (!existe(produto)) {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			
			return produtoDAO.inserir(produto);
		}
		
		return null;
	}
	
	public ProdutoDTO alterar(ProdutoDTO produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.alterar(produto);
	}
	
	public boolean excluir(ProdutoDTO produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.excluir(produto.getId());
	}
	
    public ProdutoDTO procurarPorId(ProdutoDTO produto){
    	ProdutoDAO produtoDAO = new ProdutoDAO();
    	
        return produtoDAO.procurarPorId(produto.getId());
    }
    
    public ProdutoDTO procurarPorNome(ProdutoDTO produto){
    	ProdutoDAO produtoDAO = new ProdutoDAO();
    	
        return produtoDAO.procurarPorNome(produto.getNome());
    }
	
	public boolean existe(ProdutoDTO produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.existe(produto);
	}
	
	public List<ProdutoDTO> pesquisarTodos() {
		ProdutoDAO produtoDAO = new ProdutoDAO();

		return produtoDAO.pesquisarTodos();
	}

}
