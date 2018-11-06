/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypertech.authentication.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author hypertech
 */
public abstract class ObjectDAO {
            
    public int guardar() throws Exception{
        
        String insert = toString(ConexionDAO.ACTION.INSERT);

        JdbcTemplate jdbcTemplate = ConexionDAO.jdbcTemplate();
        
        KeyHolder key = new GeneratedKeyHolder();
        
        List<String> values = getValues();
     
        jdbcTemplate.update((Connection con) -> {
            
            PreparedStatement stmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            
            for(int i=0; i<values.size(); i++)
                stmt.setString((i+ 1), values.get(i));
            
            return stmt;
        }, key);	
        
        if(key == null || key.getKey() == null)
           throw new Exception("Error al insertar los datos.");
				
        return key.getKey().intValue() ;
    }
    
    public void actualizar(String... conditions) throws Exception{
        
        String update = toString(ConexionDAO.ACTION.UPDATE);

        if(conditions.length > 0){
            
            update = update + " WHERE";
            
            for (String condition : conditions) {
                if (condition != null) 
                    update = update + " " +  conditions[0];   
            }
        }
  
    }
    
    public void eliminar(String query){
  
    }
    
    public<T> List consultar(String... conditions) throws Exception{
                
        String query = "SELECT * FROM " + this.getClass().getSimpleName() ;

        if(conditions.length > 0){
            
            query = query + " WHERE";
            
            for (String condition : conditions) {
                if (condition != null) 
                    query = query + " " +  conditions[0];   
            }
        }
            
        JdbcTemplate jdbcTemplate = ConexionDAO.jdbcTemplate();

        List<T> clientes = new ArrayList<>();
        		
	clientes = jdbcTemplate.query("SELECT * FROM cliente;", 
                (ResultSet resultSet, int arg) -> new Gson()
                        .fromJson("{}", new TypeToken<T>(){}.getType()));
        
        if(clientes.isEmpty())
            throw new Exception("No se contienen datos");

		
        return clientes;
    }

    public String toString(ConexionDAO.ACTION action) throws Exception {
        
        ArrayList<String> data = new ArrayList();
        ArrayList<String> values = new ArrayList();
        Field field[] = this.getClass().getDeclaredFields();
        
        switch(action){
            case INSERT:
                
                for (int i = 1; i < field.length; i++)
                    if(field[i].get(this) != null){
                        data.add(field[i].getName());
                        values.add(field[i].get(this) + "");
                    }  
                
                if(data.isEmpty())
                    throw new Exception("No se contienen datos");
                
                return  "INSERT INTO " + this.getClass().getSimpleName() + " (" + String.join(", ", data) + ") VALUES (" + String.join(", ", values) + ")";
                
            case UPDATE:
                
                for (int i = 1; i < field.length; i++)
                    if(field[i].get(this) != null)
                        data.add(field[i].getName() + "=" + field[i].get(this));
                
                if(data.isEmpty())
                    throw new Exception("No se contienen datos");
                
                return "UPDATE " + this.getClass().getSimpleName() + " SET " + String.join(", ", data);
                
        }
        
            
            
        return String.join(", ", data);
    }
    
    private List<String> getValues() throws Exception {
        
        ArrayList<String> values = new ArrayList();
        Field field[] = this.getClass().getDeclaredFields();
        
        for (int i = 1; i < field.length; i++)
            if(field[i].get(this) != null)
                values.add(field[i].get(this) + "");
        
        if(values.isEmpty())
            throw new Exception("No se contienen datos");
     
        return values;
    }
    
}
