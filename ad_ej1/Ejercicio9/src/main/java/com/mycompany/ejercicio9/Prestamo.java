/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio9;

/**
 *
 * @author tedax
 */
import java.sql.Date;

public class Prestamo {
    private int id;
    private int idLibro;
    private String lector;
    private String fechaPrestamo;
    private String estado;
    private Libro libro;

    public Prestamo(int id, Libro l, String lector, String fechaPrestamo, String estado) {
        this.id = id;
        this.libro = l;
        this.lector = lector;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", idLibro=" + idLibro + ", lector=" + lector + ", fechaPrestamo=" + fechaPrestamo + ", estado=" + estado + '}';
    }
    
}

