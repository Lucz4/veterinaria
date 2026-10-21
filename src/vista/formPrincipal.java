package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.table.*;
import com.formdev.flatlaf.FlatLightLaf;
import modelo.*;
import vista.*;

// [USER_IMPORTS_START]
//  ESCRIBE TUS IMPORTS AQUI (Ej: import java.io.*;)
    // ----------------------------------------------------
// [USER_IMPORTS_END]

public class formPrincipal extends JFrame {

    private JLabel Titulo;
    private JButton btnMascota;
    private JButton btnVeterinario;
    private JButton btnVacuna;
    private JButton btnConsulta;
    private JButton btnDueños;

// [USER_DECLS_START]
//  DECLARA TUS VARIABLES AQUI (Ej: private int contador;)
    // ----------------------------------------------------
// [USER_DECLS_END]

    public formPrincipal() {
        setTitle("formPrincipal");
        setSize(500, 230);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        initComponents();
        initEvents();
    }

    private void initComponents() {
        Titulo = new JLabel("Seleccione Clase a Gestionar");
        Titulo.setFont(new Font("Tahoma", Font.PLAIN, 21));
        Titulo.setBounds(115, 32, 274, 32);
        btnMascota = new JButton("Mascota");
        btnMascota.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnMascota.setBounds(50, 84, 90, 25);
        btnVeterinario = new JButton("Veterinario");
        btnVeterinario.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnVeterinario.setBounds(50, 135, 90, 25);
        btnVacuna = new JButton("Vacuna");
        btnVacuna.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnVacuna.setBounds(174, 135, 90, 25);
        btnConsulta = new JButton("Consulta");
        btnConsulta.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnConsulta.setBounds(174, 84, 90, 25);
        btnDueños = new JButton("Dueño");
        btnDueños.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnDueños.setBounds(295, 84, 90, 25);
        getContentPane().add(Titulo);
        getContentPane().add(btnMascota);
        getContentPane().add(btnVeterinario);
        getContentPane().add(btnVacuna);
        getContentPane().add(btnConsulta);
        getContentPane().add(btnDueños);
    }

    private void initEvents() {
        // [USER_ACTION_START:btnMascota]
btnMascota.addActionListener(e -> {
            formMascota form = new formMascota();
            form.setVisible(true);
        });
        // [USER_ACTION_END:btnMascota]

        // [USER_ACTION_START:btnVeterinario]
btnVeterinario.addActionListener(e -> {
            formVeterinario form = new formVeterinario();
            form.setVisible(true);
        });
        // [USER_ACTION_END:btnVeterinario]

        // [USER_ACTION_START:btnVacuna]
btnVacuna.addActionListener(e -> {
            formVacuna form = new formVacuna();
            form.setVisible(true);
        });
        // [USER_ACTION_END:btnVacuna]

        // [USER_ACTION_START:btnConsulta]
btnConsulta.addActionListener(e -> {
            formConsulta form = new formConsulta();
            form.setVisible(true);
        });
        // [USER_ACTION_END:btnConsulta]

        // [USER_ACTION_START:btnDueños]
        btnDueños.addActionListener(e -> {
            formdueños form = new formdueños();
            form.setVisible(true);
        });
        // [USER_ACTION_END:btnDueños]

    }
}