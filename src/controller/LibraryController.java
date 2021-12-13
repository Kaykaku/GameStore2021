/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ApplicationDAO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import model.Application;
import until.Auth;
import until.Value;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class LibraryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pnl_List;

    @FXML
    private Pane pnl_Library;

    @FXML
    private Pane pnl_Title;

    @FXML
    private TilePane tile_List;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private TextField txt_Search;

    List<Application> list;
    List<Application> listRefund;

    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        fillLibraryBox();
        txt_Search.setOnKeyPressed((event) -> {
            fillLibraryBox();
        });
    }

    public void fillLibraryBox() {
        listRefund = new ApplicationDAO().selectEnableRufundApplications(Auth.USER.getAccountId(), txt_Search.getText().trim());
        list = new ApplicationDAO().selectPurchaseApplications(Auth.USER.getAccountId(), txt_Search.getText().trim());
        double col = list.size() / 4 + 1;
        double space = 20;

        try {
            Pane product = (Pane) FXMLLoader.load(getClass().getResource(Value.LIBRARY_BOX));
            double height = product.getPrefHeight() * col + space * col;
            tile_List.getChildren().clear();
            tile_List.setPrefHeight(height);
            pnl_List.setPrefHeight(height > pnl_List.getPrefHeight() ? height + 40 : pnl_List.getPrefHeight());
            Library_Product_BoxController[] controllers = new Library_Product_BoxController[list.size()];
            Node[] nodes = new Node[list.size()];
            for (int i = 0; i < list.size(); i++) {
                final int h = i;

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.LIBRARY_BOX));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                controllers[h].setInfo(list.get(h));
                boolean flag = false;
                for (Application application : listRefund) {
                    if (application.getApplicationID() == list.get(h).getApplicationID()) {
                        flag = true;
                        break;
                    }
                }
                controllers[h].setController(this);
                controllers[h].setRefund(flag);
                tile_List.getChildren().add(nodes[h]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
