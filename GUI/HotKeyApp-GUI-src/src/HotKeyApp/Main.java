package HotKeyApp;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * HotKeyApp_GUI v0.5
 *
 * @author Alexander Stahl
 * @version 0.5
 */

public class Main extends Application {
    double xOffset;
    double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));

        root.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("/HotKeyApp/images/btn_logo.png"));
        scene.setFill(null);
        String css = Main.class.getResource("main.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("HotKeyApp - Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}