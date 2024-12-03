/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

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
public class ProductoDAO {
    
    public void agregarProducto(Producto producto){
        String sqlAgregarProducto = "INSERT INTO producto(nombre, stock, precio) VALUES (?, ?, ?)";
        try(Connection conn = ConexionBD.ConexionBD())
        {
            try(PreparedStatement ps = conn.prepareStatement(sqlAgregarProducto))
            {
                ps.setString(1, producto.getNombre());
                ps.setInt(2, producto.getStock());
                ps.setFloat(3, producto.getPrecio());
                
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al agregar un producto " + ex.getMessage());
        }
    }
    
    public List <Producto> listaProductos()
    {
        List <Producto> productos = new ArrayList<>();
        String sqlListarProductos = "SELECT * FROM producto;";
        try(Connection conexion = ConexionBD.ConexionBD())
        {
            try(Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sqlListarProductos))
            {
                System.out.println("Productos (ID, nombre, stock, precio)");
                while(rs.next())
                {
                    int idProducto = rs.getInt("id");
                    String nombreProducto = rs.getString("nombre");
                    int stockProducto = rs.getInt("stock");
                    float precioProducto = rs.getFloat("precio");
                    
                    Producto producto = new Producto();
                    producto.setId(idProducto);
                    producto.setNombre(nombreProducto);
                    producto.setStock(stockProducto);
                    producto.setPrecio(precioProducto);
                    
                    productos.add(producto);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar " + ex.getMessage());
        }
        return productos;
    }
}

