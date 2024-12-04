/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejericico10;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tedax
 */
public class EmpleadoDAO {
    public boolean agregarEmpleado(Empleado empleado) {
        String query = "INSERT INTO empleado (nombre, dni, departamento, sueldo, fecha_contratacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getDni());
            stmt.setString(3, empleado.getDepartamento());
            stmt.setDouble(4, empleado.getSueldo());
            stmt.setString(5, empleado.getFecha_contratacion());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        empleado.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean modificarEmpleado(Empleado empleado) {
        String query = "UPDATE empleado SET nombre = ?, departamento = ?, sueldo = ? WHERE id = ?";
        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getDepartamento());
            stmt.setDouble(3, empleado.getSueldo());
            stmt.setInt(4, empleado.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean despedirEmpleado(int idEmpleado, String fechaFinalizacion) {
        String query = "UPDATE empleado SET fecha_finalizacion = ? WHERE id = ?";
        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, fechaFinalizacion);
            stmt.setInt(2, idEmpleado);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Empleado> listarEmpleadosActivos() {
        String query = "SELECT * FROM empleado WHERE fecha_finalizacion IS NULL";
        List<Empleado> empleados = new ArrayList<>();
        try (Connection connection = ConexionBD.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("departamento"),
                        rs.getDouble("sueldo"),
                        rs.getString("fecha_contratacion"),
                        rs.getString("fecha_finalizacion")
                );
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    public List<Empleado> listarEmpleadosDespedidos() {
        String query = "SELECT * FROM empleado WHERE fecha_finalizacion IS NOT NULL";
        List<Empleado> empleados = new ArrayList<>();
        try (Connection connection = ConexionBD.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("departamento"),
                        rs.getDouble("sueldo"),
                        rs.getString("fecha_contratacion"),
                        rs.getString("fecha_finalizacion")
                );
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }
}
