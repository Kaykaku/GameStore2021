/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.PulseShort;
import Animation.RoundedImageView;
import DAO.AccountDAO;
import DAO.AppTypeDAO;
import DAO.ApplicationDAO;
import DAO.CategoryDAO;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import model.Account;
import model.AppType;
import model.Application;
import model.Category;
import until.Catch_Errors;
import until.Dialog;
import until.ExportExcel;
import until.ExportText;
import until.ProcessDate;
import until.ProcessImage;
import until.ProcessString;
import until.Validation;
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
    private ImageView Img_AppImage;

    @FXML
    private JFXTextArea txt_Description;

    @FXML
    private JFXToggleButton tog_Display;

    @FXML
    private JFXTextField txt_Price;

    @FXML
    private JFXTextField txt_Published;

    @FXML
    private JFXToggleButton tog_EnableBuy;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private Pane pnl_Container;

    @FXML
    private TextField txt_SreachApp;

    @FXML
    private ImageView Img_AppIcon;

    @FXML
    private VBox box_ListProduct;

    @FXML
    private Pane pnl_Image;

    @FXML
    private JFXButton btn_Add;

    @FXML
    private JFXButton btn_Delete;

    @FXML
    private Circle circle;
    
    @FXML
    private JFXTextField txt_Languages;

    @FXML
    private JFXComboBox<String> cbo_Category;

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
    private Pane pnl_Status;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private Label lbl_CategoryCount;

    @FXML
    private JFXToggleButton tog_Type;

    @FXML
    private JFXButton btn_AddCategory;

    @FXML
    private JFXButton btn_Clear;

    @FXML
    private JFXButton btn_DPFProduct;
    
    @FXML
    private JFXButton btn_ExcelProduct;
    
    @FXML
    private JFXButton btn_TextProduct;

    @FXML
    private Label lbl_OnSale;

    @FXML
    private Pane pnl_List;

    @FXML
    private Label lbl_Message;

    @FXML
    private Pane pnl_Category;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Pane pnl_ReleaseDate;

    ApplicationDAO applicationDAO = new ApplicationDAO();
    List<Application> listApplications = new ArrayList<>();
    List<Account> Emails = new ArrayList<>();
    AccountDAO AccDAO = new AccountDAO();
    ApplicationDAO appDAO = new ApplicationDAO();
    CategoryDAO categoryDao = new CategoryDAO();
    AppTypeDAO appTypeDAO = new AppTypeDAO();
    JFXDatePicker datePicker_CreationDate = new JFXDatePicker();
    JFXDatePicker datePicker_ReleaseDay = new JFXDatePicker();
    boolean isEdit = false;
    File avatarIcon;
    File avatarImage;
    File avtImg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.showDatePicker();
        this.displayFormAnimation();
        loadAppCategories(new Application());
        setEvent();
        ExportExcelProduct();
        ExportTextProduct();
        fillDataOnBackground();
        updateStatus();
    }

    void fillDataOnBackground() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    fillCboCategory();
                    fillListApplication();
                });
            }
        }.start();
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
                    isEdit = true;
                    edit();
                    for (Row_ProductController controller : controllers) {
                        controller.setSelected(false);
                    }
                    controllers[h].setSelected(true);
                    new PulseShort(pnl_Image).play();
                    new PulseShort(pnl_Basic_Info).play();
                    setFormApp(listApplications.get(h));

                    btn_Update.setDisable(false);
                    btn_Delete.setDisable(false);
                });
            }
        } catch (IOException e) {

        }
    }

    void fillCboCategory() {
        List<Category> list = categoryDao.selectAll();
        List<String> categories = new ArrayList<>();
        list.forEach((category) -> {
            categories.add(category.getName());
        });

        cbo_Category.getItems().clear();
        cbo_Category.getItems().addAll(categories);
    }

    void loadAppCategories(Application entity) {
        List<AppType> list = appTypeDAO.selectByApplicationId(entity.getApplicationID());
        lbl_CategoryCount.setText(list.size() + "");

        pnl_Container.getChildren().clear();
        double width = 0, x = 10, y = 10;
        if(list.size()==0){
            lbl_CategoryCount.setText("1");
            Category ca = categoryDao.selectByID(1);
            Label label = new Label(ca.getName());
            label.setStyle("-fx-background-radius : 10px; -fx-text-fill : white; -fx-background-color : " + ca.getColor());
            pnl_Container.getChildren().add(label);

            x += width;
            if ((x * 1.2) > pnl_Container.getPrefWidth()) {
                x = 10;
                y += 30;
            }
            label.setLayoutX(x);
            label.setLayoutY(y);
            label.setPadding(new Insets(2, 10, 2, 10));
            FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
            label.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.REGULAR, 16));
            width = fontLoader.computeStringWidth(label.getText(), label.getFont()) + 30;
            x = label.getLayoutX();
        }
        for (AppType appType : list) {
            Category ca = categoryDao.selectByID(appType.getCategoryId());
            Label label = new Label(ca.getName());
            label.setStyle("-fx-background-radius : 10px; -fx-text-fill : white; -fx-background-color : " + ca.getColor());
            pnl_Container.getChildren().add(label);

            x += width;
            if ((x * 1.2) > pnl_Container.getPrefWidth()) {
                x = 10;
                y += 30;
            }
            label.setLayoutX(x);
            label.setLayoutY(y);
            label.setPadding(new Insets(2, 10, 2, 10));
            FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
            label.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.REGULAR, 16));
            width = fontLoader.computeStringWidth(label.getText(), label.getFont()) + 30;
            x = label.getLayoutX();

            label.setOnMouseClicked((event) -> {

                if (appType.getCategoryId() == 1) {
                    ProcessString.showMessage(lbl_Message, "Category ALL is default!");
                    return;
                }
                appTypeDAO.delete(appType);
                ProcessString.showMessage(lbl_Message, "Successfully deleted!");
                setFormApp(applicationDAO.selectByID(appType.getApplicationID()));
            });
            final Tada ani = new Tada(label);
            ani.setCycleCount(Integer.MAX_VALUE);
            label.setOnMouseEntered((evt) -> {
                ani.play();
            });
            label.setOnMouseExited((evt) -> {
                ani.stop();
                label.setScaleX(1);
                label.setScaleY(1);
                label.setScaleZ(1);
                label.setRotate(0);
            });
        }
    }

    private void showDatePicker() {
        datePicker_CreationDate.setValue(ProcessDate.toLocalDate(ProcessDate.now()));
        datePicker_CreationDate.setEditable(false);
        pnl_CreationDate.getChildren().add(datePicker_CreationDate);
        pnl_ReleaseDate.getChildren().add(datePicker_ReleaseDay);
        ProcessDate.converter(datePicker_CreationDate);
        ProcessDate.converter(datePicker_ReleaseDay);
        datePicker_ReleaseDay.setDefaultColor(Color.LIGHTBLUE);
        datePicker_CreationDate.setDefaultColor(Color.LIGHTBLUE);
        datePicker_CreationDate.setDisable(true);
    }

    private void setAvatarIcon() {
        if (avatarIcon != null) {
            Img_AppIcon.setImage(new Image(avatarIcon.toURI().toString()));
        } else {
            Img_AppIcon.setImage(new Image(new File("src/icons/refresh120.png").toURI().toString()));
        }
        RoundedImageView.RoundedImage(Img_AppIcon, 200);
    }

    private void setAvatarImage() {
        if (avatarImage != null) {
            Img_AppImage.setImage(new Image(avatarImage.toURI().toString()));
        } else {
            Img_AppImage.setImage(new Image(new File("src/icons/icons8_picture_200px_1.png").toURI().toString()));
        }
        RoundedImageView.RoundedImage(Img_AppImage, 32);
    }

    public void setFormApp(Application entity) {
        loadAppCategories(entity);
        lbl_GameID.setText(isEdit ? entity.getApplicationID() + "" : "");
        txt_Name.setText(isEdit ? entity.getName() + "" : "");
        double number =(double) Math.round(entity.getPrice()*100)/100;
        txt_Price.setText(isEdit ? number + "" : "");
        number =(double) Math.round(entity.getSize()*100)/100;
        txt_Size.setText(isEdit ? number + "" : "");
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
        datePicker_CreationDate.setValue(isEdit ? ProcessDate.toLocalDate(entity.getCreationDate()) : LocalDate.now());
        datePicker_ReleaseDay.setValue(isEdit ? ProcessDate.toLocalDate(entity.getReleaseDay()) : null);
        txt_Languages.setText(entity.getLanguages());
        number =(double) Math.round(entity.getSale()*100)/100;
        txt_Sale.setText(isEdit ? number + "" : "");
        txt_Description.setText(isEdit ? entity.getDescription() + "" : "");
        tog_Display.setSelected(entity.isActive());
        tog_Type.setSelected(entity.isType());
        tog_EnableBuy.setSelected(entity.isEnableBuy());
    }

    private Application getForm() {
        String err = "";
        err += Validation.validationJFXTextFieldLength(txt_Name, "APPLICATION NAME", 3, 100);
        err += Validation.validationPrice(txt_Price);
        err += Validation.validationSize(txt_Size);
        err += Validation.validationImage(Img_AppIcon, avatarIcon , circle, "APPICON");
        err += Validation.validationImage(Img_AppImage, avatarImage, "APP IMAGE");
        err += Validation.validationSale(txt_Sale);
        err += Validation.validationDate(datePicker_ReleaseDay, "RELEASE DATE");
        err += Validation.validationJFXTextAreaLength(txt_Description, "DESCRIPTION", 10, 4000);
        if (err.isEmpty()) {
            Application app = new Application();
            app.setApplicationID(isEdit?Integer.parseInt(lbl_GameID.getText().trim()):-1);
            app.setName(txt_Name.getText().trim());
            app.setPrice(Double.parseDouble(txt_Price.getText().trim()));
            app.setSize(Double.parseDouble(txt_Size.getText().trim()));
            app.setAppImage(ProcessImage.toBytes(new File("/icons/add-image (1).png")));
            if (avatarImage != null) {
                app.setAppImage(ProcessImage.toBytes(avatarImage));
            }
            app.setAppIcon(ProcessImage.toBytes(new File("/icons/add-image (1).png")));
            if (avatarIcon != null) {
                app.setAppIcon(ProcessImage.toBytes(avatarIcon));
            }
            app.setDeveloper(txt_Developed.getText());
            app.setPublisher(txt_Published.getText());
            app.setCreationDate(ProcessDate.toDate((datePicker_CreationDate.getValue())));
            app.setReleaseDay(ProcessDate.toDate(datePicker_ReleaseDay.getValue()));
            app.setLanguages(txt_Languages.getText());
            app.setSale(Double.parseDouble(txt_Sale.getText().trim().isEmpty()?0+"":txt_Sale.getText().trim()));
            app.setDescription(txt_Description.getText());
            app.setActive(tog_Display.isSelected());
            app.setType(tog_Type.isSelected());
            app.setEnableBuy(tog_EnableBuy.isSelected());
            return app;
        }
        ProcessString.showMessage(lbl_Message, "An error occurred on the form!");
        Dialog.showMessageDialog("Wrong data", err);
        return null;
    }

    private void clearForm() {
        isEdit = false;
        avatarIcon = null;
        avatarImage = null;
        setFormApp(new Application());
        txt_SreachApp.setText("");
        fillListApplication();
        updateStatus();
        clearColor();
        ProcessString.showMessage(lbl_Message, "Clear form !!!");
    }

    private void clearColor() {
        Validation.clearColor(txt_Name);
        Validation.clearColor(txt_Price);
        Validation.clearColor(txt_Size);
        Validation.clearColor(txt_Sale);
        Validation.clearColor(datePicker_ReleaseDay);
        Validation.clearColor(txt_Description);
    }

    void edit() {
        isEdit = true;
        clearColor();
        updateStatus();
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
        Application entity = getForm();
        if (entity == null) {
            return;
        }
        applicationDAO.insert(entity);
        
        clearForm();
        entity = listApplications.get(listApplications.size()-1);
        new AppTypeDAO().insert(new AppType(entity.getApplicationID(), 1));
        ProcessString.showMessage(lbl_Message, "Inserted successfully !");
        
    }

    private void update() {
        Application entity = getForm();
        if (entity == null) {
            return;
        }
        applicationDAO.update(entity);
        fillListApplication();
        ProcessString.showMessage(lbl_Message, "Update successfully ID-" + entity.getApplicationID() + " !");
    }

    private void delete() {
        Integer ID = Integer.parseInt(lbl_GameID.getText().trim());
        applicationDAO.delete(ID);
        clearForm();
        ProcessString.showMessage(lbl_Message, "Deleted successfully ID-" + ID + " !");
    }
    
    void insertAppType(AppType entity) {
        if (entity == null) {
            return;
        }
        List<AppType> list = appTypeDAO.selectByApplicationId(entity.getApplicationID());
        if (list.size() > 6) {
            ProcessString.showMessage(lbl_Message, "Too much categories!");
            return;
        }
        appTypeDAO.insert(entity);
        loadAppCategories(applicationDAO.selectByID(entity.getApplicationID()));
        ProcessString.showMessage(lbl_Message, "Inserted category successfully !");
    }
    private void updateStatus() {
        btn_Add.setDisable(isEdit);
        btn_Update.setDisable(!isEdit);
        btn_Delete.setDisable(!isEdit);
        btn_AddCategory.setDisable(!isEdit);
    }

    private void setEvent() {
        txt_SreachApp.setOnKeyReleased((event) -> {
            fillListApplication();
            if (listApplications.size() > 0) {
                setFormApp(listApplications.get(0));
            }
        });
        Img_AppImage.setOnMouseClicked((event) -> {
            FileChooser fileChooser = new FileChooser();
            avatarImage = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
            if (avatarImage != null) {
                setAvatarImage();
            }
        });
        Img_AppIcon.setOnMouseClicked((event) -> {
            FileChooser fileChooser = new FileChooser();
            avatarIcon = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
            if (avatarIcon != null) {
                setAvatarIcon();
            }
        });
        btn_Add.setOnMouseClicked((event) -> {
            insert();
        });
        btn_Clear.setOnMouseClicked((event) -> {
            clearForm();
        });
        btn_Update.setOnMouseClicked((event) -> {
            update();
        });
        btn_Delete.setOnMouseClicked((event) -> {
            delete();
        });
        btn_AddCategory.setOnMouseClicked((evt) -> {
            if (cbo_Category.getSelectionModel().getSelectedIndex() == -1) {
                ProcessString.showMessage(lbl_Message,"Please choose categories");
                return;
            }
            AppType appType = new AppType();
            appType.setApplicationID(Integer.valueOf(lbl_GameID.getText()));
            int id = categoryDao.selectByName(cbo_Category.getSelectionModel().getSelectedItem()).getCategoryId();
            appType.setCategoryId(id);
            if (!appTypeDAO.isContainAppType(appType)) {
                insertAppType(appType);
            }
            setFormApp(applicationDAO.selectByID(appType.getApplicationID()));
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

}
