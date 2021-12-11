/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.BoxHoverAni;
import Animation.RoundedImageView;
import DAO.AppTypeDAO;
import DAO.CategoryDAO;
import DAO.StatisticsDAO;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.AppType;
import model.Application;
import until.ProcessImage;
import until.ProcessString;
import until.Value;
import static until.Value.FORM_DISPLAY_PRODUCT;
import static until.Variable.PNL_VIEW;

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
    @FXML
    private Label lbl_CategoriesCount;
    /**
     * Initializes the controller class.
     */
    Application application;
    CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbl_AppName.setText(ProcessString.cutString(lbl_AppName.getText(), 20));
        RoundedImageView.RoundedImage(img_AppIcon, 32);
        pnl_ProductBox_Short.setOnMouseClicked(evt -> {
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
        pnl_ProductBox_Short.setOnMouseEntered(evt -> {
            new BoxHoverAni(pnl_ProductBox_Short).play();
            pnl_ProductBox_Short.setEffect(new DropShadow(5, Color.BLACK));
        });
        pnl_ProductBox_Short.setOnMouseExited(evt -> {
            pnl_ProductBox_Short.setTranslateY(0);
            pnl_ProductBox_Short.setEffect(new DropShadow(0, Color.BLACK));
        });
    }

    void setInfo(Application entity) {
//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
        Platform.runLater(() -> {
            application = entity;
            double number;
            lbl_AppName.setText(ProcessString.cutString(entity.getName(), 30));
            List<AppType> listAppTypes = new AppTypeDAO().selectByApplicationId(entity.getApplicationID());
            lbl_Categories.setText(listAppTypes.size() > 1 ? categoryDAO.selectByID(listAppTypes.get(1).getCategoryId()).getName() : "All");
            lbl_CategoriesCount.setText(listAppTypes.size() > 2 ? "+" + (listAppTypes.size() - 1) : "");
            number = (double) Math.round(entity.getPrice() * 100) / 100;
            lbl_Price.setText("$"+(number == 0 ? "Free" : number ));
            if (entity.getAppIcon() != null) {
                Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(entity.getAppIcon()), null);
                img_AppIcon.setImage(image);
                RoundedImageView.RoundedImage(img_AppIcon, 32);
            }
            calculateAverageRating();
        });
//                return null;
//            }
//        };
//        Thread t = new Thread(task);
//        t.setDaemon(true);
//        t.start();

    }

    void setCategory(int cate) {
        Platform.runLater(() -> {
            if (cate == -1) {
                return;
            }
            lbl_Categories.setText(categoryDAO.selectByID(cate).getName());
        });
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
            switch ((int) rate) {
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
        } catch (URISyntaxException ex) {
            //Logger.getLogger(Product_Box_ShortController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
