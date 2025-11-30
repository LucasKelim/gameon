package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.OrdemDTO;
import gameon.utils.Conexao;

public class OrdemDAO {

    final String NOMEDATABELA = "ordem";
    
    public OrdemDTO inserir(OrdemDTO ordem) {
    	String sql = "INSERT INTO " + NOMEDATABELA + " (status, metodoPagamento, valorTotal, enderecoId, asaasOrdem) VALUES (?, ?, ?, ?, ?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, ordem.getStatus().toString());
            ps.setString(2, ordem.getMetodoPagamento());
            ps.setDouble(3, ordem.getValorTotal());
            ps.setInt(4, ordem.getEnderecoId());
            ps.setString(5, ordem.getAsaasOrdem());
            
        	int rows = ps.executeUpdate();
            
            if (rows == 0) {
            	return null;
            }
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                ordem.setId(rs.getInt("id"));
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return procurarPorId(ordem.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public OrdemDTO alterar(OrdemDTO ordem) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET status = ?, metodoPagamento = ?, valorTotal = ?, enderecoId = ?, asaasOrdem = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, ordem.getStatus());
            ps.setString(2, ordem.getMetodoPagamento());
            ps.setDouble(3, ordem.getValorTotal());
            ps.setInt(4, ordem.getEnderecoId());
            ps.setString(5, ordem.getAsaasOrdem());
            ps.setInt(6, ordem.getId());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return ordem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean excluir(int ordemId) {
    	String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, ordemId);
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public OrdemDTO procurarPorId(int ordemId) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, ordemId);
            
            ResultSet rs = ps.executeQuery();
            
            OrdemDTO ordem = null;
            if (rs.next()) {
            	ordem = montarOrdem(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return ordem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<OrdemDTO> procurarPorClienteId(int clienteId) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT o.* FROM " + NOMEDATABELA + " o " +
                         "INNER JOIN endereco e ON o.enderecoId = e.id " +
                         "WHERE e.clienteId = ? ORDER BY o.criadoEm DESC;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();
            List<OrdemDTO> listObj = montarLista(rs);
            ps.close();
            rs.close();
            conn.close();
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<OrdemDTO> procurarPorStatus(String status) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE status = ? ORDER BY criadoEm DESC;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, status);
            
            ResultSet rs = ps.executeQuery();
            
            List<OrdemDTO> ordens = montarLista(rs);
            
            ps.close();
            rs.close();
            conn.close();
            
            return ordens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(OrdemDTO ordem) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, ordem.getId());
            
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
    
    public List<OrdemDTO> pesquisarTodos() {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " ORDER BY criadoEm DESC;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<OrdemDTO> ordens = montarLista(rs);
            
            ps.close();
            rs.close();
            conn.close();
            
            return ordens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private List<OrdemDTO> montarLista(ResultSet rs) {
        List<OrdemDTO> ordens = new ArrayList<OrdemDTO>();
        
        try {
        	OrdemDTO ordem = null;
        	
            while (rs.next()) {
            	ordem = montarOrdem(rs);
                ordens.add(ordem);
            }
            
            return ordens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private OrdemDTO montarOrdem(ResultSet rs) {
        try {
            OrdemDTO ordem = new OrdemDTO();
          
            Timestamp timestamp = rs.getTimestamp("criadoEm");
         
            ordem.setId(rs.getInt("id"));
            ordem.setStatus(rs.getString("status"));
            ordem.setMetodoPagamento(rs.getString("metodoPagamento"));
            ordem.setValorTotal(rs.getDouble("valorTotal"));
            ordem.setEnderecoId(rs.getInt("enderecoId"));
            ordem.setAsaasOrdem(rs.getString("asaasOrdem"));
            ordem.setCriadoEm(timestamp.toLocalDateTime());
            
            return ordem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}