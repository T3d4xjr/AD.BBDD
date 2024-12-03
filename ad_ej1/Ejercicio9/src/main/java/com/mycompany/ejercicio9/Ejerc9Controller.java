/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio9;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tedax
 */
public class Ejerc9Controller {
    
    private static Scanner scanner = new Scanner(System.in);
    private static LibroDAO libroDAO = new LibroDAO();
    private static PrestamoDAO prestamoDAO = new PrestamoDAO();

    public static void listarLibros() {
        List<Libro> libros = libroDAO.listarLibros();
        for (Libro libro : libros) {
                System.out.println(libro.getId() + ", " + libro.getTitulo() + ", " + libro.getAutor() + ", " 
                        + libro.getAnioPublicacion() + ", " + libro.getCantidadDisponible() + ", " + libro.getCategoria());
            }
    }
    public static void registrarPrestamo() {
        System.out.println("Registrar un préstamo");

        // Solicitar los datos necesarios al usuario
        System.out.print("Ingrese el ID del libro: ");
        int idLibro = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        System.out.print("Ingrese el nombre del lector: ");
        String lector = scanner.nextLine();

        System.out.print("Ingrese la fecha del préstamo (YYYY-MM-DD): ");
        String fechaPrestamo = scanner.nextLine();

        try {
            // Llamar al DAO para registrar el préstamo
            prestamoDAO.registrarPrestamo(idLibro, lector, fechaPrestamo);
        } catch (SQLException e) {
            System.out.println("Error al registrar el préstamo: " + e.getMessage());
        }
    }
      // Método para devolver un préstamo
    public static void devolverPrestamo() {
        System.out.println("Devolver un préstamo");

        // Solicitar el ID del préstamo
        System.out.print("Ingrese el ID del préstamo: ");
        int idPrestamo = scanner.nextInt();

        try {
            // Llamar al DAO para devolver el préstamo
            prestamoDAO.devolverPrestamo(idPrestamo);
        } catch (SQLException e) {
            System.out.println("Error al devolver el préstamo: " + e.getMessage());
        }
    }
    public static void listarPrestamosPendientes() throws SQLException 
    {
    prestamoDAO.listarPrestamosPendientes();
    }
    public static void listarPrestamos() throws SQLException 
    {
    prestamoDAO.listarTodosPrestamos();
    }
    
}
