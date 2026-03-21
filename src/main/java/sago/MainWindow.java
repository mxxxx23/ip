package sago;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image sagoImage = new Image(this.getClass().getResourceAsStream("/images/DaSago.png"));

    private Sago sago;

    @FXML
    public void initialize() {
        assert scrollPane != null : "scrollPane was not injected (check fx:id in FXML)";
        assert dialogContainer != null : "dialogContainer was not injected (check fx:id in FXML)";
        assert userInput != null : "userInput was not injected (check fx:id in FXML)";
        assert sendButton != null : "sendButton was not injected (check fx:id in FXML)";

        // Auto-scroll
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setSago(Sago s) {
        sago = s;
        // Optional greeting bubble:
        dialogContainer.getChildren().add(
                DialogBox.getSagoDialog("Hello! I'm Sago. What can I do for you?", sagoImage)
        );
    }

    @FXML
    private void handleUserInput() {
        assert sago != null : "sago should be set before handling input";
        String input = userInput.getText();
        assert input != null : "TextField#getText() should not return null";
        String response = sago.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSagoDialog(response, sagoImage)
        );

        userInput.clear();

        if (sago.isExit()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.millis(600));
            delay.setOnFinished(event -> {
                Stage stage = (Stage) userInput.getScene().getWindow();
                stage.close();
            });
            delay.play();
        }
    }
}
