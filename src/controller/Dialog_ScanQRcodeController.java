/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Account;
import until.Auth;
import until.QRDecoder;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Dialog_ScanQRcodeController implements Initializable {

    @FXML
    private ImageView img_WebcamView;

    @FXML
    private Button btn_Exit;

    @FXML
    private Label lbl_Message;

    @FXML
    private Pane pnl_Dialog;

    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private final QRDecoder qrDecoder = new QRDecoder();

    private Webcam webcam;
    List<Account> list;
    boolean stopCamera = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = new AccountDAO().selectAll();
        startCameraInput();

        btn_Exit.setOnMouseClicked((event) -> {
            webcam.close();
            Stage stage = (Stage) btn_Exit.getScene().getWindow();
            stage.close();
        });
    }

    private void startCameraInput() {
        Task<Void> webCamTask = new Task<Void>() {
            @Override
            protected Void call() {
                webcam = Webcam.getDefault();
                webcam.open();
                startWebCamStream();

                return null;
            }
        };

        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    private void startWebCamStream() {
        

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() {
                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage img;

                //noinspection ConstantConditions,LoopConditionNotUpdatedInsideLoop
                while (!stopCamera) {
                    try {
                        if ((img = webcam.getImage()) != null) {
                            ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                            img.flush();
                            Platform.runLater(() -> imageProperty.set(ref.get()));

                            String scanResult = qrDecoder.decodeQRCode(img);
                            if (scanResult != null) {
                                onQrResult(scanResult);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        img_WebcamView.imageProperty().bind(imageProperty);
        RoundedImageView.RoundedImage(img_WebcamView, 32);
    }

    private void onQrResult(String scanResult) {
        Platform.runLater(() -> {
            boolean flag = false;
            for (Account account : list) {
                if (scanResult.equals(account.getQRcode())) {
                    lbl_Message.setText("Hello ," + account.getName() + "/n This window will close in one second...");
                    flag = true;
                    Auth.USER=account;
                    break;
                }
            }
            if (!flag) {
                lbl_Message.setText("This is not login QR code");
            } else {
                webcam.close();
                stopCamera=true;
                Stage stage = (Stage) btn_Exit.getScene().getWindow();
                stage.close();
                
            }
        });
    }
}
