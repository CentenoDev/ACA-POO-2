package com.impresion3d;

import com.impresion3d.view.VentanaPedidos;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Establece el estilo visual nativo del sistema operativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo establecer el Look and Feel: " + e.getMessage());
        }

        // Iniciar la interfaz gráfica de forma segura
        SwingUtilities.invokeLater(() -> {
            VentanaPedidos ventana = new VentanaPedidos();
            ventana.setVisible(true);
        });
    }
}