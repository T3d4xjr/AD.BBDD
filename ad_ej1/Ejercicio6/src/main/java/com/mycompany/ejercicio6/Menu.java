/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio6;

import java.util.List;
import java.util.Scanner;
/*

CREATE TABLE pelicula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    director VARCHAR(255) NOT NULL,
    anio INT NOT NULL,
    genero VARCHAR(100) NOT NULL
);

INSERT INTO pelicula (titulo, director, anio, genero) VALUES
('Inception', 'Christopher Nolan', 2010, 'Ciencia Ficción'),
('Titanic', 'James Cameron', 1997, 'Drama'),
('The Dark Knight', 'Christopher Nolan', 2008, 'Acción'),
('Pulp Fiction', 'Quentin Tarantino', 1994, 'Crimen'),
('The Matrix', 'Lana Wachowski, Lilly Wachowski', 1999, 'Ciencia Ficción'),
('Forrest Gump', 'Robert Zemeckis', 1994, 'Drama'),
('The Godfather', 'Francis Ford Coppola', 1972, 'Crimen'),
('Parasite', 'Bong Joon Ho', 2019, 'Thriller'),
('The Avengers', 'Joss Whedon', 2012, 'Acción'),
('Interstellar', 'Christopher Nolan', 2014, 'Ciencia Ficción');


*/

public class Menu {
    public static void main(String[] args) {
        PeliculaDAO peliculaDAO = new PeliculaDAO();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Menú de Gestión de Películas ===");
            System.out.println("1. Listar todas las películas");
            System.out.println("2. Buscar películas por título");
            System.out.println("3. Buscar películas por año");
            System.out.println("4. Añadir una película");
            System.out.println("5. Actualizar una película");
            System.out.println("6. Borrar una película");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea restante

            switch (opcion) {
                case 1:
                    System.out.println("\nLista de Películas:");
                    List<Pelicula> peliculas = peliculaDAO.listarTodas();
                    for (Pelicula p : peliculas) {
                        System.out.println(p);
                    }
                    break;

                case 2:
                    System.out.print("Introduce un fragmento del título: ");
                    String fragmentoTitulo = scanner.nextLine();
                    List<Pelicula> peliculasTitulo = peliculaDAO.buscarPorTitulo(fragmentoTitulo);
                    System.out.println("\nResultados de búsqueda por título:");
                    for (Pelicula p : peliculasTitulo) {
                        System.out.println(p);
                    }
                    break;

                case 3:
                    System.out.print("Introduce el año de las películas a buscar: ");
                    int anioBusqueda = scanner.nextInt();
                    List<Pelicula> peliculasAnio = peliculaDAO.buscarPorAnio(anioBusqueda);
                    System.out.println("\nResultados de búsqueda por año:");
                    for (Pelicula p : peliculasAnio) {
                       System.out.println(p);
                    }
                    break;

                case 4:
                    System.out.print("Introduce el título de la película: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Introduce el director de la película: ");
                    String director = scanner.nextLine();
                    System.out.print("Introduce el año de la película: ");
                    int anio = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea restante
                    System.out.print("Introduce el género de la película: ");
                    String genero = scanner.nextLine();

                    Pelicula nueva = new Pelicula(0, titulo, director, anio, genero);
                    if (peliculaDAO.agregarPelicula(nueva)) {
                        System.out.println("Película añadida con éxito.");
                    } else {
                        System.out.println("Error al añadir la película.");
                    }
                    break;

                case 5:
                    System.out.print("Introduce el ID de la película a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea restante
                    System.out.print("Introduce el nuevo título: ");
                    String nuevoTitulo = scanner.nextLine();
                    System.out.print("Introduce el nuevo director: ");
                    String nuevoDirector = scanner.nextLine();
                    System.out.print("Introduce el nuevo año: ");
                    int nuevoAnio = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea restante
                    System.out.print("Introduce el nuevo género: ");
                    String nuevoGenero = scanner.nextLine();

                    Pelicula actualizada = new Pelicula(idActualizar, nuevoTitulo, nuevoDirector, nuevoAnio, nuevoGenero);
                    if (peliculaDAO.actualizarPelicula(actualizada)) {
                        System.out.println("Película actualizada con éxito.");
                    } else {
                        System.out.println("Error al actualizar la película.");
                    }
                    break;

                case 6:
                    System.out.print("Introduce el ID de la película a borrar: ");
                    int idBorrar = scanner.nextInt();
                    if (peliculaDAO.eliminarPelicula(idBorrar)) {
                        System.out.println("Película borrada con éxito.");
                    } else {
                        System.out.println("Error al borrar la película.");
                    }
                    break;

                case 7:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
                    break;
            }
        } while (opcion != 7);

        scanner.close();
    }
}
