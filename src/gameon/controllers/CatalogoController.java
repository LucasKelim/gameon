package gameon.controllers;

import gameon.models.Carrinho;
import gameon.models.CarrinhoProduto;
import gameon.models.Produto;
import gameon.models.Usuario;
import gameon.models.BO.CarrinhoProdutoBO;
import gameon.models.BO.ProdutoBO;
import gameon.utils.SessaoUsuario;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import gameon.models.Cliente;
import gameon.models.BO.CarrinhoProdutoBO;

import java.util.List;

public class CatalogoController {

    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, String> colDescricao;
    @FXML private TableColumn<Produto, Number> colPreco;
    @FXML private TableColumn<Produto, Number> colEstoque;
    @FXML private Label lblMensagem;

    @FXML
    public void initialize() {
        configurarColunas();
        carregarDados();
    }

    private void configurarColunas() {
        // Ensina a tabela a pegar os dados do objeto Produto
        colNome.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNome()));
        colDescricao.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescricao()));
        colPreco.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getPreco()));
        colEstoque.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getEstoque()));
    }

    private void carregarDados() {
        try {
            ProdutoBO produtoBO = new ProdutoBO();
            List<Produto> produtos = produtoBO.pesquisarTodos();
            
            if (produtos != null) {
                ObservableList<Produto> observableList = FXCollections.observableArrayList(produtos);
                tabelaProdutos.setItems(observableList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao carregar produtos: " + e.getMessage());
            lblMensagem.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void adicionarAoCarrinho() {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        
        if (produtoSelecionado == null) {
            mostrarAlerta("Atenção", "Selecione um produto na tabela para adicionar.");
            return;
        }
        
        if (produtoSelecionado.getEstoque() <= 0) {
            mostrarAlerta("Estoque Indisponível", "Este produto está fora de estoque.");
            return;
        }
        
        // VERIFICA SE TEM CLIENTE LOGADO
        Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
        if (usuario == null || !(usuario instanceof Cliente)) {
            mostrarAlerta("Login Necessário", "Faça login como cliente para adicionar ao carrinho.");
            return;
        }
        
        Cliente cliente = (Cliente) usuario;
        
        try {
            CarrinhoProdutoBO carrinhoProdutoBO = new CarrinhoProdutoBO();
            
            // Cria o item do carrinho COM CLIENTE
            CarrinhoProduto item = new CarrinhoProduto(produtoSelecionado, 1, cliente);
            
            System.out.println("Adicionando ao carrinho: " + produtoSelecionado.getNome() + 
                              " para cliente: " + cliente.getNome());
            
            // Insere no banco
            CarrinhoProduto resultado = carrinhoProdutoBO.inserir(item);
            
            if (resultado != null) {
                lblMensagem.setText("Produto '" + produtoSelecionado.getNome() + "' adicionado ao carrinho!");
                lblMensagem.setStyle("-fx-text-fill: green;");
                
                // Atualiza também na sessão
                List<CarrinhoProduto> carrinhoSessao = SessaoUsuario.getInstancia()
                    .getCarrinhoAtual().getProdutos();
                carrinhoSessao.add(resultado);
                
            } else {
                mostrarAlerta("Erro", "Não foi possível adicionar ao carrinho.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao adicionar ao carrinho: " + e.getMessage());
        }
    }

    @FXML
    public void irParaCarrinho() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/Carrinho.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) tabelaProdutos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Meu Carrinho");
            stage.centerOnScreen();
            
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível abrir o carrinho: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}