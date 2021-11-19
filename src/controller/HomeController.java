/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import until.Value;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lbl_See_all ;
    @FXML
    private Pane pane1 ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbl_See_all.setOnMouseClicked(evt->{
            drawPanelView(Value.FORM_PRODUCT_LIST);          
        });
        pane1.setOnMouseClicked(evt->{
            drawPanelView(Value.FORM_DISPLAY_NEWS);          
        });
    }    
    void drawPanelView(String formView) {
        PNL_VIEW.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formView));
            Node node = (Node) loader.load();

            PNL_VIEW.getChildren().add(node);
        } catch (IOException ex) {
            //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
