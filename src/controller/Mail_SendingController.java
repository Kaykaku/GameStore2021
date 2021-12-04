/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AccountDAO;
import DAO.ApplicationDAO;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInLeftBig;
import animatefx.animation.FadeInRightBig;
import animatefx.animation.FadeInUpBig;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controller.Management_AccountController.static_Stage;
import static controller.MainController.static_ProgressBar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import until.Dialog;
import until.ProcessDate;
import until.ProcessString;
import model.Account;
import model.Application;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Mail_SendingController implements Initializable {

    @FXML
    private Pane pnl_Title;
    @FXML
    private Pane pnl_List_News;
    @FXML
    private Pane pnl_Add_Info;
    @FXML
    private Pane pnl_Content;
    @FXML
    private JFXButton btn_Send;
    @FXML
    private JFXButton btn_Back;
    @FXML
    private ImageView new_Image;
    @FXML
    private Label lbl_Attachment;
    @FXML
    private Label lbl_Image;
    @FXML
    private CheckBox sendAll_ChBox;

    @FXML
    private TableView<Account> tbl_Email;
    @FXML
    private ImageView icon_Attachment;
    @FXML
    private TableColumn<Account, String> col_Email;
    @FXML
    private TableColumn<Account, String> col_ID;
    @FXML
    private TableColumn<Account, String> col_Name;
    @FXML
    private TableColumn<Account, String> col_UserName;
    @FXML
    private TableColumn<Account, String> col_Select;
    @FXML
    private JFXTextArea txt_SelectedFiles;
    @FXML
    private Label lbl_Exit;
    @FXML
    private JFXTextField txt_SearchBar;
    @FXML
    private JFXTextField txt_TitleField;

    @FXML
    private JFXTextArea txt_ContentField;

    List<File> Attachment = new ArrayList<>();
    public static List<Account> Emails = new ArrayList<>();
    public static AccountDAO AccDAO = new AccountDAO();
    public static ApplicationDAO appDAO = new ApplicationDAO();
    public static List<Application> Sales = new ArrayList<>();
    List<String> listEmails = new ArrayList();
    Multipart multipart;
    public static File avtImg;
    public static ProgressBar progress = new ProgressBar();
    public static Label lbl_Progress = new Label();
    public static JFXButton btn_Cancel = new JFXButton();
    static Boolean cancelTask = false;
    static int i;
    String txt ="";

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayFormAnimation();
        setThings();
        fillTable();
    }

    void fillTable() {
        Emails = AccDAO.selectMailList(txt_SearchBar.getText().trim());
        ObservableList<Account> list = FXCollections.observableArrayList(Emails);
        col_ID.setCellValueFactory(new PropertyValueFactory<>("AccountId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col_Select.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
        tbl_Email.getItems().removeAll();
        tbl_Email.setItems(list);
    }

    void Clear() {
        txt_TitleField.setText("");
        txt_SelectedFiles.setText("");
        txt_ContentField.setText("");
        Attachment.clear();
        sendAll_ChBox.setSelected(false);
        tbl_Email.getItems().forEach((acc) -> {
            acc.getCheckbox().setSelected(false);
        });
    }

    Multipart handleMultipart() throws IOException, MessagingException {
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(txt);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        for (File f : Attachment) {
            if (Attachment != null) {
                MimeBodyPart part = new MimeBodyPart();
                part.attachFile(f);
                multipart.addBodyPart(part);
            }
        }
        return multipart;
    }

    static void removeProgress() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(()
                        -> {
                    static_ProgressBar.getChildren().removeAll(progress, btn_Cancel);
                });
            }
        }.start();
    }

    static void setStyleProgressBar() {
        static_ProgressBar.setStyle("-fx-alignment: CENTER;");
        progress.setStyle("-fx-accent: #ed6f15;");
        progress.setPrefWidth(180);
        progress.setPrefHeight(3);
        btn_Cancel.setText("Cancel");
        btn_Cancel.setAlignment(Pos.TOP_CENTER);
        btn_Cancel.setStyle("-fx-background-color: #272727;"
                + "-fx-text-fill: orange;"
                + "-fx-font-size:10pt;");
        static_ProgressBar.getChildren().addAll(progress, btn_Cancel);
    }

    public static Message SendMailContent(Session session, String send, String Subject, String Text
    ) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("GameStore "));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(send)
        );
        message.setSubject(Subject);
        message.setText(Text);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(Text);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        return message;
    }

    public static Session SendMail() {
        final String username = "khanhduonghoangkhanh11@gmail.com";
        final String password = "01664252932";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

    void setThings() {
        
        sendAll_ChBox.setOnMouseClicked((event) -> {
            tbl_Email.getItems().forEach((acc) -> {
                if (sendAll_ChBox.isSelected()) {
                    acc.getCheckbox().setSelected(true);
                } else {
                    acc.getCheckbox().setSelected(false);
                }
            });
        });
        btn_Back.setOnMouseClicked((event) -> {
            Clear();
        });
        btn_Cancel.setOnMouseClicked(event -> {
            cancelTask = true;
            System.out.println("Task Stopped!");
        });
        btn_Back.setOnMouseClicked(event -> {
            this.Clear();
        });
        lbl_Exit.setOnMouseClicked(event -> {
            static_Stage.close();
        });
        btn_Send.setOnMouseClicked(event -> {
            tbl_Email.getItems().stream().filter((acc)
                    -> (acc.getCheckbox().isSelected())).forEachOrdered((acc) -> {
                listEmails.add(acc.getEmail());
            });
            if(!listEmails.isEmpty()){
                startProcess();
                static_Stage.close();
               // this.Clear();
            }
            else{
                Dialog.showMessageDialog("Notice", "Please Choose at least a Recipient!");
            }
        });
        icon_Attachment.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            List<File> file = fileChooser.showOpenMultipleDialog(((Node) (event.getSource())).getScene().getWindow());
            if (file != null) {
                file.forEach((f) -> {
                    try {
                        Path path = f.toPath();
                        long bytes = Files.size(path);
                        System.out.println(String.format("%,d bytes", bytes));
                        if (bytes / 1024 <= 25000) {
                            if (!f.isDirectory() && !Attachment.contains(f)) {
                                Attachment.add(f);
                                txt_SelectedFiles.appendText("" + ProcessString.cutString(f.getName(), 20) + " ");
                            }
                        } else {
                            Dialog.showMessageDialog("Error", "Please Choose file that is smaller or equals with 25MB!");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
                System.out.println("You didn't choose file !");
            }
        });
        txt_SearchBar.setOnKeyReleased(event -> {
            fillTable();
        });
    }

    private void startProcess() {
        cancelTask = false;
        setStyleProgressBar();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Message message;
                    for (i = 0; i < listEmails.size() && cancelTask == false; i++) {
                        try {
                            Multipart multipart = handleMultipart();
                            Session session = SendMail();
                            String send = String.valueOf(listEmails.get(i));
                            String Subject = String.valueOf(txt_ContentField.getText().toUpperCase());
                            txt = txt_ContentField.getText() + "\n\n Thank you for choosing us!" + "\n" + ProcessDate.now();
                            message = SendMailContent(session, send, Subject, txt);
                            message.setContent(multipart);
                            Transport.send(message);
                            updateProgress(i, listEmails.size());
                            System.out.println("Sent Successfully!");

                        } catch (AddressException ex) {
                            Logger.getLogger(Mail_SendingController.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        } catch (MessagingException ex) {
                            Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

        };

        task.setOnFailed(wse -> {
            System.out.println("Task stopped, got some error");
            cancelTask = true;
            removeProgress();
        });

        task.setOnSucceeded(wse -> {
            progress.progressProperty().unbind();
            progress.setProgress(listEmails.size());
            System.out.println("Done!");
            removeProgress();

        });
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public static void sendMailsabtDiscount(Multipart multipart, List<Account> Emails, Session session, List<Application> Sales, String Sub, String Text, File avtImg)
            throws IOException, InterruptedException, MessagingException {
        setStyleProgressBar();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (i = 0; i < Emails.size() && cancelTask == false; i++) {
                    try {
                        String send = String.valueOf(Emails.get(i));
                        String subject = String.valueOf(Sub);
                        String Txt = Text;
                        BodyPart messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setText(Text);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart);
                        MimeBodyPart part = new MimeBodyPart();
                        if (avtImg != null) {
                            part.attachFile(avtImg);
                            multipart.addBodyPart(part);
                        }
                        Message message = SendMailContent(session, send, subject, Txt);
                        message.setContent(multipart);
                        Transport.send(message);
                        updateProgress(i, Emails.size());
                        System.out.println("Sent All Successfully!");

                    } catch (AddressException ex) {
                        Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MessagingException | IOException ex) {
                        Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return null;
            }

        };

        task.setOnFailed(wse -> {
            System.out.println("Task stopped, got some error");
            cancelTask = true;
            removeProgress();
        });

        task.setOnSucceeded(wse -> {
            progress.progressProperty().unbind();
            progress.setProgress(Emails.size());
            System.out.println("Done!");
            removeProgress();
        });
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    void displayFormAnimation() {
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Add_Info).play();
        new FadeInRightBig(pnl_List_News).play();
        new FadeInUpBig(pnl_Content).play();
        new FadeInUpBig(tbl_Email).play();

    }
}
