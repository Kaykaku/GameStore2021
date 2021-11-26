/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AccountDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import until.Auth;
import until.Value;

/**
 *
 * @author Admin
 */
public class Main extends Application {

    double x, y;

    @Override
    public void start(Stage stage) throws Exception {
        Auth.USER = new AccountDAO().selectByID(1);
//        Parent root = FXMLLoader.load(getClass().getResource("/gui/Form/Factory.fxml"));
//         Parent root = FXMLLoader.load(getClass().getResource(Value.FORM_STATISTICS));
//        Parent root = FXMLLoader.load(getClass().getResource(Value.FORM_PRODUCT));
        Parent root = FXMLLoader.load(getClass().getResource("/gui/Main/GameStore.fxml"));

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
