/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

/**
 *
 * @author Administrator
 */
public class Catch_Errors {
    public static boolean check_Text(JFXTextField txt) {
        if (txt.getText().trim().length() > 10 && txt.getText().trim().length() < 100 || txt.getText().isEmpty()==false) {
            return true;
        } else {
            Dialog.showMessageDialog("Lỗi!","Vui lòng nhập vào chữ cái hợp lệ từ 10 đến 100 kí tự! ");
            return false;
        }
        
    }

    public static boolean check_TextArea(JFXTextArea txt) {
        if (txt.getText().trim().length() > 10 && txt.getText().trim().length() < 300 || txt.getText().isEmpty()==false) {
            return true;
        } else {
            Dialog.showMessageDialog("Lỗi!","Vui lòng nhập vào chữ cái hợp lệ từ 10 đến 300 kí tự! ");
            return false;
        }
    }
    
     public static boolean check_Name(JFXTextField txt) {
        if (txt.getText().trim().length() > 5 && txt.getText().trim().length() < 30 || txt.getText().isEmpty()==false) {
            return true;
        } else {
            Dialog.showMessageDialog("Lỗi!","Vui lòng nhập vào chữ cái hợp lệ từ 5 đến 30 kí tự! ");
            return false;
        }
        
    }
     public static boolean check_Float(JFXTextField txt) {
        try {
            float hp = Float.parseFloat(txt.getText());
            if ((hp >= 0)||hp==-1) {
                return true;
            } else {
                Dialog.showMessageDialog("Lỗi!","Vui lòng nhập vào giá lớn hơn 0! ");
                return false;
            }
        } catch (NumberFormatException e) {
            Dialog.showMessageDialog("Lỗi", " Phải là số thực!");
            return false;
        }
    }
    
     
}
