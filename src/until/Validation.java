/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import DAO.AccountDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Account;

/**
 *
 * @author Admin
 */
public class Validation {

    public static String validationPersonName(JFXTextField textField) {
        String err = "";
        if (!textField.getText().trim().isEmpty() && !textField.getText().trim().matches("[\\D]{3,}")) {
            err = "NAME must be at least 3 characters and contain no numbers !\n";
        }
        return err;
    }

    public static String validationEmail(JFXTextField textField) {
        String err = "";
        if (!textField.getText().trim().isEmpty() && !textField.getText().trim().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            err = "EMAIL must be in the correct format!\n";
        }
        return err;
    }

    public static String validationBirthDay(JFXDatePicker datePicker) {
        String err = "";
        if (datePicker.getValue() == null) {
            err = "BIRTHDAY cannot be empty!\n";
        } else {
            LocalDate birth = datePicker.getValue();
            LocalDate now = LocalDate.now();
            int years = Period.between(birth, now).getYears();
            if (years < 12) {
                err += "You must be over 12 years old \n";
            }
        }
        return err;
    }

    public static String validationUserName(TextField textField) {
        String err = "";
        Account ac=null;
        boolean flag =textField.getText().trim().isEmpty();
        if(!flag) ac = new AccountDAO().selectByUser(textField.getText().trim());
        if (flag) {
            err = "USERNAME cannot be empty!\n";
        } else if (!textField.getText().trim().matches("[\\w]{3,}")) {
            err = "USERNAME must be at least 3 characters and contain no space !\n";
        } else if (ac != null) {
            err = "USERNAME already exist !\n";
        }
        return err;
    }

    public static String validationPassword(PasswordField textField) {
        String err = "";
        if (textField.getText().trim().isEmpty()) {
            err = "PASSWORD cannot be empty!\n";
        } else if (!textField.getText().trim().matches("[\\w]{4,}")) {
            err = "PASSWORD must be at least 4 characters and contain no space !\n";
        }
        return err;
    }

    public static String validationConfirmPassword(PasswordField textField, PasswordField textField1) {
        String err = "";
        if (textField.getText().trim().isEmpty()) {
            err = "CONFIRMPASSWORD cannot be empty!\n";
        } else if (!textField.getText().trim().matches("[\\w]{4,}")) {
            err = "PASSWORD and CONFIRMPASSWORD are not matched !\n";
        }
        return err;
    }

    public static String validationImage(File file) {
        String err = "";
        if (file == null) {
            err = "IMAGE have been chose\n";
        }
        return err;
    }

    public static String validationCategoryName(JFXTextField textField) {
        String err = "";
        if (textField.getText().trim().length() < 2) {
            err = "CATERORY must be at least 2 characters\n";
        }
        return err;
    }

    public static String validationAccountID(TextField textField) {
        String err = "";
        try {
            Account ac=null;
            boolean flag =textField.getText().trim().isEmpty();
            if(!flag) ac = new AccountDAO().selectByID(Integer.parseInt(textField.getText().trim()));
            if (flag) {
                err = "ACCOUNT ID cannot be empty!\n";
            } else if (ac == null) {
                err = "USERNAME does not exist !\n";
            }
        } catch (Exception e) {
        }

        return err;
    }
}
