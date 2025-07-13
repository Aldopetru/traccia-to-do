package gui;

import controller.Controller;
import model.Bacheca;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Finestra per la creazione di un nuovo ToDo associato a una specifica bacheca.
 */
public class ToDoFrame extends JFrame {

    /**
     * Costruttore del frame per la creazione di un nuovo ToDo.
     *
     * @param username nome utente che crea il ToDo
     * @param bacheca  bacheca a cui sarà associato il ToDo
     * @param parent   frame genitore per aggiornare la lista
     */
    public ToDoFrame(String username, Bacheca bacheca, ToDoGestioneFrame parent) {
        setTitle("Nuovo ToDo per " + bacheca.titolo_b);
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 12));
        formPanel.setBackground(beige);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField titolo = new JTextField();
        JTextField url = new JTextField();

        // Bottone per aprire l'URL
        JButton apriUrl = new JButton("🌐 Apri");
        apriUrl.setBackground(sabbia);
        apriUrl.addActionListener(e -> {
            String link = url.getText().trim();
            if (!link.isEmpty() && (link.startsWith("http://") || link.startsWith("https://"))) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore apertura URL", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci un URL valido (http/https)", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });

        JTextArea descrizione = new JTextArea(3, 20);
        descrizione.setLineWrap(true);
        descrizione.setWrapStyleWord(true);
        JScrollPane descrizioneScroll = new JScrollPane(descrizione);
        JTextField data = new JTextField();
        JCheckBox stato = new JCheckBox("Completato");
        JTextField utentiCondivisi = new JTextField();
        JTextField ordine = new JTextField();

        // Selettore immagine
        JButton selezionaImmagine = new JButton("📁 Seleziona immagine");
        JLabel labelImmagine = new JLabel("Nessuna selezione");
        final String[] pathImmagine = {""};
        selezionaImmagine.setBackground(sabbia);
        selezionaImmagine.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                pathImmagine[0] = file.getAbsolutePath();
                labelImmagine.setText(file.getName());
            }
        });

        // Selettori colore
        final String[] coloreSfondo = {"#ffffff"};
        final String[] coloreTitolo = {"#000000"};
        JButton btnSfondo = new JButton("🎨 Sfondo");
        JButton btnTitolo = new JButton("🎨 Titolo");

        btnSfondo.setBackground(sabbia);
        btnTitolo.setBackground(sabbia);

        btnSfondo.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Scegli colore sfondo", Color.WHITE);
            if (c != null) coloreSfondo[0] = "#" + Integer.toHexString(c.getRGB()).substring(2);
        });

        btnTitolo.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Scegli colore titolo", Color.BLACK);
            if (c != null) coloreTitolo[0] = "#" + Integer.toHexString(c.getRGB()).substring(2);
        });

        JButton salva = new JButton("📝 Crea ToDo");
        salva.setBackground(sabbia);
        salva.setFont(new Font("SansSerif", Font.BOLD, 15));

        // Layout del form
        formPanel.add(new JLabel("Titolo *")); formPanel.add(titolo);
        formPanel.add(new JLabel("URL")); formPanel.add(url);
        formPanel.add(new JLabel("Apri URL")); formPanel.add(apriUrl);
        formPanel.add(new JLabel("Descrizione *")); formPanel.add(descrizioneScroll);
        formPanel.add(new JLabel("Data (yyyy-MM-dd) *")); formPanel.add(data);
        formPanel.add(new JLabel("Immagine")); formPanel.add(selezionaImmagine);
        formPanel.add(new JLabel("Selezionata:")); formPanel.add(labelImmagine);
        formPanel.add(new JLabel("Colore sfondo")); formPanel.add(btnSfondo);
        formPanel.add(new JLabel("Colore titolo")); formPanel.add(btnTitolo);
        formPanel.add(new JLabel("Utenti condivisi (virgola)")); formPanel.add(utentiCondivisi);
        formPanel.add(new JLabel("Ordine *")); formPanel.add(ordine);
        formPanel.add(new JLabel("")); formPanel.add(stato);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(beige);
        bottomPanel.add(salva);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Azione bottone "Crea ToDo"
        salva.addActionListener(e -> {
            try {
                if (titolo.getText().isEmpty() || descrizione.getText().isEmpty()
                        || data.getText().isEmpty() || ordine.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "I campi con * sono obbligatori.", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Controller.creaToDo(
                        titolo.getText(),
                        url.getText(),
                        descrizione.getText(),
                        LocalDate.parse(data.getText()),
                        pathImmagine[0],
                        coloreSfondo[0],
                        coloreTitolo[0],
                        stato.isSelected(),
                        Arrays.asList(utentiCondivisi.getText().split(",")),
                        Integer.parseInt(ordine.getText()),
                        username,
                        bacheca
                );

                parent.caricaToDo();
                JOptionPane.showMessageDialog(this, "ToDo creato con successo!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}













