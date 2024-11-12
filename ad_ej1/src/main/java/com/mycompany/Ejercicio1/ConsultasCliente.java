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
}
