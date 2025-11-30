package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.EnderecoDAO;
import gameon.models.DTO.Endereco;

public class EnderecoBO {

    public boolean inserir(Endereco endereco) {
        // Validação simples: endereço precisa de um cliente vinculado
        if (endereco.getCliente() == null || endereco.getCliente().getId() == 0) {
            return false;
        }

        if (!existe(endereco)) {
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            return enderecoDAO.inserir(endereco);
        }
        
        return false;
    }
    
    public boolean alterar(Endereco endereco) {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.alterar(endereco);
    }
    
    public boolean excluir(Endereco endereco) {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.excluir(endereco);
    }
    
    public Endereco procurarPorId(Endereco endereco){
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.procurarPorId(endereco);
    }
    
    // Removi o "procurarPorEmail" pois Endereço não costuma ter email,
    // mas adicionei este que busca todos os endereços de um cliente
    public List<Endereco> procurarPorClienteId(int clienteId){
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.procurarPorClienteId(clienteId);
    }
    
    public boolean existe(Endereco endereco) {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.existe(endereco);
    }
    
    public List<Endereco> pesquisarTodos() {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.pesquisarTodos();
    }
}