/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio9;

import java.sql.Connection;

import java.sql.*;

public class PrestamoDAO {
    public boolean registrarPrestamo(Prestamo prestamo) {
        String query = "INSERT INTO prestamo (id_libro, lector, fecha_prestamo, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, prestamo.getIdLibro());
            stmt.setString(2, prestamo.getLector());
            stmt.setString(3,prestamo.getFechaPrestamo());
            stmt.setString(4, prestamo.getEstado());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean devolverPrestamo(int idPrestamo) {
        String query = "UPDATE prestamo SET estado = 'Devuelto' WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idPrestamo);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void listarPrestamosPendientes() {
        String query = "SELECT p.id, p.fecha_prestamo, l.titulo, l.autor, p.lector, p.estado " +
                       "FROM prestamo p JOIN libro l ON p.id_libro = l.id WHERE p.estado = 'Pendiente'";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nPréstamos pendientes:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String fechaPrestamo = rs.getString("fecha_prestamo");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String lector = rs.getString("lector");
                String estado = rs.getString("estado");

                System.out.println("ID: " + id + ", Fecha: " + fechaPrestamo + ", Título: " + titulo +
                        ", Autor: " + autor + ", Cliente: " + lector + ", Estado: " + estado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarTodosPrestamos() {
        String query = "SELECT p.id, p.fecha_prestamo, l.titulo, l.autor, p.lector, p.estado " +
                       "FROM prestamo p JOIN libro l ON p.id_libro = l.id";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nTodos los préstamos:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String fechaPrestamo = rs.getString("fecha_prestamo");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String lector = rs.getString("lector");
                String estado = rs.getString("estado");

                System.out.println("ID: " + id + ", Fecha: " + fechaPrestamo + ", Título: " + titulo +
                        ", Autor: " + autor + ", Cliente: " + lector + ", Estado: " + estado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
