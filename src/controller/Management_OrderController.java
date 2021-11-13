/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import animatefx.animation.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_OrderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton bt2;

    @FXML
    private Pane pnl_List_Controller;

    @FXML
    private ScrollPane pnl_ScrollList1;

    @FXML
    private Pane pnl_Title;
    
    @FXML
    private Pane pnl_Table2;
    
    @FXML
    private Pane pnl_Order_Info;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private VBox box_ListProduct;

    @FXML
    private Pane pnl_List1;

    @FXML
    private Pane pnl_Tabs;

    @FXML
    private Pane pnl_List;

    @FXML
    private GridPane box_ListProduct1;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Pane pnl_OrderDetail_Info;

    @FXML
    private JFXButton bt1;
    int row = 12;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Order.fxml"));
            box_ListProduct.setPrefSize(paneP.getPrefWidth(), paneP.getPrefHeight() * row);
            pnl_List.setPrefSize(pnl_ScrollList.getPrefWidth() - 15, paneP.getPrefHeight() * row);
            box_ListProduct.setLayoutX((pnl_List.getPrefWidth() - box_ListProduct.getPrefWidth()) / 2);
            box_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[row];
            for (int i = 0; i < row; i++) {
                final int h = i;
                nodes[h] = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Order.fxml"));
                box_ListProduct.getChildren().add(nodes[h]);
            }

            Pane paneK = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
            box_ListProduct1.setPrefSize(paneP.getPrefWidth(), paneP.getPrefHeight() * row);
            pnl_List1.setPrefSize(pnl_ScrollList1.getPrefWidth() - 15, paneP.getPrefHeight() * row);
            //box_ListProduct1.setLayoutX((pnl_List1.getPrefWidth()-box_ListProduct1.getPrefWidth())/2);
            box_ListProduct1.getChildren().clear();
            Pane[] nodes1 = new Pane[row];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < row / 2; j++) {
                    final int h = i * 2 + i;
                    nodes1[h] = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
                    box_ListProduct1.add(nodes1[h], i, j);
                }
            }
            bt2.setOnMouseClicked(evt -> {
                FadeOutDown ani = new FadeOutDown(pnl_ScrollList);
                ani.playOnFinished(new FadeInUp(pnl_Table2));
                ani.play();
                bt1.setDisable(false);
                bt2.setDisable(true);
            });
            bt1.setOnMouseClicked(evt -> {
                FadeOutDown ani = new FadeOutDown(pnl_Table2);
                ani.playOnFinished(new FadeInUp(pnl_ScrollList)).play();
                bt2.setDisable(false);
                bt1.setDisable(true);
            });
            displayFormAnimation();
        } catch (Exception e) {
        }
    }

    void displayFormAnimation() {
        new FadeInDown(pnl_Order_Info).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Tabs).play();
        new FadeInRightBig(pnl_OrderDetail_Info).play();
        new FadeInUpBig(pnl_ScrollList).play();
        new ZoomIn(pnl_List_Controller).play();
    }
}
