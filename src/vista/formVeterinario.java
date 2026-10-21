package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

import modelo.GestionVeterinario;
import modelo.Veterinario;

public class formVeterinario extends JFrame {
    private final GestionVeterinario gestionVeterinario = new GestionVeterinario();

    private final JTextField txtNombre = new JTextField(18);
    private final JPasswordField txtPassword = new JPasswordField(18);
    private final JTextField txtEspecialidad = new JTextField(18);
    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnModificar = new JButton("Modificar");
    private final JButton btnEliminar = new JButton("Eliminar");

    private final DefaultTableModel modeloVeterinarios = new DefaultTableModel(
            new String[] {"ID", "Nombre", "Especialidad"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tablaVeterinarios = new JTable(modeloVeterinarios);
    private int veterinarioSeleccionadoId = -1;

    public formVeterinario() {
        FlatLightLaf.setup();
        getContentPane().setBackground(UIManager.getColor("Panel.background"));
        setTitle("Veterinarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 500));
        setSize(950, 600);
        setLocationRelativeTo(null);

        initComponents();
        initEvents();
        actualizarTablaVeterinarios();
    }

    private void initComponents() {
        JPanel formulario = new JPanel(new BorderLayout(8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel titulo = new JLabel("VETERINARIO");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        formulario.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setBorder(BorderFactory.createTitledBorder("Datos del veterinario"));
        agregarCampo(campos, "Nombre:", txtNombre, 0);
        agregarCampo(campos, "Contraseña:", txtPassword, 1);
        agregarCampo(campos, "Especialidad:", txtEspecialidad, 2);
        txtPassword.setToolTipText("Al modificar, deja este campo vacío para conservar la contraseña actual.");

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

        tablaVeterinarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaVeterinarios.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollVeterinarios = new JScrollPane(tablaVeterinarios);
        JPanel panelVeterinarios = new JPanel(new BorderLayout());
        panelVeterinarios.setBorder(BorderFactory.createTitledBorder("Veterinarios registrados"));
        panelVeterinarios.add(scrollVeterinarios, BorderLayout.CENTER);

        javax.swing.JSplitPane contenido = new javax.swing.JSplitPane(
                javax.swing.JSplitPane.HORIZONTAL_SPLIT, formulario, panelVeterinarios);
        contenido.setResizeWeight(0.4);
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
                Veterinario veterinario = leerFormulario(0);
                int id = gestionVeterinario.guardar(veterinario);
                if (id == -1) {
                    mostrarError("No se pudo guardar el veterinario.");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Veterinario guardado con ID: " + id);
                limpiarFormulario();
                actualizarTablaVeterinarios();
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnModificar.addActionListener(e -> {
            if (veterinarioSeleccionadoId == -1) {
                mostrarError("Selecciona un veterinario para modificar.");
                return;
            }

            try {
                Veterinario veterinario = leerFormulario(veterinarioSeleccionadoId);
                if (!gestionVeterinario.actualizar(veterinario)) {
                    mostrarError("No se pudo modificar el veterinario.");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Veterinario modificado.");
                limpiarFormulario();
                actualizarTablaVeterinarios();
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            if (veterinarioSeleccionadoId == -1) {
                mostrarError("Selecciona un veterinario para eliminar.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    this, "¿Deseas eliminar el veterinario seleccionado?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                if (!gestionVeterinario.eliminar(veterinarioSeleccionadoId)) {
                    mostrarError("No se pudo eliminar el veterinario.");
                    return;
                }
            } catch (IllegalStateException ex) {
                mostrarError(ex.getMessage());
                return;
            }

            JOptionPane.showMessageDialog(this, "Veterinario eliminado.");
            limpiarFormulario();
            actualizarTablaVeterinarios();
        });

        tablaVeterinarios.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                cargarVeterinarioSeleccionado();
            }
        });
    }

    private Veterinario leerFormulario(int id) {
        String nombre = txtNombre.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String especialidad = txtEspecialidad.getText().trim();

        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (especialidad.isEmpty()) {
            throw new IllegalArgumentException("La especialidad es obligatoria.");
        }
        if (id == 0 && password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        return new Veterinario(id, nombre, password, especialidad);
    }

    private void cargarVeterinarioSeleccionado() {
        int filaSeleccionada = tablaVeterinarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            veterinarioSeleccionadoId = -1;
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
            return;
        }

        int filaModelo = tablaVeterinarios.convertRowIndexToModel(filaSeleccionada);
        veterinarioSeleccionadoId = Integer.parseInt(modeloVeterinarios.getValueAt(filaModelo, 0).toString());
        txtNombre.setText(modeloVeterinarios.getValueAt(filaModelo, 1).toString());
        txtEspecialidad.setText(modeloVeterinarios.getValueAt(filaModelo, 2).toString());
        txtPassword.setText("");
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void actualizarTablaVeterinarios() {
        ArrayList<Veterinario> veterinarios = gestionVeterinario.listar();
        modeloVeterinarios.setRowCount(0);
        for (Veterinario veterinario : veterinarios) {
            modeloVeterinarios.addRow(new Object[] {
                    veterinario.getId(), veterinario.getNombre(), veterinario.getEspecialidad()
            });
        }
    }

    private void limpiarFormulario() {
        veterinarioSeleccionadoId = -1;
        txtNombre.setText("");
        txtPassword.setText("");
        txtEspecialidad.setText("");
        tablaVeterinarios.clearSelection();
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos del veterinario", JOptionPane.ERROR_MESSAGE);
    }
}
