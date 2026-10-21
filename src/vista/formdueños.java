package vista;
import dao.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.*;
import com.formdev.flatlaf.FlatLightLaf;

import modelo.Dueno;
import modelo.GestionDueño;

// [USER_IMPORTS_START]
    //  ESCRIBE TUS IMPORTS AQUI (Ej: import java.io.*;)
    // ----------------------------------------------------
// [USER_IMPORTS_END]

public class formdueños extends JFrame {

    private JLabel EtiquetaCI;
    private JLabel EtiquetaNombre;
    private JLabel EtiquetaTelefono;
    private JLabel EtiquetaEmail;
    private JTextField txtField5;
    private JTextField txtField6;
    private JTextField txtField7;
    private JButton btnGuardar;
    private JTextField txtField9;
    private JLabel Titulo;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JTable table13;
    private DefaultTableModel modeloTable13;
    private GestionDueño Gestion = new GestionDueño();



// [USER_DECLS_START]
    //  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public formdueños() {

        // Look & Feel FlatLaf (requiere la libreria flatlaf en el classpath)
        FlatLightLaf.setup();
        getContentPane().setBackground(UIManager.getColor("Panel.background"));
        setTitle("Dueño");
        setSize(500, 410);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        initComponents();
        initEvents();
        actualizarTabla();
    }

    private void initComponents() {
        EtiquetaCI = new JLabel("CI:");
        EtiquetaCI.setFont(new Font("Tahoma", Font.PLAIN, 11));
        EtiquetaCI.setBounds(44, 63, 80, 20);
        EtiquetaNombre = new JLabel("Nombre:");
        EtiquetaNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
        EtiquetaNombre.setBounds(38, 98, 80, 20);
        EtiquetaTelefono = new JLabel("Telefono:");
        EtiquetaTelefono.setFont(new Font("Tahoma", Font.PLAIN, 11));
        EtiquetaTelefono.setBounds(39, 128, 80, 20);
        EtiquetaEmail = new JLabel("Email:");
        EtiquetaEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
        EtiquetaEmail.setBounds(42, 162, 80, 20);
        txtField5 = new JTextField();
        txtField5.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtField5.setBounds(86, 60, 120, 22);
        txtField6 = new JTextField();
        txtField6.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtField6.setBounds(85, 92, 120, 22);
        txtField7 = new JTextField();
        txtField7.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtField7.setBounds(88, 125, 120, 22);
        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnGuardar.setBounds(47, 248, 90, 25);
        txtField9 = new JTextField();
        txtField9.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtField9.setBounds(88, 159, 120, 22);
        Titulo = new JLabel("DUEÑO");
        Titulo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        Titulo.setBounds(201, 14, 80, 20);
        btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnModificar.setBounds(47, 211, 90, 25);
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnEliminar.setBounds(50, 287, 90, 25);
        modeloTable13 = new DefaultTableModel(
            new String[]{"ID", "CI", "Nombre", "Telefono", "Email"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table13 = new JTable(modeloTable13);
        table13.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table13.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll_table13 = new JScrollPane(table13);
        scroll_table13.setBounds(187, 213, 280, 120);
        getContentPane().add(EtiquetaCI);
        getContentPane().add(EtiquetaNombre);
        getContentPane().add(EtiquetaTelefono);
        getContentPane().add(EtiquetaEmail);
        getContentPane().add(txtField5);
        getContentPane().add(txtField6);
        getContentPane().add(txtField7);
        getContentPane().add(btnGuardar);
        getContentPane().add(txtField9);
        getContentPane().add(Titulo);
        getContentPane().add(btnModificar);
        getContentPane().add(btnEliminar);
        getContentPane().add(scroll_table13);
    }

    private void initEvents() {
        // [USER_ACTION_START:btnGuardar]
        btnGuardar.addActionListener(e -> {
                try {
String nombre = txtField6.getText();
            int CI = Integer.parseInt(txtField5.getText());
            int telefono = Integer.parseInt(txtField7.getText());
            String email = txtField9.getText();

        // Instanciamos el modelo y llamamos al DAO

        Dueno dueno = new Dueno(0, nombre, CI, telefono, email);
        int id = Gestion.guardar(dueno);

        if (id != -1) {
            JOptionPane.showMessageDialog(this, "Dueño guardado con ID: " + id);
            limpiarFormulario();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el dueño.");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});


       // [USER_ACTION_START:btnModificar]
        btnEliminar.addActionListener(e -> {
    try {
        int filaSeleccionada = table13.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un dueño para eliminar.");
            return;
        }
        int filaModelo = table13.convertRowIndexToModel(filaSeleccionada);
        int idDueno = Integer.parseInt(modeloTable13.getValueAt(filaModelo, 0).toString());
        boolean eliminado = Gestion.eliminar(idDueno);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Dueño eliminado.");
            limpiarFormulario();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el dueño.");
        }
    } catch (SQLException ex) {
        if (ex.getErrorCode() == 1451 && "23000".equals(ex.getSQLState())) {
            JOptionPane.showMessageDialog(this,
                    "No se puede eliminar el dueño porque tiene mascotas asociadas. "
                            + "Elimina o reasigna sus mascotas primero.");
        } else {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el dueño.");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});
        
        // [USER_ACTION_END:btnModificar]

        // [USER_ACTION_START:btnEliminar]
        btnModificar.addActionListener(e -> {
           try {

            int CI = Integer.parseInt(txtField5.getText());
        boolean modificado = Gestion.eliminar(CI);

        if (modificado) {
            JOptionPane.showMessageDialog(this, "Dueño modificado.");
            limpiarFormulario();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar.");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});
        };
        // [USER_ACTION_END:btnEliminar]

    
        // [USER_ACTION_END:btnGuardar]

 

// [USER_METHODS_START]
    //  AGREGA TUS METODOS PERSONALIZADOS AQUI
    // ----------------------------------------------------
    public void actualizarTabla() {
            try {
        ArrayList<Dueno> listaDuenos = Gestion.listar();
        modeloTable13.setRowCount(0); 

        for (Dueno dueno : listaDuenos) {
            modeloTable13.addRow(new Object[] {
                dueno.getId(),
                dueno.getCi(),
                dueno.getNombre(),
                dueno.getTelefono(),
                dueno.getEmail(),
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void limpiarFormulario() {
        this.txtField5.setText("");
        this.txtField6.setText("");
        this.txtField7.setText("");
        this.txtField9.setText("");
    }
// [USER_METHODS_END]
}
/*   */