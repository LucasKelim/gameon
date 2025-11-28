package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp; 
import java.util.ArrayList;
import java.util.List;

import gameon.models.DTO.Cliente;
import gameon.models.DTO.Usuario;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import gameon.utils.Conexao;

public class UsuarioDAO {
    final String NOMEDATABELA = "usuario";
    
    public int inserir(Usuario usuario) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + " (nome, email, senha) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha()); 
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            int usuarioId = 0;
            if (rs.next()) {
            	usuarioId = rs.getInt(1);
            }
            
            if (usuarioId == 0) {
            	return 0;
            }
            
            ps.close();
            conn.close();
            
            return usuarioId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public boolean alterar(Usuario usuario) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET nome = ?, email = ?, senha = ? WHERE id = ?;"; // Corrigido campos
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, usuario.getId()); 
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(Usuario usuario) { 
        try {
            Connection conn = Conexao.conectar();
            String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Usuario procurarPorId(Usuario usuario) { 
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getId()); 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario obj = montarUsuario(rs); 
                ps.close();
                rs.close();
                conn.close();
                return obj;
            } else {
                ps.close();
                rs.close();
                conn.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Usuario procurarPorEmail(Usuario usuario) { 
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE email = ?;"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario obj = montarUsuario(rs); 
                ps.close();
                rs.close();
                conn.close();
                return obj;
            } else {
                ps.close();
                rs.close();
                conn.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(Usuario usuario) { 
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE email = ?;"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                rs.close();
                conn.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    public List<Usuario> pesquisarTodos() { 
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Usuario> listObj = montarListaUsuario(rs); 
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Usuario> montarListaUsuario(ResultSet rs) {
        List<Usuario> listObj = new ArrayList<Usuario>();
        try {
            while (rs.next()) {
                // Usuario obj = montarUsuario(rs);
                // listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private Usuario montarUsuario(ResultSet rs) {
        try {
        	// Rever isso depois
            Usuario usuario = new Cliente();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            
            // Criar objeto Email
            Email email = new Email(rs.getString("email"));
            Senha senha = new Senha(rs.getString("senha"));
            usuario.setEmail(email);
            usuario.setSenha(senha);
            
            Timestamp timestamp = rs.getTimestamp("criadoEm");
            if (timestamp != null) {
                usuario.setCriadoEm(timestamp.toLocalDateTime());
                
            }
            
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            
        }
        
    }
    
}