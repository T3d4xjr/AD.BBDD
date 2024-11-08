/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.Ejercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DAM1
 */
public class ConexionMySQLBasica {

    
    
    private static final String URL ="jdbc:mysql://localhost:3306/ad_ej1";
    private static final String USER ="2dam";
    private static final String PASSWORD ="2dam";
    
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL,USER,PASSWORD)){
            String sql ="SELECT * FROM  cliente";
            try(Statement stmt =conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
            {
                System.out.println("Clientes:");
                System.out.println("ID\tNombre\t\tEmail\t\tTelefono");
            
            while(rs.next())    
                    {
                    int id =rs.getInt("id");
                    String nombre =rs.getString("nombre");
                    String email =rs.getString("email");
                    String telefono=rs.getString("telefono");
                    
                        System.out.printf("%d\t%s\t\t%s\t%s%n",id,nombre,email,telefono);
                    
                    }   
            }
            
        } catch (SQLException e) {
            System.out.println("Error al conectar la base de datos");
            System.out.println(e.getLocalizedMessage());
        }
        
        
        
    }
}
