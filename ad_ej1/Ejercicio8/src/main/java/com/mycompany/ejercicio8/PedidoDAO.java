/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

import java.sql.*;

public class PedidoDAO {

    // Agregar un nuevo pedido
    public boolean agregarPedido(Pedido pedido) {
    String query = "INSERT INTO pedido (fecha, cliente) VALUES (?, ?)";

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setDate(1, new java.sql.Date(pedido.getFecha().getTime()));
        stmt.setString(2, pedido.getCliente());

        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
            // Obtener el ID generado automáticamente
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setId(generatedKeys.getInt(1));  // Asignamos el ID generado
                }
            }
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    // Listar todos los pedidos
    public void listarPedidos() {
    String query = "SELECT p.id, p.fecha, p.cliente, SUM(dp.subtotal) AS total "
                 + "FROM pedido p "
                 + "JOIN detalle_pedido dp ON p.id = dp.id_pedido "
                 + "GROUP BY p.id, p.fecha, p.cliente";

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
