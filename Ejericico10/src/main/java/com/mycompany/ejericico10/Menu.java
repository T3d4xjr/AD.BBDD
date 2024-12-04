/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejericico10;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tedax
 */
public class Menu {
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
        int opcion;
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        ProyectoDAO proyectoDAO = new ProyectoDAO();

        do {
            System.out.println("\n=== Gestión de Empleados y Proyectos ===");
            System.out.println("1. Añadir empleado");
            System.out.println("2. Modificar empleado");
            System.out.println("3. Despedir empleado");
            System.out.println("4. Listar empleados activos");
            System.out.println("5. Listar empleados despedidos");
            System.out.println("6. Añadir proyecto");
            System.out.println("7. Añadir un proyecto con empleados");
            System.out.println("8. Modificar un proyecto");
            System.out.println("9. Añadir empleado a proyecto");
            System.out.println("10. Añadir varios empleados a un proyecto");
            System.out.println("11. Eliminar un empleado de un proyecto");
            System.out.println("12. Listar todos los proyectos futuros");
            System.out.println("13. Listar todos los proyectos pasados");
            System.out.println("14. Listar todos los proyectos activos");
            System.out.println("15. Listar los detalles de un proyecto");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    anadirEmpleado(scanner, empleadoDAO);
                    break;
                case 2:
                    // modificarEmpleado(scanner, empleadoDAO);
                    break;
                case 3:
                    despedirEmpleado(scanner, empleadoDAO);
                    break;
                case 4:
                    listarEmpleadosActivos(empleadoDAO);
                    break;
                case 5:
                    listarEmpleadosDespedidos(empleadoDAO);
                    break;
                case 6:
                    anadirProyecto(scanner);
                    break;
                case 7:
                    anadirProyectoConEmpleados(scanner);
                    break;
                case 8:
                    modificarProyecto(scanner);
                    break;
                case 9:
                    anadirEmpleadoAProyecto(scanner);
                    break;
                case 10:
                    anadirVariosEmpleadosAProyecto(scanner);
                    break;
                case 11:
                    eliminarEmpleadoDeProyecto(scanner);
                    break;
                case 12:
                    listarProyectosFuturos();
                    break;
                case 13:
                    listarProyectosPasados();
                    break;
                case 14:
                    listarProyectosActivos();
                    break;
                case 15:
                    listarDetallesProyecto(scanner);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 0);

        scanner.close();
    }
        // Método para añadir un empleado
    public static void anadirEmpleado(Scanner scanner, EmpleadoDAO empleadoDAO) {
        System.out.print("Ingrese el nombre del empleado: ");
        String nombre = scanner.next();
        System.out.print("Ingrese el DNI del empleado: ");
        String dni = scanner.next();
        System.out.print("Ingrese el departamento del empleado: ");
        String departamento = scanner.next();
        System.out.print("Ingrese el sueldo del empleado: ");
        double sueldo = scanner.nextDouble();
        System.out.print("Ingrese la fecha de contratación del empleado (YYYY-MM-DD): ");
        String fechaContratacion = scanner.next();

        Empleado empleado = new Empleado(0, nombre, dni, departamento, sueldo, fechaContratacion, "NULL");

        // Intentamos agregar el empleado y mostramos un mensaje basado en el resultado
        if (empleadoDAO.agregarEmpleado(empleado)) {
            System.out.println("Empleado añadido exitosamente.");
        } else {
            System.out.println("Error al añadir el empleado.");
        }
    }


    /**
    // Método para modificar un empleado
    public static void modificarEmpleado(Scanner scanner, EmpleadoDAO empleadoDAO) {
        System.out.print("Ingrese el ID del empleado a modificar: ");
        int id = scanner.nextInt();

        System.out.print("Ingrese el nuevo nombre del empleado: ");
        String nombre = scanner.next();
        System.out.print("Ingrese el nuevo departamento del empleado: ");
        String departamento = scanner.next();
        System.out.print("Ingrese el nuevo sueldo del empleado: ");
        double sueldo = scanner.nextDouble();

        Empleado empleado = new Empleado(id, nombre, departamento, sueldo);
        

        if (empleadoDAO.modificarEmpleado(empleado)) {
            System.out.println("Empleado modificado exitosamente.");
        } else {
            System.out.println("Error al modificar el empleado.");
        }
    }
*/
    // Método para despedir a un empleado
    public static void despedirEmpleado(Scanner scanner, EmpleadoDAO empleadoDAO) {
        System.out.print("Ingrese el ID del empleado a despedir: ");
        int id = scanner.nextInt();
        System.out.print("Ingrese la fecha de finalización del contrato (YYYY-MM-DD): ");
        String fechaFinalizacion = scanner.next();

        if (empleadoDAO.despedirEmpleado(id, fechaFinalizacion)) {
            System.out.println("Empleado despedido exitosamente.");
        } else {
            System.out.println("Error al despedir el empleado.");
        }
    }

    // Método para listar los empleados activos
    public static void listarEmpleadosActivos(EmpleadoDAO empleadoDAO) {
        List<Empleado> empleadosActivos = empleadoDAO.listarEmpleadosActivos();
        if (empleadosActivos.isEmpty()) {
            System.out.println("No hay empleados activos.");
        } else {
            System.out.println("Empleados activos:");
            for (Empleado empleado : empleadosActivos) {
                System.out.println(empleado);
            }
        }
    }

    // Método para listar los empleados despedidos
    public static void listarEmpleadosDespedidos(EmpleadoDAO empleadoDAO) {
        List<Empleado> empleadosDespedidos = empleadoDAO.listarEmpleadosDespedidos();
        if (empleadosDespedidos.isEmpty()) {
            System.out.println("No hay empleados despedidos.");
        } else {
            System.out.println("Empleados despedidos:");
            for (Empleado empleado : empleadosDespedidos) {
                System.out.println(empleado);
            }
        }
    }

    public static void anadirProyecto(Scanner scanner){
         
    }
    public static void anadirProyectoConEmpleados(Scanner scanner){
        
    }
    public static void modificarProyecto(Scanner scanner){
        
    }
    public static void anadirEmpleadoAProyecto(Scanner scanner){
        
    }
    public static void anadirVariosEmpleadosAProyecto(Scanner scanner){
        
    }
    public static void eliminarEmpleadoDeProyecto(Scanner scanner){
        
    }
    public static void listarProyectosFuturos(){
        
    }
    public static void listarProyectosPasados(){
        
    }
    public static void listarProyectosActivos(){
        
    }
    public static void listarDetallesProyecto(Scanner scanner){
        
    }
   
    
}

