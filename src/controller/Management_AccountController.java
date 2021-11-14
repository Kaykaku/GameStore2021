/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_AccountController implements Initializable {

    @FXML
    private TableView<?> tbl_Accounts;

    @FXML
    private TextField txt_Sreach;

    @FXML
    private JFXToggleButton tog_EnableComment;

    @FXML
    private JFXButton btn_SendMail;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private JFXButton btn_SendMessage;

    @FXML
    private Pane pnl_Image;

    @FXML
    private Pane pnl_Login_Info;

    @FXML
    private Label lbl_AccountID;

    @FXML
    private JFXTextField txt_Email;

    @FXML
    private JFXRadioButton rdo_Male;

    @FXML
    private JFXButton btn_Add;

    @FXML
    private TextField txt_UserName;

    @FXML
    private JFXButton btn_Delete;

    @FXML
    private Pane pnl_Table_Controller;

    @FXML
    private ImageView img_Avatar;

    @FXML
    private JFXRadioButton rdo_Female;

    @FXML
    private JFXButton btn_Update;

    @FXML
    private Pane pnl_Table;

    @FXML
    private PasswordField txt_NewPassword;

    @FXML
    private Pane pnl_Status;

    @FXML
    private JFXToggleButton tog_Active;

    @FXML
    private JFXToggleButton tog_Role;

    @FXML
    private PasswordField txt_ComfirmPassword;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private JFXButton btn_Clear;

    @FXML
    private JFXButton btn_ChangePass;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private JFXTextField txt_Address;

    @FXML
    private Pane pnl_TItle;

    @FXML
    private Pane pnl_ReleaseDate;

    @FXML
    private JFXComboBox<?> cbo_Country;

    JFXDatePicker dpr_CreationDate;
    JFXDatePicker dpr_ReleaseDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawDatePicker();
        setEvent();
        displayFormAnimation();
    }

    void drawDatePicker() {
        dpr_CreationDate = new JFXDatePicker();
        dpr_CreationDate.setDefaultColor(Paint.valueOf("lightblue"));
        dpr_ReleaseDate = new JFXDatePicker();
        dpr_ReleaseDate.setDefaultColor(Paint.valueOf("lightblue"));
        dpr_CreationDate.setPrefWidth(250);
        dpr_ReleaseDate.setPrefWidth(250);

        pnl_CreationDate.getChildren().add(dpr_CreationDate);
        pnl_ReleaseDate.getChildren().add(dpr_ReleaseDate);
    }
    void displayFormAnimation() {
        new FadeInDown(pnl_Basic_Info).play();
        new FadeInLeftBig(pnl_TItle).play();
        new FadeInLeft(pnl_Image).play();
        new FadeInLeft(pnl_Add_Info).play();
        new FadeInRight(pnl_Login_Info).play();
        new FadeInRightBig(pnl_Table_Controller).play();
        new ZoomIn(pnl_Status).play();
        new FadeInUpBig(pnl_Table).play();
    }
    void setEvent(){
        
    }
}
