/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import until.Auth;
import until.Dialog;
import until.Value;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Dialog_CreateQRcodeController implements Initializable {

    @FXML
    private Button btn_Exit;

    @FXML
    private JFXButton btn_CreateQRcode;

    @FXML
    private ImageView img_QRcode;

    @FXML
    private JFXButton btn_SaveQRcode;

    @FXML
    private Pane pnl_Dialog;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Auth.USER.getQRcode() != null) {
            readQRcode(Auth.USER.getQRcode());
        }
        RoundedImageView.RoundedImage(img_QRcode, 32);
        setEvent();
    }

    void setEvent() {
        btn_Exit.setOnMouseClicked((event) -> {
            Stage stage = (Stage) btn_Exit.getScene().getWindow();
            stage.close();
        });
        btn_CreateQRcode.setOnMouseClicked((event) -> {
            createQRcode();
        });
        btn_SaveQRcode.setOnMouseClicked((event) -> {
            chooseImage();
        });
    }

    private void chooseImage() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose directory");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png"));
            fileChooser.setInitialFileName("QRcode.png");
            fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                BufferedImage img =SwingFXUtils.fromFXImage(img_QRcode.getImage(), null);              
                ImageIO.write(img, "png", file);
                if (Dialog.showComfirmDialog(null, "Save successfully! \n Do you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (file.exists()) {
                        desktop.open(file);
                    }
                }
            }
        } catch (IOException e) {

        }
    }

    void createQRcode() {
        String data = getAlphaNumericString(10);
        Auth.USER.setQRcode(data);
        new AccountDAO().updateQRcode(Auth.USER);
        readQRcode(data);
    }

    void readQRcode(String QRcode) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(QRcode, BarcodeFormat.QR_CODE, 400, 400);

            Image image = SwingFXUtils.toFXImage(MatrixToImageWriter.toBufferedImage(matrix), null);
            img_QRcode.setImage(image);
            RoundedImageView.RoundedImage(img_QRcode, 32);
        } catch (WriterException ex) {
        }
    }

    private String getAlphaNumericString(int n) {
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));
        StringBuilder r = new StringBuilder();
        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }
        return r.toString();
    }
}
