package com.impresion3d.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Configuración para XAMPP (root sin contraseña y base de datos 'impresion3d_db')
    private static final String URL = "jdbc:mysql://localhost:3306/impresion3d_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
