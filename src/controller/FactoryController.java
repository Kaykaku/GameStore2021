/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.BoxChange;
import Animation.BoxMove;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Application;
import until.Value;

/**
 *
 * @author Admin
 */
public class FactoryController implements Initializable {

    @FXML
    private Pane pnl1;

    @FXML
    private Pane pnl2;
    @FXML
    private HBox hbox;

    Product_Box_HeaderController[] controllers;
    int step = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillListApps();
        startHeader();
    }

    void fillListApps() {
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Product_Box_Header.fxml"));
            double width = (paneP.getPrefWidth() + hbox.getSpacing()) * 8;
            hbox.setPrefSize(width, paneP.getPrefHeight());

            hbox.getChildren().clear();
            Pane[] nodes = new Pane[8];
            controllers = new Product_Box_HeaderController[8];
            for (int i = 0; i < 8; i++) {

                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/gui/Item/Product_Box_Header.fxml"));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                hbox.getChildren().add(nodes[h]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void startHeader() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(500), this::doStep),
                new KeyFrame(Duration.millis(3500), this::doStep),
                new KeyFrame(Duration.millis(6500), this::doStep),
                new KeyFrame(Duration.millis(9500), this::doStep),
                new KeyFrame(Duration.millis(12500), this::doStep),
                new KeyFrame(Duration.millis(15500), this::doStep),
                new KeyFrame(Duration.millis(18500), this::doStep),
                new KeyFrame(Duration.millis(21500), this::doStep),
                new KeyFrame(Duration.millis(24500), this::doStep)
        );
        timeline.setCycleCount(5);
        timeline.play();
    }

    void doStep(ActionEvent event) {
        step++;
        if (step != 9) {
            if(step==1){
                controllers[7].getBox().setStyle("-fx-border-color: transparent;");
                controllers[0].getBox().setStyle("-fx-border-color: #F38064;");
            }else{
                controllers[step - 2].getBox().setStyle("-fx-border-color: transparent;");
                controllers[step - 1].getBox().setStyle("-fx-border-color: #F38064;");
            }           
            new BoxChange(controllers[step - 1].getBar()).play();
            
            if (step > 4) {
                new BoxMove(hbox).play();
            }
        } else {
            step = 0;
            new BoxMove(hbox).play();
        }
    }
}
