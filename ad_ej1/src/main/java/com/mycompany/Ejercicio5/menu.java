/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Ejercicio5;

import java.util.List;
import java.util.Scanner;

/*
CREATE TABLE empleado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    puesto VARCHAR(50) NOT NULL,
    salario DOUBLE NOT NULL,
    fecha_ingreso DATE NOT NULL
);

INSERT INTO empleado (nombre, puesto, salario, fecha_ingreso) VALUES 
('Juan Pérez', 'Gerente', 55000.00, '2020-01-15'),
('Ana López', 'Desarrollador', 45000.00, '2021-05-20'),
('Carlos Gómez', 'Analista', 40000.00, '2019-10-01'),
('Laura Martínez', 'Diseñadora', 38000.00, '2022-03-12'),
('Miguel Torres', 'Soporte Técnico', 30000.00, '2020-08-25');

*/
public class menu {
    public static void main(String[] args) {
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nGestión de Empleados");
            System.out.println("1. Listar todos los empleados");
            System.out.println("2. Añadir un empleado");
            System.out.println("3. Actualizar un empleado");
            System.out.println("4. Borrar un empleado");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("\nLista de Empleados:");
                    List<Empleado> empleados = empleadoDAO.obtenerTodos();
                    for (Empleado e : empleados) {
                        System.out.printf("ID: %d, Nombre: %s, Puesto: %s, Salario: %.2f, Fecha de Ingreso: %s%n",
                                e.getId(), e.getNombre(), e.getPuesto(), e.getSalario(), e.getFechaIngreso());
                    }
                    break;

                case 2:
                    System.out.print("Nombre: ");
                    String nombre = scanner.next();
                    System.out.print("Puesto: ");
                    String puesto = scanner.next();
                    System.out.print("Salario: ");
                    double salario = scanner.nextDouble();
                    System.out.print("Fecha de ingreso (YYYY-MM-DD): ");
                    String fechaIngreso = scanner.next();

                    Empleado nuevo = new Empleado(0, nombre, puesto, salario, fechaIngreso);
                    empleadoDAO.añadirEmpleado(nuevo);
                    System.out.println("Empleado añadido.");
                    break;

                case 3:
                    System.out.print("ID del empleado a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.next();
                    System.out.print("Nuevo puesto: ");
                    String nuevoPuesto = scanner.next();
                    System.out.print("Nuevo salario: ");
                    double nuevoSalario = scanner.nextDouble();
                    System.out.print("Nueva fecha de ingreso (YYYY-MM-DD): ");
                    String nuevaFechaIngreso = scanner.next();

                    Empleado actualizado = new Empleado(idActualizar, nuevoNombre, nuevoPuesto, nuevoSalario, nuevaFechaIngreso);
                    empleadoDAO.actualizarEmpleado(actualizado);
                    System.out.println("Empleado actualizado.");
                    break;

                case 4:
                    System.out.print("ID del empleado a borrar: ");
                    int idBorrar = scanner.nextInt();
                    empleadoDAO.borrarEmpleado(idBorrar);
                    System.out.println("Empleado borrado.");
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 5);

        scanner.close();
    }
}

