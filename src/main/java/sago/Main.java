package sago;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

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
}
