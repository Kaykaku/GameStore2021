/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import model.Account;

/**
 *
 * @author leminhthanh
 */
public class Auth {
    public static Account USER = null;
    
    public static boolean isManager(){
        return USER.getRole()==1 || USER.getRole()==0;
    }
    public static boolean isAdmin(){
        return USER.getRole()==0;
    }
}
