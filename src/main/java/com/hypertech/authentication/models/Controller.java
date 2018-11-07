/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypertech.authentication.models;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author hypertech
 */
public abstract class Controller{
            
    public int guarda() throws HException {
        
        try{
            
            String insert = toString(Enums.ACTION.INSERT);
        
            System.out.println(insert);

            JdbcTemplate jdbcTemplate = ConexionDAO.jdbcTemplate();

            KeyHolder key = new GeneratedKeyHolder();

            List<String> values = getValues();

            jdbcTemplate.update((Connection con) -> {

                PreparedStatement stmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                for(int i=0; i<values.size(); i++){

                    String[] value = values.get(i).split(":");

                    if("int".equals(value[0]))
                        stmt.setString((i+ 1), value[1]);
                    else
                        stmt.setString((i+ 1), value[0]);

                }

                return stmt;
            }, key);	
        
            return key.getKey().intValue();
            
        }catch(DataAccessException ex) {
            throw new HException("Error al registrar los datos", ex.getMessage(), ex);
	}
        
    }
    
    public void actualiza(String... conditions) throws HException{
        
        try{
            
            String update = toString(Enums.ACTION.UPDATE);

            if(conditions.length > 0){

                update = update + " WHERE";

                for (String condition : conditions) {
                    if (condition != null) 
                        update = update + " " + condition;   
                }
            }
            
        }catch(HException ex) {
            throw new HException("Error al actualizar los datos", ex.getMessage(), ex);
	}     
             
    }
    
    public void elimina(String query) throws HException{
        
        try{
            
        }catch(Exception ex) {
            throw new HException("Error al eliminar los datos", ex.getMessage(), ex);
	}
    }
    
    public Object consulta(String query, String... conditions) throws HException {
        
        try{
            
            JdbcTemplate jdbcTemplate = ConexionDAO.jdbcTemplate();

            return jdbcTemplate.queryForObject(
		query, conditions, new BeanPropertyRowMapper(getClass()));
            
        }catch(DataAccessException ex) {
            throw new HException("No se encontraron resultados", ex.getMessage(), ex);
	}

    }
    
    public List consultar(String... conditions) throws HException{
        
        try{
            
            String query = "SELECT * FROM " + this.getClass().getSimpleName() ;

            if(conditions.length > 0){

                query = query + " WHERE";

                for (String condition : conditions)
                    if (condition != null) 
                        query = query + " " +  condition;   

            }

            JdbcTemplate jdbcTemplate = ConexionDAO.jdbcTemplate();

            List resultado  = jdbcTemplate.query(query,
                    new BeanPropertyRowMapper(getClass()));

            return resultado;
            
        }catch(DataAccessException ex) {
            throw new HException("No se encontraron resultados", ex.getMessage(), ex);
	}
        
    }

    protected String toString(Enums.ACTION action) throws HException {
        
        ArrayList<String> data = new ArrayList();
        ArrayList<String> values = new ArrayList();
        Field field[] = this.getClass().getDeclaredFields();
        
        switch(action){
            case INSERT:
                try {
                    
                    for (int i = 1; i < field.length; i++)
                    if(field[i].get(this) != null){
                        data.add(field[i].getName());
                        values.add("?");
                    }  
                    
                } catch(IllegalAccessException ex){ }
                
                
                if(data.isEmpty())
                    throw new HException("No se contienen datos");
                
                return  "INSERT INTO " + this.getClass().getSimpleName() + " (" + String.join(", ", data) + ") VALUES (" + String.join(", ", values) + ");";
                
            case UPDATE:
                
                String value = "";
                
                try {
                    
                    for (int i = 1; i < field.length; i++)
                    if(field[i].get(this) != null)
                        data.add(field[i].getName() + "=?");
                
                } catch(IllegalAccessException ex){ }
                
                if(data.isEmpty())
                    throw new HException("No se contienen datos");
                
                return "UPDATE " + this.getClass().getSimpleName() + " SET " + String.join(", ", data);
                
        }
                 
        throw new HException("No se contienen datos");
    }
    
    protected List<String> getValues() throws HException {
        
        ArrayList<String> values = new ArrayList();
        Field field[] = this.getClass().getDeclaredFields();
        
        try {
            
            for (int i = 1; i < field.length; i++)
            if(field[i].get(this) != null)
                if("int".equals(field[i].getType().toString()))
                    values.add("int:" + field[i].get(this));
                else
                    values.add("" + field[i].get(this));
            
        } catch(IllegalAccessException ex){ }
        
        if(values.isEmpty())
            throw new HException("No se contienen datos");
     
        return values;
    }   
        
}
