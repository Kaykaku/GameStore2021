/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import until.Auth;
import until.ProcessImage;
import until.Value;
import static until.Value.*;
import until.Variable;

/**
 *
 * @author Admin
 */
public class MainController implements Initializable {

    @FXML
    private ImageView img_User_Icon_Small;

    @FXML
    private Label lbl_UserName_Hide;

    @FXML
    private ImageView img_User_Icon_Medium;

    @FXML
    private Pane pnl_View;

    @FXML
    private Label lbl_UserName;

    @FXML
    private VBox vbox_menu;

    @FXML
    private Pane pnl_ManageAccount;

    @FXML
    private Button btn_Minimize;

    @FXML
    private Label lbl_Email_Hide;

    @FXML
    private VBox vbox_menuHide;

    @FXML
    private Button btn_Exit;

    @FXML
    private JFXButton btn_WishLish;

    @FXML
    private Pane pnl_menu;

    @FXML
    private JFXButton btn_AccountSettings;

    @FXML
    private Label lbl_SignOut;

    boolean isShowManageAccount = false;
    private boolean[] isSelected;
    int menu = 7;
    ArrayList<String> ListView;
    ArrayList<Object[]> ListItems;
    MenuItemController[] controller;
    private boolean isUser = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnl_ManageAccount.setTranslateY(-pnl_ManageAccount.getPrefHeight());
        Variable.PNL_VIEW = pnl_View;
        Variable.WIDTH_VIEW = pnl_View.getPrefWidth();
        Variable.HEIGHT_VIEW = pnl_View.getPrefHeight();

        loadRoleItems();
        drawMenuItems();
        loadUserInfo();

        controller[0].setStyle("-fx-background-color: #454545;");
        controller[0].setItemInfo("", ListItems.get(0)[1].toString() + "selected.png");
        drawPanelView(ListView.get(0),true);

        img_User_Icon_Small.setOnMouseClicked(evt -> {
            showManageAccount();
        });
        btn_AccountSettings.setOnMouseClicked(evt -> {
            if(!Variable.IS_ACCOUNT_INFORMATION_OPEN)drawPanelView(FORM_USER_INFORMATION,false);
            Variable.IS_ACCOUNT_INFORMATION_OPEN=true;        
            showManageAccount();
        });
        btn_WishLish.setOnMouseClicked(evt -> {
            if(!Variable.IS_WISHLIST_OPEN)drawPanelView(FORM_WISHLISH,false);
            Variable.IS_WISHLIST_OPEN=true;
            showManageAccount();
        });
        btn_Exit.setOnMouseClicked(evt ->{
            System.exit(0);
        });
        btn_Minimize.setOnMouseClicked(evt ->{
            
        });
        lbl_SignOut.setOnMouseClicked(evt ->{
            signOut(evt);
        });
    }
    void signOut(MouseEvent evt){
                try {
                    ((Node) (evt.getSource())).getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource(Value.FORM_LOGIN));
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                Auth.USER = null;
    }
    void loadRoleItems() {
        pnl_ManageAccount.setTranslateX(0);
        ListView = new ArrayList<>();
        ListItems = new ArrayList<>();

        ListView.add(FORM_HOME);
        ListItems.add(new Object[]{"Home", "home40"});
        
        if (!Auth.isManager()) {            
            ListView.add(FORM_HOME_GAMES);
            ListView.add(FORM_HOME_APPS);
            ListView.add(FORM_LIBRARY);
            
            ListItems.add(new Object[]{"Games", "games40"});
            ListItems.add(new Object[]{"Apps", "apps40"});
            ListItems.add(new Object[]{"Library", "library40"});
            
        }
        if (Auth.isAdmin()) {
            ListView.add(FORM_ACCOUNT);         
            ListView.add(FORM_CATEGORY);
            ListView.add(FORM_PRODUCT);
            ListView.add(FORM_ORDER);
            ListView.add(FORM_NEWS);
            ListView.add(FORM_STATISTICS);
            ListItems.add(new Object[]{"Accounts", "accounts40"});
            ListItems.add(new Object[]{"Category", "categories40"});
            ListItems.add(new Object[]{"Products", "products40"});
            ListItems.add(new Object[]{"Orders", "orders40"});
            ListItems.add(new Object[]{"News", "news40"});
            ListItems.add(new Object[]{"Statistics", "statistics40"});
            vbox_menuHide.getChildren().remove(vbox_menuHide.getChildren().size()-1);
            pnl_ManageAccount.setPrefHeight(pnl_ManageAccount.getPrefHeight()-btn_WishLish.getPrefHeight()-10);
        }
    }

    void loadUserInfo() {
        if (Auth.USER.getImage() != null) {
            img_User_Icon_Small.setImage( new Image(ProcessImage.toFile(Auth.USER.getImage(), "smallAvatar.png").toURI().toString()));
            img_User_Icon_Medium.setImage( new Image(ProcessImage.toFile(Auth.USER.getImage(), "smallAvatar.png").toURI().toString()));
            RoundedImageView.RoundedImage(img_User_Icon_Small, 35);
            RoundedImageView.RoundedImage(img_User_Icon_Medium, img_User_Icon_Medium.getFitWidth());
        }
        lbl_UserName.setText(Auth.USER.getUsername());
        lbl_UserName_Hide.setText(Auth.USER.getName());
        lbl_Email_Hide.setText(Auth.USER.getEmail());
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
                    Variable.IS_ACCOUNT_INFORMATION_OPEN=false;
                    Variable.IS_WISHLIST_OPEN=false;
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
                        drawPanelView(ListView.get(h),true);
                        controller[h].setStyle("-fx-background-color: #454545;");
                    }

                });
            }
        } catch (IOException ex) {

        }
    }

    void drawPanelView(String formView,boolean clear) {
        if(clear)pnl_View.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formView));
            
            if(formView.equals(Value.FORM_HOME)){               
                Variable.HOME_PAGE=0;
            }else if(formView.equals(Value.FORM_HOME_GAMES)){
                loader = new FXMLLoader(getClass().getResource(FORM_HOME));
                Variable.HOME_PAGE=1;
            }else if(formView.equals(Value.FORM_HOME_APPS)){
                loader = new FXMLLoader(getClass().getResource(FORM_HOME));
                Variable.HOME_PAGE=2;
            }
            Node node = (Node) loader.load();
            pnl_View.getChildren().add(node);
            
        } catch (IOException ex) {
            //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void showManageAccount() {
        if (!isShowManageAccount) {
            pnl_ManageAccount.setOpacity(1);
            new SlideInDown(pnl_ManageAccount).play();
        } else {
            new FadeOutUp(pnl_ManageAccount).play();
        }
        isShowManageAccount = !isShowManageAccount;
    }

}
