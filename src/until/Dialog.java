/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import controller.Dialog_MessageController;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Admin
 */
public class Dialog {

//    public static void showMessageDialog(String title, String content) {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//
//        alert.showAndWait();
//    }
    public static void showMessageDialog(String title, String content) {
        try {
            FXMLLoader loader = new FXMLLoader(Dialog.class.getResource(Value.DIALOG_MESSAGE));
            Parent root = loader.load();
            Dialog_MessageController controller = loader.getController();
            controller.setText(content,0);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            
            stage.initStyle(StageStyle.TRANSPARENT);       
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {

        }
    }

     public static int showComfirmDialog(String title, String content) {
        try {
            FXMLLoader loader = new FXMLLoader(Dialog.class.getResource(Value.DIALOG_MESSAGE));
            Parent root = loader.load();
            Dialog_MessageController controller = loader.getController();
            controller.setText(content,1);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            
            stage.initStyle(StageStyle.TRANSPARENT);       
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            return controller.getSelectOP();
        } catch (IOException ex) {

        }
        return 0;
    }
}
