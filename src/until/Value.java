/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import java.net.URI;
import java.net.URL;
import javafx.scene.layout.Pane;

/**
 *
 * @author Admin
 */
public class Value {   
    public static final String MAIN  ="/gui/Main/GameStore.fxml";
    public static final String EMPTY  ="/gui/Form/empty.fxml";
    public static final String FORM_PRODUCT_LIST  ="/gui/Form/ProductList.fxml";
    public static final String FORM_NEWS_LIST  ="/gui/Form/NewsList.fxml";
    public static final String FORM_ACCOUNT  ="/gui/Form/Management_Account.fxml";
    public static final String FORM_CATEGORY  ="/gui/Form/Management_Category.fxml";
    public static final String FORM_PRODUCT ="/gui/Form/Management_Product.fxml";
    public static final String FORM_ORDER  ="/gui/Form/Management_Order.fxml";
    public static final String FORM_NEWS  ="/gui/Form/Management_News.fxml";
    public static final String FORM_STATISTICS  ="/gui/Form/Statistics.fxml";
    public static final String FORM_WISHLISH  ="/gui/Form/Wishlist.fxml";
    public static final String FORM_DISPLAY_PRODUCT  ="/gui/Form/DisplayProduct.fxml";
    public static final String FORM_DISPLAY_NEWS ="/gui/Form/DisplayNews.fxml";
    public static final String FORM_HOME  ="/gui/Form/Home.fxml";
    public static final String FORM_HOME_APPS  ="HOME_APPS";
    public static final String FORM_HOME_GAMES  ="HOME_GAMES";
    public static final String FORM_LIBRARY  ="/gui/Form/Library.fxml";
    public static final String FORM_USER_INFORMATION  ="/gui/Form/User_Information.fxml";
    public static final String FORM_LOGIN  ="/gui/Form/Login.fxml";
    public static final String FORM_MAIL  ="/gui/Form/Mail_Sending.fxml";
    public static final String PAY  ="/gui/Form/Pay.fxml";
    
    public static final String DIALOG_SCANQRCODE  ="/gui/Form/Dialog_ScanQRcode.fxml";
    public static final String DIALOG_CREATEQRCODE  ="/gui/Form/Dialog_CreateQRcode.fxml";
    public static final String DIALOG_TAKEPICTURE  ="/gui/Form/Dialog_TakePicture.fxml";
    public static final String DIALOG_MESSAGE  ="/gui/Form/Dialog_Message.fxml";
    public static final String DIALOG_WAITING  ="/gui/Form/Dialog_Waiting.fxml";
    
    public static final String ROW_ORDER  ="/gui/Item/Row_Order.fxml";
    public static final String ROW_PRODUCT  ="/gui/Item/Row_Product.fxml";
    public static final String ROW_WISHLIST  ="/gui/Item/Row_Wishlist.fxml";
    public static final String ROW_NEWS  ="/gui/Item/Row_News.fxml";
    public static final String PRODUCT_BOX_SHORT  ="/gui/Item/Product_Box_Short.fxml";
    public static final String PRODUCT_BOX  ="/gui/Item/Product_Box.fxml";
    public static final String ROW_ORDERDETAIL  ="/gui/Item/Row_OrderDetail.fxml";
    public static final String ROW_COMMENT  ="/gui/Item/Row_Comment.fxml";
    public static final String LIBRARY_BOX  ="/gui/Item/Library_Product_Box.fxml";
    public static final String NEWS_BOX  ="/gui/Item/News_Box.fxml";
    public static final String PRODUCT_BOX_HEADER = "/gui/Item/Product_Box_Header.fxml";
 
    public static final String MALE  ="/icons/male256.png";
    public static final String FEMALE  ="/icons/female256.png";
    public static final String STAR_FILL  ="/icons/star_yellow32.png";
    public static final String STAR_REGULAR  ="/icons/starnotfill50.png";
    
    public static final String WSTAR_FILL  ="/icons/starWhiteFill50.png";
    public static final String WSTAR_REGULAR  ="/icons/starWhiteRegular50.png";
    public static final String WSTAR_HALF  ="/icons/starWhiteHalfFill50.png";
    
    public static final String ICON_APP  ="/icons/icon_app.png";
    public static final String DEFAULT_FOLDER  ="C:\\";
    public static final String EXAMPLE_LINK="https://axcelavietnam.com/wp-content/uploads/2020/04/the-big-picture-la-tu-vung-duoc-su-dung-pho-bien-trong-tieng-anh-thuong-mai.jpg";
    
    public static final String DEFAULT_EMAIL ="duongtanluc3565@gmail.com";
    public static final String DEFAULT_PASSWORD ="John@cena2019";
    
    public URL getURL(String path){
        return getClass().getClassLoader().getResource(path);
    }
}
