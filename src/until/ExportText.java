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
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    public static void create(String path) throws IOException {
        final List<Object[]> objects = getListObjects();
        writeToFileText(objects, path);
    }

    public static void writeToFileText(List<Object[]> object, String path) {
        try {
            FileWriter fr = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fr);
            for (Object[] obj : object) {
                String tamp = obj.toString();
                bw.write(tamp);
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

    public static void exportText(Component parent, List<Object[]> Object, String fileName) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter Fintxt = new FileNameExtensionFilter("Text(.txt)", "txt", "txt");
        fileChooser.setFileFilter(Fintxt);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
         
        fileChooser.setDialogTitle("Select folder");
        fileChooser.setSelectedFile(new File(fileName));
        int x = fileChooser.showDialog(parent, "Yes");
        if (x == JFileChooser.APPROVE_OPTION) {
            try {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith("txt")) {
                    path += ".txt";
                }
                ExportText.create(path);
                Dialog.showMessageDialog(null, "Save successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    static FileChooser fileChooser = new FileChooser();

    public static void ExportFileProduct() {
        ApplicationDAO applicationDAO = new ApplicationDAO();
        List<Application> list = applicationDAO.selectAll();
       fileChooser.setInitialDirectory(new File("C:\\Users\\Admin\\Downloads"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".txt"));
        fileChooser.setInitialFileName("product");
        File path = fileChooser.showSaveDialog(new Stage());
        if (path != null) {
            try {
                PrintStream pr = new PrintStream(path);
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i).toString();
                    pr.println(temp + "\n");
                }
                Dialog.showMessageDialog(null, "Save successfully!");
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
        fileChooser.setInitialDirectory(new File("C:\\Users\\Admin\\Downloads"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".txt"));
        fileChooser.setInitialFileName("Category");
        fileChooser.setTitle("Select folder");
        File path = fileChooser.showSaveDialog(new Stage());
        if (path != null) {
            try {
                PrintStream pr = new PrintStream(path);
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i).toString();
                    pr.println(temp + "\n");
                }
                Dialog.showMessageDialog(null, "Save successfully!");
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
        fileChooser.setInitialDirectory(new File("C:\\Users\\Admin\\Downloads"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".txt"));
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
                Dialog.showMessageDialog(null, "Save successfully!");
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
        fileChooser.setInitialDirectory(new File("C:\\Users\\Admin\\Downloads"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".txt"));
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
                Dialog.showMessageDialog(null, "Save successfully!");
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
        fileChooser.setInitialDirectory(new File("C:\\Users\\Admin\\Downloads"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".txt"));
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
                Dialog.showMessageDialog(null, "Save successfully!");
                pr.flush();
                pr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
