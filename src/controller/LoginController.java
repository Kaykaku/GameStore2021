/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.MoveLeft;
import Animation.MoveRight;
import DAO.AccountDAO;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Account;
import until.Auth;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Modality;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import until.Value;

/**
 * FXML Controller class
 *
 * @author Admin
 */
/**
 * FXML Controller class
 *
 * @author Admin
 */
public class LoginController implements Initializable {

    private Preferences preferences;

    @FXML
    private Pane pnl_Register;

    @FXML
    private JFXButton btn_Login;

    @FXML
    private Circle btn_bg1;

    @FXML
    private Pane pnl_Login;

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
    int count;
    int otp;
    boolean check = false;
    boolean isRegisterForm = false;
    boolean isChangePassForm = false;
    Random random = new Random();

    @FXML
    private TextField txt_Username_Login;
    @FXML
    private PasswordField txt_Password_Login;
    @FXML
    private JFXCheckBox cbo_Remember;
    @FXML
    private Label lbl_Message_Login;
    @FXML
    private TextField txt_Username_ChangePass;
    @FXML
    private TextField txt_OTP_ChangePass;
    @FXML
    private PasswordField txt_NewPass_ChangePass;
    @FXML
    private PasswordField txt_ComfirmPass_ChangePass;
    @FXML
    private JFXButton btn_SendOTP;
    @FXML
    private JFXButton btn_ChangePass;
    @FXML
    private Label lbl_Message_ChangePass;
    @FXML
    private TextField txt_Username_Register;
    @FXML
    private TextField txt_Email_Register;
    @FXML
    private PasswordField txt_Password_Register;
    @FXML
    private PasswordField txt_ComfirmPassword_Register;
    @FXML
    private JFXButton btn_Register;
    @FXML
    private JFXButton btn_LoginQRcode;
    @FXML
    private JFXCheckBox cbo_Agree;
    @FXML
    private Label lbl_Message_Register;
    @FXML
    private Label lblCountDown;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbo_Remember.setSelected(true);

        preferences = Preferences.userNodeForPackage(LoginController.class);
        if (preferences != null) {
            if ((preferences.get("username", null) != null) && (!preferences.get("username", null).isEmpty())) {
                txt_Username_Login.setText(preferences.get("username", null));
                txt_Password_Login.setText(preferences.get("password", null));
            }
        }

        setEvent();
    }

    private void setEvent() {
        btn_Minimize.setOnMouseClicked((event) -> {
            Stage obj = (Stage) pnl_Login.getScene().getWindow();
            obj.setIconified(true);
        });
        
        
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
            SetEmpty();
        });

        lbl_ForgotPassword.setOnMouseClicked((evt) -> {
            if (!isChangePassForm) {
                new SlideOutRight(pnl_Login).playOnFinished(new SlideOutLeft(pnl_ChangePass)).play();
                isChangePassForm = !isChangePassForm;
            }
            SetEmpty();
        });
        btn_Back.setOnMouseClicked((evt) -> {
            new SlideInLeft(pnl_ChangePass).playOnFinished(new SlideInRight(pnl_Login)).play();
            isChangePassForm = !isChangePassForm;
            SetEmpty();
        });
        btn_Exit.setOnMouseClicked((event) -> {
            System.exit(0);
        });

        //input login
        setOnKeyPressed(txt_Username_Login, txt_Password_Login);
        setOnKeyPressed(txt_Password_Login, txt_Password_Login);

        txt_Username_Login.setOnKeyReleased(event -> {
            if (txt_Username_Login.getText().isEmpty()) {
                Messages(lbl_Message_Login, "Username cannot be empty!");
                Incorrect(txt_Username_Login);
            } else {
                Messages(lbl_Message_Login, "");
                Correct(txt_Username_Login);
            }
        });
        txt_Password_Login.setOnKeyReleased(event -> {
            if (txt_Password_Login.getText().isEmpty()) {
                Messages(lbl_Message_Login, "Password cannot be empty!");
                Incorrect(txt_Password_Login);
            } else {
                if (txt_Password_Login.getText().length() > 5) {
                    Messages(lbl_Message_Login, "");
                    Correct(txt_Password_Login);
                } else {
                    Messages(lbl_Message_Login, "Password much length more than 5!");
                    Incorrect(txt_Password_Login);
                }
            }
        });
        btn_LoginQRcode.setOnMouseClicked((event) -> {
            try {
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                Parent root = FXMLLoader.load(getClass().getResource(Value.DIALOG_SCANQRCODE));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.showAndWait();
                if (Auth.USER != null) {
                    try {
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        Parent parent = FXMLLoader.load(getClass().getResource("/gui/Main/GameStore.fxml"));
                        Stage stagez = new Stage();
                        stagez.initStyle(StageStyle.UNDECORATED);
                        stagez.setScene(new Scene(parent));
                        stagez.show();

                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(User_InformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //input Change Password
        setOnKeyPressed(txt_Username_ChangePass, txt_OTP_ChangePass);
        setOnKeyPressed(txt_OTP_ChangePass, txt_NewPass_ChangePass);
        setOnKeyPressed(txt_NewPass_ChangePass, txt_ComfirmPass_ChangePass);
        setOnKeyPressed(txt_ComfirmPass_ChangePass, txt_Username_ChangePass);

        txt_Username_ChangePass.setOnKeyReleased(event -> {
            if (txt_Username_ChangePass.getText().isEmpty()) {
                Incorrect(txt_Username_ChangePass);
                btn_SendOTP.setDisable(true);
                Messages(lbl_Message_ChangePass, "Username cannot be empty!");
            } else {
                Messages(lbl_Message_ChangePass, "");
                Correct(txt_Username_ChangePass);
                btn_SendOTP.setDisable(false);
            }
        });
        txt_OTP_ChangePass.setOnKeyReleased(event -> {
            String digit = txt_OTP_ChangePass.getText().trim();

            if (txt_OTP_ChangePass.getText().isEmpty()) {
                Messages(lbl_Message_ChangePass, "Code OTP cannot be empty!");
                Incorrect(txt_OTP_ChangePass);
            } else {
                Correct(txt_OTP_ChangePass);
                Messages(lbl_Message_ChangePass, "");
            }
        });
        txt_OTP_ChangePass.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                            String newValue
                    ) {
                        if (!newValue.matches("\\d*")) {
                            txt_OTP_ChangePass.setText(newValue.replaceAll("[^\\d]", ""));
                            Incorrect(txt_OTP_ChangePass);
                            Messages(lbl_Message_ChangePass, "OTP only digit!");
                        } else {
                            Correct(txt_OTP_ChangePass);
                            Messages(lbl_Message_ChangePass, "");
                        }
                    }
                }
                );
        txt_NewPass_ChangePass.setOnKeyReleased(event -> {
            if (txt_NewPass_ChangePass.getText().trim().isEmpty()) {
                Incorrect(txt_NewPass_ChangePass);
                Messages(lbl_Message_ChangePass, "New password cannot be empty!");
            } else {
                if (txt_NewPass_ChangePass.getText().length() > 5) {
                    Messages(lbl_Message_ChangePass, "");
                    Correct(txt_NewPass_ChangePass);
                } else {
                    Incorrect(txt_NewPass_ChangePass);
                    Messages(lbl_Message_ChangePass, "Password much length more than 5!");
                }
            }
        }
        );
        txt_ComfirmPass_ChangePass.setOnKeyReleased(event -> {
            if (txt_ComfirmPass_ChangePass.getText().isEmpty()) {
                Incorrect(txt_ComfirmPass_ChangePass);
                Messages(lbl_Message_ChangePass, "Comfirm password cannot be empty!");
            } else {
                if (txt_ComfirmPass_ChangePass.getText().equals(txt_NewPass_ChangePass.getText())) {
                    Messages(lbl_Message_ChangePass, "");
                    Correct(txt_ComfirmPass_ChangePass);
                } else {
                    Messages(lbl_Message_ChangePass, "Password differently!");
                    Incorrect(txt_ComfirmPass_ChangePass);
                }
            }
        }
        );

        //input Register
        setOnKeyPressed(txt_Username_Register, txt_Email_Register);
        setOnKeyPressed(txt_Email_Register, txt_Password_Register);
        setOnKeyPressed(txt_Password_Register, txt_ComfirmPassword_Register);
        setOnKeyPressed(txt_ComfirmPassword_Register, txt_Username_Register);

        txt_Username_Register.setOnKeyReleased(event -> {
            if (txt_Username_Register.getText().trim().isEmpty()) {
                Incorrect(txt_Username_Register);
                Messages(lbl_Message_Register, "Username cannot be empty!");
            } else {
                Messages(lbl_Message_Register, "");
                Correct(txt_Username_Register);
            }
        }
        );

        txt_Email_Register.setOnKeyReleased(event -> {
            String email = txt_Email_Register.getText();
            if (email.isEmpty()) {
                Incorrect(txt_Email_Register);
                Messages(lbl_Message_Register, "Email cannot be empty!");
            } else {

                String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches()) {
                    Messages(lbl_Message_Register, "Email is invalid!");

                } else {
                    Messages(lbl_Message_Register, "");
                    Correct(txt_Email_Register);
                }

            }
        }
        );

        txt_Password_Register.setOnKeyReleased(event -> {
            if (txt_Password_Register.getText().trim().isEmpty()) {
                Incorrect(txt_Password_Register);
                Messages(lbl_Message_Register, "Password cannot be empty!");
            } else {
                if (txt_Password_Register.getText().length() > 5) {
                    Messages(lbl_Message_Register, "");
                    Correct(txt_Password_Register);
                } else {
                    Incorrect(txt_Password_Register);
                    Messages(lbl_Message_Register, "Password much length more than 5!");
                }
            }
        }
        );
        txt_ComfirmPassword_Register.setOnKeyReleased(event -> {
            if (txt_ComfirmPassword_Register.getText().isEmpty()) {
                Incorrect(txt_ComfirmPassword_Register);
                Messages(lbl_Message_Register, "Comfirm password cannot be empty!");
            } else {
                if (txt_ComfirmPassword_Register.getText().equals(txt_Password_Register.getText())) {
                    Messages(lbl_Message_Register, "");
                    Correct(txt_ComfirmPassword_Register);
                } else {
                    Messages(lbl_Message_Register, "Password differently!");
                    Incorrect(txt_ComfirmPassword_Register);
                }
            }
        }
        );
    }

    @FXML
    private void LoginAction(ActionEvent event) {
        String usename = txt_Username_Login.getText();
        String password = txt_Password_Login.getText();

        AccountDAO dao = new AccountDAO();
        Account account = dao.selectByUser(usename);
        if (usename.isEmpty() && password.isEmpty()) {
            txt_Username_Login.requestFocus();
            Incorrect(txt_Username_Login);
            Incorrect(txt_Password_Login);
            Messages(lbl_Message_Login, "Username and password cannot be empty!");

        } else if (usename.isEmpty() || password.isEmpty()) {
            if (usename.isEmpty()) {
                txt_Username_Login.requestFocus();
            } else {
                txt_Password_Login.requestFocus();
            }
        } else {
            if (account == null) {
                Messages(lbl_Message_Login, "Fail username!");
                Incorrect(txt_Username_Login);
            } else if (!password.equals(account.getPassword())) {
                Messages(lbl_Message_Login, "Fail password!");
                Incorrect(txt_Password_Login);
            } else {
                if (account.isActive()) {
                    if (cbo_Remember.isSelected()) {
                        preferences.put("username", usename);
                        preferences.put("password", password);

                        System.out.println("Login success!");
                        Auth.USER = account;
                        try {
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                            Parent root = FXMLLoader.load(getClass().getResource("/gui/Main/GameStore.fxml"));
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(new Scene(root));
                            stage.show();

                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        preferences.put("username", "");
                        preferences.put("password", "");
                    }
                } else {
                    Messages(lbl_Message_Login, "You account is blocked!");
                    Incorrect(txt_Password_Login);
                    Incorrect(txt_Username_Login);
                }
            }
        }

    }

    @FXML
    private void SendOTPAction(ActionEvent event) {
        otp = (int) Math.floor(((Math.random() * 899999) + 100000));
        AccountDAO dao = new AccountDAO();
        Account account = dao.selectByUser(txt_Username_ChangePass.getText());
        if (account == null) {
            Messages(lbl_Message_ChangePass, "Cannot find username!");
        } else {
            String send = account.getEmail();
            System.out.println(send);
            if (send != null) {

                Session session = SendMail();

                new Thread(new Runnable() {
                    public void run() {
                        Message message;
                        try {
                            message = SendMailContent(session, send);
                            Transport.send(message);
                        } catch (MessagingException ex) {
                            ex.printStackTrace();
                        }

                    }
                }).start();

                check = true;
                clock();
                System.out.println("Done");

            }
        }
    }

    @FXML
    private void ChangePasswordAction(ActionEvent event) {
        String username = txt_Username_ChangePass.getText();
        String otpCode = txt_OTP_ChangePass.getText();
        String password = txt_NewPass_ChangePass.getText();
        String password1 = txt_ComfirmPass_ChangePass.getText();

        if (username.isEmpty() && otpCode.isEmpty() && password.isEmpty() && password1.isEmpty()) {

            Incorrect(txt_Username_ChangePass);
            Incorrect(txt_OTP_ChangePass);
            Incorrect(txt_NewPass_ChangePass);
            Incorrect(txt_ComfirmPass_ChangePass);
            Messages(lbl_Message_ChangePass, "Cannot not Empty!");
        } else if (username.isEmpty() || otpCode.isEmpty() || password.isEmpty() || password1.isEmpty()) {
            if (username.isEmpty()) {
                txt_Username_ChangePass.requestFocus();
            } else if (otpCode.isEmpty()) {
                txt_OTP_ChangePass.requestFocus();
            } else if (password.isEmpty()) {
                txt_NewPass_ChangePass.requestFocus();
            } else if (password1.isEmpty()) {
                txt_ComfirmPass_ChangePass.requestFocus();
            }
        } else {
            Messages(lbl_Message_ChangePass, "");
            Correct(txt_Username_ChangePass);
            Correct(txt_NewPass_ChangePass);
            Correct(txt_ComfirmPass_ChangePass);
            if (check = true) {
                if (count == -1) {
                    Messages(lbl_Message_ChangePass, "Code OTP too time!");
                    Incorrect(txt_OTP_ChangePass);
                    txt_OTP_ChangePass.requestFocus();
                } else {
                    if (Integer.parseInt(otpCode) == otp) {
                        Messages(lbl_Message_ChangePass, "");
                        Correct(txt_OTP_ChangePass);
                        AccountDAO dao = new AccountDAO();
                        Account account = new Account();

                        account.setPassword(password1);
                        account.setUsername(username);

                        dao.update_Register(account);

                        SetEmpty();

                        if (isChangePassForm) {
                            new SlideInLeft(pnl_ChangePass).playOnFinished(new SlideInRight(pnl_Login)).play();
                            isChangePassForm = !isChangePassForm;
                        }

                    } else {
                        Messages(lbl_Message_ChangePass, "Code OTP incorrect!");
                        Incorrect(txt_OTP_ChangePass);
                        txt_OTP_ChangePass.requestFocus();
                    }
                }
            }
        }
    }

    @FXML
    private void RegisterAction(ActionEvent event) {

        String username = txt_Username_Register.getText();
        String email = txt_Email_Register.getText();
        String password = txt_Password_Register.getText();
        String password1 = txt_ComfirmPassword_Register.getText();
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (username.isEmpty() && email.isEmpty() && password.isEmpty() && password1.isEmpty()) {
            Incorrect(txt_Username_Register);
            Incorrect(txt_Email_Register);
            Incorrect(txt_Password_Register);
            Incorrect(txt_ComfirmPassword_Register);
            Messages(lbl_Message_Register, "Cannot not Empty!");
        } else if (username.isEmpty() || email.isEmpty() || password.isEmpty() || password1.isEmpty() || !cbo_Agree.isSelected()) {
            if (username.isEmpty()) {
                txt_Username_Register.requestFocus();
            } else if (email.isEmpty()) {
                txt_Email_Register.requestFocus();
            } else if (password.isEmpty()) {
                txt_Password_Register.requestFocus();
            } else if (password1.isEmpty()) {
                txt_ComfirmPassword_Register.requestFocus();
            } else if (!cbo_Agree.isSelected()) {
                Messages(lbl_Message_Register, "Click agee with us!");
            }
        } else if (!matcher.matches()) {
            Messages(lbl_Message_Register, "Email is invalid!");
            Incorrect(txt_Email_Register);
        } else {
            Messages(lbl_Message_Register, "");
            Correct(txt_Email_Register);
            if (password1.equals(password)) {
                Messages(lbl_Message_Register, "");
                Correct(txt_ComfirmPassword_Register);
                AccountDAO dao = new AccountDAO();
                Account account = new Account();
                if (dao.selectByUser(username) == null) {
                    Correct(txt_Username_Register);
                    if (dao.selectByEmail(email) == null) {
                        Correct(txt_Email_Register);
                        if (password.length() < 5) {
                            Incorrect(txt_Password_Register);
                            Messages(lbl_Message_Register, "Password much length more than 5!");
                        } else {
                            Messages(lbl_Message_Register, "");
                            Correct(txt_Password_Register);
                            Date creationDate = new Date();
                            account.setUsername(username);
                            account.setEmail(email);
                            account.setPassword(password);
                            account.setCreationDate(creationDate);
                            account.setRole(2);
                            account.setActive(true);
                            dao.insert_Register(account);

                            System.out.println("Register success!");
                            SetEmpty();

                            MoveRight ani = new MoveRight(img_bg1, pnl_Login.getPrefWidth());
                            ani.play();
                            btn_Change.setText("Register now");

                            new SlideInUp(pnl_Register).playOnFinished(new SlideInDown(pnl_Login)).play();

                        }

                    } else {
                        Messages(lbl_Message_Register, "Email already taken!");
                        Incorrect(txt_Email_Register);
                    }

                } else {
                    Messages(lbl_Message_Register, "Username already taken!");
                    Incorrect(txt_Username_Register);
                    txt_Username_Register.requestFocus();
                }

            } else {
                Messages(lbl_Message_Register, "Password differently!");
                Incorrect(txt_ComfirmPassword_Register);
            }
        }

    }

    private Message SendMailContent(Session session, String send) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("thanhlmps18795@fpt.edu.vn "));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(send)
        );
        message.setSubject("Mail đổi mật khẩu");
        message.setText("Mã xác minh thay đổi mật khẩu của bạn là: " + otp);
        return message;
    }

    private Session SendMail() {
        final String username = "GamexStore.ST@gmail.com";
        final String password = "GamexStore.ST123";
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

    private void clock() {
        count = 59;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (count == -1) {
                        btn_SendOTP.setDisable(false);
                        timer.cancel();
                        btn_SendOTP.setText("Send OTP");
                        otp = otp + 9999;
                        check = false;
                    } else {
                        btn_SendOTP.setText("Send OTP " + count);
                        count--;
                        btn_SendOTP.setDisable(true);
                    }
                });
            }
        }, 1000, 1000);

    }

    private void setOnKeyPressed(TextField textField1, TextField textField2) {
        textField1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    textField2.requestFocus();
                }
            }
        });
    }

    private void SetEmpty() {
        txt_Username_Login.setText("");
        txt_Password_Login.setText("");
        txt_Username_ChangePass.setText("");
        txt_OTP_ChangePass.setText("");
        txt_ComfirmPass_ChangePass.setText("");
        txt_NewPass_ChangePass.setText("");
        txt_Username_Register.setText("");
        txt_Email_Register.setText("");
        txt_ComfirmPassword_Register.setText("");
        txt_Password_Register.setText("");
    }

    private void Messages(Label label, String string) {
        label.setText(string);
    }

    public void Incorrect(TextField name) {
        name.setStyle("-fx-border-color: red");
    }

    public void Correct(TextField name) {
        name.setStyle("-fx-border-color: #fff");
    }

}
