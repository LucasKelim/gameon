package gameon.utils;

import gameon.models.DTO.Carrinho;
import gameon.models.DTO.UsuarioDTO;
import java.util.ArrayList;

public class SessaoUsuario {
    
    // Instância única da classe (Padrão Singleton)
    private static SessaoUsuario instancia;
    
    private UsuarioDTO usuarioLogado;
    private Carrinho carrinhoAtual;

    // Construtor privado para ninguém dar "new SessaoUsuario()" fora daqui
    private SessaoUsuario() {
        carrinhoAtual = new Carrinho();
        carrinhoAtual.setProdutos(new ArrayList<>()); 
    }

    // Método global para pegar a sessão
    public static SessaoUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SessaoUsuario();
        }
        return instancia;
    }

    public UsuarioDTO getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(UsuarioDTO usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Carrinho getCarrinhoAtual() {
        return carrinhoAtual;
    }
    
    public void limparSessao() {
        usuarioLogado = null;
        carrinhoAtual = new Carrinho();
        carrinhoAtual.setProdutos(new ArrayList<>());
    }
}