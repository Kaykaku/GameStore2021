/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Category_ItemController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ImageView img_delete;

    @FXML
    private Label lbl_category;
    @FXML
    private Pane pnl_backgr;
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        lbl_category.setText("abcvcvcc");
        pnl_backgr.setStyle("-fx-background-color: aqua;");
        pnl_backgr.setPrefWidth(lbl_category.getPrefWidth());
        System.out.println(lbl_category.getPrefWidth());
        lbl_category.setLayoutX(15);
        img_delete.setVisible(false);
        //img_delete.setLayoutX(pnl_backgr.getPrefWidth());
        System.out.println(pnl_backgr.getPrefWidth());
    }    
    
}
