/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypertech.authentication.controllers;

import com.hypertech.authentication.models.HException;
import com.hypertech.authentication.models.ResponseJSON;
import com.hypertech.authentication.models.Usuario;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hypertech
 */
@RestController
public class ControllerUsuario {
    
    @RequestMapping(value = "guardarUsuario", method = RequestMethod.POST)
    public ResponseJSON guardarUsuario(@RequestBody Usuario usuario) {
		 
	try {
			 	 		 
            if(usuario.getUsuarioId()== 0)
		usuario.setUsuarioId(usuario.guarda());
            else
                usuario.actualiza("usuarioId=" + usuario.getUsuarioId());
            
            return new ResponseJSON(true, "Exitoso", usuario.getUsuarioId());	
            
	} catch(HException e) {
            return e.getResponse();			 
        }	
		 
    }
    
    @RequestMapping(value = "/loginUsuario", method = RequestMethod.POST)
    public ResponseJSON loginUsuario(@RequestBody Usuario usuario) {
	 
        try {
            
            return new ResponseJSON (true, "Exitoso", usuario.logIn());	
            
        } catch(HException e) {
            return e.getResponse();			 
        }			 
    }
    
    @RequestMapping(value = "/consultarUsuarios", method = RequestMethod.POST)
    public ResponseJSON consultarUsuarios() {
	 
        try {
            
            return new ResponseJSON (true, "Exitoso", new Usuario().consultar());	
            
        } catch(HException e) {
            return e.getResponse();			 
        }			 
    }


    
}
