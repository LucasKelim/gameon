package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;

import gameon.models.CarrinhoProduto;
import gameon.models.Ordem;
import gameon.models.OrdemProduto;
import gameon.models.Produto;
import gameon.models.DAO.OrdemProdutoDAO;
import gameon.models.DTO.OrdemProdutoDTO;

public class OrdemProdutoBO {
    
    private OrdemProdutoDAO ordemProdutoDAO = new OrdemProdutoDAO();
    private OrdemBO ordemBO = new OrdemBO();
    private ProdutoBO produtoBO = new ProdutoBO();
    
    public OrdemProduto inserir(OrdemProduto ordemProduto) {
        OrdemProdutoDTO dto = toDTO(ordemProduto);
        
        dto = ordemProdutoDAO.inserir(dto);
        
        return toModel(dto);
    }
    
    public boolean inserirItensDaOrdem(int ordemId, List<CarrinhoProduto> itensCarrinho) {
        try {
            Ordem ordem = ordemBO.procurarPorId(ordemId);
            if (ordem == null) {
                System.out.println("Erro: Ordem não encontrada ID: " + ordemId);
                return false;
            }
            
            for (CarrinhoProduto item : itensCarrinho) {
                OrdemProduto ordemProduto = new OrdemProduto();
                ordemProduto.setOrdem(ordem);
                ordemProduto.setProduto(item.getProduto());
                ordemProduto.setQuantidade(item.getQuantidade());
                
                OrdemProduto resultado = inserir(ordemProduto);
                if (resultado == null) {
                    System.out.println("Aviso: Não foi possível salvar item: " + item.getProduto().getNome());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<OrdemProduto> listarPorOrdem(int ordemId) {
        List<OrdemProdutoDTO> dtos = ordemProdutoDAO.listarPorOrdem(ordemId);
        return toModelList(dtos);
    }
    
    private OrdemProduto toModel(OrdemProdutoDTO dto) {
        if (dto == null) return null;
        
        OrdemProduto ordemProduto = new OrdemProduto();
        
        // Busca ordem
        Ordem ordem = ordemBO.procurarPorId(dto.getOrdem()); // ← getOrdem() não getOrdemId()
        ordemProduto.setOrdem(ordem);
        
        // Busca produto
        Produto produto = produtoBO.procurarPorId(dto.getProduto()); // ← getProduto() não getProdutoId()
        ordemProduto.setProduto(produto);
        
        ordemProduto.setQuantidade(dto.getQuantidade());
        ordemProduto.setCriadoEm(dto.getCriadoEm());
        
        return ordemProduto;
    }
    
    private List<OrdemProduto> toModelList(List<OrdemProdutoDTO> dtos) {
        List<OrdemProduto> itens = new ArrayList<>();
        if (dtos == null) return itens;
        
        for (OrdemProdutoDTO dto : dtos) {
            itens.add(toModel(dto));
        }
        return itens;
    }
    
    private OrdemProdutoDTO toDTO(OrdemProduto ordemProduto) {
        OrdemProdutoDTO dto = new OrdemProdutoDTO();
        
        dto.setOrdem(ordemProduto.getOrdem().getId()); // ← setOrdem() não setOrdemId()
        dto.setProduto(ordemProduto.getProduto().getId()); // ← setProduto() não setProdutoId()
        dto.setQuantidade(ordemProduto.getQuantidade());
        dto.setCriadoEm(ordemProduto.getCriadoEm());
        
        return dto;
    }
}