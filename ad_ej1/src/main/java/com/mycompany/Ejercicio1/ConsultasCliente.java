package com.mycompany.Ejercicio1;

import java.sql.*;
import java.util.Scanner;

//ALTER TABLE cliente ADD COLUMN contraseña VARCHAR(100) NOT NULL AFTER telefono;


public class ConsultasCliente {

   
    public static void listarClientes(Connection conn) throws SQLException {
   
    String sql = "SELECT * FROM cliente";
    
   
    try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
      
        String sqlCount = "SELECT COUNT(*) AS total FROM cliente";
        int totalClientes = 0;
        
      
        try (Statement stmtCount = conn.createStatement(); ResultSet rsCount = stmtCount.executeQuery(sqlCount)) {
            if (rsCount.next()) {
                totalClientes = rsCount.getInt("total");
            }
        }
        
    
        System.out.println("\nHay " + totalClientes + " clientes:");

      
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String email = rs.getString("email");
            String ciudad = rs.getString("ciudad");
            String telefono = rs.getString("telefono");
            
       
            System.out.println(id + " " + nombre + " " + email + " " + ciudad + " " + telefono);
        }
    } catch (SQLException e) {
        System.out.println("Error al listar los clientes.");
        e.printStackTrace();
    }
}


 
    public static void añadirCliente(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Introduce el email: ");
        String email = sc.nextLine();
        System.out.print("Introduce la ciudad: ");
        String ciudad = sc.nextLine();
        System.out.print("Introduce el teléfono: ");
        String telefono = sc.nextLine();

        String sql = "INSERT INTO cliente (nombre, email, ciudad, telefono) " +
                     "VALUES ('" + nombre + "', '" + email + "', '" + ciudad + "', '" + telefono + "')";
        try (Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println(rowsAffected > 0 ? "Cliente añadido con éxito." : "Error al añadir el cliente.");
        }
    }

    
    public static void buscarClientePorEmail(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce parte del email para buscar: ");
        String emailFragment = sc.nextLine();
        String sql = "SELECT * FROM cliente WHERE email LIKE '%" + emailFragment + "%'";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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

 
    public static void modificarCliente(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el email del cliente a modificar: ");
        String email = sc.nextLine();

        System.out.print("Introduce el nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Introduce el nuevo teléfono: ");
        String telefono = sc.nextLine();
        System.out.print("Introduce la nueva ciudad: ");
        String ciudad = sc.nextLine();

        String sql = "UPDATE cliente SET nombre = '" + nombre + "', telefono = '" + telefono + 
                     "', ciudad = '" + ciudad + "' WHERE email = '" + email + "'";
        try (Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println(rowsAffected > 0 ? "Cliente modificado con éxito." : "No se encontró el cliente.");
        }
    }

  
    public static void borrarCliente(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el email del cliente a borrar: ");
        String email = sc.nextLine();
        String sql = "DELETE FROM cliente WHERE email = '" + email + "'";
        try (Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println(rowsAffected > 0 ? "Cliente borrado con éxito." : "No se encontró el cliente.");
        }
    }
    public static void rankingClientes(Connection conn) throws SQLException {
    
    String sql = """
        SELECT 
            c.nombre,
            c.email,
            COUNT(p.id) AS numero_pedidos,
            SUM(p.precio_total) AS gasto_tota
        FROM 
            cliente c
        LEFT JOIN 
            pedido p ON c.id = p.id_cliente
        GROUP BY 
            c.id
        ORDER BY 
            gasto_total DESC;
        """;

   
    try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
        System.out.println("\nRanking de Clientes:");
        System.out.println("Nombre\tEmail\tNúmero de Pedidos\tGasto Total");

       
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String email = rs.getString("email");
            int numeroPedidos = rs.getInt("numero_pedidos");
            double gastoTotal = rs.getDouble("gasto_total");

            // Imprimir cada cliente en el ranking
            System.out.println(nombre + "\t" + email + "\t" + numeroPedidos + "\t" + gastoTotal);
        }
    } catch (SQLException e) {
        System.out.println("Error al ejecutar el ranking de clientes.");
        e.printStackTrace();
    }
}


   
    public static void añadirPedido(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el ID del cliente: ");
        int clienteId = sc.nextInt();
        sc.nextLine();

        System.out.print("Introduce la fecha del pedido (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        System.out.print("Introduce el precio total del pedido: ");
        double precioTotal = sc.nextDouble();
        sc.nextLine();

        String sql = "INSERT INTO pedido (id_cliente, fecha, precio_total) VALUES (" + clienteId + ", '" + fecha + "', " + precioTotal + ")";
        try (Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println(rowsAffected > 0 ? "Pedido añadido con éxito." : "Error al añadir el pedido.");
        }
    }

 
    public static void actualizarPedido(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el ID del pedido a actualizar: ");
        int pedidoId = sc.nextInt();
        sc.nextLine();

        System.out.print("Introduce la nueva fecha del pedido (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        System.out.print("Introduce el nuevo precio total del pedido: ");
        double precioTotal = sc.nextDouble();
        sc.nextLine();

        String sql = "UPDATE pedido SET fecha = '" + fecha + "', precio_total = " + precioTotal + " WHERE id = " + pedidoId;
        try (Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println(rowsAffected > 0 ? "Pedido actualizado con éxito." : "No se encontró el pedido.");
        }
    }

    
    public static void borrarPedido(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce el ID del pedido a borrar: ");
        int pedidoId = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM pedido WHERE id = " + pedidoId;
        try (Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println(rowsAffected > 0 ? "Pedido borrado con éxito." : "No se encontró el pedido.");
        }
    }
    
    public static void iniciarSesion(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce tu email: ");
        String email = sc.nextLine();
        System.out.print("Introduce tu contraseña: ");
        String contraseña = sc.nextLine();

       
        String sql = "SELECT nombre FROM cliente WHERE email = '" + email + "' AND contraseña = '" + contraseña + "'";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                
                String nombre = rs.getString("nombre");
                System.out.println("Hola " + nombre);
            } else {
                
                System.out.println("Usuario o Contraseña erróneo");
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión.");
            e.printStackTrace();
        }
    }
    
    public static void cambiarContraseña(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Introduce tu email: ");
        String email = sc.nextLine();

        
        String sqlCheck = "SELECT COUNT(*) AS total FROM cliente WHERE email = '" + email + "'";

        try (Statement stmt = conn.createStatement(); ResultSet rsCheck = stmt.executeQuery(sqlCheck)) {
            rsCheck.next();
            int totalClientes = rsCheck.getInt("total");

            if (totalClientes == 0) {
                System.out.println("El email no existe.");
                return;
            }
        }

        
        System.out.print("Introduce tu nueva contraseña: ");
        String nuevaContraseña = sc.nextLine();

        
        String sqlUpdate = "UPDATE cliente SET contraseña = '" + nuevaContraseña + "' WHERE email = '" + email + "'";

        try (Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sqlUpdate);
            System.out.println(rowsAffected > 0 ? "Contraseña actualizada con éxito." : "Error al actualizar la contraseña.");
        } catch (SQLException e) {
            System.out.println("Error al cambiar la contraseña.");
            e.printStackTrace();
        }
    }


}
    
