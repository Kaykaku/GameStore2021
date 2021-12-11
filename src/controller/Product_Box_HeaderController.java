/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.BoxChange;
import Animation.RoundedImageView;
import animatefx.animation.AnimateFXInterpolator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Application;
import until.ProcessImage;
import until.Value;
import until.Variable;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Product_Box_HeaderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pnl_Box;

    @FXML
    private ImageView img_Image;

    @FXML
    private Pane pnl_Blur;

    @FXML
    private Text lbl_Title;

    @FXML
    private Pane pnl_Bar;

    Application application;
    Timeline timeline;
    BoxChange ani;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_Image, 32);
        pnl_Box.setOnMouseClicked((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_DISPLAY_PRODUCT));
                Node node = (Node) loader.load();
                DisplayProductController controller = loader.getController();
                controller.setInformation(application);
                Variable.PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    Pane getBar() {
        return pnl_Bar;
    }

    Pane getBox() {
        return pnl_Blur;
    }

    Image getImage() {
        return img_Image.getImage();
    }

    void pause() {
        timeline.pause();
        ani.pause();
    }

    void play() {
        timeline.play();
        ani.play();
    }

    void setInfo(Application entity) {
        Platform.runLater(() -> {
            application = entity;
            if (entity.getAppImage() != null) {
                Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(entity.getAppImage()), null);
                img_Image.setImage(image);
                RoundedImageView.RoundedImage(img_Image, 32);
            }
            lbl_Title.setText(entity.getName());
        });
    }

    void setHover(boolean flag) {

        if (flag) {
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(0),
                            new KeyValue(pnl_Box.scaleXProperty(), 1, AnimateFXInterpolator.EASE)
                    ), new KeyFrame(Duration.millis(0),
                            new KeyValue(pnl_Box.scaleYProperty(), 1, AnimateFXInterpolator.EASE)
                    ), new KeyFrame(Duration.millis(0),
                            new KeyValue(pnl_Blur.opacityProperty(), 0, AnimateFXInterpolator.EASE)
                    ),
                     new KeyFrame(Duration.millis(2000),
                            new KeyValue(pnl_Box.scaleXProperty(), 1.1, AnimateFXInterpolator.EASE)
                    ), new KeyFrame(Duration.millis(2000),
                            new KeyValue(pnl_Blur.opacityProperty(), 1, AnimateFXInterpolator.EASE)
                    ), new KeyFrame(Duration.millis(2000),
                            new KeyValue(lbl_Title.opacityProperty(), 1, AnimateFXInterpolator.EASE)
                    ), new KeyFrame(Duration.millis(2000),
                            new KeyValue(pnl_Box.scaleYProperty(), 1.1, AnimateFXInterpolator.EASE)
                    ),
                     new KeyFrame(Duration.millis(4000),
                            new KeyValue(pnl_Box.scaleXProperty(), 1, AnimateFXInterpolator.EASE)
                    ), new KeyFrame(Duration.millis(4000),
                            new KeyValue(pnl_Box.scaleYProperty(), 1, AnimateFXInterpolator.EASE)
                    ), new KeyFrame(Duration.millis(4000),
                            new KeyValue(pnl_Blur.opacityProperty(), 0, AnimateFXInterpolator.EASE)
                    )
            );
            pnl_Bar.setOpacity(1);
            ani = new BoxChange(pnl_Bar);
            ani.play();
            timeline.play();
            pnl_Box.setStyle("-fx-border-color: #F38064;");
        } else {
            img_Image.setScaleX(1);
            img_Image.setScaleY(1);
            pnl_Box.setStyle("-fx-border-color: transparent;");

        }
    }
}
