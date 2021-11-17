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
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.News;
import until.ProcessDate;
import until.ProcessImage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Row_NewsController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Label lbl_CreationDate;

    @FXML
    private ImageView img_Icon;

    @FXML
    private Text lbl_Content;

    @FXML
    private Label lbl_Views;

    @FXML
    private Text lbl_Title;

    @FXML
    private Label lbl_NewsID;
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    
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
     void setNewsInfor(News entity){
        lbl_Title.setText(entity.getTitle()+"");
        lbl_Content.setText(entity.getContents());
        lbl_NewsID.setText(""+entity.getNewsID());
        lbl_Views.setText(""+entity.getViews());
        lbl_CreationDate.setText(ProcessDate.toString(entity.getCreationDate()));
        if (entity.getImage()!=null) {
            img_Icon.setImage(new Image(ProcessImage.toFile(entity.getImage(), "appIcon.png").toURI().toString()));
            RoundedImageView.RoundedImage(img_Icon, 10);
        }
    }
    
}
