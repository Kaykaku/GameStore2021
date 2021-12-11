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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Application;
import until.ProcessDate;
import until.ProcessImage;
import static until.Value.FORM_DISPLAY_PRODUCT;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Row_ProductController implements Initializable {

    @FXML
    private Pane pnl_Row;
    
    @FXML
    private Pane pnl_RowContent;

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
    
    @FXML
    private JFXButton btn_View;
    /**
     * Initializes the controller class.
     */
    private boolean isShowOption = false;
    Application application;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_IconApp, 10);
        img_IconApp.setEffect(new DropShadow(5, Color.BLACK));
        pnl_RowContent.setOnMouseEntered(evt -> {
            new GrowUp(pnl_RowContent, 1.02).play();
        });
        pnl_RowContent.setOnMouseExited(evt -> {
            pnl_RowContent.setScaleX(1);
            pnl_RowContent.setScaleY(1);
        });
        pnl_MenuHide.setOnMouseClicked(evt -> {
            if (!isShowOption) {
                new MoveLeft(pnl_RowContent, pnl_MenuShow.getPrefWidth() - 10).play();
            } else {
                new MoveRight(pnl_RowContent, pnl_MenuShow.getPrefWidth() - 10).play();
            }
            isShowOption = !isShowOption;
        });
        pnl_Row.setOnMouseExited(evt -> {
            if (isShowOption) {
                new MoveRight(pnl_RowContent, pnl_MenuShow.getPrefWidth() - 10).play();
                isShowOption = !isShowOption;
            }
            
        });
        btn_View.setOnMouseClicked((event) -> {
            //PNL_VIEW.getChildren().clear();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FORM_DISPLAY_PRODUCT));
                Node node = (Node) loader.load();
                DisplayProductController controller = loader.getController();
                controller.setInformation(application);
                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    void setAppInfo(Application entity) {
        application=entity;
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
                img_IconApp.setImage(ProcessImage.toImageFX(entity.getAppIcon()));
                RoundedImageView.RoundedImage(img_IconApp, 10);
            }
        });
    }

    void setSelected(boolean isSelected) {
        if (isSelected) {
            pnl_RowContent.setStyle("-fx-background-color: #185FEE ;");
        } else {
            pnl_RowContent.setStyle("-fx-background-color: #2f2f2f ;");
        }
    }
}
