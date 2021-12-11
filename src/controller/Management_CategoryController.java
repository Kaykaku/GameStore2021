/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.PulseShort;
import Animation.RoundedImageView;
import DAO.AppTypeDAO;
import DAO.ApplicationDAO;
import DAO.CategoryDAO;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import model.AppType;
import model.Application;
import model.Category;
import until.Dialog;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
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
public class Management_CategoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_ExportTextApp;

    @FXML
    private JFXButton btn_ExportPDFApp;

    @FXML
    private JFXButton btn_ExportExcelApp;

    @FXML
    private Label lbl_CategoryID;

    @FXML
    private TextField txt_SearchCategory;

    @FXML
    private TableColumn<Category, Integer> col_ID;

    @FXML
    private TextField txt_SreachApp;

    @FXML
    private TableColumn<Category, Integer> col_AppCount;

    @FXML
    private JFXButton btn_Add;

    @FXML
    private JFXButton btn_Delete;

    @FXML
    private JFXComboBox<String> cbo_Category;

    @FXML
    private Pane pnl_List_Product;

    @FXML
    private TableColumn<Category, String> col_Name;

    @FXML
    private Label lbl_AppID;

    @FXML
    private Label lbl_Message;

    @FXML
    private Label lbl_Message1;

    @FXML
    private JFXButton btn_Update;

    @FXML
    private JFXColorPicker colorPicker;

    @FXML
    private Pane pnl_Image_Product;

    @FXML
    private Pane pnl_App_Info;

    @FXML
    private Label lbl_AppName;

    @FXML
    private Pane pnl_Title;

    @FXML
    private VBox vbox_ListProduct;

    @FXML
    private Pane pnl_Table_Category;

    @FXML
    private Label lbl_CategoryCount;

    @FXML
    private JFXButton btn_Clear;

    @FXML
    private JFXButton btn_AddCategory;

    @FXML
    private Pane pnl_List;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private Pane pnl_Container;

    @FXML
    private Pane pnl_Login;

    @FXML
    private JFXTextField txt_CategoryName;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Label lbl_AppCount;

    @FXML
    private ImageView img_AppIcon;

    @FXML
    private TableColumn<Category, String> col_Color;

    @FXML
    private TableView<Category> tbl_Categories;
    @FXML
    private JFXButton btn_First;
    @FXML
    private JFXButton btn_Preview;
    @FXML
    private JFXButton btn_Next;
    @FXML
    private JFXButton btn_Last;
    @FXML
    private JFXButton btn_PDFCategory;
    @FXML
    private JFXButton btn_TextCategory;
    @FXML
    private JFXButton btn_ExcelCategory;

    CategoryDAO categoryDAO = new CategoryDAO();
    ApplicationDAO applicationDAO = new ApplicationDAO();
    AppTypeDAO appTypeDAO = new AppTypeDAO();
    List<Category> listCategories = new ArrayList<>();
    List<Application> listApplications = new ArrayList<>();
    boolean isEdit = false;
    int index = -1, indexApp = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillDataOnBackground(1000);
        displayFormAnimation();
        setEvent();
        setEventExport();
        setEventAppExport();
        updateStatus();

    }

    void fillDataOnBackground(int delay) {
        fillTableCategories();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    fillCboCategory();

                    fillListApp();
                });
            }
        }.start();
    }

    void fillCboCategory() {
        List<Category> list = categoryDAO.selectAll();
        List<String> categories = new ArrayList<>();
        for (Category category : list) {
            categories.add(category.getName());
        }

        cbo_Category.getItems().clear();
        cbo_Category.getItems().addAll(categories);
    }

    void fillTableCategories() {
        listCategories = categoryDAO.selectByKeyWord(txt_SearchCategory.getText().trim());
        ObservableList<Category> list = FXCollections.observableArrayList(listCategories);

        col_ID.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Color.setCellValueFactory(new PropertyValueFactory<>("color"));
        col_AppCount.setCellValueFactory(new PropertyValueFactory<>("appCount"));

        Callback<TableColumn<Category, String>, TableCell<Category, String>> callbackBoo = (TableColumn<Category, String> param) -> new TableCell<Category, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-background-color : " + item);
                }
            }
        };
        col_Color.setCellFactory(callbackBoo);

        tbl_Categories.getItems().clear();
        tbl_Categories.getItems().addAll(list);
    }

    void fillListApp() {
        listApplications = applicationDAO.selectByKeyWord(txt_SreachApp.getText().trim(), index > 0 ? Integer.parseInt(lbl_CategoryID.getText()) : 1);
        btn_AddCategory.setDisable(true);

        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_PRODUCT));
            double height = (paneP.getPrefHeight() + vbox_ListProduct.getSpacing()) * listApplications.size();
            vbox_ListProduct.setPrefSize(paneP.getPrefWidth(), height);
            pnl_List.setPrefHeight(height > pnl_ScrollList.getPrefHeight() ? height : pnl_ScrollList.getPrefHeight());

            vbox_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[listApplications.size()];
            Row_ProductController[] controllers = new Row_ProductController[listApplications.size()];

            for (int i = 0; i < listApplications.size(); i++) {
                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.ROW_PRODUCT));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                vbox_ListProduct.getChildren().add(nodes[h]);
                controllers[h].setAppInfo(listApplications.get(h));

                nodes[h].setOnMouseClicked(evt -> {
                    indexApp = h;
                    setFormApp(listApplications.get(h));
                    btn_AddCategory.setDisable(false);
                    for (Row_ProductController controller : controllers) {
                        controller.setSelected(false);
                    }
                    controllers[h].setSelected(true);
                    new PulseShort(pnl_Image_Product).play();
                    new PulseShort(pnl_App_Info).play();
                });
            }
        } catch (Exception e) {
        }
    }

    void setFormApp(Application entity) {
        lbl_AppID.setText(entity.getApplicationID() + "");
        lbl_AppName.setText(entity.getName());
        if (entity.getAppIcon() != null) {
            img_AppIcon.setImage(ProcessImage.toImageFX(entity.getAppIcon()));
            RoundedImageView.RoundedImage(img_AppIcon, 32);
        } else {
            try {
                img_AppIcon.setImage(new Image(getClass().getResource("/icons/icons8_picture_200px_1.png").toURI().toString()));
                RoundedImageView.RoundedImage(img_AppIcon, 32);
            } catch (URISyntaxException ex) {
                //Logger.getLogger(Management_CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<AppType> list = appTypeDAO.selectByApplicationId(entity.getApplicationID());
        lbl_CategoryCount.setText(list.size() + "");

        pnl_Container.getChildren().clear();
        double width = 0, x = 10, y = 10;
        for (AppType appType : list) {
            Category ca = categoryDAO.selectByID(appType.getCategoryId());
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
                    ProcessString.showMessage(lbl_Message1, "Category ALL is default!");
                    return;
                }
                appTypeDAO.delete(appType);
                ProcessString.showMessage(lbl_Message1, "Successfully deleted!");
                setFormApp(applicationDAO.selectByID(appType.getApplicationID()));
                fillTableCategories();
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

    void setFormCate(Category entity) {
        lbl_CategoryID.setText(isEdit ? entity.getCategoryId() + "" : "Editing");
        txt_CategoryName.setText(isEdit ? entity.getName() : "");
        colorPicker.setValue(isEdit ? Color.valueOf(entity.getColor()) : Color.WHITE);
        lbl_AppCount.setText(entity.getAppCount() + "");
    }

    Category getFormCate() {
        String err = Validation.validationCategoryName(txt_CategoryName);
        if (err.isEmpty()) {
            Category entity = new Category();
            entity.setCategoryId(isEdit ? Integer.valueOf(lbl_CategoryID.getText()) : -1);
            entity.setName(txt_CategoryName.getText().trim());
            String hex = "#" + colorPicker.getValue().toString().substring(2, 8).toUpperCase();
            entity.setColor(hex);

            return entity;
        }
        ProcessString.showMessage(lbl_Message, "An error occurred on the form!");
        Dialog.showMessageDialog("Wrong data", err);
        return null;
    }

    void updateStatus() {
        boolean first = (index == 0);
        boolean last = (index == listCategories.size() - 1);

        btn_Add.setDisable(isEdit);
        btn_Delete.setDisable(!isEdit);
        btn_Update.setDisable(!isEdit);

        btn_First.setDisable(isEdit && first);
        btn_Preview.setDisable(isEdit && first);
        btn_Next.setDisable(isEdit && last);
        btn_Last.setDisable(isEdit && last);
    }

    void clearForm() {
        index = -1;
        isEdit = false;
        setFormCate(new Category());
        txt_SearchCategory.setText("");
        txt_SreachApp.setText("");
        fillTableCategories();
        fillListApp();
        updateStatus();
        Validation.clearColor(txt_CategoryName);
        ProcessString.showMessage(lbl_Message, "Clear form !!!");
    }

    void edit() {
        if (index == -1) {
            return;
        }
        isEdit = true;
        Validation.clearColor(txt_CategoryName);
        int id = (int) col_ID.getCellObservableValue(index).getValue();
        Category entity = categoryDAO.selectByID(id);
        setFormCate(entity);
        fillDataOnBackground(500);
        updateStatus();
    }

    void insert() {
        Category entity = getFormCate();
        if (entity == null) {
            return;
        }
        categoryDAO.insert(entity);
        fillTableCategories();
        clearForm();
        fillCboCategory();
        ProcessString.showMessage(lbl_Message, "Inserted successfully !");
    }

    void insertAppType(AppType entity) {
        if (entity == null) {
            return;
        }
        List<AppType> list = appTypeDAO.selectByApplicationId(entity.getApplicationID());
        if (list.size() > 6) {
            ProcessString.showMessage(lbl_Message1, "Too much categories!");
            return;
        }
        appTypeDAO.insert(entity);
        fillTableCategories();
        ProcessString.showMessage(lbl_Message1, "Inserted successfully !");
    }

    void update() {
        Category entity = getFormCate();
        if (entity == null) {
            return;
        }
        if (entity.getCategoryId() == 1) {
            ProcessString.showMessage(lbl_Message, "Cannot update default ID-1 ALL !");
            return;
        }
        categoryDAO.update(entity);
        fillTableCategories();
        clearForm();
        if (indexApp != -1) {
            setFormApp(applicationDAO.selectByID(Integer.valueOf(lbl_AppID.getText())));
        }
        fillCboCategory();
        ProcessString.showMessage(lbl_Message, "Update successfully + ID-" + entity.getCategoryId() + " !");
    }

    void delete() {
        int id = Integer.parseInt(lbl_CategoryID.getText());
        if (id == 1) {
            ProcessString.showMessage(lbl_Message, "Cannot delete default ID-1 ALL !");
            return;
        }
        categoryDAO.delete(id);
        fillTableCategories();
        clearForm();
        if (indexApp != -1) {
            setFormApp(applicationDAO.selectByID(Integer.valueOf(lbl_AppID.getText())));
        }
        ProcessString.showMessage(lbl_Message, "Deleted successfully ID-" + id + " !");
    }

    void first() {
        index = 0;
        edit();
    }

    void prev() {
        if (index > 0) {
            index--;
            edit();
        }
    }

    void next() {
        if (index < listCategories.size() - 1) {
            index++;
            edit();
        }
    }

    void last() {
        index = listCategories.size() - 1;
        edit();
    }

    private void setEventExport() {
        String[] header = new String[]{"ID", "Name", "Color"};
        List<Category> list = categoryDAO.selectAll();
        List<Object[]> listObjs = new ArrayList<>();
        list.forEach((News) -> {
            listObjs.add(News.toObjects());
        });
        String fileName = "Categories";
        String title = "Category List";
        btn_PDFCategory.setOnAction(evt -> {
            try {
                ExportPDF.ExportPDF(Variable.MAIN_STAGE, header, listObjs, fileName + ".pdf", title);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btn_ExcelCategory.setOnAction(evt -> {

            try {
                ExportExcel.exportFile(Variable.MAIN_STAGE, header, listObjs, fileName + ".xlsx", title);
            } catch (IOException ex) {
                Logger.getLogger(Management_CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_TextCategory.setOnAction(evt -> {
            ExportText.ExportFileCategory();
        });
    }

    private void setEventAppExport() {
        String[] header = new String[]{"ID", "Name", "Price($)", "Size(MB)", "Developer", "Publisher", "ReleaseDay",
            "CreationDate", "Languages", "Sale", "Active", "EnableBuy"};
        List<Application> list = applicationDAO.selectAll();
        List<Object[]> listObjs = new ArrayList<>();
        list.forEach((Application) -> {
            listObjs.add(Application.toObjects());
        });
        String fileName = "Applications";
        String title = "Application List";
        btn_ExportExcelApp.setOnAction(evt -> {

            try {
                ExportExcel.exportFile(Variable.MAIN_STAGE, header, listObjs, fileName+".xlsx" + ".xlsx", title);
            } catch (IOException ex) {

            }
        });

        btn_ExportTextApp.setOnAction(evt -> {
            ExportText.ExportFileProduct();
        });

        btn_ExportPDFApp.setOnAction(evt -> {
            try {
                ExportPDF.ExportPDF(Variable.MAIN_STAGE, header, listObjs, fileName + ".pdf", title);
            } catch (Exception ex) {

            }

        });
    }

    void setEvent() {
        tbl_Categories.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                index = tbl_Categories.getSelectionModel().getSelectedIndex();
                edit();
                new PulseShort(pnl_Basic_Info).play();
            }
        });
        txt_SearchCategory.setOnKeyReleased(evt -> {
            fillTableCategories();
            if (listCategories.size() > 0) {
                setFormCate(listCategories.get(0));
            }
        });
        txt_SreachApp.setOnKeyReleased(evt -> {
            fillListApp();
            if (listApplications.size() > 0) {
                setFormApp(listApplications.get(0));
            }
        });

        btn_Clear.setOnMouseClicked((event) -> {
            clearForm();
        });
        btn_Add.setOnMouseClicked((event) -> {
            insert();
        });
        btn_Update.setOnMouseClicked((event) -> {
            update();
        });
        btn_Delete.setOnMouseClicked((event) -> {
            delete();
        });
        btn_First.setOnMouseClicked((event) -> {
            first();
        });
        btn_Preview.setOnMouseClicked((event) -> {
            prev();
        });
        btn_Next.setOnMouseClicked((event) -> {
            next();
        });
        btn_Last.setOnMouseClicked((event) -> {
            last();
        });

        btn_AddCategory.setOnMouseClicked((evt) -> {
            if (cbo_Category.getSelectionModel().getSelectedIndex() == -1) {
                ProcessString.showMessage(lbl_Message1, "Please choose categories");
                return;
            }
            AppType appType = new AppType();
            appType.setApplicationID(Integer.valueOf(lbl_AppID.getText()));
            int id = categoryDAO.selectByName(cbo_Category.getSelectionModel().getSelectedItem()).getCategoryId();
            appType.setCategoryId(id);
            if (!appTypeDAO.isContainAppType(appType)) {
                insertAppType(appType);
            }
            setFormApp(applicationDAO.selectByID(appType.getApplicationID()));
            fillTableCategories();
        });
    }

    void displayFormAnimation() {
        new FadeInDown(pnl_Basic_Info).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Table_Category).play();
        new FadeInRightBig(pnl_List_Product).play();
        new FadeInUp(pnl_Image_Product).play();
        new FadeInUpBig(pnl_App_Info).play();
    }
}
