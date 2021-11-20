/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import javafx.scene.control.Label;

/**
 *
 * @author Administrator
 */
public class Catch_Errors {
    public static boolean check_Text(JFXTextField txt) {
        if (txt.getText().trim().length() >= 10 && txt.getText().trim().length() < 100 ) {
            return true;
        } else {
            Dialog.showMessageDialog("Error!","Please "+txt.getPromptText()+" from 10 to 100 characters!");
            txt.requestFocus();
            return false;
        }
        
    }

    public static boolean check_TextArea(JFXTextArea txt) {
        if (txt.getText().trim().length() >= 10 && txt.getText().trim().length() < 4000 ) {
            return true;
        } else {
            Dialog.showMessageDialog("Error!","Please "+txt.getPromptText()+" from 10 to 4000 characters!");
            txt.requestFocus();
            return false;
        }
    }
    
     public static boolean check_Name(JFXTextField txt) {
        if (txt.getText().trim().length() >= 5 && txt.getText().trim().length() < 30 ) {
            return true;
        } else {
            Dialog.showMessageDialog("Error!","Please "+txt.getPromptText()+" from 5 to 30 characters!");
            txt.requestFocus();
            return false;
        }
        
    }
     public static boolean check_Float(JFXTextField txt) {
        try {
            float hp = Float.parseFloat(txt.getText());
            if ((hp >= 0)) {
                return true;
            } else {
                Dialog.showMessageDialog("Error!","Please enter a number that greater than 0!");
                txt.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Dialog.showMessageDialog("Error!","You must type a real number!");
            txt.requestFocus();
            return false;
        }
    }
    public static boolean check_TextProduct(JFXTextField txt) {
        if (txt.getText().trim().length() >= 5 && txt.getText().trim().length() < 30 ) {
            return true;
        } else {
            Dialog.showMessageDialog("Wrong data!","Please "+txt.getPromptText()+", from 5 to 30 characters!");
            txt.requestFocus();
            return false;
        }
        
    }
     public static boolean check_Languages(JFXTextField txt) {
        if (txt.getText().trim().length() >= 4 && txt.getText().trim().length() < 15 ) {
            return true;
        } else {
            Dialog.showMessageDialog("Wrong data!","Please enter the language from 4 to 15 characters");
            txt.requestFocus();
            return false;
        }
        
    }

    public static boolean check_TextAreaProduct(JFXTextArea txt) {
        if (txt.getText().trim().length() > 50 && txt.getText().trim().length() < 1000) {
            return true;
        } else {
            Dialog.showMessageDialog("Wrong data!","Please enter the Description from 50 and 1000 characters! ");
            txt.requestFocus();
            return false;
        }
    }

     
     public static Boolean validationReleaseDay(JFXDatePicker datePicker) {
        if (datePicker.getValue() == null) {
             Dialog.showMessageDialog("Wrong data","ReleaseDay cannot be empty!");
              return false;
        }else{
            return true;
        }
       
    }
     public static boolean check_FloatProduct(JFXTextField txt) {
        try {
            float hp = Float.parseFloat(txt.getText());
            if ((hp >= 0)) {
                return true;
            } else {
                Dialog.showMessageDialog("Wrong data!","Please "+txt.getPromptText()+",a value greater than or equal to 0! ");
                txt.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Dialog.showMessageDialog("Wrong data", " You must type a real number!");
            txt.requestFocus();
            return false;
        }
    }
     public static Boolean validationImageProduct(File file) {
        if (file==null) {
           Dialog.showMessageDialog("Wrong data","Image haven't been chosen");
           return false;
        }
        return true;
    }
     
}
