/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AppTypeDAO;
import DAO.ApplicationViewDAO;
import DAO.CategoryDAO;
import DAO.StatisticsDAO;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.AppType;
import model.Application;
import model.ApplicationView;
import until.ProcessImage;
import until.ProcessString;
import until.Value;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Product_Box_ShortController implements Initializable {

    @FXML
    private HBox Hbox_Star_View;

    @FXML
    private ImageView img_AppIcon;

    @FXML
    private Label lbl_Categories;

    @FXML
    private ImageView img_4star;

    @FXML
    private Text lbl_AppName;

    @FXML
    private Label lbl_Views;

    @FXML
    private ImageView img_5star;

    @FXML
    private Pane pnl_ProductBox_Short;

    @FXML
    private Label lbl_Price;

    @FXML
    private ImageView img_3star;

    @FXML
    private ImageView img_2star;

    @FXML
    private ImageView img_1star;

    /**
     * Initializes the controller class.
     */
    Application application;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbl_AppName.setText(ProcessString.cutString(lbl_AppName.getText(), 20));
        RoundedImageView.RoundedImage(img_AppIcon, 32);
    }

    void setInfo(Application entity) {
        application=entity;
        lbl_AppName.setText(ProcessString.cutString(entity.getName(), 20));
        List<AppType> listAppTypes = new AppTypeDAO().selectByApplicationId(entity.getApplicationID());
        lbl_Categories.setText(listAppTypes.size() > 1 ? new CategoryDAO().selectByID(listAppTypes.get(1).getCategoryId()).getName() : "All");
        lbl_Categories.setText(lbl_Categories.getText() + " " + (listAppTypes.size() > 2 ? "+" + (listAppTypes.size() - 1) : ""));
        lbl_Price.setText(entity.getPrice() == 0 ? "Free" : entity.getPrice() + "$");

        if(entity.getAppIcon()!=null){
            img_AppIcon.setImage(new Image(ProcessImage.toFile(entity.getAppIcon(), "icon.png").toURI().toString()));
            RoundedImageView.RoundedImage(img_AppIcon, 32);
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
        lbl_Views.setText(views+"");
        loadStar(ratings);
        averageRating = (double) Math.round(averageRating / ratings * 10) / 10;
        loadStar(averageRating);
    }
    void loadStar(double rate) {
        Image starFill = new Image(new File(Value.WSTAR_FILL).toURI().toString());
        Image starNot = new Image(new File(Value.WSTAR_REGULAR).toURI().toString());
        Image starHalf = new Image(new File(Value.WSTAR_HALF).toURI().toString());
        img_1star.setImage(starNot);
        img_2star.setImage(starNot);
        img_3star.setImage(starNot);
        img_4star.setImage(starNot);
        img_5star.setImage(starNot);
        if(rate>0.4){
            img_1star.setImage(starHalf);
        }
        else if(rate>1.4){
            img_2star.setImage(starHalf);
        }
        else if(rate>2.4){
            img_3star.setImage(starHalf);
        }
        else if(rate>3.4){
            img_4star.setImage(starHalf);
        }
        else if(rate>4.4){
            img_5star.setImage(starHalf);
        }
        switch ((int)rate) {
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
    }
}
