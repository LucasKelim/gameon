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
    	String sql = "INSERT INTO " + NOMEDATABELA + " (metodoPagamento, valorTotal, enderecoId, asaasOrdem) VALUES (?, ?, ?, ?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, ordem.getMetodoPagamento());
            ps.setDouble(2, ordem.getValorTotal());
            ps.setInt(3, ordem.getEnderecoId());
            ps.setString(4, ordem.getAsaasOrdem());
            
        	int rows = ps.executeUpdate();
            
            if (rows == 0) {
            	return null;
            }
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                ordem.setId(rs.getInt(1));
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
    
    public OrdemDTO alterar(OrdemDTO ordemDTO) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET status = ?, metodoPagamento = ?, valorTotal = ?, enderecoId = ?, asaasOrdem = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, ordemDTO.getStatus());
            ps.setString(2, ordemDTO.getMetodoPagamento());
            ps.setDouble(3, ordemDTO.getValorTotal());
            ps.setInt(4, ordemDTO.getEnderecoId());
            ps.setString(5, ordemDTO.getAsaasOrdem());
            ps.setInt(6, ordemDTO.getId());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return ordemDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public OrdemDTO alterarStatus(OrdemDTO ordemDTO) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET status = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, ordemDTO.getStatus());
            ps.setInt(6, ordemDTO.getId());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return ordemDTO;
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
    
    public boolean existe(OrdemDTO ordemDTO) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, ordemDTO.getId());
            
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
            OrdemDTO ordemDTO = new OrdemDTO();
          
            Timestamp timestamp = rs.getTimestamp("criadoEm");
         
            ordemDTO.setId(rs.getInt("id"));
            ordemDTO.setStatus(rs.getString("status"));
            ordemDTO.setMetodoPagamento(rs.getString("metodoPagamento"));
            ordemDTO.setValorTotal(rs.getDouble("valorTotal"));
            ordemDTO.setEnderecoId(rs.getInt("enderecoId"));
            ordemDTO.setAsaasOrdem(rs.getString("asaasOrdem"));
            ordemDTO.setCriadoEm(timestamp.toLocalDateTime());
            
            return ordemDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}