package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.OrdemProdutoDTO;
import gameon.utils.Conexao;

public class OrdemProdutoDAO {
    
    final String NOMEDATABELA = "ordem_produto";
    
    public OrdemProdutoDTO inserir(OrdemProdutoDTO ordemProduto) {
        String sql = "INSERT INTO " + NOMEDATABELA + " (ordemId, produtoId, quantidade) VALUES (?, ?, ?);";
        
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, ordemProduto.getOrdem()); // ← getOrdem() não getOrdemId()
            ps.setInt(2, ordemProduto.getProduto()); // ← getProduto() não getProdutoId()
            ps.setInt(3, ordemProduto.getQuantidade());
            
            int rows = ps.executeUpdate();
            
            if (rows == 0) {
                return null;
            }
            
            ps.close();
            conn.close();
            
            return ordemProduto;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<OrdemProdutoDTO> listarPorOrdem(int ordemId) {
        String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE ordemId = ?;";
        
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, ordemId);
            
            ResultSet rs = ps.executeQuery();
            
            List<OrdemProdutoDTO> itens = montarLista(rs);
            
            ps.close();
            rs.close();
            conn.close();
            
            return itens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<OrdemProdutoDTO> listarPorProduto(int produtoId) {
        String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE produtoId = ?;";
        
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, produtoId);
            
            ResultSet rs = ps.executeQuery();
            
            List<OrdemProdutoDTO> itens = montarLista(rs);
            
            ps.close();
            rs.close();
            conn.close();
            
            return itens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean excluirPorOrdem(int ordemId) {
        String sql = "DELETE FROM " + NOMEDATABELA + " WHERE ordemId = ?;";
        
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
    
    private List<OrdemProdutoDTO> montarLista(ResultSet rs) {
        List<OrdemProdutoDTO> itens = new ArrayList<>();
        
        try {
            while (rs.next()) {
                OrdemProdutoDTO dto = new OrdemProdutoDTO();
                
                Timestamp timestamp = rs.getTimestamp("criadoEm");
                
                dto.setId(rs.getInt("id"));
                dto.setOrdem(rs.getInt("ordemId")); // ← setOrdem() não setOrdemId()
                dto.setProduto(rs.getInt("produtoId")); // ← setProduto() não setProdutoId()
                dto.setQuantidade(rs.getInt("quantidade"));
                dto.setCriadoEm(timestamp.toLocalDateTime());
                
                itens.add(dto);
            }
            
            return itens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}