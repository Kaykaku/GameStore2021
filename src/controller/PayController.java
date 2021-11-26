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
import DAO.WishlistDAO;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInUp;
import animatefx.animation.SlideOutLeft;
import animatefx.animation.SlideOutRight;
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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
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
import until.ProcessDate;
import until.ProcessImage;
import until.Validation;
import until.Value;
import static until.Value.FORM_HOME_GAMES;
import static until.Value.FORM_LIBRARY;
import static until.Value.FORM_PRODUCT_LIST;
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
    private Button btn_momo;
    @FXML
    private Button btn_paypay;
    @FXML
    private Label lbl_Message;
    @FXML
    private Pane pnl_Code;
    @FXML
    private Button btn_Back;

    String capcha;
    double total = 0;
    double minus = 0;
    int quantity = 0;
    int count = 0;
    Application app;
    Account user = Auth.USER;

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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        capcha = captchaValue();
        lbl_Code.setText(capcha);

        setEvent();
        setInformation(new ApplicationDAO().selectByID(1));
        setInformations();
        pnl_Code.setOpacity(0);
        lbl_Code.setAlignment(Pos.CENTER);
        lbl_Code.setStyle("-fx-background-color: #616161");
    }

    public void setInformation(Application entity) {
        this.app = entity;
        loadBasic();

    }

    public void setInformations() {
        loadWishList();
    }

    private void setEvent() {
        btn_continueShopping.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FORM_PRODUCT_LIST));
            Node node;
            try {
                node = (Node) loader.load();
                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                Logger.getLogger(DisplayProductController.class.getName()).log(Level.SEVERE, null, ex);
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

        List<Wishlist> wishlists = new WishlistDAO().selectByAccountID(user.getAccountId());

        btn_paypay.setOnAction(event -> {
            String code = txt_code.getText();
            if (!code.isEmpty()) {
                if (cbo_agree.isSelected()) {

                    if (code.equalsIgnoreCase(capcha)) {
                        if (app.getApplicationID() == 1) {
                            flag = true;
                            loadWishList();
                        } else {
                            Order(app);
                        }
                        new SlideOutLeft(pnl_main).playOnFinished(new SlideInUp(pnl_finis)).play();
                        openWebpage("https://www.paypal.com");
                        lbl_Message.setText("");
                        Clear();
                    } else {
                        lbl_Message.setText("Code reCapcha incorrect!");
                        System.out.println("Ok " + capcha);
                    }
                } else {
                    lbl_Message.setText("You not agree with the payment!");
                }
            } else {
                lbl_Message.setText("Code reCapcha can't empty!");
            }
        });
        btn_momo.setOnAction(event -> {
            String code = txt_code.getText();
            if (!code.isEmpty()) {
                if (cbo_agree.isSelected()) {
                    if (code.equalsIgnoreCase(capcha)) {
                        new SlideInUp(pnl_Code).play();
                        clock();
                        if (app.getApplicationID() == 1) {
                            flag = true;
                            loadWishList();
                        } else {
                            Order(app);
                        }                        
                        lbl_Message.setText("");
                        Clear();
                    } else {
                        lbl_Message.setText("Code reCapcha incorrect!");

                        System.out.println("Ok " + capcha);
                    }
                } else {
                    lbl_Message.setText("You not agree with the payment!");
                }
            } else {
                lbl_Message.setText("Code reCapcha can't empty!");
            }
        });

    }

    boolean flag = false;

    public List<Wishlist> loadWishList() {
        total = Validation.price;
        List<Wishlist> wishlists = new WishlistDAO().selectByAccountID(user.getAccountId());
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_WISHLIST));
            double height = (paneP.getPrefHeight() + vbox_ListProduct.getSpacing()) * wishlists.size();
            pnl_List.setPrefHeight(height);
            vbox_ListProduct.setPrefSize(paneP.getPrefWidth(), height);

            vbox_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[wishlists.size()];
            Row_WishlistController[] controllers = new Row_WishlistController[wishlists.size()];
            for (int i = 0; i < wishlists.size(); i++) {
                final int h = i;

                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.ROW_WISHLIST));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();
                Application app = new ApplicationDAO().selectByID(wishlists.get(h).getApplicatonId());
                controllers[h].setInfo(app);

                quantity = h + 1;
                Button btn_delete = controllers[h].getBtnDelete();
                btn_delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            nodes[h].getChildren().remove(0);
                            wishlists.remove(h);
                            if (mouseEvent.getClickCount() == 2) {
                                minus = controllers[h].getPrice();
                                quantity -= 1;
                                total = total - minus;

                                lbl_Total_Price.setText(String.format("%.2f", total) + "$");
                                lbl_quantity.setText(Integer.toString(quantity));

                                System.out.println("Minus 1: " + minus);

                                QRcode(String.valueOf(total));

                                double height = (paneP.getPrefHeight() + vbox_ListProduct.getSpacing()) * (wishlists.size() - 1);
                                pnl_List.setPrefHeight(height);
                                vbox_ListProduct.setPrefSize(paneP.getPrefWidth(), height);
                            }

                        }
                    }

                });

                if (flag == true) {
                    System.out.println("...");
                    Order(app);

                }
                vbox_ListProduct.getChildren().add(nodes[h]);
            }
        } catch (Exception e) {
        }
        System.out.println("quantity: " + quantity);

        lbl_Total_Price.setText(total + "$");
        lbl_quantity.setText(Integer.toString(quantity));
        QRcode(String.valueOf(total));

        return wishlists;
    }

    void loadBasic() {
        try {
            vbox_ListProduct.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.ROW_WISHLIST));
            Node node = (Node) loader.load();
            Row_WishlistController controller = loader.getController();
            controller.setInfo(app);

            vbox_ListProduct.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(PayController.class.getName()).log(Level.SEVERE, null, ex);
        }
        lbl_Total_Price.setText(app.getPrice() + "$");
        lbl_quantity.setText("1");
        QRcode(String.valueOf(app.getPrice()));
    }

    public void QRcode(String content) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        String title = "";
        int width = 250;
        int height = 250;
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

            System.out.println("Success...");

        } catch (WriterException ex) {
            ex.printStackTrace();
        }

        img_QRCODE.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

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

    private void Order(Application app) {

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
        new WishlistDAO().delete(user.getAccountId(), app.getApplicationID());
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

    private void clock() {
        count = 30;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (count == -1) {
                        timer.cancel();
                        lbl_message_pnlCode.setText("Scane QR code");
                        pnl_Code.setOpacity(0);
                        new SlideOutLeft(pnl_main).playOnFinished(new SlideInUp(pnl_finis)).play();
                    } else {
                        lbl_message_pnlCode.setText("Scane QR code (" + count + ")");
                        count--;
                        pnl_Code.setOpacity(1);
                    }
                });
            }
        }, 1000, 1000);

    }

}