/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AccountDAO;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Thread.sleep(5000);
                    System.gc();
//                    System.out.println(Runtime.getRuntime().totalMemory());
//                    System.out.println(Thread.activeCount());
                }
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
//                Auth.USER = new AccountDAO().selectByID(107);
//        Parent root = FXMLLoader.load(getClass().getResource(Value.DIALOG_MESSAGE));
//         Parent root = FXMLLoader.load(getClass().getResource(Value.DIALOG_WAITING));
        Parent root = FXMLLoader.load(getClass().getResource(Value.FORM_LOGIN));
//        Parent root = FXMLLoader.load(getClass().getResource(Value.MAIN));

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.getIcons().clear();
        stage.getIcons().add(new Image(getClass().getResource(Value.ICON_APP).toURI().toString()));
        stage.show();

        root.setOnMousePressed(evt -> {
            x = evt.getScreenX() - stage.getX();
            y = evt.getScreenY() - stage.getY();
        });
        root.setOnMouseDragged(evt -> {
            stage.setX(evt.getScreenX() - x);
            stage.setY(evt.getScreenY() - y);
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
