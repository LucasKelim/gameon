package gameon.controllers;

import java.text.DecimalFormat;
import java.util.List;

import gameon.models.Produto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CarrinhoController {

    @FXML
    private VBox vboxItens;

    @FXML
    private Label lblTotal;
    
    @FXML private Label lblMensagem;

    private List<Produto> produtosCarrinho;

    private DecimalFormat df = new DecimalFormat("R$ #,##0.00");


    // Chame este método quando abrir a tela
    public void setProdutosCarrinho(List<Produto> produtos) {
        this.produtosCarrinho = produtos;
        carregarItens();
        atualizarTotal();
    }

    private void carregarItens() {
        vboxItens.getChildren().clear();

        for (Produto p : produtosCarrinho) {

            HBox linha = new HBox(15);
            linha.setPadding(new Insets(10));
            linha.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5;");

            Label lblNome = new Label(p.getNome());
            lblNome.setStyle("-fx-font-size: 16;");

            Label lblPreco = new Label(df.format(p.getPreco()));

            Spinner<Integer> spQuantidade = new Spinner<>(1, 99, 1);
            spQuantidade.valueProperty().addListener((obs, oldVal, newVal) -> atualizarTotal());

            Button btnRemover = new Button("Remover");
            btnRemover.setOnAction(event -> {
                produtosCarrinho.remove(p);
                carregarItens();
                atualizarTotal();
            });

            linha.getChildren().addAll(lblNome, lblPreco, spQuantidade, btnRemover);

            vboxItens.getChildren().add(linha);
        }
    }

    private void atualizarTotal() {
        double total = vboxItens.getChildren()
            .stream()
            .filter(n -> n instanceof HBox)
            .map(n -> (HBox) n)
            .mapToDouble(linha -> {
                double preco = 0;
                int quantidade = 1;

                for (var node : linha.getChildren()) {
                    if (node instanceof Label lbl && lbl.getText().startsWith("R$")) {
                        preco = Double.parseDouble(
                            lbl.getText().replace("R$", "").replace(",", ".").trim()
                        );
                    } else if (node instanceof Spinner<?> sp) {
                        quantidade = (int) sp.getValue();
                    }
                }

                return preco * quantidade;
            })
            .sum();

        lblTotal.setText("Total: " + df.format(total));
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
            lblMensagem.setText("Erro ao abrir catálogo.");
        }
    }

    @FXML
    private void irParaCheckout() {
        // implementar navegação
    }
}
