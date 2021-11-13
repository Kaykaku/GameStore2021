/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MenuItemController implements Initializable {

    @FXML
    private Button btn_item;

    @FXML
    private ImageView img_itemicon;
    
    String iconName ;
    String itemName ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void setItemInfo (String itemName , String itemIcon ){
        iconName =itemIcon;
        this.itemName =itemName;
        btn_item.setText(itemName);
        img_itemicon.setImage(new Image(Main.class.getResource("/icons/"+itemIcon)+""));
    }
    public void setStyle(String style){
        btn_item.setStyle(style);
    }
    public String getIconName(){
        return iconName;
    }
    public String getItemName(){
        return itemName;
    }
}
