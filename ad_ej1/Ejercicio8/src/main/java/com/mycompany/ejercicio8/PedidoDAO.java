/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

import java.sql.*;
import java.util.List;

public class PedidoDAO {

   public boolean agregarPedido(Pedido pedido, List<detallepedido> detalles) {
    String queryPedido = "INSERT INTO pedido (fecha, cliente) VALUES (?, ?)";
    String queryDetalle = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
    String updateStock = "UPDATE producto SET stock = stock - ? WHERE id = ?";

    try (Connection conn = ConexionBD.getConnection()) {
        // Deshabilitar el autocommit para manejar la transacción manusalmente
        conn.setAutoCommit(false);

        // Insertar el pedido
        try (PreparedStatement stmtPedido = conn.prepareStatement(queryPedido, Statement.RETURN_GENERATED_KEYS)) {
            stmtPedido.setString(1, pedido.getFecha());
            stmtPedido.setString(2, pedido.getCliente());

            int affectedRows = stmtPedido.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmtPedido.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pedido.setId(generatedKeys.getInt(1)); // Asignar ID del pedido
                    } else {
                        throw new SQLException("No se pudo obtener el ID del pedido.");
                    }
                }
            } else {
                throw new SQLException("Error al insertar el pedido.");
            }
        }

        // Procesar detalles del pedido
        for (detallepedido detalle : detalles) {
            // Calcular el subtotal
           

            // Insertar el detalle
            try (PreparedStatement stmtDetalle = conn.prepareStatement(queryDetalle)) {
                stmtDetalle.setInt(1, pedido.getId());
                stmtDetalle.setInt(2, detalle.getIdProducto());
                stmtDetalle.setInt(3, detalle.getCantidad());
                stmtDetalle.setDouble(4,detalle.getSubtotal());
                stmtDetalle.executeUpdate();
            }

            // Actualizar el stock
            try (PreparedStatement stmtUpdateStock = conn.prepareStatement(updateStock)) {
                stmtUpdateStock.setInt(1, detalle.getCantidad());
                stmtUpdateStock.setInt(2, detalle.getIdProducto());
                stmtUpdateStock.executeUpdate();
            }
        }

        // Confirmar la transacción
        conn.commit();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        try (Connection conn = ConexionBD.getConnection()) {
            if (conn != null) {
                conn.rollback(); // Revertir transacción
            }
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
    } finally {
        try (Connection conn = ConexionBD.getConnection()) {
            if (conn != null) {
                conn.setAutoCommit(true); // Restaurar autocommit
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return false;
}



    // Listar todos los pedidos
    public void listarPedidos() {
    String query = "SELECT p.id, p.fecha, p.cliente, SUM(dp.subtotal) AS total "
                 + "FROM pedido p "
                 + "JOIN detalle_pedido dp ON p.id = dp.id_pedido "
                 + "GROUP BY p.id";

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            Date fecha = rs.getDate("fecha");
            String cliente = rs.getString("cliente");
            double total = rs.getDouble("total");

            // Imprimir los detalles del pedido con el total calculado
            System.out.println("ID: " + id + ", Fecha: " + fecha + ", Cliente: " + cliente + ", Total: " + total + "€");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
