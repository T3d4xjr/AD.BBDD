/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Ejercicio1;

/**
 *
 * @author DAM1
 */
import com.mysql.cj.x.protobuf.MysqlxSql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DAM1
 */
public class GestionCliente {

    
    
    private static final String URL ="jdbc:mysql://localhost:3306/ad_ej1";
    private static final String USER ="2dam";
    private static final String PASSWORD ="2dam";
    
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL,USER,PASSWORD)){
            
            //Consulta 1:
            String sql ="SELECT * FROM  cliente";
            try(Statement stmt =conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
            {
                System.out.println("Clientes:");
                System.out.println("ID\tNombre\tEmail\tciudad\tTelefono");
            
            while(rs.next())    
                    {
                    int id =rs.getInt("id");
                    String nombre =rs.getString("nombre");
                    String email =rs.getString("email");
                    String ciudad =rs.getString("ciudad");
                    String telefono=rs.getString("telefono");
                    
                        System.out.println(id+"\t"+nombre+"\t"+email+"\t"+ciudad+"\t"+telefono);
                    
                    }   
            stmt.close();
            }
        
         
            
        //Consulta 2    
        String sql2 ="SELECT * FROM  cliente";
                
        try(Statement stmt2 =conn.createStatement(); ResultSet rs = stmt2.executeQuery(sql2))
            {
                System.out.println("Clientes:");
                System.out.println("ID\tNombre\tEmail\tciudad\tTelefono");
            
            while(rs.next())    
                    {
                    int id =rs.getInt("id");
                    String nombre =rs.getString("nombre");
                    String email =rs.getString("email");
                    String ciudad =rs.getString("ciudad");
                    String telefono=rs.getString("telefono");
                    
                        System.out.println(id+"\t"+nombre+"\t"+email+"\t"+ciudad+"\t"+telefono);
                    
                    }   
            stmt2.close();
            }
        
        
        conn.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar la base de datos");
            System.out.println(e.getLocalizedMessage());
        }
        
        
        
        
        
        
            
        }
        
 }
    
