package gameon.controllers;


import gameon.models.BO.UsuarioBO;
import gameon.models.DTO.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblMensagem;

    public void fazerLogin() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        UsuarioBO usuarioBO = new UsuarioBO();
        Usuario usuarioTemp = new gameon.models.DTO.Cliente(); // Polimorfismo, usando Cliente como base
        usuarioTemp.setEmail(new gameon.models.valuesobjects.Email(email)); 
        
        // Você precisará adaptar seu UsuarioBO/DAO para validar login (buscar por email e comparar senha)
        Usuario usuarioEncontrado = usuarioBO.procurarPorEmail(usuarioTemp);

        if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(senha)) {
            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Login realizado com sucesso!");
            // Aqui você redirecionaria para a tela principal (Menu.fxml)
        } else {
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Email ou senha inválidos.");
        }
    }
}