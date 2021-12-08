/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class Dialog_MessageController implements Initializable {

    @FXML
    private Text txt_Content;

    @FXML
    private JFXButton btn_OP1;
    @FXML
    private JFXButton btn_OP2;

    @FXML
    private ImageView img_robot;

    @FXML
    private Pane pnl_Box;
    
    @FXML
    private HBox hbox_OP;
    
    @FXML
    private HBox hbox_content;
   int selected =0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ZoomIn ani = new ZoomIn(pnl_Box);
        ani.setSpeed(2);
        ani.play();
        btn_OP1.requestFocus();
        btn_OP1.setOnMouseClicked((event) -> {
            selected=1;
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
        btn_OP2.setOnMouseClicked((event) -> {
            selected=2;
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
        btn_OP1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    selected=1;
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            }
        });
        btn_OP2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    selected=2;
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            }
        });
    }

//    public void setText(String text,int mode) {
//        if (text.endsWith("\n")) {
//            text = text.substring(0, text.length() - 1);
//        }
//        txt_Content.setText(text);
//        img_robot.setFitWidth(txt_Content.getBoundsInParent().getHeight() > img_robot.getFitWidth() ? txt_Content.getBoundsInParent().getHeight() : img_robot.getFitWidth());
//        pnl_Box.setPrefHeight(pnl_Box.getBoundsInParent().getHeight() + 40);
//        btn_OP1.setLayoutX(pnl_Box.getBoundsInParent().getWidth()+20);
//        btn_OP1.setLayoutY(pnl_Box.getBoundsInParent().getHeight() - 10);
//    }
    public void setText(String text,int mode) {
        if (text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
        }
        if(mode==0){
            hbox_OP.getChildren().remove(btn_OP2);
        } else if(mode==1){
            btn_OP1.setText("Yes");
            btn_OP2.setText("No");
        }
        txt_Content.setText(text);
        img_robot.setFitWidth(txt_Content.getBoundsInParent().getHeight() > img_robot.getFitWidth() ? txt_Content.getBoundsInParent().getHeight() : img_robot.getFitWidth());
        img_robot.setFitHeight(img_robot.getFitWidth());
        pnl_Box.layout();
        pnl_Box.setPrefWidth(hbox_content.getWidth()+20);
        hbox_OP.setLayoutX(pnl_Box.getPrefWidth()-(hbox_OP.getChildren().size()*110));
        hbox_OP.setLayoutY(txt_Content.getBoundsInParent().getHeight()>90?txt_Content.getBoundsInParent().getHeight()+30:90);
    }
    public int getSelectOP(){
        return  selected;
    };
}
