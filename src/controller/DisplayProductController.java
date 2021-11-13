/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import static until.Value.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class DisplayProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView img_AppIcon;
    @FXML
    private JFXButton btn_Back;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_AppIcon, 12);
        
        btn_Back.setOnMouseClicked((evt) -> {
            PNL_VIEW.getChildren().remove(1);
        });
    }    
    
}
