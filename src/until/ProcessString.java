/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

/**
 *
 * @author Admin
 */
public class ProcessString {
    public static String cutString(String text , int limit){
        text =text.length()>limit-2? text.substring(0, limit)+"...":text;
        return text;
    }
}
