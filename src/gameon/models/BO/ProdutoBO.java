package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.ProdutoDAO;
import gameon.models.DTO.Produto;

public class ProdutoBO {
	

	public boolean inserir(Produto produto) {
		if (!existe(produto)) {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			
			return produtoDAO.inserir(produto);
		}
		
		return false;
	}
	
	public boolean alterar(Produto produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.alterar(produto);
	}
	
	public boolean excluir(Produto produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.excluir(produto);
	}
	
    public Produto procurarPorId(Produto produto){
    	ProdutoDAO produtoDAO = new ProdutoDAO();
    	
        return produtoDAO.procurarPorId(produto);
    }
    
    public Produto procurarPorNome(Produto produto){
    	ProdutoDAO produtoDAO = new ProdutoDAO();
    	
        return produtoDAO.procurarPorNome(produto);
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
