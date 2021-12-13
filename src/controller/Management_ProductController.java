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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Account;
import model.AppType;
import model.Application;
import model.Category;
import until.Dialog;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
import until.MailSender;
import until.MailTemplate;
import until.ProcessDate;
import until.ProcessImage;
import until.ProcessString;
import until.Validation;
import until.Value;
import until.Variable;

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
    private HBox hbox_Controller1;

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

    @FXML
    private JFXButton btn_sendSale;

    @FXML
    private JFXButton btn_AllCustomer;

    @FXML
    private JFXButton btn_YourCustomize;

    @FXML
    private JFXButton btn_sendGames;

    @FXML
    private Pane pnl_FillBg;

    @FXML
    private JFXButton btn_AllAccount;

    @FXML
    private Text lbl_HideMenu1;
    @FXML
    private Label lbl_HideMenu;

    @FXML
    private JFXButton btn_AllManager;

    @FXML
    private JFXButton btn_AllActiveCustomer;

    @FXML
    private JFXButton btn_AllActiveAccount;

    @FXML
    private JFXButton btn_Back;

    Application app = null;
    ApplicationDAO applicationDAO = new ApplicationDAO();
    List<Application> listApplications = new ArrayList<>();
    List<Account> emails = new ArrayList<>();
    AccountDAO accDAO = new AccountDAO();
    CategoryDAO categoryDao = new CategoryDAO();
    AppTypeDAO appTypeDAO = new AppTypeDAO();
    JFXDatePicker datePicker_CreationDate = new JFXDatePicker();
    JFXDatePicker datePicker_ReleaseDay = new JFXDatePicker();
    boolean isEdit = false, isSendRelease = true;
    Image avatarIcon;
    Image avatarImage;
    Image avtImg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.showDatePicker();
        this.displayFormAnimation();
        loadAppCategories(new Application());
        setEvent();
        setEventExport();
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
                    edit(listApplications.get(h));
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
        if (list.isEmpty()) {
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
            Img_AppIcon.setImage(avatarIcon);
        } else {
            Img_AppIcon.setImage(new ProcessImage().toImageFX("/icons/refresh120.png"));
        }
        RoundedImageView.RoundedImage(Img_AppIcon, 200);
    }

    private void setAvatarImage() {
        if (avatarImage != null) {
            Img_AppImage.setImage(avatarImage);
        } else {
            Img_AppImage.setImage(new ProcessImage().toImageFX("/icons/icons8_picture_200px_1.png"));
        }
        RoundedImageView.RoundedImage(Img_AppImage, 32);
    }

    public void setFormApp(Application entity) {
        loadAppCategories(entity);
        lbl_GameID.setText(isEdit ? entity.getApplicationID() + "" : "");
        txt_Name.setText(isEdit ? entity.getName() + "" : "");
        double number = (double) Math.round(entity.getPrice() * 100) / 100;
        txt_Price.setText(isEdit ? number + "" : "");
        number = (double) Math.round(entity.getSize() * 100) / 100;
        txt_Size.setText(isEdit ? number + "" : "");
        if (entity.getAppImage() != null) {
            avatarImage = ProcessImage.toImageFX(entity.getAppImage());
        }
        setAvatarImage();
        if (entity.getAppIcon() != null) {
            avatarIcon = ProcessImage.toImageFX(entity.getAppIcon());
        }
        setAvatarIcon();
        txt_Developed.setText(entity.getDeveloper());
        txt_Published.setText(entity.getPublisher());
        datePicker_CreationDate.setValue(isEdit ? ProcessDate.toLocalDate(entity.getCreationDate()) : LocalDate.now());
        datePicker_ReleaseDay.setValue(isEdit ? ProcessDate.toLocalDate(entity.getReleaseDay()) : null);
        txt_Languages.setText(entity.getLanguages());
        number = (double) Math.round(entity.getSale() * 100) / 100;
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
        err += Validation.validationImage(Img_AppIcon, avatarIcon, circle, "APPICON");
        err += Validation.validationImage(Img_AppImage, avatarImage, "APP IMAGE");
        err += Validation.validationSale(txt_Sale);
        err += Validation.validationDate(datePicker_ReleaseDay, "RELEASE DATE");
        err += Validation.validationJFXTextAreaLength(txt_Description, "DESCRIPTION", 10, 4000);
        if (err.isEmpty()) {
            Application app = new Application();
            app.setApplicationID(isEdit ? Integer.parseInt(lbl_GameID.getText().trim()) : -1);
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
            app.setSale(Double.parseDouble(txt_Sale.getText().trim().isEmpty() ? 0 + "" : txt_Sale.getText().trim()));
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
        app = null;
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

    void edit(Application entity) {
        app = entity;
        isEdit = true;
        clearColor();
        updateStatus();
    }

    private void insert() {
        Application entity = getForm();
        if (entity == null) {
            return;
        }
        applicationDAO.insert(entity);

        clearForm();
        entity = listApplications.get(listApplications.size() - 1);
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
                isEdit = true;
                edit(listApplications.get(0));
                setFormApp(listApplications.get(0));
            }
        });
        Img_AppImage.setOnMouseClicked((event) -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG JPG", "*.png","*.jpg"));
            fc.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
            fc.setTitle("Select folder");
            File f = fc.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
            if (f != null) {
                avatarImage = new Image(f.toURI().toString());
                setAvatarImage();
            }
        });
        Img_AppIcon.setOnMouseClicked((event) -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG JPG", "*.png","*.jpg"));
            fc.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
            fc.setTitle("Select folder");
            File f = fc.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
            if (f != null) {
                avatarIcon = new Image(f.toURI().toString());
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
                ProcessString.showMessage(lbl_Message, "Please choose categories");
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
        btn_sendGames.setOnMouseClicked((event) -> {
            if (app == null) {
                Dialog.showMessageDialog("", "You must select an app to perform this action");
                return;
            }
            isSendRelease = true;
            pnl_FillBg.setScaleX(1);
            pnl_FillBg.setScaleY(1);
            new ZoomIn(pnl_FillBg).play();
            lbl_HideMenu.setText("Notice of release");
            lbl_HideMenu1.setText(app.getName().toUpperCase());
        });
        btn_sendSale.setOnMouseClicked((event) -> {
            if (app == null) {
                Dialog.showMessageDialog("", "You must select an app to perform this action");
                return;
            }
            isSendRelease = false;
            pnl_FillBg.setScaleX(1);
            pnl_FillBg.setScaleY(1);
            new ZoomIn(pnl_FillBg).play();
            lbl_HideMenu.setText("Notice of sale off");
            lbl_HideMenu1.setText(app.getName().toUpperCase());
        });
        btn_AllAccount.setOnMouseClicked((event) -> {
            emails = accDAO.selectByKeyWord("", -1, -1, -1);
            startSendMailProgress();
        });
        btn_AllActiveAccount.setOnMouseClicked((event) -> {
            emails = accDAO.selectByKeyWord("", -1, 1, -1);
            startSendMailProgress();
        });
        btn_AllActiveCustomer.setOnMouseClicked((event) -> {
            emails = accDAO.selectByKeyWord("", 2, 1, -1);
            startSendMailProgress();
        });
        btn_AllCustomer.setOnMouseClicked((event) -> {
            emails = accDAO.selectByKeyWord("", 2, -1, -1);
            startSendMailProgress();
        });
        btn_AllManager.setOnMouseClicked((event) -> {
            emails = accDAO.selectByKeyWord("", 1, -1, -1);
            List<Account> listad = accDAO.selectByKeyWord("", 0, -1, -1);
            emails.addAll(listad);
            startSendMailProgress();
        });
        btn_YourCustomize.setOnMouseClicked((event) -> {
            try {
                if (isSendRelease) {
                    Variable.DEFAULT_MAILTEMPLATE = MailTemplate.getReleaseApplicationEmail(app);
                    Variable.DEFAULT_MAILTITLETEMPLATE = MailTemplate.getReleaseApplicationTitleEmail(app);
                } else {
                    Variable.DEFAULT_MAILTEMPLATE = MailTemplate.getDiscountApplicationEmail(app);
                    Variable.DEFAULT_MAILTITLETEMPLATE = MailTemplate.getDiscountApplicationTitleEmail(app);
                }
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Value.FORM_MAIL));
                Parent root1 = (Parent) fxmlLoader.load();

                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                Scene scene = new Scene(root1);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                stage.close();
                pnl_FillBg.setScaleX(0);
                pnl_FillBg.setScaleY(0);
                new ZoomOut(pnl_FillBg).play();
            } catch (IOException ex) {
                Logger.getLogger(Management_AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_Back.setOnMouseClicked((event) -> {
            pnl_FillBg.setScaleX(0);
            pnl_FillBg.setScaleY(0);
            new ZoomOut(pnl_FillBg).play();
        });
    }

    private void setEventExport() {
        String[] header = new String[]{"ID", "Name", "Price($)", "Size(MB)", "Developer", "Publisher", "ReleaseDay",
            "CreationDate", "Languages", "Sale", "Active", "EnableBuy"};
        List<Application> list = applicationDAO.selectAll();
        List<Object[]> listObjs = new ArrayList<>();
        list.forEach((Application) -> {
            listObjs.add(Application.toObjects());
        });
        String fileName = "Applications";
        String title = "Application List";
        btn_ExcelProduct.setOnAction(evt -> {

            try {
                ExportExcel.exportFile(Variable.MAIN_STAGE, header, listObjs, fileName + ".xlsx", title);
            } catch (IOException ex) {

            }
        });

        btn_TextProduct.setOnAction(evt -> {
            ExportText.ExportFileProduct();
        });

        btn_DPFProduct.setOnAction(evt -> {
            try {
                ExportPDF.ExportPDF(Variable.MAIN_STAGE, header, listObjs, fileName + ".pdf", title);
            } catch (Exception ex) {

            }

        });
    }

    void startSendMailProgress() {
        if (!Variable.IS_SENDING) {
            Dialog.showMessageDialog("", "Your sending progress is running!");
        }
        if (isSendRelease) {
            new MailSender().startProgress(emails, MailTemplate.getReleaseApplicationTitleEmail(app), MailTemplate.getReleaseApplicationEmail(app));
        } else {
            new MailSender().startProgress(emails, MailTemplate.getDiscountApplicationTitleEmail(app), MailTemplate.getDiscountApplicationEmail(app));
        }

        pnl_FillBg.setScaleX(0);
        pnl_FillBg.setScaleY(0);
        new ZoomOut(pnl_FillBg).play();
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
        new ZoomInUp(hbox_Controller1).play();
        GlowText ani = new GlowText(lbl_OnSale, Color.WHITE, Color.RED);
        ani.setCycleCount(Timeline.INDEFINITE);
        ani.play();
    }

}
