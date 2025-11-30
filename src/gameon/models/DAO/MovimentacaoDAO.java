package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import gameon.models.DTO.MovimentacaoDTO;
import gameon.utils.Conexao;

public class MovimentacaoDAO {
	final String NOMEDATABELA = "movimentacao";
    
    public MovimentacaoDTO inserir(MovimentacaoDTO movimentacao) {
    	String sql = "INSERT INTO " + NOMEDATABELA + " (tipo, quantidade, produtoId) VALUES (?, ?, ?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, movimentacao.getMovimentacao());
            ps.setInt(2, movimentacao.getQuantidade());
            ps.setInt(3, movimentacao.getProdutoId());
            
           int rows = ps.executeUpdate();
            
            if (rows == 0) {
            	return null;
            }
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
            	movimentacao.setId(rs.getInt(1));
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return procurarPorId(movimentacao.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public MovimentacaoDTO procurarPorId(int movimentacaoId) { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, movimentacaoId);
            
            ResultSet rs = ps.executeQuery();
            
            MovimentacaoDTO movimentacao = null;
            if (rs.next()) {
            	movimentacao = montarMovimentacao(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return movimentacao;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(MovimentacaoDTO movimentacao) { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, movimentacao.getId());
            
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
    
    public List<MovimentacaoDTO> pesquisarTodos() { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<MovimentacaoDTO> movimentacoes = montarListaMovimentacao(rs);
            
            return movimentacoes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<MovimentacaoDTO> montarListaMovimentacao(ResultSet rs) {
        List<MovimentacaoDTO> movimentacoes = new ArrayList<MovimentacaoDTO>();
        
        try {
        	MovimentacaoDTO movimentacao = null;
        	
            while (rs.next()) {
                 movimentacao = montarMovimentacao(rs);
                 movimentacoes.add(movimentacao);
            }
            
            return movimentacoes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private MovimentacaoDTO montarMovimentacao(ResultSet rs) {
        try {
            MovimentacaoDTO movimentacao = new MovimentacaoDTO();
            
            Timestamp timestamp = rs.getTimestamp("criadoEm");
            
            movimentacao.setId(rs.getInt("id"));
            movimentacao.setMovimentacao(rs.getString("tipo"));
            movimentacao.setQuantidade(rs.getInt("quantidade"));
            movimentacao.setProdutoId(rs.getInt("produtoId"));
            movimentacao.setCriadoEm(timestamp.toLocalDateTime());
            
            return movimentacao;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
