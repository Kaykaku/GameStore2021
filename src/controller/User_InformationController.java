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
import javafx.embed.swing.SwingFXUtils;
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
import javax.imageio.ImageIO;
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
    Image avataImage;

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
                scene.setFill(Color.TRANSPARENT);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
            } catch (IOException ex) {
                //Logger.getLogger(User_InformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_Camera.setOnMouseClicked((event) -> {
            try {
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                Parent root = FXMLLoader.load(getClass().getResource(Value.DIALOG_TAKEPICTURE));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                scene.setFill(Color.TRANSPARENT);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (Variable.AVATAR != null) {
                avataImage = Variable.AVATAR;
                setAvatar();
            }
        });
        btn_Update.setOnMouseClicked(event -> {
            this.UpdateInfor();
            
        });
        btn_ChosePicture.setOnMouseClicked(event -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG JPG", "*.png","*.jpg"));
            fc.setInitialDirectory(new File("C:\\Users\\Admin\\Downloads"));
            fc.setTitle("Select folder");
            File f = fc.showOpenDialog(Variable.MAIN_STAGE);
            if (f != null) {
                avataImage = new Image(f.toURI().toString());
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
        try {
            String path = getClass().getResource(rdo_Female.isSelected() ? Value.FEMALE : Value.MALE).toURI().toString();
            img_Avatar.setImage(new Image(path));

            RoundedImageView.RoundedImage(img_Avatar, img_Avatar.getFitWidth());
            avataImage = null;
        } catch (URISyntaxException ex) {
            //Logger.getLogger(User_InformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    void setAvatar() {
        if (avataImage != null) {
            img_Avatar.setImage(avataImage);
        } else {
            try {
                String path = getClass().getResource(rdo_Female.isSelected() ? Value.FEMALE : Value.MALE).toURI().toString();
                img_Avatar.setImage(new Image(path));
            } catch (URISyntaxException ex) {
                //Logger.getLogger(User_InformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        errP += Validation.validationNew_ConfirmPassword(txt_NewPassword, txt_ComfirmPassword);
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
            avataImage = SwingFXUtils.toFXImage(ProcessImage.toImage(account.getImage()), null); 
            img_Avatar.setImage(avataImage);
            RoundedImageView.RoundedImage(img_Avatar, img_Avatar.getFitWidth());
        }
        txt_Username.setText(account.getUsername() + "");
        txt_Username.setEditable(false);
        lbl_Accountid.setText(account.getAccountId() + "");
        txt_Name.setText(account.getName() == null ? "" : account.getName());
        datePicker_Birthday.setValue(account.getBirthDay() != null ? ProcessDate.toLocalDate(account.getBirthDay()) : null);
        rdo_Female.setSelected(account.isGender());
        rdo_Male.setSelected(!account.isGender());
        cbo_Country.getSelectionModel().select(account.getCountry());
        txt_Email.setText(account.getEmail() + "");
        txt_Address.setText(account.getAddress() == null ? "" : account.getAddress());
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
        err = "";
        err += Validation.validationEmail(txt_Email);
        err += Validation.validationBirthDay(datePicker_Birthday);
        if (err.isEmpty()) {
            try {
                Account entity = new Account();
                entity.setAccountId(Auth.USER.getAccountId());
                entity.setName(txt_Name.getText().trim());
                entity.setBirthDay(ProcessDate.toDate(datePicker_Birthday.getValue()));
                entity.setGender(rdo_Female.isSelected());
                entity.setCountry(cbo_Country.getSelectionModel().getSelectedItem());
                entity.setEmail(txt_Email.getText().trim());
                entity.setAddress(txt_Address.getText() != null ? txt_Address.getText().trim() : "");

                Image image =new Image(getClass().getResource(rdo_Female.isSelected() ? Value.FEMALE : Value.MALE).toURI().toString());
                entity.setImage(ProcessImage.toBytes(image));
                if (avataImage != null) {
                    entity.setImage(ProcessImage.toBytes(avataImage));
                }
                return entity;
            } catch (URISyntaxException ex) {
                //Logger.getLogger(User_InformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Dialog.showMessageDialog("Wrong data", err);
        return null;
    }

    private void UpdateInfor() {
        Account acc = getForm();
        if (err.isEmpty()) {
            accountDAO.updateInfor(acc);
            Auth.USER=accountDAO.selectByID(acc.getAccountId());
            Dialog.showMessageDialog("Done", "Information Updated!");
            Variable.MAIN_CONTROLLER.loadUserInfo();
        }
    }

    void setGroupButton() {
        ToggleGroup grbutton = new ToggleGroup();
        rdo_Male.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Female.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Female.setToggleGroup(grbutton);
        rdo_Male.setToggleGroup(grbutton);
        rdo_Male.setSelected(true);
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
