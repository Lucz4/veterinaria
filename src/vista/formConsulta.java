package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

import modelo.Consulta;
import modelo.GestionConsulta;
import modelo.GestionMascota;
import modelo.GestionVeterinario;
import modelo.Mascota;
import modelo.Veterinario;

public class formConsulta extends JFrame {
    private GestionConsulta gestionConsulta = new GestionConsulta();
    private GestionMascota gestionMascota = new GestionMascota();
    private GestionVeterinario gestionVeterinario = new GestionVeterinario();

    private JTextField txtProcedimiento = new JTextField(18);
    private JTextField txtFecha = new JTextField(18);
    private JTextField txtHora = new JTextField(18);
    private JTextField txtIdVeterinario = new JTextField(18);
    private JButton btnGuardar = new JButton("Guardar");
    private JButton btnModificar = new JButton("Modificar");
    private JButton btnEliminar = new JButton("Eliminar");

    private DefaultTableModel modeloMascotas = new DefaultTableModel(
            new String[] {"ID", "Nombre", "Especie"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable tablaMascotas = new JTable(modeloMascotas);

    private DefaultTableModel modeloConsulta = new DefaultTableModel(
            new String[] {"ID", "Procedimiento", "Fecha", "Hora", "ID mascota", "ID Veterinario"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable tablaConsultas = new JTable(modeloConsulta);

    private int consultaSeleccionadaId = -1;

    public formConsulta() {
        FlatLightLaf.setup();
        getContentPane().setBackground(UIManager.getColor("Panel.background"));
        setTitle("Consultas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 560));
        setSize(1050, 650);
        setLocationRelativeTo(null);

        initComponents();
        initEvents();
        actualizarTablaMascotas();
        actualizarTablaConsultas();
    }

    private void initComponents() {
        JPanel formulario = new JPanel(new BorderLayout(8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel titulo = new JLabel("CONSULTA");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        formulario.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setBorder(BorderFactory.createTitledBorder("Datos de la consulta"));
        agregarCampo(campos, "Procedimiento:", txtProcedimiento, 0);
        agregarCampo(campos, "Fecha (AAAA-MM-DD):", txtFecha, 1);
        agregarCampo(campos, "Hora (HH:mm:ss):", txtHora, 2);
        agregarCampo(campos, "ID veterinario:", txtIdVeterinario, 3);
        txtFecha.setToolTipText("Formato: AAAA-MM-DD");
        txtHora.setToolTipText("Formato: HH:mm o HH:mm:ss");

        JPanel botones = new JPanel();
        botones.add(btnGuardar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);

        JPanel cuerpoFormulario = new JPanel(new BorderLayout(8, 8));
        cuerpoFormulario.add(campos, BorderLayout.NORTH);
        cuerpoFormulario.add(botones, BorderLayout.SOUTH);
        formulario.add(cuerpoFormulario, BorderLayout.CENTER);

        tablaMascotas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaMascotas.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollMascotas = new JScrollPane(tablaMascotas);
        JPanel panelMascotas = new JPanel(new BorderLayout());
        panelMascotas.setBorder(BorderFactory.createTitledBorder("Selecciona una mascota"));
        panelMascotas.add(scrollMascotas, BorderLayout.CENTER);

        tablaConsultas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaConsultas.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollConsultas = new JScrollPane(tablaConsultas);
        JPanel panelConsultas = new JPanel(new BorderLayout());
        panelConsultas.setBorder(BorderFactory.createTitledBorder("Consultas registradas"));
        panelConsultas.add(scrollConsultas, BorderLayout.CENTER);

        javax.swing.JSplitPane tablas = new javax.swing.JSplitPane(
                javax.swing.JSplitPane.VERTICAL_SPLIT, panelMascotas, panelConsultas);
        tablas.setResizeWeight(0.35);
        tablas.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 8));

        javax.swing.JSplitPane contenido = new javax.swing.JSplitPane(
                javax.swing.JSplitPane.HORIZONTAL_SPLIT, formulario, tablas);
        contenido.setResizeWeight(0.35);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contenido, BorderLayout.CENTER);
    }

    private void agregarCampo(JPanel panel, String etiqueta, JTextField campo, int fila) {
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(6, 8, 6, 8);
        restricciones.anchor = GridBagConstraints.LINE_START;
        restricciones.gridx = 0;
        restricciones.gridy = fila;
        panel.add(new JLabel(etiqueta), restricciones);

        restricciones.gridx = 1;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.weightx = 1;
        panel.add(campo, restricciones);
    }

    private void initEvents() {
        btnGuardar.addActionListener(e -> {
            try {
                int idMascota = obtenerIdMascotaSeleccionada();
                int idVeterinario = obtenerIdVeterinarioIngresado();
                Consulta consulta = leerFormulario(0, idMascota, idVeterinario);
                int id = gestionConsulta.guardar(consulta);
                if (id == -1) {
                    mostrarError("No se pudo guardar la consulta.");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Consulta guardada con ID: " + id);
                limpiarFormulario();
                actualizarTablaConsultas();
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnModificar.addActionListener(e -> {
            if (consultaSeleccionadaId == -1) {
                mostrarError("Selecciona una consulta para modificar.");
                return;
            }

            try {
                int idMascota = obtenerIdMascotaSeleccionada();
                int idVeterinario = obtenerIdVeterinarioIngresado();
                Consulta consulta = leerFormulario(consultaSeleccionadaId, idMascota, idVeterinario);
                if (!gestionConsulta.actualizar(consulta)) {
                    mostrarError("No se pudo modificar la consulta.");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Consulta modificada.");
                limpiarFormulario();
                actualizarTablaConsultas();
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            if (consultaSeleccionadaId == -1) {
                mostrarError("Selecciona una consulta para eliminar.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    this, "¿Deseas eliminar la consulta seleccionada?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            if (!gestionConsulta.eliminar(consultaSeleccionadaId)) {
                mostrarError("No se pudo eliminar la consulta.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Consulta eliminada.");
            limpiarFormulario();
            actualizarTablaConsultas();
        });

        tablaConsultas.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                cargarConsultaSeleccionada();
            }
        });
    }

    private Consulta leerFormulario(int id, int idMascota, int idVeterinario) {
        String procedimiento = txtProcedimiento.getText().trim();
        if (procedimiento.isEmpty()) {
            throw new IllegalArgumentException("El procedimiento es obligatorio.");
        }

        if (idVeterinario <= 0) {
            throw new IllegalArgumentException("El ID del veterinario debe ser un entero positivo.");
        }

        try {
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());
            return new Consulta(id, procedimiento, fecha, hora, idMascota, idVeterinario);
        } catch (java.time.format.DateTimeParseException ex) {
            throw new IllegalArgumentException(
                    "Revisa la fecha (AAAA-MM-DD) y la hora (HH:mm o HH:mm:ss).");
        }
    }

    private int obtenerIdMascotaSeleccionada() {
        int filaSeleccionada = tablaMascotas.getSelectedRow();
        if (filaSeleccionada == -1) {
            throw new IllegalArgumentException("Selecciona una mascota antes de guardar la consulta.");
        }
        int filaModelo = tablaMascotas.convertRowIndexToModel(filaSeleccionada);
        return Integer.parseInt(modeloMascotas.getValueAt(filaModelo, 0).toString());
    }

    private int obtenerIdVeterinarioIngresado() {
        String texto = txtIdVeterinario.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar el ID del veterinario.");
        }

        try {
            int idVeterinario = Integer.parseInt(texto);
            if (idVeterinario <= 0) {
                throw new IllegalArgumentException("El ID del veterinario debe ser un entero positivo.");
            }
            return idVeterinario;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El ID del veterinario debe ser un entero positivo.");
        }
    }

    private void cargarConsultaSeleccionada() {
        int filaSeleccionada = tablaConsultas.getSelectedRow();
        if (filaSeleccionada == -1) {
            consultaSeleccionadaId = -1;
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
            return;
        }

        int filaModelo = tablaConsultas.convertRowIndexToModel(filaSeleccionada);
        consultaSeleccionadaId = Integer.parseInt(modeloConsulta.getValueAt(filaModelo, 0).toString());
        txtProcedimiento.setText(modeloConsulta.getValueAt(filaModelo, 1).toString());
        txtFecha.setText(modeloConsulta.getValueAt(filaModelo, 2).toString());
        txtHora.setText(modeloConsulta.getValueAt(filaModelo, 3).toString());
        txtIdVeterinario.setText(modeloConsulta.getValueAt(filaModelo, 5).toString());
        seleccionarMascota(Integer.parseInt(modeloConsulta.getValueAt(filaModelo, 4).toString()));
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void seleccionarMascota(int idMascota) {
        for (int fila = 0; fila < modeloMascotas.getRowCount(); fila++) {
            int id = Integer.parseInt(modeloMascotas.getValueAt(fila, 0).toString());
            if (id == idMascota) {
                tablaMascotas.setRowSelectionInterval(fila, fila);
                return;
            }
        }
        tablaMascotas.clearSelection();
    }

    private void actualizarTablaMascotas() {
        ArrayList<Mascota> mascotas = gestionMascota.listar();
        modeloMascotas.setRowCount(0);
        for (Mascota mascota : mascotas) {
            modeloMascotas.addRow(new Object[] {
                    mascota.getId(), mascota.getNombre(), mascota.getEspecie()
            });
        }
    }

    private void actualizarTablaConsultas() {
        ArrayList<Consulta> consultas = gestionConsulta.listar();
        modeloConsulta.setRowCount(0);
        for (Consulta consulta : consultas) {
            modeloConsulta.addRow(new Object[] {
                    consulta.getId(), consulta.getProcedimiento(), consulta.getFecha(), consulta.getHora(),
                    consulta.getId_mascota(), consulta.getId_veterinario()
            });
        }
    }

    private void limpiarFormulario() {
        consultaSeleccionadaId = -1;
        txtProcedimiento.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtIdVeterinario.setText("");
        tablaConsultas.clearSelection();
        tablaMascotas.clearSelection();
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos de la consulta", JOptionPane.ERROR_MESSAGE);
     }
}
