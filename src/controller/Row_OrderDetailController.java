/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.MoveLeft;
import Animation.MoveRight;
import Animation.PulseShort;
import Animation.RoundedImageView;
import DAO.ApplicationDAO;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Application;
import model.OrderDetail;
import until.ProcessImage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Row_OrderDetailController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pnl_Row;

    @FXML
    private Pane pnl_MenuHide;

    @FXML
    private Label lbl_Id;

    @FXML
    private Label lbl_Code;

    @FXML
    private Label lbl_Name;

    @FXML
    private ImageView img_IconApp;

    @FXML
    private Label lbl_Price;

    @FXML
    private JFXButton btn_Delete;

    @FXML
    private Label lbl_Sale;
    boolean isShowOption = false;
    ApplicationDAO dao = new ApplicationDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_IconApp, 10);
        img_IconApp.setEffect(new DropShadow(5, Color.BLACK));
        PulseShort ani = new PulseShort(pnl_Row);
        ani.setCycleCount(Integer.MAX_VALUE);
        pnl_Row.setOnMouseEntered(evt -> {
            //new GrowUp(pnl_Row, 1.02).play();
            ani.play();
            btn_Delete.setOpacity(1);
        });
        pnl_Row.setOnMouseExited(evt -> {
            pnl_Row.setScaleX(1);
            pnl_Row.setScaleY(1);
            btn_Delete.setOpacity(0);
            ani.stop();
        });
        pnl_MenuHide.setOnMouseClicked(evt -> {
            if (!isShowOption) {
                new MoveLeft(pnl_Row, pnl_Row.getPrefWidth() - 10).play();
            } else {
                new MoveRight(pnl_Row, pnl_Row.getPrefWidth() - 10).play();
            }
            isShowOption = !isShowOption;
        });
    }

    void setOrderDetailInfo(OrderDetail entity) {
        Platform.runLater(() -> {
            Application app = dao.selectByID(entity.getApplicationId());
            lbl_Id.setText(entity.getApplicationId() + "");
            lbl_Name.setText(app.getName());
            double price = (double) Math.round(entity.getPrice() * (100 - entity.getSale())) / 100;
            lbl_Price.setText(price == 0 ? "Free" : "*" + price + "$");
            lbl_Sale.setText(entity.getSale() + "%");
            if (app.getAppIcon() != null) {
                img_IconApp.setImage(ProcessImage.toImageFX(app.getAppIcon()));
                RoundedImageView.RoundedImage(img_IconApp, 10);
            }
            lbl_Code.setText(entity.getDiscountCode());
        });
    }

    void setSelected(boolean isSelected) {
        if (isSelected) {
            pnl_Row.setStyle("-fx-background-color: #185FEE ;");
        } else {
            pnl_Row.setStyle("-fx-background-color: #2f2f2f ;");
        }
    }

    JFXButton getButtonDelete() {
        return btn_Delete;
    }
}
