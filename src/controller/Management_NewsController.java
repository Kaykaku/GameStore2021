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
import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import model.Account;
import model.News;
import static until.Auth.USER;
import until.Catch_Errors;
import until.Dialog;
import until.ExportExcel;
import until.ExportPDF;
import until.ExportText;
import until.ProcessImage;
import until.ProcessDate;
import until.Value;
import until.Variable;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Management_NewsController implements Initializable {

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
    private Label lbl_Views;

    @FXML
    private JFXToggleButton tog_View;

    @FXML
    private TextField txt_Search;

    @FXML
    private JFXButton btn_PDFNews;
    @FXML
    private JFXButton btn_ExcelNew;

    @FXML
    private JFXButton btn_TextNew;
    int s;
    int row = 20;
//    byte [] path = null;
    JFXDatePicker datePicker = new JFXDatePicker();
    NewsDAO newsDao = new NewsDAO();
    AccountDAO accountDao = new AccountDAO();
    List<News> list_News = new ArrayList<>();
    Image image = new Image("/icons/add-image (1).png");
    Image avatarImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayFormAnimation();
        datePicker();
        fillDataOnBackground();
        search();
        setEventExport();
        updateStatus();

    }

    void fill_ListView() {
        list_News = newsDao.selectByKeyWord(txt_Search.getText().trim());
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource("/gui/Item/Row_Product.fxml"));
            double height = (paneP.getPrefHeight() + Vbox_ListNews.getSpacing()) * list_News.size() * 2;
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

    void fillDataOnBackground() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    fill_ListView();
                });
            }
        }.start();
    }

    void updateStatus() {
        enable_BtnAdd();
        enable_BtnUpdate();
        enable_DeleteBtn();
    }

    void datePicker() {
        lbl_AccountId.setText(USER.getName());
        datePicker.setDisable(true);
        datePicker.setDefaultColor(Paint.valueOf("lightblue"));
        datePicker.setStyle("-fx-text-fill: green");
        pnl_CreationDate.getChildren().add(datePicker);
        datePicker.setValue(ProcessDate.toLocalDate(ProcessDate.now()));
        ProcessDate.converter(datePicker);
        new_Image.setImage(image);
    }

    private void setAvatarImage() {
        if (avatarImage != null) {
            new_Image.setImage(avatarImage);
        } else {
            new_Image.setImage(new ProcessImage().toImageFX("/icons/add-image (1).png"));
        }
        RoundedImageView.RoundedImage(new_Image, 32);
    }

    void setFormNews(News entity) {
        Account acc2 = new Account();
        AccountDAO acc = new AccountDAO();
        acc2 = acc.selectByID(entity.getAccountId());
        lbl_NewsID.setText(entity.getNewsID() + "");
        lbl_AccountId.setText(acc2.getName());
        datePicker.setValue(ProcessDate.toLocalDate(entity.getCreationDate()));
        txt_Title.setText(entity.getTitle());
        txt_Description.setText(entity.getDescription());
        txt_Content.setText(entity.getContents());
        if (entity.getImage() != null) {
            avatarImage = ProcessImage.toImageFX(entity.getImage());
        }
        setAvatarImage();
        if (entity.isToggle_Views()) {
            tog_View.setSelected(true);
            lbl_Views.setText(String.valueOf(entity.getViews()));
        } else {
            tog_View.setSelected(false);
            lbl_Views.setText("Views");
        }
    }

    void enable_BtnUpdate() {
        if (!(txt_Content.getText().isEmpty() || txt_Description.getText().isEmpty()
                || txt_Title.getText().isEmpty() || new_Image.getImage() == image || lbl_NewsID.getText().equals("News Id"))) {
            btn_Update.setDisable(false);
        } else {
            btn_Update.setDisable(true);
        }
    }

    void enable_BtnAdd() {
        if (!(txt_Content.getText().isEmpty() || txt_Description.getText().isEmpty()
                || txt_Title.getText().isEmpty()
                || new_Image.getImage().equals(image))) {
            if ((lbl_NewsID.getText().equals("News Id"))) {
                btn_Add.setDisable(false);
            }
        } else {
            btn_Add.setDisable(true);
        }
    }

    void enable_DeleteBtn() {
        if (!(lbl_NewsID.getText().equals("News Id"))) {
            btn_Delete.setDisable(false);
        } else {
            btn_Delete.setDisable(true);
        }
    }

    News getNews() {
        News model = new News();
        if (lbl_NewsID.getText().equals("News Id") == false) {
            model.setNewsID(Integer.parseInt(lbl_NewsID.getText()));
        } else {
            model.setNewsID(s);
        }
        model.setCreationDate(Date.valueOf(datePicker.getValue()));
        model.setTitle(txt_Title.getText());
        model.setDescription(txt_Description.getText());
        model.setContents(txt_Content.getText());
//        model.setImage(path);
        model.setToggle_Views(tog_View.isSelected());
        model.setAccountId(USER.getAccountId());
        model.setImage(ProcessImage.toBytes(new File("/icons/add-image (1).png")));
        if (avatarImage != null) {
            model.setImage(ProcessImage.toBytes(avatarImage));
        }
        return model;
    }

    void setNews(News entity) {
        lbl_NewsID.setText(entity.getNewsID() + "");
        lbl_AccountId.setText(String.valueOf(entity.getAccountId()));
        datePicker.setValue(ProcessDate.toLocalDate(ProcessDate.now()));
        txt_Title.setText("");
        txt_Description.setText("");
        txt_Content.setText("");
        new_Image.setImage(image);
    }

    void insert() {
        News model = getNews();
        try {
            newsDao.insert(model);
            fill_ListView();
            Dialog.showMessageDialog("Notice", "Inserted Successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void update() {
        News model = getNews();
        try {
            newsDao.update(model);
            fill_ListView();
            Dialog.showMessageDialog("Notice", "Updated Successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clear() {
        this.setNews(new News());
        lbl_NewsID.setText("News Id");
        lbl_AccountId.setText("Admin");
        lbl_Views.setText("Views");
        tog_View.setSelected(false);
        this.enable_BtnAdd();
        this.enable_BtnUpdate();
        this.enable_DeleteBtn();
    }

    void delete() {
        String ID = lbl_NewsID.getText();
        try {
            newsDao.delete(ID);
            Dialog.showMessageDialog("Notice", "Deleted Successful!");
            fill_ListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void search() {
        txt_Search.setOnKeyReleased(evt -> {
            fill_ListView();
            if (list_News.size() > 0) {
                setFormNews(list_News.get(0));
            }
        });
    }

    private void setEventExport() {
        String[] header = new String[]{"ID", "CreationDate", "Title", "Description", "Contents", "Image", "AccountId", "Views"};
        List<News> list = newsDao.selectAll();
        List<Object[]> listObjs = new ArrayList<>();
        list.forEach((News) -> {
            listObjs.add(News.toObjects());
        });
        String fileName = "News";
        String title = "News List";
        btn_PDFNews.setOnAction(evt -> {
            try {
                ExportPDF.ExportPDF(Variable.MAIN_STAGE, header, listObjs, fileName + ".pdf", title);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btn_ExcelNew.setOnAction(evt -> {

            try {
                ExportExcel.exportFile(Variable.MAIN_STAGE, header, listObjs, fileName + ".xlsx", title);
            } catch (IOException ex) {
                Logger.getLogger(Management_NewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_TextNew.setOnAction(evt -> {
//            try {          
//                List<News> list = newsDao.selectAll();
//                List<Object[]> listObjs = new ArrayList<>();
//                list.forEach((News) -> {
//                    listObjs.add(News.toObjects());
//                });
//                String fileName = "Newstxt";
//                ExportText.exportText(null,  listObjs, fileName);
//            } catch (IOException ex) {
//               ex.printStackTrace();
//            }
            ExportText.ExportFileNews();
        });
    }

    @FXML
    private void handleButtonAddAction(ActionEvent event) {
        if (Catch_Errors.check_Text(txt_Title)
                && Catch_Errors.check_TextArea(txt_Description)
                && Catch_Errors.check_TextArea(txt_Content)) {
            this.insert();
            this.clear();
        }
    }

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
    private void handleButtonUpdateAction(ActionEvent event) {
        if (Catch_Errors.check_Text(txt_Title)
                && Catch_Errors.check_TextArea(txt_Description)
                && Catch_Errors.check_TextArea(txt_Content)) {
            this.update();
            this.clear();
        }
    }

    @FXML
    private void handleButtonDeleteAction(ActionEvent event) {
        try {
            this.delete();
            this.clear();
        } catch (Exception e) {

        }

    }

    @FXML
    private void handleImgAction(MouseEvent event) {
//        this.ChooseImage();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG JPG", "*.png", "*.jpg"));
        fc.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fc.setTitle("Select folder");
        File f = fc.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
        if (f != null) {
            avatarImage = new Image(f.toURI().toString());
            setAvatarImage();
            updateStatus();
        }
    }
//    private void ChooseImage(){
//        try {
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Choose Picture");
//            fileChooser.getExtensionFilters().clear();
//            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("icons", "*.png","*.jpg"));
//            File file = fileChooser.showOpenDialog(null);
//            BufferedImage bImage = ImageIO.read(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ImageIO.write(bImage, "png", bos );
//            byte [] data = bos.toByteArray();
//            path = data;
//            if (file != null) {
//                new_Image.setImage(new Image(file.toURI().toString()));
//                System.out.println(file);
//            }else{
//                System.out.println("Lỗi");
//            }
//               this.enable_BtnAdd();
//               this.enable_BtnUpdate();
//    }
//         catch (Exception e) {
//             System.out.println("Bạn chưa chọn ảnh");
//        }
//    }

    void displayFormAnimation() {
        new FadeInDown(pnl_Basic_Info).play();
        new FadeInDownBig(pnl_Image).play();
        new FadeInLeftBig(pnl_Title).play();
        new FadeInLeft(pnl_Add_Info).play();
        new FadeInRightBig(pnl_List_News).play();
        new FadeInUpBig(pnl_Content).play();
        new ZoomInUp(hbox_Controller).play();
    }
}
