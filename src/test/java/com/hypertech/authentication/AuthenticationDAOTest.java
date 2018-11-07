/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypertech.authentication;

import com.hypertech.authentication.models.Enums;
import com.hypertech.authentication.models.Usuario;

/**
 *
 * @author hypertech
 */
public class AuthenticationDAOTest {
    
    public static void main(String[] args) throws Exception {
        
        Usuario user = new Usuario(Enums.ROL.USER.ordinal(), "ejfm", "ejfm", "EJ", "F", "M", "ejfm@email.com", null, "5501022343", null);
        
    }
    
}
