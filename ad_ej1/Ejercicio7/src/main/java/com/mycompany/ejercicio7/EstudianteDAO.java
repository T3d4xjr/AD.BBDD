/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio7;

import java.sql.*;

public class EstudianteDAO {
    public boolean agregarEstudiante(Estudiante estudiante) {
        String query = "INSERT INTO estudiantes (nombre, email, telefono, direccion) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getEmail());
            stmt.setString(3, estudiante.getTelefono());
            stmt.setString(4, estudiante.getDireccion());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void introduceEstudiante() throws SQLException{
        
        String query = "INSERT INTO estudiantes (nombre, email, telefono, direccion) VALUES (?, ?, ?, ?)";
        Connection conn = ConexionBD.getConnection();
       try (
             PreparedStatement stmt = conn.prepareStatement(query)) {
           conn.setAutoCommit(false);
           

            stmt.setString(1,"Nombre1");
            stmt.setString(2,"n@gmail.com");
            stmt.setString(3, "666");
            stmt.setString(4,"c/");
            stmt.executeUpdate();
            stmt.setString(1,"Nombre1");
            stmt.setString(2,"hola@gmail.com");
            stmt.setString(3, "666");
            stmt.setString(4,"c/");
            stmt.executeUpdate();
            stmt.setString(1,"Nombre1");
            stmt.setString(2,"n@gmail.com");
            stmt.setString(3, "666");
            stmt.setString(4,"c/");
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
           conn.rollback();
        }finally{
           conn.setAutoCommit(true);
       }
    }
}
