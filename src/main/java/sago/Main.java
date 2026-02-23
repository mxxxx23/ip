package sago;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        ScrollPane scrollPane = new ScrollPane();
        VBox dialogContainer = new VBox();
        TextField userInput = new TextField();
        Button sendButton = new Button("Send");

        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(scrollPane, userInput, sendButton);

        // Layout positions
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 50.0);

        AnchorPane.setLeftAnchor(userInput, 0.0);
        AnchorPane.setBottomAnchor(userInput, 0.0);
        userInput.setPrefWidth(300);

        AnchorPane.setRightAnchor(sendButton, 0.0);
        AnchorPane.setBottomAnchor(sendButton, 0.0);

        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.show();
    }
}