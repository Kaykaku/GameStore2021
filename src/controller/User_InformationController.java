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
import static controller.MainController.static_Email_Hide;
import static controller.MainController.static_Icon_Medium;
import static controller.MainController.static_UserName;
import static controller.MainController.static_UserName_Hide;
import static controller.MainController.static_User_Icon_Small;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
import javafx.scene.Node;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Account;
import until.Auth;
import until.Dialog;
import until.ProcessDate;
import until.ProcessImage;
import until.Validation;
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
    private JFXButton btn_Logout;

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
    private JFXButton btn_QRcode;

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
    private JFXComboBox<String> cbo_Country;

    @FXML
    private Button btn_ClearAvatar;

    Account account = Auth.USER;
    private JFXDatePicker datePicker_CreationDate;
    private JFXDatePicker datePicker_Birthday;
    AccountDAO accountDAO = new AccountDAO();
    String err = "";
    File avataImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RoundedImageView.RoundedImage(img_Avatar, img_Avatar.getFitWidth());
        txt_Name.setEditable(true);
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
        btn_Camera.setOnMouseClicked((event) -> {
            try {
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                Parent root = FXMLLoader.load(getClass().getResource(Value.DIALOG_TAKEPICTURE));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (Variable.AVATAR != null) {
                avataImage = ProcessImage.toFile(Variable.AVATAR);
                setAvatar();
            }
        });
        btn_Update.setOnMouseClicked(event -> {
            this.UpdateInfor();
            loadUserInfor();
        });
        btn_ChosePicture.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            avataImage = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
            if (avataImage != null) {
                setAvatar();
            }
        });
        btn_ClearAvatar.setOnMouseClicked(event -> {
            clearAvata();
        });
        rdo_Male.setOnAction(event -> {
            setAvatar();
        });
        rdo_Female.setOnAction(event -> {
            setAvatar();
        });
        btnChangepass.setOnMouseClicked(event -> {
            this.update_Password();
        });
        btn_Logout.setOnMouseClicked((event) -> {
            try {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource(Value.FORM_LOGIN));
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image(new File(Value.ICON_APP).toURI().toString()));
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(LoginController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            Auth.USER = null;
        });
        btn_Contact.setOnMouseClicked((event) -> {
            try {
                openWebpage(new URL("https://mail.google.com/"));
            } catch (MalformedURLException ex) {

            }
        });
    }

    void clearAvata() {
        img_Avatar.setImage(new Image(new File(rdo_Female.isSelected() ? "src/icons/female256.png" : "src/icons/male256.png").toURI().toString()));
        avataImage = ProcessImage.toFile(img_Avatar.getImage());
        RoundedImageView.RoundedImage(img_Avatar, img_Avatar.getFitWidth());
    }

    void loadUserInfor() {
        if (Auth.USER.getImage() != null) {
            static_User_Icon_Small.setImage(new Image(ProcessImage.toFile(Auth.USER.getImage(), "smallAvatar.png").toURI().toString()));
            static_Icon_Medium.setImage(new Image(ProcessImage.toFile(Auth.USER.getImage(), "smallAvatar.png").toURI().toString()));
            RoundedImageView.RoundedImage(static_User_Icon_Small, 35);
            RoundedImageView.RoundedImage(static_Icon_Medium, static_Icon_Medium.getFitWidth());
        }
        static_UserName.setText(Auth.USER.getUsername());
        static_UserName_Hide.setText(Auth.USER.getName().isEmpty()?Auth.USER.getUsername():Auth.USER.getName());
        static_Email_Hide.setText(Auth.USER.getEmail());
    }

    void setAvatar() {
        if (avataImage != null) {
            img_Avatar.setImage(new Image(avataImage.toURI().toString()));
        } else {
            img_Avatar.setImage(new Image(new File(rdo_Female.isSelected() ? "src/icons/female256.png" : "src/icons/male256.png").toURI().toString()));
        }
        RoundedImageView.RoundedImage(img_Avatar, img_Avatar.getFitWidth());
    }

    Account getModel() {
        Account acc = new Account();
        acc.setPassword(txt_NewPassword.getText());
        acc.setAccountId(Auth.USER.getAccountId());
        return acc;

    }

    void Clear() {
        txt_OldPassword.setText("");
        txt_NewPassword.setText("");
        txt_ComfirmPassword.setText("");
    }

    void update_Password() {
        String errP = "";
        errP += Validation.validationConfirmPassword(txt_NewPassword, txt_ComfirmPassword);
        errP += Validation.validationOld_NewPass(txt_OldPassword, txt_NewPassword);
        if (errP.isEmpty()) {
            if (txt_OldPassword.getText().equals(Auth.USER.getPassword())) {
                Account entity = getModel();
                accountDAO.updatePass(entity);
                Auth.USER.setPassword(txt_ComfirmPassword.getText());
                Dialog.showMessageDialog("Notice!", "Changed Pass successfully!");
                this.Clear();
            } else if (!txt_OldPassword.getText().equals(Auth.USER.getPassword())) {
                Dialog.showMessageDialog("Error", "Incorrect password!");
                txt_OldPassword.requestFocus();
            }
        } else {
            Dialog.showMessageDialog("Wrong data", errP);
        }
    }

    void setInformation() {
        if (account.getImage() != null) {
            avataImage = new File("photo/avatar.png");
            img_Avatar.setImage(new Image(ProcessImage.toFile(account.getImage(), "avatar.png").toURI().toString()));
            RoundedImageView.RoundedImage(img_Avatar, img_Avatar.getFitWidth());
        }
        txt_Username.setText(account.getUsername() + "");
        txt_Username.setEditable(false);
        lbl_Accountid.setText(account.getAccountId() + "");
        txt_Name.setText(account.getName()==null?"":account.getName());
        datePicker_Birthday.setValue(account.getBirthDay() != null ? ProcessDate.toLocalDate(account.getBirthDay()) : null);
        rdo_Female.setSelected(account.isGender());
        rdo_Male.setSelected(!account.isGender());
        cbo_Country.getSelectionModel().select(account.getCountry());
        txt_Email.setText(account.getEmail() + "");
        txt_Address.setText(account.getAddress()==null?"":account.getAddress());
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

    Account getForm() {
        err="";
        err += Validation.validationEmail(txt_Email);
        err += Validation.validationBirthDay(datePicker_Birthday);
        if (err.isEmpty()) {
            Account entity = new Account();
            entity.setAccountId(Auth.USER.getAccountId());
            entity.setName(txt_Name.getText().trim());
            entity.setBirthDay(ProcessDate.toDate(datePicker_Birthday.getValue()));
            entity.setGender(rdo_Female.isSelected());
            entity.setCountry(cbo_Country.getSelectionModel().getSelectedItem());
            entity.setEmail(txt_Email.getText().trim());
            entity.setAddress(txt_Address.getText() != null ? txt_Address.getText().trim() : "");
            entity.setImage(ProcessImage.toBytes(new File(rdo_Female.isSelected() ? "src/icons/female256.png" : "src/icons/male256.png")));
            if(avataImage!=null){
                entity.setImage(ProcessImage.toBytes(avataImage));
            }
            return entity;
        }
        Dialog.showMessageDialog("Wrong data", err);
        return null;
    }

    void updateAuInfor() {
        Auth.USER.setName(txt_Name.getText());
        Auth.USER.setBirthDay(ProcessDate.toDate(datePicker_Birthday.getValue()));
        Auth.USER.setGender(rdo_Female.isSelected());
        Auth.USER.setCountry(cbo_Country.getSelectionModel().getSelectedItem());
        Auth.USER.setEmail(txt_Email.getText().trim());
        Auth.USER.setAddress(txt_Address.getText() != null ? txt_Address.getText().trim() : "");
        if (avataImage != null) {
            Auth.USER.setImage(ProcessImage.toBytes(avataImage));

        } else {
            Auth.USER.setImage(ProcessImage.toBytes(new File(rdo_Female.isSelected() ? "src/icons/female256.png" : "src/icons/male256.png")));
        }
    }

    private void UpdateInfor() {
        Account acc = getForm();
        if (err.isEmpty()) {
            accountDAO.updateInfor(acc);
            updateAuInfor();
            Dialog.showMessageDialog("Done", "Information Updated!");
        }
    }

    void setGroupButton() {
        ToggleGroup grbutton = new ToggleGroup();
        rdo_Male.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Female.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Female.setToggleGroup(grbutton);
        rdo_Male.setToggleGroup(grbutton);

    }

    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
