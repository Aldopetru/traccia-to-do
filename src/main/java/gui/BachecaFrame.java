package gui;

import controller.Controller;
import model.Bacheca;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BachecaFrame extends JFrame {
    private JComboBox<Bacheca.tipoBacheca> comboTipo;
    private JTextArea txtDescrizione;
    private JButton btnCreaBacheca, btnModifica, btnElimina;
    private DefaultListModel<Bacheca> bachecaListModel;
    private JList<Bacheca> bachecaList;
    private String username;

    public BachecaFrame(String username) {
        this.username = username;

        setTitle("Le tue Bacheche - " + username);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.setBackground(beige);

        comboTipo = new JComboBox<>(Bacheca.tipoBacheca.values());
        txtDescrizione = new JTextArea(3, 20);
        txtDescrizione.setLineWrap(true);
        btnCreaBacheca = new JButton("➕ Crea Bacheca");
        styleRoundedButton(btnCreaBacheca, sabbia);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(beige);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Crea nuova Bacheca"));

        inputPanel.add(new JLabel("Tipo Bacheca *"));
        inputPanel.add(comboTipo);
        inputPanel.add(new JLabel("Descrizione (opzionale)"));
        inputPanel.add(new JScrollPane(txtDescrizione));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(btnCreaBacheca);

        bachecaListModel = new DefaultListModel<>();
        bachecaList = new JList<>(bachecaListModel);
        JScrollPane listScroll = new JScrollPane(bachecaList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Le tue bacheche"));

        btnModifica = new JButton("✏️ Modifica");
        btnElimina = new JButton("🗑️ Elimina");
        styleRoundedButton(btnModifica, sabbia);
        styleRoundedButton(btnElimina, sabbia);

        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(beige);
        actionPanel.add(btnModifica);
        actionPanel.add(btnElimina);

        panelMain.add(inputPanel, BorderLayout.NORTH);
        panelMain.add(listScroll, BorderLayout.CENTER);
        panelMain.add(actionPanel, BorderLayout.SOUTH);

        setContentPane(panelMain);

        List<Bacheca> bacheche = Controller.getBachechePerUtente(username);
        bacheche.forEach(bachecaListModel::addElement);

        btnCreaBacheca.addActionListener(e -> {
            try {
                Bacheca.tipoBacheca tipo = (Bacheca.tipoBacheca) comboTipo.getSelectedItem();
                String descrizione = txtDescrizione.getText();
                Bacheca nuova = Controller.creaBacheca(username, tipo, descrizione);
                bachecaListModel.addElement(nuova);
                txtDescrizione.setText("");
                new ToDoGestioneFrame(username, nuova);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnModifica.addActionListener(e -> {
            Bacheca selezionata = bachecaList.getSelectedValue();
            if (selezionata != null) {
                String nuovaDescrizione = JOptionPane.showInputDialog(this, "Modifica descrizione:", selezionata.descrizione);
                if (nuovaDescrizione != null && !nuovaDescrizione.isBlank()) {
                    selezionata.descrizione = nuovaDescrizione;
                    bachecaList.repaint();
                    JOptionPane.showMessageDialog(this, "Descrizione aggiornata!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona una bacheca da modificare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnElimina.addActionListener(e -> {
            Bacheca selezionata = bachecaList.getSelectedValue();
            if (selezionata != null) {
                int conferma = JOptionPane.showConfirmDialog(this, "Confermi eliminazione di \"" + selezionata.titolo_b + "\"?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    Controller.eliminaBacheca(username, selezionata);
                    bachecaListModel.removeElement(selezionata);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona una bacheca da eliminare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });

        bachecaList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Bacheca selezionata = bachecaList.getSelectedValue();
                if (selezionata != null) {
                    new ToDoGestioneFrame(username, selezionata);
                }
            }
        });

        setVisible(true);
    }

    private void styleRoundedButton(JButton button, Color background) {
        button.setFocusPainted(false);
        button.setBackground(background);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
    }
}

