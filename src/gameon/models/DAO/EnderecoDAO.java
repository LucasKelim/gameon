package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Endereco;
import gameon.utils.Conexao;

public class EnderecoDAO {

    final String NOMEDATABELA = "endereco";
    
    public boolean inserir(Endereco endereco) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + " (logradouro, numero, bairro, cidade, cep, estado, clienteId) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, endereco.getLogradouro());
            ps.setInt(2, endereco.getNumero());
            ps.setString(3, endereco.getBairro());
            ps.setString(4, endereco.getCidade());
            ps.setString(5, endereco.getCodigoPostal());
            ps.setString(6, endereco.getEstado());
            ps.setInt(7, endereco.getCliente().getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar(Endereco endereco) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, cep = ?, estado = ?, clienteId = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, endereco.getLogradouro());
            ps.setInt(2, endereco.getNumero());
            ps.setString(3, endereco.getBairro());
            ps.setString(4, endereco.getCidade());
            ps.setString(5, endereco.getCodigoPostal());
            ps.setString(6, endereco.getEstado());
            ps.setInt(7, endereco.getCliente().getId());
            ps.setInt(8, endereco.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(Endereco endereco) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, endereco.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Endereco procurarPorId(Endereco endereco) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, endereco.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Endereco obj = new Endereco();
                obj.setId(rs.getInt(1));
                obj.setLogradouro(rs.getString(2));
                obj.setNumero(rs.getInt(3));
//                obj.setBairro(rs.getString(4));
//                obj.setCidade(rs.getString(5));
//                obj.getCodigoPostal(rs.getString(6));
                obj.setEstado(rs.getString(7));
                // clienteId será carregado separadamente se necessário
                obj.setCriadoEm(rs.getTimestamp(9).toLocalDateTime());
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
    
    public List<Endereco> procurarPorClienteId(int clienteId) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE clienteId = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();
            List<Endereco> listObj = montarLista(rs);
            ps.close();
            rs.close();
            conn.close();
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean existe(Endereco endereco) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, endereco.getId());
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
    
    public List<Endereco> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Endereco> listObj = montarLista(rs);
            ps.close();
            rs.close();
            conn.close();
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private List<Endereco> montarLista(ResultSet rs) {
        List<Endereco> listObj = new ArrayList<Endereco>();
        try {
            while (rs.next()) {
                Endereco obj = new Endereco();
                obj.setId(rs.getInt(1));
                obj.setLogradouro(rs.getString(2));
                obj.setNumero(rs.getInt(3));
                obj.setBairro(rs.getString(4));
                obj.setCidade(rs.getString(5));
                obj.setCodigoPostal(rs.getString(6));
                obj.setEstado(rs.getString(7));
                obj.setCriadoEm(rs.getTimestamp(9).toLocalDateTime());
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
