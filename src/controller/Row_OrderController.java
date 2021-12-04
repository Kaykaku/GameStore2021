/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.BoxHoverMoveBack;
import DAO.AccountDAO;
import DAO.OrderDetailDAO;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Account;
import model.Order;
import model.OrderDetail;
import until.ProcessDate;
import until.Variable;

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
    private Label lbl_CreationDate;

    @FXML
    private Label lbl_NameCustomer;

    @FXML
    private Label lbl_Quantity;

    @FXML
    private Label lbl_ID;

    @FXML
    private Label lbl_Total;

    @FXML
    private Pane pnl_Row_Order;

    @FXML
    private Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnl_Row_Order.setOnMouseEntered(evt -> {
            new BoxHoverMoveBack(pnl_Row_Order).play();
            pnl_Row_Order.setEffect(new DropShadow(5, Color.BLACK));
        });
        pnl_Row_Order.setOnMouseExited(evt -> {
            pnl_Row_Order.setTranslateY(0);
            pnl_Row_Order.setScaleY(1);
            pnl_Row_Order.setEffect(new DropShadow(0, Color.BLACK));
        });
    }

    void setInfo(Object[] objs) {                   
            lbl_ID.setText(objs[0].toString());
            lbl_CreationDate.setText(ProcessDate.toString((Date)objs[2]));
            lbl_NameCustomer.setText(objs[3].toString());
            lbl_Quantity.setText(objs[4].toString());
            double number =objs[5]==null?0: (double) Math.round((double)objs[5] *100)/100;
            lbl_Total.setText(number + "$");
            String status = (int) objs[6] == 0 ? "Processing" :(int) objs[6] == 1 ? "Accepted" : "Refunded";
            lblStatus.setText(status);
    }

    void setSelected(boolean isSelected) {
        if (isSelected) {
            pnl_Row_Order.setStyle("-fx-background-color: #185FEE ;");
        } else {
            pnl_Row_Order.setStyle("-fx-background-color: #2f2f2f ;");
        }
    }
}
