package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import gameon.models.DTO.CarrinhoProdutoDTO;
import gameon.utils.Conexao;

public class CarrinhoProdutoDAO {
	
	final String NOMEDATABELA = "carrinho_produto";
    
	public CarrinhoProdutoDTO inserir(CarrinhoProdutoDTO carrinhoProdutoDTO) {
	    String sql = "INSERT INTO " + NOMEDATABELA + " (produtoId, clienteId, quantidade) VALUES (?, ?, ?);";
	    
	    try {
	        Connection conn = Conexao.conectar();
	        PreparedStatement ps = conn.prepareStatement(sql);
	        
	        ps.setInt(1, carrinhoProdutoDTO.getProdutoId());
	        ps.setInt(2, carrinhoProdutoDTO.getClienteId());
	        ps.setInt(3, carrinhoProdutoDTO.getQuantidade());
	        
	        int rows = ps.executeUpdate();
	        
	        ps.close();
	        conn.close();
	        
	        if (rows == 0) {
	            return null;
	        }

	        return procurarPorId(carrinhoProdutoDTO.getProdutoId(), carrinhoProdutoDTO.getClienteId());	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public CarrinhoProdutoDTO aumentarQuantidade(CarrinhoProdutoDTO carrinhoProdutoDTO) {
	    String sql = "UPDATE " + NOMEDATABELA + " SET quantidade = quantidade + 1 WHERE produtoId = ? AND clienteId = ?;";
	    
	    try {
	        Connection conn = Conexao.conectar();
	        PreparedStatement ps = conn.prepareStatement(sql);
	        
	        ps.setInt(1, carrinhoProdutoDTO.getProdutoId());
	        ps.setInt(2, carrinhoProdutoDTO.getClienteId());
	        
	        int rows = ps.executeUpdate();
	        
	        ps.close();
	        conn.close();
	        
	        if (rows == 0) {
	            return null;
	        }

	        return procurarPorId(carrinhoProdutoDTO.getProdutoId(), carrinhoProdutoDTO.getClienteId());	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    public CarrinhoProdutoDTO alterar(CarrinhoProdutoDTO carrinhoProdutoDTO) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET quantidade = ? WHERE produtoId = ? AND clienteId = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, carrinhoProdutoDTO.getQuantidade());
            ps.setInt(2, carrinhoProdutoDTO.getProdutoId());
            ps.setInt(3, carrinhoProdutoDTO.getClienteId());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return carrinhoProdutoDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean excluir(int produtoId, int clienteId) {
        String sql = "DELETE FROM " + NOMEDATABELA + " WHERE produtoId = ? AND clienteId = ?;";
        
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, produtoId);
            ps.setInt(2, clienteId);
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public CarrinhoProdutoDTO procurarPorId(int produtoId, int clienteId) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE produtoId = ? AND clienteId = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, produtoId);
            ps.setInt(2, clienteId);
            
            ResultSet rs = ps.executeQuery();
            
            CarrinhoProdutoDTO carrinhoProdutoDTO = null;
            if (rs.next()) {	
            	carrinhoProdutoDTO = montarProduto(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return carrinhoProdutoDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(CarrinhoProdutoDTO carrinhoProdutoDTO) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE produtoId = ? AND clienteId = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, carrinhoProdutoDTO.getProdutoId());
            ps.setInt(2, carrinhoProdutoDTO.getClienteId());
            
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
    
    public List<CarrinhoProdutoDTO> pesquisarTodos() {
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<CarrinhoProdutoDTO> carrinhoProdutosDTO = montarLista(rs);
            
            return carrinhoProdutosDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<CarrinhoProdutoDTO> montarLista(ResultSet rs) {
        List<CarrinhoProdutoDTO> carrinhoProdutosDTO = new ArrayList<CarrinhoProdutoDTO>();
        
        try {
        	CarrinhoProdutoDTO carrinhoProduto = null;
        	
            while (rs.next()) {
            	carrinhoProduto = montarProduto(rs);
            	carrinhoProdutosDTO.add(carrinhoProduto);
            }
            
            return carrinhoProdutosDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private CarrinhoProdutoDTO montarProduto(ResultSet rs) {
        try {
        	CarrinhoProdutoDTO carrinhoProdutoDTO = new CarrinhoProdutoDTO();
        	
        	Timestamp timestamp = rs.getTimestamp("criadoEm");
            
        	carrinhoProdutoDTO.setProdutoId(rs.getInt("produtoId"));
        	carrinhoProdutoDTO.setClienteId(rs.getInt("clienteId"));
        	carrinhoProdutoDTO.setQuantidade(rs.getInt("quantidade"));
        	carrinhoProdutoDTO.setCriadoEm(timestamp.toLocalDateTime());
            
            return carrinhoProdutoDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
 // No CarrinhoProdutoDAO.java
    public boolean excluirPorCliente(int clienteId) {
        String sql = "DELETE FROM " + NOMEDATABELA + " WHERE clienteId = ?;";
        
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, clienteId);
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
