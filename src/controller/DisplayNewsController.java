/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import DAO.NewsDAO;
import animatefx.animation.ZoomInDown;
import animatefx.animation.ZoomOutDown;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.News;
import until.ProcessDate;
import until.ProcessImage;
import static until.Variable.PNL_VIEW;

/**
 *
 * @author Admin
 */
public class DisplayNewsController implements Initializable {

    @FXML
    private Pane pnl_Content;

    @FXML
    private Label lbl_CreationDate;

    @FXML
    private Text lbl_Content;

    @FXML
    private ImageView img_Image;

    @FXML
    private Label lbl_Views;

    @FXML
    private Text lbl_Description;

    @FXML
    private Pane pnl_BackGround;
    
    @FXML
    private Pane pnl;

    @FXML
    private Label lbl_Author;

    @FXML
    private Text lbl_Title;

    @FXML
    private JFXButton btn_Back;

    @FXML
    private ScrollPane pnl_MainScroll;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pnl_MainScroll.setPrefHeight(830);
        new ZoomInDown(pnl).play();
        btn_Back.setOnMouseClicked((evt) -> {
            new ZoomOutDown(pnl).play();
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException ex) {
                    }
                    Platform.runLater(() -> {
                        PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);
                    });
                }
            }.start();

        });
    }

    public void setInfo(News entity) {
        randomBg();
        entity.setViews(entity.getViews() + 1);
        new NewsDAO().updateViews(entity);
        lbl_Title.setText(entity.getTitle());
        lbl_Description.setText(entity.getDescription());
        lbl_Content.setText(entity.getContents());
        lbl_CreationDate.setText(ProcessDate.toString(entity.getCreationDate()));
        lbl_Views.setText(entity.getViews() + "");
        lbl_Author.setText(new AccountDAO().selectByID(entity.getAccountId()).getName());
        if (entity.getImage() != null) {
            Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(entity.getImage()), null);
            img_Image.setImage(image);
            RoundedImageView.RoundedImage(img_Image, 32);
        }
    }

    public void randomBg() {
        int rand = new Random().nextInt(5);
        String color = "";
        switch (rand) {
            case 0:
                color = "container";
                break;
            case 1:
                color = "container1";
                break;
            case 2:
                color = "container2";
                break;
            case 3:
                color = "container4";
                break;
            case 4:
                color = "container5";
                break;
            case 5:
                color = "container6";
                break;
        }
        pnl_BackGround.getStyleClass().add(color);
    }
}
