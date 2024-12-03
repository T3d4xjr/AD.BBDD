/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tedax
 */
public class PedidoDAO {

    private Scanner scanner = new Scanner(System.in);

    public void registrarPedido(String fecha, String cliente, int cantidadProductos) throws SQLException {
        String sqlPedido = "INSERT INTO pedido (fecha, cliente) VALUES (?, ?)";
        String sqlDetallePedido = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String sqlActualizarStock = "UPDATE producto SET stock = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.ConexionBD()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement psPedido = conexion.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psPedido.setString(1, fecha);
                psPedido.setString(2, cliente);
                psPedido.executeUpdate();

                ResultSet rs = psPedido.getGeneratedKeys();
                if (rs.next()) {
                    int idPedido = rs.getInt(1);
                    try (PreparedStatement psDetalle = conexion.prepareStatement(sqlDetallePedido)) {
                        for (int i = 0; i < cantidadProductos; i++) {
                            System.out.println("Ingrese el ID del producto:");
                            int idProducto = scanner.nextInt();
                            
                            if (idProducto <= 0) {
                                System.out.println("ID de producto inválido.");
                                conexion.rollback();
                                return;
                            }

                            Producto producto = obtenerProductoPorId(idProducto);

                            if (producto == null) {
                                System.out.println("Producto no encontrado. Cancelando pedido.");
                                conexion.rollback();
                                return;
                            }

                            System.out.println("Ingrese la cantidad:");
                            int cantidad = scanner.nextInt();

                            if (cantidad > producto.getStock()) {
                                System.out.println("No hay suficiente stock para el producto " + producto.getNombre() + ". Cancelando pedido.");
                                conexion.rollback();
                                return;
                            }

                            float subtotal = producto.getPrecio() * cantidad;

                            psDetalle.setInt(1, idPedido);
                            psDetalle.setInt(2, producto.getId());
                            psDetalle.setInt(3, cantidad);
                            psDetalle.setFloat(4, subtotal);
                            psDetalle.addBatch();
                        }
                        psDetalle.executeBatch();
                    }

                    try (PreparedStatement psActualizarStock = conexion.prepareStatement(sqlActualizarStock)) {
                        for (int i = 0; i < cantidadProductos; i++) {
                         
                            Producto producto = obtenerProductoPorId(i);
                            if (producto == null) {
                                continue; 
                            }

                            int cantidad = 1;
                            producto.setStock(producto.getStock() - cantidad);
                            psActualizarStock.setInt(1, producto.getStock());
                            psActualizarStock.setInt(2, producto.getId());
                            psActualizarStock.addBatch();
                        }
                        psActualizarStock.executeBatch();
                    }

                    conexion.commit();
                    System.out.println("Pedido registrado correctamente.");
                }
            } catch (SQLException e) {
                conexion.rollback();
                System.out.println("Error al registrar el pedido: " + e.getMessage());
            }
        }
    }

    public Producto obtenerProductoPorId(int idProducto) {
        Producto producto = null;
        String sql = "SELECT * FROM producto WHERE id = ?";

        try (Connection conexion = ConexionBD.ConexionBD(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setStock(rs.getInt("stock"));
                    producto.setPrecio(rs.getFloat("precio"));
                } 
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }
        return producto;
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sqlListarPedidos = "SELECT p.id, p.fecha, p.cliente, SUM(dp.subtotal) AS total"
                + " FROM pedido p"
                + " JOIN detalle_pedido dp ON p.id = dp.id_pedido"
                + " GROUP BY p.id;";

        try (Connection conexion = ConexionBD.ConexionBD()) {
            try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sqlListarPedidos)) {
                System.out.println("Pedidos (ID, fecha, cliente, total): ");
                while (rs.next()) {
                    int idPedido = rs.getInt("id");
                    String fecha = rs.getString("fecha");  // Obtenemos la fecha como String
                    String cliente = rs.getString("cliente");
                    float total = rs.getFloat("total");

                    // Añadimos el pedido a la lista
                    Pedido pedido = new Pedido(idPedido, fecha, cliente);
                    pedidos.add(pedido);

                    // Formateamos el total y lo mostramos en el formato adecuado
                    System.out.println(idPedido + ", " + fecha + ", " + cliente + ", " + String.format("%.2f", total) + "€");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar los pedidos");
        }

        return pedidos;
    }



    public void listarDetallesPedido(int idPedido) {
        String sqlDetallesPedido
                = "SELECT p.id, p.fecha, p.cliente, SUM(dp.subtotal) AS total "
                + "FROM pedido p "
                + "JOIN detalle_pedido dp ON p.id = dp.id_pedido "
                + "WHERE p.id = ? "
                + "GROUP BY p.id;";

        String sqlDetallesProductos
                = "SELECT pr.nombre, dp.cantidad, dp.subtotal "
                + "FROM detalle_pedido dp "
                + "JOIN producto pr ON dp.id_producto = pr.id "
                + "WHERE dp.id_pedido = ?;";

        try (Connection conexion = ConexionBD.ConexionBD()) {
            try (PreparedStatement stmtPedido = conexion.prepareStatement(sqlDetallesPedido)) {
                stmtPedido.setInt(1, idPedido);
                try (ResultSet rsPedido = stmtPedido.executeQuery()) {
                    if (rsPedido.next()) {
                        String fechaPedido = rsPedido.getString("fecha");
                        String cliente = rsPedido.getString("cliente");
                        float total = rsPedido.getFloat("total");

                        // Modificado: Usando println y concatenación
                        System.out.println("Detalles del pedido " + idPedido + ":");
                        System.out.println("Fecha: " + fechaPedido+ "\nCliente: " + cliente + "\nTotal: " + total);

                        try (PreparedStatement stmtProductos = conexion.prepareStatement(sqlDetallesProductos)) {
                            stmtProductos.setInt(1, idPedido);
                            try (ResultSet rsProductos = stmtProductos.executeQuery()) {
                                System.out.println("Productos (nombre, cantidad, subtotal):");
                                while (rsProductos.next()) {
                                    String nombreProducto = rsProductos.getString("nombre");
                                    int cantidad = rsProductos.getInt("cantidad");
                                    float subtotal = rsProductos.getFloat("subtotal");

                                    // Modificado: Usando println y concatenación
                                    System.out.println(nombreProducto + ", " + cantidad + ", " + subtotal);
                                }
                            }
                        }
                    } else {
                        System.out.println("No se encontró el pedido con ID " + idPedido);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener los detalles del pedido: " + ex.getMessage());
        }
    }
}

