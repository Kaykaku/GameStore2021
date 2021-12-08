/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.PulseShort;
import DAO.AccountDAO;
import DAO.ApplicationDAO;
import DAO.OrderDAO;
import DAO.OrderDetailDAO;
import DAO.StatisticsDAO;
import com.jfoenix.controls.JFXButton;
import animatefx.animation.*;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.Account;
import model.Application;
import model.Order;
import model.OrderDetail;
import until.Dialog;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
import until.MailSender;
import until.MailTemplate;
import until.ProcessDate;
import until.ProcessString;
import until.Validation;
import until.Value;
import until.Variable;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_OrderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ChoiceBox<String> cbx_ID;
    
    @FXML
    private ChoiceBox<String> cbx_Price;
    
    @FXML
    private ChoiceBox<String> cbx_Apps;
    
    @FXML
    private ChoiceBox<String> cbx_Customer;
    
    @FXML
    private ChoiceBox<String> cbx_Status;
    
    @FXML
    private JFXButton btn_Order_Add;
    
    @FXML
    private JFXTextField txt_AppCode;
    
    @FXML
    private Pane pnl_Apps_Non_Purchase;
    
    @FXML
    private Label lbl_AppID;
    
    @FXML
    private TilePane tile_ListOrderDetails;
    
    @FXML
    private Pane pnl_Order_Info;
    
    @FXML
    private Label lbl_OrderCreationDate;
    
    @FXML
    private Label lbl_AppName;
    
    @FXML
    private Label lbl_Message1;
    
    @FXML
    private TextField txt_SreachApp;
    
    @FXML
    private ScrollPane pnl_ScrollOrderDetails;
    
    @FXML
    private Pane pnl_Order_Total;
    
    @FXML
    private Pane pnl_Tabs;
    
    @FXML
    private JFXButton pnl_Tab_Orders;
    
    @FXML
    private TextField txt_SearchTabs;
    
    @FXML
    private VBox vbox_Orders;
    
    @FXML
    private ScrollPane pnl_ScrollListApplications;
    
    @FXML
    private JFXRadioButton rdo_Accepted;
    
    @FXML
    private VBox vbox_ListApplications;
    
    @FXML
    private Pane pnl_OrderDetails;
    
    @FXML
    private Pane pnl_List_Controller;
    
    @FXML
    private JFXRadioButton rdo_Processing;
    
    @FXML
    private Pane pnl_Title;
    
    @FXML
    private JFXRadioButton rdo_Refunded;
    
    @FXML
    private JFXButton btn_Order_Clear;
    
    @FXML
    private JFXButton btn_Back;
    
    @FXML
    private Label lbl_Message;
    
    @FXML
    private Label lbl_OrderCustomerName;
    
    @FXML
    private Label lbl_AppPrice;
    
    @FXML
    private Label lbl_AppPriceAfterSale;
    
    @FXML
    private Label lbl_AppSale;
    
    @FXML
    private ScrollPane pnl_ScrollOrders;
    
    @FXML
    private JFXTextField txt_OderCustomerID;
    
    @FXML
    private JFXButton btn_Order_Delete;
    
    @FXML
    private JFXButton btn_OrderDetail_Delete;
    
    @FXML
    private JFXButton btn_OrderDetail_Update;
    
    @FXML
    private Pane pnl_ListOrderDetails;
    
    @FXML
    private Pane pnl_ListOrders;
    
    @FXML
    private Pane pnl_Hint;
    
    @FXML
    private Label lbl_Total;
    
    @FXML
    private Label lbl_OderID;
    
    @FXML
    private Label lbl_Hint;
    
    @FXML
    private Pane pnl_OrderDetail_Info;
    
    @FXML
    private JFXButton btn_Order_Update;
    
    @FXML
    private TextField txt_SearchCustomer;
    
    @FXML
    private JFXButton btn_ShowListCustomer;
    
    @FXML
    private JFXButton pnl_Tab_OrderDetails;
    
    @FXML
    private Pane pnl_ListApplications;
    
    @FXML
    private Pane pnl_Management_Order;
    
    @FXML
    private Pane pnl_ListCustomer;
    
    @FXML
    private JFXButton btn_PDFOrder;
    @FXML
    private JFXButton btn_ExcelOrder;
    
    @FXML
    private JFXButton btn_SendOrder;
    @FXML
    private JFXButton btn_ExportOrder;
    
    @FXML
    private JFXButton btn_TextOrder;
    @FXML
    private TableView<Account> tbl_Accounts;
    @FXML
    private TableColumn<Account, String> col_Username;
    @FXML
    private TableColumn<Account, String> col_Name;
    @FXML
    private TableColumn<Account, Date> col_Birthday;
    @FXML
    private TableColumn<Account, Boolean> col_Gender;
    @FXML
    private TableColumn<Account, Integer> col_ID;
    
    List<Order> listOrders;
    List<OrderDetail> listOrderDetails;
    List<Application> listApplications;
    List<Object[]> list;
    
    AccountDAO accountDAO = new AccountDAO();
    ApplicationDAO applicationDAO = new ApplicationDAO();
    OrderDAO orderDAO = new OrderDAO();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    
    boolean isTabs1 = true;
    boolean isOrderEdit = false;
    boolean isOrderDetailEdit = false;
    int orderIndex = -1, detailIndex = -1;
    Comparator<Object[]> comparator;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectChoseBox(0);
        displayFormAnimation();
        setGroupButton();
        fillChoiceBox();
        setEvent();
        setEventExport();
        clearOrderForm();
        updateStatus();
        
        fillDataOnBackground();
        
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
                    fillListOrders();
                    fillTable();
                });
            }
        }.start();
    }
    void setGroupButton() {
        ToggleGroup grbutton = new ToggleGroup();
        rdo_Processing.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Accepted.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Refunded.setSelectedColor(Color.valueOf("#4a84f8"));
        rdo_Processing.setToggleGroup(grbutton);
        rdo_Accepted.setToggleGroup(grbutton);
        rdo_Refunded.setToggleGroup(grbutton);
    }
    
    void fillChoiceBox() {
        List<String> listChoiceBox = new ArrayList<>();
        listChoiceBox.add("All date");
        listChoiceBox.add("Date ASC");
        listChoiceBox.add("Date DESC");
        cbx_ID.setItems(FXCollections.observableArrayList(listChoiceBox));
        cbx_ID.getSelectionModel().select(0);
        
        listChoiceBox.clear();
        listChoiceBox.add("All customer");
        listChoiceBox.add("A-Z");
        listChoiceBox.add("Z-A");
        cbx_Customer.setItems(FXCollections.observableArrayList(listChoiceBox));
        cbx_Customer.getSelectionModel().select(0);
        
        listChoiceBox.clear();
        listChoiceBox.add("All Quantity");
        listChoiceBox.add("Quantity ASC");
        listChoiceBox.add("Quantity DESC");
        cbx_Apps.setItems(FXCollections.observableArrayList(listChoiceBox));
        cbx_Apps.getSelectionModel().select(0);
        
        listChoiceBox.clear();
        listChoiceBox.add("All Price");
        listChoiceBox.add("Price ASC");
        listChoiceBox.add("Price DESC");
        cbx_Price.setItems(FXCollections.observableArrayList(listChoiceBox));
        cbx_Price.getSelectionModel().select(0);
        
        listChoiceBox.clear();
        listChoiceBox.add("All status");
        listChoiceBox.add("Processing");
        listChoiceBox.add("Accepted");
        listChoiceBox.add("Refunded");
        cbx_Status.setItems(FXCollections.observableArrayList(listChoiceBox));
        cbx_Status.getSelectionModel().select(0);
    }
    
    void fillListOrders() {
        try {
            list = new StatisticsDAO().getOderAndDetails(txt_SearchTabs.getText().trim());
            filter();
            Collections.sort(list, comparator);
            int row = list.size();
            
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_ORDER));
            double height = (paneP.getPrefHeight() + 10) * row;
            vbox_Orders.setPrefSize(paneP.getPrefWidth(), height);
            pnl_ListOrders.setPrefSize(pnl_ScrollOrders.getPrefWidth() - 15, height > pnl_ScrollOrders.getPrefHeight() ? height : pnl_ScrollOrders.getPrefHeight());
            vbox_Orders.setLayoutX((pnl_ListOrders.getPrefWidth() - vbox_Orders.getPrefWidth()) / 2);
            
            vbox_Orders.getChildren().clear();
            Pane[] nodes = new Pane[row];
            Row_OrderController[] controllers = new Row_OrderController[row];
            for (int i = 0; i < row; i++) {
                final int h = i;
                
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(Value.ROW_ORDER));
                    nodes[h] = (Pane) loader.load();
                    controllers[h] = loader.getController();
                    vbox_Orders.getChildren().add(nodes[h]);
                    controllers[h].setInfo(list.get(h));
                    
                    nodes[h].setOnMouseClicked(evt -> {
                        for (Row_OrderController controller : controllers) {
                            controller.setSelected(false);
                        }
                        controllers[h].setSelected(true);
                        editOrder();
                        new PulseShort(pnl_Order_Info).play();
                        setOrderForm(list.get(h));
                        tile_ListOrderDetails.getChildren().clear();
                        vbox_ListApplications.getChildren().clear();
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                }
                                Platform.runLater(() -> {                                    
                                    fillListOrderDetails();
                                    fillListApp();
                                });
                            }
                        }.start();
                        changeTabs();
                        clearOrderDetailForm();
                    });
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
        }
        
    }
    
    void fillListOrderDetails() {
        try {
            listOrderDetails = orderDetailDAO.selectByOrderID(Integer.parseInt(lbl_OderID.getText()));
            int row = listOrderDetails.size();
            double total = 0;
            
            Pane paneK = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_ORDERDETAIL));
            double height = (paneK.getPrefHeight() + 15) * (row + 1) / 2;
            tile_ListOrderDetails.setPrefSize(paneK.getPrefWidth() * 2 + 20, height);
            pnl_ListOrderDetails.setPrefSize(pnl_ScrollOrderDetails.getPrefWidth() - 15, height > pnl_ScrollOrderDetails.getPrefHeight() ? height : pnl_ScrollOrderDetails.getPrefHeight());

            //box_ListProduct1.setLayoutX((pnl_List1.getPrefWidth()-box_ListProduct1.getPrefWidth())/2);
            tile_ListOrderDetails.getChildren().clear();
            Pane[] nodes = new Pane[row];
            Row_OrderDetailController[] controllers = new Row_OrderDetailController[row];
            for (int i = 0; i < row; i++) {
                final int h = i;
                OrderDetail orde = listOrderDetails.get(h);
                total += orde.getPrice() * (100 - orde.getSale());
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.ROW_ORDERDETAIL));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();
                tile_ListOrderDetails.getChildren().add(nodes[h]);
                controllers[h].setOrderDetailInfo(orde);
                
                nodes[h].setOnMouseClicked(evt -> {
                    for (Row_OrderDetailController controller : controllers) {
                        controller.setSelected(false);
                    }
                    detailIndex = h;
                    controllers[h].setSelected(true);
                    new PulseShort(pnl_OrderDetail_Info).play();
                    isOrderDetailEdit = true;
                    setOrderDetailForm(orde);
                    updateStatus();
                    
                });
                controllers[h].getButtonDelete().setOnMouseClicked((event) -> {
                    deleteOrderDetail(orde);
                });
            }
            total = (double) Math.round(total) / 100;
            lbl_Total.setText(total + "$");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    void fillListApp() {
        listApplications = applicationDAO.selectNonPurchaseApplications(Integer.parseInt(txt_OderCustomerID.getText()), txt_SreachApp.getText().trim());
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_PRODUCT));
            double height = (paneP.getPrefHeight() + vbox_ListApplications.getSpacing()) * listApplications.size();
            vbox_ListApplications.setPrefSize(paneP.getPrefWidth(), height);
            pnl_ListApplications.setPrefHeight(height > pnl_ScrollListApplications.getPrefHeight() ? height : pnl_ScrollListApplications.getPrefHeight());
            
            vbox_ListApplications.getChildren().clear();
            Pane[] nodes = new Pane[listApplications.size()];
            Row_ProductController[] controllers = new Row_ProductController[listApplications.size()];
            
            for (int i = 0; i < listApplications.size(); i++) {
                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.ROW_PRODUCT));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();
                
                vbox_ListApplications.getChildren().add(nodes[h]);
                controllers[h].setAppInfo(listApplications.get(h));
                
                nodes[h].setOnMousePressed(evt -> {
                    
                    pnl_Management_Order.getChildren().add(nodes[h]);
                    nodes[h].setLayoutX(evt.getSceneX() - 90);
                    nodes[h].setLayoutY(evt.getSceneY() - 60);
                    pnl_Hint.setOpacity(1);
                    pnl_Hint.setScaleX(1);
                });
                nodes[h].setOnMouseDragged(evt -> {
                    nodes[h].setLayoutX(evt.getSceneX() - 90);
                    nodes[h].setLayoutY(evt.getSceneY() - 60);
                });
                nodes[h].setOnMouseReleased(evt -> {
                    pnl_Hint.setOpacity(0);
                    pnl_Hint.setScaleX(0);
                    pnl_Management_Order.getChildren().remove(nodes[h]);
                    
                    if (evt.getSceneX() > 0 && evt.getSceneX() < 800 && evt.getSceneY() > 420 && evt.getSceneY() < 820) {
                        OrderDetail orDetail = new OrderDetail();
                        Application app = listApplications.get(h);
                        orDetail.setApplicationId(app.getApplicationID());
                        orDetail.setPrice(app.getPrice());
                        orDetail.setSale(app.getSale());
                        orDetail.setOrderID(Integer.valueOf(lbl_OderID.getText()));
                        insertOrderDetail(orDetail);
                        
                        return;
                    }
                    vbox_ListApplications.getChildren().add(h, nodes[h]);
                });
                
            }
        } catch (IOException e) {
        }
    }
    
    void fillTable() {
        List<Account> listAccounts = accountDAO.selectByKeyWord(txt_SearchCustomer.getText().trim(), 2, -1, -1);
        
        ObservableList<Account> list = FXCollections.observableArrayList(listAccounts);
        
        col_ID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        Callback<TableColumn<Account, Boolean>, TableCell<Account, Boolean>> callbackBoo = (TableColumn<Account, Boolean> param) -> new TableCell<Account, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item ? "Female" : "Male");
                }
            }
        };
        Callback<TableColumn<Account, Date>, TableCell<Account, Date>> callbackDate = (TableColumn<Account, Date> param) -> new TableCell<Account, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(ProcessDate.toString(item));
                }
            }
        };
        col_Gender.setCellFactory(callbackBoo);
        col_Birthday.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        col_Birthday.setCellFactory(callbackDate);
        col_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
        tbl_Accounts.getItems().removeAll();
        tbl_Accounts.setItems(list);
    }
    
    void setOrderForm(Object[] entity) {
        lbl_OderID.setText(isOrderEdit ? entity[0] + "" : "");
        lbl_OrderCustomerName.setText(isOrderEdit ? entity[3].toString() : "");
        txt_OderCustomerID.setText(isOrderEdit ? entity[1].toString() + "" : "");
        lbl_OrderCreationDate.setText(isOrderEdit ? ProcessDate.toString((Date) entity[2]) : ProcessDate.toString(new Date()));
        rdo_Accepted.setSelected(isOrderEdit ? (int) entity[6] == 1 : true);
        rdo_Processing.setSelected(isOrderEdit ? (int) entity[6] == 0 : false);
        rdo_Refunded.setSelected(isOrderEdit ? (int) entity[6] == 2 : false);
    }
    
    void setOrderDetailForm(OrderDetail entity) {
        boolean flag = entity != null;
        Application app = applicationDAO.selectByID(flag ? entity.getApplicationId() : -1);
        lbl_AppID.setText(flag ? entity.getApplicationId() + "" : "");
        lbl_AppName.setText(app != null ? app.getName() : "");
        double price = (double) Math.round(entity.getPrice() * 100) / 100;
        lbl_AppPrice.setText((flag ? price : 0) + "$");
        price = (double) Math.round(entity.getPrice() * (100 - entity.getSale())) / 100;
        lbl_AppPriceAfterSale.setText((flag ? price : 0) + "$");
        lbl_AppSale.setText("(-" + (flag ? entity.getSale() : 0) + "%)");
        txt_AppCode.setText(flag ? entity.getDiscountCode() : "");
    }
    
    Order getOrderForm() {
        String err = Validation.validationAccountID(txt_OderCustomerID);
        if (err.isEmpty()) {
            Order entity = new Order();
            entity.setOrderID(isOrderEdit ? Integer.valueOf(lbl_OderID.getText()) : -1);
            entity.setAccountId(Integer.valueOf(txt_OderCustomerID.getText()));
            entity.setCreationDate(ProcessDate.toDate(lbl_OrderCreationDate.getText()));
            entity.setStatus(rdo_Processing.isSelected() ? 0 : rdo_Accepted.isSelected() ? 1 : 2);
            
            return entity;
        }
        ProcessString.showMessage(lbl_Message, "An error occurred on the form!");
        Dialog.showMessageDialog("Wrong data", err);
        return null;
    }
    
    OrderDetail getOrderDetailForm() {
        String err = Validation.validationAccountID(txt_OderCustomerID);
        if (err.isEmpty()) {
            OrderDetail entity = new OrderDetail();
            entity.setApplicationId(isOrderDetailEdit ? Integer.valueOf(lbl_AppID.getText()) : -1);
            entity.setPrice(isOrderDetailEdit ? Integer.valueOf(lbl_AppPrice.getText()) : -1);
            entity.setSale(isOrderDetailEdit ? Integer.valueOf(lbl_AppSale.getText()) : -1);
            entity.setDiscountCode(txt_AppCode.getText().trim());
            entity.setDiscountCode(txt_AppCode.getText().trim());
            return entity;
        }
        Dialog.showMessageDialog("Wrong data", err);
        return null;
    }
    
    void updateStatus() {
        txt_OderCustomerID.setEditable(!isOrderEdit);
        btn_Order_Add.setDisable(isOrderEdit);
        btn_Order_Update.setDisable(!isOrderEdit);
        btn_Order_Delete.setDisable(!isOrderEdit);
        
        btn_OrderDetail_Delete.setDisable(!isOrderDetailEdit);
        btn_OrderDetail_Update.setDisable(!isOrderDetailEdit);
        
    }
    
    void clearOrderForm() {
        orderIndex = -1;
        isOrderEdit = false;
        if (!isTabs1) {
            changeTabs();
        }
        setOrderForm(new Object[7]);
        
        updateStatus();
        clearOrderDetailForm();
        pnl_Tab_OrderDetails.setDisable(true);
        ProcessString.showMessage(lbl_Message, "Clear form !");
    }
    
    void clearOrderDetailForm() {
        isOrderDetailEdit = false;
        detailIndex = -1;
        setOrderDetailForm(new OrderDetail());
        updateStatus();
    }
    
    void editOrder() {
        isOrderEdit = true;
        updateStatus();
    }
    
    void insertOrder() {
        Order entity = getOrderForm();
        if (entity == null) {
            return;
        }
        orderDAO.insert(entity);
        fillListOrders();
        clearOrderForm();
        clearOrderDetailForm();
        ProcessString.showMessage(lbl_Message, "Inserted successfully !");
    }
    
    void insertOrderDetail(OrderDetail entity) {
        if (orderDetailDAO.SelectByPrimaryKey(entity) != null) {
            ProcessString.showMessage(lbl_Message1, "Cannot insert because it already exists in this order!");
            return;
        }
        orderDetailDAO.insert(entity);
        fillListOrders();
        fillListOrderDetails();
        clearOrderDetailForm();
        ProcessString.showMessage(lbl_Message1, "Inserted successfully !");
    }
    
    void deleteOrderDetail(OrderDetail entity) {
        orderDetailDAO.deleteByPrimaryKey(entity.getOrderID(), entity.getApplicationId());
        fillListOrders();
        fillListOrderDetails();
        fillListApp();
        clearOrderDetailForm();
        updateStatus();
        ProcessString.showMessage(lbl_Message1, "Deleted successfully ID-" + entity.getOrderID() + " !");
    }
    
    void updateOrder() {
        Order entity = getOrderForm();
        if (entity == null) {
            return;
        }
        orderDAO.update(entity);
        fillListOrders();
        ProcessString.showMessage(lbl_Message, "Update successfully ID-" + entity.getOrderID() + " !");
    }
    
    void updateOrderDetail(OrderDetail entity) {
        orderDetailDAO.update(entity);
        fillListOrders();
        fillListOrderDetails();
        updateStatus();
        ProcessString.showMessage(lbl_Message1, "Update successfully ID-" + entity.getOrderID() + " !");
    }
    
    void deleteOrder() {
        Order entity = getOrderForm();
        if (entity == null) {
            return;
        }
        orderDAO.delete(entity.getOrderID());
        clearOrderForm();
        fillListOrders();
        ProcessString.showMessage(lbl_Message, "Deleted successfully ID-" + entity.getOrderID() + " !");
    }
    
    void changeTabs() {
        AnimationFX ani1;
        AnimationFX ani2;
        if (isTabs1) {
            ani1 = new FadeOutDown(pnl_ScrollOrders);
            ani1.setSpeed(2);
            ani2 = new FadeInUp(pnl_OrderDetails);
            ani2.setSpeed(2);
            ani1.playOnFinished(ani2);
            ani1.play();
            
            ani1 = new FadeOutRight(pnl_List_Controller);
            ani1.setSpeed(2);
            ani2 = new FadeInRight(pnl_Apps_Non_Purchase);
            ani2.setSpeed(2);
            ani1.playOnFinished(ani2);
            ani1.play();
            
            new FadeOut(txt_SearchTabs).play();
            pnl_Tab_Orders.setDisable(!isTabs1);
            pnl_Tab_OrderDetails.setDisable(isTabs1);
            
        } else {
            ani1 = new FadeOutDown(pnl_OrderDetails);
            ani1.setSpeed(2);
            ani2 = new FadeInUp(pnl_ScrollOrders);
            ani2.setSpeed(2);
            ani1.playOnFinished(ani2);
            ani1.play();
            
            ani1 = new FadeOutRight(pnl_Apps_Non_Purchase);
            ani1.setSpeed(2);
            ani2 = new FadeInRight(pnl_List_Controller);
            ani2.setSpeed(2);
            ani1.playOnFinished(ani2);
            ani1.play();
            
            new FadeIn(txt_SearchTabs).play();
            pnl_Tab_OrderDetails.setDisable(isTabs1);
            pnl_Tab_Orders.setDisable(!isTabs1);
        }
        isTabs1 = !isTabs1;
    }
    
    private void setEventExport() {
        String[] header = new String[]{"ID", "CreationDate", "Status", "AcountID"};
        List<Order> listOr = orderDAO.selectAll();
        List<Object[]> listObjs = new ArrayList<>();
        listOr.forEach((News) -> {
            listObjs.add(News.toObjects());
        });
        String fileName = "Orders";
        String title = "Orders List";
        btn_PDFOrder.setOnAction(evt -> {
            try {
                ExportPDF.ExportPDF(Variable.MAIN_STAGE, header, listObjs, fileName + ".pdf", title);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btn_ExcelOrder.setOnAction(evt -> {
            
            try {
                ExportExcel.exportFile(Variable.MAIN_STAGE, header, listObjs, fileName + ".xlsx", title);
            } catch (IOException ex) {
                Logger.getLogger(Management_OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_TextOrder.setOnAction(evt -> {
            ExportText.ExportFileOrder();
        });
    }
    
    void setEvent() {
        pnl_Tab_Orders.setDisable(true);
        
        btn_ShowListCustomer.setOnMouseClicked((event) -> {
            pnl_ListCustomer.setScaleX(1);
            pnl_ListCustomer.setScaleY(1);
        });
        btn_Back.setOnMouseClicked((event) -> {
            pnl_ListCustomer.setScaleX(0);
            pnl_ListCustomer.setScaleY(0);
        });
        txt_SearchCustomer.setOnKeyReleased((event) -> {
            fillTable();
        });
        txt_OderCustomerID.setOnKeyReleased((event) -> {
            try {
                int id = Integer.parseInt(txt_OderCustomerID.getText().trim());
                Account acc = accountDAO.selectByID(id);
                if(acc!=null){
                    String name = acc.getName().isEmpty() ||acc.getName()==null?acc.getUsername():acc.getName();
                    lbl_OrderCustomerName.setText(name);
                    return;
                }
            } catch (NumberFormatException e) {
            }  
            lbl_OrderCustomerName.setText("Could not be found");
        });
        tbl_Accounts.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 1) {
                int index = tbl_Accounts.getSelectionModel().getSelectedIndex();
                if (index == -1) {
                    return;
                }
                txt_OderCustomerID.setText(col_ID.getCellObservableValue(index).getValue()+"");
                lbl_OrderCustomerName.setText(col_Name.getCellObservableValue(index).getValue()+"");
                pnl_ListCustomer.setScaleX(0);
                pnl_ListCustomer.setScaleY(0);
            }
        });
        pnl_Tab_OrderDetails.setOnMouseClicked(evt -> {
            changeTabs();
        });
        pnl_Tab_Orders.setOnMouseClicked(evt -> {
            changeTabs();
        });
        txt_SearchTabs.setOnKeyReleased((evt) -> {
            if (isTabs1) {
                fillListOrders();
            }
        });
        txt_SreachApp.setOnKeyReleased((evt) -> {
            if (!isTabs1) {
                fillListApp();
            }
        });
        
        
        btn_Order_Clear.setOnMouseClicked((evt) -> {
            clearOrderForm();
        });
        btn_Order_Add.setOnMouseClicked((evt) -> {
            insertOrder();
        });
        btn_Order_Delete.setOnMouseClicked((evt) -> {
            deleteOrder();
        });
        btn_Order_Update.setOnMouseClicked((evt) -> {
            updateOrder();
        });
        
        btn_OrderDetail_Delete.setOnMouseClicked((evt) -> {
            deleteOrderDetail(listOrderDetails.get(detailIndex));
        });
        btn_OrderDetail_Update.setOnMouseClicked((evt) -> {
            updateOrderDetail(listOrderDetails.get(detailIndex));
        });
        
        cbx_ID.setOnAction((event) -> {
            int num = cbx_ID.getSelectionModel().getSelectedIndex();
            selectChoseBox(num == 0 ? 0 : num);
            fillListOrders();
        });
        cbx_Price.setOnAction((event) -> {
            int num = cbx_Price.getSelectionModel().getSelectedIndex();
            selectChoseBox(num == 0 ? 0 : num + 2);
            fillListOrders();
        });
        cbx_Apps.setOnAction((event) -> {
            int num = cbx_Apps.getSelectionModel().getSelectedIndex();
            selectChoseBox(num == 0 ? 0 : num + 4);
            fillListOrders();
        });
        cbx_Customer.setOnAction((event) -> {
            int num = cbx_Customer.getSelectionModel().getSelectedIndex();
            selectChoseBox(num == 0 ? 0 : num + 6);
            fillListOrders();
        });
        cbx_Status.setOnAction((event) -> {
            fillListOrders();
        });
        btn_SendOrder.setOnMouseClicked((event) -> {
            Account acc = accountDAO.selectByID(Integer.parseInt(txt_OderCustomerID.getText()));
            Order order = orderDAO.selectByID(Integer.parseInt(lbl_OderID.getText()));
            if(order.getStatus()==0){
                Dialog.showMessageDialog("", "This order still progressing!");
                return;
            }
            new MailSender().startProgress(acc, MailTemplate.getOrderTitleEmail(order), MailTemplate.getOrderEmail(order));
        });
        btn_ExportOrder.setOnMouseClicked((event) -> {
            Order order = orderDAO.selectByID(Integer.parseInt(lbl_OderID.getText()));
            if(order.getStatus()==0){
                Dialog.showMessageDialog("", "This order still progressing!");
                return;
            }
            String htmlText =  MailTemplate.getOrderEmail(order);
            ExportText.exportHTML(Variable.MAIN_STAGE,htmlText, "Order-"+order.getOrderID()+".html");
        });
    }
    
    void selectChoseBox(int z) {
        cbx_Apps.getSelectionModel().select(0);
        cbx_ID.getSelectionModel().select(0);
        cbx_Customer.getSelectionModel().select(0);
        cbx_Price.getSelectionModel().select(0);
        switch (z) {
            case 0:
                comparator = new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] t, Object[] t1) {
                        Integer i = (int) t[0];
                        Integer i1 = (int) t1[0];
                        return i1.compareTo(i);
                    }
                    
                };
                break;
            case 1:
            case 2:
                cbx_ID.getSelectionModel().select(z == 1 ? 1 : 2);
                comparator = new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] t, Object[] t1) {
                        Integer i = (int) t[0];
                        Integer i1 = (int) t1[0];
                        
                        if (z == 1) {
                            return i.compareTo(i1);
                        } else {
                            return i1.compareTo(i);
                        }
                    }
                    
                };
                break;
            case 3:
            case 4:
                cbx_Price.getSelectionModel().select(z == 3 ? 1 : 2);
                comparator = new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] t, Object[] t1) {
                        Double d = (double) t[5];
                        Double d1 = (double) t1[5];
                        if (z == 3) {
                            return d.compareTo(d1);
                        } else {
                            return d1.compareTo(d);
                        }
                    }
                    
                };
                break;
            case 5:
            case 6:
                cbx_Apps.getSelectionModel().select(z == 5 ? 1 : 2);
                comparator = new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] t, Object[] t1) {
                        Integer i = (int) t[4];
                        Integer i1 = (int) t1[4];
                        
                        if (z == 5) {
                            return i.compareTo(i1);
                        } else {
                            return i1.compareTo(i);
                        }
                    }
                    
                };
                break;
            case 7:
            case 8:
                cbx_Customer.getSelectionModel().select(z == 7 ? 1 : 2);
                comparator = new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] t, Object[] t1) {
                        if (z == 7) {
                            return t[3].toString().compareTo(t1[3].toString());
                        } else {
                            return t1[3].toString().compareTo(t[3].toString());
                        }
                    }
                    
                };
                break;
        }
    }
    
    void filter() {
        int status = cbx_Status.getSelectionModel().getSelectedIndex() - 1;
        if (status == -1) {
            return;
        }
        
        for (int i = 0; i < list.size();) {
            if ((int) list.get(i)[6] != status) {
                list.remove(i);
                continue;
            }
            i++;
        }
    }
    
    void displayFormAnimation() {
        new FadeInDown(pnl_Order_Info).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Tabs).play();
        new FadeInRightBig(pnl_OrderDetail_Info).play();
        new FadeInUpBig(pnl_ScrollOrders).play();
        new ZoomIn(pnl_List_Controller).play();
    }
    
}
