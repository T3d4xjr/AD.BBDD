/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio9;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author tedax
 */
public class Menu {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException 
    {
        int opcion;
    
        do {
            // Menú de opciones
            System.out.println("BD Ejer9");
            System.out.println("1. Listar libros");
            System.out.println("2. Registrar préstamo");
            System.out.println("3. devolver un pedido");
            System.out.println("4. Listar pedidos pendientes");
            System.out.println("5. Listar detalles de un pedido");
            System.out.println("6. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer del scanner

            // Aquí usamos el switch para ejecutar la opción seleccionada
            switch (opcion) {
                case 1:
                    Ejerc9Controller.listarLibros();
                    break;
                case 2:
                    Ejerc9Controller.registrarPrestamo();
                    break;
                case 3:
                    Ejerc9Controller.devolverPrestamo();
                    break;
                case 4:
                    Ejerc9Controller.listarPrestamosPendientes();
                    break;
                case 5:
                    Ejerc9Controller.listarPrestamos();
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("No es una opción válida.");
            }
        } while (opcion != 6);  // Se repite hasta que la opción seleccionada sea 6 (Salir)
    }
}
