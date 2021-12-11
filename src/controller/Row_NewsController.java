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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.News;
import until.ProcessDate;
import until.ProcessImage;
import until.ProcessString;
import until.Value;
import static until.Variable.PNL_VIEW;

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
    private Pane pnl_RowContent;

    @FXML
    private Pane pnl_MenuShow;
    
    @FXML
    private Pane pnl_Row;

    @FXML
    private Label lbl_CreationDate;

    @FXML
    private Pane pnl_MenuHide;

    @FXML
    private JFXButton btn_View;

    @FXML
    private ImageView img_Icon;

    @FXML
    private Text lbl_Content;

    @FXML
    private Label lbl_NewsID;

    @FXML
    private Label lbl_Views;

    @FXML
    private Text lbl_Title;

    private boolean isShowOption = false;
    News news;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_Icon, 10);
        img_Icon.setEffect(new DropShadow(5, Color.BLACK));
        pnl_RowContent.setOnMouseEntered(evt -> {
            new GrowUp(pnl_Row, 1.02).play();
        });
        pnl_RowContent.setOnMouseExited(evt -> {
            pnl_Row.setScaleX(1);
            pnl_Row.setScaleY(1);
        });
        pnl_MenuShow.setOnMouseClicked(evt -> {
            if (!isShowOption) {
                new MoveLeft(pnl_RowContent, pnl_MenuHide.getPrefWidth() - 10).play();
            } else {
                new MoveRight(pnl_RowContent, pnl_MenuHide.getPrefWidth() - 10).play();
            }
            isShowOption = !isShowOption;
        });
        pnl_Row.setOnMouseExited(evt -> {
            if (isShowOption) {
                new MoveRight(pnl_RowContent, pnl_MenuHide.getPrefWidth() - 10).play();
                isShowOption = !isShowOption;
            }
            
        });
        btn_View.setOnMouseClicked((event) -> {
            //PNL_VIEW.getChildren().clear();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_DISPLAY_NEWS));
                Node node = (Node) loader.load();
                DisplayNewsController controller = loader.getController();
                controller.setInfo(news);
                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    void setNewsInfor(News entity) {
        news=entity;
        Platform.runLater(() -> {
            lbl_Title.setText(ProcessString.cutString(entity.getTitle(), 100));
            lbl_Content.setText(ProcessString.cutString(entity.getDescription(), 130));
            lbl_NewsID.setText("" + entity.getNewsID());
            lbl_Views.setText("" + entity.getViews());
            lbl_CreationDate.setText(ProcessDate.toString(entity.getCreationDate()));
            if (entity.getImage() != null) {
                img_Icon.setImage(ProcessImage.toImageFX(entity.getImage()));
                RoundedImageView.RoundedImage(img_Icon, 10);
            }
        });
    }

}
