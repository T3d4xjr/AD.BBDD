/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio7;

import java.sql.*;

public class AsignaturaDAO {
    public boolean agregarAsignatura(Asignatura asignatura) {
        String query = "INSERT INTO asignaturas (nombre, curso, horas) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, asignatura.getNombre());
            stmt.setInt(2, asignatura.getCurso());
            stmt.setInt(3, asignatura.getHoras());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
