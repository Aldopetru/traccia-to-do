package gui;

import controller.Controller;
import model.Bacheca;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

public class ToDoFrame extends JFrame {
    public ToDoFrame(String username, Bacheca bacheca, ToDoGestioneFrame parent) {
        setTitle("Nuovo ToDo per " + bacheca.titolo_b);
        setSize(450, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(beige);
        p.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JTextField titolo = new JTextField(20);
        JTextField url = new JTextField(20);
        JTextArea descrizione = new JTextArea(3, 20);
        JTextField data = new JTextField(10);
        JTextField immagine = new JTextField(20);
        JTextField coloreSfondo = new JTextField(10);
        JTextField coloreTitolo = new JTextField(10);
        JCheckBox stato = new JCheckBox("Completato");
        JTextField utentiCondivisi = new JTextField(20);
        JTextField ordine = new JTextField(5);
        JButton salva = new JButton("📝 Crea ToDo");

        salva.setBackground(sabbia);
        salva.setFont(new Font("SansSerif", Font.BOLD, 14));
        salva.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(new JLabel("Titolo *")); p.add(titolo);
        p.add(new JLabel("URL")); p.add(url);
        p.add(new JLabel("Descrizione *")); p.add(new JScrollPane(descrizione));
        p.add(new JLabel("Data (yyyy-MM-dd) *")); p.add(data);
        p.add(new JLabel("Immagine")); p.add(immagine);
        p.add(new JLabel("Colore sfondo")); p.add(coloreSfondo);
        p.add(new JLabel("Colore titolo")); p.add(coloreTitolo);
        p.add(stato);
        p.add(new JLabel("Utenti condivisi (virgola)")); p.add(utentiCondivisi);
        p.add(new JLabel("Ordine *")); p.add(ordine);
        p.add(Box.createVerticalStrut(10));
        p.add(salva);

        setContentPane(p);
        setVisible(true);

        salva.addActionListener(e -> {
            try {
                if (titolo.getText().isEmpty() || descrizione.getText().isEmpty()
                        || data.getText().isEmpty() || ordine.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "I campi * sono obbligatori.", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Controller.creaToDo(
                        titolo.getText(),
                        url.getText(),
                        descrizione.getText(),
                        LocalDate.parse(data.getText()),
                        immagine.getText(),
                        coloreSfondo.getText(),
                        coloreTitolo.getText(),
                        stato.isSelected(),
                        Arrays.asList(utentiCondivisi.getText().split(",")),
                        Integer.parseInt(ordine.getText()),
                        username,
                        bacheca
                );

                parent.caricaToDo();
                JOptionPane.showMessageDialog(this, "ToDo creato!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

