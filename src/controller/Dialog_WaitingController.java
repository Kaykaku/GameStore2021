/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import animatefx.animation.Swing;
import animatefx.animation.Tada;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import until.Auth;
import until.Value;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Dialog_WaitingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane png_Box;

    @FXML
    private ImageView img_bg;

    @FXML
    private TilePane tile_text;

    Label label = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(5000);
                Platform.runLater(() -> {
                    try {
                        img_bg.getScene().getWindow().hide();
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource(Value.MAIN));
                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.getIcons().add(new Image(getClass().getResource(Value.ICON_APP).toURI().toString()));
                        stage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(Dialog_WaitingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

        String hello = "HELLO " + Auth.USER.getUsername().toUpperCase();
        Task<Void> task1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                char[] arr = hello.toCharArray();
                int time = 4000 / (arr.length * 2);
                boolean f = true;
                for (int i = 0; i < arr.length * 2; i++) {
                    Thread.sleep(time);
                    if (f) {
                        label = new Label(arr[i / 2] + "");
                    } else {
                        label = new Label(" ");
                    }
                    f = !f;
                    label.setStyle("-fx-background-radius : 10px; -fx-text-fill : white; -fx-font-size: 20px; ");
                    Platform.runLater(() -> {
                        tile_text.getChildren().add(label);
                        new Swing(label).play();
                    });
                }
                new Tada(tile_text).play();
                return null;
            }

        };
        Thread t1 = new Thread(task1);
        t1.setDaemon(true);
        t1.start();
    }

}
