/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import model.AppType;
import model.Application;
import model.Category;
import until.Dialog;
import until.ProcessImage;
import until.Validation;

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

    CategoryDAO categoryDAO = new CategoryDAO();
    ApplicationDAO applicationDAO = new ApplicationDAO();
    AppTypeDAO appTypeDAO = new AppTypeDAO();
    List<Category> listCategories = new ArrayList<>();
    List<Application> listApplications = new ArrayList<>();
    boolean isEdit = false;
    int index = -1;
    File avatarFile = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCboCategory();
        fillTableCategories();
        fillListApp();
        setEvent();
        updateStatus();
        displayFormAnimation();
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
        
        Callback<TableColumn<Category, String>, TableCell<Category, String>> callbackBoo = new Callback<TableColumn<Category, String>, TableCell<Category, String>>() {
            @Override
            public TableCell<Category, String> call(TableColumn<Category, String> param) {
                return new TableCell<Category, String>() {
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
            }
        };
        col_Color.setCellFactory(callbackBoo);

        tbl_Categories.getItems().clear();
        tbl_Categories.getItems().addAll(list);
    }

    void fillListApp() {
        listApplications = applicationDAO.selectByKeyWord(txt_SreachApp.getText().trim());
        btn_AddCategory.setDisable(true);
        
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
            double height = (paneP.getPrefHeight() + vbox_ListProduct.getSpacing()) * listApplications.size();
            vbox_ListProduct.setPrefSize(paneP.getPrefWidth(), height);
            pnl_List.setPrefHeight(height > pnl_ScrollList.getPrefHeight() ? height : pnl_ScrollList.getPrefHeight());
            
            vbox_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[listApplications.size()];
            Row_ProductController[] controllers = new Row_ProductController[listApplications.size()];
            
            for (int i = 0; i < listApplications.size(); i++) {
                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/gui/Item/Row_Product.fxml"));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();
                
                vbox_ListProduct.getChildren().add(nodes[h]);
                controllers[h].setAppInfo(listApplications.get(h));
                
                nodes[h].setOnMouseClicked(evt -> {
                    setFormApp(listApplications.get(h));
                    btn_AddCategory.setDisable(false);
                });

                nodes[h].setOnMouseClicked(evt -> {
                    setFormApp(listApplications.get(h));
                    btn_AddCategory.setDisable(false);
                });
                
                nodes[h].setOnMouseClicked(evt -> {
                    setFormApp(listApplications.get(h));
                    btn_AddCategory.setDisable(false);
                });
            }
        } catch (Exception e) {
        }
    }

    void setFormApp(Application entity) {
        lbl_AppID.setText(entity.getApplicationID() + "");
        lbl_AppName.setText(entity.getName());
        if (entity.getAppIcon() != null) {
            img_AppIcon.setImage(new Image(ProcessImage.toFile(entity.getAppIcon(), "appIcon.png").toURI().toString()));
            RoundedImageView.RoundedImage(img_AppIcon, 32);
        }
        
        
        List<AppType> list = appTypeDAO.selectByApplicationId(entity.getApplicationID());
        lbl_CategoryCount.setText(list.size()+"");
        
        pnl_Container.getChildren().clear();
        double width = 0, x = 10;
        for (AppType appType : list) {
            Category ca = categoryDAO.selectByID(appType.getCategoryId());
            Label label = new Label(ca.getName());
            label.setStyle("-fx-background-radius : 10px; -fx-text-fill : white; -fx-background-color : " + ca.getColor());
            pnl_Container.getChildren().add(label);
            
            label.setLayoutX(x + width);
            label.setLayoutY(10);
            label.setPadding(new Insets(2, 10, 2, 10));
            FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
            label.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.REGULAR, 16));
            width = fontLoader.computeStringWidth(label.getText(), label.getFont()) + 30;
            x = label.getLayoutX();

            label.setOnMouseClicked((event) -> {
                if (list.size() <= 1) {
                    Dialog.showMessageDialog("Error", "At least one category");
                    return;
                }
                appTypeDAO.delete(appType);
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
        avatarFile = null;
        setFormCate(new Category());
        updateStatus();
    }

    void edit() {
        isEdit = true;
        avatarFile = null;
        if (index == -1) {
            return;
        }
        int id = (int) col_ID.getCellObservableValue(index).getValue();
        Category entity = categoryDAO.selectByID(id);
        setFormCate(entity);
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
    }

    void insertAppType(AppType entity) {
        if (entity == null) {
            return;
        }
        appTypeDAO.insert(entity);
        fillTableCategories();
        clearForm();
        
    }

    void update() {
        Category entity = getFormCate();
        if (entity == null) {
            return;
        }
        categoryDAO.update(entity);
        fillTableCategories();
        clearForm();
        setFormApp(applicationDAO.selectByID(Integer.valueOf(lbl_AppID.getText())));
        fillCboCategory();
    }

    void delete() {
        Category entity = getFormCate();
        if (entity == null) {
            return;
        }
        categoryDAO.delete(entity.getCategoryId());
        fillTableCategories();
        clearForm();
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

    void setEvent() {
        tbl_Categories.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                index = tbl_Categories.getSelectionModel().getSelectedIndex();
                edit();
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

        img_AppIcon.setOnMouseClicked((evt) -> {
            File f = new FileChooser().showOpenDialog(((Node) (evt.getSource())).getScene().getWindow());
            if (f != null) {
                model.Application a = new model.Application();
                a.setAppIcon(ProcessImage.toBytes(f));
                a.setApplicationID(Integer.valueOf(lbl_AppID.getText()));
                new ApplicationDAO().setImage(a);
            }
        });

        btn_AddCategory.setOnMouseClicked((evt) -> {
            if (cbo_Category.getSelectionModel().getSelectedIndex() == -1) {
                Dialog.showMessageDialog("Error", "Please choose categories");
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
