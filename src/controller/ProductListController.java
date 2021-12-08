/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ApplicationDAO;
import DAO.CategoryDAO;
import DAO.StatisticsDAO;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import model.Application;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.ApplicationStatistic;
import model.Category;
import until.Value;
import until.Variable;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ProductListController implements Initializable {

    @FXML
    private Label lbl_Loading;

    @FXML
    private Label lbl_filter;

    @FXML
    private Text lbl_Decription;

    @FXML
    private TextField txt_SreachApp;

    @FXML
    private ChoiceBox<String> cbx_sort;

    @FXML
    private ImageView img_Header;

    @FXML
    private Label lbl_ListName111;

    @FXML
    private Pane pnl_Header;

    @FXML
    private Label lbl_Title;

    @FXML
    private ChoiceBox<Category> cbx_category;

    @FXML
    private ChoiceBox<String> cbx_advanced;

    @FXML
    private Label lbl_ListName11;

    @FXML
    private Pane pnl_List;

    @FXML
    private Label lbl_ListName1;

    @FXML
    private TilePane tile_List;

    @FXML
    private Label lbl_ListName;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Pane pane;

    @FXML
    private Pane pnl_Title_In;

    @FXML
    private JFXButton btn_Back;

    @FXML
    private JFXButton btn_Filter;

//    double col = 0;
    double space = 30;

    List<Application> listApplications = new ArrayList<>();
    ApplicationDAO applicationDAO = new ApplicationDAO();
    int categoryId = 0, advanced = 0, sort = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Variable.START = Instant.now();
        setEvent();
        fillChoiceBox();
//        fillDataOnBackground();
    }

    public void setFilter(int categoryId,int advanced){
        this.categoryId=categoryId;
        this.advanced=advanced;
        List<Category> cates = new CategoryDAO().selectAll();
        for (int i = 0; i < cates.size(); i++) {
            if(cates.get(i).getCategoryId()==categoryId){
                cbx_category.getSelectionModel().select(i);
                break;
            }
        }
        txt_SreachApp.setText("");
        cbx_advanced.getSelectionModel().select(advanced);
        fillDataOnBackground();
    }
    void setEvent() {
        txt_SreachApp.setOnKeyReleased((event) -> {
            fillDataOnBackground();
        });
        btn_Back.setOnMouseClicked((evt) -> {
            PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);
        });
        cbx_category.setOnAction((event) -> {
            categoryId = cbx_category.getSelectionModel().getSelectedItem().getCategoryId();
        });
        cbx_advanced.setOnAction((event) -> {
            advanced = cbx_advanced.getSelectionModel().getSelectedIndex();
        });
        cbx_sort.setOnAction((event) -> {
            sort = cbx_sort.getSelectionModel().getSelectedIndex();
        });
        btn_Filter.setOnMouseClicked((event) -> {
            if (advanced != 0) {
                cbx_sort.getSelectionModel().select(0);
            }
            fillDataOnBackground();
        });
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
                    fillProduct_Box();
                });
            }
        }.start();
    }

    void fillChoiceBox() {
        List<String> list = new ArrayList<>();
        List<Category> listcate = new CategoryDAO().selectAll();
        cbx_category.setItems(FXCollections.observableArrayList(listcate));
        cbx_category.getSelectionModel().select(0);

        list.clear();
        list.add("All");
        list.add("Top new");
        list.add("Top views");
        list.add("Top trending");
        list.add("Top comments");
        list.add("Top ratings");
        list.add("Top sale");
        cbx_advanced.setItems(FXCollections.observableArrayList(list));
        cbx_advanced.getSelectionModel().select(0);

        list.clear();
        list.add("All");
        list.add("Name");
        list.add("Price");
        list.add("Date release");
        list.add("Size");
        cbx_sort.setItems(FXCollections.observableArrayList(list));
        cbx_sort.getSelectionModel().select(0);
    }

    void filter() {
        if (advanced != 0) {
            List<ApplicationStatistic> listStatistics = new StatisticsDAO().getStatisticApplications();
            Collections.sort(listStatistics, new Comparator<ApplicationStatistic>() {
                @Override
                public int compare(ApplicationStatistic t, ApplicationStatistic t1) {
                    switch (advanced) {
                        case 0:
                            Integer i1 = t.getId();
                            Integer i2 = t1.getId();
                            return i1.compareTo(i2);
                        case 3:
                            Integer s1 = t.getSold();
                            Integer s2 = t1.getSold();
                            return s1.compareTo(s2);
                        case 2:
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
            Collections.reverse(listStatistics);

            List<Application> clone = new ArrayList<>();
            for (int i = 0; i < listStatistics.size(); i++) {
                for (Application app : listApplications) {
                    if (listStatistics.get(i).getId() == app.getApplicationID()) {
                        clone.add(app);
                        break;
                    }
                }
            }
            listApplications = clone;
        }
        if (sort == 0) {
            return;
        }
        Comparator<Application> com = new Comparator<Application>() {
            @Override
            public int compare(Application t, Application t1) {
                Integer i = 0, i1 = 0;
                Double d = 0.0, d1 = 0.0;
                switch (advanced) {
                    case 6:
                        d = t.getSale();
                        d1 = t1.getSale();
                        return d.compareTo(d1);
                    case 1:
                        Date dt = t.getReleaseDay();
                        Date dt1 = t1.getReleaseDay();
                        return dt.compareTo(dt1);
                }
                return 0;
            }
        };
        Collections.sort(listApplications, com);
        Comparator<Application> com1 = new Comparator<Application>() {
            @Override
            public int compare(Application t, Application t1) {
                Integer i = 0, i1 = 0;
                Double d = 0.0, d1 = 0.0;
                switch (sort) {
                    case 0:
                        i = t.getApplicationID();
                        i1 = t1.getApplicationID();
                        return i.compareTo(i1);
                    case 1:
                        return t.getName().compareTo(t1.getName());
                    case 2:
                        d = t.getPrice();
                        d1 = t1.getPrice();
                        return d.compareTo(d1);
                    case 3:
                        Date dt = t.getReleaseDay();
                        Date dt1 = t1.getReleaseDay();
                        return dt.compareTo(dt1);
                    case 4:
                        d = t.getSize();
                        d1 = t1.getSize();
                        return d.compareTo(d1);
                }
                return 0;
            }
        };
        Collections.sort(listApplications, com1);
    }

    private void fillProduct_Box() {
        lbl_ListName.setText(cbx_advanced.getSelectionModel().getSelectedItem()+" "+cbx_category.getSelectionModel().getSelectedItem().getName()+" "+"games");
        lbl_Loading.setText("Loading applications....");
        lbl_Loading.setVisible(true);
        listApplications = applicationDAO.selectByKeyWord(txt_SreachApp.getText().trim(), categoryId, 1);
        filter();
        if (listApplications.size() == 0) {
            lbl_Loading.setText("Not found applications");
        }
        int row = listApplications.size() / 4 + (listApplications.size() % 4 == 0 ? 0 : 1);
        int col = 4;

        try {
            //pnl_ScrollList.setPrefSize(WIDTH_VIEW, HEIGHT_VIEW);
            Pane product = (Pane) FXMLLoader.load(getClass().getResource(Value.PRODUCT_BOX));

            tile_List.getChildren().clear();
            tile_List.setHgap(space);
            tile_List.setVgap(space);

            double height = (product.getPrefHeight() + space) * row;
            double width = (product.getPrefWidth() + space) * col;

            pnl_List.setPrefHeight(height > 240 ? height : 240);
            pane.setPrefHeight(pnl_Header.getPrefHeight() + pnl_Title_In.getPrefHeight() + pnl_List.getPrefHeight() + 30);

            tile_List.setPrefWidth(width);
            tile_List.setPrefHeight(height);
            tile_List.setLayoutX((pnl_List.getPrefWidth() - tile_List.getPrefWidth()) / 2);
            ProductBoxController[] controllers = new ProductBoxController[listApplications.size()];
            Node[] nodes = new Node[listApplications.size()];
            for (int i = 0; i < listApplications.size(); i++) {

                final int h = i;

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.PRODUCT_BOX));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                controllers[h].setAppInfo(listApplications.get(h));
                tile_List.getChildren().add(nodes[h]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listApplications.size() != 0) {
            lbl_Loading.setVisible(false);
        }
    }

}
