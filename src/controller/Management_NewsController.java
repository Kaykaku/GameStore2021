/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import DAO.NewsDAO;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import model.Account;
import model.News;
import static until.Auth.USER;
import until.Catch_Errors;
import until.Dialog;
import until.ProcessImage;
import until.ProcessDate;


/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_NewsController implements Initializable {
        byte [] path = null;
    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextArea txt_Description;

    @FXML
    private ImageView new_Image;

    @FXML
    private JFXTextArea txt_Content;

    @FXML
    private Pane pnl_Add_Info;

    @FXML
    private JFXTextField txt_Title;

    @FXML
    private Pane pnl_Title;

    @FXML
    private Pane pnl_ListNews;

    @FXML
    private ScrollPane pnl_ScrollListNews;

    @FXML
    private Pane pnl_CreationDate;

    @FXML
    private Pane pnl_List_News;

    @FXML
    private Pane pnl_Image;

    @FXML
    private JFXButton btn_Add;
    
    @FXML
    private JFXButton btn_Update;
    
    @FXML
    private JFXButton btn_Delete;
    @FXML
    private Pane pnl_Content;

    @FXML
    private Pane pnl_Basic_Info;

    @FXML
    private VBox Vbox_ListNews;

    @FXML
    private HBox hbox_Controller;
    
    @FXML
    private Label lbl_NewsID;

    @FXML
    private Label lbl_AccountId;
    
    @FXML
    private Label lbl_Dateerror;
    
    @FXML
    private Label lbl_ImageError;
    
    @FXML
    private TextField txt_Search;
    int s ;
    int row = 20;
            JFXDatePicker datePicker = new JFXDatePicker();
            NewsDAO newsDao = new NewsDAO();
            AccountDAO accountDao = new AccountDAO();
            List<News> list_News = new ArrayList<>();
            Image image = new Image("/icons/add-image (1).png");
            
        @Override
        public void initialize(URL url, ResourceBundle rb) { 
                //error
                lbl_ImageError.setVisible(false);
                //Set form last index
                lbl_AccountId.setText(USER.getName());
                // 
                datePicker.setDisable(true);
                datePicker.setDefaultColor(Paint.valueOf("lightblue"));
                datePicker.setStyle("-fx-text-fill: green");
                pnl_CreationDate.getChildren().add(datePicker);
                datePicker.setValue(ProcessDate.toLocalDate(ProcessDate.now()));
                ProcessDate.converter(datePicker);
                new_Image.setImage(image);
                //
                displayFormAnimation();
                fill_ListView();
                enable_BtnAdd();
                enable_BtnUpdate();
                enable_DeleteBtn();
                search();
        }
    
     void fill_ListView(){
            list_News = newsDao.selectByKeyWord(txt_Search.getText().trim());
            try {
                Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
                double height = (paneP.getPrefHeight() + Vbox_ListNews.getSpacing()) * list_News.size()*2;
                Vbox_ListNews.setPrefSize(paneP.getPrefWidth(), height);
                pnl_ListNews.setPrefHeight(height > pnl_ScrollListNews.getPrefHeight() ? height : pnl_ScrollListNews.getPrefHeight());
                
                Vbox_ListNews.getChildren().clear();
                Pane[] nodes = new Pane[list_News.size()];
                Row_NewsController[] controllers = new Row_NewsController[list_News.size()];
                
                for (int i = 0; i < list_News.size(); i++) {                
                    final int h = i;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/gui/Item/Row_News.fxml"));
                    nodes[h] = (Pane) loader.load();
                    controllers[h] = loader.getController();
                    
                    Vbox_ListNews.getChildren().add(nodes[h]);
                    controllers[h].setNewsInfor(list_News.get(h));
                    
                    nodes[h].setOnMouseClicked(evt -> {
                    setFormNews(list_News.get(h));
                    enable_DeleteBtn();
                    enable_BtnAdd();
                    enable_BtnUpdate();
                });
                }
            } catch (IOException ex) {
                Logger.getLogger(Management_NewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
  
    void displayFormAnimation() {
        new FadeInDown(pnl_Basic_Info).play();
        new FadeInDownBig(pnl_Image).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Add_Info).play();
        new FadeInRightBig(pnl_List_News).play();
        new FadeInUpBig(pnl_Content).play();
        new ZoomInUp(hbox_Controller).play();
    }  
        
    void setFormNews(News entity) {
        Account  acc2 = new Account();
        AccountDAO acc = new AccountDAO();
        acc2 = acc.selectByID(entity.getAccountId());
        lbl_NewsID.setText(entity.getNewsID()+ "");
        lbl_AccountId.setText(acc2.getName());
        datePicker.setValue(ProcessDate.toLocalDate(entity.getCreationDate()));
        txt_Title.setText(entity.getTitle());
        txt_Description.setText(entity.getDescription());
        txt_Content.setText(entity.getContents());
        if (entity.getImage()!= null) {
            new_Image.setImage(new Image(ProcessImage.toFile(entity.getImage(), "appIcon.png").toURI().toString()));
            RoundedImageView.RoundedImage(new_Image, 32);
        }else{
            new_Image.setImage(image);
        }
    }
    void enable_BtnUpdate(){
        if(!(txt_Content.getText().isEmpty() || txt_Description.getText().isEmpty() 
                || txt_Title.getText().isEmpty() || new_Image.getImage()==image || lbl_NewsID.getText().equals("News Id"))){
            btn_Update.setDisable(false);
        }else{
            btn_Update.setDisable(true);
            
        }  
    }
    void enable_BtnAdd(){
        if(!(txt_Content.getText().isEmpty() || txt_Description.getText().isEmpty() 
                || txt_Title.getText().isEmpty() || new_Image.getImage().equals(image))){
            btn_Add.setDisable(false);
        }else{
            btn_Add.setDisable(true);
        }  
    } 
    void enable_DeleteBtn(){
        if(!(lbl_NewsID.getText().equals("News Id"))){
            btn_Delete.setDisable(false);
        }else{
            btn_Delete.setDisable(true);
        }  
    }
    
    News getNews(){
        News model = new News();
        if (lbl_NewsID.getText().equals("News Id")==false){
            model.setNewsID(Integer.parseInt(lbl_NewsID.getText()));
        }
        else{
            model.setNewsID(s);
        }
        model.setCreationDate(Date.valueOf(datePicker.getValue()));
        model.setTitle(txt_Title.getText());
        model.setDescription(txt_Description.getText()); 
        model.setContents(txt_Content.getText());
        model.setImage(path);
        model.setAccountId(USER.getAccountId());
        return model;
    }
    void setNews(News entity) {
        lbl_NewsID.setText(entity.getNewsID()+ "");
        lbl_AccountId.setText(String.valueOf(entity.getAccountId()));
        datePicker.setValue(ProcessDate.toLocalDate(ProcessDate.now()));
        txt_Title.setText("");
        txt_Description.setText("");
        txt_Content.setText("");
        new_Image.setImage(image);
    }
    void insert(){
        News model = getNews();
        try {
            newsDao.insert(model);
            fill_ListView();
            Dialog.showMessageDialog("Notice", "Inserted Successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void update(){
        News model = getNews();
        try {
            newsDao.update(model);
            fill_ListView();
            Dialog.showMessageDialog("Notice", "Updated Successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void clear(){
        this.setNews(new News());
        lbl_NewsID.setText("News Id");
        lbl_AccountId.setText("Admin");
        this.enable_BtnAdd();
        this.enable_BtnUpdate();
        this.enable_DeleteBtn();
    } 
    void delete(){
        String ID = lbl_NewsID.getText();
        try {
            newsDao.delete(ID);
            Dialog.showMessageDialog("Notice", "Deleted Successful!");
            fill_ListView();
          }catch (Exception e) {
            e.printStackTrace();
        }
    } 
    void search(){
         txt_Search.setOnKeyReleased(evt -> {
            fill_ListView();
            if (list_News.size() > 0) {
                setFormNews(list_News.get(0));
            }
        });
    }
    boolean CheckNullImage(){
        if (new_Image.getImage() != image) {
            lbl_ImageError.setVisible(false);
            return true;
        }
        else{
            lbl_ImageError.setVisible(true);
            return false;
        }
    }
    
    @FXML
    private void handleButtonAddAction(ActionEvent event) {
                    if(CheckNullImage() == true){
                        if (Catch_Errors.check_Text(txt_Title)
                        && Catch_Errors.check_TextArea(txt_Description)
                        && Catch_Errors.check_TextArea(txt_Content)) {
                            this.insert();
                            this.clear();
                }}}
       
    @FXML
    private void handleButtonClearAction(ActionEvent event) {
       this.clear(); 
    }
    @FXML
    private void handleDisableButton(KeyEvent event) {
                        this.enable_BtnAdd();
                        this.enable_BtnUpdate();
       
    }
    @FXML
    private void handleButtonUpdateAction(ActionEvent event)  {
                        if (Catch_Errors.check_Text(txt_Title)
                        && Catch_Errors.check_TextArea(txt_Description)
                        && Catch_Errors.check_TextArea(txt_Content)) {
                            this.update();
                            this.clear();
                }}
    @FXML
    private void handleButtonDeleteAction(ActionEvent event) {
        try {
            this.delete();
            this.clear();
        } catch (Exception e) {
            
        }
       
    }
    @FXML
    private void handleImgAction(MouseEvent e) {
        lbl_ImageError.setVisible(false);
        this.ChooseImage();
    }
    private void ChooseImage(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Picture");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("icons", "*.png","*.jpg"));
            File file = fileChooser.showOpenDialog(null);
            BufferedImage bImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            byte [] data = bos.toByteArray();
            path = data;
            if (file != null) {
                new_Image.setImage(new Image(file.toURI().toString()));
                System.out.println(file);
            }else{
                System.out.println("Lỗi");
            }
               this.enable_BtnAdd();
               this.enable_BtnUpdate();
    }
         catch (Exception e) {
             System.out.println("Bạn chưa chọn ảnh");
        }
    }
}
