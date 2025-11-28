package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.Usuario;

public class UsuarioBO {
	
	public boolean inserir(Usuario usuario) {
		if (!existe(usuario)) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			
			return usuarioDAO.inserir(usuario);
		}
		
		return false;
	}
	
	public boolean alterar(Usuario usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.alterar(usuario);
	}
	
	public boolean excluir(Usuario usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.excluir(usuario);
	}
	
    public Usuario procurarPorId(Usuario usuario){
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	
        return usuarioDAO.procurarPorId(usuario);
    }
    
    public Usuario procurarPorEmail(Usuario usuario){
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	
        return usuarioDAO.procurarPorEmail(usuario);
    }
	
	public boolean existe(Usuario usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.existe(usuario);
	}
	
	public List<Usuario> pesquisarTodos() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		return usuarioDAO.pesquisarTodos();
	}
	
}
