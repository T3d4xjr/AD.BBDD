/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Ejercicio5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author DAM1
 */
public class menu {
    public static final String URL ="";
    public static final String USER ="";
    public static final String PASSWORD ="";
    
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1.Listar empleados");
            System.out.println("2.Añadir empleados");
            System.out.println("3.Actualizar empleados");
            System.out.println("4.Borrar empleados");
            System.out.println("5.Salir");
            opcion=sc.nextInt();
            
            try(Connection conn =DriverManager.getConnection(URL,USER,PASSWORD)) {
                switch(opcion){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        System.err.println("Error en la opcion.Intentelo de nuevo ");
                
                }
            } catch (SQLException e) {
                System.err.println("Error de la base de datos");
                e.printStackTrace();
            }
            
        } while (opcion !=5);
    }
}
