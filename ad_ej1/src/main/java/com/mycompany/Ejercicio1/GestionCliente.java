package com.mycompany.Ejercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionCliente {

    private static final String URL = "jdbc:mysql://localhost:3306/ad_ej1";
    private static final String USER = "2dam";
    private static final String PASSWORD = "2dam";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n*** Menú GestionCliente ***");
            System.out.println("1. Listar todos los clientes");
            System.out.println("2. Añadir un cliente");
            System.out.println("3. Buscar cliente por fragmento de email");
            System.out.println("4. Modificar datos de un cliente");
            System.out.println("5. Borrar un cliente por email");
            System.out.println("6. Ranking de clientes");
            System.out.println("7. Añadir un pedido");
            System.out.println("8. Actualizar un pedido");
            System.out.println("9. Borrar un pedido");
            System.out.println("10. Iniciar Sesion");
             System.out.println("11. Cambiar contraseña");
            System.out.println("12. Salir del programa");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                switch (opcion) {
                    case 1:
                        ConsultasCliente.listarClientes(conn);
                        break;
                    case 2:
                        ConsultasCliente.añadirCliente(conn, sc);
                        break;
                    case 3:
                        ConsultasCliente.buscarClientePorEmail(conn, sc);
                        break;
                    case 4:
                        ConsultasCliente.modificarCliente(conn, sc);
                        break;
                    case 5:
                        ConsultasCliente.borrarCliente(conn, sc);
                        break;
                    case 6:
                        ConsultasCliente.rankingClientes(conn);
                        break;
                    case 7:
                        ConsultasCliente.añadirPedido(conn, sc);
                        break;
                    case 8:
                        ConsultasCliente.actualizarPedido(conn, sc);
                        break;
                    case 9:
                        ConsultasCliente.borrarPedido(conn, sc);
                        break;
                    case 10:
                        ConsultasCliente.iniciarSesion(conn, sc);
                        break;
                    case 11:
                        ConsultasCliente.cambiarContraseña(conn, sc);
                        break;
                    case 12:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos");
                e.printStackTrace();
            }
        } while (opcion != 12);

        sc.close();
    }
}
