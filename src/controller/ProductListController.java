/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ApplicationDAO;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import model.Application;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import until.Value;
import until.Variable;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ProductListController implements Initializable {

    @FXML
    private Label lbl_filter;

    @FXML
    private Pane pnl_List;

    @FXML
    private Text lbl_Decription;

    @FXML
    private TilePane tile_List;

    @FXML
    private Label lbl_ListName;

    @FXML
    private TextField txt_SreachApp;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private Pane pane;

    @FXML
    private ImageView img_Header;

    @FXML
    private Pane pnl_Title_In;

    @FXML
    private Label lbl_Title;
    
    @FXML
    private Label lbl_Loading;

    @FXML
    private Pane pnl_Header;

//    double col = 0;
    double space = 30;

    List<Application> listApplications = new ArrayList<>();
    ApplicationDAO applicationDAO = new ApplicationDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Variable.START = Instant.now();
        setEvent();
        fillDataOnBackground();
    }
    void setEvent(){
        txt_SreachApp.setOnKeyReleased((event) -> {
            fillDataOnBackground();
        });
    }
    void fillDataOnBackground() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(new Runnable() {
                    public void run() {
                        fillProduct_Box();
                    }
                });
            }
        }.start();
    }

    private void fillProduct_Box() {
        
        lbl_Loading.setText("Loading applications....");
        lbl_Loading.setVisible(true);
        listApplications = applicationDAO.selectByKeyWord(1,txt_SreachApp.getText().trim());
        if(listApplications.size()==0) lbl_Loading.setText("Not found applications");
        int row = listApplications.size() / 4+ (listApplications.size()%4==0?0:1);
        int col = 4;

        try {
            //pnl_ScrollList.setPrefSize(WIDTH_VIEW, HEIGHT_VIEW);
            Pane product = (Pane) FXMLLoader.load(getClass().getResource(Value.PRODUCT_BOX));

            tile_List.getChildren().clear();
            tile_List.setHgap(space);
            tile_List.setVgap(space);

            double height = (product.getPrefHeight() + space) * row;
            double width = (product.getPrefWidth() + space) * col;

            pnl_List.setPrefHeight(height>240?height:240);
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
        if(listApplications.size()!=0) lbl_Loading.setVisible(false);
    }

}
