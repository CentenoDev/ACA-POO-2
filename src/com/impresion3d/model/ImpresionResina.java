package com.impresion3d.model;

public class ImpresionResina extends Pieza3D implements IPostProcesado {

    public ImpresionResina(int idPedido, String nombreCliente, double pesoGramos, double tiempoHoras) {
        super(idPedido, nombreCliente, pesoGramos, tiempoHoras);
    }

    @Override
    public double calcularPrecioFinal() {
        return (this.pesoGramos * 300) + (this.tiempoHoras * 3000) + calcularCostoLimpieza() + calcularCostoPintura();
    }

    @Override
    public double calcularCostoLimpieza() {
        return 5000; 
    }

    @Override
    public double calcularCostoPintura() {
        return 15000; 
    }
}