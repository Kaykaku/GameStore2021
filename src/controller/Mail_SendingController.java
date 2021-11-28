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
import static controller.Management_AccountController.static_Mail;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private JFXTextField txt_Title;
    @FXML
    private Pane pnl_Content;
    @FXML
    private JFXTextArea txt_Content;
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

    void Clear() {
        txt_Title.setText("");
        txt_SelectedFiles.setText("");
        txt_Content.setText("");
        Attachment.clear();
        sendAll_ChBox.setSelected(false);
        for (Account acc : tbl_Email.getItems()) {
               acc.getCheckbox().setSelected(false);
                }
    }

    void setThings() {
//        txt_To.setDisable(true);
//        txt_SelectedFiles.setEditable(false);
//        txt_To.setText(static_Mail.getText());
//        tbl_Email.setOnMouseClicked((event) -> {
//            if (event.getClickCount() == 2) {
//                this.fillFieldTo();
//            }
//        });
        sendAll_ChBox.setOnMouseClicked((event) -> {
            for (Account acc : tbl_Email.getItems()) {
                if (sendAll_ChBox.isSelected()) {
                    acc.getCheckbox().setSelected(true);
                } else {
                    acc.getCheckbox().setSelected(false);
                }
            }
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
    }
    

    void fillTable() {
        String keys = "";
        Emails = AccDAO.selectMail();
        ObservableList<Account> list = FXCollections.observableArrayList(Emails);
        col_ID.setCellValueFactory(new PropertyValueFactory<>("AccountId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col_Select.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
        tbl_Email.getItems().removeAll();
        tbl_Email.setItems(list);
    }

    static void setStyleProgressBar() {
        progress.setStyle("-fx-accent: #ed6f15; ");
        progress.setPrefWidth(180);
        progress.setPrefHeight(3);
        btn_Cancel.setText("X");
        btn_Cancel.setStyle("-fx-background-color: #202020;-fx-text-fill: #ffffff; -fx-padding: 5px; -fx-border-insets: 5px; -fx-background-insets: 5px;");
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
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

//    void fillFieldTo() {
//        int index = 0;
//        index = tbl_Email.getSelectionModel().getSelectedIndex();
//        String Email = col_Email.getCellObservableValue(index).getValue();
//        if (!txt_To.getText().contains(Email)) {
//            if (txt_To.getText().isEmpty()) {
//                txt_To.appendText(Email);
//                listEmails.add(Email);
//            } else {
//                txt_To.appendText(", " + Email);
//                listEmails.add(Email);
//            }
//        } else {
//            Dialog.showMessageDialog("Notice", "You have already chosen this one!");
//        }
//    }

    Multipart handleMultipart() throws IOException, MessagingException {
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(" ");
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
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(new Runnable() {
                    public void run() {
                        static_ProgressBar.getChildren().removeAll(progress, btn_Cancel);

                    }
                });
            }
        }.start();
    }

    private void startProcessSendAll() {
        cancelTask = false;
        setStyleProgressBar();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Multipart multipart = handleMultipart();
                    Emails = AccDAO.selectEmail();
                    for (i = 0; i < Emails.size() && cancelTask == false; i++) {
                        try {
                            Session session = SendMail();
                            String send = String.valueOf(Emails.get(i));
                            String Subject = txt_Title.getText().toUpperCase();
                            String Text = txt_Content.getText() + "\n\n Thank you for choosing us!" + "\n" + ProcessDate.now();
                            Message message = SendMailContent(session, send, Subject, Text);
                            message.setContent(multipart);
                            Transport.send(message);
                            updateProgress(i, Emails.size());
                            System.out.println("Sent Successfully!");
                        } catch (AddressException ex) {
                            Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MessagingException | IOException ex) {
                            Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);

                        }
                    }
                } catch (IOException | MessagingException ex) {
                    Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

        };

        task.setOnFailed(wse -> {
            System.out.println("Task stopped, got some error");
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

    private void startProcess() {
        cancelTask = false;
        setStyleProgressBar();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Multipart multipart = handleMultipart();
                    Emails = AccDAO.selectEmail();
                    for (i = 0; i < listEmails.size() && cancelTask == false; i++) {
                        try {
                            updateProgress(i, listEmails.size());
                            Session session = SendMail();
                            String send = String.valueOf(listEmails.get(i));
                            String Subject = txt_Title.getText().toUpperCase();
                            String Text = txt_Content.getText() + "\n\n Thank you for choosing us!" + "\n" + ProcessDate.now();
                            Message message = SendMailContent(session, send, Subject, Text);
                            message.setContent(multipart);
                            Transport.send(message);
                            System.out.println("Sent Successfully!");

                        } catch (AddressException ex) {
                            Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MessagingException | IOException ex) {
                            Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(Mail_SendingController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

        };

        task.setOnFailed(wse -> {
            System.out.println("Task stopped, got some error");
        });

        task.setOnSucceeded(wse -> {
            progress.progressProperty().unbind();
            progress.setProgress(Emails.size());
            System.out.println("Done!");
            removeProgress();
            // static_ProgressBar.getChildren().remove(progress);

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

    @FXML
    private void handleButtonSend(ActionEvent event) throws InterruptedException, IOException, MessagingException {
        if (sendAll_ChBox.isSelected()) {
            startProcessSendAll();
            static_Stage.close();
            this.Clear();
        } else {     
                for (Account acc : tbl_Email.getItems()) {
                if (acc.getCheckbox().isSelected()) {
                    listEmails.add(acc.getEmail());
                    System.out.println("Sending.......");
                    startProcess();
                    static_Stage.close();    
                    this.Clear();
                } else {
                    listEmails.remove(acc.getEmail());
                    this.Clear();
                }
            }
        }
        
    }

    @FXML
    private void handleAttachment(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        List<File> file = fileChooser.showOpenMultipleDialog(((Node) (event.getSource())).getScene().getWindow());
        if (file != null) {
            for (File f : file) {
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
            }
        } else {
            System.out.println("You didn't choose file !");
        }
    }

    void displayFormAnimation() {
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Add_Info).play();
        new FadeInRightBig(pnl_List_News).play();
        new FadeInUpBig(pnl_Content).play();
        new FadeInUpBig(tbl_Email).play();

    }
}
