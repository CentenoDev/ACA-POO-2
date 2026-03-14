package com.impresion3d.model;

public abstract class Pieza3D {
    protected int idPedido;
    protected String nombreCliente;
    protected double pesoGramos;
    protected double tiempoHoras;

    public Pieza3D(int idPedido, String nombreCliente, double pesoGramos, double tiempoHoras) {
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
        this.pesoGramos = pesoGramos;
        this.tiempoHoras = tiempoHoras;
    }

    public abstract double calcularPrecioFinal();

    public int getIdPedido() { return idPedido; }
    public String getNombreCliente() { return nombreCliente; }
    public double getPesoGramos() { return pesoGramos; }
    public double getTiempoHoras() { return tiempoHoras; }
}