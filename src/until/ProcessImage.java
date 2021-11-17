/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author Admin 
 */
public class ProcessImage {

    public static File toFile(byte[] data, String namePhoto) {
        try {
            File file=new File("photo/" + namePhoto);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            return file;
        } catch (IOException e) {
        }
        return null;
    }

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

}
