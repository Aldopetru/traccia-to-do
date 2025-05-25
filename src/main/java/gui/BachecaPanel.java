package gui;

import controller.Controller;
import model.Bacheca;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BachecaPanel extends JPanel {
    private JComboBox<Bacheca.tipoBacheca> comboTipo;
    private JTextArea txtDescrizione;
    private JButton btnCrea;
    private DefaultListModel<Bacheca> model;
    private JList<Bacheca> lista;
    private String username;

    public BachecaPanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 220)); // beige

        comboTipo = new JComboBox<>(Bacheca.tipoBacheca.values());
        txtDescrizione = new JTextArea(3, 20);
        btnCrea = new JButton("➕ Crea Bacheca");

        btnCrea.setBackground(new Color(222, 184, 135));
        btnCrea.setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(245, 245, 220));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nuova Bacheca"));
        formPanel.add(new JLabel("Tipo *"));
        formPanel.add(comboTipo);
        formPanel.add(new JLabel("Descrizione (opzionale)"));
        formPanel.add(new JScrollPane(txtDescrizione));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(btnCrea);

        model = new DefaultListModel<>();
        lista = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Bacheche esistenti"));

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        List<Bacheca> bacheche = Controller.getBachechePerUtente(username);
        for (Bacheca b : bacheche) {
            model.addElement(b);
        }

        btnCrea.addActionListener(e -> {
            try {
                Bacheca.tipoBacheca tipo = (Bacheca.tipoBacheca) comboTipo.getSelectedItem();
                String descrizione = txtDescrizione.getText();
                Bacheca nuova = Controller.creaBacheca(username, tipo, descrizione);
                model.addElement(nuova);
                txtDescrizione.setText("");
                JOptionPane.showMessageDialog(this, "Bacheca creata!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}


