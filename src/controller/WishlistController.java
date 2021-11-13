/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
public class WishlistController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pnl_List;

    @FXML
    private Pane pnl_Title;

    @FXML
    private Pane pnl_Order_Info;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private VBox vbox_ListProduct;
    
    int row=10;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Wishlist.fxml"));
            double height = (paneP.getPrefHeight() +vbox_ListProduct.getSpacing())* row;
            pnl_List.setPrefHeight(height );
            vbox_ListProduct.setPrefSize(paneP.getPrefWidth(), height);
            
            vbox_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[row];
            for (int i = 0; i < row; i++) {
                final int h = i;
                nodes[h] = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Wishlist.fxml"));
                vbox_ListProduct.getChildren().add(nodes[h]);
            }
        } catch (Exception e) {
        }
    }    
    
}
