/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio9;

/**
 *
 * @author tedax
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // Listar todos los libros
    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro";

        try (Connection conexion = ConexionBD.ConexionBD();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int anioPublicacion = rs.getInt("anio_publicacion");
                int cantidadDisponible = rs.getInt("cantidad_disponible");
                String categoria = rs.getString("categoria");

                Libro libro = new Libro(id, titulo, autor, anioPublicacion, cantidadDisponible, categoria);
                libros.add(libro);
                
                
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los libros: " + e.getMessage());
        }
        return libros;
    }
}
