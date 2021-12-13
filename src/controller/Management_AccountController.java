/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Account;
import until.Auth;
import until.Dialog;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
import until.ProcessDate;
import until.ProcessImage;
import until.ProcessString;
import until.Validation;
import until.Value;
import until.Variable;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_AccountController implements Initializable {

    @FXML
    private TableView<Account> tbl_Accounts;

    @FXML
    private TableColumn<Account, Integer> col_ID;

    @FXML
    private JFXButton btn_SendMail;

    @FXML
    private TextField txt_Search;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private JFXButton btn_SendMessage;

    @FXML
    private ChoiceBox<String> cbx_Comment;

    @FXML
    private ChoiceBox<String> cbx_Role;

    @FXML
    private ChoiceBox<String> cbx_Active;
    @FXML
    private Pane pnl_Image;

    @FXML
    private Pane pnl_Login_Info;

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
    private TableColumn<Account, String> col_Username;

    @FXML
    private Pane pnl_Table_Controller;

    @FXML
    private ImageView img_Avatar;

    @FXML
    private ImageView img_Admin;

    @FXML
    private JFXRadioButton rdo_Female;

    @FXML
    private TableColumn<Account, String> col_Name;

    @FXML
    private JFXButton btn_Update;

    @FXML
    private JFXTextField txt_Name;

    @FXML
    private Pane pnl_Table;

    @FXML
    private JFXToggleButton tog_Comment;

    @FXML
    private TableColumn<Account, Date> col_CreationDate;

    @FXML
    private PasswordField txt_NewPassword;

    @FXML
    private Label lbl_ID;

    @FXML
    private Label lbl_Message;

    @FXML
    private Pane pnl_Status;

    @FXML
    private JFXToggleButton tog_Active;

    @FXML
    private JFXToggleButton tog_Role;

    @FXML
    private PasswordField txt_ConfirmPassword;

    @FXML
    private TableColumn<Account, Date> col_Birthday;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private Pane pnl_FillBg;

    @FXML
    private TableColumn<Account, String> col_Email;

    @FXML
    private TableColumn<Account, Boolean> col_Gender;

    @FXML
    private JFXButton btn_Clear;

    @FXML
    private TableColumn<Account, String> col_Country;

    @FXML
    private JFXButton btn_ChangePass;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private JFXTextField txt_Address;

    @FXML
    private Pane pnl_TItle;

    @FXML
    private Pane pnl_BirthDay;

    @FXML
    private JFXComboBox<String> cbo_Country;
    @FXML
    private JFXComboBox<String> cb_Other;
    @FXML
    private JFXComboBox<String> cb_Active;
    @FXML
    private JFXComboBox<String> cb_Role;
    @FXML
    private JFXButton btn_PDFAccount;
    @FXML
    private Pane pnl_ProgressBar;
    @FXML
    private JFXButton btn_ExcelAccount;
    @FXML
    private JFXButton btn_TextAccount;
    private JFXDatePicker datePicker_CreationDate;
    private JFXDatePicker datePicker_Birthday;
    AccountDAO accountDAO = new AccountDAO();
    List<Account> listAccounts = new ArrayList<>();

    boolean isEdit = false;
    int index = -1, role = -1;
    Image avatar = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayFormAnimation();
        drawDatePicker();
        setGroupButton();
        setEvent();
        SetEventExportFile();
        setAvatar();
        updateStatus();
        fillDataOnBackground();
    }

    void fillDataOnBackground() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    fillCboCountry();
                    fillChoiceBox();
                    fillTable();

                    listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
                });
            }
        }.start();

    }

    void drawDatePicker() {
        datePicker_CreationDate = new JFXDatePicker();
        datePicker_Birthday = new JFXDatePicker();
        datePicker_CreationDate.setPrefWidth(250);
        datePicker_Birthday.setPrefWidth(250);
        datePicker_Birthday.setDefaultColor(Color.LIGHTBLUE);
        datePicker_CreationDate.setDefaultColor(Color.LIGHTBLUE);

        pnl_CreationDate.getChildren().add(datePicker_CreationDate);
        pnl_BirthDay.getChildren().add(datePicker_Birthday);

        ProcessDate.converter(datePicker_Birthday);
        ProcessDate.converter(datePicker_CreationDate);

        datePicker_CreationDate.setDisable(true);
        datePicker_CreationDate.setValue(LocalDate.now());
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

    void fillChoiceBox() {
        List<String> list = new ArrayList<>();
        list.add("All role");
        list.add("Admin");
        list.add("Manager");
        list.add("User");
        cbx_Role.setItems(FXCollections.observableArrayList(list));
        cbx_Role.getSelectionModel().select(0);

        list.clear();
        list.add("All status");
        list.add("Inactive");
        list.add("Active");
        cbx_Active.setItems(FXCollections.observableArrayList(list));
        cbx_Active.getSelectionModel().select(0);

        list.clear();
        list.add("All comment");
        list.add("Block comment");
        list.add("Enable comment");
        cbx_Comment.setItems(FXCollections.observableArrayList(list));
        cbx_Comment.getSelectionModel().select(0);
    }

    void fillTable() {
        listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim(),
                (cbx_Role.getSelectionModel().getSelectedIndex() - 1),
                (cbx_Active.getSelectionModel().getSelectedIndex() - 1),
                (cbx_Comment.getSelectionModel().getSelectedIndex() - 1)
        );

        ObservableList<Account> list = FXCollections.observableArrayList(listAccounts);

        col_ID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        Callback<TableColumn<Account, Boolean>, TableCell<Account, Boolean>> callbackBoo = (TableColumn<Account, Boolean> param) -> new TableCell<Account, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item ? "Female" : "Male");
                }
            }
        };
        Callback<TableColumn<Account, Date>, TableCell<Account, Date>> callbackDate = (TableColumn<Account, Date> param) -> new TableCell<Account, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(ProcessDate.toString(item));
                }
            }
        };
        col_Gender.setCellFactory(callbackBoo);
        col_Birthday.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        col_Birthday.setCellFactory(callbackDate);
        col_Country.setCellValueFactory(new PropertyValueFactory<>("country"));
        col_CreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        col_CreationDate.setCellFactory(callbackDate);
        col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
        tbl_Accounts.getItems().removeAll();
        tbl_Accounts.setItems(list);
    }

    void setGroupButton() {
        ToggleGroup grbutton = new ToggleGroup();
        rdo_Male.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Female.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Female.setToggleGroup(grbutton);
        rdo_Male.setToggleGroup(grbutton);
    }

    Account getModel() {
        String err = "";
        err += Validation.validationName(txt_UserName);
        err += Validation.validationPassword(txt_NewPassword);
        err += Validation.validationConfirmPassword(txt_NewPassword, txt_ConfirmPassword);
        err += Validation.validationNew_ConfirmPassword(txt_NewPassword, txt_ConfirmPassword);
        if (err.isEmpty()) {
            Account entity = new Account();
            entity.setUsername(txt_UserName.getText());
            entity.setPassword(txt_NewPassword.getText());
            return entity;
        }
        Dialog.showMessageDialog("Wrong data", err);
        return null;

    }

    void clear() {
        txt_NewPassword.setText("");
        txt_ConfirmPassword.setText("");
    }

    void updatePass() {
        Account acc = getModel();
        if (acc == null) {
            return;
        }
        if (Auth.USER.getRole() >= accountDAO.selectByUser(acc.getUsername()).getRole()) {
            ProcessString.showMessage(lbl_Message, "You do not have the authority !");
            Dialog.showMessageDialog("Error authority account", "You do not have the authority to change the data of this account!");
            return;
        }
        accountDAO.updatePass2(acc);
        if (txt_UserName.getText().equals(Auth.USER.getUsername())) {
            Auth.USER.setPassword(txt_NewPassword.getText());
        }
        this.clear();
        Dialog.showMessageDialog("Done", "Updated Password!");
    }

    void setAvatar() {
        if (avatar != null) {
            img_Avatar.setImage(avatar);
        } else {
            try {
                String path = getClass().getResource(rdo_Female.isSelected() ? Value.FEMALE : Value.MALE).toURI().toString();
                img_Avatar.setImage(new Image(path));
            } catch (URISyntaxException ex) {
                //Logger.getLogger(Management_AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        RoundedImageView.RoundedImage(img_Avatar, 200);
    }

    void setForm(Account entity) {
        role = entity.getRole();
        if(isEdit){
            role = entity.getRole();
        }else{
            role=2;
        }
        lbl_ID.setText(isEdit ? entity.getAccountId() + "" : "Editing");
        txt_Name.setText(isEdit && entity.getName() != null ? entity.getName() : "");
        datePicker_Birthday.setValue(isEdit && entity.getBirthDay() != null ? ProcessDate.toLocalDate(entity.getBirthDay()) : null);
        datePicker_CreationDate.setValue(isEdit ? ProcessDate.toLocalDate(entity.getCreationDate()) : LocalDate.now());
        rdo_Male.setSelected(isEdit ? !entity.isGender() : true);
        rdo_Female.setSelected(isEdit ? entity.isGender() : false);
        cbo_Country.getSelectionModel().select(isEdit ? entity.getCountry() : "");
        txt_Email.setText(isEdit ? entity.getEmail() : "");
        txt_Address.setText(isEdit ? entity.getAddress() : "");
        txt_UserName.setText(isEdit ? entity.getUsername() : "");
        tog_Active.setSelected(isEdit ? entity.isActive() : false);
        if (role == 0) {
            img_Admin.setOpacity(1);
            tog_Role.setDisable(true);
            tog_Role.setSelected(true);
        } else {
            img_Admin.setOpacity(0);
            tog_Role.setDisable(false);
            tog_Role.setSelected(isEdit ? entity.getRole() == 1 : false);
        }
        tog_Comment.setSelected(isEdit ? entity.isComment() : false);
        if (entity.getImage() != null) {
            avatar = ProcessImage.toImageFX(entity.getImage());
        }
        txt_NewPassword.setText("");
        txt_ConfirmPassword.setText("");
        setAvatar();
    }

    void updateStatus() {
        txt_UserName.setEditable(!isEdit);
        btn_Add.setDisable(isEdit);
        btn_Delete.setDisable(!isEdit);
        btn_Update.setDisable(!isEdit);
    }

    Account getForm() {
        String err = "";
        err += Validation.validationPersonName(txt_Name);
        err += Validation.validationEmail(txt_Email);
        err += Validation.validationBirthDay(datePicker_Birthday);
        if (!isEdit) {
            err += Validation.validationUserName(txt_UserName);
            err += Validation.validationPassword(txt_NewPassword);
            err += Validation.validationConfirmPassword(txt_NewPassword, txt_ConfirmPassword);
        }

        if (err.isEmpty()) {
            Account entity = new Account();
            entity.setAccountId(isEdit ? Integer.valueOf(lbl_ID.getText()) : -1);
            entity.setName(txt_Name.getText().trim());
            entity.setBirthDay(ProcessDate.toDate(datePicker_Birthday.getValue()));
            entity.setCreationDate(ProcessDate.toDate(datePicker_CreationDate.getValue()));
            entity.setGender(rdo_Female.isSelected());
            entity.setCountry(cbo_Country.getSelectionModel().getSelectedItem());
            entity.setEmail(txt_Email.getText().trim());
            entity.setActive(tog_Active.isSelected());
            entity.setComment(tog_Comment.isSelected());
            entity.setRole(role);
            entity.setUsername(txt_UserName.getText().trim());
            entity.setPassword(isEdit ? accountDAO.selectByID(entity.getAccountId()).getPassword() : txt_NewPassword.getText().trim());
            entity.setAddress(txt_Address.getText() != null ? txt_Address.getText().trim() : "");
            String path = "";
            try {
                path = getClass().getResource(rdo_Female.isSelected() ? Value.FEMALE : Value.MALE).toURI().toString();
            } catch (URISyntaxException ex) {
                //Logger.getLogger(Management_AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
            entity.setImage(ProcessImage.toBytes(new Image(path)));
            if (avatar != null) {
                entity.setImage(ProcessImage.toBytes(avatar));
            }
            return entity;
        }
        ProcessString.showMessage(lbl_Message, "An error occurred on the form!");
        Dialog.showMessageDialog("Wrong data", err);

        return null;
    }

    void clearColor() {
        Validation.clearColor(txt_Name);
        Validation.clearColor(datePicker_Birthday);
        Validation.clearColor(txt_Email);
        Validation.clearColor(txt_UserName);
        Validation.clearColor(txt_NewPassword);
        Validation.clearColor(txt_ConfirmPassword);
    }

    void clearForm() {
        index = -1;
        isEdit = false;
        avatar = null;
        setForm(new Account());
        cbx_Active.getSelectionModel().select(0);
        cbx_Role.getSelectionModel().select(0);
        cbx_Comment.getSelectionModel().select(0);
        txt_Search.setText("");
        clearColor();
        fillTable();
        updateStatus();
        ProcessString.showMessage(lbl_Message, "Clear form!!!");
    }

    void edit() {
        index = tbl_Accounts.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        isEdit = true;
        avatar = null;
        clearColor();

        int id = (int) col_ID.getCellObservableValue(index).getValue();
        Account entity = accountDAO.selectByID(id);
        Variable.DEFAULT_MAIL = accountDAO.selectByID(id);
        setForm(entity);
        updateStatus();
    }

    void insert() {
        Account entity = getForm();
        if (entity == null) {
            return;
        }
        if (Auth.USER.getRole() >= entity.getRole()) {
            ProcessString.showMessage(lbl_Message, "You do not have the authority !");
            Dialog.showMessageDialog("Error authority account", "You do not have the authority to change the data of this account!");
            return;
        }
        accountDAO.insert(entity);
        listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
        fillTable();
        clearForm();
        ProcessString.showMessage(lbl_Message, "Inserted successfully!");
    }

    void update() {
        Account entity = getForm();
        if (entity == null) {
            return;
        }
        if (Auth.USER.getRole() >= entity.getRole()) {
            ProcessString.showMessage(lbl_Message, "You do not have the authority !");
            Dialog.showMessageDialog("Error authority account", "You do not have the authority to change the data of this account!");
            return;
        }
        accountDAO.update(entity);
        listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
        fillTable();
        ProcessString.showMessage(lbl_Message, "Update successfully ID-" + entity.getAccountId() + " !");
    }

    void delete() {
        int id = Integer.parseInt(lbl_ID.getText());
        if (Auth.USER.getRole() >= role) {
            ProcessString.showMessage(lbl_Message, "You do not have the authority !");
            Dialog.showMessageDialog("Error authority account", "You do not have the authority to change the data of this account!");
            return;
        }
        accountDAO.delete(id);
        fillTable();
        clearForm();
        ProcessString.showMessage(lbl_Message, "Deleted successfully ID-" + id + " !");
    }

    private void SetEventExportFile() {
        String[] header = new String[]{"ID", "Name", "BirthDay", "Gender",
            "Email", "Address", "Country", "Creation Date", "Username",
            "Active", "Role", "Comment"};
        List<Account> list = accountDAO.selectAll();
        List<Object[]> listObjs = new ArrayList<>();
        list.forEach((News) -> {
            listObjs.add(News.toObjects());
        });
        String fileName = "Account";
        String title = "Account List";

        btn_PDFAccount.setOnAction(evt -> {
            try {
                ExportPDF.ExportPDF(Variable.MAIN_STAGE, header, listObjs, fileName + ".pdf", title);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btn_ExcelAccount.setOnAction(evt -> {

            try {
                ExportExcel.exportFile(Variable.MAIN_STAGE, header, listObjs, fileName + ".xlsx", title);
            } catch (IOException ex) {
                Logger.getLogger(Management_AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_TextAccount.setOnAction(evt -> {
            ExportText.ExportFileAccount();
        });
    }

    void setEvent() {
        tbl_Accounts.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 1) {
                edit();
            }
        });
        cbx_Role.setOnAction((event) -> {
            fillTable();
        });
        cbx_Active.setOnAction((event) -> {
            fillTable();
        });
        cbx_Comment.setOnAction((event) -> {
            fillTable();
        });

        txt_Search.setOnKeyReleased(evt -> {
            fillTable();
            if (listAccounts.size() > 0) {
                setForm(listAccounts.get(0));
            }
        });
        rdo_Male.setOnAction(evt -> {
            setAvatar();
        });
        rdo_Female.setOnAction(evt -> {
            setAvatar();
        });
        tog_Role.setOnMouseClicked((event) -> {
            role = tog_Role.isSelected()?1:2;
        });
        img_Avatar.setOnMouseClicked((evt) -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG JPG", "*.png","*.jpg"));
            fc.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
            fc.setTitle("Select folder");
            File path = fc.showOpenDialog(Variable.MAIN_STAGE);
            if (path != null) {
                avatar = new Image(path.toURI().toString());
                setAvatar();
            }
        });
        btn_Clear.setOnMouseClicked((event) -> {
            clearForm();
        });
        btn_Add.setOnMouseClicked((event) -> {
            insert();
        });
        btn_Update.setOnMouseClicked((event) -> {
            update();
        });
        btn_Delete.setOnMouseClicked((event) -> {
            delete();
        });
        btn_ChangePass.setOnMouseClicked((event) -> {
            try {
                this.updatePass();
            } catch (Exception e) {
            }
        });
        btn_SendMail.setOnMouseClicked((MouseEvent event) -> {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Value.FORM_MAIL));
                Parent root1 = (Parent) fxmlLoader.load();

                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                Scene scene = new Scene(root1);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                pnl_FillBg.setScaleX(1);
                pnl_FillBg.setScaleY(1);

                stage.showAndWait();
                stage.close();
                pnl_FillBg.setScaleX(0);
                pnl_FillBg.setScaleY(0);
            } catch (IOException ex) {
//                Logger.getLogger(Management_AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

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
}
