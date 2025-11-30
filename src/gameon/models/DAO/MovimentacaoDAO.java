package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import gameon.models.DTO.Movimentacao;
import gameon.utils.Conexao;

public class MovimentacaoDAO {
	final String NOMEDATABELA = "movimentacao";
    
    public Movimentacao inserir(Movimentacao movimentacao) {
    	String sql = "INSERT INTO " + NOMEDATABELA + " (tipo, quantidade, produtoId) VALUES (?, ?, ?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, movimentacao.getMovimentacao());
            ps.setInt(2, movimentacao.getQuantidade());
            ps.setInt(3, movimentacao.getProduto().getId());
            
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
    
    public Movimentacao procurarPorId(int movimentacaoId) { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, movimentacaoId);
            
            ResultSet rs = ps.executeQuery();
            
            Movimentacao movimentacao = null;
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
    
    public boolean existe(Movimentacao movimentacao) { 
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
    
    public List<Movimentacao> pesquisarTodos() { 
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<Movimentacao> movimentacoes = montarListaMovimentacao(rs);
            
            return movimentacoes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Movimentacao> montarListaMovimentacao(ResultSet rs) {
        List<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();
        
        try {
        	Movimentacao movimentacao = null;
        	
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
    
    private Movimentacao montarMovimentacao(ResultSet rs) {
        try {
            Movimentacao movimentacao = new Movimentacao();
            
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
