/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import DAO.StatisticsDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Account;
import until.Auth;
import until.ProcessDate;
import until.ProcessImage;
import until.Value;
import until.Variable;
import static until.Variable.PNL_VIEW;

/**
 *
 * @author Admin
 */
public class User_InformationController implements Initializable {

    @FXML
    private JFXButton btnChangepass;

    @FXML
    private TextField txt_Username;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private Label lbl_AppPurchased;

    @FXML
    private JFXButton btn_AboutUs;

    @FXML
    private JFXButton btn_QRcode;

    @FXML
    private Label lbl_AppViews;

    @FXML
    private Pane pnl_Image;

    @FXML
    private Button btn_ChosePicture;

    @FXML
    private Label lbl_Accountid;

    @FXML
    private JFXTextField txt_Email;

    @FXML
    private Pane pnl_LoginInfo;

    @FXML
    private JFXRadioButton rdo_Male;

    @FXML
    private ImageView img_Avatar;

    @FXML
    private JFXRadioButton rdo_Female;

    @FXML
    private JFXButton btn_Update;

    @FXML
    private Pane pnl_BirthDay;

    @FXML
    private JFXTextField txt_Name;

    @FXML
    private JFXButton btn_Back;

    @FXML
    private PasswordField txt_NewPassword;

    @FXML
    private Label lbl_AmountPaid;

    @FXML
    private Button btn_Camera;

    @FXML
    private Pane pnl_Statistics;

    @FXML
    private JFXButton btn_Contact;

    @FXML
    private VBox vbox_Controller;

    @FXML
    private PasswordField txt_ComfirmPassword;

    @FXML
    private Label lbl_Comments;

    @FXML
    private Label lbl_Ratings;

    @FXML
    private PasswordField txt_OldPassword;

    @FXML
    private Label lbl_TotalOders;

    @FXML
    private Label lbl_CreationDate;

    @FXML
    private JFXTextField txt_Address;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private JFXButton btn_SignOut;

    @FXML
    private JFXComboBox<String> cbo_Country;

    @FXML
    private Button btn_ClearAvatar;

    Account account = Auth.USER;
    private JFXDatePicker datePicker_CreationDate;
    private JFXDatePicker datePicker_Birthday;
    AccountDAO accountDAO = new AccountDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RoundedImageView.RoundedImage(img_Avatar, img_Avatar.getFitWidth());
        drawDatePicker();
        fillCboCountry();
        setGroupButton();
        setInformation();
        setEvent();
    }

    void setEvent() {
        btn_Back.setOnMouseClicked((event) -> {
            Variable.IS_ACCOUNT_INFORMATION_OPEN = false;
            PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);
        });
        btn_QRcode.setOnMouseClicked((event) -> {
            try {
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                Parent root = FXMLLoader.load(getClass().getResource(Value.DIALOG_CREATEQRCODE));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(User_InformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    void setInformation() {
        if (account.getImage() != null) {
            img_Avatar.setImage(new Image(ProcessImage.toFile(account.getImage(), "avatar.png").toURI().toString()));
            RoundedImageView.RoundedImage(img_Avatar, img_Avatar.getFitWidth());
        }
        txt_Username.setText(account.getUsername()+ "");
        txt_Username.setEditable(false);
        lbl_Accountid.setText(account.getAccountId() + "");
        txt_Name.setText(account.getName() + "");
        datePicker_Birthday.setValue(account.getBirthDay() != null ? ProcessDate.toLocalDate(account.getBirthDay()) : null);
        rdo_Female.setSelected(account.isGender());
        rdo_Male.setSelected(!account.isGender());
        cbo_Country.getSelectionModel().select(account.getCountry());
        txt_Email.setText(account.getEmail() + "");
        txt_Address.setText(account.getAddress() + "");
        lbl_CreationDate.setText(ProcessDate.toString(account.getCreationDate()));

        Object[] accStatics = new StatisticsDAO().getAccountStatistics(account.getAccountId());
        lbl_AppViews.setText(accStatics[0] + " Views");
        lbl_TotalOders.setText(accStatics[1] + " Orders");
        lbl_Ratings.setText(accStatics[2] + " Ratings");
        lbl_Comments.setText(accStatics[3] + " Comments");
        lbl_AppPurchased.setText(accStatics[4] + " Apps");
        double paid = accStatics[5] != null ? (double) Math.round((double) accStatics[5] * 100) / 100 : 0;
        lbl_AmountPaid.setText(paid + "$");
    }

    void drawDatePicker() {
        datePicker_Birthday = new JFXDatePicker();
        datePicker_Birthday.setDefaultColor(Paint.valueOf("lightblue"));
        datePicker_Birthday.setPrefWidth(250);

        pnl_BirthDay.getChildren().add(datePicker_Birthday);

        ProcessDate.converter(datePicker_Birthday);
    }

    void fillCboCountry() {
        List<String> list = new ArrayList<>();
        list.add("Vietnam");
        list.add("China");
        list.add("America");
        list.add("England");
        list.add("Japan");

        cbo_Country.setItems(FXCollections.observableArrayList(list));
    }

    void setGroupButton() {
        ToggleGroup grbutton = new ToggleGroup();
        rdo_Male.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Female.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Female.setToggleGroup(grbutton);
        rdo_Male.setToggleGroup(grbutton);
    }
}
