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

public class Main extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));

    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        userInput = new TextField();
        sendButton = new Button("Send");

        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(scrollPane, userInput, sendButton);

        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 50.0);

        AnchorPane.setLeftAnchor(userInput, 0.0);
        AnchorPane.setBottomAnchor(userInput, 0.0);
        AnchorPane.setRightAnchor(userInput, 76.0); // leave space for button

        AnchorPane.setRightAnchor(sendButton, 0.0);
        AnchorPane.setBottomAnchor(sendButton, 0.0);

        // Auto-scroll to bottom when new messages appear
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        // Handle user input (both mouse click + Enter)
        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());

        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void handleUserInput() {
        dialogContainer.getChildren().addAll(new DialogBox(userInput.getText(), userImage));
        userInput.clear();
    }
}