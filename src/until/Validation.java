/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import DAO.AccountDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.Account;

/**
 *
 * @author Admin
 */
public class Validation {

    public static double price = 0;

    public static String validationPersonName(TextField textField) {
        String err = "";
        if (!textField.getText().trim().isEmpty() && !textField.getText().trim().matches("[\\D]{3,}")) {
            err = "NAME must be at least 3 characters and contain no numbers !\n";
            changeColor(textField);
        }
        return err;
    }

    public static String validationName(TextField textField) {
        String err = "";
        if (textField.getText().trim().isEmpty()) {
            err = "Username not is empty!\n";
            changeColor(textField);
        }
        return err;
    }

    public static String validationEmail(JFXTextField textField) {
        String err = "";
        if (textField.getText().trim().isEmpty()) {
            err = "EMAIL cannot be empty!\n";
        } else if (!textField.getText().trim().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            err = "EMAIL must be in the correct format!\n";
        }
        if (!err.isEmpty()) {
            changeColor(textField);
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
        if (!err.isEmpty()) {
            changeColor(datePicker);
        }
        return err;
    }

    public static String validationDate(JFXDatePicker datePicker, String name) {
        String err = "";
        if (datePicker.getValue() == null) {
            err = name + " cannot be empty!\n";
        }
        if (!err.isEmpty()) {
            changeColor(datePicker);
        }
        return err;
    }

    public static String validationUserName(TextField textField) {
        String err = "";
        Account ac = null;
        boolean flag = textField.getText().trim().isEmpty();
        if (!flag) {
            ac = new AccountDAO().selectByUser(textField.getText().trim());
        }
        if (flag) {
            err = "USERNAME cannot be empty!\n";
        } else if (!textField.getText().trim().matches("[\\w]{3,}")) {
            err = "USERNAME must be at least 3 characters and contain no space !\n";
        } else if (ac != null) {
            err = "USERNAME already exist !\n";
        }
        if (!err.isEmpty()) {
            changeColor(textField);
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
        if (!err.isEmpty()) {
            changeColor(textField);
        }
        return err;
    }

    public static String validationOld_NewPass(PasswordField textField, PasswordField textField1) {
        String err = "";
        if (textField.getText().trim().equals(textField1.getText()) && textField.getText().length() > 5) {
            err = "Old password and new password must be difference!\n";
        } else if (textField.getText().trim().isEmpty() && textField1.getText().trim().isEmpty()) {
            err = "PASSWORD AND NEWPASSWORD can not be Empty!";
        }
        if (!err.isEmpty()) {
            changeColor(textField);
            changeColor(textField1);
        }
        return err;
    }

    public static String validationConfirmPassword(PasswordField textField, PasswordField textField1) {
        String err = "";
        if (textField.getText().trim().isEmpty()) {
            err = "CONFIRMPASSWORD cannot be empty!\n";
        } else if (!textField.getText().trim().matches("[\\w]{6,}")) {
            err = "PASSWORD and CONFIRMPASSWORD must be matched and bigger than 5 !\n";
        }
        if (!err.isEmpty()) {
            changeColor(textField);
            changeColor(textField1);
        }
        return err;
    }

    public static String validationNew_ConfirmPassword(PasswordField textField, PasswordField textField1) {
        String err = "";
        System.out.println(textField.getText()+"   "+textField1.getText());
        if (!textField.getText().equals(textField1.getText())) {
            err = "Incorrect Confirm password!\n";
        }
        if (!err.isEmpty()) {
            changeColor(textField1);
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

    public static String validationCategoryName(TextField textField) {
        String err = "";
        if (textField.getText().trim().length() < 2) {
            err = "CATERORY must be at least 2 characters\n";
        }
        if (!err.isEmpty()) {
            changeColor(textField);
        }
        return err;
    }

    public static String validationAccountID(TextField textField) {
        String err = "";
        try {
            Account ac = null;
            boolean flag = textField.getText().trim().isEmpty();
            try {
                if (!flag) {
                    ac = new AccountDAO().selectByID(Integer.parseInt(textField.getText().trim()));
                }
                if (flag) {
                    err = "ACCOUNT ID cannot be empty!\n";
                } else if (ac == null) {
                    err = "USERNAME does not exist !\n";
                }

            } catch (NumberFormatException e) {
                err = "ACCOUNT ID must be a number !\n";
            }

        } catch (Exception e) {
        }
        if (!err.isEmpty()) {
            changeColor(textField);
        }
        return err;
    }

    public static String validationJFXTextFieldLength(TextField textField, String name, int... ints) {
        String err = "";
        if (textField.getText().trim().isEmpty()) {
            err = name + " cannot be empty!\n";
        } else if (textField.getText().trim().length() < ints[0]) {
            err = name + " must be at least " + ints[0] + " letters \n";
        } else if (ints.length > 1) {
            if (textField.getText().trim().length() < ints[0] || textField.getText().trim().length() > ints[1]) {
                err = name + " must be from " + ints[0] + " to " + ints[1] + " letters\n";
            }
        }
        if (!err.isEmpty()) {
            changeColor(textField);
        }
        return err;
    }

    public static String validationJFXTextAreaLength(TextArea textField, String name, int... ints) {
        String err = "";
        if (textField.getText().trim().isEmpty()) {
            err = name + " cannot be empty!\n";
        } else if (textField.getText().trim().length() < ints[0]) {
            err = name + " must be at least " + ints[0] + " letters \n";
        } else if (ints.length > 1) {
            if (textField.getText().trim().length() < ints[0] || textField.getText().trim().length() > ints[1]) {
                err = name + " must be from " + ints[0] + " to " + ints[1] + " letters\n";
            }
        }
        if (!err.isEmpty()) {
            changeColor(textField);
        }
        return err;
    }

    public static String validationPrice(TextField textField) {
        String err = "";
        try {
            double hp = Double.parseDouble(textField.getText().trim());
            if (hp < 0) {
                err += "PRICE must be greater than or equal to 0!\n";
            }
        } catch (NumberFormatException e) {
            err += "PRICE must must be a real number!!\n";
        }
        if (!err.isEmpty()) {
            changeColor(textField);
        }
        return err;
    }

    public static String validationSale(TextField textField) {
        String err = "";
        if (textField.getText().trim().isEmpty()) {
            return err;
        }
        try {
            double hp = Double.parseDouble(textField.getText().trim());
            if (hp < 0) {
                err += "SALE must be greater than or equal to 0!\n";
            }
        } catch (NumberFormatException e) {
            err += "SALE must must be a real number!!\n";
        }
        if (!err.isEmpty()) {
            changeColor(textField);
        }
        return err;
    }

    public static String validationSize(TextField textField) {
        String err = "";
        try {
            double hp = Double.parseDouble(textField.getText().trim());
            if (hp <= 0) {
                err += "SIZE must be greater than to 0!\n";
            }
        } catch (NumberFormatException e) {
            err += "SIZE must must be a real number!!\n";
        }
        if (!err.isEmpty()) {
            changeColor(textField);
        }
        return err;
    }

    public static String validationImage(ImageView imageView, Image image, Circle circle, String name) {
        String err = "";
        if (image == null) {
            err += name + " must be chose!\n";
        }
        if (!err.isEmpty()) {
            changeColor(imageView, circle);
        }
        return err;
    }

    public static String validationImage(ImageView imageView, Image image, String name) {
        String err = "";
        if (image == null) {
            err += name + " must be chose!\n";
        }
        if (!err.isEmpty()) {
            changeColor(imageView, null);
        }
        return err;
    }

    private static void changeColor(ImageView imageView, Circle circle) {
        if (circle != null) {
            circle.setStroke(Color.valueOf("#F38064"));
        }
        imageView.setStyle("-fx-effect : dropshadow( three-pass-box  , #F38064 , 0 , 1 , 3 , 3 )");
        imageView.setOnMouseReleased((evt) -> {
            clearColor(circle);
            clearColor(imageView);
        });
    }

    private static void changeColor(Node node) {
        if (node instanceof JFXTextField) {
            ((JFXTextField) node).setUnFocusColor(Color.valueOf("#F38064"));
        } else if (node instanceof JFXTextArea) {
            ((JFXTextArea) node).setUnFocusColor(Color.valueOf("#F38064"));
        } else if (node instanceof JFXDatePicker) {
            ((JFXDatePicker) node).getStyleClass().add("jfx-date-picker-error");
        } else if (node instanceof TextField || node instanceof TextArea) {
            node.setStyle("-fx-border-color : " + "#F38064");
        } else if (node instanceof ImageView) {
            node.setStyle("-fx-effect : dropshadow( three-pass-box  , #F38064 , 0 , 1 , 3 , 3 )");
        }
        node.focusedProperty().addListener((evt) -> {
            clearColor(node);
        });
    }

    public static void clearColor(Node node) {
        if (node instanceof JFXTextField) {
            ((JFXTextField) node).setUnFocusColor(Paint.valueOf("#4d4d4d"));
        } else if (node instanceof JFXTextArea) {
            ((JFXTextArea) node).setUnFocusColor(Paint.valueOf("#4d4d4d"));
        } else if (node instanceof JFXDatePicker) {
            if (node.getStyleClass().size() > 3) {
                ((JFXDatePicker) node).getStyleClass().remove(node.getStyleClass().size() - 1);
            }
        } else if (node instanceof TextField) {
            node.setStyle("-fx-border-color : " + "TRANSPARENT");
        } else if (node instanceof TextArea) {
            node.setStyle("-fx-border-color : " + "TRANSPARENT");
        } else if (node instanceof Circle) {
            ((Circle) node).setStroke(Color.WHITE);
        } else if (node instanceof ImageView) {
            node.setStyle("-fx-effect : dropshadow( three-pass-box  , transparent , 0 , 1 , 0 , 0 )");
        }
    }

    public static void Messages(Label label, String string) {
        label.setText(string);
    }

    public static void Incorrect(TextField name) {
        name.setStyle("-fx-border-color: #F38064");
    }

    public static void Correct(TextField name) {
        name.setStyle("-fx-border-color: #fff");
    }
}
