package com.impresion3d.view;

import com.impresion3d.controller.PedidoController;
import com.impresion3d.model.*;
import java.awt.*;
import java.util.ArrayList; // Necesario para la carga de datos
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class VentanaPedidos extends JFrame {

    private PedidoController controlador;
    private JTextField txtNombre, txtPeso, txtTiempo;
    private JComboBox<String> cbTipo;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    // Paleta de colores "Tech-Industrial"
    private final Color COLOR_FONDO = new Color(24, 26, 27); // Negro Carbono
    private final Color COLOR_PANEL = new Color(34, 38, 41); // Gris Oscuro
    private final Color COLOR_ACENTO = new Color(0, 188, 212); // Cian Tecnológico
    private final Color COLOR_TEXTO = new Color(240, 240, 240); // Blanco Suave

    public VentanaPedidos() {
        controlador = new PedidoController();
        configurarVentana();
        inicializarInterfaz();
        cargarDatosDesdeDB(); // Carga los datos existentes al iniciar
    }

    private void configurarVentana() {
        setTitle("3D PRINT MANAGER | Panel de Control de Producción");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(COLOR_FONDO);
        setLocationRelativeTo(null);
    }

    private void inicializarInterfaz() {
        setLayout(new BorderLayout(0, 0));

        // --- PANEL LATERAL (Registro de Pedido) ---
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(COLOR_PANEL);
        panelLateral.setPreferredSize(new Dimension(320, 0));
        panelLateral.setLayout(null);
        panelLateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_ACENTO));

        JLabel lblTitulo = new JLabel("NUEVO PEDIDO");
        lblTitulo.setForeground(COLOR_ACENTO);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBounds(30, 40, 260, 30);
        panelLateral.add(lblTitulo);

        // Campos de entrada
        txtNombre = crearCampoEstilizado("CLIENTE", 100, panelLateral);
        txtPeso = crearCampoEstilizado("PESO ESTIMADO (G)", 180, panelLateral);
        txtTiempo = crearCampoEstilizado("TIEMPO DE IMPRESIÓN (H)", 260, panelLateral);

        JLabel lblTipo = new JLabel("TECNOLOGÍA DE IMPRESIÓN");
        lblTipo.setForeground(Color.GRAY);
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTipo.setBounds(30, 340, 200, 20);
        panelLateral.add(lblTipo);

        cbTipo = new JComboBox<>(new String[]{"Filamento (FDM)", "Resina (SLA/DLP)"});
        cbTipo.setBounds(30, 365, 260, 35);
        panelLateral.add(cbTipo);

        JButton btnGuardar = new JButton("REGISTRAR Y GUARDAR");
        btnGuardar.setBounds(30, 450, 260, 45);
        btnGuardar.setBackground(COLOR_ACENTO);
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardarPedido());
        panelLateral.add(btnGuardar);

        add(panelLateral, BorderLayout.WEST);

        // --- PANEL CENTRAL (Historial) ---
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(COLOR_FONDO);
        panelCentral.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTabla = new JLabel("📦 HISTORIAL DE PRODUCCIÓN");
        lblTabla.setForeground(COLOR_TEXTO);
        lblTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelCentral.add(lblTabla, BorderLayout.NORTH);

        // Tabla con diseño moderno
        modeloTabla = new DefaultTableModel(new String[]{"CLIENTE", "PESO (G)", "TIEMPO (H)", "MÉTODO", "VALOR TOTAL"}, 0);
        tablaPedidos = new JTable(modeloTabla);
        estilizarTabla();

        JScrollPane scroll = new JScrollPane(tablaPedidos);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PANEL));
        scroll.getViewport().setBackground(COLOR_FONDO);
        panelCentral.add(scroll, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);
    }

    private JTextField crearCampoEstilizado(String titulo, int y, JPanel panel) {
        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(Color.GRAY);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setBounds(30, y, 200, 20);
        panel.add(lbl);

        JTextField campo = new JTextField();
        campo.setBounds(30, y + 25, 260, 35);
        campo.setBackground(new Color(45, 50, 54));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(COLOR_ACENTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 65, 70)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        panel.add(campo);
        return campo;
    }

    private void estilizarTabla() {
        tablaPedidos.setBackground(COLOR_PANEL);
        tablaPedidos.setForeground(COLOR_TEXTO);
        tablaPedidos.setRowHeight(35);
        tablaPedidos.setGridColor(new Color(50, 55, 60));
        tablaPedidos.setSelectionBackground(new Color(0, 188, 212, 100));
        tablaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JTableHeader header = tablaPedidos.getTableHeader();
        header.setBackground(COLOR_FONDO);
        header.setForeground(COLOR_ACENTO);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_ACENTO));
    }

    private void guardarPedido() {
        try {
            String nombre = txtNombre.getText();
            double peso = Double.parseDouble(txtPeso.getText());
            double tiempo = Double.parseDouble(txtTiempo.getText());

            Pieza3D nueva;
            String seleccion = cbTipo.getSelectedItem().toString();
            if (seleccion.contains("Filamento")) {
                nueva = new ImpresionFDM(0, nombre, peso, tiempo);
            } else {
                nueva = new ImpresionResina(0, nombre, peso, tiempo);
            }

            controlador.agregarPedido(nueva);

            modeloTabla.addRow(new Object[]{
                nueva.getNombreCliente(),
                nueva.getPesoGramos() + " g",
                nueva.getTiempoHoras() + " h",
                (nueva instanceof ImpresionFDM ? "FDM" : "RESINA"),
                "$" + String.format("%.2f", nueva.calcularPrecioFinal())
            });

            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Registro exitoso en Base de Datos.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: Verifique que peso y tiempo sean números.");
        }
    }

    private void cargarDatosDesdeDB() {
        ArrayList<Pieza3D> existentes = controlador.obtenerTodosLosPedidos();
        for (Pieza3D p : existentes) {
            String tipo = (p instanceof ImpresionFDM) ? "FDM" : "RESINA";
            modeloTabla.addRow(new Object[]{
                p.getNombreCliente(),
                p.getPesoGramos() + " g",
                p.getTiempoHoras() + " h",
                tipo,
                "$" + String.format("%.2f", p.calcularPrecioFinal())
            });
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPeso.setText("");
        txtTiempo.setText("");
    }
}
