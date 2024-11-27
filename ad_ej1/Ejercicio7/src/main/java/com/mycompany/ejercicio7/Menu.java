/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio7;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * CREATE TABLE estudiantes ( id INT AUTO_INCREMENT PRIMARY KEY, nombre
 * VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL UNIQUE, telefono
 * VARCHAR(15), direccion VARCHAR(255) );
 *
 * CREATE TABLE asignaturas ( id INT AUTO_INCREMENT PRIMARY KEY, nombre
 * VARCHAR(100) NOT NULL UNIQUE, curso INT NOT NULL, horas INT NOT NULL );
 *
 * CREATE TABLE matriculas ( id_estudiante INT NOT NULL, id_asignatura INT NOT
 * NULL, año INT NOT NULL, estado ENUM('cursando', 'superada', 'no superada',
 * 'cancelada') NOT NULL, calificacion DECIMAL(5,2), PRIMARY KEY (id_estudiante,
 * id_asignatura, año), FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id),
 * FOREIGN KEY (id_asignatura) REFERENCES asignaturas(id) );
 *
 * -- Datos de ejemplo INSERT INTO estudiantes (nombre, email, telefono,
 * direccion) VALUES ('Juan Pérez', 'juan.perez@mail.com', '123456789', 'Calle
 * A, Ciudad B'), ('Ana López', 'ana.lopez@mail.com', '987654321', 'Calle X,
 * Ciudad Y');
 *
 * INSERT INTO asignaturas (nombre, curso, horas) VALUES ('Matemáticas', 1,
 * 120), ('Historia', 2, 90);
 *
 * INSERT INTO matriculas (id_estudiante, id_asignatura, año, estado,
 * calificacion) VALUES (1, 1, 2023, 'cursando', NULL), (2, 2, 2023, 'cursando',
 * NULL);
 *
 */
public class Menu {

    private static final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private static final AsignaturaDAO asignaturaDAO = new AsignaturaDAO();
    private static final MatriculaDAO matriculaDAO = new MatriculaDAO();

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nMenú de Gestión de Ciclo:");
            System.out.println("1. Añadir estudiante");
            System.out.println("2. Añadir asignatura");
            System.out.println("3. Matricular estudiante en asignatura");
            System.out.println("4. Añadir calificación");
            System.out.println("5. Ver historial de matrícula de un estudiante");
            System.out.println("6. Cancelar matrícula");
            System.out.println("7. Salir");
            System.out.println("8. Opcion añadir 3 estudiantes");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    añadirEstudiante(scanner);
                    break;
                case 2:
                    añadirAsignatura(scanner);
                    break;
                case 3:
                    matricularEstudiante(scanner);
                    break;
                case 4:
                    añadirCalificacion(scanner);
                    break;
                case 5:
                    verHistorial(scanner);
                    break;
                case 6:
                    cancelarMatricula(scanner);
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                case 8:
                    introduce3Estudiante(scanner);
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 8);
    }

    private static void introduce3Estudiante(Scanner scanner) throws SQLException {
        estudianteDAO.introduceEstudiante();
    }
    private static void añadirEstudiante(Scanner scanner) {
        System.out.print("Nombre del estudiante: ");
        String nombre = scanner.nextLine();
        System.out.print("Email del estudiante: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono del estudiante: ");
        String telefono = scanner.nextLine();
        System.out.print("Dirección del estudiante: ");
        String direccion = scanner.nextLine();

        Estudiante estudiante = new Estudiante(0, nombre, email, telefono, direccion);
        if (estudianteDAO.agregarEstudiante(estudiante)) {
            System.out.println("Estudiante añadido exitosamente.");
        } else {
            System.out.println("Error al añadir el estudiante.");
        }
    }

    private static void añadirAsignatura(Scanner scanner) {
        System.out.print("Nombre de la asignatura: ");
        String nombre = scanner.nextLine();
        System.out.print("Curso: ");
        int curso = scanner.nextInt();
        System.out.print("Horas: ");
        int horas = scanner.nextInt();

        Asignatura asignatura = new Asignatura(0, nombre, curso, horas);
        if (asignaturaDAO.agregarAsignatura(asignatura)) {
            System.out.println("Asignatura añadida exitosamente.");
        } else {
            System.out.println("Error al añadir la asignatura.");
        }
    }

    private static void matricularEstudiante(Scanner scanner) {
        System.out.print("Email del estudiante: ");
        String email = scanner.nextLine();
        System.out.print("Nombre de la asignatura: ");
        String nombreAsignatura = scanner.nextLine();
        System.out.print("Año: ");
        int año = scanner.nextInt();

        if (matriculaDAO.matricularEstudiante(email, nombreAsignatura, año)) {
            System.out.println("Estudiante matriculado exitosamente.");
        } else {
            System.out.println("Error al matricular al estudiante.");
        }
    }

    private static void añadirCalificacion(Scanner scanner) {
        System.out.print("Email del estudiante: ");
        String email = scanner.nextLine();
        System.out.print("Nombre de la asignatura: ");
        String nombreAsignatura = scanner.nextLine();
        System.out.print("Año: ");
        int año = scanner.nextInt();
        System.out.print("Calificación: ");
        double calificacion = scanner.nextDouble();

        if (matriculaDAO.actualizarCalificacion(email, nombreAsignatura, año, calificacion)) {
            System.out.println("Calificación añadida exitosamente.");
        } else {
            System.out.println("Error al añadir la calificación.");
        }
    }

    private static void verHistorial(Scanner scanner) {
        System.out.print("Email del estudiante: ");
        String email = scanner.nextLine();

        List<Matricula> matricula = matriculaDAO.historialPorEstudiante(email);

        if (matricula.isEmpty()) {
            System.out.println("No se encontró historial para el estudiante.");
        } else {
            System.out.println("Historial de matrículas:");
            for (Matricula m : matricula) {
                
                System.out.println(m);
            }
        }

    }

    private static void cancelarMatricula(Scanner scanner) {
        System.out.print("Email del estudiante: ");
        String email = scanner.nextLine();
        System.out.print("Nombre de la asignatura: ");
        String nombreAsignatura = scanner.nextLine();
        System.out.print("Año: ");
        int año = scanner.nextInt();

        if (matriculaDAO.cancelarMatricula(email, nombreAsignatura, año)) {
            System.out.println("Matrícula cancelada exitosamente.");
        } else {
            System.out.println("Error al cancelar la matrícula.");
        }
    }

}
