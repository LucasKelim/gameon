package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Produto;
import gameon.utils.Conexao;

public class ProdutoDAO {

    final String NOMEDATABELA = "produto";
    
    public Produto inserir(Produto produto) {
    	String sql = "INSERT INTO " + NOMEDATABELA + " (nome, descricao, preco, estoque, status, adminId) VALUES (?, ?, ?, ?, ?, ?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getEstoque());
            ps.setBoolean(5, produto.getStatus());
            ps.setInt(6, produto.getAdmin().getId());
            
            int rows = ps.executeUpdate();
            
            if (rows == 0) {
            	return null;
            }
            
        	ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                produto.setId(rs.getInt("id"));
            }

            ps.close();
            rs.close();
            conn.close();
            
            return procurarPorId(produto.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Produto alterar(Produto produto) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET nome = ?, descricao = ?, preco = ?, estoque = ?, status = ?, adminId = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
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
            
            return produto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean excluir(int produtoId) {
    	String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, produtoId);
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Produto procurarPorId(int produtoId) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, produtoId);
            
            ResultSet rs = ps.executeQuery();
            
            Produto produto = null;
            if (rs.next()) {	
                produto = montarProduto(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return produto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Produto procurarPorNome(String produtoNome) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, produtoNome);
            
            ResultSet rs = ps.executeQuery();
            
            Produto produto = null;
            if (rs.next()) {
                produto = montarProduto(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return produto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(Produto produto) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, produto.getNome());
            
            ResultSet rs = ps.executeQuery();
            
            boolean res = false;
            if (rs.next()) {
            	return true;                
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
    
    public List<Produto> pesquisarTodos() {
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<Produto> produtos = montarLista(rs);
            
            return produtos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Produto> montarLista(ResultSet rs) {
        List<Produto> produtos = new ArrayList<Produto>();
        
        try {
        	Produto produto = null;
        	
            while (rs.next()) {
            	produto = montarProduto(rs);
            	produtos.add(produto);
            }
            
            return produtos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private Produto montarProduto(ResultSet rs) {
        try {
        	Produto produto = new Produto();
        	
        	Timestamp timestamp = rs.getTimestamp("criadoEm");
            
        	produto.setId(rs.getInt("id"));
        	produto.setNome(rs.getString("nome"));
        	produto.setDescricao(rs.getString("descricao"));
        	produto.setPreco(rs.getDouble("preco"));
        	produto.setEstoque(rs.getInt("estoque"));
        	produto.setStatus(rs.getBoolean("status"));
        	produto.setAdminId(rs.getInt("adminId"));
        	produto.setCriadoEm(timestamp.toLocalDateTime());
            
            return produto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}