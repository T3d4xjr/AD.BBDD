/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio7;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class MatriculaDAO {
    public boolean matricularEstudiante(String email, String nombreAsignatura, int año) {
        String query = "INSERT INTO matriculas (id_estudiante, id_asignatura, año, estado, calificacion) " +
                       "SELECT e.id, a.id, ?, 'cursando', NULL FROM estudiantes e, asignaturas a " +
                       "WHERE e.email = ? AND a.nombre = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, año);
            stmt.setString(2, email);
            stmt.setString(3, nombreAsignatura);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarCalificacion(String email, String nombreAsignatura, int año, double calificacion) {
        String query = "UPDATE matriculas SET calificacion = ?, estado = ? WHERE id_estudiante = " +
                       "(SELECT id FROM estudiantes WHERE email = ?) AND id_asignatura = " +
                       "(SELECT id FROM asignaturas WHERE nombre = ?) AND año = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String estado = calificacion >= 5.0 ? "superada" : "no superada";
            stmt.setDouble(1, calificacion);
            stmt.setString(2, estado);
            stmt.setString(3, email);
            stmt.setString(4, nombreAsignatura);
            stmt.setInt(5, año);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     public List<Matricula> historialPorEstudiante(String email) {
    List<Matricula> matriculas = new ArrayList<>();
    String query = "SELECT m.id_estudiante, m.id_asignatura, m.año, m.estado, m.calificacion " +
                   "FROM matriculas m " +
                   "JOIN estudiantes e ON m.id_estudiante = e.id " +
                   "WHERE e.email = ? " +
                   "ORDER BY m.año DESC";

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Matricula matricula = new Matricula(
                    rs.getInt("id_estudiante"), 
                    rs.getInt("id_asignatura"), 
                    rs.getInt("año"), 
                    rs.getString("estado"), 
                    rs.getDouble("calificacion")
            );

            matriculas.add(matricula);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return matriculas;
}


    public boolean cancelarMatricula(String email, String nombreAsignatura, int año) {
        String query = "UPDATE matriculas SET estado = 'cancelada' WHERE id_estudiante = " +
                       "(SELECT id FROM estudiantes WHERE email = ?) AND id_asignatura = " +
                       "(SELECT id FROM asignaturas WHERE nombre = ?) AND año = ? AND estado = 'cursando'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, nombreAsignatura);
            stmt.setInt(3, año);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
