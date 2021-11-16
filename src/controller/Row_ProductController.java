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
import java.net.URL;
import java.util.ResourceBundle;
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
    void setAppInfo(Application entity){
        lbl_Id.setText(entity.getApplicationID()+"");
        lbl_Name.setText(entity.getName());
        lbl_Price.setText(entity.getPrice()==0?"Free":entity.getPrice()+"$");
        lbl_RealeaseDate.setText(ProcessDate.toString(entity.getReleaseDay()));
        lbl_Sale.setText(entity.getSale()+"%");
        lbl_Size.setText(entity.getSize()+"Mb");
        if (entity.getAppIcon()!=null) {
            img_IconApp.setImage(new Image(ProcessImage.toFile(entity.getAppIcon(), "appIcon.png").toURI().toString()));
            RoundedImageView.RoundedImage(img_IconApp, 10);
        }
    }
}
