package vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import modelo.GestionMascota;

// [USER_IMPORTS_START]
//  ESCRIBE TUS IMPORTS AQUI (Ej: import java.io.*;)
    // ----------------------------------------------------
// [USER_IMPORTS_END]

public class Eliminarmascota extends JFrame {

    private JLabel label1;
    private JTextField txtEliminar;
    private JButton btnAceptar;
    GestionMascota Gestion = new GestionMascota();

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public Eliminarmascota() {
        setTitle("Eliminar mascota");
        setSize(300, 130);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        initComponents();
        initEvents();
    }

    private void initComponents() {
        label1 = new JLabel("INSERTAR ID A ELIMINAR: ");
        label1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label1.setBounds(17, 14, 80, 20);
        txtEliminar = new JTextField();
        txtEliminar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtEliminar.setBounds(160, 10, 120, 22);
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnAceptar.setBounds(92, 52, 90, 25);
        getContentPane().add(label1);
        getContentPane().add(txtEliminar);
        getContentPane().add(btnAceptar);
    }

    private void initEvents() {
        // [USER_ACTION_START:btnAceptar]
btnAceptar.addActionListener(e -> {

            try {
            int id = Integer.parseInt(txtEliminar.getText());

        // Llamamos al método eliminar de la gestión (retorna boolean)
        boolean eliminado = Gestion.eliminar(id);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Mascota eliminada.");
            formMascota form = new formMascota();
            form.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar la mascota.");
            limpiarFormulario();
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
        });
        // [USER_ACTION_END:btnAceptar]

    }
    public void limpiarFormulario() {
        txtEliminar.setText("");
    }
// [USER_METHODS_END]
}