/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ApplicationDAO;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import until.ProcessImage;
import until.Value;
import static until.Value.FORM_ACCOUNT;

/**
 *
 * @author Admin
 */
public class Main extends Application {

    double x, y;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Value.FORM_ORDER));
//        Parent root = FXMLLoader.load(getClass().getResource("/gui/Form/Login.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/gui/Main/GameStore.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

//        root.setOnMousePressed(evt -> {
//            x = evt.getScreenX() - stage.getX();
//            y = evt.getScreenY() - stage.getY();
//        });
//        root.setOnMouseDragged(evt -> {
//            stage.setX(evt.getScreenX() - x);
//            stage.setY(evt.getScreenY() - y);
//        });
               
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
