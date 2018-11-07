/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypertech.authentication.models;

import java.util.List;

/**
 *
 * @author hypertech
 */
public class Usuario extends ObjectDAO{
       
    private int usuarioId;
    
    private int tipo;
    
    private String usuario;

    private String contrasenia;
    
    private String nombre;
	
    private String apaterno;

    private String amaterno;

    private String correo_electronico;
	
    private String archivo_selfie;
	
    private String telefono;
	
    private String fecha_registro;

    public Usuario() {

    }

    public Usuario(int tipo, String usuario, String contrasenia, String pNombre, String aPaterno, String aMaterno, String correo, String imagen,
			String telefono, String fechaRegistro) {
	super();
        this.tipo = tipo;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.nombre = pNombre;
	this.apaterno = aPaterno;
        this.amaterno = aMaterno;
	this.correo_electronico = correo;
        this.archivo_selfie = imagen;
	this.telefono = telefono;
        this.fecha_registro = fechaRegistro;
	
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public String getContrasenia() {
        return contrasenia;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getArchivo_selfie() {
        return archivo_selfie;
    }

    public void setArchivo_selfie(String archivo_selfie) {
        this.archivo_selfie = archivo_selfie;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    
    @Override
    public int guarda() throws Exception{
        
        System.out.print("guardar");

        
        List<String> values = getValues();
        int error = 0;
        
        for(int i=0; i<values.size(); i++){
                
            String[] value = values.get(i).split(":");
                            
            if(!"int".equals(value[0]))
                if(value[0] == null || "NULL".equals(value[0]))
                    error++;
            
        }
        
        if(error == 0 || error > 2)
            throw new Exception("Datos incompletos");
    
        return super.guarda();
    }
    
    public Usuario logIn() throws Exception{
        
        String query = "SELECT * FROM Usuario WHERE usuario=? OR correo_electronico=?";

        Usuario resultado = (Usuario) consulta(query, new String[]{ getUsuario(), getCorreo_electronico() });
        
        if(!getContrasenia().equals(resultado.getContrasenia()))
            throw new Exception("Contraseña incorrecta");
         
        return resultado; 
    }
    
}
