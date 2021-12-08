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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.ApplicationStatistic;
import model.OAB;
import model.ProgressStatistic;
import model.RBY;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
import until.ProcessString;
import until.Variable;

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
    private JFXButton btn_NewProgress;

    @FXML
    private JFXButton btn_TopViews;

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
    private TableView<ApplicationStatistic> TableView_AS;

    @FXML
    private TableColumn<ApplicationStatistic, Integer> col_AS1;

    @FXML
    private TableColumn<ApplicationStatistic, String> col_AS2;

    @FXML
    private TableColumn<ApplicationStatistic, Double> col_AS3;

    @FXML
    private TableColumn<ApplicationStatistic, Integer> col_AS4;

    @FXML
    private TableColumn<ApplicationStatistic, Integer> col_AS5;

    @FXML
    private TableColumn<ApplicationStatistic, Double> col_AS6;

    @FXML
    private TableColumn<ApplicationStatistic, Integer> col_AS7;

    @FXML
    private TableColumn<ApplicationStatistic, Double> col_AS8;

    @FXML
    private TableColumn<ApplicationStatistic, Integer> col_AS9;

    @FXML
    private TableView<ProgressStatistic> TableView_NP;

    @FXML
    private TableColumn<ProgressStatistic, Integer> col_NP1;

    @FXML
    private TableColumn<ProgressStatistic, Integer> col_NP2;

    @FXML
    private TableColumn<ProgressStatistic, Integer> col_NP3;

    @FXML
    private TableColumn<ProgressStatistic, Integer> col_NP5;

    @FXML
    private TableColumn<ProgressStatistic, Integer> col_NP6;

    @FXML
    private TableColumn<ProgressStatistic, Integer> col_NP7;

    @FXML
    private TableColumn<ProgressStatistic, Integer> col_NP4;

    @FXML
    private JFXButton btn_RevenueByYear;

    @FXML
    private JFXButton btn_OrdersByYear;

    @FXML
    private JFXButton btn_TopRatings;

    @FXML
    private JFXButton btn_TopComments;

    @FXML
    private JFXButton btn_OrdersByMonth;

    @FXML
    private JFXButton btn_AppsByYear;

    @FXML
    private JFXButton btn_AppsByMonth;

    @FXML
    private JFXButton btn_TopTrending;

    @FXML
    private JFXButton btn_OP1;

    @FXML
    private JFXButton btn_OP2;

    @FXML
    private JFXButton btn_OP3;

    @FXML
    private JFXButton btn_OP4;

    @FXML
    private Label lbl_TableName;

    @FXML
    private TableColumn<RBY, Integer> col_RBY_Orders2;

    List<ApplicationStatistic> list;
    StatisticsDAO dao = new StatisticsDAO();
    int selectedIndex = 0;
    String[] header;
    List<Object[]> listObjs;
    String fileName, title;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableView_RBM.toFront();
        fillTableRBM();
        selectedIndex = 1;
        setEvent();
        setEventExport();
        displayFormAnimation();
    }

    public void drawBarChart(String title, String xValue, String yValue, XYChart.Series... args) {

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
        lbl_TableName.setText("List revenue by Year");

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
            series1.getData().add(new XYChart.Data(list.get(i).getYear() + "", list.get(i).getRevenue1()));
            series2.getData().add(new XYChart.Data(list.get(i).getYear() + "", list.get(i).getRevenue2()));
        }
        drawBarChart("Revenue of the last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")", "$", "year", series1, series2);
    }

    void fillTableRBM() {
        lbl_TableName.setText("List revenue by Month");

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
            series1.getData().add(new XYChart.Data("Month" + list.get(i).getMonth() + "", list.get(i).getRevenue1()));
            series2.getData().add(new XYChart.Data("Month" + list.get(i).getMonth() + "", list.get(i).getRevenue2()));
        }
        drawBarChart("Revenue of the last 5 month (" + list.get(0).getYear() + ")", "$", ",month", series1, series2);
    }

    void fillTableABY() {
        lbl_TableName.setText("List of apps sold by year");

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
            String name = list.get(i).getYear() + "";
            series1.getData().add(new XYChart.Data(name, list.get(i).getApps()));
            series2.getData().add(new XYChart.Data(name, list.get(i).getProcessingApps()));
            series3.getData().add(new XYChart.Data(name, list.get(i).getAcceptedApp()));
            series4.getData().add(new XYChart.Data(name, list.get(i).getRefundedApps()));
        }
        drawBarChart("Number of Application sold last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")", "Apps", "Year", series1, series2, series3, series4);
    }

    void fillTableOBY() {
        lbl_TableName.setText("List of total orders by year");
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
            String name = list.get(i).getYear() + "";
            series1.getData().add(new XYChart.Data(name, list.get(i).getOrders()));
            series2.getData().add(new XYChart.Data(name, list.get(i).getProcessingOrders()));
            series3.getData().add(new XYChart.Data(name, list.get(i).getAcceptedOrders()));
            series4.getData().add(new XYChart.Data(name, list.get(i).getRefundedOrders()));
        }
        drawBarChart("Total orders last 5 years (" + list.get(4).getYear() + "-" + list.get(0).getYear() + ")", "Orders", "Year", series1, series2, series3, series4);
    }

    void fillTableOBM() {
        lbl_TableName.setText("List of total orders sold by year");
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
            String name = list.get(i).getYear() + "-" + list.get(i).getMonth();
            series1.getData().add(new XYChart.Data(name, list.get(i).getOrders()));
            series2.getData().add(new XYChart.Data(name, list.get(i).getProcessingOrders()));
            series3.getData().add(new XYChart.Data(name, list.get(i).getAcceptedOrders()));
            series4.getData().add(new XYChart.Data(name, list.get(i).getRefundedOrders()));
        }
        drawBarChart("Total orders last 5 month (" + list.get(0).getYear() + ")", "Orders", "Year", series1, series2, series3, series4);
    }

    void fillTableABM() {
        lbl_TableName.setText("List of apps sold by Month");
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
            String name = list.get(i).getYear() + "-" + list.get(i).getMonth();
            series1.getData().add(new XYChart.Data(name, list.get(i).getApps()));
            series2.getData().add(new XYChart.Data(name, list.get(i).getProcessingApps()));
            series3.getData().add(new XYChart.Data(name, list.get(i).getAcceptedApp()));
            series4.getData().add(new XYChart.Data(name, list.get(i).getRefundedApps()));
        }
        drawBarChart("Number of applicaton sold last 5 month (" + list.get(0).getYear() + ")", "Apps", "Month", series1, series2, series3, series4);
    }

    void fillTableNP() {
        lbl_TableName.setText("List of progress by month");
        List<ProgressStatistic> listObjects = dao.getNewProgress();

        ObservableList<ProgressStatistic> listOL = FXCollections.observableArrayList(listObjects);

        col_NP1.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_NP2.setCellValueFactory(new PropertyValueFactory<>("month"));
        col_NP3.setCellValueFactory(new PropertyValueFactory<>("account"));
        col_NP4.setCellValueFactory(new PropertyValueFactory<>("views"));
        col_NP5.setCellValueFactory(new PropertyValueFactory<>("ratings"));
        col_NP6.setCellValueFactory(new PropertyValueFactory<>("comments"));
        col_NP7.setCellValueFactory(new PropertyValueFactory<>("applications"));

        TableView_NP.getItems().removeAll();
        TableView_NP.setItems(listOL);

        List<PieChart.Data> datas = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 5; i++) {
            if (i >= listOL.size()) {
                break;
            }
            total += listOL.get(i).getViews();
        }

        for (int i = 0; i < 5; i++) {
            if (i >= listOL.size()) {
                break;
            }
            double number = (double) Math.round(listOL.get(i).getViews() / total * 10000) / 100;
            datas.add(new PieChart.Data("Month " + listOL.get(i).getMonth() + " - " + number + "%", listOL.get(i).getViews()));
        }
        drawPieChart(datas, "Progress for last 4 month (" + listOL.get(0).getYear() + ")");

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        series1.setName("Accounts");
        series2.setName("Views");
        series3.setName("Ratings");
        series4.setName("Comments");
        series5.setName("Applications");

        for (int i = 0; i < 4; i++) {
            if (i >= listOL.size()) {
                break;
            }
            String name = listOL.get(i).getYear() + "-" + listOL.get(i).getMonth();
            series1.getData().add(new XYChart.Data(name, listOL.get(i).getAccount()));
            series2.getData().add(new XYChart.Data(name, listOL.get(i).getViews()));
            series3.getData().add(new XYChart.Data(name, listOL.get(i).getRatings()));
            series4.getData().add(new XYChart.Data(name, listOL.get(i).getComments()));
            series4.getData().add(new XYChart.Data(name, listOL.get(i).getApplications()));
        }
        drawBarChart("Progress for last 4 month (" + listOL.get(0).getYear() + ")", "Turns", "Month", series1, series2, series3, series4, series5);
    }

    void fillTableAS() {

        ObservableList<ApplicationStatistic> list = FXCollections.observableArrayList(this.list);

        col_AS1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_AS2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_AS3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_AS4.setCellValueFactory(new PropertyValueFactory<>("sold"));
        col_AS5.setCellValueFactory(new PropertyValueFactory<>("refund"));
        col_AS6.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_AS7.setCellValueFactory(new PropertyValueFactory<>("views"));
        col_AS8.setCellValueFactory(new PropertyValueFactory<>("ratings"));
        col_AS9.setCellValueFactory(new PropertyValueFactory<>("comments"));

        col_AS3.setCellFactory((TableColumn<ApplicationStatistic, Double> param) -> new TableCell<ApplicationStatistic, Double>() {
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
        col_AS6.setCellFactory((TableColumn<ApplicationStatistic, Double> param) -> new TableCell<ApplicationStatistic, Double>() {
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
        col_AS8.setCellFactory((TableColumn<ApplicationStatistic, Double> param) -> new TableCell<ApplicationStatistic, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    double price = (double) Math.round(item * 100) / 100;
                    setText(price + " ★");
                }
            }
        });

        TableView_AS.getItems().removeAll();
        TableView_AS.setItems(list);
    }

    void fillList(int z) {
        list = dao.getStatisticApplications();
        Collections.sort(list, new Comparator<ApplicationStatistic>() {
            @Override
            public int compare(ApplicationStatistic t, ApplicationStatistic t1) {
                switch (z) {
                    case 1:
                        Integer s1 = t.getSold();
                        Integer s2 = t1.getSold();
                        return s1.compareTo(s2);
                    case 2:
                        Integer r1 = t.getRefund();
                        Integer r2 = t1.getRefund();
                        return r1.compareTo(r2);
                    case 3:
                        Integer v1 = t.getViews();
                        Integer v2 = t1.getViews();
                        return v1.compareTo(v2);
                    case 4:
                        Integer c1 = t.getComments();
                        Integer c2 = t1.getComments();
                        return c1.compareTo(c2);
                    case 5:
                        Double ra1 = t.getRatings();
                        Double ra2 = t1.getRatings();
                        return ra1.compareTo(ra2);
                }
                return 0;
            }

        });
        Collections.reverse(list);

        List<PieChart.Data> datas = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            switch (z) {
                case 1:
                    total += list.get(i).getSold();
                    break;
                case 2:
                    total += list.get(i).getRefund();
                    break;
                case 3:
                    total += list.get(i).getViews();
                    break;
                case 4:
                    total += list.get(i).getComments();
                    break;
                case 5:
                    total += list.get(i).getRatings();
                    break;
            }

        }

        String title = "";
        for (int i = 0; i < 5; i++) {
            if (i >= list.size()) {
                break;
            }
            double number = 0;
            switch (z) {
                case 1:
                    lbl_TableName.setText("List best selling apps");
                    title = "Top 5 best selling apps";
                    number = (double) Math.round(list.get(i).getSold() / total * 10000) / 100;
                    break;
                case 2:
                    lbl_TableName.setText("List apps with the most refunds");
                    title = "Top 5 apps with the most refunds";
                    number = (double) Math.round(list.get(i).getRefund() / total * 10000) / 100;
                    break;
                case 3:
                    lbl_TableName.setText("List most viewed apps");
                    title = "Top 5 most viewed apps";
                    number = (double) Math.round(list.get(i).getViews() / total * 10000) / 100;
                    break;
                case 4:
                    lbl_TableName.setText("List most commented apps");
                    title = "Top 5 most commented apps";
                    number = (double) Math.round(list.get(i).getComments() / total * 10000) / 100;
                    break;
                case 5:
                    lbl_TableName.setText("List high ratings apps");
                    title = "Top 5 high ratings apps";
                    number = (double) Math.round(list.get(i).getRatings() / total * 10000) / 100;
                    break;
            }
            datas.add(new PieChart.Data(ProcessString.cutString(list.get(i).getName(), 10) + " - " + number + "%", number));
        }
        drawPieChart(datas, title);

        XYChart.Series series1 = new XYChart.Series();

        for (int i = 0; i < 4; i++) {
            if (i >= list.size()) {
                break;
            }
            String name = ProcessString.cutString(list.get(i).getName(), 20);
            switch (z) {
                case 1:
                    series1.setName("Sold");
                    series1.getData().add(new XYChart.Data(name, list.get(i).getSold()));
                    break;
                case 2:
                    series1.setName("Refund");
                    series1.getData().add(new XYChart.Data(name, list.get(i).getRefund()));
                    break;
                case 3:
                    series1.setName("Views");
                    series1.getData().add(new XYChart.Data(name, list.get(i).getViews()));
                    break;
                case 4:
                    series1.setName("Comment");
                    series1.getData().add(new XYChart.Data(name, list.get(i).getComments()));
                    break;
                case 5:
                    series1.setName("Ratings");
                    series1.getData().add(new XYChart.Data(name, list.get(i).getRatings()));
                    break;
            }

        }
        drawBarChart(title, "Turns", "Month", series1);
    }

    void setEvent() {
        btn_RevenueByMonth.setOnAction((event) -> {
            TableView_RBM.toFront();
            fillTableRBM();
            selectedIndex=1;
            setEventExport();
        });
        btn_RevenueByYear.setOnAction((event) -> {
            TableView_RBY.toFront();
            fillTableRBY();
            selectedIndex=2;
            setEventExport();
        });
        btn_OrdersByMonth.setOnAction((event) -> {
            TableView_OBM.toFront();
            fillTableOBM();
            selectedIndex=3;
            setEventExport();
        });
        btn_OrdersByYear.setOnAction((event) -> {
            TableView_OBY.toFront();
            fillTableOBY();
            selectedIndex=4;
            setEventExport();
        });
        btn_AppsByMonth.setOnAction((event) -> {
            TableView_ABM.toFront();
            fillTableABM();
            selectedIndex=3;
            setEventExport();
        });
        btn_AppsByYear.setOnAction((event) -> {
            TableView_ABY.toFront();
            fillTableABY();
            selectedIndex=4;
            setEventExport();
        });
        btn_TopTrending.setOnAction((event) -> {
            TableView_AS.toFront();
            fillList(1);
            fillTableAS();
            selectedIndex=5;
            setEventExport();
        });
        btn_TopRefund.setOnAction((event) -> {
            TableView_AS.toFront();
            fillList(2);
            fillTableAS();
            selectedIndex=5;
            setEventExport();
        });
        btn_TopViews.setOnAction((event) -> {
            TableView_AS.toFront();
            fillList(3);
            fillTableAS();
            selectedIndex=5;
            setEventExport();
        });
        btn_TopRatings.setOnAction((event) -> {
            TableView_AS.toFront();
            fillList(5);
            fillTableAS();
            selectedIndex=5;
            setEventExport();
        });
        btn_TopComments.setOnAction((event) -> {
            TableView_AS.toFront();
            fillList(4);
            fillTableAS();
            selectedIndex=5;
            setEventExport();
        });
        btn_NewProgress.setOnAction((event) -> {
            TableView_NP.toFront();
            fillTableNP();
            selectedIndex=6;
            setEventExport();
        });
        
        btn_OP2.setOnAction(evt -> {
            try {
                ExportPDF.ExportPDF(Variable.MAIN_STAGE, header, listObjs, fileName + ".pdf", title);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btn_OP3.setOnAction(evt -> {

            try {
                ExportExcel.exportFile(Variable.MAIN_STAGE, header, listObjs, fileName + ".xlsx", title);
            } catch (IOException ex) {
                Logger.getLogger(Management_OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_OP1.setOnAction(evt -> {
            try {
                ExportText.exportText(Variable.MAIN_STAGE,header, listObjs, fileName + ".txt");
            } catch (IOException ex) {
                //Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void setEventExport() {
        
        if (selectedIndex == 2) {
            List<RBY> list = dao.getRevenue_ByYear();
            listObjs = new ArrayList<>();
            list.forEach((e) -> {
                listObjs.add(e.toObjectRBY());
            });
            header = new String[]{"Year","Total amount sold(etimate)","Total revenue(etimate)","Total amount sold(reality)","Total revenue(realtity)"};
             fileName = "RevenueByYear";
             title = "List revenue by year";
        }else if(selectedIndex == 1){
            List<RBY> list = dao.getRevenue_ByMonth();
            listObjs = new ArrayList<>();
            list.forEach((e) -> {
                listObjs.add(e.toObjectRBM());
            });
            header = new String[]{"Month","Total amount sold(etimate)","Total revenue(etimate)","Total amount sold(reality)","Total revenue(realtity)"};
             fileName = "RevenueByMonth";
             title = "List revenue by month";
        }else if(selectedIndex ==3){
            List<OAB> list = dao.getOdersApps_ByMonth();
            listObjs = new ArrayList<>();
            list.forEach((e) -> {
                listObjs.add(e.toObjectOAM());
            });
            header = new String[]{"Month","Total amount sold(etimate)","Total revenue(etimate)","Total amount sold(reality)","Total revenue(realtity)"};
             fileName = "OrdersAndAppsSoldByMonth";
             title = "List orders and apps amount sold by month";
        }else if(selectedIndex ==4){
            List<OAB> list = dao.getOdersApps_ByYear();
            listObjs = new ArrayList<>();
            list.forEach((e) -> {
                listObjs.add(e.toObjectOAY());
            });
            header = new String[]{"Year","Total amount sold(etimate)","Total revenue(etimate)","Total amount sold(reality)","Total revenue(realtity)"};
             fileName = "OrdersAndAppsSoldByYear";
             title = "List orders and apps amount sold by year";
        }else if(selectedIndex ==5){
            List<ApplicationStatistic> list = dao.getStatisticApplications();
            listObjs = new ArrayList<>();
            list.forEach((e) -> {
                listObjs.add(e.toObject());
            });
            header = new String[]{"ApplicationID","Name","Price","Amount sold","Amount refund","Total proceeds","Views","Ratings","Comments"};
             fileName = "StatisticApplications";
             title = "List Statistic Applications";
        }else if(selectedIndex ==6){
            List<ProgressStatistic> list = dao.getNewProgress();
            listObjs = new ArrayList<>();
            list.forEach((e) -> {
                listObjs.add(e.toObject());
            });
            header = new String[]{"New Month","New Accounts","New Views","New Ratings","New Comments","New Applications"};
             fileName = "ProgressByMonth";
             title = "List progress by month";
        }

        
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
