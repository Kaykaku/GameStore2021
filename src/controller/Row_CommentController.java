/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.RoundedImageView;
import DAO.AccountDAO;
import DAO.ApplicationViewDAO;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Account;
import model.ApplicationView;
import model.Comment;
import until.ProcessDate;
import until.ProcessImage;
import until.Value;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Row_CommentController implements Initializable {
     @FXML
    private ImageView img_1Star;

    @FXML
    private Pane pnl_Row;

    @FXML
    private ImageView img_Avatar;

    @FXML
    private ImageView img_3Star;

    @FXML
    private Label lbl_Description;

    @FXML
    private Label lbl_CommentDate;

    @FXML
    private Label lbl_UserName;

    @FXML
    private ImageView img_2Star;

    @FXML
    private ImageView img_4Star;

    @FXML
    private ImageView img_5Star;

    @FXML
    private Label lbl_Title;

    @FXML
    private JFXButton btn_Delete;
    /**
     * Initializes the controller class.
     */
    Comment comment;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setInfo(Comment entity){
        this.comment=entity;
        lbl_Title.setText(entity.getTitle());
        lbl_Description.setText(entity.getDescription());
        lbl_CommentDate.setText(ProcessDate.toString(entity.getCreationDate()));
        
        Account account = new AccountDAO().selectByAppViewID(entity.getApplicatonViewId());
        lbl_UserName.setText(account.getUsername());
        if (account.getImage()!=null) {
            Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(account.getImage()), null);
            img_Avatar.setImage(image);
            RoundedImageView.RoundedImage(img_Avatar, 40);
        }
        
        ApplicationView appView = new ApplicationViewDAO().selectByID(entity.getApplicatonViewId());
        loadStar(appView.getRate());
    }
    void loadStar(int rate) {
        Image starFill = new Image(new File(Value.STAR_FILL).toURI().toString());
        Image starNot = new Image(new File(Value.STAR_REGULAR).toURI().toString());
        img_1Star.setImage(starNot);
        img_2Star.setImage(starNot);
        img_3Star.setImage(starNot);
        img_4Star.setImage(starNot);
        img_5Star.setImage(starNot);
        switch (rate) {
            case 5:
                img_5Star.setImage(starFill);
            case 4:
                img_4Star.setImage(starFill);
            case 3:
                img_3Star.setImage(starFill);
            case 2:
                img_2Star.setImage(starFill);
            case 1:
                img_1Star.setImage(starFill);
        }
    }
    JFXButton getButtonDelete(){
        return btn_Delete;
    }
    Comment getComment(){
        return comment;
    }
}
