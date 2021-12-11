/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import DAO.NewsDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.News;
import until.ProcessDate;
import until.ProcessImage;
import until.ProcessString;
import until.Value;
import static until.Value.FORM_DISPLAY_PRODUCT;
import until.Variable;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class News_BoxController implements Initializable {

    @FXML
    private ImageView img_Image;

    @FXML
    private Text lbl_Description;

    @FXML
    private Label lbl_Views;

    @FXML
    private Label lbl_Author;

    @FXML
    private Pane pnl_BoxNews;

    @FXML
    private Text lbl_Title;

    @FXML
    private Label lbl_CreationDay;

    News news;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnl_BoxNews.setOnMouseClicked((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_DISPLAY_NEWS));
                Node node = (Node) loader.load();
                DisplayNewsController controller = loader.getController();
                controller.setInfo(news);
                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        });
    }

    public void setInfo(News entity) {
        news = entity;
        
        if (entity.getImage() != null) {
            Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(entity.getImage()), null);
            img_Image.setImage(image);
            RoundedImageView.RoundedImage(img_Image, 20);
        }
        lbl_Title.setText(ProcessString.cutString(entity.getTitle(), 80));
        lbl_Description.setText(ProcessString.cutString(entity.getDescription(), 160));
        lbl_CreationDay.setText(ProcessDate.toString(entity.getCreationDate()));
        lbl_Views.setText(entity.getViews()+ "");
        lbl_Author.setText(new AccountDAO().selectByID(entity.getAccountId()).getName());
        
    }

    
}
