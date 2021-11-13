/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_ProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_clear;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private Pane pnl_Title;

    @FXML
    private Pane pnl_Status;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private VBox box_ListProduct;

    @FXML
    private Pane pnl_Image;

    @FXML
    private Pane pnl_List;

    @FXML
    private Pane pnl_Category;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private Pane pnl_List_Product;

    @FXML
    private Pane pnl_Description;

    @FXML
    private HBox hbox_Controller;

    @FXML
    private Pane pnl_Controller;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Pane pnl_ReleaseDate;
    
    @FXML
    private Label lbl_OnSale;
    
    int row = 10;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        try {
            JFXDatePicker datePicker = new JFXDatePicker();
            datePicker.setDefaultColor(Paint.valueOf("lightblue"));
            JFXDatePicker datePicker1 = new JFXDatePicker();
            datePicker1.setDefaultColor(Paint.valueOf("lightblue"));
            datePicker.setStyle("-fx-text-fill: green");
            datePicker1.setStyle("-fx-text-fill: #ffffff ;");
            
            pnl_CreationDate.getChildren().add(datePicker);
            pnl_ReleaseDate.getChildren().add(datePicker1);
            
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
            double height = (paneP.getPrefHeight() + box_ListProduct.getSpacing())* row;
            box_ListProduct.setPrefSize(paneP.getPrefWidth(), height);
            pnl_List.setPrefHeight(height);
            box_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[row];
            for (int i = 0; i < row; i++) {
                final int h = i;
                nodes[h] = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
                box_ListProduct.getChildren().add(nodes[h]);
            }
            displayFormAnimation();
            
        } catch (Exception e) {
        }
    }
    void displayFormAnimation(){
        new FadeInDownBig(pnl_Basic_Info).play();
        new FadeInDown(pnl_Image).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Category).play();
        new FadeInLeftBig(pnl_Add_Info).play();
        new FadeInRightBig(pnl_List_Product).play();
        new FadeInUp(pnl_Status).play();
        new FadeInUpBig(pnl_Description).play();
        new ZoomInUp(pnl_Controller).play();
        GlowText ani = new GlowText(lbl_OnSale, Color.WHITE , Color.RED);
        ani.setCycleCount(Integer.MAX_VALUE);
        ani.play();
    }
}
