package gameon.models.BO;

import java.util.List;

import gameon.models.DAO.EnderecoDAO;
import gameon.models.DTO.EnderecoDTO;

public class EnderecoBO {

    public EnderecoDTO inserir(EnderecoDTO endereco) {
        if (endereco.getClienteId() == 0) {
            return null;
        }

        if (!existe(endereco)) {
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            return enderecoDAO.inserir(endereco);
        }
        
        return null;
    }
    
    public EnderecoDTO alterar(EnderecoDTO endereco) {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.alterar(endereco);
    }
    
    public boolean excluir(EnderecoDTO endereco) {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.excluir(endereco.getId());
    }
    
    public EnderecoDTO procurarPorId(EnderecoDTO endereco){
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.procurarPorId(endereco.getId());
    }
    
    public List<EnderecoDTO> procurarPorCliente(EnderecoDTO endereco){
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.procurarPorClienteId(endereco.getClienteId());
    }
    
    public boolean existe(EnderecoDTO endereco) {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.existe(endereco);
    }
    
    public List<EnderecoDTO> pesquisarTodos() {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        return enderecoDAO.pesquisarTodos();
    }
}