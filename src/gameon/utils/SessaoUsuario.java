package gameon.utils;

import java.util.ArrayList;
import gameon.models.Carrinho; // Usa o Model
import gameon.models.Usuario;  // Usa o Model

public class SessaoUsuario {
    
    private static SessaoUsuario instancia;
    
    private Usuario usuarioLogado;
    private Carrinho carrinhoAtual;

    private SessaoUsuario() {
        carrinhoAtual = new Carrinho();
        // Garante que a lista comece vazia para evitar erros
        if (carrinhoAtual.getProdutos() == null) {
            carrinhoAtual.setProdutos(new ArrayList<>());
        }
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
        carrinhoAtual.setProdutos(new ArrayList<>());
    }
}