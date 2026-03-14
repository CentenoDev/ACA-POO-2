package com.impresion3d.controller;

import com.impresion3d.dao.ConexionDB;
import com.impresion3d.exceptions.DatosInvalidosException;
import com.impresion3d.model.*;
import java.sql.*;
import java.util.ArrayList;

public class PedidoController {

    private ArrayList<Pieza3D> listaPedidos;

    public PedidoController() {
        this.listaPedidos = new ArrayList<>();
    }

    public void agregarPedido(Pieza3D nuevaPieza) throws DatosInvalidosException {
        if (nuevaPieza.getPesoGramos() <= 0 || nuevaPieza.getTiempoHoras() <= 0) {
            throw new DatosInvalidosException("Error: Datos numéricos inválidos.");
        }
        listaPedidos.add(nuevaPieza);
        guardarEnBaseDeDatos(nuevaPieza);
    }

    private void guardarEnBaseDeDatos(Pieza3D pieza) {
        String sql = "INSERT INTO pedidos (nombre_cliente, peso_gramos, tiempo_horas, tipo_impresion, precio_final) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (conn != null) {
                stmt.setString(1, pieza.getNombreCliente());
                stmt.setDouble(2, pieza.getPesoGramos());
                stmt.setDouble(3, pieza.getTiempoHoras());
                stmt.setString(4, (pieza instanceof ImpresionFDM) ? "FDM" : "Resina");
                stmt.setDouble(5, pieza.calcularPrecioFinal());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    // NUEVO MÉTODO: Cargar desde la base de datos (READ)
    public ArrayList<Pieza3D> obtenerTodosLosPedidos() {
        ArrayList<Pieza3D> pedidosDB = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection conn = ConexionDB.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("nombre_cliente");
                double peso = rs.getDouble("peso_gramos");
                double tiempo = rs.getDouble("tiempo_horas");
                String tipo = rs.getString("tipo_impresion");
                int id = rs.getInt("id_pedido");

                Pieza3D pieza;
                if (tipo.equalsIgnoreCase("FDM")) {
                    pieza = new ImpresionFDM(id, nombre, peso, tiempo);
                } else {
                    pieza = new ImpresionResina(id, nombre, peso, tiempo);
                }
                pedidosDB.add(pieza);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
        this.listaPedidos = pedidosDB; // Sincronizamos la lista en memoria
        return pedidosDB;
    }
}
