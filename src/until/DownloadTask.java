/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

/**
 *
 * @author Admin
 */
public class DownloadTask extends Task<Void> {

    private String url;
    private String name;
    private File file;

    public DownloadTask() {
    }

    public DownloadTask(File file, String url, String name) {
        this.url = url;
        this.name = name;
        this.file = file;
    }

    public void start() {
        Variable.DOWNLOAD_PROGRESSBAR.progressProperty().bind(this.progressProperty());
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
        Variable.IS_DOWNLOADING = true;
        Variable.MAIN_CONTROLLER.showDownloadProgress(true);
        Variable.DOWNLOAD_LABEL.setText(name);
    }

    @Override
    protected Void call() throws Exception {
        URLConnection connection = new URL(url).openConnection();
        long fileLength = connection.getContentLengthLong();
        try (
                BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());
                FileOutputStream fileOS = new FileOutputStream(file)) {
            byte data[] = new byte[1024];
            int byteContent;
            long nread = 0L;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1 && Variable.IS_DOWNLOADING) {
                fileOS.write(data, 0, byteContent);
                nread += byteContent;
                updateProgress(nread, fileLength);
                Variable.DOWNLOAD_LABEL.setText(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void cancelled() {
        Dialog.showMessageDialog("", "Your download cancelled");
        Variable.MAIN_CONTROLLER.showDownloadProgress(false);
    }

    @Override
    protected void failed() {
        Dialog.showMessageDialog("", "Your download failed");
        Variable.MAIN_CONTROLLER.showDownloadProgress(false);
    }

    @Override
    protected void succeeded() {
        if (Variable.IS_DOWNLOADING) {
            if (Dialog.showComfirmDialog(null, "Your download successfull! \n Do you want to open it?") == 1) {
                if (!Desktop.isDesktopSupported()) {
                    System.out.println("not supported");
                    return;
                }
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (IOException ex) {
                        //Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        Variable.MAIN_CONTROLLER.showDownloadProgress(false);
        Variable.IS_DOWNLOADING = false;
    }

}
