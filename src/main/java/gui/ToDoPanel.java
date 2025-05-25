package gui;

import controller.Controller;
import model.Bacheca;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

public class ToDoPanel extends JPanel {
    private JTextField txtTitoloToDo, txtURL, txtData, txtImmagine, txtColoreSfondo, txtColoreTitolo, txtUtentiCondivisi, txtOrdine;
    private JTextArea txtDescrizioneToDo;
    private JCheckBox checkStato;
    private JButton btnCreaToDo;
    private String username;
    private Bacheca bacheca;

    public ToDoPanel(String username, Bacheca bacheca) {
        this.username = username;
        this.bacheca = bacheca;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 220)); // beige
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtTitoloToDo = new JTextField(20);
        txtURL = new JTextField(20);
        txtDescrizioneToDo = new JTextArea(3, 20);
        txtData = new JTextField(10); // yyyy-MM-dd
        txtImmagine = new JTextField(20);
        txtColoreSfondo = new JTextField(10);
        txtColoreTitolo = new JTextField(10);
        checkStato = new JCheckBox("Completato");
        txtUtentiCondivisi = new JTextField(20);
        txtOrdine = new JTextField(5);
        btnCreaToDo = new JButton("📝 Crea ToDo");

        btnCreaToDo.setBackground(new Color(222, 184, 135)); // sabbia
        btnCreaToDo.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCreaToDo.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(new JLabel("Titolo *")); add(txtTitoloToDo);
        add(new JLabel("URL")); add(txtURL);
        add(new JLabel("Descrizione *")); add(new JScrollPane(txtDescrizioneToDo));
        add(new JLabel("Data Scadenza (yyyy-MM-dd) *")); add(txtData);
        add(new JLabel("Immagine")); add(txtImmagine);
        add(new JLabel("Colore Sfondo")); add(txtColoreSfondo);
        add(new JLabel("Colore Titolo")); add(txtColoreTitolo);
        add(checkStato);
        add(new JLabel("Utenti Condivisi (virgola)")); add(txtUtentiCondivisi);
        add(new JLabel("Ordine *")); add(txtOrdine);
        add(Box.createVerticalStrut(10));
        add(btnCreaToDo);

        btnCreaToDo.addActionListener(e -> {
            try {
                if (txtTitoloToDo.getText().isEmpty() || txtDescrizioneToDo.getText().isEmpty()
                        || txtData.getText().isEmpty() || txtOrdine.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "I campi * sono obbligatori.", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Controller.creaToDo(
                        txtTitoloToDo.getText(),
                        txtURL.getText(),
                        txtDescrizioneToDo.getText(),
                        LocalDate.parse(txtData.getText()),
                        txtImmagine.getText(),
                        txtColoreSfondo.getText(),
                        txtColoreTitolo.getText(),
                        checkStato.isSelected(),
                        Arrays.asList(txtUtentiCondivisi.getText().split(",")),
                        Integer.parseInt(txtOrdine.getText()),
                        username,
                        bacheca
                );

                JOptionPane.showMessageDialog(this, "ToDo creato con successo!");
                pulisciCampi();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void pulisciCampi() {
        txtTitoloToDo.setText("");
        txtURL.setText("");
        txtDescrizioneToDo.setText("");
        txtData.setText("");
        txtImmagine.setText("");
        txtColoreSfondo.setText("");
        txtColoreTitolo.setText("");
        checkStato.setSelected(false);
        txtUtentiCondivisi.setText("");
        txtOrdine.setText("");
    }
}

