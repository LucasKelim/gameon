package gameon.controllers;

import gameon.models.BO.ClienteBO;
import gameon.models.BO.EnderecoBO;
import gameon.models.DTO.Cliente;
import gameon.models.DTO.Endereco;
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroClienteController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtSenha;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;

    // Campos de Endereço
    @FXML private TextField txtCep;
    @FXML private TextField txtEstado;
    @FXML private TextField txtCidade;
    @FXML private TextField txtBairro;
    @FXML private TextField txtLogradouro;
    @FXML private TextField txtNumero;

    @FXML
    private void cadastrarCliente() {
        try {
            // 1. Monta o objeto Cliente
            Cliente cli = new Cliente();
            cli.setNome(txtNome.getText());
            cli.setEmail(new Email(txtEmail.getText())); // Usa seu Value Object
            cli.setSenha(new Senha(txtSenha.getText())); // Usa seu Value Object
            cli.setCpf(txtCpf.getText());
            cli.setTelefone(txtTelefone.getText());
            
            // 2. Chama o ClienteBO para inserir (Isso salva no Asaas, Usuario e Cliente)
            ClienteBO clienteBO = new ClienteBO();
            Cliente clienteSalvo = clienteBO.inserir(cli);

            if (clienteSalvo != null) {
                // 3. Se salvou o cliente, agora salva o endereço
                salvarEndereco(clienteSalvo);
                
                exibirAlerta("Sucesso", "Cadastro realizado com sucesso!");
                voltarLogin();
            } else {
                exibirAlerta("Erro", "Não foi possível realizar o cadastro. Verifique os dados.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro", "Falha no sistema: " + e.getMessage());
        }
    }

    private void salvarEndereco(Cliente cliente) {
        // Se você tiver EnderecoBO e EnderecoDTO implementados:
        try {
            Endereco end = new Endereco();
            end.setCodigoPostal(txtCep.getText());
            end.setEstado(txtEstado.getText());
            end.setCidade(txtCidade.getText());
            end.setBairro(txtBairro.getText()); // Se tiver esse campo no DTO
            end.setLogradouro(txtLogradouro.getText());
            
            if (!txtNumero.getText().isEmpty()) {
                end.setNumero(Integer.parseInt(txtNumero.getText()));
            }
            
            // Vincula o endereço ao cliente recém-criado
            end.setCliente(cliente);

            EnderecoBO enderecoBO = new EnderecoBO();
            enderecoBO.inserir(end);
            
        } catch (Exception e) {
            System.out.println("Erro ao salvar endereço (o cliente foi salvo): " + e.getMessage());
        }
    }

    @FXML
    private void voltarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/Login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) txtNome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}