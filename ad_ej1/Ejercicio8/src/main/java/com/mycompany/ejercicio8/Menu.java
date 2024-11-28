/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

import java.sql.*;
import java.util.Scanner;

public class Menu {

    private static final ProductoDAO productoDAO = new ProductoDAO();
    private static final PedidoDAO pedidoDAO = new PedidoDAO();
    private static final DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nMenú de Gestión de Tienda:");
            System.out.println("1. Añadir Producto");
            System.out.println("2. Listar Productos");
            System.out.println("3. Añadir Pedido");
            System.out.println("4. Listar Pedidos");
            System.out.println("5. Listar Detalles de un Pedido");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    añadirProducto(scanner);
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    añadirPedido(scanner);
                    break;
                case 4:
                    listarPedidos();
                    break;
                case 5:
                    listarDetallesPedido(scanner);
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }

    private static void añadirProducto(Scanner scanner) {
        System.out.print("Ingrese el ID del producto: ");
        int idProducto = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el stock del producto: ");
        int stock = scanner.nextInt();
        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();

        Producto producto = new Producto(idProducto, nombre, stock, precio);
        if (productoDAO.agregarProducto(producto)) {
            System.out.println("Producto añadido exitosamente.");
        } else {
            System.out.println("Error al añadir el producto.");
        }
    }

    private static void listarProductos() {
        productoDAO.listarProductos();
    }

    private static void añadirPedido(Scanner scanner) {
        System.out.print("Ingrese la fecha del pedido (YYYY-MM-DD): ");
        String fechaStr = scanner.nextLine();
        Date fecha = Date.valueOf(fechaStr);
        System.out.print("Ingrese el nombre del cliente: ");
        String cliente = scanner.nextLine();

        Pedido pedido = new Pedido(0, fecha, cliente);
        if (pedidoDAO.agregarPedido(pedido)) {
            System.out.println("Pedido añadido exitosamente.");
        } else {
            System.out.println("Error al añadir el pedido.");
        }
    }

    private static void listarPedidos() {
        pedidoDAO.listarPedidos();
    }

    private static void listarDetallesPedido(Scanner scanner) {
        System.out.print("Ingrese el ID del pedido: ");
        int idPedido = scanner.nextInt();
        detallePedidoDAO.listarDetallesPedido(idPedido);
    }
}


