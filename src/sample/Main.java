package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final Integer APP_SCENE_WIDTH = 1018;
    public static final Integer APP_SCENE_HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"));
        primaryStage.setTitle("Black Jack");
        Scene main = new Scene(root, APP_SCENE_WIDTH, APP_SCENE_HEIGHT);
        main.getStylesheets().add("sample/view/css/styles.css");
        primaryStage.setScene(main);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
