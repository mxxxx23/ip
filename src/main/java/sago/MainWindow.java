package sago;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image sagoImage = new Image(this.getClass().getResourceAsStream("/images/DaSago.png"));

    private Sago sago;

    @FXML
    public void initialize() {
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
        String input = userInput.getText();
        String response = sago.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSagoDialog(response, sagoImage)
        );

        userInput.clear();

        // Optional: close on bye
        // if (sago.isExit()) { ((Stage) dialogContainer.getScene().getWindow()).close(); }
    }
}