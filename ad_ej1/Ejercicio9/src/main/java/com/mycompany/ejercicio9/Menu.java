/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio9;

import java.util.Scanner;

public class Menu {

    private static final LibroDAO libroDAO = new LibroDAO();
    private static final PrestamoDAO prestamoDAO = new PrestamoDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nMenú de Gestión de Biblioteca:");
            System.out.println("1. Listar Libros");
            System.out.println("2. Registrar Préstamo");
            System.out.println("3. Devolver Préstamo");
            System.out.println("4. Listar Préstamos Pendientes");
            System.out.println("5. Listar Todos los Préstamos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    listarLibros();
                    break;
                case 2:
                    registrarPrestamo(scanner);
                    break;
                case 3:
                    devolverPrestamo(scanner);
                    break;
                case 4:
                    listarPrestamosPendientes();
                    break;
                case 5:
                    listarTodosPrestamos();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }

    private static void listarLibros() {
        libroDAO.listarLibros();
    }

    private static void registrarPrestamo(Scanner scanner) {
        System.out.print("Ingrese el ID del libro: ");
        int idLibro = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        System.out.print("Ingrese el nombre del lector: ");
        String lector = scanner.nextLine();
        System.out.print("Ingrese la fecha del préstamo (YYYY-MM-DD): ");
        String fechaStr = scanner.nextLine();

        
        Prestamo prestamo = new Prestamo(0, idLibro, lector, fechaStr, "Pendiente");

        if (prestamoDAO.registrarPrestamo(prestamo)) {
            System.out.println("Préstamo registrado con éxito.");
        } else {
            System.out.println("Error al registrar el préstamo.");
        }
    }

    private static void devolverPrestamo(Scanner scanner) {
        System.out.print("Ingrese el ID del préstamo: ");
        int idPrestamo = scanner.nextInt();

        if (prestamoDAO.devolverPrestamo(idPrestamo)) {
            System.out.println("Préstamo devuelto con éxito.");
        } else {
            System.out.println("Error al devolver el préstamo.");
        }
    }

    private static void listarPrestamosPendientes() {
        prestamoDAO.listarPrestamosPendientes();
    }

    private static void listarTodosPrestamos() {
        prestamoDAO.listarTodosPrestamos();
    }
}
