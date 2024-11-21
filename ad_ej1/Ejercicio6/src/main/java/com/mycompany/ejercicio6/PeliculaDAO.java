/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio6;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    public List<Pelicula> listarTodas() {
        List<Pelicula> peliculas = new ArrayList<>();
        String query = "SELECT * FROM pelicula";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Pelicula pelicula = new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("anio"),
                    rs.getString("genero")
                );
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    public List<Pelicula> buscarPorTitulo(String fragmento) {
        List<Pelicula> peliculas = new ArrayList<>();
        String query = "SELECT * FROM pelicula WHERE titulo LIKE ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + fragmento + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pelicula pelicula = new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("anio"),
                    rs.getString("genero")
                );
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    public List<Pelicula> buscarPorAnio(int anio) {
        List<Pelicula> peliculas = new ArrayList<>();
        String query = "SELECT * FROM pelicula WHERE anio = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, anio);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pelicula pelicula = new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("anio"),
                    rs.getString("genero")
                );
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    public boolean agregarPelicula(Pelicula pelicula) {
        String query = "INSERT INTO pelicula (titulo, director, anio, genero) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getDirector());
            stmt.setInt(3, pelicula.getAnio());
            stmt.setString(4, pelicula.getGenero());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarPelicula(Pelicula pelicula) {
        String query = "UPDATE pelicula SET titulo = ?, director = ?, anio = ?, genero = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getDirector());
            stmt.setInt(3, pelicula.getAnio());
            stmt.setString(4, pelicula.getGenero());
            stmt.setInt(5, pelicula.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarPelicula(int id) {
        String query = "DELETE FROM pelicula WHERE id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
