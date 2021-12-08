/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import controller.MainController;
import java.time.Duration;
import java.time.Instant;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Account;

/**
 *
 * @author Admin
 */
public class Variable {

    public static double WIDTH_VIEW = 0;
    public static double HEIGHT_VIEW = 0;
    public static double WIDTH_PRODUCT_LIST = 0;
    public static double HEIGHT_PRODUCT_LIST = 0;
    public static Pane PNL_VIEW;
    public static int HOME_PAGE=0;
    public static boolean IS_ACCOUNT_INFORMATION_OPEN=false;
    public static boolean IS_WISHLIST_OPEN=false;
    public static Stage MAIN_STAGE=null;
    public static Image AVATAR = null;
    public static MainController MAIN_CONTROLLER=null;
    public static ProgressBar DOWNLOAD_PROGRESSBAR=null;
     public static ProgressBar SENDING_PROGRESSBAR=null;
    public static Label DOWNLOAD_LABEL=null;
    public static Label SENDING_LABEL=null;
    public static boolean IS_DOWNLOADING=false;
    public static boolean IS_SENDING=false;
    public static Thread THREAD_SENDING=null;
    public static Account DEFAULT_MAIL =null;
    public static String DEFAULT_MAILTEMPLATE =null;
    public static String DEFAULT_MAILTITLETEMPLATE =null;
    
    public static Instant END;
    public static Instant START;
    public static Duration TIMEELAPSED;
}
