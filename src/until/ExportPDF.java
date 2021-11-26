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
import com.pdfjet.A3;
import com.pdfjet.A4;
import com.pdfjet.Cell;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class ExportPDF {

    static JFileChooser fileChooser = new JFileChooser();
    static FileNameExtensionFilter Findpdf = new FileNameExtensionFilter("PDF(.pdf)", "pdf", "pdf");
    static FileChooser fc = new FileChooser();

    public static void exportPDFProduct() throws FileNotFoundException, Exception {
        ApplicationDAO appDao = new ApplicationDAO();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".pdf"));
        fc.setTitle("Select folder");
        File path = fc.showSaveDialog(new Stage());
        if (path != null) {
            FileOutputStream fos = new FileOutputStream(path);
            PDF pdf = new PDF(fos);
            Page page = new Page(pdf, A3.PORTRAIT);
            Font heading = new Font(pdf, CoreFont.HELVETICA_BOLD);
            Font dataTable = new Font(pdf, CoreFont.HELVETICA);
            heading.setSize(10);
            dataTable.setSize(8.5);
            Table table = new Table();
            List<List<Cell>> tableData = new ArrayList<>();
            List<Cell> tableRow = new ArrayList<>();
            Cell cell = new Cell(heading, "ID");
            tableRow.add(cell);
            cell = new Cell(heading, "Name");
            tableRow.add(cell);
            cell = new Cell(heading, "Price");
            tableRow.add(cell);
            cell = new Cell(heading, "Size");
            tableRow.add(cell);
            cell = new Cell(heading, "Developer");
            tableRow.add(cell);
            cell = new Cell(heading, "Publisher");
            tableRow.add(cell);
            cell = new Cell(heading, "CreationDate");
            tableRow.add(cell);
            cell = new Cell(heading, "ReleaseDay");
            tableRow.add(cell);
            cell = new Cell(heading, "Languages");
            tableRow.add(cell);
            cell = new Cell(heading, "Sale");
            tableRow.add(cell);
            cell = new Cell(heading, "Active");
            tableRow.add(cell);
            cell = new Cell(heading, "EnableBuy");
            tableRow.add(cell);
            tableData.add(tableRow);
            List<Application> list = appDao.selectAll();
            for (Application app : list) {
                Cell AplicationID = new Cell(dataTable, String.valueOf(app.getApplicationID()));
                Cell Name = new Cell(dataTable, app.getName());
                Cell Price = new Cell(dataTable, String.valueOf(app.getPrice()));
                Cell Size = new Cell(dataTable, String.valueOf(app.getSize()));
                Cell Developer = new Cell(dataTable, app.getDeveloper());
                Cell Publisher = new Cell(dataTable, app.getPublisher());
                Cell CreationDate = new Cell(dataTable, String.valueOf(app.getCreationDate()));
                Cell ReleaseDay = new Cell(dataTable, String.valueOf(app.getReleaseDay()));
                Cell Languages = new Cell(dataTable, app.getLanguages());
                Cell Sale = new Cell(dataTable, String.valueOf(app.getSale()));
                Cell Active = new Cell(dataTable, String.valueOf(app.isActive() ? "Active" : "InActive"));
                Cell EnableBuy = new Cell(dataTable, String.valueOf(app.isEnableBuy() ? "Enable Buy" : "InEnable Buy"));

                tableRow = new ArrayList<Cell>();
                tableRow.add(AplicationID);
                tableRow.add(Name);
                tableRow.add(Price);
                tableRow.add(Size);
                tableRow.add(Developer);
                tableRow.add(Publisher);
                tableRow.add(CreationDate);
                tableRow.add(ReleaseDay);
                tableRow.add(Languages);
                tableRow.add(Sale);
                tableRow.add(Active);
                tableRow.add(EnableBuy);
//                    tableRow.add(Description);
                tableData.add(tableRow);
            }
            table.setData(tableData);
            table.setPosition(10f, 50f);
            for (int i = 0; i < tableRow.size(); i++) {
                if (i == 0) {
                    table.setColumnWidth(i, 20f);
                } else if (i == 1) {
                    table.setColumnWidth(i, 140f);
                } else if (i == 2) {
                    table.setColumnWidth(i, 40f);
                } else if (i == 3) {
                    table.setColumnWidth(i, 40f);
                } else if (i == 9) {
                    table.setColumnWidth(i, 40f);
                } else if (i == 10) {
                    table.setColumnWidth(i, 42f);
                } else if (i == 11) {
                    table.setColumnWidth(i, 58f);
                } else {
                    table.setColumnWidth(i, 90f);
                }

            }
            while (true) {
                table.drawOn(page);
                if (!table.hasMoreData()) {
                    table.resetRenderedPagesCount();
                    break;
                }
                page = new Page(pdf, A3.PORTRAIT);

            }
            pdf.flush();
            fos.close();
        }
    }

    public static void exportPDFNews() throws Exception {
        NewsDAO newDao = new NewsDAO();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".pdf"));
        fc.setTitle("Select folder");
        File path = fc.showSaveDialog(new Stage());
        if (path != null) {
            FileOutputStream fos = new FileOutputStream(path);
            PDF pdf = new PDF(fos);
            Page page = new Page(pdf, A4.PORTRAIT);
            Font heading = new Font(pdf, CoreFont.HELVETICA_BOLD);
            Font dataTable = new Font(pdf, CoreFont.HELVETICA);
            dataTable.setSize(10);
            Table table = new Table();
            List<List<Cell>> tableData = new ArrayList<List<Cell>>();
            List<Cell> tableRow = new ArrayList<>();
            Cell cell = new Cell(heading, "ID");
            tableRow.add(cell);
            cell = new Cell(heading, "Name");
            tableRow.add(cell);
            cell = new Cell(heading, "Creation Date");
            tableRow.add(cell);
            cell = new Cell(heading, "Tilte");
            tableRow.add(cell);
            cell = new Cell(heading, "Description");
            tableRow.add(cell);
            cell = new Cell(heading, "Content");
            tableRow.add(cell);
            cell = new Cell(heading, "Views");
            tableRow.add(cell);
            tableRow.add(cell);
            tableData.add(tableRow);
            List<News> list = newDao.selectAll();
            for (News model : list) {
                Cell NewID = new Cell(dataTable, String.valueOf(model.getNewsID()));
                Cell Name = new Cell(dataTable, model.getName());
                Cell CreationBy = new Cell(dataTable, String.valueOf(model.getCreationDate()));
                Cell Tilte = new Cell(dataTable, model.getTitle());
                Cell Description = new Cell(dataTable, model.getDescription());
                Cell Content = new Cell(dataTable, model.getContents());
                Cell Views = new Cell(dataTable, String.valueOf(model.getViews()));

                tableRow = new ArrayList<Cell>();
                tableRow.add(NewID);
                tableRow.add(Name);
                tableRow.add(CreationBy);
                tableRow.add(Tilte);
                tableRow.add(Description);
                tableRow.add(Content);
                tableRow.add(Views);
                tableData.add(tableRow);
            }
            table.setData(tableData);
            table.setPosition(30f, 60f);
            for (int i = 0; i < tableRow.size(); i++) {
                if (i == 0) {
                    table.setColumnWidth(i, 20f);
                } else if (i == 1) {
                    table.setColumnWidth(i, 70f);
                } else {
                    table.setColumnWidth(i, 200f);
                }

            }
            while (true) {
                table.drawOn(page);
                if (!table.hasMoreData()) {
                    table.resetRenderedPagesCount();
                    break;
                }
                page = new Page(pdf, A4.PORTRAIT);

            }
            pdf.flush();
            fos.close();
        }

    }

    public static void exportPDFCategory() throws Exception {
        CategoryDAO cateDAO = new CategoryDAO();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".pdf"));
        fc.setTitle("Select folder");
        File path = fc.showSaveDialog(new Stage());
        if (path != null) {
            FileOutputStream fos = new FileOutputStream(path);
            PDF pdf = new PDF(fos);
            Page page = new Page(pdf, A4.PORTRAIT);
            Font heading = new Font(pdf, CoreFont.HELVETICA_BOLD);
            Font dataTable = new Font(pdf, CoreFont.HELVETICA);
            dataTable.setSize(10);
            Table table = new Table();
            List<List<Cell>> tableData = new ArrayList<List<Cell>>();
            List<Cell> tableRow = new ArrayList<>();
            Cell cell = new Cell(heading, "ID");
            tableRow.add(cell);
            cell = new Cell(heading, "Name");
            tableRow.add(cell);
            cell = new Cell(heading, "Color");
            tableRow.add(cell);
            tableData.add(tableRow);
            List<Category> list = cateDAO.selectAll();
            for (Category model : list) {
                Cell ID = new Cell(dataTable, String.valueOf(model.getCategoryId()));
                Cell Name = new Cell(dataTable, model.getName());
                Cell Color = new Cell(dataTable, model.getColor());
                tableRow = new ArrayList<Cell>();
                tableRow.add(ID);
                tableRow.add(Name);
                tableRow.add(Color);
                tableData.add(tableRow);
            }
            table.setData(tableData);
            table.setPosition(100f, 60f);
            for (int i = 0; i < tableRow.size(); i++) {
                if (i == 0) {
                    table.setColumnWidth(i, 20f);
                } else {
                    table.setColumnWidth(i, 130f);
                }
            }
            while (true) {
                table.drawOn(page);
                if (!table.hasMoreData()) {
                    table.resetRenderedPagesCount();
                    break;
                }
                page = new Page(pdf, A4.PORTRAIT);

            }
            pdf.flush();
            fos.close();
        }

    }

    public static void exportPDFAccount() throws Exception {
        AccountDAO accDAO = new AccountDAO();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".pdf"));
        fc.setTitle("Select folder");
        File path = fc.showSaveDialog(new Stage());
        if (path != null) {
            FileOutputStream fos = new FileOutputStream(path);
            PDF pdf = new PDF(fos);
            Page page = new Page(pdf, A4.PORTRAIT);
            Font heading = new Font(pdf, CoreFont.HELVETICA_BOLD);
            Font dataTable = new Font(pdf, CoreFont.HELVETICA);
            dataTable.setSize(10);
            Table table = new Table();
            List<List<Cell>> tableData = new ArrayList<List<Cell>>();
            List<Cell> tableRow = new ArrayList<>();
            Cell cell = new Cell(heading, "ID");
            tableRow.add(cell);
            cell = new Cell(heading, "Name");
            tableRow.add(cell);
            cell = new Cell(heading, "Gender");
            tableRow.add(cell);
            cell = new Cell(heading, "BirthDay");
            tableRow.add(cell);
            cell = new Cell(heading, "Country");
            tableRow.add(cell);
            cell = new Cell(heading, "Email");
            tableRow.add(cell);
            tableData.add(tableRow);
            List<Account> list = accDAO.selectAll();
            for (Account model : list) {
                Cell ID = new Cell(dataTable, String.valueOf(model.getAccountId()));
                Cell Name = new Cell(dataTable, model.getName());
                Cell Gender = new Cell(dataTable, String.valueOf(model.isGender() ? "Male" : "Famale"));
                Cell BirthDay = new Cell(dataTable, String.valueOf(model.getBirthDay()));
                Cell Country = new Cell(dataTable, model.getCountry());
                Cell Email = new Cell(dataTable, model.getEmail());
                Cell Creation = new Cell(dataTable, String.valueOf(model.getCreationDate()));
                Cell UserName = new Cell(dataTable, model.getUsername());
                Cell Password = new Cell(dataTable, model.getPassword() + "");
                Cell Active = new Cell(dataTable, model.isActive() ? "Active" : "InActive");
                Cell Role = new Cell(dataTable, model.getRole() + "");

                tableRow = new ArrayList<Cell>();
                tableRow.add(ID);
                tableRow.add(Name);
                tableRow.add(Gender);
                tableRow.add(BirthDay);
                tableRow.add(Country);
                tableRow.add(Email);
                tableRow.add(Creation);
                tableRow.add(UserName);
                tableRow.add(Password);
                tableRow.add(Active);
                tableRow.add(Role);
                tableData.add(tableRow);
            }
            table.setData(tableData);
            table.setPosition(30f, 60f);
            for (int i = 0; i < tableRow.size(); i++) {
                if (i == 0) {
                    table.setColumnWidth(i, 20f);
                } else if (i == 3) {
                    table.setColumnWidth(i, 50f);
                } else {
                    table.setColumnWidth(i, 130f);
                }
            }
            while (true) {
                table.drawOn(page);
                if (!table.hasMoreData()) {
                    table.resetRenderedPagesCount();
                    break;
                }
                page = new Page(pdf, A4.PORTRAIT);

            }
            pdf.flush();
            fos.close();
        }
    }

    public static void exportPDFOrder() throws Exception {
        OrderDAO orDAO = new OrderDAO();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", ".pdf"));
        fc.setTitle("Select folder");
        File path = fc.showSaveDialog(new Stage());
        if (path != null) {
            FileOutputStream fos = new FileOutputStream(path);
            PDF pdf = new PDF(fos);
            Page page = new Page(pdf, A4.PORTRAIT);
            Font heading = new Font(pdf, CoreFont.HELVETICA_BOLD);
            Font dataTable = new Font(pdf, CoreFont.HELVETICA);
            dataTable.setSize(10);
            Table table = new Table();
            List<List<Cell>> tableData = new ArrayList<List<Cell>>();
            List<Cell> tableRow = new ArrayList<>();
            Cell cell = new Cell(heading, "ID");
            tableRow.add(cell);
            cell = new Cell(heading, "CreationDate");
            tableRow.add(cell);
            cell = new Cell(heading, "Status");
            tableRow.add(cell);
            cell = new Cell(heading, "AccountID");
            tableRow.add(cell);
            tableData.add(tableRow);
            List<Order> list = orDAO.selectAll();
            for (Order model : list) {
                Cell ID = new Cell(dataTable, String.valueOf(model.getOrderID()));
                Cell CreationDate = new Cell(dataTable, model.getCreationDate() + "");
                Cell Status = new Cell(dataTable, String.valueOf(model.getStatus()));
                Cell AccountId = new Cell(dataTable, String.valueOf(model.getAccountId()));
                tableRow = new ArrayList<Cell>();
                tableRow.add(ID);
                tableRow.add(CreationDate);
                tableRow.add(Status);
                tableRow.add(AccountId);
                tableData.add(tableRow);
            }
            table.setData(tableData);
            table.setPosition(50f, 60f);
            for (int i = 0; i < tableRow.size(); i++) {
                table.setColumnWidth(i, 100f);
            }
            while (true) {
                table.drawOn(page);
                if (!table.hasMoreData()) {
                    table.resetRenderedPagesCount();
                    break;
                }
                page = new Page(pdf, A4.PORTRAIT);

            }
            pdf.flush();
            fos.close();
        }
    }
}
