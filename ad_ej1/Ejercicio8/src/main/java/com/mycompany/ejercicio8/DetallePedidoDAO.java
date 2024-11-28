/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

import java.sql.*;

public class DetallePedidoDAO {
    // Listar detalles de un pedido
     public void listarDetallesPedido(int idPedido) {
        String query = "SELECT p.id, p.fecha, p.cliente, pr.nombre, dp.cantidad, (pr.precio * dp.cantidad) AS subtotal "
                     + "FROM pedido p "
                     + "JOIN detalle_pedido dp ON p.id = dp.id_pedido "
                     + "JOIN producto pr ON dp.id_producto = pr.id "
                     + "WHERE p.id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecer el parámetro de la consulta
            stmt.setInt(1, idPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Imprimir los detalles del pedido
                    Date fecha = rs.getDate("fecha");
                    String cliente = rs.getString("cliente");

                    System.out.println("Detalles del pedido " + idPedido + ":");
                    System.out.println("Fecha: " + fecha);
                    System.out.println("Cliente: " + cliente);
                    System.out.println("Productos:");

                    // Recorrer todos los productos asociados al pedido
                    do {
                        String nombreProducto = rs.getString("nombre");
                        int cantidad = rs.getInt("cantidad");
                        double subtotal = rs.getDouble("subtotal");

                        System.out.println(nombreProducto + ", Cantidad: " + cantidad + ", Subtotal: " + subtotal + "€");
                    } while (rs.next()); // Si hay más productos en el pedido
                } else {
                    System.out.println("No se encontró el pedido con ID: " + idPedido);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

