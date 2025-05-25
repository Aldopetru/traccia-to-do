package gui;

import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

public class ToDoModificaFrame extends JFrame {
    public ToDoModificaFrame(ToDo t) {
        setTitle("Modifica ToDo");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(beige);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JTextField titolo = new JTextField(t.titolo_todo, 20);
        JTextField url = new JTextField(t.URL, 20);
        JTextArea descrizione = new JTextArea(t.descrizione, 3, 20);
        JTextField data = new JTextField(t.datascadenza.toString(), 10);
        JTextField immagine = new JTextField(t.image, 20);
        JTextField coloreSfondo = new JTextField(t.colore_sfondo, 10);
        JTextField coloreTitolo = new JTextField(t.colore_titolo, 10);
        JCheckBox stato = new JCheckBox("Completato", t.Stato_todo);
        JTextField utentiCondivisi = new JTextField(String.join(",", t.utenti_condivisi), 20);
        JTextField ordine = new JTextField(String.valueOf(t.ordine), 5);
        JButton salva = new JButton("💾 Salva");

        salva.setBackground(sabbia);
        salva.setFont(new Font("SansSerif", Font.BOLD, 14));
        salva.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(new JLabel("Titolo *")); panel.add(titolo);
        panel.add(new JLabel("URL")); panel.add(url);
        panel.add(new JLabel("Descrizione *")); panel.add(new JScrollPane(descrizione));
        panel.add(new JLabel("Data *")); panel.add(data);
        panel.add(new JLabel("Immagine")); panel.add(immagine);
        panel.add(new JLabel("Colore Sfondo")); panel.add(coloreSfondo);
        panel.add(new JLabel("Colore Titolo")); panel.add(coloreTitolo);
        panel.add(stato);
        panel.add(new JLabel("Utenti Condivisi")); panel.add(utentiCondivisi);
        panel.add(new JLabel("Ordine *")); panel.add(ordine);
        panel.add(Box.createVerticalStrut(10));
        panel.add(salva);

        setContentPane(panel);
        setVisible(true);

        salva.addActionListener(e -> {
            try {
                t.titolo_todo = titolo.getText();
                t.URL = url.getText();
                t.descrizione = descrizione.getText();
                t.datascadenza = LocalDate.parse(data.getText());
                t.image = immagine.getText();
                t.colore_sfondo = coloreSfondo.getText();
                t.colore_titolo = coloreTitolo.getText();
                t.Stato_todo = stato.isSelected();
                t.utenti_condivisi = Arrays.asList(utentiCondivisi.getText().split(","));
                t.ordine = Integer.parseInt(ordine.getText());

                JOptionPane.showMessageDialog(this, "ToDo aggiornato!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        });
    }
}


