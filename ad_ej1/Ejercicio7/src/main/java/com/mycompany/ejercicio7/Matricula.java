/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio7;

/**
 *
 * @author tedax
 */
public class Matricula {
    private int idEstudiante;
    private int idAsignatura;
    private int año;
    private String estado;
    private Double calificacion;

    public Matricula(int idEstudiante, int idAsignatura, int año, String estado, Double calificacion) {
        this.idEstudiante = idEstudiante;
        this.idAsignatura = idAsignatura;
        this.año = año;
        this.estado = estado;
        this.calificacion = calificacion;
    }
    
    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "Matricula{" + "idEstudiante=" + idEstudiante + ", idAsignatura=" + idAsignatura + ", a\u00f1o=" + año + ", estado=" + estado + ", calificacion=" + calificacion + '}';
    }

    
}