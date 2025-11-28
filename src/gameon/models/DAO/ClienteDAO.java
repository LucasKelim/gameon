package gameon.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import gameon.models.DTO.Cliente;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import gameon.utils.Conexao;

public class ClienteDAO {
    final String NOMEDATABELA = "cliente";
    
    public boolean inserir(Cliente cliente) {
        try {
            Connection conn = Conexao.conectar();
            
            // Primeiro insere na tabela usuario (dados da classe pai)
            String sqlUsuario = "INSERT INTO usuario (nome, email, senha, criadoEm) VALUES (?, ?, ?, ?);";
            PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, cliente.getNome());
            psUsuario.setString(2, cliente.getEmail().toString());
            psUsuario.setString(3, cliente.getSenha());
            
            if (cliente.getCriadoEm() != null) {
                psUsuario.setTimestamp(4, Timestamp.valueOf(cliente.getCriadoEm()));
            } else {
                psUsuario.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }
            
            psUsuario.executeUpdate();
            
            // Pega o ID gerado para o usuário
            ResultSet rs = psUsuario.getGeneratedKeys();
            int usuarioId = 0;
            if (rs.next()) {
                usuarioId = rs.getInt(1);
            }
            rs.close();
            psUsuario.close();
            
            // Insere na tabela cliente
            String sqlCliente = "INSERT INTO " + NOMEDATABELA + " (id, cpf, telefone, asaasCliente) VALUES (?, ?, ?, ?);";
            PreparedStatement psCliente = conn.prepareStatement(sqlCliente);
            psCliente.setInt(1, usuarioId);
            psCliente.setString(2, cliente.getCpf());
            psCliente.setString(3, cliente.getTelefone());
            psCliente.setString(4, cliente.getAsaasCliente());
            psCliente.executeUpdate();
            
            psCliente.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar(Cliente cliente) {
        try {
            Connection conn = Conexao.conectar();
            
            // Atualiza a tabela usuario
            String sqlUsuario = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?;";
            PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
            psUsuario.setString(1, cliente.getNome());
            psUsuario.setString(2, cliente.getEmail().toString());
            psUsuario.setString(3, cliente.getSenha());
            psUsuario.setInt(4, cliente.getId());
            psUsuario.executeUpdate();
            psUsuario.close();
            
            // Atualiza a tabela cliente
            String sqlCliente = "UPDATE " + NOMEDATABELA + " SET cpf = ?, telefone = ?, asaasCliente = ? WHERE id = ?;";
            PreparedStatement psCliente = conn.prepareStatement(sqlCliente);
            psCliente.setString(1, cliente.getCpf());
            psCliente.setString(2, cliente.getTelefone());
            psCliente.setString(3, cliente.getAsaasCliente());
            psCliente.setInt(4, cliente.getId());
            psCliente.executeUpdate();
            psCliente.close();
            
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir(Cliente cliente) {
        try {
            Connection conn = Conexao.conectar();
            
            // Primeiro exclui registros relacionados em outras tabelas
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
            
            // Depois exclui da tabela cliente
            String sqlCliente = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?;";
            PreparedStatement psCliente = conn.prepareStatement(sqlCliente);
            psCliente.setInt(1, cliente.getId());
            psCliente.executeUpdate();
            psCliente.close();
            
            // Finalmente exclui da tabela usuario
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
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.*, c.cpf, c.telefone, c.asaasCliente " +
                        "FROM usuario u " +
                        "INNER JOIN cliente c ON u.id = c.id " +
                        "WHERE u.id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
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
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE cpf = ? OR asaasCliente = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getAsaasCliente());
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
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
    
    // Método auxiliar para montar um Cliente a partir do ResultSet
    private Cliente montarCliente(ResultSet rs) {
        try {
            Cliente cliente = new Cliente();
            
            // Dados da classe Usuario (pai)
            cliente.setId(rs.getInt("id"));
            cliente.setNome(rs.getString("nome"));
            
            Email email = new Email(rs.getString("email"));
            cliente.setEmail(email);
            
            Senha senha = new Senha(rs.getString("senha"));
            cliente.setSenha(senha);
            
            Timestamp timestamp = rs.getTimestamp("criadoEm");
            if (timestamp != null) {
                cliente.setCriadoEm(timestamp.toLocalDateTime());
            }
            
            // Dados específicos da classe Cliente (filho)
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