/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

import java.sql.*;

public class ProductoDAO {

    // Agregar un nuevo producto
    public boolean agregarProducto(Producto producto) {
        String query = "INSERT INTO producto (nombre, stock, precio) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getStock());
            stmt.setDouble(3, producto.getPrecio());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Listar todos los productos
    public void listarProductos() {
        String query = "SELECT * FROM producto";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int stock = rs.getInt("stock");
                double precio = rs.getDouble("precio");

                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Stock: " + stock + ", Precio: " + precio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
