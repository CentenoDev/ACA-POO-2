package com.impresion3d.exceptions;

// EXCEPCIONES: Creamos nuestra propia regla de error
public class DatosInvalidosException extends Exception {

    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
