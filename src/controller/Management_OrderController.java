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
import com.jfoenix.controls.JFXButton;
import animatefx.animation.*;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Account;
import model.Application;
import model.Order;
import model.OrderDetail;
import until.Dialog;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
import until.ProcessDate;
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
    private Label lbl_Total;

    @FXML
    private Label lbl_OderID;

    @FXML
    private Pane pnl_OrderDetail_Info;

    @FXML
    private JFXButton btn_Order_Update;

    @FXML
    private JFXButton pnl_Tab_OrderDetails;

    @FXML
    private Pane pnl_ListApplications;

    @FXML
    private Pane pnl_Management_Order;

    @FXML
    private JFXButton btn_PDFOrder;
    @FXML
    private JFXButton btn_ExcelOrder;

    @FXML
    private JFXButton btn_TextOrder;

    List<Order> listOrders;
    List<OrderDetail> listOrderDetails;
    List<Application> listApplications;

    AccountDAO accountDAO = new AccountDAO();
    ApplicationDAO applicationDAO = new ApplicationDAO();
    OrderDAO orderDAO = new OrderDAO();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    boolean isTabs1 = true;
    boolean isOrderEdit = false;
    boolean isOrderDetailEdit = false;
    int orderIndex = -1, detailIndex = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Variable.START = Instant.now();
        displayFormAnimation();
        setGroupButton();
        setEvent();
         ExportPDFOrder();
        ExportExcelOrder();
        ExportTextOrder();
        updateStatus();
        fillChoiceBox();
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

    void fillChoiceBox(){
        List<String> list = new ArrayList<>();
        list.add("All date");
        list.add("Date ASC");
        list.add("Date DESC");
        cbx_ID.setItems(FXCollections.observableArrayList(list));
        cbx_ID.getSelectionModel().select(0);
        
        list.clear();
        list.add("All customer");       
        list.add("A-Z");
        list.add("Z-A");
        cbx_Customer.setItems(FXCollections.observableArrayList(list));
        cbx_Customer.getSelectionModel().select(0);
        
        list.clear();
        list.add("All Quantity");       
        list.add("Quantity ASC");
        list.add("Quantity DESC");
        cbx_Apps.setItems(FXCollections.observableArrayList(list));
        cbx_Apps.getSelectionModel().select(0);
        
        list.clear();
        list.add("All Price");       
        list.add("Price ASC");
        list.add("Price DESC");
        cbx_Price.setItems(FXCollections.observableArrayList(list));
        cbx_Price.getSelectionModel().select(0);
        
        list.clear();
        list.add("All status");       
        list.add("Processing");
        list.add("Accepted");
        list.add("Refunded");
        cbx_Status.setItems(FXCollections.observableArrayList(list));
        cbx_Status.getSelectionModel().select(0);
    }
    void fillListOrders() {
        try {
            List<Account> listAccount = accountDAO.selectAll();
            listOrders = orderDAO.selectByKeyWord(txt_SearchTabs.getText().trim());
            int row = listOrders.size();

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
                    controllers[h].setInfo(listOrders.get(h),listAccount);

                    nodes[h].setOnMouseClicked(evt -> {
                        for (Row_OrderController controller : controllers) {
                            controller.setSelected(false);
                        }
                        controllers[h].setSelected(true);
                        editOrder();
                        setOrderForm(listOrders.get(h));
                        fillListOrderDetails();
                        fillListApp();
                        new PulseShort(pnl_Order_Info).play();
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
        } catch (Exception e) {
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
                });
                nodes[h].setOnMouseDragged(evt -> {
                    nodes[h].setLayoutX(evt.getSceneX() - 90);
                    nodes[h].setLayoutY(evt.getSceneY() - 60);
                });
                nodes[h].setOnMouseReleased(evt -> {
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
        } catch (Exception e) {
        }
    }

    void setOrderForm(Order entity) {
        lbl_OderID.setText(isOrderEdit ? entity.getOrderID() + "" : "");
        lbl_OrderCustomerName.setText(isOrderEdit ? accountDAO.selectByID(Integer.valueOf(entity.getAccountId())).getName() : "");
        txt_OderCustomerID.setText(isOrderEdit ? entity.getAccountId() + "" : "");
        lbl_OrderCreationDate.setText(isOrderEdit ? ProcessDate.toString(entity.getCreationDate()) : ProcessDate.toString(new Date()));
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
        setOrderForm(new Order());

        updateStatus();
        clearOrderDetailForm();
        pnl_Tab_OrderDetails.setDisable(true);
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
        fillDataOnBackground();
        clearOrderForm();
        clearOrderDetailForm();
    }

    void insertOrderDetail(OrderDetail entity) {
        orderDetailDAO.insert(entity);
        fillDataOnBackground();
        fillListOrderDetails();
        clearOrderDetailForm();
    }

    void deleteOrderDetail(OrderDetail entity) {
        orderDetailDAO.deleteByPrimaryKey(entity.getOrderID(), entity.getApplicationId());
        fillDataOnBackground();
        fillListOrderDetails();
        fillListApp();
        clearOrderDetailForm();
        updateStatus();
    }

    void updateOrder() {
        Order entity = getOrderForm();
        if (entity == null) {
            return;
        }
        orderDAO.update(entity);
        clearOrderForm();
        fillDataOnBackground();
    }

    void updateOrderDetail(OrderDetail entity) {
        orderDetailDAO.update(entity);
        fillDataOnBackground();
        fillListOrderDetails();
        updateStatus();
    }

    void deleteOrder() {
        Order entity = getOrderForm();
        if (entity == null) {
            return;
        }
        orderDAO.delete(entity.getOrderID());
        clearOrderForm();;
        fillDataOnBackground();
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
    private void ExportPDFOrder() {
        btn_PDFOrder.setOnAction(evt -> {
            try {
                ExportPDF.exportPDFOrder();
                Dialog.showMessageDialog(null, "File Ordetail save successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void ExportExcelOrder() {
        btn_ExcelOrder.setOnAction(evt -> {
            String[] header = new String[]{"ID", "CreationDate", "Status", "AcountID"};
            List<Order> list = orderDAO.selectAll();
            List<Object[]> listObjs = new ArrayList<>();
            list.forEach((News) -> {
                listObjs.add(News.toObjects());
            });
            String fileName = "News";
            String title = "News List";
            try {
                ExportExcel.exportFile(null, header, listObjs, fileName, title);
            } catch (IOException ex) {
                Logger.getLogger(Management_OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void ExportTextOrder() {
        btn_TextOrder.setOnAction(evt -> {
            ExportText.ExportFileOrder();
        });
    }


    void setEvent() {
        pnl_Tab_Orders.setDisable(true);

        pnl_Tab_OrderDetails.setOnMouseClicked(evt -> {
            changeTabs();
        });
        pnl_Tab_Orders.setOnMouseClicked(evt -> {
            changeTabs();
        });
        txt_SearchTabs.setOnKeyReleased((evt) -> {
            if (isTabs1) {
                fillDataOnBackground();
            }
        });
        txt_SreachApp.setOnKeyReleased((evt) -> {
            if (!isTabs1) {
                fillListApp();
            }
        });
        
        cbx_Apps.setOnAction((evt) -> {
            
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
    }
    void filter(int x){
        cbx_Customer.getSelectionModel().select(0);
        cbx_ID.getSelectionModel().select(0);
        cbx_Price.getSelectionModel().select(0);
        cbx_Customer.getSelectionModel().select(0);
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
