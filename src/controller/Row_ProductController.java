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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Row_ProductController implements Initializable {

    @FXML
    private Pane pane1;
    @FXML
    private ImageView img_Icon;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    /**
     * Initializes the controller class.
     */
    private boolean isShowOption = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_Icon, 10);
        img_Icon.setEffect(new DropShadow(5, Color.BLACK));
        pane1.setOnMouseEntered(evt -> {
            new GrowUp(pane1, 1.02).play();
        });
        pane1.setOnMouseExited(evt -> {
            pane1.setScaleX(1);
            pane1.setScaleY(1);
        });
        pane3.setOnMouseClicked(evt -> {
            if (!isShowOption) {
                new MoveLeft(pane1, pane2.getPrefWidth() - 10).play();
            } else {
                new MoveRight(pane1, pane2.getPrefWidth() - 10).play();
            }
            isShowOption = !isShowOption;
        });
    }

}
