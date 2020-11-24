package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;

public class HelpWindow {
    @FXML
    public Button btnReturn;

    /**
     * Display a help window
     *
     * @param actionEvent
     * @param classObj
     */
    public static void displayHelp(ActionEvent actionEvent, Class classObj) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(classObj.getResource("/sample/view/HelpWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Main.APP_SCENE_WIDTH, Main.APP_SCENE_HEIGHT);
            scene.getStylesheets().add("sample/view/css/menu.css");
            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(scene);
            stage.show();
            // can hide the the previous window
            // ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Close Help window and return to the main window.
     *
     * @param actionEvent
     */
    public void btnClickReturn(ActionEvent actionEvent) {
        Window w = btnReturn.getScene().getWindow();
        if (w instanceof Stage)
            ((Stage) w).close();
    }
}
