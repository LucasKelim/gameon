package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;

import gameon.models.Admin;
import gameon.models.Usuario;
import gameon.models.DAO.AdminDAO;
import gameon.models.DAO.UsuarioDAO;
import gameon.models.DTO.AdminDTO;
import gameon.models.DTO.UsuarioDTO;

public class AdminBO {
	
	private AdminDAO adminDAO = new AdminDAO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public Admin inserir(Admin admin) {
		AdminDTO adminDTO = toAdminDTO(admin);
		
		if (!existe(admin)) {
	        UsuarioBO usuarioBO = new UsuarioBO();
			Usuario usuario = usuarioBO.inserir(admin);
			
			adminDTO.setId(usuario.getId());
	        
			adminDTO = adminDAO.inserir(adminDTO);
			
			return toModel(adminDTO);
		}
		
		return null;
	}
	
	public Admin alterar(Admin admin) {
        UsuarioDTO usuarioDTO = toUsuarioDTO(admin);
        usuarioDAO.alterar(usuarioDTO);
        
        AdminDTO adminDTO = toAdminDTO(admin);		
		adminDAO.alterar(adminDTO);
		
		return admin;
	}
	
	public boolean excluir(Admin admin) {
		UsuarioDTO usuarioDTO = toUsuarioDTO(admin);
		AdminDTO adminDTO = toAdminDTO(admin);
		
		if (adminDAO.excluir(adminDTO.getId())) {
			return usuarioDAO.excluir(usuarioDTO.getId());
		}
		
		return false;
	}
	
	public Admin procurarPorId(int adminId) {
        AdminDTO admin = adminDAO.procurarPorId(adminId);
        if (admin == null) return null;

        return toModel(admin);
    }
    
    public Admin procurarPorEmail(String email) {
        UsuarioDTO usuarioDTO = usuarioDAO.procurarPorEmail(email);
        if (usuarioDTO == null) return null;
        
        Admin admin = procurarPorId(usuarioDTO.getId());
        
        return admin;
    }
	
	public boolean existe(Admin admin) {
		AdminDTO adminDTO = toAdminDTO(admin);
		
		return adminDAO.existe(adminDTO);
	}
	
	public List<Admin> pesquisarTodos() {
		List<AdminDTO> adminsDTO = adminDAO.pesquisarTodos();
		
		return toModelList(adminsDTO);
	}
	
    private Admin toModel(AdminDTO adminDTO) {
    	
    	UsuarioDTO usuarioDTO = usuarioDAO.procurarPorId(adminDTO.getId());
    	
    	if (usuarioDTO == null) return null;
    	
    	Admin admin = new Admin();
    	
    	return admin;
    }
    
    private List<Admin> toModelList(List<AdminDTO> adminsDTO) {
    	List<Admin> admins = new ArrayList<Admin>();
    	
    	for (AdminDTO adminDTO : adminsDTO) {
    		admins.add(toModel(adminDTO));
    	}
    	
    	return admins;
    }
    
    private UsuarioDTO toUsuarioDTO(Admin admin) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        
        usuarioDTO.setId(admin.getId());
        usuarioDTO.setNome(admin.getNome());
        usuarioDTO.setEmail(admin.getEmail().getEmail());
        usuarioDTO.setSenha(admin.getSenha().getValor());
        usuarioDTO.setCriadoEm(admin.getCriadoEm());
        
        return usuarioDTO;
    }

    private AdminDTO toAdminDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        
        adminDTO.setId(admin.getId());
        
        return adminDTO;
    }
	
}
