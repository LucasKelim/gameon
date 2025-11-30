package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;

import gameon.models.Endereco;          // MODEL (Vem da tela)
import gameon.models.DAO.EnderecoDAO;   // DAO
import gameon.models.DTO.EnderecoDTO;   // DTO (Vai pro banco)

public class EnderecoBO {

    // CORREÇÃO: Recebe 'Endereco' (Model), e não DTO
    public boolean inserir(Endereco model) {
        
        // Validação básica
        if (model == null || model.getCliente() == null || model.getCliente().getId() == 0) {
            System.out.println("Erro: Endereço inválido ou sem cliente.");
            return false;
        }

        // 1. Converte Model -> DTO para o banco
        EnderecoDTO dto = converterParaDTO(model);

        // 2. Chama o DAO passando o DTO
        if (!existe(dto)) {
            EnderecoDAO dao = new EnderecoDAO();
            // O DAO retorna o objeto salvo, verificamos se não é nulo
            EnderecoDTO salvo = dao.inserir(dto);
            return salvo != null;
        }
        
        return false;
    }
    
    public boolean alterar(Endereco model) {
        EnderecoDTO dto = converterParaDTO(model);
        
        EnderecoDAO dao = new EnderecoDAO();
        EnderecoDTO alterado = dao.alterar(dto);
        return alterado != null;
    }
    
    public boolean excluir(Endereco model) {
        EnderecoDAO dao = new EnderecoDAO();
        // O DAO geralmente pede o ID (int)
        return dao.excluir(model.getId());
    }
    
    // Retorna Model para a tela
    public Endereco procurarPorId(int id){
        EnderecoDAO dao = new EnderecoDAO();
        EnderecoDTO dto = dao.procurarPorId(id); 
        
        return converterParaModel(dto);
    }
    
    public List<Endereco> pesquisarTodos() {
        EnderecoDAO dao = new EnderecoDAO();
        List<EnderecoDTO> listaDTO = dao.pesquisarTodos();
        
        List<Endereco> listaModel = new ArrayList<>();
        if (listaDTO != null) {
            for (EnderecoDTO dto : listaDTO) {
                listaModel.add(converterParaModel(dto));
            }
        }
        return listaModel;
    }
    
    // Método auxiliar para não ficar repetindo código de conversão
    private EnderecoDTO converterParaDTO(Endereco model) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(model.getId());
        dto.setLogradouro(model.getLogradouro());
        dto.setNumero(model.getNumero());
        dto.setBairro(model.getBairro());
        dto.setCidade(model.getCidade());
        dto.setCodigoPostal(model.getCep()); 
        dto.setEstado(model.getEstado());
        // dto.setPais(model.getPais()); // Se tiver país no DTO
        
        // Pega o ID do objeto Cliente e passa para o campo ID do DTO
        if (model.getCliente() != null) {
            dto.setClienteId(model.getCliente().getId());
        }
        
        return dto;
    }

    private Endereco converterParaModel(EnderecoDTO dto) {
        if (dto == null) return null;
        
        Endereco model = new Endereco();
        model.setId(dto.getId());
        model.setLogradouro(dto.getLogradouro());
        model.setNumero(dto.getNumero());
        model.setBairro(dto.getBairro());
        model.setCidade(dto.getCidade());
        model.setCep(dto.getCodigoPostal());
        model.setEstado(dto.getEstado());
        // model.setPais(dto.getPais());
        
        return model;
    }
    
    public boolean existe(EnderecoDTO dto) {
        EnderecoDAO dao = new EnderecoDAO();
        return dao.existe(dto);
    }
}