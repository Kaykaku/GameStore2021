/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.GrowUp;
import Animation.MoveLeft;
import Animation.MoveRight;
import Animation.RoundedImageView;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Application;
import until.ProcessDate;
import until.ProcessImage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Row_ProductController implements Initializable {

    @FXML
    private Pane pnl_Row;

    @FXML
    private Pane pnl_MenuHide;

    @FXML
    private Label lbl_Size;

    @FXML
    private Pane pnl_MenuShow;

    @FXML
    private Label lbl_Id;

    @FXML
    private Label lbl_Name;

    @FXML
    private ImageView img_IconApp;

    @FXML
    private Label lbl_Price;

    @FXML
    private Label lbl_Sale;

    @FXML
    private Label lbl_RealeaseDate;
    /**
     * Initializes the controller class.
     */
    private boolean isShowOption = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_IconApp, 10);
        img_IconApp.setEffect(new DropShadow(5, Color.BLACK));
        pnl_Row.setOnMouseEntered(evt -> {
            new GrowUp(pnl_Row, 1.02).play();
        });
        pnl_Row.setOnMouseExited(evt -> {
            pnl_Row.setScaleX(1);
            pnl_Row.setScaleY(1);
        });
        pnl_MenuShow.setOnMouseClicked(evt -> {
            if (!isShowOption) {
                new MoveLeft(pnl_Row, pnl_MenuShow.getPrefWidth() - 10).play();
            } else {
                new MoveRight(pnl_Row, pnl_MenuShow.getPrefWidth() - 10).play();
            }
            isShowOption = !isShowOption;
        });
    }

    void setAppInfo(Application entity) {

        Platform.runLater(() -> {
            lbl_Id.setText(entity.getApplicationID() + "");
            lbl_Name.setText(entity.getName());
            double number = (double) Math.round(entity.getPrice() * 100) / 100;
            lbl_Price.setText(number == 0 ? "Free" : number + "$");
            lbl_RealeaseDate.setText(ProcessDate.toString(entity.getReleaseDay()));
            number = (double) Math.round(entity.getSale() * 100) / 100;
            lbl_Sale.setText(number + "%");
            number = (double) Math.round(entity.getSize() * 100) / 100;
            lbl_Size.setText(number + "Mb");
            if (entity.getAppIcon() != null) {
                img_IconApp.setImage(new Image(ProcessImage.toFile(entity.getAppIcon(),"a.png").toURI().toString()));
                RoundedImageView.RoundedImage(img_IconApp, 10);
            }
        });
    }

    void setSelected(boolean isSelected) {
        if (isSelected) {
            pnl_Row.setStyle("-fx-background-color: #185FEE ;");
        } else {
            pnl_Row.setStyle("-fx-background-color: #2f2f2f ;");
        }
    }

}
