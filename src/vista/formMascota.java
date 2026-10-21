package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.table.*;
import modelo.Mascota;
import modelo.GestionMascota;
import modelo.Dueno;
import modelo.GestionDueño;

// [USER_IMPORTS_START]
//  ESCRIBE TUS IMPORTS AQUI (Ej: import java.io.*;)
    // ----------------------------------------------------
// [USER_IMPORTS_END]

public class formMascota extends JFrame {

    private GestionMascota gestion = new GestionMascota();
    private GestionDueño GestionD = new GestionDueño();

    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JTextField txtNOMBRE;
    private JTextField txtEDAD;
    private JTextField txtESPECIE;
    private JTextField txtRAZA;
    private JTextField txtGENERO;
    private JTextField txtPESO;
    private JTable tablamascota;
    private DefaultTableModel modeloTablamascota;
    private JTable tabladueño;
    private DefaultTableModel modeloTabladueño;
    private JLabel a;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnModificar;

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public formMascota() {
        setTitle("Mi Formulario");
        setSize(570, 360);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        initComponents();
        initEvents();
        actualizarTabla();
        actualizarTablaDueño();
    }

    private void initComponents() {
        label1 = new JLabel("NOMBRE");
        label1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label1.setBounds(15, 22, 80, 20);
        label2 = new JLabel("EDAD");
        label2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label2.setBounds(15, 57, 12, 20);
        label3 = new JLabel("ESPECIE");
        label3.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label3.setBounds(15, 87, 80, 20);
        label4 = new JLabel("RAZA");
        label4.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label4.setBounds(15, 118, 80, 20);
        label5 = new JLabel("GENERO");
        label5.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label5.setBounds(15, 151, 80, 20);
        label6 = new JLabel("PESO");
        label6.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label6.setBounds(15, 181, 80, 20);
        txtNOMBRE = new JTextField();
        txtNOMBRE.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtNOMBRE.setBounds(70, 17, 120, 22);
        txtEDAD = new JTextField();
        txtEDAD.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtEDAD.setBounds(70, 51, 120, 22);
        txtESPECIE = new JTextField();
        txtESPECIE.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtESPECIE.setBounds(70, 80, 120, 22);
        txtRAZA = new JTextField();
        txtRAZA.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtRAZA.setBounds(70, 113, 120, 22);
        txtGENERO = new JTextField();
        txtGENERO.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtGENERO.setBounds(70, 143, 120, 22);
        txtPESO = new JTextField();
        txtPESO.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtPESO.setBounds(70, 179, 120, 22);
        modeloTablamascota = new DefaultTableModel(
            new String[]{"Nombre", "Edad", "Especie", "Raza", "Genero", "Peso", "Id.Dueño"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tablamascota = new JTable(modeloTablamascota);
        tablamascota.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablamascota.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll_tablamascota = new JScrollPane(tablamascota);
        scroll_tablamascota.setBounds(211, 31, 325, 182);
        modeloTabladueño = new DefaultTableModel(
            new String[]{"Id.Dueño", "Nombre"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tabladueño = new JTable(modeloTabladueño);
        tabladueño.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabladueño.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll_tabladueño = new JScrollPane(tabladueño);
        scroll_tabladueño.setBounds(211, 220, 325, 100);
        a = new JLabel("MASCOTAS");
        a.setFont(new Font("Tahoma", Font.PLAIN, 11));
        a.setBounds(293, 1, 80, 20);
        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnGuardar.setBounds(25, 224, 150, 20);
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnEliminar.setBounds(27, 258, 150, 25);
        btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnModificar.setBounds(27, 290, 150, 23);
        getContentPane().add(label1);
        getContentPane().add(label2);
        getContentPane().add(label3);
        getContentPane().add(label4);
        getContentPane().add(label5);
        getContentPane().add(label6);
        getContentPane().add(txtNOMBRE);
        getContentPane().add(txtEDAD);
        getContentPane().add(txtESPECIE);
        getContentPane().add(txtRAZA);
        getContentPane().add(txtGENERO);
        getContentPane().add(txtPESO);
        getContentPane().add(scroll_tablamascota);
        getContentPane().add(scroll_tabladueño);
        getContentPane().add(a);
        getContentPane().add(btnGuardar);
        getContentPane().add(btnEliminar);
        getContentPane().add(btnModificar);
        
    }

    private void initEvents() {
        // [USER_ACTION_START:btnGuardar]
btnGuardar.addActionListener(e -> {
    try {
        int filaSeleccionada = tabladueño.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un dueño antes de guardar la mascota.");
            return;
        }
        int filaModelo = tabladueño.convertRowIndexToModel(filaSeleccionada);
        int idDueno = Integer.parseInt(modeloTabladueño.getValueAt(filaModelo, 0).toString());

        String Nombre = txtNOMBRE.getText();
        String Genero = txtGENERO.getText();
        int Edad = Integer.parseInt(txtEDAD.getText());
        String Especie = txtESPECIE.getText();
        String Raza = txtRAZA.getText();
        double Peso = Double.parseDouble(txtPESO.getText());

        Mascota mascota = new Mascota(0, Nombre, Edad, Especie, Raza, Genero, Peso, idDueno);
        int id = gestion.guardar(mascota);

        if (id != -1) {
            JOptionPane.showMessageDialog(this, "Mascota guardada con ID: " + id);
            limpiarFormulario();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la mascota.");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }});
        // [USER_ACTION_END:btnGuardar]

        // [USER_ACTION_START:btnEliminar]
btnEliminar.addActionListener(e -> {
            Eliminarmascota Eliminarform= new Eliminarmascota();
            Eliminarform.setVisible(true);
            this.dispose();
        });
        // [USER_ACTION_END:btnEliminar]

        // [USER_ACTION_START:btnModificar]
btnModificar.addActionListener(e -> {
            // TODO: accion de btnModificar
        });


        // [USER_ACTION_END:btnModificar]

    }

// [USER_METHODS_START]
//  AGREGA TUS METODOS PERSONALIZADOS AQUI
    // ----------------------------------------------------
    public void actualizarTabla() {
                try {
        ArrayList<Mascota> listaMascota = gestion.listar();
        modeloTablamascota.setRowCount(0); // Limpia la tabla visual
        
        for (Mascota mascota : listaMascota) {
            modeloTablamascota.addRow(new Object[] {

                mascota.getNombre(),
                mascota.getEdad(),
                mascota.getEspecie(),
                mascota.getGenero(),
                mascota.getRaza(),
                mascota.getPeso(),
                mascota.getId_dueño(),
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    };
    };

        public void actualizarTablaDueño() {
                        try {
        ArrayList<Dueno> listaDuenos = GestionD.listar();
        modeloTabladueño.setRowCount(0); // Limpia la tabla visual
        
        for (Dueno dueno : listaDuenos) {
            modeloTabladueño.addRow(new Object[] {
                dueno.getId(),
                dueno.getNombre(),
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    };
    };

    public void limpiarFormulario() {
        // TODO: limpiar campos del formulario
    }
        
// [USER_METHODS_END]
};