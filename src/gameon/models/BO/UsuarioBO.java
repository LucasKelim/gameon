package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import gameon.models.Usuario;
import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.UsuarioDTO;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class UsuarioBO {
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO(); 
	
	public Usuario inserir(Usuario usuario) {
		String hash = BCrypt.hashpw(usuario.getSenha().getValor(), BCrypt.gensalt());
		
		usuario.setSenha(new Senha(hash));
		
		if (!existe(usuario)) {
			UsuarioDTO usuarioDTO = toDTO(usuario);
			
			usuarioDTO = usuarioDAO.inserir(usuarioDTO);
			
			return toModel(usuarioDTO);
		}
		
		return null;
	}
	
	public Usuario alterar(Usuario usuario) {
		UsuarioDTO usuarioDTO = toDTO(usuario);
		
		usuarioDTO = usuarioDAO.alterar(usuarioDTO);
		
		return toModel(usuarioDTO);
	}
	
	public boolean excluir(Usuario usuario) {
		UsuarioDTO usuarioDTO = toDTO(usuario);
		
		return usuarioDAO.excluir(usuarioDTO.getId());
	}
	
    public Usuario procurarPorId(int usuarioId){
		UsuarioDTO usuarioDTO = usuarioDAO.procurarPorId(usuarioId);
    	
        return toModel(usuarioDTO);
    }
    
    public Usuario procurarPorEmail(String email){
    	UsuarioDTO usuarioDTO = usuarioDAO.procurarPorEmail(email);
    	
        return toModel(usuarioDTO);
    }
	
	public boolean existe(Usuario usuario) {
		UsuarioDTO usuarioDTO = toDTO(usuario);
		
		return usuarioDAO.existe(usuarioDTO);
	}
	
	public List<Usuario> pesquisarTodos() {
		List<UsuarioDTO> usuariosDTO = usuarioDAO.pesquisarTodos();
		
		return toModelList(usuariosDTO); 
	}
	
	public boolean verificarSenha(String senha, String senhaAtual) {
		return BCrypt.checkpw(senha, senhaAtual);
	}
	
    private Usuario toModel(UsuarioDTO usuarioDTO) {
    	Usuario usuario = new Usuario(
    		usuarioDTO.getId(),
    		usuarioDTO.getNome(),
    		new Email(usuarioDTO.getEmail()),
    		new Senha(usuarioDTO.getSenha()),
    		usuarioDTO.getCriadoEm()
		);
    	
        return usuario;
    }
    
    private List<Usuario> toModelList(List<UsuarioDTO> usuariosDTO) {
    	List<Usuario> usuarios = new ArrayList<Usuario>();
    	
    	for (UsuarioDTO usuarioDTO : usuariosDTO) {
    		usuarios.add(toModel(usuarioDTO));
    	}
    	
    	return usuarios;
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail().getEmail());
        usuarioDTO.setSenha(usuario.getSenha().getValor());
        usuarioDTO.setCriadoEm(usuario.getCriadoEm());
        
        return usuarioDTO;
    }
}