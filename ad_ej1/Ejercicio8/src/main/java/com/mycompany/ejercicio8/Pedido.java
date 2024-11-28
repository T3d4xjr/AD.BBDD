/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio8;

import java.util.Date;

public class Pedido {
    private int id;
    private Date fecha;
    private String cliente;

    public Pedido(int id, Date fecha, String cliente) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   

    public Date getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    

    @Override
    public String toString() {
        return id + ", " + fecha + ", " + cliente + ", ";
    }

   

}
