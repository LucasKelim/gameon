package gameon.controllers;

import gameon.models.Usuario;
import gameon.models.BO.UsuarioBO;
import gameon.utils.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblMensagem;

    public void fazerLogin() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            lblMensagem.setText("Preencha todos os campos!");
            return;
        }

        UsuarioBO usuarioBO = new UsuarioBO();
        
        Usuario usuarioEncontrado = usuarioBO.procurarPorEmail(email);

        if (usuarioEncontrado != null && 
            usuarioBO.verificarSenha(senha, usuarioEncontrado.getSenha().getValor())) {
            
            SessaoUsuario.getInstancia().setUsuarioLogado(usuarioEncontrado);
            
            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Sucesso! Entrando...");
            
            irParaCatalogo();
            
        } else {
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Email ou senha incorretos.");
        }
    }

    private void irParaCatalogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/Catalogo.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Catálogo");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao abrir catálogo.");
        }
    }

    @FXML
    public void irParaCadastro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/CadastroCliente.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Cadastro");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao abrir cadastro: " + e.getMessage());
        }
    }
}