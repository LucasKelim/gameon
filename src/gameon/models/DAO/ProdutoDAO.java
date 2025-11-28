package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Produto;
import gameon.utils.Conexao;

public class ProdutoDAO {

    final String NOMEDATABELA = "produto";
    
    public boolean inserir(Produto produto) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + " (nome, descricao, preco, estoque, status, adminId) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getEstoque());
            ps.setBoolean(5, produto.getStatus());
            ps.setInt(6, produto.getAdmin().getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar(Produto produto) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET nome = ?, descricao = ?, preco = ?, estoque = ?, status = ?, adminId = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getEstoque());
            ps.setBoolean(5, produto.getStatus());
            ps.setInt(6, produto.getAdmin().getId());
            ps.setInt(7, produto.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(Produto produto) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, produto.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Produto procurarPorId(Produto produto) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, produto.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Produto obj = new Produto();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDescricao(rs.getString(3));
                obj.setPreco(rs.getDouble(4));
                obj.setEstoque(rs.getInt(5));
                obj.setStatus(rs.getBoolean(6));
                // adminId será tratado separadamente se necessário
                obj.setCriadoEm(rs.getTimestamp(8).toLocalDateTime());
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
    
    public Produto procurarPorNome(Produto produto) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Produto obj = new Produto();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDescricao(rs.getString(3));
                obj.setPreco(rs.getDouble(4));
                obj.setEstoque(rs.getInt(5));
                obj.setStatus(rs.getBoolean(6));
                // adminId será tratado separadamente se necessário
                obj.setCriadoEm(rs.getTimestamp(8).toLocalDateTime());
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
    
    public boolean existe(Produto produto) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, produto.getNome());
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
    
    public List<Produto> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Produto> listObj = montarLista(rs);
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Produto> montarLista(ResultSet rs) {
        List<Produto> listObj = new ArrayList<Produto>();
        try {
            while (rs.next()) {
                Produto obj = new Produto();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDescricao(rs.getString(3));
                obj.setPreco(rs.getDouble(4));
                obj.setEstoque(rs.getInt(5));
                obj.setStatus(rs.getBoolean(6));
                // adminId será tratado separadamente se necessário
                obj.setCriadoEm(rs.getTimestamp(8).toLocalDateTime());
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}