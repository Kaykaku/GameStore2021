/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.NewsDAO;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.News;
import until.Value;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class NewsListController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    private Pane pnl_List;

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
    
    double space=30;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setEvent();
        fillDataOnBackground();
    }    
    void setEvent() {
        txt_SreachApp.setOnKeyReleased((event) -> {
            fillDataOnBackground();
        });
        btn_Back.setOnMouseClicked((evt) -> {
            PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);
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
                    fillNews_Box();
                });
            }
        }.start();
    }
    private void fillNews_Box() {
        lbl_Loading.setText("Loading news....");
        lbl_Loading.setVisible(true);
        List<News> list = new NewsDAO().selectActiveByKeyWord(txt_SreachApp.getText().trim());
        if (list.size() == 0) {
            lbl_Loading.setText("Not found news");
        }
        int row = list.size() / 3 + (list.size() % 3 == 0 ? 0 : 1);
        int col = 3;

        try {
            //pnl_ScrollList.setPrefSize(WIDTH_VIEW, HEIGHT_VIEW);
            Pane product = (Pane) FXMLLoader.load(getClass().getResource(Value.NEWS_BOX));

            tile_List.getChildren().clear();
            tile_List.setHgap(space);
            tile_List.setVgap(space);

            double height = (product.getPrefHeight() + space) * row;
            double width = (product.getPrefWidth() + space) * col;

            pnl_List.setPrefHeight(height > 240 ? height : 240);
            pane.setPrefHeight(pnl_Header.getPrefHeight() + pnl_Title_In.getPrefHeight() + pnl_List.getPrefHeight() + 30);

            tile_List.setPrefWidth(width);
            tile_List.setPrefHeight(height);
            tile_List.setLayoutX((pnl_List.getPrefWidth() - tile_List.getPrefWidth()) / 2+20);
            News_BoxController[] controllers = new News_BoxController[list.size()];
            Node[] nodes = new Node[list.size()];
            for (int i = 0; i < list.size(); i++) {

                final int h = i;

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.NEWS_BOX));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                controllers[h].setInfo(list.get(h));
                tile_List.getChildren().add(nodes[h]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!list.isEmpty()) {
            lbl_Loading.setVisible(false);
        }
    }
}
