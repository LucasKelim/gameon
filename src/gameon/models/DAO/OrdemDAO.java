package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Ordem;
import gameon.models.DTO.Usuario;
import gameon.models.enums.OrdemStatus;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import gameon.models.DTO.Endereco;
import gameon.utils.Conexao;

public class OrdemDAO {

    final String NOMEDATABELA = "ordem";
    
    public Ordem inserir(Ordem ordem) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + " (status, metodoPagamento, valorTotal, enderecoId, asaasOrdem) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, ordem.getStatus().toString());
            ps.setString(2, ordem.getMetodoPagamento().descricao());
            ps.setDouble(3, ordem.getValorTotal());
            ps.setInt(4, ordem.getEndereco().getId());
            ps.setString(5, ordem.getAsaasOrdem());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ordem.setId(rs.getInt(1));
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
    
    public boolean alterar(Ordem ordem) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET status = ?, metodoPagamento = ?, valorTotal = ?, enderecoId = ?, asaasOrdem = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ordem.getStatus().toString());
            ps.setString(2, ordem.getMetodoPagamento().descricao());
            ps.setDouble(3, ordem.getValorTotal());
            ps.setInt(4, ordem.getEndereco().getId());
            ps.setString(5, ordem.getAsaasOrdem());
            ps.setInt(6, ordem.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(Ordem ordem) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ordem.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Ordem procurarPorId(Ordem ordem) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ordem.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	 ps.close();
                 rs.close();
                 conn.close();
                return montarOrdem(rs);
            
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
    
    public List<Ordem> procurarPorClienteId(int clienteId) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT o.* FROM " + NOMEDATABELA + " o " +
                         "INNER JOIN endereco e ON o.enderecoId = e.id " +
                         "WHERE e.clienteId = ? ORDER BY o.criadoEm DESC;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();
            List<Ordem> listObj = montarLista(rs);
            ps.close();
            rs.close();
            conn.close();
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Ordem> procurarPorStatus(String status) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE status = ? ORDER BY criadoEm DESC;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            List<Ordem> listObj = montarLista(rs);
            ps.close();
            rs.close();
            conn.close();
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(Ordem ordem) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ordem.getId());
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
    
    public List<Ordem> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " ORDER BY criadoEm DESC;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Ordem> listObj = montarLista(rs);
            ps.close();
            rs.close();
            conn.close();
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private Ordem montarOrdem(ResultSet rs) {
        try {
            Ordem ordem = new Ordem();
          
            Timestamp timestamp = rs.getTimestamp("criadoEm");
         
//            ordem.setId(rs.getInt("id");
//            ordem.setStatus(OrdemStatus.valueOf(rs.getString("status")));
//            ordem.setMetodoPagamento(rs.getString(""));
//            ordem.setValorTotal(rs.getDouble("valorTotal"));
//            ordem.setAsaasOrdem(rs.getString("asaasOrdem"));
//            ordem.setCriadoEm(rs.getTimestamp(7).toLocalDateTime());
            
            return ordem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            
        }
        
    }
    
    private List<Ordem> montarLista(ResultSet rs) {
        List<Ordem> listObj = new ArrayList<Ordem>();
        try {
            while (rs.next()) {
                Ordem obj = new Ordem();
                obj.setId(rs.getInt("id");
                obj.setStatus(OrdemStatus.valueOf(rs.getString(2)));
                obj.setMetodoPagamento(rs.getString(3));
                obj.setValorTotal(rs.getDouble(4));
                obj.setAsaasOrdem(rs.getString(6));
                obj.setCriadoEm(rs.getTimestamp(7).toLocalDateTime());
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}