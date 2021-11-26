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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Account;
import until.Dialog;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
import until.ProcessDate;
import until.ProcessImage;
import until.Validation;

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
    private JFXButton  btn_ExcelAccount ;
    @FXML
    private JFXButton btn_TextAccount;
    public static JFXTextField static_Mail;
    public static Stage static_Stage;
    public static Pane static_PnlProgressBar;
    private JFXDatePicker datePicker_CreationDate;
    private JFXDatePicker datePicker_Birthday;
    AccountDAO accountDAO = new AccountDAO();
    List<Account> listAccounts = new ArrayList<>();
    
    
    boolean isEdit = false;
    int index = -1;
    File avatarFile = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayFormAnimation();
        drawDatePicker();
        setGroupButton();
        setEvent();
        ExportPDFAccount();
        ExportExcelAccount();
        ExportTextAccount();
        setAvatar();
        updateStatus();
        fillDataOnBackground();
        
    }
    
    void fillDataOnBackground() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(new Runnable() {
                    public void run() {
                        fillCboCountry();
                        listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
                        fillTable();
                        static_Mail = txt_Email ;  
                        static_PnlProgressBar = pnl_ProgressBar;
                    }
                });
            }
        }.start();
    }

    void drawDatePicker() {
        datePicker_CreationDate = new JFXDatePicker();
        datePicker_CreationDate.setDefaultColor(Paint.valueOf("lightblue"));
        datePicker_Birthday = new JFXDatePicker();
        datePicker_Birthday.setDefaultColor(Paint.valueOf("lightblue"));
        datePicker_CreationDate.setPrefWidth(250);
        datePicker_Birthday.setPrefWidth(250);

        pnl_CreationDate.getChildren().add(datePicker_CreationDate);
        pnl_BirthDay.getChildren().add(datePicker_Birthday);

        ProcessDate.converter(datePicker_Birthday);
        ProcessDate.converter(datePicker_CreationDate);

        pnl_CreationDate.setDisable(true);
    }

    void fillCboCountry() {
        List<String> list = new ArrayList<>();
        list.add("Vietnam");
        list.add("China");
        list.add("America");
        list.add("England");
        list.add("Japan");
        cbo_Country.setItems(FXCollections.observableArrayList(list));
        List<String> listRole = new ArrayList<>();
        listRole.add("All Role");
        listRole.add("Admin");
        listRole.add("Manager");
        listRole.add("User");
        cb_Role.setItems(FXCollections.observableArrayList(listRole));
        List<String> listActive = new ArrayList<>();
        listActive.add("All Status");
        listActive.add("Activated");
        listActive.add("Inactivated");
        cb_Active.setItems(FXCollections.observableArrayList(listActive));
        List<String> listComment = new ArrayList<>();
        listComment.add("All Status");
        listComment.add("Activated");   
        listComment.add("Inactivated");
        cb_Other.setItems(FXCollections.observableArrayList(listComment));
    }
    void fillTable() {
        ObservableList<Account> list = FXCollections.observableArrayList(listAccounts);

        col_ID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        Callback<TableColumn<Account, Boolean>, TableCell<Account, Boolean>> callbackBoo = new Callback<TableColumn<Account, Boolean>, TableCell<Account, Boolean>>() {
            @Override
            public TableCell<Account, Boolean> call(TableColumn<Account, Boolean> param) {
                return new TableCell<Account, Boolean>() {
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
            }
        };
        Callback<TableColumn<Account, Date>, TableCell<Account, Date>> callbackDate = new Callback<TableColumn<Account, Date>, TableCell<Account, Date>>() {
            @Override
            public TableCell<Account, Date> call(TableColumn<Account, Date> param) {
                return new TableCell<Account, Date>() {
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
            }
        };
        col_Gender.setCellFactory(callbackBoo);
        col_Birthday.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        col_Birthday.setCellFactory(callbackDate);
        col_Country.setCellValueFactory(new PropertyValueFactory<>("country"));
        col_CreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        col_CreationDate.setCellFactory(callbackDate);
        col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_Username.setCellValueFactory(new PropertyValueFactory<>("userName"));

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

    void setAvatar() {
        if (avatarFile != null) {
            img_Avatar.setImage(new Image(avatarFile.toURI().toString()));
        } else {

            img_Avatar.setImage(new Image(new File(rdo_Female.isSelected() ? "src/icons/female256.png" : "src/icons/male256.png").toURI().toString()));
        }
        RoundedImageView.RoundedImage(img_Avatar, 200);
    }

    void setForm(Account entity) {
        lbl_ID.setText(isEdit ? entity.getAccountId() + "" : "Editing");
        txt_Name.setText(isEdit && entity.getName()!=null ? entity.getName() : "");
        datePicker_Birthday.setValue(isEdit && entity.getBirthDay()!=null? ProcessDate.toLocalDate(entity.getBirthDay()) : null);
        datePicker_CreationDate.setValue(isEdit ? ProcessDate.toLocalDate(entity.getCreationDate()) : LocalDate.now());
        rdo_Male.setSelected(isEdit ? !entity.isGender() : true);
        rdo_Female.setSelected(isEdit ? entity.isGender() : false);
        cbo_Country.getSelectionModel().select(isEdit ? entity.getCountry() : "");
        txt_Email.setText(isEdit ? entity.getEmail() : "");
        txt_Address.setText(isEdit ? entity.getAddress() : "");
        txt_UserName.setText(isEdit ? entity.getUserName().toUpperCase() : "");
        tog_Active.setSelected(isEdit ? entity.isActive() : false);
        tog_Role.setSelected(isEdit ? entity.getRole() == 1 : false);
        tog_Comment.setSelected(isEdit ? entity.isComment() : false);
        if (entity.getImage() != null) {
            avatarFile = ProcessImage.toFile(entity.getImage(), "avatar.png");
        }
        setAvatar();
    }

    void updateStatus() {
        txt_UserName.setEditable(isEdit);
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
            entity.setRole(tog_Role.isSelected() ? 1 : 2);
            entity.setUserName(txt_UserName.getText().trim());
            entity.setPassword(isEdit ? accountDAO.selectByID(entity.getAccountId()).getPassword() : txt_NewPassword.getText().trim());
            entity.setAddress(txt_Address.getText()!=null?txt_Address.getText().trim():"");
            entity.setImage(ProcessImage.toBytes(new File(rdo_Female.isSelected() ? "src/icons/female256.png" : "src/icons/male256.png")));
            if (avatarFile != null) {
                entity.setImage(ProcessImage.toBytes(avatarFile));
            }
            return entity;
        }
        Dialog.showMessageDialog("Wrong data", err);
        return null;
    }

    void clearForm() {
        index = -1;
        isEdit = false;
        avatarFile = null;
        setForm(new Account());
        updateStatus();
    }

    void edit() {
        isEdit = true;
        avatarFile = null;
        index = tbl_Accounts.getSelectionModel().getSelectedIndex();
        int id = (int) col_ID.getCellObservableValue(index).getValue();
        Account entity = accountDAO.selectByID(id);
        setForm(entity);
        updateStatus();
    }

    void insert() {
        Account entity = getForm();
        if (entity == null) {
            return;
        }
        accountDAO.insert(entity);
        listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
        fillTable();
        clearForm();

    }

    void update() {
        Account entity = getForm();
        if (entity == null) {
            return;
        }
        accountDAO.update(entity);
        listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
        fillTable();
        clearForm();

    }

    void delete() {
        Account entity = getForm();
        if (entity == null) {
            return;
        }
        accountDAO.delete(entity.getAccountId());
        listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
        fillTable();
        clearForm();

    }
 private void ExportPDFAccount() {
        btn_PDFAccount.setOnAction(evt -> {
            try {
                ExportPDF.exportPDFAccount();
                Dialog.showMessageDialog(null, "File save successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
  
    private void ExportExcelAccount() {
        btn_ExcelAccount.setOnAction(evt -> {
            String[] header = new String[]{"ID", "Name", "BirthDay", "Gender", "Image",
                "Email", "Address", "Country", "Creation Date", "Username",
                "Password", "Active", "Role", "Comment"};
            List<Account> list = accountDAO.selectAll();
            List<Object[]> listObjs = new ArrayList<>();
            list.forEach((News) -> {
                listObjs.add(News.toObjects());
            });
            String fileName = "Account";
            String title = "Account List";
            try {
                ExportExcel.exportFile(null, header, listObjs, fileName, title);
            } catch (IOException ex) {
                Logger.getLogger(Management_AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void ExportTextAccount() {
        btn_TextAccount.setOnAction(evt -> {
//           try {
//               List<Account> list = accountDAO.selectAll();
//               List<Object[]> listObjs = new ArrayList<>();
//               list.forEach((News) -> {
//                   listObjs.add(News.toObjects());
//               });
//               String fileName = "Account";
//               ExportText.exportText(null, listObjs, fileName);
//           } catch (IOException ex) {
//              ex.printStackTrace();
//           }
            ExportText.ExportFileAccount();
        });
    }

    void setEvent() {
        tbl_Accounts.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 1) {
                edit();
            }
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
        img_Avatar.setOnMouseClicked((evt) -> {
            FileChooser fileChooser = new FileChooser();
            avatarFile = fileChooser.showOpenDialog(((Node) (evt.getSource())).getScene().getWindow());
            if (avatarFile != null) {
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
        btn_SendMail.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/Form/Mail_Sending.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                static_Stage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(Management_AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
            });
        cb_Role.setOnAction(event ->{
                String selectedItem = cb_Role.getSelectionModel().getSelectedItem();
                if(selectedItem.equalsIgnoreCase("Admin")){
                    listAccounts = accountDAO.selectRoles(0+"");
                    fillTable();
                }else if(selectedItem.equalsIgnoreCase("Manager")){
                    listAccounts = accountDAO.selectRoles(1+"");
                    fillTable();
                }else if(selectedItem.equalsIgnoreCase("User")){
                    listAccounts = accountDAO.selectRoles(2+"");
                    fillTable();
                }else{
                    listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
                    fillTable();
                }  
            });
        cb_Active.setOnAction((ActionEvent event) ->{
                String selectedItem = cb_Active.getSelectionModel().getSelectedItem().toString();
                if(selectedItem.equalsIgnoreCase("Activated")){
                    listAccounts = accountDAO.selectActive(1+"");
                    fillTable();
                }else if(selectedItem.equalsIgnoreCase("Inactivated")){
                    listAccounts = accountDAO.selectRoles(0+"");
                    fillTable();
                }else{
                    listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
                    fillTable();
                }  
            });
        cb_Other.setOnAction((ActionEvent event) ->{
                String selectedComment = cb_Other.getSelectionModel().getSelectedItem();
                if(selectedComment.equalsIgnoreCase("Activated")){
                    listAccounts = accountDAO.selectComment(1+"");
                    fillTable();
                }else if(selectedComment.equalsIgnoreCase("Inactivated")){
                    listAccounts = accountDAO.selectComment(0+"");
                    fillTable();
                }else{
                    listAccounts = accountDAO.selectByKeyWord(txt_Search.getText().trim());
                    fillTable();
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
