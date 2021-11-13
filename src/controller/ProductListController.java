/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import static until.Value.*;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ProductListController implements Initializable {

    @FXML
    private GridPane list;
    @FXML
    private Pane pane;

    @FXML
    private ScrollPane pnl_ScrollList;
       
    @FXML
    private Pane pnl_Title;
    @FXML
    private Pane pnl_Title_In;
    /**
     * Initializes the controller class.
     */
    double space = 30;
    double row = 4;
    double col = 4;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            pnl_ScrollList.setPrefSize(WIDTH_VIEW,HEIGHT_VIEW);
            Pane product = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Product_Box.fxml"));
            list.getChildren().clear();
            list.setHgap(space);
            list.setVgap(space);
            list.setPrefSize(product.getPrefWidth() * row + space * row + 20, product.getPrefHeight() * col + space * col + 20);
            pane.setPrefSize(WIDTH_VIEW-15, product.getPrefHeight() * col + space * col + 20+590);           
            list.setPadding(new Insets(space, space, space, space));
            list.setLayoutX(list.getLayoutX() + (WIDTH_VIEW - list.getPrefWidth()) / 2);
            Node[] nodes = new Node[16];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    final int h = i * 4 + j;
                    nodes[h] = (Node) FXMLLoader.load(getClass().getResource("/gui/Item/Product_Box.fxml"));
                    list.add(nodes[h], i, j);
                }
            }
            
            pane.setOnScroll((event) -> {
                if(pnl_ScrollList.getVvalue()>0.37){
                    pnl_Title.setOpacity(1);
                    pnl_Title_In.setOpacity(0);
                }else{
                    pnl_Title.setOpacity(0);
                    pnl_Title_In.setOpacity(1);
                }
            });
            pane.setOnMouseMoved((event) -> {
                if(pnl_ScrollList.getVvalue()>0.37){
                    pnl_Title.setOpacity(1);
                    pnl_Title_In.setOpacity(0);
                }else{
                    pnl_Title.setOpacity(0);
                    pnl_Title_In.setOpacity(1);
                }
            });
        } catch (IOException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
