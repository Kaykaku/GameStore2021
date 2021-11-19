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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import Animation.BoxHoverAni;
import Animation.RoundedImageView;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import static until.Value.*;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ProductBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView img_App;

    @FXML
    private HBox Hbox_Star_View;

    @FXML
    private Label lbl_Categories;

    @FXML
    private Label lbl_Name;

    @FXML
    private Label lbl_Price;

    @FXML
    private Pane pnl_ProductBox;
    @FXML
    private Pane pnl_Row_Order;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_App, 10);
        img_App.setEffect(new DropShadow(5, Color.BLACK));
        pnl_ProductBox.setOnMouseEntered(evt -> {
            new BoxHoverAni(pnl_ProductBox).play();
            pnl_ProductBox.setEffect(new DropShadow(5, Color.BLACK));
        });
        pnl_ProductBox.setOnMouseExited(evt -> {
            pnl_ProductBox.setTranslateY(0);
            pnl_ProductBox.setEffect(new DropShadow(0, Color.BLACK));
        });
        pnl_ProductBox.setOnMouseClicked(evt -> {
            //PNL_VIEW.getChildren().clear();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FORM_DISPLAY_PRODUCT));
                Node node = (Node) loader.load();

                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
