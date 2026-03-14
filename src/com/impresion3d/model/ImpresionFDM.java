package com.impresion3d.model;

public class ImpresionFDM extends Pieza3D {

    public ImpresionFDM(int idPedido, String nombreCliente, double pesoGramos, double tiempoHoras) {
        super(idPedido, nombreCliente, pesoGramos, tiempoHoras);
    }

    @Override
    public double calcularPrecioFinal() {
        return (this.pesoGramos * 150) + (this.tiempoHoras * 2000);
    }
}
