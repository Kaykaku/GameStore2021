/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import until.QRDecoder;
import until.Variable;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Dialog_TakePictureController implements Initializable {

    @FXML
    private ImageView img_WebcamView;

    @FXML
    private JFXButton btn_Again;

    @FXML
    private Button btn_Exit;

    @FXML
    private JFXButton btn_Take;

    @FXML
    private Pane pnl_Dialog;
    /**
     * Initializes the controller class.
     */
    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private final QRDecoder qrDecoder = new QRDecoder();
    boolean isTake = false;

    private Webcam webcam;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startCameraInput();
        setEvent();
        
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
    boolean stopCamera = false;

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

    private void setEvent() {
        btn_Take.setOnMouseClicked(event -> {
            stopCamera = true;
            btn_Take.setText("SAVE PHOTO");
            if (isTake) {
                Variable.AVATAR = img_WebcamView.getImage();
                webcam.close();
                Stage stage = (Stage) btn_Exit.getScene().getWindow();
                stage.close();
            }
            isTake = true;
        });
        btn_Again.setOnMouseClicked(event -> {
            isTake = false;
            stopCamera = false;
            startWebCamStream();
        });
        btn_Exit.setOnMouseClicked((event) -> {
            webcam.close();
            Variable.AVATAR = null;
            Stage stage = (Stage) btn_Exit.getScene().getWindow();
            stage.close();
        });
    }

}
