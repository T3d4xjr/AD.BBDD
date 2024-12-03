/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio9;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author tedax
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // Registrar un nuevo préstamo y actualizar la cantidad del libro
    public void registrarPrestamo(int idLibro, String lector, String fechaPrestamo) throws SQLException {
        String sqlRegistrarPrestamo = "INSERT INTO prestamo (id_libro, lector, fecha_prestamo, estado) VALUES (?, ?, ?, 'Pendiente')";
        String sqlActualizarCantidadLibro = "UPDATE libro SET cantidad_disponible = cantidad_disponible - 1 WHERE id = ?";

        // Crear la conexión aquí y no dentro del bloque finally
        try (Connection conexion = ConexionBD.ConexionBD()) {
            // Iniciar la transacción
            conexion.setAutoCommit(false);  // Desactivamos el autocommit para hacer las operaciones en transacción

            // Registrar el préstamo
            try (PreparedStatement psPrestamo = conexion.prepareStatement(sqlRegistrarPrestamo)) {
                psPrestamo.setInt(1, idLibro);
                psPrestamo.setString(2, lector);
                psPrestamo.setString(3, fechaPrestamo);
                psPrestamo.executeUpdate();  // Registramos el préstamo
            }

            // Actualizar la cantidad disponible del libro
            try (PreparedStatement psCantidad = conexion.prepareStatement(sqlActualizarCantidadLibro)) {
                psCantidad.setInt(1, idLibro);
                psCantidad.executeUpdate();  // Actualizamos la cantidad disponible del libro
            }

            // Confirmamos la transacción
            conexion.commit();
            System.out.println("Préstamo registrado correctamente y cantidad del libro actualizada.");
        } catch (SQLException e) {
            // Si ocurre algún error, revertimos la transacción
            try {
                // Realizamos el rollback de la misma conexión
                Connection conexion = ConexionBD.ConexionBD();
                conexion.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
            }
        }finally{
            try (Connection conexion = ConexionBD.ConexionBD()) {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar el autocommit: " + e.getMessage());
            }
        }
    }
    // Método para devolver un préstamo y actualizar la cantidad del libro
    public void devolverPrestamo(int idPrestamo) throws SQLException {
        String sqlDevolverPrestamo = "UPDATE prestamo SET estado = 'Devuelto' WHERE id = ?";
        String sqlActualizarCantidadLibro = "UPDATE libro SET cantidad_disponible = cantidad_disponible + 1 WHERE id = (SELECT id_libro FROM prestamo WHERE id = ?)";

        try (Connection conexion = ConexionBD.ConexionBD()) {
            // Iniciar la transacción
            conexion.setAutoCommit(false);  // Desactivamos el autocommit para hacer las operaciones en transacción

            // Devolver el préstamo (cambiar el estado a "Devuelto")
            try (PreparedStatement psDevolver = conexion.prepareStatement(sqlDevolverPrestamo)) {
                psDevolver.setInt(1, idPrestamo);
                psDevolver.executeUpdate();  // Actualizamos el estado del préstamo
            }

            // Actualizar la cantidad disponible del libro
            try (PreparedStatement psCantidad = conexion.prepareStatement(sqlActualizarCantidadLibro)) {
                psCantidad.setInt(1, idPrestamo);
                psCantidad.executeUpdate();  // Actualizamos la cantidad disponible del libro
            }

            // Confirmamos la transacción
            conexion.commit();
            System.out.println("Préstamo devuelto correctamente y cantidad del libro actualizada.");
        } catch (SQLException e) {
           try {
                // Realizamos el rollback de la misma conexión
                Connection conexion = ConexionBD.ConexionBD();
                conexion.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
            }  // Volvemos a lanzar la excepción para que se maneje adecuadamente
        } finally {
            // Aseguramos que el autocommit vuelva a su valor por defecto
            try (Connection conexion = ConexionBD.ConexionBD()) {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar el autocommit: " + e.getMessage());
            }
        }
    }
    
    public List<Prestamo> listarPrestamosPendientes() throws SQLException {
    List<Prestamo> prestamosPendientes = new ArrayList<>();
    
    // Consulta para obtener los préstamos pendientes con los detalles del libro
    String sqlListarPrestamosPendientes = "SELECT p.id, p.fecha_prestamo, l.titulo, l.autor, p.lector, p.estado "
            + "FROM prestamo p "
            + "JOIN libro l ON p.id_libro = l.id "
            + "WHERE p.estado = 'Pendiente'";

    try (Connection conexion = ConexionBD.ConexionBD();
         PreparedStatement stmt = conexion.prepareStatement(sqlListarPrestamosPendientes);
         ResultSet rs = stmt.executeQuery()) {
        
        // Recorrer los resultados de la consulta
        while (rs.next()) {
            int idPrestamo = rs.getInt("id");  // Obtener el ID del préstamo
            String fechaPrestamo = rs.getString("fecha_prestamo");
            String tituloLibro = rs.getString("titulo");
            String autorLibro = rs.getString("autor");
            String lector = rs.getString("lector");
            String estado = rs.getString("estado");

            // Crear el objeto Prestamo con la información obtenida
            Prestamo prestamo = new Prestamo(idPrestamo, idPrestamo, lector, fechaPrestamo, estado);  // Aquí pasamos el id correcto
            
            // Agregar el préstamo a la lista
            prestamosPendientes.add(prestamo);

            // Mostrar los detalles del préstamo directamente
            System.out.println(prestamo.getId() + ", " + prestamo.getFechaPrestamo() + ", " + tituloLibro + ", " 
                    + autorLibro + ", " + prestamo.getLector() + ", " + prestamo.getEstado());
        }
    }

    return prestamosPendientes;
}

     // Método para listar todos los préstamos
    public List<Prestamo> listarTodosPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();

        // Consulta para obtener todos los préstamos con los detalles del libro
        String sqlListarTodosPrestamos = "SELECT p.id, p.fecha_prestamo, l.titulo, l.autor, p.lector, p.estado "
                + "FROM prestamo p "
                + "JOIN libro l ON p.id_libro = l.id";

        try (Connection conexion = ConexionBD.ConexionBD();
             PreparedStatement stmt = conexion.prepareStatement(sqlListarTodosPrestamos);
             ResultSet rs = stmt.executeQuery()) {
            
            // Recorrer los resultados de la consulta
            while (rs.next()) {
                int idPrestamo = rs.getInt("id");
                String fechaPrestamo = rs.getString("fecha_prestamo");
                String tituloLibro = rs.getString("titulo");
                String autorLibro = rs.getString("autor");
                String lector = rs.getString("lector");
                String estado = rs.getString("estado");

                // Crear el objeto Prestamo con la información obtenida
                Prestamo prestamo = new Prestamo(idPrestamo, idPrestamo, lector, fechaPrestamo, estado);
                
                // Mostrar los detalles del préstamo directamente
                System.out.println(prestamo.getId() + ", " + prestamo.getFechaPrestamo() + ", " + tituloLibro + ", " 
                        + autorLibro + ", " + prestamo.getLector() + ", " + prestamo.getEstado());
            }
        }

        return prestamos;
    }
}
