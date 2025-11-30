package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.ProdutoDAO;
import gameon.models.DTO.Produto;

public class ProdutoBO {
	

	public Produto inserir(Produto produto) {
		if (!existe(produto)) {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			
			return produtoDAO.inserir(produto);
		}
		
		return null;
	}
	
	public Produto alterar(Produto produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.alterar(produto);
	}
	
	public boolean excluir(Produto produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.excluir(produto.getId());
	}
	
    public Produto procurarPorId(Produto produto){
    	ProdutoDAO produtoDAO = new ProdutoDAO();
    	
        return produtoDAO.procurarPorId(produto.getId());
    }
    
    public Produto procurarPorNome(Produto produto){
    	ProdutoDAO produtoDAO = new ProdutoDAO();
    	
        return produtoDAO.procurarPorNome(produto.getNome());
    }
	
	public boolean existe(Produto produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.existe(produto);
	}
	
	public List<Produto> pesquisarTodos() {
		ProdutoDAO produtoDAO = new ProdutoDAO();

		return produtoDAO.pesquisarTodos();
	}

}
