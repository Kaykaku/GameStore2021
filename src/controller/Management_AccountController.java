/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_AccountController implements Initializable {

    @FXML
    private Pane pnl_Table;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private Pane pnl_Table_Controller;

    @FXML
    private Pane pnl_TItle;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private Pane pnl_Status;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private Pane pnl_Image;

    @FXML
    private Pane pnl_ReleaseDate;

    @FXML
    private Pane pnl_Login_Info;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.setDefaultColor(Paint.valueOf("lightblue"));
        JFXDatePicker datePicker1 = new JFXDatePicker();
        datePicker1.setDefaultColor(Paint.valueOf("lightblue"));
        datePicker.setStyle("-fx-text-fill: green");
        datePicker1.setStyle("-fx-text-fill: #ffffff ;");
        datePicker.setPrefWidth(250);
        datePicker1.setPrefWidth(250);

        pnl_CreationDate.getChildren().add(datePicker);
        pnl_ReleaseDate.getChildren().add(datePicker1);
        displayFormAnimation();
    }
    
    void displayFormAnimation(){
        new FadeInDown(pnl_Basic_Info).play();
        new FadeInLeftBig(pnl_TItle).play();
        new FadeInLeft(pnl_Image).play();
        new FadeInLeft(pnl_Add_Info).play();
        new FadeInRight(pnl_Login_Info).play();
        new FadeInRightBig(pnl_Table_Controller).play();
        new ZoomIn(pnl_Status).play();
        new FadeInUpBig(pnl_Table).play();
    }
}
