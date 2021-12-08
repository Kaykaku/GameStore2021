/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ApplicationDAO;
import DAO.OrderDAO;
import DAO.OrderDetailDAO;
import DAO.WishlistDAO;
import animatefx.animation.SlideInUp;
import animatefx.animation.SlideOutLeft;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Account;
import model.Application;
import model.Order;
import model.OrderDetail;
import model.Wishlist;
import until.Auth;
import until.Dialog;
import until.MailSender;
import until.MailTemplate;
import until.ProcessDate;
import until.Validation;
import until.Value;
import static until.Value.FORM_LIBRARY;
import static until.Value.FORM_PRODUCT_LIST;
import until.Variable;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author leminhthanh
 */
public class PayController implements Initializable {

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
    private Button btn_paypay;
    @FXML
    private Label lbl_Message;
    @FXML
    private Pane pnl_Code;
    @FXML
    private Button btn_Back;

    @FXML
    private Pane pnl_Price_Info;
    @FXML
    private Label lbl_Total_Price;
    @FXML
    private ScrollPane pnl_ScrollList;
    @FXML
    private Pane pnl_List;
    @FXML
    private VBox vbox_ListProduct;
    @FXML
    private ImageView img_QRCODE;
    @FXML
    private Label lbl_quantity;
    @FXML
    private Label lbl_message_pnlCode;
//    private Pane pnl_finis;
//    private JFXButton btn_continueShopping;
//    private Hyperlink hpl_orderlink;
    @FXML
    private JFXCheckBox cbo_agree;
    @FXML
    private Pane pnl_finis;
    @FXML
    private JFXButton btn_continueShopping;
    @FXML
    private Hyperlink hpl_orderlink;
    @FXML
    private Pane pnl_main;

    /**
     * Initializes the controller class.
     */
    String capcha;
    double total = 0;
    double minus = 0;
    int quantity = 0;
    int count = 0;
    List<Application> apps = new ArrayList<>();
    Application app ;
    Account user = Auth.USER;
    ApplicationDAO dao = new ApplicationDAO();
    WishlistController controllerW=null;
    DisplayProductController controllerD=null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        capcha = captchaValue();
        lbl_Code.setText(capcha);
        setEvent();
        lbl_Code.setAlignment(Pos.CENTER);
        lbl_Code.setStyle("-fx-background-color: #616161");
    }

    public void setInformation(Application entity, DisplayProductController controller) {
        controllerD=controller;
        apps.add(entity);
        app=entity;
        loadList();
    }

    public void setInformations(WishlistController controller) {
        controllerW=controller;
        List<Wishlist> wishlists = new WishlistDAO().selectByAccountID(user.getAccountId());
        for (Wishlist wishlist : wishlists) {
            apps.add(dao.selectByID(wishlist.getApplicationId()));
        }
        loadList();
    }

    private void setEvent() {
        btn_continueShopping.setOnAction(event -> {
            PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);
            if(controllerD!=null){
                controllerD.setInformation(app);
            }else{
                controllerW.loadWishList();
            }
        });
        hpl_orderlink.setOnMouseClicked((evt) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FORM_LIBRARY));
            Node node;
            try {
                node = (Node) loader.load();
                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                Logger.getLogger(DisplayProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_Back.setOnMouseClicked((evt) -> {
            PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);
        });

        btn_reset.setOnAction(event -> {
            capcha = captchaValue();
            lbl_Code.setText(capcha);
        });

        btn_reach.setOnAction(event -> {

            new Thread(new Runnable() {
                public void run() {
                    try {
                        VoiceManager freeVM;
                        Voice voice;
                        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                        voice = VoiceManager.getInstance().getVoice("kevin");
                        if (voice != null) {
                            voice.allocate();
                            try {

                                voice.setRate(80);
                                voice.setPitch(125);
                                voice.setVolume(3);
                                for (char c : capcha.toString().toCharArray()) {
                                    voice.speak(c + "");
                                    Thread.sleep(400);
                                }

                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        } else {
                            throw new IllegalStateException("Cannot find voice: kevin16");
                        }
                    } catch (IllegalStateException ex) {
                        ex.printStackTrace();
                    }

                }
            }).start();
        });

        btn_paypay.setOnMouseClicked(event -> {
            String code = txt_code.getText();
            if (apps.size() == 0) {
                Dialog.showMessageDialog("", "Empty List!");
                return;
            }
            if (code.isEmpty()) {
                lbl_Message.setText("Code reCapcha can't empty!");
            } else if (!cbo_agree.isSelected()) {
                lbl_Message.setText("You not agree with the payment!");
            } else if (code.equalsIgnoreCase(capcha)) {
                insertOrder(apps);
                new SlideOutLeft(pnl_main).playOnFinished(new SlideInUp(pnl_finis)).play();
                openWebpage("https://www.paypal.com");
                lbl_Message.setText("");
                Clear();
            } else {
                lbl_Message.setText("Code reCapcha incorrect!");
                System.out.println("Ok " + capcha);
            }
        });

    }

    boolean flag = false;

    public void loadList() {
        total = 0;

        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_WISHLIST));
            double height = (paneP.getPrefHeight() + vbox_ListProduct.getSpacing()) * apps.size();
            pnl_List.setPrefHeight(height);
            pnl_List.setStyle("-fx-background-color : transparent");
            vbox_ListProduct.setPrefSize(paneP.getPrefWidth(), height);

            vbox_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[apps.size()];
            Row_WishlistController[] controllers = new Row_WishlistController[apps.size()];
            for (int i = 0; i < apps.size(); i++) {
                final int h = i;
                total += apps.get(h).getPrice() * (100 - apps.get(h).getSale()) / 100;
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.ROW_WISHLIST));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();
                controllers[h].setOpacity();
                controllers[h].setInfo(apps.get(h));

                quantity = h + 1;
                Button btn_delete = controllers[h].getBtnDelete();
                btn_delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            apps.remove(h);
                            loadList();
                        }
                    }

                });
                vbox_ListProduct.getChildren().add(nodes[h]);
            }
        } catch (Exception e) {
        }
        lbl_Total_Price.setText(String.format("%.2f", total) + "$");
        lbl_quantity.setText(apps.size() + "");
        QRcode(String.format("%.2f", total) + "$");
    }

    private void QRcode(String content) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        String title = "";
        int width = 350;
        int height = 350;
        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
        } catch (WriterException ex) {
            ex.printStackTrace();
        }

        img_QRCODE.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

    }

    private String captchaValue() {
        Random random = new Random();
        int length = 6;
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
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void insertOrder(List<Application> list) {

        Order order = new Order();
        Date date = new Date();
        String sdate = ProcessDate.toString(date);
        date = ProcessDate.toDate(sdate);

        order.setAccountId(Auth.USER.getAccountId());
        order.setCreationDate(date);
        order.setStatus(1);
        new OrderDAO().insert(order);
        Order lastOrder = new OrderDAO().selectByLastOrder(Auth.USER.getAccountId());
        for (Application app : list) {
            OrderDetail orde = new OrderDetail();
            orde.setOrderID(lastOrder.getOrderID());
            orde.setApplicationId(app.getApplicationID());
            orde.setPrice(app.getPrice());
            orde.setSale(app.getSale());
            new OrderDetailDAO().insert(orde);
            new WishlistDAO().delete(user.getAccountId(), app.getApplicationID());
        }
        new MailSender().startProgress(Auth.USER, MailTemplate.getOrderTitleEmail(lastOrder), MailTemplate.getOrderEmail(lastOrder));

//        try {
//            DisplayProductController controller = new DisplayProductController();
//            controller.loadStatusAll(app);
//        } catch (Exception e) {
//            Logger.getLogger(DisplayProductController.class.getName()).log(Level.SEVERE, null, e);
//        }
    }

    private void Clear() {
        txt_CodeSale.setText("");
        txt_code.setText("");
        capcha = captchaValue();
        lbl_Code.setText(capcha);
    }

//    private void clock() {
//        count = 30;
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
//                    if (count == -1) {
//                        timer.cancel();
//                        lbl_message_pnlCode.setText("Scane QR code");
//                        pnl_Code.setOpacity(0);
//                        new SlideOutLeft(pnl_main).playOnFinished(new SlideInUp(pnl_finis)).play();
//                    } else {
//                        lbl_message_pnlCode.setText("Scane QR code (" + count + ")");
//                        count--;
//                        pnl_Code.setOpacity(1);
//                    }
//                });
//            }
//        }, 1000, 1000);
//
//    }
}
