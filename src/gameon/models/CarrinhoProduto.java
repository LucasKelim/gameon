package gameon.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class CarrinhoProduto {
    private Produto produto;
    private int quantidade;
    private Cliente cliente;  // ← ADICIONE ESTE ATRIBUTO!
    private LocalDateTime criadoEm;
    
    public CarrinhoProduto() {}
    
    // Novo construtor com cliente
    public CarrinhoProduto(Produto produto, int quantidade, Cliente cliente) {
        setProduto(produto);
        setQuantidade(quantidade);
        setCliente(cliente);
    }
    
    // Construtor antigo (para compatibilidade)
    public CarrinhoProduto(Produto produto, int quantidade) {
        this(produto, quantidade, null);
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public Cliente getCliente() {  // ← ADICIONE ESTE GETTER
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {  // ← ADICIONE ESTE SETTER
        this.cliente = cliente;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        CarrinhoProduto that = (CarrinhoProduto) o;
        
        // Agora compara produto E cliente
        return Objects.equals(this.produto.getId(), that.produto.getId()) &&
               Objects.equals(this.cliente != null ? this.cliente.getId() : null, 
                             that.cliente != null ? that.cliente.getId() : null);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(
            produto != null ? produto.getId() : null,
            cliente != null ? cliente.getId() : null
        );
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CarrinhoProduto [produto=");
        builder.append(produto != null ? produto.getNome() : "null");
        builder.append(", quantidade=");
        builder.append(quantidade);
        builder.append(", cliente=");
        builder.append(cliente != null ? cliente.getNome() : "null");
        builder.append("]");
        return builder.toString();
    }
}