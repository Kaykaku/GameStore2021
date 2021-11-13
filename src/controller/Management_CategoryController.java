/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import animatefx.animation.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_CategoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pnl_List;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private Pane pnl_Game_Info;

    @FXML
    private Pane pnl_List_Product;

    @FXML
    private Pane pnl_Title;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Pane pnl_Table_Category;

    @FXML
    private VBox vbox_ListProduct;

    @FXML
    private Pane pnl_Image_Product;

    int row = 10;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
            double height = (paneP.getPrefHeight() + vbox_ListProduct.getSpacing())* row;
            vbox_ListProduct.setPrefSize(paneP.getPrefWidth(), height);
            pnl_List.setPrefHeight( height);
            vbox_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[row];
            for (int i = 0; i < row; i++) {
                final int h = i;
                nodes[h] = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
                vbox_ListProduct.getChildren().add(nodes[h]);
            }
            displayFormAnimation();
        } catch (Exception e) {
        }
    }    
    void displayFormAnimation(){
        new FadeInDown(pnl_Basic_Info).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Table_Category).play();
        new FadeInRightBig(pnl_List_Product).play();
        new FadeInUp(pnl_Image_Product).play();
        new FadeInUpBig(pnl_Game_Info).play();
    }
}
