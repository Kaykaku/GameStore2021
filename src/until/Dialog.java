/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Admin
 */
public class Dialog {

    public static void showMessageDialog(String title,String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
    public static void Messages(Label name, String error){
        name.setText(error);
    }
    
    public static boolean CheckEmpty(TextField name){
        return name.getText().isEmpty();
    }
}
