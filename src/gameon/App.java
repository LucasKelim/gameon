package gameon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega o arquivo visual FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/Login.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Game On - Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}