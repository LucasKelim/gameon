package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Endereco;
import gameon.utils.Conexao;

public class EnderecoDAO {

    final String NOMEDATABELA = "endereco";
    
    public Endereco inserir(Endereco endereco) {
    	String sql = "INSERT INTO " + NOMEDATABELA + " (logradouro, numero, bairro, cidade, cep, estado, clienteId) VALUES (?, ?, ?, ?, ?, ?, ?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, endereco.getLogradouro());
            ps.setInt(2, endereco.getNumero());
            ps.setString(3, endereco.getBairro());
            ps.setString(4, endereco.getCidade());
            ps.setString(5, endereco.getCodigoPostal());
            ps.setString(6, endereco.getEstado());
            ps.setInt(7, endereco.getCliente().getId());
            
            int rows = ps.executeUpdate();
            
            if (rows == 0) {
            	return null;
            }
            
        	ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                endereco.setId(rs.getInt("id"));
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return procurarPorId(endereco.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Endereco alterar(Endereco endereco) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, cep = ?, estado = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, endereco.getLogradouro());
            ps.setInt(2, endereco.getNumero());
            ps.setString(3, endereco.getBairro());
            ps.setString(4, endereco.getCidade());
            ps.setString(5, endereco.getCodigoPostal());
            ps.setString(6, endereco.getEstado());
            ps.setInt(8, endereco.getId());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return endereco;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean excluir(int enderecoId) {
    	String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, enderecoId);
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Endereco procurarPorId(int enderecoId) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, enderecoId);
            
            ResultSet rs = ps.executeQuery();
            
            Endereco endereco = null;
            if (rs.next()) {
            	endereco = montarEndereco(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return endereco;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(Endereco endereco) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, endereco.getId());
            
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
    
    public List<Endereco> pesquisarTodos() {
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            List<Endereco> enderecos = montarLista(rs);
            
            ps.close();
            rs.close();
            conn.close();
            
            return enderecos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private List<Endereco> montarLista(ResultSet rs) {
        List<Endereco> enderecos = new ArrayList<Endereco>();
        try {
        	Endereco endereco = null;

            while (rs.next()) {
            	endereco = montarEndereco(rs);
                enderecos.add(endereco);
            }
            
            return enderecos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private Endereco montarEndereco(ResultSet rs) {
        try {
            Endereco endereco = new Endereco();
            
            Timestamp timestamp = rs.getTimestamp("criadoEm");
            
            endereco.setId(rs.getInt("id"));
            endereco.setLogradouro(rs.getString("longradouro"));
            endereco.setNumero(rs.getInt("numero"));
            endereco.setBairro(rs.getString("bairro"));
            endereco.setCodigoPostal(rs.getString("cep"));
            endereco.setCidade(rs.getString("cidade"));
            endereco.setEstado(rs.getString("estado"));
            endereco.setPais(rs.getString("pais"));
            endereco.setCriadoEm(timestamp.toLocalDateTime());
            
            return endereco;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
