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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    private JFXCheckBox cbo_Agree;
    @FXML
    private Label lbl_Message_Register;
    @FXML
    private Label lblCountDown;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        preferences = Preferences.userNodeForPackage(LoginController.class);
        if (preferences != null) {
            if ((preferences.get("username", null) != null) && (!preferences.get("username", null).isEmpty())) {
                txt_Username_Login.setText(preferences.get("username", null));
                txt_Password_Login.setText(preferences.get("password", null));
            }
        }

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
            System.exit(0);
        });

        //input login
        txt_Username_Login.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_Password_Login.requestFocus();
                }
            }
        });
        txt_Password_Login.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_Username_Login.requestFocus();
                }
            }
        });
        txt_Username_Login.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (txt_Username_Login.getText().isEmpty()) {
                lbl_Message_Login.setText("Username cannot be empty!");
                txt_Username_Login.getStyleClass().add("txt_Incorrect");
            } else {
                lbl_Message_Login.setText("");
                txt_Username_Login.getStyleClass().add("txt_Correct");
            }
        });
        txt_Password_Login.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (txt_Password_Login.getText().isEmpty()) {
                lbl_Message_Login.setText("Password cannot be empty!");
                txt_Password_Login.getStyleClass().add("txt_Incorrect");
            } else {
                lbl_Message_Login.setText("");
                txt_Password_Login.getStyleClass().add("txt_Correct");
            }
        });

        //input Change Password
        txt_Username_ChangePass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_OTP_ChangePass.requestFocus();
                }
            }
        });
        txt_OTP_ChangePass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_NewPass_ChangePass.requestFocus();
                }
            }
        });
        txt_NewPass_ChangePass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_ComfirmPass_ChangePass.requestFocus();
                }
            }
        });
        txt_ComfirmPass_ChangePass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_Username_ChangePass.requestFocus();
                }
            }
        });
        txt_Username_ChangePass.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (txt_Username_ChangePass.getText().isEmpty()) {
                txt_Username_ChangePass.getStyleClass().add("txt_Incorrect");
                lbl_Message_ChangePass.setText("Username cannot be empty!");
            } else {
                lbl_Message_ChangePass.setText("");
                txt_Username_ChangePass.getStyleClass().add("txt_Correct");
            }
        });
        txt_OTP_ChangePass.focusedProperty().addListener((o, oldVal, newVal) -> {
            String digit = txt_OTP_ChangePass.getText().trim();

            if (txt_OTP_ChangePass.getText().isEmpty()) {
                lbl_Message_ChangePass.setText("Code OTP cannot be empty!");
                txt_OTP_ChangePass.getStyleClass().add("txt_Incorrect");
            } else {
                for (char time : digit.toCharArray()) {
                    if (Character.isDigit(time)) {
                        txt_OTP_ChangePass.getStyleClass().add("txt_Correct");
                        lbl_Message_ChangePass.setText("");
                    } else {
                        lbl_Message_ChangePass.setText("Code OTP only number");
                        txt_OTP_ChangePass.getStyleClass().add("txt_Incorrect");
                        break;
                    }
                }
            }
        });
        txt_NewPass_ChangePass.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (txt_NewPass_ChangePass.getText().isEmpty()) {
                txt_NewPass_ChangePass.getStyleClass().add("txt_Incorrect");
                lbl_Message_ChangePass.setText("New password cannot be empty!");
            } else {
                lbl_Message_ChangePass.setText("");
                txt_NewPass_ChangePass.getStyleClass().add("txt_Correct");
            }
        });
        txt_ComfirmPass_ChangePass.focusedProperty().addListener((o, oldVal, newVal) -> {

            if (txt_ComfirmPass_ChangePass.getText().isEmpty()) {
                txt_ComfirmPass_ChangePass.getStyleClass().add("txt_Incorrect");
                lbl_Message_ChangePass.setText("Comfirm password cannot be empty!");
            } else {
                if (txt_ComfirmPass_ChangePass.getText().equals(txt_NewPass_ChangePass.getText())) {
                    lbl_Message_ChangePass.setText("");
                    txt_ComfirmPass_ChangePass.getStyleClass().add("txt_Correct");
                } else {
                    lbl_Message_ChangePass.setText("Password differently!");
                    txt_ComfirmPass_ChangePass.getStyleClass().add("txt_Incorrect");
                }
            }
        });

        //input Register
        txt_Username_Register.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_Email_Register.requestFocus();
                }
            }
        });
        txt_Email_Register.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_Password_Register.requestFocus();
                }
            }
        });
        txt_Password_Register.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_ComfirmPassword_Register.requestFocus();
                }
            }
        });
        txt_ComfirmPassword_Register.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    txt_Username_Register.requestFocus();
                }
            }
        });
        txt_Username_Register.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (txt_Username_Register.getText().isEmpty()) {
                txt_Username_Register.getStyleClass().add("txt_Incorrect");
                lbl_Message_Register.setText("Username cannot be empty!");
            } else {
                lbl_Message_Register.setText("");
                txt_Username_Register.getStyleClass().add("txt_Correct");
            }
        });
        txt_Email_Register.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (txt_Email_Register.getText().isEmpty()) {
                txt_Email_Register.getStyleClass().add("txt_Incorrect");
                lbl_Message_Register.setText("Email cannot be empty!");
            } else {
                lbl_Message_Register.setText("");
                txt_Email_Register.getStyleClass().add("txt_Correct");

            }
        });
        txt_Password_Register.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (txt_Password_Register.getText().isEmpty()) {
                txt_Password_Register.getStyleClass().add("txt_Incorrect");
                lbl_Message_Register.setText("Password cannot be empty!");
            } else {
                lbl_Message_Register.setText("");
                txt_Password_Register.getStyleClass().add("txt_Correct");
            }
        });
        txt_ComfirmPassword_Register.focusedProperty().addListener((o, oldVal, newVal) -> {

            if (txt_ComfirmPassword_Register.getText().isEmpty()) {
                txt_ComfirmPassword_Register.getStyleClass().add("txt_Incorrect");
                lbl_Message_Register.setText("Comfirm password cannot be empty!");
            } else {
                if (txt_ComfirmPassword_Register.getText().equals(txt_Password_Register.getText())) {
                    lbl_Message_Register.setText("");
                    txt_ComfirmPassword_Register.getStyleClass().add("txt_Correct");
                } else {
                    lbl_Message_Register.setText("Password differently!");
                    txt_ComfirmPassword_Register.getStyleClass().add("txt_Incorrect");
                }
            }
        });

    }

    @FXML
    private void LoginAction(ActionEvent event) {
        String usename = txt_Username_Login.getText();
        String password = txt_Password_Login.getText();

        AccountDAO dao = new AccountDAO();
        Account account = dao.selectByUser(usename);
        if (usename.isEmpty() && password.isEmpty()) {
            txt_Username_Login.requestFocus();
            txt_Username_Login.getStyleClass().add("txt_Incorrect");
            txt_Password_Login.getStyleClass().add("txt_Incorrect");
            lbl_Message_Login.setText("Username and password cannot be empty!");

        } else if (usename.isEmpty() || password.isEmpty()) {
            if (usename.isEmpty()) {
                txt_Username_Login.requestFocus();
            } else {
                txt_Password_Login.requestFocus();
            }

        } else {
            if (account == null) {
                lbl_Message_Login.setText("Fail username!");
            } else if (!password.equals(account.getPassword())) {
                lbl_Message_Login.setText("Fail password!");
            } else {
                if (cbo_Remember.isSelected()) {
                    preferences.put("username", usename);
                    preferences.put("password", password);
                } else {
                    preferences.put("username", "");
                    preferences.put("password", "");
                }

                System.out.println("Login success!");
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

                Auth.user = account;
            }
        }

    }

    @FXML
    private void SendOTPAction(ActionEvent event) {
        AccountDAO dao = new AccountDAO();
        Account account = dao.selectByUser(txt_Username_ChangePass.getText());
        if (!txt_Username_ChangePass.getText().isEmpty()) {

            if (account == null) {
                lbl_Message_ChangePass.setText("Cannot find username!");
            } else {
                String send = account.getEmail();
                System.out.println(send);
                if (send != null) {
                    Random random = new Random();
                    otp = (int) Math.floor(((Math.random() * 899999) + 100000));
                    final String username = "thanhlmps18795@fpt.edu.vn ";
                    final String password = "0987654321Thanh";

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

                    try {
                        System.out.println(send);
                        check = true;
                        clock();

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("thanhlmps18795@fpt.edu.vn "));
                        message.setRecipients(
                                Message.RecipientType.TO,
                                InternetAddress.parse(send)
                        );
                        message.setSubject("Mail đổi mật khẩu");
                        message.setText("Mã xác minh thay đổi mật khẩu của bạn là: " + otp);

                        Transport.send(message);

                        System.out.println("Done");

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
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
                        lblCountDown.setText("");
                        check = false;
                    } else {
                        lblCountDown.setText(Integer.toString(count));
                        count--;
                        btn_SendOTP.setDisable(true);
                    }
                });
            }
        }, 1000, 1000);

    }

    @FXML
    private void ChangePasswordAction(ActionEvent event) {
        String username = txt_Username_ChangePass.getText();
        String otpCode = txt_OTP_ChangePass.getText();
        String password = txt_NewPass_ChangePass.getText();
        String password1 = txt_ComfirmPass_ChangePass.getText();

        if (username.isEmpty() && otpCode.isEmpty() && password.isEmpty() && password1.isEmpty()) {

            txt_Username_ChangePass.getStyleClass().add("txt_Incorrect");
            txt_OTP_ChangePass.getStyleClass().add("txt_Incorrect");
            txt_NewPass_ChangePass.getStyleClass().add("txt_Incorrect");
            txt_ComfirmPass_ChangePass.getStyleClass().add("txt_Incorrect");
            lbl_Message_ChangePass.setText("Cannot not Empty!");
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
            lbl_Message_ChangePass.setText("");
            txt_Username_ChangePass.getStyleClass().add("txt_Correct");
            txt_NewPass_ChangePass.getStyleClass().add("txt_Correct");
            txt_ComfirmPass_ChangePass.getStyleClass().add("txt_Correct");
            if (check = true) {
                if (count == -1) {
                    lbl_Message_ChangePass.setText("Code OTP too time!");
                    txt_OTP_ChangePass.getStyleClass().add("txt_Incorrect");
                    txt_OTP_ChangePass.requestFocus();
                } else {
                    if (Integer.parseInt(otpCode) == otp) {
                        lbl_Message_ChangePass.setText("");
                        txt_OTP_ChangePass.getStyleClass().add("txt_Correct");
                        AccountDAO dao = new AccountDAO();
                        Account account = new Account();

                        account.setPassword(password1);
                        account.setUserName(username);

                        dao.update_Register(account);

                        if (isChangePassForm) {
                            new SlideInLeft(pnl_ChangePass).playOnFinished(new SlideInRight(pnl_Login)).play();
                            isChangePassForm = !isChangePassForm;
                        }

                    } else {
                        lbl_Message_ChangePass.setText("Code OTP incorrect!");
                        txt_OTP_ChangePass.getStyleClass().add("txt_Incorrect");
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
        Pattern regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(email);

        if (username.isEmpty() && email.isEmpty() && password.isEmpty() && password1.isEmpty()) {
            txt_Username_Register.getStyleClass().add("txt_Incorrect");
            txt_Email_Register.getStyleClass().add("txt_Incorrect");
            txt_Password_Register.getStyleClass().add("txt_Incorrect");
            txt_ComfirmPassword_Register.getStyleClass().add("txt_Incorrect");
            lbl_Message_Register.setText("Cannot not Empty!");
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
                lbl_Message_Register.setText("Click agee with us!");
            }
        } else {
            if (matcher.find()) {
                lbl_Message_Register.setText("");
                txt_Email_Register.getStyleClass().add("txt_Correct");
                if (password1.equals(password)) {
                    lbl_Message_Register.setText("");
                    txt_ComfirmPassword_Register.getStyleClass().add("txt_Correct");
                    AccountDAO dao = new AccountDAO();
                    Account account = new Account();
                    if (dao.selectByUser(username) == null) {
                        txt_Username_Register.getStyleClass().add("txt_Correct");
                        if (dao.selectByEmail(email) == null) {
                            txt_Email_Register.getStyleClass().add("txt_Correct");
                            if (password.length() < 5) {
                                txt_Password_Register.getStyleClass().add("txt_Incorrect");
                                lbl_Message_Register.setText("Password much length more than 5!");
                            } else {
                                lbl_Message_Register.setText("");
                                txt_Password_Register.getStyleClass().add("txt_Correct");

                                account.setUserName(username);
                                account.setEmail(email);
                                account.setPassword(password);
                                dao.insert_Register(account);

                                System.out.println("Register success!");

                                MoveRight ani = new MoveRight(img_bg1, pnl_Login.getPrefWidth());
                                ani.play();
                                btn_Change.setText("Register now");

                                new SlideInUp(pnl_Register).playOnFinished(new SlideInDown(pnl_Login)).play();

                            }

                        } else {
                            lbl_Message_Register.setText("Email already taken!");
                            txt_Email_Register.getStyleClass().add("txt_Incorrect");
                        }

                    } else {
                        lbl_Message_Register.setText("Username already taken!");
                        txt_Username_Register.getStyleClass().add("txt_Incorrect");
                        txt_Username_Register.requestFocus();
                    }

                } else {
                    lbl_Message_Register.setText("Password differently!");
                    txt_ComfirmPassword_Register.getStyleClass().add("txt_Incorrect");
                }

            } else {
                lbl_Message_Register.setText("Email is invalid!");
                txt_Email_Register.getStyleClass().add("txt_Incorrect");
            }

        }

    }

}
