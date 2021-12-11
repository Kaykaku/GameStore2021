/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Animation.BoxMove;
import DAO.ApplicationDAO;
import DAO.NewsDAO;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInUp;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Application;
import model.News;
import until.ProcessImage;
import until.Value;
import until.Variable;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private HBox hbox_RecentNews;

    @FXML
    private HBox hbox_Header;

    @FXML
    private JFXButton btn_SeeDetails;

    @FXML
    private Label lbl_Title_Box2;

    @FXML
    private Label lbl_Title_Box1;

    @FXML
    private HBox hbox_OnlineGames;

    @FXML
    private ScrollPane pnl_MainScroll;

    @FXML
    private Label lbl_Title_Header;

    @FXML
    private ImageView img_Header;

    @FXML
    private Label btn_SeeAll_New;

    @FXML
    private Label lbl_Title;

    @FXML
    private Label lbl_SeeAll_Random1;

    @FXML
    private Label lbl_SeeAll_Random2;

    @FXML
    private HBox hbox_MusicApps;

    @FXML
    private HBox hbox_BestSellerGame;

    @FXML
    private Label lbl_Title_Random1;

    @FXML
    private Label lbl_Title_Random2;

    @FXML
    private JFXButton btn_SeeAll_Random1;

    @FXML
    private JFXButton btn_SeeAll_Random2;

    @FXML
    private HBox hbox_RandomApps;

    @FXML
    private Text lbl_Description_Random2;

    @FXML
    private Text lbl_Description_Random1;
    
    @FXML
    private Pane pnl_Container1;
    
    @FXML
    private Pane pnl_Container3;
    
    @FXML
    private Pane pnl_Container2;


    List<Application> listApps;
    ApplicationDAO appDAO = new ApplicationDAO();
    Product_Box_HeaderController[] controllers;
    int step = 0,see2,box1,box2;
    Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Variable.START =Instant.now();
        pnl_MainScroll.setPrefHeight(830);
        fillDataOnBackground();
        setEvent();
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
                    setHome();
                });
            }
        }.start();
    }

    void setHome() {
        switch (Variable.HOME_PAGE) {
            case 0:
                listApps = appDAO.selectActiveAll(1);
                fillListApps(getRandomApps(listApps, 6), hbox_RandomApps, -1);
                
                box1=16;
                listApps = appDAO.selectByCategory(16, "");
                fillListApps(getRandomApps(listApps, 4), hbox_MusicApps, 16);
                listApps = appDAO.selectByType(0, "");
                fillListApps(getRandomApps(listApps, 6), hbox_BestSellerGame, -1);
                box2=2;
                listApps = appDAO.selectByCategory(2, "");
                fillListApps(getRandomApps(listApps, 4), hbox_OnlineGames, 2);
                fillListNews();
                listApps = appDAO.selectActiveAll(1);
                fillListHeader(getRandomApps(listApps, 8));
                startHeader();
                break;
            case 1:
                lbl_Title_Random1.setText("Top random games");
                lbl_Title_Random2.setText("Top best selling games");
                listApps = appDAO.selectActiveAll(1,0);
                fillListApps(getRandomApps(listApps, 6), hbox_RandomApps, -1);
                
                box1=8;
                lbl_Title_Box1.setText("Racing & Flying");
                lbl_Description_Random1.setText("Become a bird flying in the sky or become a professional car driver ?");
                pnl_Container1.getStyleClass().add("container1");
                listApps = appDAO.selectByCategory(8, "");
                fillListApps(getRandomApps(listApps, 4), hbox_MusicApps, 8);
                
                listApps = appDAO.selectByType(0, "");
                fillListApps(getRandomApps(listApps, 6), hbox_BestSellerGame, -1);       
                
                box2=6;
                lbl_Title_Box2.setText("Adventure");
                lbl_Description_Random2.setText("Make your adventure behind your screen");
                pnl_Container2.getStyleClass().add("container1");   
                
                listApps = appDAO.selectByCategory(6, "");
                fillListApps(getRandomApps(listApps, 4), hbox_OnlineGames, 6);
                
                fillListNews();
                listApps = appDAO.selectActiveAll(1,0);
                fillListHeader(getRandomApps(listApps, 8));
                startHeader();
                break;
            case 2:
                lbl_Title_Random1.setText("Top random applications");
                lbl_Title_Random2.setText("Top best selling applications");
                listApps = appDAO.selectActiveAll(1,1);
                fillListApps(getRandomApps(listApps, 6), hbox_RandomApps, -1);
                
                box1=25;
                lbl_Title_Box1.setText("LifeStyle");
                lbl_Description_Random1.setText("Make your style , enjoy your life!");
                pnl_Container1.getStyleClass().add("container");
                listApps = appDAO.selectByCategory(25, "");
                fillListApps(getRandomApps(listApps, 4), hbox_MusicApps, 25);
                
                listApps = appDAO.selectByType(0, "");
                fillListApps(getRandomApps(listApps, 6), hbox_BestSellerGame, -1);
                
                box2=19;
                lbl_Title_Box2.setText("Productivity");
                lbl_Description_Random2.setText("Make all things on the PC , your product your creative!");
                pnl_Container2.getStyleClass().add("container");
                listApps = appDAO.selectByCategory(19, "");
                fillListApps(getRandomApps(listApps, 4), hbox_OnlineGames, 19);
                
                fillListNews();
                listApps = appDAO.selectActiveAll(1,1);
                fillListHeader(getRandomApps(listApps, 8));
                startHeader();
                break;
        }
    }

    void setEvent() {
        lbl_SeeAll_Random1.setOnMouseClicked((event) -> {
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
        lbl_SeeAll_Random2.setOnMouseClicked((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_PRODUCT_LIST));
                Node node = (Node) loader.load();
                ProductListController controller=loader.getController();
                controller.setFilter(1, 5);
                Variable.PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_SeeAll_Random1.setOnMouseClicked((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_PRODUCT_LIST));
                Node node = (Node) loader.load();
                ProductListController controller=loader.getController();
                controller.setFilter(box1, 0);
                Variable.PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_SeeAll_Random2.setOnMouseClicked((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_PRODUCT_LIST));
                Node node = (Node) loader.load();
                ProductListController controller=loader.getController();
                controller.setFilter(box2, 0);
                Variable.PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_SeeDetails.setOnMouseClicked((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_DISPLAY_PRODUCT));
                Node node = (Node) loader.load();
                DisplayProductController controller = loader.getController();
                controller.setInformation(listApps.get(step - 1));
                Variable.PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_SeeAll_New.setOnMouseClicked((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Value.FORM_NEWS_LIST));
                Node node = (Node) loader.load();
                Variable.PNL_VIEW.getChildren().add(node);
            } catch (IOException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    List<Application> getRandomApps(List<Application> listApps, int x) {
        if(listApps.size()<=3){
            return  listApps;
        }
        HashSet<Application> set = new HashSet<>();
        while (set.size() != x) {
            int i = new Random().nextInt(listApps.size());
            set.add(listApps.get(i));
        }
        listApps.clear();
        for (Application application : set) {
            listApps.add(application);
        }
        return listApps;
    }

    void fillListApps(List<Application> listApps, HBox hBox, int cate) {
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.PRODUCT_BOX_SHORT));
            double width = (paneP.getPrefWidth() + hBox.getSpacing()) * listApps.size();
            hBox.setPrefSize(width, paneP.getPrefHeight() + 5);

            hBox.getChildren().clear();
            Pane[] nodes = new Pane[listApps.size()];
            Product_Box_ShortController[] controllers = new Product_Box_ShortController[listApps.size()];
            for (int i = 0; i < listApps.size(); i++) {

                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.PRODUCT_BOX_SHORT));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                hBox.getChildren().add(nodes[h]);
                controllers[h].setInfo(listApps.get(i));
                controllers[h].setCategory(cate);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void fillListNews() {
        List<News> listNews = new NewsDAO().selectByDate();
        int x = listNews.size() > 3 ? 3 : listNews.size();
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.NEWS_BOX));
            double width = (paneP.getPrefWidth() + hbox_RecentNews.getSpacing()) * listNews.size();
            hbox_RecentNews.setPrefSize(width, paneP.getPrefHeight() + 5);

            hbox_RecentNews.getChildren().clear();
            Pane[] nodes = new Pane[listNews.size()];
            News_BoxController[] controllers = new News_BoxController[listNews.size()];
            for (int i = 0; i < x; i++) {

                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.NEWS_BOX));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                hbox_RecentNews.getChildren().add(nodes[h]);
                controllers[h].setInfo(listNews.get(i));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillListHeader(List<Application> listApps) {
        try {
            Pane paneP = (Pane) FXMLLoader.load(getClass().getResource(Value.PRODUCT_BOX_HEADER));
            double width = (paneP.getPrefWidth() + hbox_Header.getSpacing()) * 8;
            hbox_Header.setPrefSize(width, paneP.getPrefHeight());

            hbox_Header.getChildren().clear();
            Pane[] nodes = new Pane[8];
            controllers = new Product_Box_HeaderController[8];
            for (int i = 0; i < 8; i++) {

                final int h = i;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Value.PRODUCT_BOX_HEADER));
                nodes[h] = (Pane) loader.load();
                controllers[h] = loader.getController();

                hbox_Header.getChildren().add(nodes[h]);
                controllers[h].setInfo(listApps.get(i));
                controllers[h].getBox().setOnMouseEntered((event) -> {
                    if (step == 0) {
                        return;
                    }
                    timeline.pause();
                    controllers[step - 1].pause();
                });
                controllers[h].getBox().setOnMouseExited((event) -> {
                    if (step == 0) {
                        return;
                    }
                    timeline.play();
                    controllers[step - 1].play();
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void startHeader() {
        int duration = 4000;
        timeline = new Timeline(
                new KeyFrame(Duration.millis(500), this::doStep),
                new KeyFrame(Duration.millis(duration * 1 + 500), this::doStep),
                new KeyFrame(Duration.millis(duration * 2 + 500), this::doStep),
                new KeyFrame(Duration.millis(duration * 3 + 500), this::doStep),
                new KeyFrame(Duration.millis(duration * 4 + 500), this::doStep),
                new KeyFrame(Duration.millis(duration * 5 + 500), this::doStep),
                new KeyFrame(Duration.millis(duration * 6 + 500), this::doStep),
                new KeyFrame(Duration.millis(duration * 7 + 500), this::doStep),
                new KeyFrame(Duration.millis(duration * 8 + 500), this::doStep)
        );
        timeline.setCycleCount(10);
        timeline.play();
    }

    void doStep(ActionEvent event) {
        step++;
        for (Product_Box_HeaderController controller : controllers) {
            controller.setHover(false);
        }
        if (step != 9) {
            controllers[step - 1].setHover(true);
            Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(listApps.get(step - 1).getAppImage()), null);
            img_Header.setImage(image);
            lbl_Title_Header.setText(listApps.get(step - 1).getName());
            new FadeInLeft(lbl_Title_Header).play();
            new FadeInUp(btn_SeeDetails).play();
            if (step > 4) {
                new BoxMove(hbox_Header).play();
            }
        } else {
            step = 0;
            new BoxMove(hbox_Header).play();
        }
    }
}
