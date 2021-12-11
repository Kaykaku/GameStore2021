/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.PulseShort;
import Animation.RoundedImageView;
import DAO.AppTypeDAO;
import DAO.ApplicationDAO;
import DAO.ApplicationViewDAO;
import DAO.CategoryDAO;
import DAO.CommentDAO;
import DAO.StatisticsDAO;
import DAO.WishlistDAO;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.AppType;
import model.Application;
import model.ApplicationView;
import model.Category;
import model.Comment;
import model.Wishlist;
import until.Auth;
import until.Dialog;
import until.ProcessDate;
import until.ProcessImage;
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
public class DisplayProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pnl_List_Applications;

    @FXML
    private Pane pnl_List;

    @FXML
    private JFXTextArea text_Description;

    @FXML
    private Label lbl_RatingDate;

    @FXML
    private Label lbl_Languages;

    @FXML
    private Pane pnl_FiveStar;

    @FXML
    private Label lbl_Size;

    @FXML
    private Label lbl_Developer;

    @FXML
    private Label lbl_CateCount;

    @FXML
    private Pane pnl_Container;

    @FXML
    private Text lbl_AppName;

    @FXML
    private JFXButton btn_Buy;

    @FXML
    private Pane pnl_AdditionInformation;

    @FXML
    private Pane pnl_App_Basic_Info;

    @FXML
    private Pane pnl_DisplayApp;

    @FXML
    private ImageView img_3Star;
    
    @FXML
    private Pane pnl_Bg;
    
    @FXML
    private ImageView img_Bg;

    @FXML
    private Label lbl_AppDeveloper;

    @FXML
    private ImageView img_AppIcon;

    @FXML
    private Pane pnl_Description;

    @FXML
    private Label lbl_Sale;

    @FXML
    private ImageView img_4Star;

    @FXML
    private JFXButton btn_Back;

    @FXML
    private JFXButton btn_AddWishList;

    @FXML
    private Pane pnl_Ratings;

    @FXML
    private Pane pnl_OneStar;

    @FXML
    private Pane pnl_TwoStar;

    @FXML
    private Label lbl_SeeMore;

    @FXML
    private Label lbl_ReleaseDate;

    @FXML
    private Pane pnl_Screenshot;

    @FXML
    private ImageView img_5Star;

    @FXML
    private Label lbl_Ratings;

    @FXML
    private ImageView img_1Star;

    @FXML
    private Label lbl_Publisher;

    @FXML
    private Pane pnl_FourStar;

    @FXML
    private Label lbl_Average_Rating_Sub;

    @FXML
    private Pane pnl_ThreeStar;

    @FXML
    private ImageView img_AppImage;

    @FXML
    private Label lbl_Average_Rating_Main;

    @FXML
    private Label lbl_Main_Ratings;

    @FXML
    private Label lbl_Views;

    @FXML
    private Label lbl_CommentCount;

    @FXML
    private ImageView img_2Star;

    @FXML
    private ScrollPane pnl_MainScroll;

    @FXML
    private HBox hbox_Star;

    @FXML
    private HBox hbox_ScrollRandom;

    @FXML
    private ScrollPane pnl_ScrollRandom;

    @FXML
    private VBox vbox_ListComment;

    @FXML
    private ScrollPane pnl_ScrollComment;

    @FXML
    private Pane pnl_ListComment;

    @FXML
    private JFXTextField txt_TitleReview;

    @FXML
    private JFXTextArea txt_DescriptionReview;

    @FXML
    private JFXButton btn_Comment;

    StatisticsDAO staDao = new StatisticsDAO();
    ApplicationViewDAO viewDAO = new ApplicationViewDAO();

    Application app;
    ApplicationView appView;

    int ratings = 0, views = 0;
    double averageRating = 0;
    boolean isAdded = false;
    List<Object[]> listStarRatings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoundedImageView.RoundedImage(img_AppIcon, 32);
        pnl_MainScroll.setPrefHeight(810);

    }

    void setInformation(Application entity) {
        this.app = entity;
        displayFormAnimation();
        LoadAppView();
        calculateAverageRating();
        loadBasicInfo();
        loadStatus();
        setEvent();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    loadAppCategories();
                    fillListComments();
                    fillListRandomApps();
                });
            }
        }.start();

    }

    void loadBasicInfo() {
        pnl_Bg.setOpacity(0);
        lbl_AppName.setText(app.getName());
        lbl_AppDeveloper.setText(app.getDeveloper());
        double number = (double) Math.round(app.getPrice()*100)/100;
        btn_Buy.setText("Get " + number + "$");

        if (app.getAppIcon() != null) {
            Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(app.getAppIcon()), null);
            img_AppIcon.setImage(image);
        }

        if (app.getAppImage() != null) {
            Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(app.getAppImage()), null);
            img_AppImage.setImage(image);
            img_Bg.setImage(image);
        }
        RoundedImageView.RoundedImage(img_AppIcon, 32);
        RoundedImageView.RoundedImage(img_AppImage, 32);
        text_Description.setText(app.getDescription());

        lbl_Developer.setText(app.getDeveloper());
        lbl_Languages.setText(app.getLanguages());
        lbl_Publisher.setText(app.getPublisher());
        lbl_ReleaseDate.setText(ProcessDate.toString(app.getReleaseDay()));
        number = (double) Math.round(app.getSale()*100)/100;
        lbl_Sale.setText((int) number + "%");
        number = (double) Math.round(app.getSize()*100)/100;
        lbl_Size.setText(number + "MB");
        
    }

    void setEvent() {
        btn_Back.setOnMouseClicked((evt) -> {
            img_Bg.setImage(null);
            disappearFormAnimation();
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException ex) {
                    }
                    Platform.runLater(() -> {
                        PNL_VIEW.getChildren().remove(PNL_VIEW.getChildren().size() - 1);
                    });
                }
            }.start();

        });
        btn_Comment.setOnMouseClicked((evt) -> {
            actionComment();
        });
        btn_AddWishList.setOnMouseClicked((evt) -> {
            if (!isAdded) {
                new WishlistDAO().insert(new Wishlist(Auth.USER.getAccountId(), app.getApplicationID()));

            } else {
                new WishlistDAO().delete(Auth.USER.getAccountId(), app.getApplicationID());
            }
            loadStatus();
        });
        btn_Buy.setOnMouseClicked((evt) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PAY));
            Node node;
            try {
                node = (Node) loader.load();
                PayController controller = loader.getController();
                controller.setInformation(app,this);
                PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
        });
        lbl_SeeMore.setOnMouseClicked((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_PRODUCT_LIST));
                Node node = (Node) loader.load();
                ProductListController controller=loader.getController();
                controller.setFilter(1, 1);
                Variable.PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    void calculateAverageRating() {
        averageRating = 0;
        ratings = 0;
        views = 0;
        listStarRatings = staDao.getRatingApp(app.getApplicationID());
        for (Object[] listObject : listStarRatings) {
            if ((int) listObject[0] != 0) {
                averageRating += (int) listObject[0] * (int) listObject[1];
                ratings += (int) listObject[1];
            }
            views += (int) listObject[1];
        }

        pnl_OneStar.setPrefWidth(0);
        pnl_TwoStar.setPrefWidth(0);
        pnl_ThreeStar.setPrefWidth(0);
        pnl_FourStar.setPrefWidth(0);
        pnl_FiveStar.setPrefWidth(0);
        for (Object[] list : listStarRatings) {
            double a = (double) ((int) list[1]) / ratings;
            switch ((int) list[0]) {
                case 1:
                    pnl_OneStar.setPrefWidth(200 * a);
                    break;
                case 2:
                    pnl_TwoStar.setPrefWidth(200 * a);
                    break;
                case 3:
                    pnl_ThreeStar.setPrefWidth(200 * a);
                    break;
                case 4:
                    pnl_FourStar.setPrefWidth(200 * a);
                    break;
                case 5:
                    pnl_FiveStar.setPrefWidth(200 * a);
                    break;
            }
        }
        averageRating = (double) Math.round(averageRating / ratings * 10) / 10;

        lbl_Average_Rating_Main.setText(averageRating + "");
        lbl_Main_Ratings.setText(ratings + "");
        lbl_Views.setText(views + "");
        lbl_Average_Rating_Sub.setText(averageRating + "");
        lbl_Ratings.setText(ratings + " Ratings");
    }

    void loadAppCategories() {
        List<AppType> list = new AppTypeDAO().selectByApplicationId(app.getApplicationID());
        lbl_CateCount.setText(list.size() + "");

        pnl_Container.getChildren().clear();
        double width = 0, x = 0;
        for (AppType appType : list) {
            Category ca = new CategoryDAO().selectByID(appType.getCategoryId());
            Label label = new Label(ca.getName());
            label.setStyle("-fx-background-radius : 10px; -fx-text-fill : white; -fx-background-color : " + ca.getColor());
            pnl_Container.getChildren().add(label);

            label.setLayoutX(x + width);
            label.setLayoutY(10);
            label.setPadding(new Insets(2, 10, 2, 10));
            FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
            label.setFont(Font.font("Arial", FontWeight.THIN, FontPosture.REGULAR, 16));
            width = fontLoader.computeStringWidth(label.getText(), label.getFont()) + 30;
            x = label.getLayoutX();
        }
    }

    void LoadAppView() {
        appView = viewDAO.selectByAccount(Auth.USER.getAccountId(), app.getApplicationID());
        if (appView == null) {
            appView = new ApplicationView();
            appView.setAccountId(Auth.USER.getAccountId());
            appView.setApplicatonId(app.getApplicationID());
            appView.setRate(0);
            appView.setRatingDate(new Date());
            appView.setViews(0);
            viewDAO.insert(appView);
            appView = viewDAO.selectByAccount(Auth.USER.getAccountId(), app.getApplicationID());
        }

        loadStar(appView.getRate());
        lbl_RatingDate.setText(appView.getRate() == 0 ? "you have not rated this app yet" : ProcessDate.toString(appView.getRatingDate()));

        for (int i = 0; i < 5; i++) {
            final int h = i;
            final int rate = appView.getRate();
            ApplicationView appView2 = appView;
            hbox_Star.getChildren().get(i).setOnMouseEntered((evt) -> {
                loadStar(h + 1);
            });
            hbox_Star.getChildren().get(i).setOnMouseExited((evt) -> {
                loadStar(rate);
            });
            hbox_Star.getChildren().get(i).setOnMouseClicked((evt) -> {
                appView2.setRate(h + 1);
                appView2.setRatingDate(new Date());
                viewDAO.update(appView2);
                LoadAppView();
                calculateAverageRating();
            });
        }
        
    }

    void loadStar(int rate) {
        try {
            Image starFill = new Image(getClass().getResource(Value.STAR_FILL).toURI().toString());
            Image starNot = new Image(getClass().getResource(Value.STAR_REGULAR).toURI().toString());
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
        } catch (URISyntaxException ex) {
            //Logger.getLogger(DisplayProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void loadStatus() {
        if (Auth.isManager()) {
            btn_Buy.setDisable(true);
            btn_AddWishList.setDisable(true);
        } else {
            Wishlist w = new WishlistDAO().selectByAccountApplicationID(Auth.USER.getAccountId(), app.getApplicationID());
            if (w == null) {
                isAdded = false;
                btn_AddWishList.setText("Add to wishlist");
            } else {
                isAdded = true;
                btn_AddWishList.setText("Remove form wishlist");
            }
            if (new ApplicationDAO().isPurchaseApplication(Auth.USER.getAccountId(), app.getApplicationID())) {
                btn_Buy.setText("Owned");
                btn_AddWishList.setText("Owned");
                btn_AddWishList.setDisable(true);
                btn_Buy.setDisable(true);
            }

        }

        if (!app.isEnableBuy()) {
            btn_Buy.setText("Out of stock");
            btn_AddWishList.setText("Out of stock");
            btn_AddWishList.setDisable(true);
            btn_Buy.setDisable(true);
        }
    }

    void loadStatusAll(Application app) {

        if (new ApplicationDAO().isPurchaseApplication(Auth.USER.getAccountId(), app.getApplicationID())) {
            btn_Buy.setText("Owned");
            btn_AddWishList.setText("Owned");
            btn_AddWishList.setDisable(true);
            btn_Buy.setDisable(true);
        }

        if (!app.isEnableBuy()) {
            btn_Buy.setText("Out of stock");
            btn_AddWishList.setText("Out of stock");
            btn_AddWishList.setDisable(true);
            btn_Buy.setDisable(true);
        }
    }
    
    void fillListComments() {
        CommentDAO dao = new CommentDAO();
        List<Comment> listComment = dao.selectByAppId(app.getApplicationID());
        lbl_CommentCount.setText(listComment.size() + " Comments");
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.ROW_COMMENT));
            double height = (paneP.getPrefHeight() + vbox_ListComment.getSpacing()) * listComment.size();
            vbox_ListComment.setPrefSize(paneP.getPrefWidth(), height);
            pnl_ListComment.setPrefHeight(height > pnl_ScrollComment.getPrefHeight() ? height : pnl_ScrollComment.getPrefHeight());

            vbox_ListComment.getChildren().clear();
            Pane[] nodes = new Pane[listComment.size()];
            Row_CommentController[] controllers = new Row_CommentController[listComment.size()];

            for (int i = 0; i < listComment.size(); i++) {
                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.ROW_COMMENT));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                vbox_ListComment.getChildren().add(nodes[h]);
                controllers[h].setInfo(listComment.get(h));
                if (listComment.get(h).getApplicatonViewId() == appView.getApplicatonViewId() || Auth.isManager()) {
                    Button button_delete = controllers[h].getButtonDelete();
                    PulseShort ani = new PulseShort(nodes[h]);
                    ani.setCycleCount(Integer.MAX_VALUE);
                    nodes[h].setOnMouseEntered(evt -> {
                        //new GrowUp(pnl_Row, 1.02).play();
                        ani.play();
                        button_delete.setOpacity(1);
                    });
                    nodes[h].setOnMouseExited(evt -> {
                        nodes[h].setScaleX(1);
                        nodes[h].setScaleY(1);
                        button_delete.setOpacity(0);
                        ani.stop();
                    });
                    button_delete.setOnMouseClicked((event) -> {
                        dao.delete(controllers[h].getComment().getCommentsID());
                        fillListComments();
                    });
                }
            }
        } catch (IOException e) {
        }
    }

    void actionComment() {
        String err = "";
        err += Validation.validationJFXTextFieldLength(txt_TitleReview, "TITLE", 5, 100);
        err += Validation.validationJFXTextAreaLength(txt_DescriptionReview, "DESCRIPTION", 5, 300);
        if (err.isEmpty()) {
            Comment comment = new Comment();
            comment.setApplicatonViewId(appView.getApplicatonViewId());
            comment.setTitle(txt_TitleReview.getText().trim());
            comment.setDescription(txt_DescriptionReview.getText().trim());
            comment.setCreationDate(new Date());

            txt_DescriptionReview.setText("");
            txt_TitleReview.setText("");
            if (!Auth.USER.isComment()) {
                Dialog.showMessageDialog("Comment failed!!!", "Sorry you can't comment because your account has been blocked by ADMIN");
                return;
            }
            new CommentDAO().insert(comment);
            fillListComments();
            return;
        }
        Dialog.showMessageDialog("Wrong data", err);
    }

    void fillListRandomApps() {
        List<Application> listApps = new ApplicationDAO().selectAll();
        HashSet<Application> set = new HashSet<>();
        while (set.size() != 4) {
            int i = new Random().nextInt(listApps.size());
            set.add(listApps.get(i));
        }

        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.PRODUCT_BOX_SHORT));
            double width = (paneP.getPrefWidth() + hbox_ScrollRandom.getSpacing()) * set.size();
            hbox_ScrollRandom.setPrefSize(width, paneP.getPrefHeight() + 5);

            hbox_ScrollRandom.getChildren().clear();
            Pane[] nodes = new Pane[set.size()];
            Product_Box_ShortController[] controllers = new Product_Box_ShortController[set.size()];
            for (int i = 0; i < set.size(); i++) {

                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.PRODUCT_BOX_SHORT));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                hbox_ScrollRandom.getChildren().add(nodes[h]);
                controllers[h].setInfo((Application) set.toArray()[i]);

            }
        } catch (IOException e) {
        }
    }

    void displayFormAnimation() {
        new FadeIn(pnl_Bg).play();
        new ZoomIn(pnl_App_Basic_Info).play();
        new ZoomInRight(pnl_MainScroll).play();
//        new ZoomIn(pnl_Description).play();
    }

    void disappearFormAnimation() {
        pnl_Bg.setOpacity(0);
        AnimationFX ani = new ZoomOut(pnl_App_Basic_Info);
        ani.setSpeed(2);
        ani.play();
        ani = new ZoomOutRight(pnl_MainScroll);
        ani.setSpeed(2);
        ani.play();
//         ani = new ZoomOut(pnl_Description);
//        ani.setSpeed(2);
//        ani.play();
        pnl_DisplayApp.setStyle("-fx-background-color :transparent;");
        pnl_List.setStyle("-fx-background-color :transparent;");
        pnl_MainScroll.setStyle("-fx-background-color :transparent;");
    }
}
