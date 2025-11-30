package gameon.models.BO;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import gameon.models.Usuario;
import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.UsuarioDTO;
import gameon.models.valuesobjects.Email;

public class UsuarioBO {
	
	public Usuario inserir(Usuario usuario) {
		
		validar(usuario);
		
		usuario.setSenha(hashSenha(usuario.getSenha()));
		
		if (!existe(usuario)) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			
			return usuarioDAO.inserir(usuario);
		}
		
		return null;
	}
	
	public UsuarioDTO alterar(UsuarioDTO usuario) {
		
		validar(usuario);
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.alterar(usuario);
	}
	
	public boolean excluir(UsuarioDTO usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.excluir(usuario.getId());
	}
	
    public UsuarioDTO procurarPorId(int usuarioId){
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	
        return usuarioDAO.procurarPorId(usuarioId);
    }
    
    public UsuarioDTO procurarPorEmail(String email){
    	UsuarioDAO usuarioDAO = new UsuarioDAO();
    	
        return usuarioDAO.procurarPorEmail(email);
    }
	
	public boolean existe(UsuarioDTO usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.existe(usuario);
	}
	
	public List<UsuarioDTO> pesquisarTodos() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		return usuarioDAO.pesquisarTodos();
	}
	
	private void validar(UsuarioDTO usuario) {
		try {
	        Email emailVO = new Email(usuario.getEmail());
	        usuario.setEmail(emailVO.getEmail());
	        
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException(e.getMessage());
	    }
	}
	
	private String hashSenha(String senha) {
		return BCrypt.hashpw(senha, BCrypt.gensalt());
	}
}
