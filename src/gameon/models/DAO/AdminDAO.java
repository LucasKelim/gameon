package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Admin;
import gameon.models.DTO.Usuario;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import gameon.utils.Conexao;

public class AdminDAO {

    final String NOMEDATABELA = "admin";
    
    public boolean inserir(Admin admin) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + " (id) VALUES (?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, admin.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar(Admin admin) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?;";
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
    
    public boolean excluir(Admin admin) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "DELETE FROM usuario WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, admin.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Admin procurarPorId(Admin admin) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.id, u.nome, u.email, u.senha, u.criadoEm FROM usuario u " +
                         "INNER JOIN admin a ON u.id = a.id WHERE u.id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, admin.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin obj = new Admin();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setEmail(new Email(rs.getString(3)));
                obj.setSenha(new Senha(rs.getString(4)));
                obj.setCriadoEm(rs.getTimestamp(5).toLocalDateTime());
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
    
    public Admin procurarPorEmail(Admin admin) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.id, u.nome, u.email, u.senha, u.criadoEm FROM usuario u " +
                         "INNER JOIN admin a ON u.id = a.id WHERE u.email = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin obj = new Admin();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setEmail(new Email(rs.getString(3)));
                obj.setSenha(new Senha(rs.getString(4)));
                obj.setCriadoEm(rs.getTimestamp(5).toLocalDateTime());
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
    
    public boolean existe(Admin admin) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM admin WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, admin.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                rs.close();
                conn.close();
                return true;
            }
            ps.close();
            rs.close();
            conn.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Admin> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.id, u.nome, u.email, u.senha, u.criadoEm FROM usuario u " +
                         "INNER JOIN admin a ON u.id = a.id;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Admin> listObj = montarLista(rs);
            ps.close();
            rs.close();
            conn.close();
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private List<Admin> montarLista(ResultSet rs) {
        List<Admin> listObj = new ArrayList<Admin>();
        try {
            while (rs.next()) {
                Admin obj = new Admin();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setEmail(new Email(rs.getString(3)));
                obj.setSenha(new Senha(rs.getString(4)));
                obj.setCriadoEm(rs.getTimestamp(5).toLocalDateTime());
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
