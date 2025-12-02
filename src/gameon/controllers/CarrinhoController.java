package gameon.controllers;

import java.text.DecimalFormat;
import java.util.List;

import gameon.models.Cliente;
import gameon.models.CarrinhoProduto;
import gameon.models.Produto;
import gameon.models.Usuario;
import gameon.models.BO.CarrinhoProdutoBO;
import gameon.utils.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CarrinhoController {

    @FXML private VBox vboxItens;
    @FXML private Label lblTotal;
    // REMOVA ESTA LINHA: @FXML private Label lblMensagem;
    
    private CarrinhoProdutoBO carrinhoProdutoBO = new CarrinhoProdutoBO();
    private DecimalFormat df = new DecimalFormat("R$ #,##0.00");

    @FXML
    public void initialize() {
        System.out.println("DEBUG: CarrinhoController iniciando...");
        carregarItensCarrinho();
    }

    private void carregarItensCarrinho() {
        vboxItens.getChildren().clear();
        
        // Verifica se tem cliente logado
        Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
        
        if (usuario == null || !(usuario instanceof Cliente)) {
            Label lblErro = new Label("Faça login para ver seu carrinho");
            lblErro.setStyle("-fx-text-fill: red; -fx-font-size: 14;");
            vboxItens.getChildren().add(lblErro);
            return;
        }
        
        Cliente cliente = (Cliente) usuario;
        
        try {
            CarrinhoProdutoBO carrinhoProdutoBO = new CarrinhoProdutoBO();
            
            // Busca os itens do carrinho APENAS deste cliente
            List<CarrinhoProduto> itens = carrinhoProdutoBO.listarPorCliente(cliente.getId());
            
            System.out.println("Itens no carrinho do cliente " + cliente.getNome() + ": " + 
                              (itens != null ? itens.size() : "null"));
            
            if (itens == null || itens.isEmpty()) {
                Label lblVazio = new Label("Seu carrinho está vazio");
                lblVazio.setStyle("-fx-font-size: 14; -fx-text-fill: #666;");
                vboxItens.getChildren().add(lblVazio);
                atualizarTotal();
                return;
            }
            
            for (CarrinhoProduto item : itens) {
                adicionarItemNaTela(item);
            }
            
            atualizarTotal();
            
        } catch (Exception e) {
            e.printStackTrace();
            Label lblErro = new Label("Erro ao carregar carrinho: " + e.getMessage());
            lblErro.setStyle("-fx-text-fill: red; -fx-font-size: 14;");
            vboxItens.getChildren().add(lblErro);
        }
    }

    private void mostrarMensagemNoVBox(String texto, boolean isErro) {
        Label mensagemLabel = new Label(texto);
        if (isErro) {
            mensagemLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14;");
        } else {
            mensagemLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
        }
        mensagemLabel.setPadding(new Insets(10, 20, 10, 20));
        vboxItens.getChildren().add(0, mensagemLabel);
    }

    private void adicionarItemNaTela(CarrinhoProduto item) {
        Produto p = item.getProduto();
        
        HBox linha = new HBox(15);
        linha.setPadding(new Insets(10));
        linha.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5;");
        
        Label lblNome = new Label(p.getNome());
        lblNome.setStyle("-fx-font-size: 14; -fx-pref-width: 150;");
        
        Label lblPreco = new Label(df.format(p.getPreco()));
        lblPreco.setStyle("-fx-pref-width: 80;");
        
        Spinner<Integer> spQuantidade = new Spinner<>(1, p.getEstoque(), item.getQuantidade());
        spQuantidade.setPrefWidth(80);
        spQuantidade.valueProperty().addListener((obs, oldVal, newVal) -> {
            item.setQuantidade(newVal);
            atualizarQuantidadeNoBanco(item);
            atualizarTotal();
        });
        
        Label lblSubtotal = new Label(df.format(p.getPreco() * item.getQuantidade()));
        lblSubtotal.setStyle("-fx-pref-width: 100; -fx-font-weight: bold;");
        
        Button btnRemover = new Button("✕");
        btnRemover.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        btnRemover.setOnAction(event -> {
            removerItem(item);
            vboxItens.getChildren().remove(linha);
            atualizarTotal();
        });
        
        // Atualiza subtotal quando quantidade muda
        spQuantidade.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblSubtotal.setText(df.format(p.getPreco() * newVal));
        });
        
        linha.getChildren().addAll(lblNome, lblPreco, spQuantidade, lblSubtotal, btnRemover);
        vboxItens.getChildren().add(linha);
    }
    
    private void atualizarQuantidadeNoBanco(CarrinhoProduto item) {
        try {
            CarrinhoProdutoBO bo = new CarrinhoProdutoBO();
            bo.alterar(item);
            mostrarMensagemNoVBox("Quantidade atualizada", false);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensagemNoVBox("Erro ao atualizar quantidade", true);
        }
    }
    
    private void removerItem(CarrinhoProduto item) {
        try {
            CarrinhoProdutoBO bo = new CarrinhoProdutoBO();
            bo.excluir(item);
            mostrarMensagemNoVBox("Item removido", false);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensagemNoVBox("Erro ao remover item", true);
        }
    }

    private void atualizarTotal() {
        double total = 0;
        
        for (var node : vboxItens.getChildren()) {
            if (node instanceof HBox linha) {
                double preco = 0;
                int quantidade = 1;
                
                for (var child : linha.getChildren()) {
                    if (child instanceof Label lbl) {
                        String texto = lbl.getText();
                        if (texto.startsWith("R$") && !texto.contains("Total")) {
                            try {
                                preco = Double.parseDouble(
                                    texto.replace("R$", "").replace(".", "")
                                        .replace(",", ".").trim()
                                );
                            } catch (NumberFormatException e) {
                                // Ignora
                            }
                        }
                    } else if (child instanceof Spinner<?> sp) {
                        quantidade = (int) sp.getValue();
                    }
                }
                
                total += preco * quantidade;
            }
        }
        
        if (lblTotal != null) {
            lblTotal.setText("Total: " + df.format(total));
        }
    }


    @FXML
    private void voltarMenu() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/Catalogo.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) vboxItens.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Catálogo");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensagemNoVBox("Erro ao abrir catálogo.", true);
        }
    }


    @FXML
    private void irParaCheckout() {
        // Verifica se há itens no carrinho
        if (vboxItens.getChildren().isEmpty()) {
            mostrarMensagemNoVBox("Seu carrinho está vazio", true);
            return;
        }
        
        // Verifica se o primeiro elemento é uma mensagem (carrinho vazio)
        if (vboxItens.getChildren().get(0) instanceof Label) {
            Label primeiroItem = (Label) vboxItens.getChildren().get(0);
            if (primeiroItem.getText().contains("vazio") || 
                primeiroItem.getText().contains("Faça login")) {
                return; // Já tem mensagem de erro
            }
        }
        
        // Verifica se cliente está logado
        Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
        if (usuario == null || !(usuario instanceof Cliente)) {
            mostrarMensagemNoVBox("Faça login para finalizar a compra", true);
            return;
        }
        
        Cliente cliente = (Cliente) usuario;
        
        // Calcula total
        double total = calcularTotalReal();
        if (total <= 0) {
            mostrarMensagemNoVBox("Valor inválido para checkout", true);
            return;
        }
        
        System.out.println("Checkout iniciado para cliente: " + cliente.getNome());
        System.out.println("Total: R$ " + total);
        
        // Salva total na sessão
        SessaoUsuario.getInstancia().setTotalCarrinho(total);
        
        // Vai para seleção de endereço
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/SelecionarEndereco.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) vboxItens.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Selecionar Endereço");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensagemNoVBox("Erro ao abrir checkout: " + e.getMessage(), true);
        }
    }

    private double calcularTotalReal() {
        try {
            Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
            if (!(usuario instanceof Cliente)) return 0;
            
            Cliente cliente = (Cliente) usuario;
            CarrinhoProdutoBO carrinhoBO = new CarrinhoProdutoBO();
            
            // Busca itens reais do banco
            List<CarrinhoProduto> itens = carrinhoBO.listarPorCliente(cliente.getId());
            if (itens == null || itens.isEmpty()) return 0;
            
            double total = 0;
            for (CarrinhoProduto item : itens) {
                total += item.getProduto().getPreco() * item.getQuantidade();
            }
            
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}