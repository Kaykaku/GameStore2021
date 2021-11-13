/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import until.Value;
import static until.Value.*;

/**
 *
 * @author Admin
 */
public class MainController implements Initializable {

    @FXML
    private VBox vbox_menu;

    @FXML
    private Pane pnl_View;

    @FXML
    private Pane pnl_ManageAccount;
    
    @FXML
    private ImageView img_User_Icon_Small;
    
    @FXML
    private JFXButton btn_WishLish;
    
    @FXML
    private JFXButton btn_AccountSettings;
    
    boolean isShowManageAccount = false;
    private boolean[] isSelected;
    int menu = 7;
    ArrayList<String> ListView;
    ArrayList<Object[]> ListItems;
    MenuItemController[] controller;
    private boolean isUser =false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PNL_VIEW = pnl_View;
        ListView = new ArrayList<>();
        ListView.add(FORM_HOME);
        ListView.add(FORM_HOME_APPS);
        ListView.add(FORM_HOME_GAMES);
        ListView.add(FORM_ACCOUNT);
        ListView.add(FORM_CATEGORY);
        ListView.add(FORM_PRODUCT);
        ListView.add(FORM_ORDER);
        ListView.add(FORM_NEWS);
        ListView.add(FORM_STATISTICS);
        ListView.add(FORM_LIBRARY);

        ListItems = new ArrayList<>();
        ListItems.add(new Object[]{"Home", "home40"});
        ListItems.add(new Object[]{"Apps", "apps40"});
        ListItems.add(new Object[]{"Games", "games40"});
        ListItems.add(new Object[]{"Accounts", "accounts40"});
        ListItems.add(new Object[]{"Category", "categories40"});
        ListItems.add(new Object[]{"Products", "products40"});
        ListItems.add(new Object[]{"Orders", "orders40"});
        ListItems.add(new Object[]{"News", "news40"});
        ListItems.add(new Object[]{"Statistics", "statistics40"});
        ListItems.add(new Object[]{"Library", "library40"});

        Value.WIDTH_VIEW = pnl_View.getPrefWidth();
        Value.HEIGHT_VIEW = pnl_View.getPrefHeight();
        drawMenuItems();
        drawPanelView(EMPTY);

        controller[0].setStyle("-fx-background-color: #454545;");
        controller[0].setItemInfo("", ListItems.get(0)[1].toString() + "selected.png");
        drawPanelView(ListView.get(0));
        
        img_User_Icon_Small.setOnMouseClicked(evt->{
            showManageAccount();
        });
        btn_AccountSettings.setOnMouseClicked(evt->{
            drawPanelView(FORM_USER_INFORMATION);
            showManageAccount();
        });
        btn_WishLish.setOnMouseClicked(evt->{
            drawPanelView(FORM_WISHLISH);
            showManageAccount();
        });
    }

    private void drawMenuItems() {
        vbox_menu.getChildren().clear();

        Button[] menuItems = new Button[ListView.size()];
        controller = new MenuItemController[ListView.size()];
        isSelected = new boolean[ListView.size()];
        isSelected[0] = true;
        try {
            for (int i = 0; i < ListItems.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/gui/Item/MenuItem.fxml"));
                menuItems[i] = (Button) loader.load();
                vbox_menu.getChildren().add(menuItems[i]);
                final int h = i;
                String ItemsName = ListItems.get(i)[0].toString();
                String ItemsIcon = ListItems.get(i)[1].toString();

                controller[h] = loader.getController();
                controller[h].setItemInfo(ItemsName, ItemsIcon + ".png");
                menuItems[i].setOnMouseEntered(evt -> {
                    if (!isSelected[h]) {
                        controller[h].setItemInfo(ItemsName, ItemsIcon + "hover.png");
                    }
                });
                menuItems[i].setOnMouseExited(evt -> {
                    if (!isSelected[h]) {
                        controller[h].setItemInfo(ItemsName, ItemsIcon + ".png");
                    }
                });
                menuItems[i].setOnMouseReleased(evt -> {
                    Arrays.fill(isSelected, Boolean.FALSE);
                    isSelected[h] = true;
                    for (MenuItemController ct : controller) {
                        String icon = ct.getIconName();
                        String name = ct.getItemName();
                        icon = icon.substring(0, icon.indexOf("40"));
                        if (name.isEmpty()) {
                            name = icon.substring(0, 1).toUpperCase() + icon.substring(1);
                        }
                        ct.setItemInfo(name, icon + "40.png");
                        ct.setStyle("-fx-background-color: #202020;");
                    }
                    if (isSelected[h]) {
                        controller[h].setItemInfo("", ItemsIcon + "selected.png");
                        new BounceIn(menuItems[h]).play();
                        //showManagement();
                        drawPanelView(ListView.get(h));
                        controller[h].setStyle("-fx-background-color: #454545;");
                    }

                });
            }
        } catch (IOException ex) {

        }
    }

    void drawPanelView(String formView) {
        pnl_View.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formView));
            Node node = (Node) loader.load();

            pnl_View.getChildren().add(node);
        } catch (IOException ex) {
            //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void showManageAccount() {
        if (!isShowManageAccount) {
            new FlipInX(pnl_ManageAccount).play();
        } else {
            new FlipOutX(pnl_ManageAccount).play();
        }
        isShowManageAccount=!isShowManageAccount;
    }

    public double getWidth_view() {
        return pnl_View.getPrefWidth();
    }

    public double getHeight_view() {
        return pnl_View.getPrefHeight();
    }

}
