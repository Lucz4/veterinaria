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

import modelo.GestionMascota;
import modelo.GestionVacuna;
import modelo.Mascota;
import modelo.Vacuna;

public class formVacuna extends JFrame {
    private GestionVacuna gestionVacuna = new GestionVacuna();
    private GestionMascota gestionMascota = new GestionMascota();

    private JTextField txtTipo = new JTextField(18);
    private JTextField txtFecha = new JTextField(18);
    private JTextField txtHora = new JTextField(18);
    private JTextField txtVencimiento = new JTextField(18);
    private JTextField txtIdConsulta = new JTextField(18);
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

    private DefaultTableModel modeloVacunas = new DefaultTableModel(
            new String[] {"ID", "Tipo", "Fecha", "Hora", "Vencimiento", "ID mascota", "ID consulta"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable tablaVacunas = new JTable(modeloVacunas);

    private int vacunaSeleccionadaId = -1;

    public formVacuna() {
        FlatLightLaf.setup();
        getContentPane().setBackground(UIManager.getColor("Panel.background"));
        setTitle("Vacunas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 560));
        setSize(1050, 650);
        setLocationRelativeTo(null);

        initComponents();
        initEvents();
        actualizarTablaMascotas();
        actualizarTablaVacunas();
    }

    private void initComponents() {
        JPanel formulario = new JPanel(new BorderLayout(8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel titulo = new JLabel("VACUNA");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        formulario.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setBorder(BorderFactory.createTitledBorder("Datos de la vacuna"));
        agregarCampo(campos, "Tipo:", txtTipo, 0);
        agregarCampo(campos, "Fecha (AAAA-MM-DD):", txtFecha, 1);
        agregarCampo(campos, "Hora (HH:mm:ss):", txtHora, 2);
        agregarCampo(campos, "Vencimiento (AAAA-MM-DD):", txtVencimiento, 3);
        agregarCampo(campos, "ID consulta:", txtIdConsulta, 4);
        txtFecha.setToolTipText("Formato: AAAA-MM-DD");
        txtHora.setToolTipText("Formato: HH:mm o HH:mm:ss");
        txtVencimiento.setToolTipText("Formato: AAAA-MM-DD");

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
        tablaVacunas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaVacunas.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollVacunas = new JScrollPane(tablaVacunas);
        JPanel panelVacunas = new JPanel(new BorderLayout());
        panelVacunas.setBorder(BorderFactory.createTitledBorder("Vacunas registradas"));
        panelVacunas.add(scrollVacunas, BorderLayout.CENTER);
        javax.swing.JSplitPane tablas = new javax.swing.JSplitPane(
                javax.swing.JSplitPane.VERTICAL_SPLIT, panelMascotas, panelVacunas);
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
            Vacuna vacuna = leerFormulario(0, idMascota);
            int id = gestionVacuna.guardar(vacuna);
            if (id == -1) {
                mostrarError("No se pudo guardar la vacuna.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Vacuna guardada con ID: " + id);
            limpiarFormulario();
            actualizarTablaVacunas();
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }});
        btnModificar.addActionListener(e -> {
        if (vacunaSeleccionadaId == -1) {
            mostrarError("Selecciona una vacuna para modificar.");
            return;
        }

        try {
            int idMascota = obtenerIdMascotaSeleccionada();
            Vacuna vacuna = leerFormulario(vacunaSeleccionadaId, idMascota);
            if (!gestionVacuna.actualizar(vacuna)) {
                mostrarError("No se pudo modificar la vacuna.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Vacuna modificada.");
            limpiarFormulario();
            actualizarTablaVacunas();
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    });
        btnEliminar.addActionListener(e -> {
        if (vacunaSeleccionadaId == -1) {
            mostrarError("Selecciona una vacuna para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this, "¿Deseas eliminar la vacuna seleccionada?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        if (!gestionVacuna.eliminar(vacunaSeleccionadaId)) {
            mostrarError("No se pudo eliminar la vacuna.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Vacuna eliminada.");
        limpiarFormulario();
        actualizarTablaVacunas();
    });

        tablaVacunas.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                cargarVacunaSeleccionada();
            }
        });
    }

    private Vacuna leerFormulario(int id, int idMascota) {
        String tipo = txtTipo.getText().trim();
        if (tipo.isEmpty()) {
            throw new IllegalArgumentException("El tipo de vacuna es obligatorio.");
        }

        try {
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());
            LocalDate vencimiento = LocalDate.parse(txtVencimiento.getText().trim());
            int idConsulta = Integer.parseInt(txtIdConsulta.getText().trim());
            if (idConsulta <= 0) {
                throw new IllegalArgumentException("El ID de consulta debe ser un entero positivo.");
            }
            return new Vacuna(id, tipo, fecha, hora, vencimiento, idMascota, idConsulta);
        } catch (java.time.format.DateTimeParseException ex) {
            throw new IllegalArgumentException(
                    "Revisa las fechas (AAAA-MM-DD) y la hora (HH:mm o HH:mm:ss).");
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El ID de consulta debe ser un entero positivo.");
        }
    }

    private int obtenerIdMascotaSeleccionada() {
        int filaSeleccionada = tablaMascotas.getSelectedRow();
        if (filaSeleccionada == -1) {
            throw new IllegalArgumentException("Selecciona una mascota antes de guardar la vacuna.");
        }
        int filaModelo = tablaMascotas.convertRowIndexToModel(filaSeleccionada);
        return Integer.parseInt(modeloMascotas.getValueAt(filaModelo, 0).toString());
    }

    private void cargarVacunaSeleccionada() {
        int filaSeleccionada = tablaVacunas.getSelectedRow();
        if (filaSeleccionada == -1) {
            vacunaSeleccionadaId = -1;
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
            return;
        }

        int filaModelo = tablaVacunas.convertRowIndexToModel(filaSeleccionada);
        vacunaSeleccionadaId = Integer.parseInt(modeloVacunas.getValueAt(filaModelo, 0).toString());
        txtTipo.setText(modeloVacunas.getValueAt(filaModelo, 1).toString());
        txtFecha.setText(modeloVacunas.getValueAt(filaModelo, 2).toString());
        txtHora.setText(modeloVacunas.getValueAt(filaModelo, 3).toString());
        txtVencimiento.setText(modeloVacunas.getValueAt(filaModelo, 4).toString());
        txtIdConsulta.setText(modeloVacunas.getValueAt(filaModelo, 6).toString());
        seleccionarMascota(Integer.parseInt(modeloVacunas.getValueAt(filaModelo, 5).toString()));
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

    private void actualizarTablaVacunas() {
        ArrayList<Vacuna> vacunas = gestionVacuna.listar();
        modeloVacunas.setRowCount(0);
        for (Vacuna vacuna : vacunas) {
            modeloVacunas.addRow(new Object[] {
                    vacuna.getId(), vacuna.getTipo(), vacuna.getFecha(), vacuna.getHora(),
                    vacuna.getVencimiento(), vacuna.getId_mascota(), vacuna.getId_consulta()
            });
        }
    }

    private void limpiarFormulario() {
        vacunaSeleccionadaId = -1;
        txtTipo.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtVencimiento.setText("");
        txtIdConsulta.setText("");
        tablaVacunas.clearSelection();
        tablaMascotas.clearSelection();
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos de vacuna", JOptionPane.ERROR_MESSAGE);
    }
}
