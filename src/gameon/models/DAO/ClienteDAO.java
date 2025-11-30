package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Cliente;
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
            
            return procurarPorId(cliente.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Cliente alterar(Cliente cliente) {
    	String sql = "UPDATE " + NOMEDATABELA + " SET cpf = ?, telefone = ? WHERE id = ?;";
    	
        try {
            Connection conn = Conexao.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getTelefone());
            ps.setInt(3, cliente.getId());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return procurarPorId(cliente.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean excluir(int clienteId) {
    	String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
    	
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
    
    public Cliente procurarPorId(int clienteId) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ?";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, clienteId);
            
            ResultSet rs = ps.executeQuery();
            
            Cliente cliente = null;
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
    
    public Cliente procurarPorCpf(String clienteCpf) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE cpf = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, clienteCpf);
            
            ResultSet rs = ps.executeQuery();
            
            Cliente cliente = null;
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
    
    public Cliente procurarPorAsaasId(String clienteAsaasId) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE asaasCliente = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, clienteAsaasId);
            
            ResultSet rs = ps.executeQuery();
            
            Cliente cliente = null;
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
    
    public boolean existe(Cliente cliente) {
    	String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE id = ? OR cpf = ? OR asaasCliente = ? OR telefone = ?;";
    	
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getAsaasCliente());
            ps.setString(4, cliente.getTelefone());
            
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
    	String sql = "SELECT * FROM " + NOMEDATABELA + ";";
    	
        try {
            Connection conn = Conexao.conectar();
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
        List<Cliente> clientes = new ArrayList<Cliente>();
        
        try {
        	Cliente cliente = null;
        	
            while (rs.next()) {
            	cliente = montarCliente(rs);
            	clientes.add(cliente);
            }
            
            return clientes;
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