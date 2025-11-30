package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.AdminDTO;
import gameon.utils.Conexao;

public class AdminDAO {

    final String NOMEDATABELA = "admin";
    
    public AdminDTO inserir(AdminDTO admin) {
    	String sql = "INSERT INTO " + NOMEDATABELA + " (id) VALUES (?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, admin.getId());
            
            int rows = ps.executeUpdate();
            
            if (rows == 0) {
            	return null;
            }
            
            ps.close();
            conn.close();
            
            return procurarPorId(admin.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean alterar(AdminDTO admin) {
//    	String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?;";
//    	
//        try {
//            Connection conn = Conexao.conectar();
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, admin.getNome());
//            ps.setString(2, admin.getEmail());
//            ps.setString(3, admin.getSenha());
//            ps.setInt(4, admin.getId());
//            ps.executeUpdate();
//            ps.close();
//            conn.close();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
    	
    	return false;
    }
    
    public boolean excluir(int adminId) {
    	String sql = "DELETE FROM admin WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, adminId);
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public AdminDTO procurarPorId(int adminId) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, adminId);
            
            ResultSet rs = ps.executeQuery();
            
            AdminDTO admin = null;
            if (rs.next()) {
            	admin = montarAdmin(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
                
            return admin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(AdminDTO admin) {
    	String sql = "SELECT * FROM admin WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, admin.getId());
            
            ResultSet rs = ps.executeQuery();
            
            boolean res = false;
            if (rs.next()) {
                res = true;
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<AdminDTO> pesquisarTodos() {
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<AdminDTO> admins = montarLista(rs);
            
            ps.close();
            rs.close();
            conn.close();
            
            return admins;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private List<AdminDTO> montarLista(ResultSet rs) {
        List<AdminDTO> admins = new ArrayList<AdminDTO>();
        
        try {
        	AdminDTO admin = null;
        	
            while (rs.next()) {
            	admin = montarAdmin(rs);
                admins.add(admin);
            }
            
            return admins;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private AdminDTO montarAdmin(ResultSet rs) {
        try {
            AdminDTO admin = new AdminDTO();
            
            admin.setId(rs.getInt("id"));
            
            return admin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
