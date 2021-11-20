/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ApplicationDAO;
import animatefx.animation.SlideOutLeft;
import animatefx.animation.SlideOutRight;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Application;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import until.Value;
import static until.Variable.HEIGHT_VIEW;
import static until.Variable.WIDTH_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ProductListController implements Initializable {

    @FXML
    private GridPane list;
    @FXML
    private Pane pane;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Pane pnl_Title;
    @FXML
    private Pane pnl_Title_In;
    /**
     * Initializes the controller class.
     */
//    double col = 0;
    double space = 30;
    double row = 4;
    boolean isRegisterForm = false;
    boolean isChangePassForm = false;

    List<Application> listApplications = new ArrayList<>();
    ApplicationDAO applicationDAO = new ApplicationDAO();
    @FXML
    private TextField txt_SreachApp;
    @FXML
    private Label lbl_filter;
    @FXML
    private ComboBox<Integer> cbo_Year;
    @FXML
    private Pane pnl_filter;
    @FXML
    private JFXButton btn_Less50_Price;
    @FXML
    private JFXButton btn_Less100_Price;
    @FXML
    private JFXButton btn_Less200_Price;
    @FXML
    private JFXButton btn_More200_Price;
    @FXML
    private JFXButton btn_Year_ReleaseDay;
    @FXML
    private JFXButton btn_Week_ReleaseDay;
    @FXML
    private JFXButton btn_Month_ReleaseDay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fillComboBoxYear();
        
        try {

            listApplications = applicationDAO.selectAll();
            fillProduct_Box(listApplications);

            pane.setOnScroll((event) -> {
                if (pnl_ScrollList.getVvalue() > 0.37) {
                    pnl_Title.setOpacity(1);
                    pnl_Title_In.setOpacity(0);
                } else {
                    pnl_Title.setOpacity(0);
                    pnl_Title_In.setOpacity(1);
                }
            });
            pane.setOnMouseMoved((event) -> {
                if (pnl_ScrollList.getVvalue() > 0.37) {
                    pnl_Title.setOpacity(1);
                    pnl_Title_In.setOpacity(0);
                } else {
                    pnl_Title.setOpacity(0);
                    pnl_Title_In.setOpacity(1);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        lbl_filter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        new SlideOutLeft(pnl_filter).play();
                        lbl_filter.setGraphic(new ImageView("icons/icons8-exit.png"));
                    } else if (mouseEvent.getClickCount() == 1) {
                        new SlideOutRight(pnl_filter).play();
                        lbl_filter.setGraphic(new ImageView("icons/icons8-list.png"));
                    }
                }
            }
        });
        
        txt_SreachApp.setOnAction(event ->{
            listApplications = applicationDAO.selectByKeyWord(txt_SreachApp.getText().trim());
            fillProduct_Box(listApplications);
        });
        
        //Lỗi logic 
        cbo_Year.setOnAction(event -> {
            int year = cbo_Year.getSelectionModel().getSelectedItem();

            System.out.println(year);
            listApplications = applicationDAO.getReleaseDay_SearchByYear(year);
            fillProduct_Box(listApplications);
        });

        btn_Week_ReleaseDay.setOnAction((event) -> {
            listApplications = applicationDAO.getReleaseDay_ThisWeek();
            fillProduct_Box(listApplications);
        });

        btn_Month_ReleaseDay.setOnAction((event) -> {
            listApplications = applicationDAO.getReleaseDay_ThisMonth();
            fillProduct_Box(listApplications);
        });

        btn_Year_ReleaseDay.setOnAction((event) -> {
            listApplications = applicationDAO.getReleaseDay_ThisYear();
            fillProduct_Box(listApplications);
        });
    }

    private void fillProduct_Box(List<Application> listApplications) {
        double col = listApplications.size() / 4;
        
        try {
            pnl_ScrollList.setPrefSize(WIDTH_VIEW, HEIGHT_VIEW);
            Pane product = (Pane) FXMLLoader.load(getClass().getResource(Value.PRODUCT_BOX));
            list.getChildren().clear();
            list.setHgap(space);
            list.setVgap(space);
            list.setPrefSize(product.getPrefWidth() * row + space * row + 20, product.getPrefHeight() * col + space * col + 20);
            pane.setPrefSize(WIDTH_VIEW - 15, product.getPrefHeight() * col + space * col + 20 + 590);
            list.setPadding(new Insets(space, space, space, space));
            list.setLayoutX(list.getLayoutX() + (WIDTH_VIEW - list.getPrefWidth()) / 2);
            ProductBoxController[] controllers = new ProductBoxController[listApplications.size()];
            Node[] nodes = new Node[listApplications.size()];
            int jump=0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < col; j++) {
                    final int h = jump;
                    jump++;

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(Value.PRODUCT_BOX));
                    nodes[h] = (Pane) loader.load();
                    controllers[h] = loader.getController();

                    controllers[h].setAppInfo(listApplications.get(h));
                    list.add(nodes[h], i, j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillComboBoxYear() {
        List<Integer> list = applicationDAO.selectYears();
        try {
            cbo_Year.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
