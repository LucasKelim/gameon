package gameon.controllers;

import gameon.models.Cliente;
import gameon.models.Endereco;
import gameon.models.Usuario;
import gameon.models.BO.EnderecoBO;
import gameon.utils.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.List;

public class SelecionarEnderecoController {

    @FXML private VBox vboxEnderecos;
    @FXML private Label lblTotal;
    @FXML private Label lblMensagem;
    
    private EnderecoBO enderecoBO = new EnderecoBO();
    private ToggleGroup toggleGroup = new ToggleGroup();
    private DecimalFormat df = new DecimalFormat("R$ #,##0.00");

    @FXML
    public void initialize() {
        System.out.println("SelecionarEnderecoController inicializado");
        carregarEnderecos();
        carregarTotal();
    }

    private void carregarEnderecos() {
        vboxEnderecos.getChildren().clear();
        
        Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
        if (!(usuario instanceof Cliente)) {
            lblMensagem.setText("Erro: Usuário não é cliente");
            lblMensagem.setStyle("-fx-text-fill: red;");
            return;
        }
        
        Cliente cliente = (Cliente) usuario;
        System.out.println("Carregando endereços para cliente: " + cliente.getNome() + " (ID: " + cliente.getId() + ")");
        
        try {
            // Usando o método que você já tem no EnderecoDAO
            List<Endereco> enderecos = enderecoBO.pesquisarTodos();
            
            // Filtra apenas os endereços deste cliente
            List<Endereco> enderecosCliente = enderecos.stream()
                .filter(e -> e.getCliente() != null && e.getCliente().getId() == cliente.getId())
                .toList();
            
            System.out.println("Endereços encontrados: " + enderecosCliente.size());
            
            if (enderecosCliente.isEmpty()) {
                lblMensagem.setText("Nenhum endereço cadastrado. Cadastre um endereço para continuar.");
                lblMensagem.setStyle("-fx-text-fill: red;");
                
                // Adiciona botão para cadastrar endereço
                Button btnCadastrar = new Button("+ Cadastrar Novo Endereço");
                btnCadastrar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14;");
                btnCadastrar.setOnAction(e -> cadastrarNovoEndereco());
                
                VBox container = new VBox(10);
                container.setPadding(new Insets(20));
                container.getChildren().addAll(lblMensagem, btnCadastrar);
                vboxEnderecos.getChildren().add(container);
                return;
            }
            
            boolean primeiro = true;
            for (Endereco endereco : enderecosCliente) {
                RadioButton rb = new RadioButton(formatarEndereco(endereco));
                rb.setToggleGroup(toggleGroup);
                rb.setUserData(endereco);
                
                if (primeiro) {
                    rb.setSelected(true);
                    primeiro = false;
                }
                
                vboxEnderecos.getChildren().add(rb);
            }
            
            lblMensagem.setText("Selecione o endereço de entrega:");
            lblMensagem.setStyle("-fx-text-fill: green;");
            
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao carregar endereços: " + e.getMessage());
            lblMensagem.setStyle("-fx-text-fill: red;");
        }
    }
    
    private String formatarEndereco(Endereco endereco) {
        return String.format("%s, %s\n%s - %s/%s\nCEP: %s",
            endereco.getLogradouro(),
            endereco.getNumero() > 0 ? endereco.getNumero() : "S/N",
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep()
        );
    }
    
    private void cadastrarNovoEndereco() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/CadastroEndereco.fxml"));
            Parent root = loader.load();
            
            // Configura o controller para voltar para seleção de endereço
            CadastroEnderecoController controller = loader.getController();
            controller.setVoltarParaSelecao(true);
            
            Stage stage = (Stage) vboxEnderecos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Cadastrar Endereço");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível abrir cadastro de endereço.");
        }
    }
    
    private void carregarTotal() {
        double total = SessaoUsuario.getInstancia().getTotalCarrinho();
        lblTotal.setText("Total: " + df.format(total));
    }

    @FXML
    private void continuarParaPagamento() {
        RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
        
        if (selected == null) {
            mostrarAlerta("Seleção Necessária", "Selecione um endereço para entrega.");
            return;
        }
        
        Endereco enderecoSelecionado = (Endereco) selected.getUserData();
        SessaoUsuario.getInstancia().setEnderecoSelecionado(enderecoSelecionado);
        
        System.out.println("Endereço selecionado: " + enderecoSelecionado.getLogradouro());
        
        // Vai para tela de pagamento
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/PagamentoPix.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) vboxEnderecos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Pagamento com PIX");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao abrir pagamento: " + e.getMessage());
            lblMensagem.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void voltarParaCarrinho() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/Carrinho.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) vboxEnderecos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Carrinho");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
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