/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.PulseShort;
import Animation.RoundedImageView;
import DAO.StatisticsDAO;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Application;
import until.ProcessDate;
import until.ProcessImage;
import until.Value;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Row_WishlistController implements Initializable {

    @FXML
    private Label lbl_Developer;

    @FXML
    private Label lbl_OriginPrice;

    @FXML
    private Label lbl_ReleaseDate;

    @FXML
    private ImageView img_4star;

    @FXML
    private Pane pnl_Row;

    @FXML
    private Pane pnl_Row_Wishlist;

    @FXML
    private Label lbl_Type;

    @FXML
    private Label lbl_Price;

    @FXML
    private ImageView img_2star;

    @FXML
    private Pane pane2;

    @FXML
    private JFXButton btn_Delete;

    @FXML
    private Pane pane3;

    @FXML
    private HBox Hbox_Star_View;

    @FXML
    private ImageView img_AppIcon;

    @FXML
    private Label lbl_Name;

    @FXML
    private Label lbl_Views;

    @FXML
    private ImageView img_5star;

    @FXML
    private ImageView img_3star;

    @FXML
    private Label lbl_Sale;

    @FXML
    private ImageView img_1star;

    /**
     * Initializes the controller class.
     */
    int row = 10;
    Application application;
    double price;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_AppIcon, 32);
        setEvent();

    }

    double getPrice() {
        return price;
    }

    void setEvent() {
        PulseShort ani = new PulseShort(pnl_Row_Wishlist);
        ani.setCycleCount(Timeline.INDEFINITE);
        pnl_Row_Wishlist.setOnMouseEntered(evt -> {
            ani.play();
            pnl_Row_Wishlist.setEffect(new DropShadow(5, Color.BLACK));
            btn_Delete.setOpacity(1);
        });
        pnl_Row_Wishlist.setOnMouseExited(evt -> {
            ani.stop();
            pnl_Row_Wishlist.setScaleX(1);
            pnl_Row_Wishlist.setScaleY(1);
            pnl_Row.setEffect(new DropShadow(0, Color.BLACK));
            btn_Delete.setOpacity(0);
        });
    }

    Button getBtnDelete() {
        return btn_Delete;
    }

    public void setInfo(Application entity) {
        randomBg();
        application = entity;

        lbl_Name.setText(entity.getName() + "");
        double number = (double) Math.round(entity.getPrice() * (100-entity.getSale())) / 100;
        lbl_Price.setText(number == 0 ? "Free" : number + "$");
        lbl_Developer.setText(entity.getDeveloper() + "");
         number = (double) Math.round(entity.getPrice() * 100) / 100;
        lbl_OriginPrice.setText(number == 0 ? "Free" : number + "$");
        lbl_Sale.setText(entity.getSale() + "%");
        lbl_ReleaseDate.setText(ProcessDate.toString(entity.getReleaseDay()) + "");
        lbl_Type.setText(entity.isType() ? "Application" : "Game");
        price = (double) Math.round(entity.getPrice() * (100 - entity.getSale())) / 100;
        lbl_Price.setText(price + "$");

        if (entity.getAppIcon() != null) {
            Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(entity.getAppIcon()), null);
            img_AppIcon.setImage(image);
            RoundedImageView.RoundedImage(img_AppIcon, 32);
            img_AppIcon.setEffect(new DropShadow(5, Color.BLACK));
        }
        calculateAverageRating();

    }

    void calculateAverageRating() {
        double averageRating = 0;
        double ratings = 0;
        int views = 0;
        List<Object[]> listStarRatings = new StatisticsDAO().getRatingApp(application.getApplicationID());
        for (Object[] listObject : listStarRatings) {
            if ((int) listObject[0] != 0) {
                averageRating += (int) listObject[0] * (int) listObject[1];
                ratings += (int) listObject[1];
            }
            views += (int) listObject[1];
        }
        lbl_Views.setText(views + "");
        loadStar(ratings);
        averageRating = (double) Math.round(averageRating / ratings * 10) / 10;
        loadStar(averageRating);
    }

    void loadStar(double rate) {
        try {
            Image starFill = new Image(getClass().getResource(Value.WSTAR_FILL).toURI().toString());
            Image starNot = new Image(getClass().getResource(Value.WSTAR_REGULAR).toURI().toString());
            Image starHalf = new Image(getClass().getResource(Value.WSTAR_HALF).toURI().toString());
            img_1star.setImage(starNot);
            img_2star.setImage(starNot);
            img_3star.setImage(starNot);
            img_4star.setImage(starNot);
            img_5star.setImage(starNot);
            if (rate >= 4.4) {
                img_5star.setImage(starHalf);
            } else if (rate >= 3.4) {
                img_4star.setImage(starHalf);
            } else if (rate >= 2.4) {
                img_3star.setImage(starHalf);
            } else if (rate >= 1.4) {
                img_2star.setImage(starHalf);
            } else if (rate >= 0.4) {
                img_1star.setImage(starHalf);
            }
            switch ((int) Math.floor(rate)) {
                case 5:
                    img_5star.setImage(starFill);
                case 4:
                    img_4star.setImage(starFill);
                case 3:
                    img_3star.setImage(starFill);
                case 2:
                    img_2star.setImage(starFill);
                case 1:
                    img_1star.setImage(starFill);
            }
        } catch (Exception e) {
        }

    }

    public void randomBg() {
        int rand = new Random().nextInt(5);
        String color = "";
        switch (rand) {
            case 0:
                color = "-fx-background-color: linear-gradient( to right top,#69351F,#867324);";
                break;
            case 1:
                color = "-fx-background-color: linear-gradient( to right top,#000000,#3F2E4B,#906D5E);";
                break;
            case 2:
                color = "-fx-background-color: linear-gradient( to right top,#1D3111,#3F2E4B,#4F7226);";
                break;
            case 3:
                color = "-fx-background-color: linear-gradient( to right bottom,#69351F,#867324);";
                break;
            case 4:
                color = "-fx-background-color: linear-gradient( to right bottom,#000000,#3F2E4B,#906D5E);";
                break;
            case 5:
                color = "-fx-background-color: linear-gradient( to right bottom,#1D3111,#3F2E4B,#4F7226);";
                break;
        }

        pnl_Row.setStyle(color);
    }

    public void setOpacity() {
        pane3.setOpacity(0);
    }
}
