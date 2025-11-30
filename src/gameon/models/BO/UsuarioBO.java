package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.Usuario;
import gameon.models.valuesobjects.Email;

public class UsuarioBO {
	
	public Usuario inserir(Usuario usuario) {
		
		validar(usuario);
		
		if (!existe(usuario)) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			
			return usuarioDAO.inserir(usuario);
		}
		
		return null;
	}
	
	public Usuario alterar(Usuario usuario) {
		
		validar(usuario);
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.alterar(usuario);
	}
	
	public boolean excluir(Usuario usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.excluir(usuario.getId());
	}
	
    public Usuario procurarPorId(int usuarioId){
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	
        return usuarioDAO.procurarPorId(usuarioId);
    }
    
    public Usuario procurarPorEmail(String email){
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	
        return usuarioDAO.procurarPorEmail(email);
    }
	
	public boolean existe(Usuario usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.existe(usuario);
	}
	
	public List<Usuario> pesquisarTodos() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		return usuarioDAO.pesquisarTodos();
	}
	
	private void validar(Usuario usuario) {
		try {
	        Email emailVO = new Email(usuario.getEmail());
	        usuario.setEmail(emailVO.getEmail());
	        
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException(e.getMessage());
	    }
	}
	
}
