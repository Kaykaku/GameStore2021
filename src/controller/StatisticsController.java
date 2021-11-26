/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.StatisticsDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.OAB;
import model.RBY;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class StatisticsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_TopRefund;

    @FXML
    private Pane pnl_Table;

    @FXML
    private JFXButton btn_NewAccounts;

    @FXML
    private JFXButton btn_TopView;

    @FXML
    private JFXButton btn_RevenueByMonth;

    @FXML
    private PieChart pnl_PieChart;

    @FXML
    private Pane pnl_Title;

    @FXML
    private Pane pnl_Controller;

    @FXML
    private BarChart<String, Number> pnl_Barchart;

    @FXML
    private TableView<RBY> TableView_RBY;

    @FXML
    private TableColumn<RBY, Double> col_RBY_Total2;

    @FXML
    private TableColumn<RBY, Double> col_RBY_Total1;

    @FXML
    private TableColumn<RBY, Integer> col_RBY_Year;

    @FXML
    private TableColumn<RBY, Integer> col_RBY_Orders1;

    @FXML
    private TableView<RBY> TableView_RBM;

    @FXML
    private TableColumn<RBY, Double> col_RBM_Total2;

    @FXML
    private TableColumn<RBY, Double> col_RBM_Total1;

    @FXML
    private TableColumn<RBY, Integer> col_RBM_Year;

    @FXML
    private TableColumn<RBY, Integer> col_RBM_Month;

    @FXML
    private TableColumn<RBY, Integer> col_RBM_Orders1;

    @FXML
    private TableColumn<RBY, Integer> col_RBM_Orders2;

    @FXML
    private TableView<OAB> TableView_OBY;

    @FXML
    private TableColumn<OAB, Double> col_OBY1;

    @FXML
    private TableColumn<OAB, Double> col_OBY2;

    @FXML
    private TableColumn<OAB, Integer> col_OBY3;

    @FXML
    private TableColumn<OAB, Integer> col_OBY4;

    @FXML
    private TableColumn<OAB, Integer> col_OBY5;

    @FXML
    private TableView<OAB> TableView_ABY;

    @FXML
    private TableColumn<OAB, Double> col_ABY1;

    @FXML
    private TableColumn<OAB, Double> col_ABY2;

    @FXML
    private TableColumn<OAB, Integer> col_ABY3;

    @FXML
    private TableColumn<OAB, Integer> col_ABY4;

    @FXML
    private TableColumn<OAB, Integer> col_ABY5;

    @FXML
    private TableView<OAB> TableView_OBM;

    @FXML
    private TableColumn<OAB, Double> col_OBM1;

    @FXML
    private TableColumn<OAB, Double> col_OBM2;

    @FXML
    private TableColumn<OAB, Integer> col_OBM3;

    @FXML
    private TableColumn<OAB, Integer> col_OBM4;

    @FXML
    private TableColumn<OAB, Integer> col_OBM5;

    @FXML
    private TableColumn<OAB, Integer> col_OBM6;

    @FXML
    private TableView<OAB> TableView_ABM;

    @FXML
    private TableColumn<OAB, Double> col_ABM1;

    @FXML
    private TableColumn<OAB, Double> col_ABM2;

    @FXML
    private TableColumn<OAB, Integer> col_ABM3;

    @FXML
    private TableColumn<OAB, Integer> col_ABM4;

    @FXML
    private TableColumn<OAB, Integer> col_ABM5;

    @FXML
    private TableColumn<OAB, Integer> col_ABM6;

    @FXML
    private JFXButton btn_RevenueByYear;

    @FXML
    private JFXButton btn_OrdersByYear;

    @FXML
    private JFXButton Top_Rating;

    @FXML
    private JFXButton btn_Comment;

    @FXML
    private JFXButton btn_OrdersByMonth;

    @FXML
    private JFXButton btn_AppsByYear;

    @FXML
    private JFXButton btn_AppsByMonth;

    @FXML
    private JFXButton btn_TopTrending;

    @FXML
    private TableColumn<RBY, Integer> col_RBY_Orders2;

    StatisticsDAO dao = new StatisticsDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableView_RBM.toFront();
        fillTableRBM();
        setEvent();
        displayFormAnimation();
    }

    public void drawBarChart(String title,String xValue ,String yValue,XYChart.Series... args) {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        pnl_Barchart.setTitle(title);
        xAxis.setLabel(xValue);
        yAxis.setLabel(yValue);

        pnl_Barchart.getData().clear();
        for (XYChart.Series arg : args) {
            pnl_Barchart.getData().add(arg);
        }

        pnl_Barchart.setBarGap(3);
        pnl_Barchart.setCategoryGap(30);
        yAxis.setAnimated(false);
        
    }

    void fillTableRBY() {
        List<RBY> listObjects = dao.getRevenue_ByYear();

        ObservableList<RBY> list = FXCollections.observableArrayList(listObjects);

        col_RBY_Year.setCellValueFactory(new PropertyValueFactory<>("Year"));
        col_RBY_Total1.setCellValueFactory(new PropertyValueFactory<>("revenue1"));
        col_RBY_Orders1.setCellValueFactory(new PropertyValueFactory<>("total1"));
        col_RBY_Total2.setCellValueFactory(new PropertyValueFactory<>("revenue2"));
        col_RBY_Orders2.setCellValueFactory(new PropertyValueFactory<>("total2"));
        col_RBY_Total1.setCellFactory((TableColumn<RBY, Double> param) -> new TableCell<RBY, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    double price = (double) Math.round(item * 100) / 100;
                    setText(price + "$");
                }
            }
        });
        col_RBY_Total2.setCellFactory((TableColumn<RBY, Double> param) -> new TableCell<RBY, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    double price = (double) Math.round(item * 100) / 100;
                    setText(price + "$");
                }
            }
        });

        TableView_RBY.getItems().removeAll();
        TableView_RBY.setItems(list);

        List<PieChart.Data> datas = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            total += list.get(i).getRevenue2();

        }

        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            double number = (double) Math.round(list.get(i).getRevenue2() / total * 10000) / 100;
            datas.add(new PieChart.Data(list.get(i).getYear() + " - " + number + "%", list.get(i).getRevenue2()));
        }
        drawPieChart(datas, "Revenue of the last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")");
        
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("ETIMATE");
        series2.setName("REALITY");
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            series1.getData().add(new XYChart.Data(list.get(i).getYear()+"", list.get(i).getRevenue1()));
            series2.getData().add(new XYChart.Data(list.get(i).getYear()+"", list.get(i).getRevenue2()));
        }
        drawBarChart("Revenue of the last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")","$","year",series1, series2);
    }

    void fillTableRBM() {
        List<RBY> listObjects = dao.getRevenue_ByMonth();

        ObservableList<RBY> list = FXCollections.observableArrayList(listObjects);

        col_RBM_Year.setCellValueFactory(new PropertyValueFactory<>("Year"));
        col_RBM_Month.setCellValueFactory(new PropertyValueFactory<>("month"));
        col_RBM_Total1.setCellValueFactory(new PropertyValueFactory<>("revenue1"));
        col_RBM_Orders1.setCellValueFactory(new PropertyValueFactory<>("total1"));
        col_RBM_Total2.setCellValueFactory(new PropertyValueFactory<>("revenue2"));
        col_RBM_Orders2.setCellValueFactory(new PropertyValueFactory<>("total2"));
        col_RBM_Total1.setCellFactory((TableColumn<RBY, Double> param) -> new TableCell<RBY, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    double price = (double) Math.round(item * 100) / 100;
                    setText(price + "$");
                }
            }
        });
        col_RBM_Total2.setCellFactory((TableColumn<RBY, Double> param) -> new TableCell<RBY, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    double price = (double) Math.round(item * 100) / 100;
                    setText(price + "$");
                }
            }
        });

        TableView_RBM.getItems().removeAll();
        TableView_RBM.setItems(list);

        List<PieChart.Data> datas = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            total += list.get(i).getRevenue2();

        }

        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            double number = (double) Math.round(list.get(i).getRevenue2() / total * 10000) / 100;
            datas.add(new PieChart.Data("Month " + list.get(i).getMonth() + " - " + number + "%", list.get(i).getRevenue2()));
        }
        drawPieChart(datas, "Revenue of the last 5 month (" + list.get(0).getYear() + ")");
        
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("ETIMATE");
        series2.setName("REALITY");
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            series1.getData().add(new XYChart.Data("Month"+list.get(i).getMonth()+"", list.get(i).getRevenue1()));
            series2.getData().add(new XYChart.Data("Month"+list.get(i).getMonth()+"", list.get(i).getRevenue2()));
        }
        drawBarChart("Revenue of the last 5 month (" + list.get(0).getYear() + ")","$",",month",series1, series2);
    }

    void fillTableABY() {
        List<OAB> listObjects = dao.getOdersApps_ByYear();

        ObservableList<OAB> list = FXCollections.observableArrayList(listObjects);

        col_ABY1.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_ABY2.setCellValueFactory(new PropertyValueFactory<>("apps"));
        col_ABY3.setCellValueFactory(new PropertyValueFactory<>("processingApps"));
        col_ABY4.setCellValueFactory(new PropertyValueFactory<>("acceptedApp"));
        col_ABY5.setCellValueFactory(new PropertyValueFactory<>("refundedApps"));

        TableView_ABY.getItems().removeAll();
        TableView_ABY.setItems(list);

        List<PieChart.Data> datas = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            total += list.get(i).getAcceptedApp();

        }

        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            double number = (double) Math.round(list.get(i).getAcceptedApp() / total * 10000) / 100;
            datas.add(new PieChart.Data(list.get(i).getYear() + " - " + number + "%", list.get(i).getAcceptedApp()));
        }
        drawPieChart(datas, "Number of Application sold last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")");
        
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        series1.setName("Total");
        series2.setName("Processing");
        series3.setName("Accepted");
        series4.setName("Refunded");
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            String name =list.get(i).getYear()+"";
            series1.getData().add(new XYChart.Data(name, list.get(i).getApps()));
            series2.getData().add(new XYChart.Data(name, list.get(i).getProcessingApps()));
            series3.getData().add(new XYChart.Data(name, list.get(i).getAcceptedApp()));
            series4.getData().add(new XYChart.Data(name, list.get(i).getRefundedApps()));
        }
        drawBarChart("Number of Application sold last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")","Apps","Year",series1, series2,series3, series4);
    }

    void fillTableOBY() {
        List<OAB> listObjects = dao.getOdersApps_ByYear();

        ObservableList<OAB> list = FXCollections.observableArrayList(listObjects);

        col_OBY1.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_OBY2.setCellValueFactory(new PropertyValueFactory<>("orders"));
        col_OBY3.setCellValueFactory(new PropertyValueFactory<>("processingOrders"));
        col_OBY4.setCellValueFactory(new PropertyValueFactory<>("acceptedOrders"));
        col_OBY5.setCellValueFactory(new PropertyValueFactory<>("refundedOrders"));

        TableView_OBY.getItems().removeAll();
        TableView_OBY.setItems(list);

        List<PieChart.Data> datas = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            total += list.get(i).getOrders();

        }

        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            double number = (double) Math.round(list.get(i).getOrders() / total * 10000) / 100;
            datas.add(new PieChart.Data(list.get(i).getYear() + " - " + number + "%", list.get(i).getOrders()));
        }
        drawPieChart(datas, "Total orders last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")");
        
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        series1.setName("Total");
        series2.setName("Processing");
        series3.setName("Accepted");
        series4.setName("Refunded");
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            String name =list.get(i).getYear()+"";
            series1.getData().add(new XYChart.Data(name, list.get(i).getOrders()));
            series2.getData().add(new XYChart.Data(name, list.get(i).getProcessingOrders()));
            series3.getData().add(new XYChart.Data(name, list.get(i).getAcceptedOrders()));
            series4.getData().add(new XYChart.Data(name, list.get(i).getRefundedOrders()));
        }
        drawBarChart("Total orders last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")","Orders","Year",series1, series2,series3, series4);
    }

    void fillTableOBM() {
        List<OAB> listObjects = dao.getOdersApps_ByMonth();

        ObservableList<OAB> list = FXCollections.observableArrayList(listObjects);

        col_OBM1.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_OBM2.setCellValueFactory(new PropertyValueFactory<>("month"));
        col_OBM3.setCellValueFactory(new PropertyValueFactory<>("orders"));
        col_OBM4.setCellValueFactory(new PropertyValueFactory<>("processingOrders"));
        col_OBM5.setCellValueFactory(new PropertyValueFactory<>("acceptedOrders"));
        col_OBM6.setCellValueFactory(new PropertyValueFactory<>("refundedOrders"));

        TableView_OBM.getItems().removeAll();
        TableView_OBM.setItems(list);

        List<PieChart.Data> datas = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            total += list.get(i).getOrders();
        }

        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            double number = (double) Math.round(list.get(i).getOrders() / total * 10000) / 100;
            datas.add(new PieChart.Data("Month " + list.get(i).getMonth() + " - " + number + "%", list.get(i).getOrders()));
        }
        drawPieChart(datas, "Total orders last 5 month (" + list.get(0).getYear() + ")");
        
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        series1.setName("Total");
        series2.setName("Processing");
        series3.setName("Accepted");
        series4.setName("Refunded");
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            String name =list.get(i).getYear()+"-"+list.get(i).getMonth();
            series1.getData().add(new XYChart.Data(name, list.get(i).getOrders()));
            series2.getData().add(new XYChart.Data(name, list.get(i).getProcessingOrders()));
            series3.getData().add(new XYChart.Data(name, list.get(i).getAcceptedOrders()));
            series4.getData().add(new XYChart.Data(name, list.get(i).getRefundedOrders()));
        }
        drawBarChart("Total orders last 5 month (" + list.get(0).getYear() + ")","Orders","Year",series1, series2,series3, series4);
    }

    void fillTableABM() {
        List<OAB> listObjects = dao.getOdersApps_ByMonth();

        ObservableList<OAB> list = FXCollections.observableArrayList(listObjects);

        col_ABM1.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_ABM2.setCellValueFactory(new PropertyValueFactory<>("month"));
        col_ABM3.setCellValueFactory(new PropertyValueFactory<>("apps"));
        col_ABM4.setCellValueFactory(new PropertyValueFactory<>("processingApps"));
        col_ABM5.setCellValueFactory(new PropertyValueFactory<>("acceptedApp"));
        col_ABM6.setCellValueFactory(new PropertyValueFactory<>("refundedApps"));

        TableView_ABM.getItems().removeAll();
        TableView_ABM.setItems(list);

        List<PieChart.Data> datas = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            total += list.get(i).getAcceptedApp();
        }

        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            double number = (double) Math.round(list.get(i).getAcceptedApp() / total * 10000) / 100;
            datas.add(new PieChart.Data("Month " + list.get(i).getMonth() + " - " + number + "%", list.get(i).getAcceptedApp()));
        }
        drawPieChart(datas, "Number of applicaton sold last 5 month (" + list.get(0).getYear() + ")");
        
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        series1.setName("Total");
        series2.setName("Processing");
        series3.setName("Accepted");
        series4.setName("Refunded");
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            String name =list.get(i).getYear()+""+list.get(i).getMonth();
            series1.getData().add(new XYChart.Data(name, list.get(i).getApps()));
            series2.getData().add(new XYChart.Data(name, list.get(i).getProcessingApps()));
            series3.getData().add(new XYChart.Data(name, list.get(i).getAcceptedApp()));
            series4.getData().add(new XYChart.Data(name, list.get(i).getRefundedApps()));
        }
        drawBarChart("Number of applicaton sold last 5 month (" + list.get(0).getYear() + ")","Apps","Month",series1, series2,series3, series4);
    }

    void setEvent() {
        btn_RevenueByMonth.setOnAction((event) -> {
            TableView_RBM.toFront();
            fillTableRBM();
        });
        btn_RevenueByYear.setOnAction((event) -> {
            TableView_RBY.toFront();
            fillTableRBY();
        });
        btn_OrdersByMonth.setOnAction((event) -> {
            TableView_OBM.toFront();
            fillTableOBM();
        });
        btn_OrdersByYear.setOnAction((event) -> {
            TableView_OBY.toFront();
            fillTableOBY();
        });
        btn_AppsByMonth.setOnAction((event) -> {
            TableView_ABM.toFront();
            fillTableABM();
        });
        btn_AppsByYear.setOnAction((event) -> {
            TableView_ABY.toFront();
            fillTableABY();
        });
    }

    void drawPieChart(List<PieChart.Data> list, String title) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(list);
        pnl_PieChart.setData(pieChartData);
        pnl_PieChart.setTitle(title);
        pnl_PieChart.setLabelLineLength(10);
        pnl_PieChart.setLegendSide(Side.BOTTOM);
    }


    void displayFormAnimation() {
        new FadeInDownBig(pnl_Controller).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeftBig(pnl_Barchart).play();
        new FadeInRightBig(pnl_Table).play();
        new FadeInUpBig(pnl_PieChart).play();
    }

}
