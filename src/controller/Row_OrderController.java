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

    void setInfo(Order entity,List<Account> listAccount) {
        
        Platform.runLater(() -> {
            double total = 0;         
            List<OrderDetail> list = new OrderDetailDAO().selectByOrderID(entity.getOrderID());
             String name ="";
            for (Account account : listAccount) {
                if(account.getAccountId()==entity.getAccountId()){
                    name =account.getName()==null? account.getUsername():account.getName();
                    break;
                }
            }
            for (OrderDetail orderDetail : list) {
                total += orderDetail.getPrice() * (100 - orderDetail.getSale());
            }
            total = (double) Math.round(total) / 100;

            lbl_ID.setText(entity.getOrderID() + "");
            lbl_CreationDate.setText(ProcessDate.toString(entity.getCreationDate()));
            lbl_NameCustomer.setText(entity.getAccountId() + "-" + name);
            lbl_Quantity.setText(list.size() + " Apps");
            lbl_Total.setText(total + "$");
            String status = entity.getStatus() == 0 ? "Processing" : entity.getStatus() == 1 ? "Accepted" : "Refunded";
            lblStatus.setText(status);

            Variable.END = Instant.now();
            Variable.TIMEELAPSED = Duration.between(Variable.START, Variable.END);
            System.out.println(Variable.TIMEELAPSED.toMillis());
        });
    }

    void setSelected(boolean isSelected) {
        if (isSelected) {
            pnl_Row_Order.setStyle("-fx-background-color: #185FEE ;");
        } else {
            pnl_Row_Order.setStyle("-fx-background-color: #2f2f2f ;");
        }
    }
}
