/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.OrderDAO;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Application;
import model.Order;
import until.Auth;
import until.Dialog;
import until.DownloadTask;
import until.MailSender;
import until.MailTemplate;
import until.ProcessDate;
import until.ProcessImage;
import until.ProcessString;
import until.Value;
import static until.Value.FORM_DISPLAY_PRODUCT;
import until.Variable;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Library_Product_BoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_Refund;

    @FXML
    private Label lbl_AppName;

    @FXML
    private Label lbl_DatePurchased;

    @FXML
    private ImageView img_AppImage;

    @FXML
    private ImageView img_download;

    @FXML
    private JFXButton btn_ViewDetails;

    @FXML
    private VBox vbox_btn;

    LibraryController controller;
    Application application;
    OrderDAO dao = new OrderDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void setInfo(Application application) {
        this.application = application;
        if (application.getAppIcon() != null) {
            Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(application.getAppIcon()), null);
            img_AppImage.setImage(image);

        }
        lbl_AppName.setText(application.getName());
        Order order = dao.selectByAccountAppID(Auth.USER.getAccountId(), application.getApplicationID());
        lbl_DatePurchased.setText("Purchased since " + ProcessDate.toString(order.getCreationDate()));

        btn_ViewDetails.setOnMouseClicked(evt -> {
            //PNL_VIEW.getChildren().clear();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FORM_DISPLAY_PRODUCT));
                Node node = (Node) loader.load();
                DisplayProductController controller = loader.getController();
                controller.setInformation(application);
                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_Refund.setOnMouseClicked((event) -> {
            if (Dialog.showComfirmDialog("", "Refund this app will also refund any app in the same bill. Money will be automatically transferred to the purchased account. Do you have a refund confirmation?") != 1) {
                return;
            }
            order.setStatus(2);
            dao.update(order);;
            new MailSender().startProgress(Auth.USER, MailTemplate.getOrderTitleEmail(order), MailTemplate.getOrderEmail(order));
            controller.fillLibraryBox();
        });
        img_download.setOnMouseClicked((event) -> {
            if(Variable.IS_DOWNLOADING==true){
                int i =Dialog.showComfirmDialog("", "Another game is downloaing ! \n Do you want cancel old download and start download this game?");
                if(i!=1){
                    return;
                }
            }
            Variable.IS_DOWNLOADING=false;
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
            fc.setTitle("Select folder");
            fc.setInitialFileName("Examle.jpg");
            File path = fc.showSaveDialog((Stage) img_download.getScene().getWindow());
            if (path != null) {
                new DownloadTask(path, Value.EXAMPLE_LINK, ProcessString.cutString(application.getName(), 15)).start();
            }

        });
    }

    void setController(LibraryController controller) {
        this.controller = controller;
    }

    void setRefund(boolean flag) {
        if (!flag) {
            vbox_btn.getChildren().remove(1);
            vbox_btn.setLayoutY(vbox_btn.getLayoutY() + 50);
        }
    }
}
