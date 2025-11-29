package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Cliente;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import gameon.utils.Conexao;

public class ClienteDAO {
    final String NOMEDATABELA = "cliente";
    
    public Cliente inserir(Cliente cliente) {
    	String sql = "INSERT INTO " + NOMEDATABELA + " (id, cpf, telefone, asaasCliente) VALUES (?, ?, ?, ?);";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getAsaasCliente());
            
            int rows = ps.executeUpdate();
            
            if (rows == 0) {
            	return null;
            }
            
            ps.close();
            conn.close();
            
            return procurarPorId(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Cliente alterar(Cliente cliente) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET cpf = ?, telefone = ?, asaasCliente = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getAsaasCliente());
            ps.setInt(4, cliente.getId());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return procurarPorId(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean excluir(Cliente cliente) {
        try {
            Connection conn = Conexao.conectar();
            
            String sqlCarrinho = "DELETE FROM carrinho_produto WHERE cliente = ?;";
            PreparedStatement psCarrinho = conn.prepareStatement(sqlCarrinho);
            psCarrinho.setInt(1, cliente.getId());
            psCarrinho.executeUpdate();
            psCarrinho.close();
            
            String sqlEndereco = "DELETE FROM endereco WHERE cliente = ?;";
            PreparedStatement psEndereco = conn.prepareStatement(sqlEndereco);
            psEndereco.setInt(1, cliente.getId());
            psEndereco.executeUpdate();
            psEndereco.close();
            
            String sqlCliente = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement psCliente = conn.prepareStatement(sqlCliente);
            psCliente.setInt(1, cliente.getId());
            psCliente.executeUpdate();
            psCliente.close();
            
            String sqlUsuario = "DELETE FROM usuario WHERE id = ?;";
            PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
            psUsuario.setInt(1, cliente.getId());
            psUsuario.executeUpdate();
            psUsuario.close();
            
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Cliente procurarPorId(Cliente cliente) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, cliente.getId());
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
            	cliente = montarCliente(rs);
            }
            
            ps.close();
            rs.close();
            conn.close();
            
            return cliente;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Cliente procurarPorCpf(Cliente cliente) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.*, c.cpf, c.telefone, c.asaasCliente " +
                        "FROM usuario u " +
                        "INNER JOIN cliente c ON u.id = c.id " +
                        "WHERE c.cpf = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cliente.getCpf());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Cliente obj = montarCliente(rs);
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
    
    public Cliente procurarPorEmail(Cliente cliente) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.*, c.cpf, c.telefone, c.asaasCliente " +
                        "FROM usuario u " +
                        "INNER JOIN cliente c ON u.id = c.id " +
                        "WHERE u.email = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cliente.getEmail());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Cliente obj = montarCliente(rs);
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
    
    public Cliente procurarPorAsaasId(Cliente cliente) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.*, c.cpf, c.telefone, c.asaasCliente " +
                        "FROM usuario u " +
                        "INNER JOIN cliente c ON u.id = c.id " +
                        "WHERE c.asaasCliente = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cliente.getAsaasCliente());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Cliente obj = montarCliente(rs);
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
    
    public boolean existe(Cliente cliente) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE cpf = ? OR asaasCliente = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getAsaasCliente());
            
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
    
    public List<Cliente> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.*, c.cpf, c.telefone, c.asaasCliente " +
                        "FROM usuario u " +
                        "INNER JOIN cliente c ON u.id = c.id " +
                        "ORDER BY u.criadoEm DESC;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Cliente> listObj = montarLista(rs);
            ps.close();
            rs.close();
            conn.close();
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Cliente> montarLista(ResultSet rs) {
        List<Cliente> listObj = new ArrayList<Cliente>();
        try {
            while (rs.next()) {
                Cliente obj = montarCliente(rs);
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private Cliente montarCliente(ResultSet rs) {
        try {
            Cliente cliente = new Cliente();
            
            cliente.setId(rs.getInt("id"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setAsaasCliente(rs.getString("asaasCliente"));
            
            return cliente;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}