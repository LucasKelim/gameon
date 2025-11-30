package gameon.controllers;

import gameon.models.Cliente; 
import gameon.models.Endereco; 
import gameon.models.BO.ClienteBO;
import gameon.models.BO.EnderecoBO;
import gameon.models.valuesobjects.Email; 
import gameon.models.valuesobjects.Senha;
import gameon.models.valuesobjects.Cpf;
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
            Cliente cliente = new Cliente();
            cliente.setNome(txtNome.getText());
            
            cliente.setEmail(new Email(txtEmail.getText())); 
            cliente.setSenha(new Senha(txtSenha.getText()));
            
            cliente.setCpf(new Cpf(txtCpf.getText()));
            cliente.setTelefone(txtTelefone.getText());

            ClienteBO clienteBO = new ClienteBO();
            Cliente clienteSalvo = clienteBO.inserir(cliente);
            
            System.out.println(clienteSalvo);

            if (clienteSalvo != null) {
                salvarEndereco(clienteSalvo);
                
                exibirAlerta("Sucesso", "Cadastro realizado com sucesso!");
                voltarLogin();
            } else {
                exibirAlerta("Erro", "Não foi possível realizar o cadastro. Verifique se o email ou CPF já existem.");
            }

        } catch (IllegalArgumentException e) {
            exibirAlerta("Dados Inválidos", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro Crítico", "Falha no sistema: " + e.getMessage());
        }
    }

    private void salvarEndereco(Cliente cliente) {
        try {
            Endereco endereco = new Endereco();
            endereco.setCep(txtCep.getText());
            endereco.setEstado(txtEstado.getText());
            endereco.setCidade(txtCidade.getText());
            endereco.setBairro(txtBairro.getText());
            endereco.setLogradouro(txtLogradouro.getText());
            
            if (!txtNumero.getText().isEmpty()) {
            	endereco.setNumero(Integer.parseInt(txtNumero.getText()));
            }
            
            endereco.setCliente(cliente);

            EnderecoBO enderecoBO = new EnderecoBO();
            enderecoBO.inserir(endereco);
            
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