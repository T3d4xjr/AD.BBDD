/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio9;

import java.sql.*;

public class LibroDAO {
    public void listarLibros() {
    String query = "SELECT * FROM libro";

    try (Connection conn = ConexionDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        System.out.println("\nLibros disponibles:");
        while (rs.next()) {
            int id = rs.getInt("id");
            String titulo = rs.getString("titulo");
            String autor = rs.getString("autor");
            int anioPublicacion = rs.getInt("anio_publicacion");
            String categoria = rs.getString("categoria");
            int cantidadDisponible = rs.getInt("cantidad_disponible");

            System.out.println("ID: " + id + ", Título: " + titulo + ", Autor: " + autor +
                    ", Año: " + anioPublicacion + ", Categoría: " + categoria + ", Cantidad: " + cantidadDisponible);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
