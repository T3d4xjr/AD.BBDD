/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejericico10;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {

    // Añadir un proyecto
    public boolean agregarProyecto(Proyecto proyecto) {
        String query = "INSERT INTO proyecto (nombre, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getFecha_inicio());
            stmt.setString(3, proyecto.getFecha_fin());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        proyecto.setId(generatedKeys.getInt(1)); // Obtener el ID generado
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

        //Añadir proyectos con empleados.
    

    // Modificar un proyecto
    public boolean modificarProyecto(Proyecto proyecto) {
        String query = "UPDATE proyecto SET nombre = ?, fecha_inicio = ?, fecha_fin = ? WHERE id = ?";
        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getFecha_inicio());
            stmt.setString(3, proyecto.getFecha_fin());
            stmt.setInt(4, proyecto.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Añadir un empleado a un proyecto
    public boolean agregarEmpleadoAProyecto(int idProyecto, int idEmpleado) {
        String query = "INSERT INTO proyecto_empleado (id_proyecto, id_empleado) VALUES (?, ?)";
        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProyecto);
            stmt.setInt(2, idEmpleado);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Añadir varios empleados a un proyecto
    

    // Eliminar un empleado de un proyecto
    public boolean eliminarEmpleadoDeProyecto(int idProyecto, int idEmpleado) {
        String query = "DELETE FROM proyecto_empleado WHERE id_proyecto = ? AND id_empleado = ?";
        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProyecto);
            stmt.setInt(2, idEmpleado);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Listar todos los proyectos futuros
    public List<Proyecto> listarProyectosFuturos() {
        String query = "SELECT * FROM proyecto WHERE fecha_inicio > CURRENT_DATE";
        List<Proyecto> proyectos = new ArrayList<>();
        try (Connection connection = ConexionBD.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Proyecto proyecto = new Proyecto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("fecha_inicio"),
                        rs.getString("fecha_fin")
                );
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    // Listar todos los proyectos pasados
    public List<Proyecto> listarProyectosPasados() {
        String query = "SELECT * FROM proyecto WHERE fecha_fin < CURRENT_DATE";
        List<Proyecto> proyectos = new ArrayList<>();
        try (Connection connection = ConexionBD.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Proyecto proyecto = new Proyecto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("fecha_inicio"),
                        rs.getString("fecha_fin")
                );
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    // Listar todos los proyectos activos
    public List<Proyecto> listarProyectosActivos() {
        String query = "SELECT * FROM proyecto WHERE fecha_inicio <= CURRENT_DATE AND fecha_fin >= CURRENT_DATE";
        List<Proyecto> proyectos = new ArrayList<>();
        try (Connection connection = ConexionBD.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Proyecto proyecto = new Proyecto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("fecha_inicio"),
                        rs.getString("fecha_fin")
                );
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    // Listar detalles de un proyecto
    public Proyecto obtenerDetallesProyecto(int idProyecto) {
        String query = "SELECT * FROM proyecto WHERE id = ?";
        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProyecto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Proyecto proyecto = new Proyecto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("fecha_inicio"),
                            rs.getString("fecha_fin")
                    );
                    return proyecto;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
