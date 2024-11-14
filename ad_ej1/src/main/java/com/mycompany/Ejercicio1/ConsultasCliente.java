/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Ejercicio1;

import java.sql.*;
import java.util.Scanner;

public class ConsultasCliente {

    // Método para listar todos los clientes
    public static void listarClientes(Connection conn) throws SQLException {
        String sql = "SELECT * FROM cliente";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nClientes:");
            System.out.println("ID\tNombre\tEmail\tCiudad\tTeléfono");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String ciudad = rs.getString("ciudad");
                String telefono = rs.getString("telefono");
                System.out.println(id + "\t" + nombre + "\t" + email + "\t" + ciudad + "\t" + telefono);
            }
        }
    }

    // Método para añadir un cliente
    public static void añadirCliente(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Introduce el email: ");
        String email = sc.nextLine();
        System.out.print("Introduce la ciudad: ");
        String ciudad = sc.nextLine();
        System.out.print("Introduce el teléfono: ");
        String telefono = sc.nextLine();

        String sqlCheck = "SELECT COUNT(*) FROM cliente WHERE email = ?";
        try (PreparedStatement stmtAdd = conn.prepareStatement(sqlCheck)) {
            stmtAdd.setString(1, email);
            ResultSet rs = stmtAdd.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("El email ya está registrado.");
                return;
            }
        }

        String sqlInsert = "INSERT INTO cliente (nombre, email, ciudad, telefono) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
            stmtInsert.setString(1, nombre);
            stmtInsert.setString(2, email);
            stmtInsert.setString(3, ciudad);
            stmtInsert.setString(4, telefono);
            int rowsAffected = stmtInsert.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Cliente añadido con éxito." : "Error al añadir el cliente.");
        }
    }

    // Método para buscar cliente por email
    public static void buscarClientePorEmail(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce parte del email para buscar: ");
        String emailFragment = sc.nextLine();
        String sql = "SELECT * FROM cliente WHERE email LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + emailFragment + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("nombre") + "\t" + rs.getString("email") +
                        "\t" + rs.getString("ciudad") + "\t" + rs.getString("telefono"));
            }
        }
    }

    // Método para modificar datos de un cliente
    public static void modificarCliente(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el email del cliente a modificar: ");
        String email = sc.nextLine();

        System.out.print("Introduce el nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Introduce el nuevo teléfono: ");
        String telefono = sc.nextLine();
        System.out.print("Introduce la nueva ciudad: ");
        String ciudad = sc.nextLine();

        String sqlUpdate = "UPDATE cliente SET nombre = ?, telefono = ?, ciudad = ? WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
            stmt.setString(1, nombre);
            stmt.setString(2, telefono);
            stmt.setString(3, ciudad);
            stmt.setString(4, email);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Cliente modificado con éxito." : "No se encontró el cliente.");
        }
    }

    // Método para borrar un cliente
    public static void borrarCliente(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el email del cliente a borrar: ");
        String email = sc.nextLine();
        String sqlDelete = "DELETE FROM cliente WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Cliente borrado con éxito." : "No se encontró el cliente.");
        }
    }
        // Método para mostrar el ranking de clientes
    public static void rankingClientes(Connection conn) throws SQLException {
        String sql = """
            SELECT c.nombre, c.email, COUNT(p.id) AS num_pedidos, IFNULL(SUM(p.gasto), 0) AS total_gasto
            FROM cliente c
            LEFT JOIN pedido p ON c.id = p.cliente_id
            GROUP BY c.id
            ORDER BY total_gasto DESC
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            System.out.println("\nRanking de Clientes:");
            System.out.println("Nombre\tEmail\tNúmero de Pedidos\tGasto Total");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                int numPedidos = rs.getInt("num_pedidos");
                double totalGasto = rs.getDouble("total_gasto");

                System.out.println(nombre + "\t" + email + "\t" + numPedidos + "\t" + totalGasto);
            }
        }
    }
    // Método para añadir un pedido
    public static void añadirPedido(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el email del cliente: ");
        String email = sc.nextLine();

        // Verificar si el cliente existe
        String sqlCheck = "SELECT id FROM cliente WHERE email = ?";
        int clienteId = -1;
        try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, email);
            ResultSet rs = stmtCheck.executeQuery();
            if (rs.next()) {
                clienteId = rs.getInt("id");
            } else {
                System.out.println("Cliente no encontrado. Debe registrar al cliente.");
                return;
            }
        }

        // Solicitar datos del pedido
        System.out.print("Introduce la fecha del pedido (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        System.out.print("Introduce el gasto del pedido: ");
        double gasto = sc.nextDouble();
        sc.nextLine();

        // Insertar el pedido
        String sqlInsertPedido = "INSERT INTO pedido (cliente_id, fecha, gasto) VALUES (?, ?, ?)";
        try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertPedido)) {
            stmtInsert.setInt(1, clienteId);
            stmtInsert.setString(2, fecha);
            stmtInsert.setDouble(3, gasto);
            int rowsAffected = stmtInsert.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Pedido añadido con éxito." : "Error al añadir el pedido.");
        }
    }
    // Método para actualizar un pedido
    public static void actualizarPedido(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el ID del pedido a actualizar: ");
        int pedidoId = sc.nextInt();
        sc.nextLine();

        // Solicitar nuevos datos del pedido
        System.out.print("Introduce la nueva fecha del pedido (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        System.out.print("Introduce el nuevo gasto del pedido: ");
        double gasto = sc.nextDouble();
        sc.nextLine();

        // Actualizar el pedido
        String sqlUpdate = "UPDATE pedido SET fecha = ?, gasto = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
            stmt.setString(1, fecha);
            stmt.setDouble(2, gasto);
            stmt.setInt(3, pedidoId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Pedido actualizado con éxito." : "No se encontró el pedido.");
        }
    }
    // Método para borrar un pedido
    public static void borrarPedido(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el ID del pedido a borrar: ");
        int pedidoId = sc.nextInt();
        sc.nextLine();

        // Borrar el pedido
        String sqlDelete = "DELETE FROM pedido WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {
            stmt.setInt(1, pedidoId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Pedido borrado con éxito." : "No se encontró el pedido.");
        }
    }

    


}
