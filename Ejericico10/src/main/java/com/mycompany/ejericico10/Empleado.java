/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejericico10;

/**
 *
 * @author tedax
 */
public class Empleado {
    int id;
    String nombre;
    String dni;
    String departamento;
    Double sueldo;
    String fecha_contratacion;
    String fecha_finalizaacion;

    public Empleado(int id, String nombre, String dni, String departamento, Double sueldo, String fecha_contratacion, String fecha_finalizaacion) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.departamento = departamento;
        this.sueldo = sueldo;
        this.fecha_contratacion = fecha_contratacion;
        this.fecha_finalizaacion = fecha_finalizaacion;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public String getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(String fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    public String getFecha_finalizaacion() {
        return fecha_finalizaacion;
    }

    public void setFecha_finalizaacion(String fecha_finalizaacion) {
        this.fecha_finalizaacion = fecha_finalizaacion;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombre=" + nombre + ", dni=" + dni + ", departamento=" + departamento + ", sueldo=" + sueldo + ", fecha_contratacion=" + fecha_contratacion + ", fecha_finalizaacion=" + fecha_finalizaacion + '}';
    }
    
    
    
    
}
