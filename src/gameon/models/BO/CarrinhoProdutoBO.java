package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gameon.models.CarrinhoProduto;
import gameon.models.Cliente;
import gameon.models.Produto;
import gameon.models.Usuario;
import gameon.models.DAO.CarrinhoProdutoDAO;
import gameon.models.DTO.CarrinhoProdutoDTO;
import gameon.utils.SessaoUsuario;

public class CarrinhoProdutoBO {
    
    private CarrinhoProdutoDAO carrinhoProdutoDAO = new CarrinhoProdutoDAO();
    private ProdutoBO produtoBO = new ProdutoBO();
    private ClienteBO clienteBO = new ClienteBO();
    
    public CarrinhoProduto inserir(CarrinhoProduto carrinhoProduto) {
        if (carrinhoProduto.getCliente() == null) {
            System.out.println("ERRO: CarrinhoProduto sem cliente!");
            return null;
        }
        
        CarrinhoProdutoDTO carrinhoProdutoDTO = toDTO(carrinhoProduto);
        
        if (!existe(carrinhoProduto)) {    
            carrinhoProdutoDTO = carrinhoProdutoDAO.inserir(carrinhoProdutoDTO);
            return toModel(carrinhoProdutoDTO);
        } else {
            carrinhoProdutoDTO = carrinhoProdutoDAO.aumentarQuantidade(carrinhoProdutoDTO);
            return toModel(carrinhoProdutoDTO);            
        }
    }
    
    public CarrinhoProduto alterar(CarrinhoProduto carrinhoProduto) {
        CarrinhoProdutoDTO carrinhoProdutoDTO = toDTO(carrinhoProduto);
        
        carrinhoProdutoDTO = carrinhoProdutoDAO.alterar(carrinhoProdutoDTO);
        
        return toModel(carrinhoProdutoDTO);
    }
    
    public boolean excluir(CarrinhoProduto carrinhoProduto) {
        CarrinhoProdutoDTO carrinhoProdutoDTO = toDTO(carrinhoProduto);
        
        return carrinhoProdutoDAO.excluir(
            carrinhoProdutoDTO.getProdutoId(), 
            carrinhoProdutoDTO.getClienteId()
        );
    }
    
    // Novo método para excluir todos os itens de um cliente
    public boolean limparCarrinhoCliente(int clienteId) {
        try {
            return carrinhoProdutoDAO.excluirPorCliente(clienteId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public CarrinhoProduto procurarPorId(int produtoId, int clienteId) {
        CarrinhoProdutoDTO carrinhoProdutoDTO = carrinhoProdutoDAO.procurarPorId(produtoId, clienteId);
        return toModel(carrinhoProdutoDTO);
    }
    
    // Novo método para listar apenas do cliente
    public List<CarrinhoProduto> listarPorCliente(int clienteId) {
        List<CarrinhoProdutoDTO> todosDTO = carrinhoProdutoDAO.pesquisarTodos();
        
        if (todosDTO == null) return new ArrayList<>();
        
        // Filtra apenas os itens deste cliente
        List<CarrinhoProdutoDTO> filtrados = todosDTO.stream()
            .filter(dto -> dto.getClienteId() == clienteId)
            .collect(Collectors.toList());
        
        return toModelList(filtrados);
    }
    
    public boolean existe(CarrinhoProduto carrinhoProduto) {
        CarrinhoProdutoDTO carrinhoProdutoDTO = toDTO(carrinhoProduto);
        return carrinhoProdutoDAO.existe(carrinhoProdutoDTO);
    }
    
    public List<CarrinhoProduto> pesquisarTodos() {
        List<CarrinhoProdutoDTO> carrinhoProdutosDTO = carrinhoProdutoDAO.pesquisarTodos();
        return toModelList(carrinhoProdutosDTO);
    }
    
    private CarrinhoProduto toModel(CarrinhoProdutoDTO carrinhoProdutoDTO) {
        if (carrinhoProdutoDTO == null) return null;
        
        Produto produto = produtoBO.procurarPorId(carrinhoProdutoDTO.getProdutoId());
        Cliente cliente = clienteBO.procurarPorId(carrinhoProdutoDTO.getClienteId());
        
        if (produto == null || cliente == null) {
            System.out.println("ERRO: Produto ou Cliente não encontrado para CarrinhoProduto");
            return null;
        }
        
        CarrinhoProduto carrinhoProduto = new CarrinhoProduto(
            produto,
            carrinhoProdutoDTO.getQuantidade(),
            cliente
        );
        carrinhoProduto.setCriadoEm(carrinhoProdutoDTO.getCriadoEm());
        
        return carrinhoProduto;
    }
    
    private List<CarrinhoProduto> toModelList(List<CarrinhoProdutoDTO> carrinhoProdutosDTO) {
        List<CarrinhoProduto> carrinhoProdutos = new ArrayList<>();
        
        if (carrinhoProdutosDTO == null) return carrinhoProdutos;
        
        for (CarrinhoProdutoDTO dto : carrinhoProdutosDTO) {
            CarrinhoProduto model = toModel(dto);
            if (model != null) {
                carrinhoProdutos.add(model);
            }
        }
        
        return carrinhoProdutos;
    }
    
    private CarrinhoProdutoDTO toDTO(CarrinhoProduto carrinhoProduto) {
        CarrinhoProdutoDTO dto = new CarrinhoProdutoDTO();
        
        dto.setProdutoId(carrinhoProduto.getProduto().getId());
        
        if (carrinhoProduto.getCliente() != null) {
            dto.setClienteId(carrinhoProduto.getCliente().getId());
        } else {
            // Fallback para usuário logado
            Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
            if (usuario instanceof Cliente) {
                dto.setClienteId(usuario.getId());
            } else {
                throw new IllegalArgumentException("CarrinhoProduto não tem cliente associado");
            }
        }
        
        dto.setQuantidade(carrinhoProduto.getQuantidade());
        dto.setCriadoEm(carrinhoProduto.getCriadoEm());
        
        return dto;
    }
}