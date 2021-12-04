/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import java.time.Duration;
import java.time.Instant;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    
    public static Instant END;
    public static Instant START;
    public static Duration TIMEELAPSED;
}
