/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import DAO.ApplicationDAO;
import DAO.CategoryDAO;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import model.Account;
import model.Application;
import until.Catch_Errors;
import until.Dialog;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
import until.ProcessDate;
import until.ProcessImage;
import until.Value;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_ProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextArea txt_Description;

    @FXML
    private JFXButton btn_clear;

    @FXML
    private JFXTextField txt_Price;

    @FXML
    private JFXTextField txt_Published;

    @FXML
    private JFXToggleButton tog_EnableBuy;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private TextField txt_SreachApp;

    @FXML
    private VBox box_ListProduct;

    @FXML
    private Pane pnl_Image;

    @FXML
    private JFXButton btn_Add;

    @FXML
    private JFXTextField txt_Languages;

    @FXML
    private Pane pnl_List_Product;

    @FXML
    private Pane pnl_Description;

    @FXML
    private HBox hbox_Controller;

    @FXML
    private JFXTextField txt_Size;

    @FXML
    private Pane pnl_Controller;

    @FXML
    private Label lbl_GameID;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private JFXButton btn_Update;

    @FXML
    private JFXTextField txt_Sale;

    @FXML
    private JFXTextField txt_Name;

    @FXML
    private JFXTextField txt_Developed;

    @FXML
    private Pane pnl_Title;

    @FXML
    private ImageView Img_Icon;

    @FXML
    private Pane pnl_Status;

    @FXML
    private JFXToggleButton tog_Active;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private Label lbl_OnSale;

    @FXML
    private ImageView Img_Game;

    @FXML
    private Pane pnl_List;

    @FXML
    private Pane pnl_Category;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Pane pnl_ReleaseDate;

    @FXML
    private JFXButton btn_DPFProduct;
    
    @FXML
    private JFXButton btn_ExcelProduct;
    @FXML
    private JFXButton btn_TextProduct;
    
    @FXML
    private JFXButton btn_sendSale;

    @FXML
    private JFXButton btn_sendGames;
    
    
    ApplicationDAO applicationDAO = new ApplicationDAO();
    List<Application> listApplications = new ArrayList<>();
    List<Account> Emails = new ArrayList<>();
    AccountDAO AccDAO = new AccountDAO();
    ApplicationDAO appDAO = new ApplicationDAO();
    CategoryDAO categoryDao = new CategoryDAO();
    JFXDatePicker datePicker_CreationDate = new JFXDatePicker();
    JFXDatePicker datePicker_ReleaseDay = new JFXDatePicker();
    Image image = new Image("icons/add-image (1).png");
    boolean isEdit = false;
    File avatarIcon;
    File avatarImage;
    File avtImg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.showDatePicker();
        this.EventSearch();
        this.ExportPDFProduct();
        this.ExportExcelProduct();
        this.ExportTextProduct();
        this.fillListApplication();
        this.displayFormAnimation();
        btn_Update.setDisable(true);
        btn_delete.setDisable(true);
        ProcessDate.converter(datePicker_CreationDate);
        ProcessDate.converter(datePicker_ReleaseDay);

    }

    private void fillListApplication() {
        listApplications = applicationDAO.selectByKeyWord(txt_SreachApp.getText().trim());

        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_PRODUCT));
            double height = (paneP.getPrefHeight() + box_ListProduct.getSpacing()) * listApplications.size();
            box_ListProduct.setPrefSize(paneP.getPrefWidth(), height);
            pnl_List.setPrefHeight(height > pnl_ScrollList.getPrefHeight() ? height : pnl_ScrollList.getPrefHeight());

            box_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[listApplications.size()];
            Row_ProductController[] controllers = new Row_ProductController[listApplications.size()];

            for (int i = 0; i < listApplications.size(); i++) {
                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.ROW_PRODUCT));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                box_ListProduct.getChildren().add(nodes[h]);
                controllers[h].setAppInfo(listApplications.get(h));

                nodes[h].setOnMouseClicked(evt -> {
                    setFormApp(listApplications.get(h));
                    btn_Add.setDisable(true);
                    btn_Update.setDisable(false);
                    btn_delete.setDisable(false);
                });

                nodes[h].setOnMouseClicked(evt -> {
                    setFormApp(listApplications.get(h));
                    btn_Add.setDisable(true);
                    btn_Update.setDisable(false);
                    btn_delete.setDisable(false);
                });

                nodes[h].setOnMouseClicked(evt -> {
                    setFormApp(listApplications.get(h));
                    btn_Add.setDisable(true);
                    btn_Update.setDisable(false);
                    btn_delete.setDisable(false);

                });
            }
        } catch (Exception e) {

        }
    }

    private void showDatePicker() {
        datePicker_CreationDate.setValue(ProcessDate.toLocalDate(ProcessDate.now()));
        datePicker_CreationDate.setEditable(false);
        datePicker_CreationDate.setDefaultColor(Paint.valueOf("lightblue"));
        datePicker_ReleaseDay.setDefaultColor(Paint.valueOf("lightblue"));
        datePicker_CreationDate.setStyle("-fx-text-fill: green");
        datePicker_ReleaseDay.setStyle("-fx-text-fill: #ffffff");
        pnl_CreationDate.getChildren().add(datePicker_CreationDate);
        pnl_ReleaseDate.getChildren().add(datePicker_ReleaseDay);
    }

    private void setAvatarIcon() {
        if (avatarIcon != null) {
            Img_Icon.setImage(new Image(avatarIcon.toURI().toString()));
        } else {
            Img_Icon.setImage(new Image(new File("icons/add-image (1).png").toURI().toString()));
        }
        RoundedImageView.RoundedImage(Img_Icon, 200);
    }

    private void setAvatarImage() {
        if (avatarImage != null) {
            Img_Game.setImage(new Image(avatarImage.toURI().toString()));
        } else {
            Img_Game.setImage(new Image(new File("icons/add-image (1).png").toURI().toString()));
        }
        RoundedImageView.RoundedImage(Img_Game, 32);
    }

    public void setFormApp(Application entity) {
        lbl_GameID.setText(entity.getApplicationID() + "");
        txt_Name.setText(entity.getName());
        txt_Price.setText(entity.getPrice() + "");
        txt_Size.setText(entity.getSize() + "");
        if (entity.getAppImage() != null) {
            avatarImage = ProcessImage.toFile(entity.getAppImage(), "avatar.png");
        }
        setAvatarImage();
        if (entity.getAppIcon() != null) {
            avatarIcon = ProcessImage.toFile(entity.getAppIcon(), "avatar.png");
        }
        setAvatarIcon();
        txt_Developed.setText(entity.getDeveloper());
        txt_Published.setText(entity.getPublisher());
        datePicker_CreationDate.setValue(ProcessDate.toLocalDate(entity.getCreationDate()));
        datePicker_ReleaseDay.setValue(ProcessDate.toLocalDate(entity.getReleaseDay()));
        txt_Languages.setText(entity.getLanguages());
        txt_Sale.setText(entity.getSale() + "");
        txt_Description.setText(entity.getDescription());
        tog_Active.setSelected(entity.isActive());
        tog_EnableBuy.setSelected(entity.isEnableBuy());
    }
    int s;

    private Application getForm() {
        Application App = new Application();
        App.setName(txt_Name.getText().trim());
        App.setPrice(Float.parseFloat(txt_Price.getText()));
        App.setSize(Float.parseFloat(txt_Size.getText()));
        App.setAppImage(ProcessImage.toBytes(new File("/icons/add-image (1).png")));
        if (avatarImage != null) {
            App.setAppImage(ProcessImage.toBytes(avatarImage));
        }
        App.setAppIcon(ProcessImage.toBytes(new File("/icons/add-image (1).png")));
        if (avatarIcon != null) {
            App.setAppIcon(ProcessImage.toBytes(avatarIcon));
        }
        App.setDeveloper(txt_Developed.getText());
        App.setPublisher(txt_Published.getText());
        App.setCreationDate(ProcessDate.toDate((datePicker_CreationDate.getValue())));
        App.setReleaseDay(ProcessDate.toDate(datePicker_ReleaseDay.getValue()));
        App.setLanguages(txt_Languages.getText());
        App.setSale(Float.parseFloat(txt_Sale.getText()));
        App.setDescription(txt_Description.getText());
        App.setActive(tog_Active.isSelected());
        App.setEnableBuy(tog_EnableBuy.isSelected());
        if (lbl_GameID.getText().equals("Game ID") == false) {
            App.setApplicationID(Integer.parseInt(lbl_GameID.getText()));
        } else {
            App.setApplicationID(s);
        }
        return App;

    }

    private void setApplication(Application entity) {
        lbl_GameID.setText("Game ID");
        txt_Name.setText(isEdit ? entity.getName() : "");
        txt_Price.setText(isEdit ? entity.getPrice() + "" : "");
        txt_Size.setText(isEdit ? entity.getSize() + "" : "");
        Img_Game.setImage(image);
        Img_Icon.setImage(image);
        txt_Developed.setText(entity.getDeveloper());
        txt_Published.setText(entity.getPublisher());
        datePicker_CreationDate.setValue(ProcessDate.toLocalDate(ProcessDate.now()));
        datePicker_ReleaseDay.setValue(null);
        txt_Languages.setText(entity.getLanguages());
        txt_Sale.setText(entity.getSale() + "");
        txt_Description.setText("");
        tog_Active.setSelected(false);
        tog_EnableBuy.setSelected(false);
    }
    private void sendMailAbtSale() throws IOException, MessagingException, InterruptedException{
        avtImg = null;
        Mail_SendingController msd = new Mail_SendingController();
        Multipart multipart = msd.handleMultipart();
        Emails = AccDAO.selectEmail();
        Session session = Mail_SendingController.SendMail();
        listApplications = appDAO.selectSale();
        String Subject = "GAMESTORE IS NOW HAVING A REALLY BIG DISCOUNT";
        int x = listApplications.size();
        String Text= "Hi, How are you doing, we are now having "+x+" games are on sale"
        + "  \nwhich are gonna blow your mind, open GameXStore and check it out!"+
        "\n\n Thank you for choosing us!"+"\n"+ProcessDate.now();

        Mail_SendingController.sendMailsabtDiscount(multipart,Emails,session,listApplications,Subject,Text,avtImg);
    }
    @SuppressWarnings("empty-statement")
    private void sendMailAbtGame() throws IOException, InterruptedException, MessagingException{
        Mail_SendingController msd = new Mail_SendingController();
        Multipart multipart = msd.handleMultipart();
        Emails = AccDAO.selectEmail();
        Session session = Mail_SendingController.SendMail();
        listApplications = appDAO.selectLastApp();
        String Subject = "GAMESTORE JUST HAVE GOT A NEW GAME - GO CHECK IT OUT";
        int x = listApplications.size();
        String Text= "Hi, How are you doing again?, A new Game just went on sale,It is "+listApplications.get(0).getName()+
        ", \nDeveloped by "+listApplications.get(0).getDeveloper()+" which is gonna blow your mind, open GameXStore and check it out!"+
        "\n\n Thank you for choosing us!"+"\n"+ProcessDate.now();;
            avtImg = ProcessImage.toFile(listApplications.get(0).getAppImage(), "avatar.png");
        Mail_SendingController.sendMailsabtDiscount(multipart,Emails,session,listApplications,Subject,Text,avtImg);
        
    }
    private void insert() {
        Application App = getForm();
        try {
            applicationDAO.insert(App);
            fillListApplication();
            this.Clear();
            Dialog.showMessageDialog("Notice", "Inserted Successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Update() {
        Application App = getForm();
        try {
            applicationDAO.update(App);
            fillListApplication();
            this.Clear();
            Dialog.showMessageDialog("Notice", "Updated Successful!");
        } catch (Exception e) {

        }
    }

    private void Delete() {
        Integer ID = Integer.parseInt(lbl_GameID.getText().trim());
        try {
            applicationDAO.delete(ID);
            fillListApplication();
            this.Clear();
        } catch (Exception e) {
        }

    }

    private void Clear() {
        btn_Add.setDisable(false);
        btn_Update.setDisable(true);
        btn_delete.setDisable(true);
        this.setApplication(new Application());
    }

    private void EventSearch() {
        txt_SreachApp.setOnKeyReleased(evt -> {
            fillListApplication();
            if (listApplications.size() > 0) {
                setFormApp(listApplications.get(0));
            }
        });
    }

    private void ExportPDFProduct() {
        btn_DPFProduct.setOnAction(evt -> {
            try {
                ExportPDF.exportPDFProduct();
                Dialog.showMessageDialog(null, "File save successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
     private void ExportExcelProduct() {
        btn_ExcelProduct.setOnAction(evt -> {
            String[] header = new String[]{"ID", "Name", "Price", "Size", "Image", "Icon", "Developer", "Publisher", "ReleaseDay",
                "CreationDate", "Languages","Sale","Active","EnableBuy", "Description"};
            List<Application> list = applicationDAO.selectAll();
            List<Object[]> listObjs = new ArrayList<>();
            list.forEach((Application) -> {
                listObjs.add(Application.toObjects());
            });
            String fileName = "Product";
            String title = "Application List";
            try {
                ExportExcel.exportFile(null, header, listObjs, fileName, title);
            } catch (IOException ex) {
                
            }
        });
    }
     private void ExportTextProduct() {
        btn_TextProduct.setOnAction(evt -> {       
              ExportText.ExportFileProduct();
        });
    }

    @FXML
    private void handleImageGame(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        avatarImage = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
        if (avatarImage != null) {
            setAvatarImage();
        }
    }

    @FXML
    private void handleImageIcon(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        avatarIcon = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
        if (avatarIcon != null) {
            setAvatarIcon();
        }
    }

    @FXML
    private void handleButtonClearAction(ActionEvent event) {
        this.Clear();
    }

    @FXML
    private void handleButtonAddAction(ActionEvent event) {
        if (Catch_Errors.validationImageProduct(avatarImage)
                && Catch_Errors.validationImageProduct(avatarIcon)
                && Catch_Errors.check_TextProduct(txt_Name)
                && Catch_Errors.check_FloatProduct(txt_Price)
                && Catch_Errors.check_FloatProduct(txt_Size)) {
            if (Catch_Errors.check_TextProduct(txt_Developed)
                    && Catch_Errors.check_Languages(txt_Languages)
                    && Catch_Errors.check_TextProduct(txt_Published)) {
                if (Catch_Errors.check_FloatProduct(txt_Sale)
                        && Catch_Errors.validationReleaseDay(datePicker_ReleaseDay)
                        && Catch_Errors.check_TextAreaProduct(txt_Description)) {
                    this.insert();
                }
            }

        }
    }

    @FXML
    private void handleButtonUpdateAction(ActionEvent event) {
        if (Catch_Errors.check_TextProduct(txt_Name)
                && Catch_Errors.check_FloatProduct(txt_Price)
                && Catch_Errors.check_FloatProduct(txt_Size)) {
            if (Catch_Errors.check_TextProduct(txt_Developed)
                    && Catch_Errors.check_TextProduct(txt_Published)
                    && Catch_Errors.check_Languages(txt_Languages)
                    && Catch_Errors.check_FloatProduct(txt_Sale)
                    && Catch_Errors.validationReleaseDay(datePicker_ReleaseDay)) {
                if (Catch_Errors.check_TextAreaProduct(txt_Description)) {
                    this.Update();
                }
            }

        }
    }

    @FXML
    private void handleButtonDeleteAction(ActionEvent event) {
        try {
            this.Delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleButtonSendSales(ActionEvent event) throws IOException, MessagingException, InterruptedException {
        this.sendMailAbtSale();
    }
    @FXML
    private void handleButtonSendGames(ActionEvent event) throws IOException, MessagingException, InterruptedException {
        this.sendMailAbtGame();
    }

    private void displayFormAnimation() {
        new FadeInDownBig(pnl_Basic_Info).play();
        new FadeInDown(pnl_Image).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Category).play();
        new FadeInLeftBig(pnl_Add_Info).play();
        new FadeInRightBig(pnl_List_Product).play();
        new FadeInUp(pnl_Status).play();
        new FadeInUpBig(pnl_Description).play();
        new ZoomInUp(hbox_Controller).play();
        GlowText ani = new GlowText(lbl_OnSale, Color.WHITE, Color.RED);
        ani.setCycleCount(Integer.MAX_VALUE);
        ani.play();
    }

    private Object ExportPDF() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
