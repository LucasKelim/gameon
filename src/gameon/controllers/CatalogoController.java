package gameon.controllers;

import gameon.models.BO.ProdutoBO;
import gameon.models.DTO.CarrinhoProduto;
import gameon.models.DTO.Produto;
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
            List<Produto> listaProdutos = produtoBO.pesquisarTodos(); // Chama o seu DAO via BO
            
            if (listaProdutos != null) {
                ObservableList<Produto> observableList = FXCollections.observableArrayList(listaProdutos);
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
        // Pega o item selecionado na tabela
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            mostrarAlerta("Atenção", "Selecione um produto na tabela para adicionar.");
            return;
        }

        if (produtoSelecionado.getEstoque() <= 0) {
            mostrarAlerta("Estoque Indisponível", "Este produto está fora de estoque.");
            return;
        }

        // Lógica para adicionar ou atualizar no carrinho da sessão
        adicionarNaSessao(produtoSelecionado);
        
        lblMensagem.setText("Produto '" + produtoSelecionado.getNome() + "' adicionado ao carrinho!");
        lblMensagem.setStyle("-fx-text-fill: green;");
    }

    private void adicionarNaSessao(Produto produto) {
        List<CarrinhoProduto> carrinho = SessaoUsuario.getInstancia().getCarrinhoAtual().getProdutos();
        
        // Verifica se o produto já está no carrinho para apenas aumentar a quantidade
        boolean jaExiste = false;
        for (CarrinhoProduto item : carrinho) {
            if (item.getProduto().getId() == produto.getId()) {
                item.setQuantidade(item.getQuantidade() + 1);
                jaExiste = true;
                break;
            }
        }

        // Se não existe, cria um novo item
        if (!jaExiste) {
            CarrinhoProduto novoItem = new CarrinhoProduto();
            novoItem.setProduto(produto);
            novoItem.setQuantidade(1);
            carrinho.add(novoItem);
        }
    }

    @FXML
    public void irParaCarrinho() {
        try {
            // Navega para a tela de Carrinho (que vamos criar a seguir ou você já tem)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/Carrinho.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) tabelaProdutos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Meu Carrinho");
            stage.centerOnScreen();
            
        } catch (Exception e) {
            // Se der erro (provavelmente pq o arquivo Carrinho.fxml não existe ainda), avisa
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