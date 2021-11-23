/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.ApplicationDAO;
import DAO.OrderDAO;
import DAO.OrderDetailDAO;
import animatefx.animation.SlideOutLeft;
import animatefx.animation.SlideOutRight;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Application;
import model.Order;
import model.OrderDetail;
import until.Auth;
import until.ProcessDate;
import until.ProcessImage;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author leminhthanh
 */
public class PayController implements Initializable {

    @FXML
    private ImageView img_AppIcon;
    @FXML
    private Label lbl_Code;
    @FXML
    private TextField txt_code;
    @FXML
    private Button btn_reach;
    @FXML
    private Button btn_reset;
    @FXML
    private TextField txt_CodeSale;
    @FXML
    private Button btn_momo;
    @FXML
    private Button btn_paypay;
    @FXML
    private Label lbl_Message;
    @FXML
    private Pane pnl_Code;
    @FXML
    private Label btn_Eixt_PnlCode;
    @FXML
    private Label lbl_Name;
    @FXML
    private Label lbl_Prire;
    @FXML
    private Button btn_Back;
    @FXML
    private ImageView img_AppImage;
    
    String capcha;
    Application app;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
capcha = captchaValue();

        lbl_Code.setText(capcha);

        setInformation(new ApplicationDAO().selectByID(2));

        lbl_Code.setAlignment(Pos.CENTER);
    }

    public void setInformation(Application entity) {
        this.app = entity;
        loadBasicIf();
        setEvent();
    }

    private void setEvent() {
        btn_Back.setOnMouseClicked((evt) -> {
            PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);
        });
        
        btn_reset.setOnAction(event -> {
            capcha = captchaValue();
            lbl_Code.setText(capcha);
        });

        btn_reach.setOnAction(event -> {
            VoiceManager freeVM;
            Voice voice;
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            voice = VoiceManager.getInstance().getVoice("kevin");
            if (voice != null) {
                voice.allocate();
                try {
                    voice.setRate(100);
                    voice.setPitch(125);
                    voice.setVolume(3);
                    voice.speak(capcha);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            } else {
                throw new IllegalStateException("Cannot find voice: kevin16");
            }
        });

        btn_paypay.setOnAction(event -> {
            String code = txt_code.getText();
            if (!code.isEmpty()) {
                if (code.equalsIgnoreCase(capcha)) {
                    openWebpage("https://www.paypal.com");
                    lbl_Message.setText("");
                    Order();
                    Clear();
                } else {
                    lbl_Message.setText("Code reCapcha incorrect!");
                    System.out.println("Ok " + capcha);
                }
            } else {
                lbl_Message.setText("Code reCapcha can't empty!");
            }
        });

        btn_momo.setOnAction(event -> {
            String code = txt_code.getText();
            if (!code.isEmpty()) {
                if (code.equalsIgnoreCase(capcha)) {
                    new SlideOutLeft(pnl_Code).play();
                    lbl_Message.setText("");
                    Order();
                    Clear();
                } else {
                    lbl_Message.setText("Code reCapcha incorrect!");

                    System.out.println("Ok " + capcha);
                }
            } else {
                lbl_Message.setText("Code reCapcha can't empty!");
            }
        });
        btn_Eixt_PnlCode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        new SlideOutRight(pnl_Code).play();
                    }
                }
            }
        });
    }

    private String captchaValue() {
        Random random = new Random();
        int length = 7 + (Math.abs(random.nextInt()) % 3);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int baseCharacterNumber = Math.abs(random.nextInt()) % 62;
            int Characternumber = 0;
            if (baseCharacterNumber < 26) {
                Characternumber = 65 + baseCharacterNumber;
            } else if (baseCharacterNumber < 52) {
                Characternumber = 97 + (baseCharacterNumber - 26);
            } else {
                Characternumber = 48 + (baseCharacterNumber - 52);
            }
            buffer.append((char) Characternumber);

        }
        return buffer.toString();
    }

    private void openWebpage(String url) {
        try {
            try {
                Desktop.getDesktop().browse(new URL(url).toURI());
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void Order() {

        Order order = new Order();
        Date date = new Date();
        String sdate = ProcessDate.toString(date);
        date = ProcessDate.toDate(sdate);

        order.setAccountId(Auth.USER.getAccountId());
        order.setCreationDate(date);
        order.setStatus(1);
        new OrderDAO().insert(order);
        order = new OrderDAO().selectByLastOrder(Auth.USER.getAccountId());
        OrderDetail orde = new OrderDetail();
        orde.setOrderID(order.getOrderID());
        orde.setApplicationId(app.getApplicationID());
        orde.setPrice(app.getPrice());
        orde.setSale(app.getSale());
        new OrderDetailDAO().insert(orde);
    }

    void loadBasicIf() {
        lbl_Name.setText(app.getName());
        lbl_Prire.setText(app.getPrice() + "$");

        if (app.getAppIcon() != null) {
            img_AppIcon.setImage(new Image(ProcessImage.toFile(app.getAppIcon(), "appIcon.png").toURI().toString()));
        }
        if (app.getAppImage() != null) {
            img_AppImage.setImage(new Image(ProcessImage.toFile(app.getAppImage(), "appImage.png").toURI().toString()));
        }
        RoundedImageView.RoundedImage(img_AppIcon, 32);
    }

    private void Clear() {
        txt_CodeSale.setText("");
        txt_code.setText("");
        capcha = captchaValue();
        lbl_Code.setText(capcha);
    } 
    
}
