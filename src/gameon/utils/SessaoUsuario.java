package gameon.utils;

import gameon.models.Carrinho;
import gameon.models.Endereco;
import gameon.models.Ordem;
import gameon.models.Usuario;

public class SessaoUsuario {
    
    private static SessaoUsuario instancia;
    
    private Usuario usuarioLogado;
    private Carrinho carrinhoAtual;
    private double totalCarrinho;  // ← ADICIONE
    private Endereco enderecoSelecionado;  // ← ADICIONE
    private Ordem ordemAtual;  // ← ADICIONE

    private SessaoUsuario() {
        carrinhoAtual = new Carrinho();
    }

    public static SessaoUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SessaoUsuario();
        }
        return instancia;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Carrinho getCarrinhoAtual() {
        return carrinhoAtual;
    }
    
    public boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }
    
    public void limparSessao() {
        usuarioLogado = null;
        carrinhoAtual = new Carrinho();
        totalCarrinho = 0;
        enderecoSelecionado = null;
        ordemAtual = null;
    }
    
    // ===== NOVOS MÉTODOS =====
    
    public double getTotalCarrinho() {
        return totalCarrinho;
    }
    
    public void setTotalCarrinho(double totalCarrinho) {
        this.totalCarrinho = totalCarrinho;
    }
    
    public Endereco getEnderecoSelecionado() {
        return enderecoSelecionado;
    }
    
    public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
        this.enderecoSelecionado = enderecoSelecionado;
    }
    
    public Ordem getOrdemAtual() {
        return ordemAtual;
    }
    
    public void setOrdemAtual(Ordem ordemAtual) {
        this.ordemAtual = ordemAtual;
    }
}