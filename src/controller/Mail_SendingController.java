/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AccountDAO;
import animatefx.animation.FadeInDown;
import animatefx.animation.FadeInDownBig;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInLeftBig;
import animatefx.animation.FadeInRightBig;
import animatefx.animation.FadeInUpBig;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Account;
import until.Dialog;
import until.MailSender;
import until.Value;
import until.Variable;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Mail_SendingController implements Initializable {

    @FXML
    private ChoiceBox<String> cbx_Role;

    @FXML
    private TableColumn<Account, Integer> col_ID;

    @FXML
    private Pane pnl_Receiver;

    @FXML
    private JFXTextArea txt_Content;

    @FXML
    private TextField txt_Search;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private ChoiceBox<String> cbx_Active;

    @FXML
    private TableColumn<Account, String> col_UserName;

    @FXML
    private TableView<Account> tbl_Email;

    @FXML
    private JFXCheckBox cbo_All_In;

    @FXML
    private Pane pnl_List_News;

    @FXML
    private JFXCheckBox cbo_All_In_Table;

    @FXML
    private Button btn_Exit;

    @FXML
    private TableColumn<Account, String> col_Name;

    @FXML
    private TableColumn<Account, Boolean> col_Select;

    @FXML
    private ImageView Img_File;

    @FXML
    private JFXTextField txt_Title;

    @FXML
    private Pane pnl_Title;

    @FXML
    private ChoiceBox<String> cbx_Comment;

    @FXML
    private TableColumn<Account, String> col_Email;

    @FXML
    private JFXButton btn_Clear;

    @FXML
    private Label lbl_FileName;

    @FXML
    private Pane pnl_Content;

    @FXML
    private JFXButton btn_Send;

    @FXML
    private Label lbl_Reciever;

    @FXML
    private HBox hbox_Checkbox;

    @FXML
    private VBox vbox_Flter;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    AccountDAO dao = new AccountDAO();
    List<Account> listAccounts;
    File f;
    int total = 0, count = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayFormAnimation();
        fillDataOnBackground();
        setEvent();
    }

    void setDefaultAccount(Account acc) {
        txt_Search.setText(acc.getUsername());
        lbl_Reciever.setText(acc.getEmail());
        tbl_Email.getItems().get(0).getCheckbox().setSelected(true);
    }

    void setDefaultMailTemplate() {
        txt_Title.setEditable(false);
        txt_Content.setEditable(false);
        txt_Title.setText(Variable.DEFAULT_MAILTITLETEMPLATE);
        txt_Content.setText(Variable.DEFAULT_MAILTEMPLATE);
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
                    fillChoiceBox();
                    fillTable();
                    if (Variable.DEFAULT_MAIL != null) {
                        setDefaultAccount(Variable.DEFAULT_MAIL);
                    }
                    if (Variable.DEFAULT_MAILTEMPLATE != null) {
                        setDefaultMailTemplate();
                    }
                });
            }
        }.start();

    }

    void setEvent() {
        cbo_All_In.setOnMouseClicked((event) -> {
            txt_Search.setText("");
            cbx_Role.getSelectionModel().select(0);
            cbx_Comment.getSelectionModel().select(0);
            cbx_Active.getSelectionModel().select(0);
            fillTable();
            Platform.runLater(() -> {
                tbl_Email.getItems().forEach((acc) -> {
                    acc.getCheckbox().setSelected(cbo_All_In.isSelected());
                });
            });
            total = cbo_All_In.isSelected() ? listAccounts.size() : 0;
            lbl_Reciever.setText("Selected " + total + " accounts");
            cbo_All_In_Table.setSelected(false);
        });
        cbo_All_In_Table.setOnAction((event) -> {
            Platform.runLater(() -> {
                tbl_Email.getItems().forEach((acc) -> {
                    acc.getCheckbox().setSelected(cbo_All_In_Table.isSelected());
                });
            });
            total = cbo_All_In_Table.isSelected() ? listAccounts.size() : 0;
            lbl_Reciever.setText("Selected " + total + " accounts");
            cbo_All_In.setSelected(false);
        });
        tbl_Email.setOnMouseClicked((event) -> {
            int index = tbl_Email.getSelectionModel().getSelectedIndex();
            boolean flag = tbl_Email.getItems().get(index).getCheckbox().isSelected();
            tbl_Email.getItems().get(index).getCheckbox().setSelected(!flag);
            total = flag ? total - 1 : total + 1;
            lbl_Reciever.setText("Selected " + total + " accounts");
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
        });
        btn_Exit.setOnMouseClicked((event) -> {
            Variable.DEFAULT_MAIL = null;
            Variable.DEFAULT_MAILTEMPLATE = null;
            Variable.DEFAULT_MAILTITLETEMPLATE = null;
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
        Img_File.setOnMouseClicked((event) -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
            fc.setTitle("Select folder");
            File path = fc.showOpenDialog((Stage) Img_File.getScene().getWindow());
            if (path != null) {
                f = path;
                lbl_FileName.setText(path.getName());
            }
        });
        btn_Send.setOnMouseClicked((event) -> {
            start();
        });
        btn_Clear.setOnMouseClicked((event) -> {
            clear();
        });
    }

    void clear() {
        total = 0;
        count = 0;
        lbl_Reciever.setText("");
        txt_Content.setText("");
        txt_Title.setText("");
        File f = null;
        txt_Search.setText("");
        cbx_Role.getSelectionModel().select(0);
        cbx_Comment.getSelectionModel().select(0);
        cbx_Active.getSelectionModel().select(0);
        fillTable();
        Platform.runLater(() -> {
            tbl_Email.getItems().forEach((acc) -> {
                acc.getCheckbox().setSelected(false);
            });
        });
    }

    void start() {
        if (total == 0) {
            Dialog.showMessageDialog("", "You must choose at least 1 account");
            return;
        }
        List<Account> list = new ArrayList<>();
        for (Account acc : tbl_Email.getItems()) {
            if (acc.getCheckbox().isSelected()) {
                list.add(acc);
            }
        }
        new MailSender().startProgress(list, txt_Title.getText().trim(), txt_Content.getText().trim(), f);
        clear();
        Dialog.showMessageDialog("", "Your sending progress is running!");
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
        listAccounts = dao.selectByKeyWord(txt_Search.getText().trim(),
                (cbx_Role.getSelectionModel().getSelectedIndex() - 1),
                (cbx_Active.getSelectionModel().getSelectedIndex() - 1),
                (cbx_Comment.getSelectionModel().getSelectedIndex() - 1)
        );

        ObservableList<Account> list = FXCollections.observableArrayList(listAccounts);

        col_ID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_Select.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
        tbl_Email.getItems().removeAll();
        tbl_Email.setItems(list);

    }

    void setAccount(Account acc) {
        txt_Search.setText(acc.getUsername());
        fillTable();
        lbl_Reciever.setText(acc.getEmail());
    }

    void displayFormAnimation() {
        new FadeInDownBig(pnl_Receiver).play();
        new FadeInDown(vbox_Flter).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Add_Info).play();
        new FadeInRightBig(pnl_List_News).play();
        new FadeInUpBig(pnl_Content).play();
        new FadeInRightBig(tbl_Email).play();
        new FadeInUpBig(btn_Send).play();
        new FadeInUpBig(hbox_Checkbox).play();
    }
}
