/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.MoveLeft;
import Animation.MoveRight;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class LoginController implements Initializable {

    @FXML
    private Pane pnl_Register;

    @FXML
    private JFXButton btn_Login;

    @FXML
    private Circle btn_bg1;

    @FXML
    private JFXButton btn_Login1;

    @FXML
    private Pane pnl_Login;

    @FXML
    private JFXButton btn_Login111;

    @FXML
    private JFXButton btn_Login11;

    @FXML
    private Button btn_Minimize;

    @FXML
    private Pane pnl_ChangePass;

    @FXML
    private ImageView img_bg1;

    @FXML
    private Button btn_Exit;

    @FXML
    private Label lbl_ForgotPassword;

    @FXML
    private JFXButton btn_Change;

    @FXML
    private Button btn_Back;

    /**
     * Initializes the controller class.
     */
    boolean isRegisterForm = false;
    boolean isChangePassForm = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_Change.setOnMouseClicked((evt) -> {
            if (!isRegisterForm) {
                if (isChangePassForm) {
                    new SlideInLeft(pnl_ChangePass).playOnFinished(new SlideInRight(pnl_Login)).play();
                    isChangePassForm = !isChangePassForm;
                }
                MoveLeft ani = new MoveLeft(img_bg1, pnl_Login.getPrefWidth());
                ani.play();
                btn_Change.setText("Login now");
                new SlideOutUp(pnl_Login).playOnFinished(new SlideOutDown(pnl_Register)).play();
            } else {
                MoveRight ani = new MoveRight(img_bg1, pnl_Login.getPrefWidth());
                ani.play();
                btn_Change.setText("Register now");

                new SlideInUp(pnl_Register).playOnFinished(new SlideInDown(pnl_Login)).play();
            }
            isRegisterForm = !isRegisterForm;
        });

        lbl_ForgotPassword.setOnMouseClicked((evt) -> {
            if (!isChangePassForm) {
                new SlideOutRight(pnl_Login).playOnFinished(new SlideOutLeft(pnl_ChangePass)).play();
                isChangePassForm = !isChangePassForm;
            }
        });
        btn_Back.setOnMouseClicked((evt) -> {
            new SlideInLeft(pnl_ChangePass).playOnFinished(new SlideInRight(pnl_Login)).play();
            isChangePassForm = !isChangePassForm;
        });
        btn_Exit.setOnMouseClicked((event) -> {
            try {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/gui/Main/GameStore.fxml"));
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
