/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.OrderDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Application;
import model.Order;
import until.Auth;
import until.ProcessDate;
import until.ProcessImage;
import until.ProcessString;
import static until.Value.FORM_DISPLAY_PRODUCT;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Library_Product_BoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView img_AppImage;

    @FXML
    private Label lbl_AppName;

    @FXML
    private Label lbl_DatePurchased;

    @FXML
    private JFXButton btn_ViewDetails;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void setInfo(Application application){
        if(application.getAppIcon()!=null){
            img_AppImage.setImage(new Image(ProcessImage.toFile(application.getAppIcon(), "largeIcon.png").toURI().toString()));
            
        }
        lbl_AppName.setText(application.getName());
        Order order = new OrderDAO().selectByAccountAppID(Auth.USER.getAccountId(), application.getApplicationID());
        lbl_DatePurchased.setText("Purchased since "+ProcessDate.toString(order.getCreationDate()));
        
        btn_ViewDetails.setOnMouseClicked(evt -> {
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
    }
}
