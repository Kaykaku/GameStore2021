/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class ProcessImage {

//    public static File toFile(byte[] data, String namePhoto) {
//        try {
//            File file = new File("/icons/" + namePhoto);
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(data);
//            return file;
//        } catch (IOException e) {
//        }
//        return null;
//    }

    public static String getExtensionImg(String ImgName) {
        return ImgName.substring(ImgName.lastIndexOf("."), ImgName.length());
    }

    public static byte[] toBytes(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return bytes;
        } catch (IOException ex) {

        }
        return null;
    }

    public static byte[] toBytes(Image image) {
        try {
            BufferedImage bf = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bf, "jpg", bos);
            byte[] data = bos.toByteArray();
            return data;
        } catch (IOException ex) {

        }
        return null;
    }

    public static FileInputStream toFileInputStream(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return fis;
        } catch (IOException ex) {

        }
        return null;
    }

    public static Image toImage(Blob blob) {
        InputStream in = null;
        try {
            in = blob.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ProcessImage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {

            }
        }
        return null;
    }

    public static BufferedImage toImage(byte[] bytes) {
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(is);
            return bi;
        } catch (IOException ex) {

        }
        return null;
    }
    public static Image toImageFX(byte[] bytes) {
        Image image = SwingFXUtils.toFXImage(ProcessImage.toImage(bytes), null);
        return image;
    }
    public Image toImageFX(String soure) {
        try {
            Image image = new Image(getClass().getResource(soure).toURI().toString());
            return image;
        } catch (URISyntaxException ex) {
            //Logger.getLogger(ProcessImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    public static File toFile(Image img) {
//        BufferedImage bi = SwingFXUtils.fromFXImage(img, null);
//        File f = new File("avt.png");
//        try {
//
//            ImageIO.write(bi, "png", f);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return f;
//    }
}
