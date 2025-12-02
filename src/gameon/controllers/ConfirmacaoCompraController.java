package gameon.controllers;

import gameon.models.Ordem;
import gameon.models.OrdemProduto;
import gameon.models.BO.OrdemProdutoBO;
import gameon.utils.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.List;

public class ConfirmacaoCompraController {

    @FXML private Label lblNumeroPedido;
    @FXML private Label lblStatus;
    @FXML private Label lblValor;
    @FXML private Label lblMensagem;
    @FXML private VBox vboxItens;
    
    private DecimalFormat df = new DecimalFormat("R$ #,##0.00");
    private OrdemProdutoBO ordemProdutoBO = new OrdemProdutoBO();

    @FXML
    public void initialize() {
        carregarConfirmacao();
    }
    
    private void carregarConfirmacao() {
        try {
            Ordem ordem = SessaoUsuario.getInstancia().getOrdemAtual();
            
            if (ordem == null) {
                lblMensagem.setText("Erro: Nenhuma ordem encontrada");
                return;
            }
            
            // Mostra informações da ordem
            lblNumeroPedido.setText("Pedido #" + ordem.getId());
            lblStatus.setText("Status: " + ordem.getStatus());
            lblValor.setText("Valor: " + df.format(ordem.getValorTotal()));
            
            // Carrega itens do pedido
            List<OrdemProduto> itens = ordemProdutoBO.listarPorOrdem(ordem.getId());
            
            if (itens != null && !itens.isEmpty()) {
                for (OrdemProduto item : itens) {
                    Label lblItem = new Label(
                        String.format("%s x%d - %s", 
                            item.getProduto().getNome(),
                            item.getQuantidade(),
                            df.format(item.getProduto().getPreco() * item.getQuantidade())
                        )
                    );
                    lblItem.setStyle("-fx-font-size: 14; -fx-padding: 5 0;");
                    vboxItens.getChildren().add(lblItem);
                }
            }
            
            lblMensagem.setText("Compra realizada com sucesso! Aguarde a confirmação do pagamento.");
            
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao carregar confirmação: " + e.getMessage());
        }
    }

    @FXML
    private void voltarParaCatalogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/Catalogo.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblNumeroPedido.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Catálogo");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}