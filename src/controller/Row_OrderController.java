/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.BoxHoverAni;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Row_OrderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Pane pnl_Row_Order;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnl_Row_Order.setOnMouseEntered(evt -> {
            new BoxHoverAni(pnl_Row_Order).play();
            pnl_Row_Order.setEffect(new DropShadow(5, Color.BLACK));
        });
        pnl_Row_Order.setOnMouseExited(evt -> {
            pnl_Row_Order.setTranslateY(0);
            pnl_Row_Order.setEffect(new DropShadow(0, Color.BLACK));
        });
    }    
    
}
