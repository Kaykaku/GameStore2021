/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import DAO.AccountDAO;
import DAO.ApplicationDAO;
import DAO.CategoryDAO;
import DAO.NewsDAO;
import DAO.OrderDAO;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Account;
import model.Application;
import model.Category;
import model.News;
import model.Order;

/**
 *
 * @author NguyenHuan
 */
public class ExportText {

    private static List<Object[]> listObj = new ArrayList<>();
    public static String[] header;

    public static void create(String path) throws IOException {
        final List<Object[]> objects = getListObjects();
        writeToFileText(objects, path);
    }

    public static void writeToFileText(List<Object[]> object, String path) {
        try {
            FileWriter fr = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fr);
            for (Object object1 : header) {
                String tamp = object1.toString() + "\t \t \t";
                bw.write(tamp);
            }
            bw.write("\n");
            for (Object[] obj : object) {
                for (Object object1 : obj) {
                    String tamp = object1.toString() + "\t";
                    bw.write(tamp);
                }
                bw.write("\n");

            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Object[]> getListObjects() {
        return listObj;
    }

    public static void exportHTML(Stage parent, String text, String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", "*.html"));
        fileChooser.setInitialFileName(fileName);
        fileChooser.setTitle("Select folder");
        File path = fileChooser.showSaveDialog(parent);
        if (path != null) {
            try {
                FileWriter fr = new FileWriter(path);
                BufferedWriter bw = new BufferedWriter(fr);
                String tamp = text;
                bw.write(tamp);
                bw.flush();
                bw.close();

                if (Dialog.showComfirmDialog(null, "Save successfully! \nDo you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (path.exists()) {
                        desktop.open(path);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void exportText(Stage parent, String[] header, List<Object[]> listObjects, String fileName) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", "*.txt"));
        fileChooser.setInitialFileName(fileName);
        fileChooser.setTitle("Select folder");
        File path = fileChooser.showSaveDialog(parent);
        if (path != null) {
            try {
                listObj = listObjects;
                ExportText.header = header;
                ExportText.create(path.getAbsolutePath());
                if (Dialog.showComfirmDialog(null, "Save successfully! \nDo you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (path.exists()) {
                        desktop.open(path);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    static FileChooser fileChooser = new FileChooser();

    public static void ExportFileProduct() {
        ApplicationDAO applicationDAO = new ApplicationDAO();
        List<Application> list = applicationDAO.selectAll();
        fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", "*.txt"));
        fileChooser.setInitialFileName("product");
        File path = fileChooser.showSaveDialog(new Stage());
        if (path != null) {
            try {
                PrintStream pr = new PrintStream(path);
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i).toString();
                    pr.println(temp + "\n");
                }
                if (Dialog.showComfirmDialog(null, "Save successfully! \nDo you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (path.exists()) {
                        desktop.open(path);
                    }
                }
                pr.flush();
                pr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void ExportFileCategory() {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> list = categoryDAO.selectAll();
        fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", "*.txt"));
        fileChooser.setInitialFileName("Category");
        fileChooser.setTitle("Select folder");
        File path = fileChooser.showSaveDialog(new Stage());
        if (path != null) {
            try {
                PrintStream pr = new PrintStream(path);
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i).toString1();
                    pr.println(temp + "\n");
                }
                if (Dialog.showComfirmDialog(null, "Save successfully! \nDo you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (path.exists()) {
                        desktop.open(path);
                    }
                }
                pr.flush();
                pr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void ExportFileNews() {
        NewsDAO newsDAO = new NewsDAO();
        List<News> list = newsDAO.selectAll();
        fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", "*.txt"));
        fileChooser.setInitialFileName("News");
        fileChooser.setTitle("Select folder");
        File path = fileChooser.showSaveDialog(new Stage());
        if (path != null) {
            try {
                PrintStream pr = new PrintStream(path);
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i).toString();
                    pr.println(temp + "\n");
                }
                if (Dialog.showComfirmDialog(null, "Save successfully! \nDo you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (path.exists()) {
                        desktop.open(path);
                    }
                }
                pr.flush();
                pr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void ExportFileAccount() {
        AccountDAO accDAO = new AccountDAO();
        List<Account> list = accDAO.selectAll();
        fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", "*.txt"));
        fileChooser.setInitialFileName("Account");
        fileChooser.setTitle("Select folder");
        File path = fileChooser.showSaveDialog(new Stage());
        if (path != null) {
            try {
                PrintStream pr = new PrintStream(path);
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i).toStrings();
                    pr.println(temp + "\n");
                }
                if (Dialog.showComfirmDialog(null, "Save successfully! \nDo you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (path.exists()) {
                        desktop.open(path);
                    }
                }
                pr.flush();
                pr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void ExportFileOrder() {
        OrderDAO orDAO = new OrderDAO();
        List<Order> list = orDAO.selectAll();
        fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", "*.txt"));
        fileChooser.setInitialFileName("Order");
        fileChooser.setTitle("Select folder");
        File path = fileChooser.showSaveDialog(new Stage());
        if (path != null) {
            try {
                PrintStream pr = new PrintStream(path);
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i).toString();
                    pr.println(temp + "\n");
                }
                if (Dialog.showComfirmDialog(null, "Save successfully! \nDo you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (path.exists()) {
                        desktop.open(path);
                    }
                }
                pr.flush();
                pr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
