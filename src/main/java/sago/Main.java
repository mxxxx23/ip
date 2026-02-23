package sago;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class Main extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));

    private final Sago sago = new Sago();
    private Image sagoImage = new Image(this.getClass().getResourceAsStream("/images/DaSago.png"));

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            Scene scene = new Scene(loader.load(), 450, 600);
            stage.setScene(scene);
            stage.setTitle("Sago");

            stage.setMinWidth(400);
            stage.setMinHeight(500);
            stage.setResizable(true);

            MainWindow controller = loader.getController();
            controller.setSago(new Sago());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUserInput() {
        String input = userInput.getText();
        String response = sago.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSagoDialog(response, sagoImage)
        );

        userInput.clear();

        if (sago.isExit()) {
            // optional: close the window immediately
            // ((Stage) userInput.getScene().getWindow()).close();
        }
    }
}