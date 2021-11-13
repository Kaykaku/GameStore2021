/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import animatefx.animation.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
    private Pane pnl_Table;

    @FXML
    private PieChart pnl_PieChart;

    @FXML
    private Pane pnl_Title;

    @FXML
    private BarChart<String, Number> pnl_Barchart;

    @FXML
    private GridPane pnl_GridButton;

    final static String May = "May";
    final static String Jun = "Jun";
    final static String Jul = "Jul";
    final static String Aug = "Aug";
    final static String Sep = "Sep";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Property 1", 13),
                        new PieChart.Data("Property 2", 25),
                        new PieChart.Data("Property 3", 10),
                        new PieChart.Data("Property 4", 22),
                        new PieChart.Data("Property 5", 30));
        pnl_PieChart.setData(pieChartData);
        pnl_PieChart.setTitle("Properties");
        pnl_PieChart.setLabelLineLength(10);
        pnl_PieChart.setLegendSide(Side.LEFT);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        pnl_Barchart.setTitle("monthly revenue by type");
        xAxis.setLabel("Type");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Games");
        series1.getData().add(new XYChart.Data(May, 25601.34));
        series1.getData().add(new XYChart.Data(Jun, 20148.82));
        series1.getData().add(new XYChart.Data(Jul, 10000));
        series1.getData().add(new XYChart.Data(Aug, 35407.15));
        series1.getData().add(new XYChart.Data(Sep, 12000));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Ebook");
        series2.getData().add(new XYChart.Data(May, 57401.85));
        series2.getData().add(new XYChart.Data(Jun, 41941.19));
        series2.getData().add(new XYChart.Data(Jul, 45263.37));
        series2.getData().add(new XYChart.Data(Aug, 117320.16));
        series2.getData().add(new XYChart.Data(Sep, 14845.27));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Video");
        series3.getData().add(new XYChart.Data(May, 45000.65));
        series3.getData().add(new XYChart.Data(Jun, 44835.76));
        series3.getData().add(new XYChart.Data(Jul, 18722.18));
        series3.getData().add(new XYChart.Data(Aug, 17557.31));
        series3.getData().add(new XYChart.Data(Sep, 92633.68));

        pnl_Barchart.getData().addAll(series1, series2, series3);
        pnl_Barchart.setBarGap(3);
        pnl_Barchart.setCategoryGap(30);
        yAxis.setAnimated(false);

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(
                new KeyFrame(Duration.millis(500),
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        for (XYChart.Series< String,Number> series : ((BarChart<String,Number>) pnl_Barchart).getData()) {
                            for (XYChart.Data< String,Number> data : series.getData()) {
                                data.setYValue(Math.random() * 1);
                            }
                        }
                    }
                }
                ));
        tl.setCycleCount(1);
        tl.setAutoReverse(true);  
        tl.setDelay(new Duration(300));
        tl.play();     
        displayFormAnimation();
    }
    void displayFormAnimation() {
        new FadeInDownBig(pnl_GridButton).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeftBig(pnl_Barchart).play();
        new FadeInRightBig(pnl_Table).play();
        new FadeInUpBig(pnl_PieChart).play();
    }

}
