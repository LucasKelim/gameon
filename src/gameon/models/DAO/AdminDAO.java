package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Admin;
import gameon.utils.Conexao;

public class AdminDAO {

    final String NOMEDATABELA = "admin";
    
    public Admin inserir(Admin admin) {
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
    
    public boolean alterar(Admin admin) {
    	String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getNome());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getSenha());
            ps.setInt(4, admin.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
    
    public Admin procurarPorId(int adminId) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, adminId);
            
            ResultSet rs = ps.executeQuery();
            
            Admin admin = null;
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
    
    public boolean existe(Admin admin) {
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
    
    public List<Admin> pesquisarTodos() {
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<Admin> admins = montarLista(rs);
            
            ps.close();
            rs.close();
            conn.close();
            
            return admins;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private List<Admin> montarLista(ResultSet rs) {
        List<Admin> admins = new ArrayList<Admin>();
        
        try {
        	Admin admin = null;
        	
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
    
    private Admin montarAdmin(ResultSet rs) {
        try {
            Admin admin = new Admin();
            
            admin.setId(rs.getInt("id"));
            
            return admin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
