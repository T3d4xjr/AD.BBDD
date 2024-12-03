/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author tedax
 */
public class Ejerc8Controller {
    private static ProductoDAO productoDAO = new ProductoDAO();
private static PedidoDAO pedidoDAO = new PedidoDAO();

private static Scanner scanner = new Scanner(System.in);

public static void agregarProducto() {
    System.out.println("Ingrese el nombre del producto");
    String nombreProducto = scanner.nextLine();
    System.out.println("Ingrese el stock del producto");
    int stockProducto = scanner.nextInt();
    scanner.nextLine();
    System.out.println("Ingrese el precio del producto");
    float precioProducto = scanner.nextFloat();

    Producto producto = new Producto(nombreProducto, stockProducto, precioProducto);
    productoDAO.agregarProducto(producto);
    System.out.println("El producto " + nombreProducto + " ha sido añadido correctamente");
}

public static void listarProductos() {
    for (Producto producto : productoDAO.listaProductos()) {
        // Cambia esto para imprimir los atributos específicos del producto
        System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", Stock: " + producto.getStock() + ", Precio: " + producto.getPrecio() + "€");
    }
}


public static void registrarPedido() throws SQLException {
    System.out.println("Ingrese la fecha del pedido (YYYY-MM-DD):");
    String fechaStr = scanner.nextLine();
    

    System.out.println("Ingrese el nombre del cliente:");
    String cliente = scanner.nextLine();

    System.out.println("¿Cuántos productos va a comprar?");
    int cantidadProductos = scanner.nextInt();
    scanner.nextLine();  

    pedidoDAO.registrarPedido(fechaStr, cliente, cantidadProductos);
}

public static void listarPedidos() {
    for (Pedido pedidos : pedidoDAO.listarPedidos()) {
            System.out.println(pedidos);
        }
}

public static void listarDetallesPedido() {
    System.out.println("Introduce el id del pedido");
    int idPedido = scanner.nextInt();
    pedidoDAO.listarDetallesPedido(idPedido);
}

}
