/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ApplicationDAO;
import DAO.WishlistDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Account;
import model.Application;
import model.Wishlist;
import until.Auth;
import until.Validation;
import until.Value;
import static until.Value.PAY;
import until.Variable;
import static until.Variable.PNL_VIEW;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class WishlistController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Pane pnl_List;

    @FXML
    private Pane pnl_Title;

    @FXML
    private Pane pnl_Price_Info;

    @FXML
    private Pane pnl_Order_Info;

    @FXML
    private Label lbl_Total_Price;

    @FXML
    private ScrollPane pnl_ScrollList;

    @FXML
    private VBox vbox_ListProduct;

    @FXML
    private Pane pnl_WishList;

    @FXML
    private JFXButton btn_Payment;
    
    @FXML
    private JFXButton btn_Back;

    Account user =Auth.USER;
    double total=0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadWishList();
        setEvent();
    }  
    void setEvent(){
        btn_Payment.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PAY));
            Node node;
            try {
                node = (Node) loader.load();
                PayController controller = loader.getController();
                controller.setInformations(this);
                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
//                Logger.getLogger(DisplayProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_Back.setOnMouseClicked((event) -> {
            Variable.IS_WISHLIST_OPEN=false;
            PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);           
        });
    }
    void loadWishList(){
        total=0;
        List<Wishlist> list = new WishlistDAO().selectByAccountID(user.getAccountId());
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_WISHLIST));
            double height = (paneP.getPrefHeight() +vbox_ListProduct.getSpacing())* list.size();
            pnl_List.setPrefHeight(height );
            vbox_ListProduct.setPrefSize(paneP.getPrefWidth(), height);
            
            vbox_ListProduct.getChildren().clear();
            Pane[] nodes = new Pane[list.size()];
            Row_WishlistController[] controllers= new Row_WishlistController[list.size()];
            for (int i = 0; i < list.size(); i++) {
                final int h = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.ROW_WISHLIST));
                nodes[h] = (Pane) loader.load();
                controllers[h]=loader.getController();
                Application app =new ApplicationDAO().selectByID(list.get(h).getApplicationId());
                controllers[h].setInfo(app);
                total+=controllers[h].getPrice();
                Button btn_delete =controllers[h].getBtnDelete();
                btn_delete.setOnMouseClicked((event) -> {
                    new WishlistDAO().delete(user.getAccountId(), app.getApplicationID());
                    loadWishList();
                });
                
                vbox_ListProduct.getChildren().add(nodes[h]);
            }
        } catch (IOException e) {
        }
        Validation.price = total;
        lbl_Total_Price.setText(total+"$");
    }
}
