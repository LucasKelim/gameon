package gameon.controllers;

import gameon.models.Cliente;
import gameon.models.Endereco;
import gameon.models.Usuario;
import gameon.models.BO.EnderecoBO;
import gameon.utils.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroEnderecoController {

    @FXML private TextField txtCep;
    @FXML private TextField txtEstado;
    @FXML private TextField txtCidade;
    @FXML private TextField txtBairro;
    @FXML private TextField txtLogradouro;
    @FXML private TextField txtNumero;
    
    private EnderecoBO enderecoBO = new EnderecoBO();
    private boolean voltarParaSelecao = true;

    @FXML
    public void initialize() {
        // Auto-preenche CEP se quiser (opcional)
    }

    @FXML
    private void salvarEndereco() {
        try {
            // Valida campos obrigatórios
            if (txtLogradouro.getText().isEmpty() || txtBairro.getText().isEmpty() ||
                txtCidade.getText().isEmpty() || txtEstado.getText().isEmpty() ||
                txtCep.getText().isEmpty()) {
                mostrarAlerta("Campos Obrigatórios", 
                    "Preencha todos os campos obrigatórios (exceto número).");
                return;
            }
            
            Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
            if (!(usuario instanceof Cliente)) {
                mostrarAlerta("Erro", "Apenas clientes podem cadastrar endereços.");
                return;
            }
            
            Cliente cliente = (Cliente) usuario;
            
            // Cria o endereço
            Endereco endereco = new Endereco();
            endereco.setLogradouro(txtLogradouro.getText());
            
            if (!txtNumero.getText().isEmpty()) {
                try {
                    endereco.setNumero(Integer.parseInt(txtNumero.getText()));
                } catch (NumberFormatException e) {
                    mostrarAlerta("Número Inválido", "Digite um número válido para o endereço.");
                    return;
                }
            }
            
            endereco.setBairro(txtBairro.getText());
            endereco.setCidade(txtCidade.getText());
            endereco.setEstado(txtEstado.getText());
            endereco.setCep(txtCep.getText());
            endereco.setCliente(cliente);
            
            // Salva no banco
            Endereco enderecoSalvo = enderecoBO.inserir(endereco);
            
            if (enderecoSalvo != null) {
                mostrarAlerta("Sucesso", "Endereço cadastrado com sucesso!");
                
                // Se estiver no fluxo de checkout, volta para seleção
                if (voltarParaSelecao) {
                    voltarParaSelecionarEndereco();
                } else {
                    voltarParaPerfil();
                }
            } else {
                mostrarAlerta("Erro", "Não foi possível salvar o endereço.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao salvar endereço: " + e.getMessage());
        }
    }

    @FXML
    private void voltar() {
        if (voltarParaSelecao) {
            voltarParaSelecionarEndereco();
        } else {
            voltarParaPerfil();
        }
    }
    
    private void voltarParaSelecionarEndereco() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/SelecionarEndereco.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtCep.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Selecionar Endereço");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void voltarParaPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/PerfilCliente.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtCep.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Meu Perfil");
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
    
    // Método para configurar se volta para seleção ou perfil
    public void setVoltarParaSelecao(boolean voltarParaSelecao) {
        this.voltarParaSelecao = voltarParaSelecao;
    }
}