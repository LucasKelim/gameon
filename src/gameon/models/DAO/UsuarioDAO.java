package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp; 
import java.util.ArrayList;
import java.util.List;

import gameon.models.DTO.UsuarioDTO;
import gameon.utils.Conexao;

public class UsuarioDAO {
    final String NOMEDATABELA = "usuario";
    
    public UsuarioDTO inserir(UsuarioDTO usuario) {
    	String sql = "INSERT INTO " + NOMEDATABELA + " (nome, email, senha) VALUES (?, ?, ?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha()); 
            
            int rows = ps.executeUpdate();
            
            if (rows == 0) {
            	return null;
            }
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
            	usuario.setId(rs.getInt(1));
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return procurarPorId(usuario.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public UsuarioDTO alterar(UsuarioDTO usuario) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET nome = ?, email = ?, senha = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, usuario.getId()); 
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return procurarPorId(usuario.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean excluir(int usuarioId) { 
    	String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, usuarioId);
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public UsuarioDTO procurarPorId(int usuarioId) { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, usuarioId);
            
            ResultSet rs = ps.executeQuery();
            
            UsuarioDTO usuario = null;
            if (rs.next()) {
            	usuario = montarUsuario(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public UsuarioDTO procurarPorEmail(String email) { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE email = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            
            UsuarioDTO usuario = null;
            if (rs.next()) {
            	usuario = montarUsuario(rs);
            } 
            
            ps.close();
            rs.close();
            conn.close();
            
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(UsuarioDTO usuario) { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ? OR email = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, usuario.getId());
            ps.setString(2, usuario.getEmail());
            
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
    
    public List<UsuarioDTO> pesquisarTodos() { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<UsuarioDTO> usuarios = montarListaUsuario(rs);
            
            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<UsuarioDTO> montarListaUsuario(ResultSet rs) {
        List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
        
        try {
        	UsuarioDTO usuario = null;
        	
            while (rs.next()) {
                 usuario = montarUsuario(rs);
                 usuarios.add(usuario);
            }
            
            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private UsuarioDTO montarUsuario(ResultSet rs) {
        try {
            UsuarioDTO usuario = new UsuarioDTO() {};
            
            Timestamp timestamp = rs.getTimestamp("criadoEm");
            
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setCriadoEm(timestamp.toLocalDateTime());
            
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            
        }
    }
}