/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

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
            System.out.println("BD TIENDA EJ8");
            System.out.println("1. Añadir un producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Añadir un pedido");
            System.out.println("4. Listar pedidos");
            System.out.println("5. Listar detalles de un pedido");
            System.out.println("6. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer del scanner

            // Aquí usamos el switch para ejecutar la opción seleccionada
            switch (opcion) {
                case 1:
                    Ejerc8Controller.agregarProducto();
                    break;
                case 2:
                    Ejerc8Controller.listarProductos();
                    break;
                case 3:
                    Ejerc8Controller.registrarPedido();
                    break;
                case 4:
                    Ejerc8Controller.listarPedidos();
                    break;
                case 5:
                    Ejerc8Controller.listarDetallesPedido();
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
