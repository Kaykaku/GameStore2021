/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import com.pdfjet.A3;
import com.pdfjet.Cell;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author NguyenHuan
 */
public class ExportPDF {

    public static void writeTable(List<Object[]> list, String[] headers, File path) throws FileNotFoundException, Exception {
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

        for (String s : headers) {
            Cell cell = new Cell(heading, s);
            tableRow.add(cell);
        }

        tableData.add(tableRow);

        for (Object[] objs : list) {
            tableRow = new ArrayList<>();
            for (Object obj : objs) {
                Cell cell = new Cell(dataTable, String.valueOf(obj));
                tableRow.add(cell);
            }
            tableData.add(tableRow);
        }
        table.setData(tableData);

        table.setPosition(10f, 50f);
        table.autoAdjustColumnWidths();
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

    public static void ExportPDF(Stage parent, String[] header, List<Object[]> rows, String fileName, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Value.DEFAULT_FOLDER));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add All", "*.pdf"));
        fileChooser.setInitialFileName(fileName);
        fileChooser.setTitle("Select folder");
        File path = fileChooser.showSaveDialog(parent);
        if (path != null) {
            try {
                writeTable(rows, header, path);
                if (Dialog.showComfirmDialog(null, "Save successfully! \n Do you want to open it?") == 1) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (path.exists()) {
                        desktop.open(path);
                    }
                }
            } catch (Exception ex) {

            }
        }

    }
}
