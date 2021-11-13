/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_NewsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pnl_Content;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private VBox Vbox_ListNews;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private HBox hbox_Controller;

    @FXML
    private Pane pnl_Title;

    @FXML
    private Pane pnl_ListNews;

    @FXML
    private ScrollPane pnl_ScrollListNews;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private Pane pnl_List_News;

    @FXML
    private Pane pnl_Image;

    int row = 20;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            JFXDatePicker datePicker = new JFXDatePicker();
            datePicker.setDefaultColor(Paint.valueOf("lightblue"));
            datePicker.setStyle("-fx-text-fill: green");

            pnl_CreationDate.getChildren().add(datePicker);

            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_News.fxml"));
            Vbox_ListNews.setPrefSize(paneP.getPrefWidth(), paneP.getPrefHeight() * row);
            pnl_ListNews.setPrefSize(paneP.getPrefWidth(), paneP.getPrefHeight() * row);
            Vbox_ListNews.getChildren().clear();
            Pane[] nodes = new Pane[row];
            for (int i = 0; i < row; i++) {
                final int h = i;
                nodes[h] = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_News.fxml"));
                Vbox_ListNews.getChildren().add(nodes[h]);
            }
            displayFormAnimation();
        } catch (Exception e) {
        }
    }

    void displayFormAnimation() {
        new FadeInDown(pnl_Basic_Info).play();
        new FadeInDownBig(pnl_Image).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Add_Info).play();
        new FadeInRightBig(pnl_List_News).play();
        new FadeInUpBig(pnl_Content).play();
        new ZoomInUp(hbox_Controller).play();
    }
}
