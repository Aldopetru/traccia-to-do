package gui;

import controller.Controller;
import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Finestra per la modifica di un ToDo esistente.
 */
public class ToDoModificaFrame extends JFrame {

    /**
     * Costruttore della finestra di modifica di un ToDo.
     *
     * @param t      il ToDo da modificare
     * @param parent il frame genitore che gestisce la lista dei ToDo
     */
    public ToDoModificaFrame(ToDo t, ToDoGestioneFrame parent) {
        setTitle("Modifica ToDo");
        setSize(500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        JPanel p = new JPanel(new GridLayout(0, 2, 10, 10));
        p.setBackground(beige);
        p.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField titolo = new JTextField(t.titolo_todo, 20);
        JTextField url = new JTextField(t.URL, 20);

        // Bottone per apertura URL
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

        JTextArea descrizione = new JTextArea(t.descrizione, 3, 20);
        descrizione.setLineWrap(true);
        descrizione.setWrapStyleWord(true);
        JScrollPane descrizioneScroll = new JScrollPane(descrizione);
        JTextField data = new JTextField(t.datascadenza.toString(), 10);
        JCheckBox stato = new JCheckBox("Completato", t.Stato_todo);
        JTextField utentiCondivisi = new JTextField(String.join(",", t.utenti_condivisi), 20);
        JTextField ordine = new JTextField(String.valueOf(t.ordine), 5);

        // Rendi il campo utentiCondivisi disabilitato se non sei l'autore
        boolean isAutore = parent.getUsername().equals(t.username);
        utentiCondivisi.setEditable(isAutore);

        // Selezione immagine
        JButton selezionaImmagine = new JButton("📁 Seleziona");
        JLabel labelImmagine = new JLabel(t.image == null || t.image.isEmpty() ? "Nessuna selezionata" : new File(t.image).getName());
        final String[] pathImmagine = {t.image};

        selezionaImmagine.setBackground(sabbia);
        selezionaImmagine.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                pathImmagine[0] = file.getAbsolutePath();
                labelImmagine.setText(file.getName());
            }
        });

        // Selettori colori
        final String[] coloreSfondo = {t.colore_sfondo != null ? t.colore_sfondo : "#ffffff"};
        final String[] coloreTitolo = {t.colore_titolo != null ? t.colore_titolo : "#000000"};
        JButton btnSfondo = new JButton("🎨 Sfondo");
        JButton btnTitolo = new JButton("🎨 Titolo");

        btnSfondo.setBackground(sabbia);
        btnTitolo.setBackground(sabbia);

        btnSfondo.addActionListener(e -> {
            Color defaultColor = Color.WHITE;
            try {
                if (coloreSfondo[0] != null && !coloreSfondo[0].isEmpty()) {
                    defaultColor = Color.decode(coloreSfondo[0]);
                }
            } catch (NumberFormatException ex) {
                // default rimane bianco
            }
            Color c = JColorChooser.showDialog(this, "Colore sfondo", defaultColor);
            if (c != null) coloreSfondo[0] = "#" + Integer.toHexString(c.getRGB()).substring(2);
        });

        btnTitolo.addActionListener(e -> {
            Color defaultColor = Color.BLACK;
            try {
                if (coloreTitolo[0] != null && !coloreTitolo[0].isEmpty()) {
                    defaultColor = Color.decode(coloreTitolo[0]);
                }
            } catch (NumberFormatException ex) {
                // default rimane nero
            }
            Color c = JColorChooser.showDialog(this, "Colore titolo", defaultColor);
            if (c != null) coloreTitolo[0] = "#" + Integer.toHexString(c.getRGB()).substring(2);
        });

        // Bottone salvataggio
        JButton salva = new JButton("💾 Salva modifiche");
        salva.setBackground(sabbia);
        salva.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Layout
        p.add(new JLabel("Titolo *")); p.add(titolo);
        p.add(new JLabel("URL")); p.add(url);
        p.add(new JLabel("Apri URL")); p.add(apriUrl);
        p.add(new JLabel("Descrizione *")); p.add(descrizioneScroll);
        p.add(new JLabel("Data (yyyy-MM-dd) *")); p.add(data);
        p.add(new JLabel("Immagine")); p.add(selezionaImmagine);
        p.add(new JLabel("")); p.add(labelImmagine);
        p.add(new JLabel("Colore sfondo")); p.add(btnSfondo);
        p.add(new JLabel("Colore titolo")); p.add(btnTitolo);
        p.add(new JLabel("Completato")); p.add(stato);
        p.add(new JLabel("Utenti condivisi")); p.add(utentiCondivisi);
        p.add(new JLabel("Ordine *")); p.add(ordine);
        p.add(new JLabel("")); p.add(salva);

        setContentPane(p);
        setVisible(true);

        // Salvataggio modifiche
        salva.addActionListener(e -> {
            try {
                t.titolo_todo = titolo.getText();
                t.URL = url.getText();
                t.descrizione = descrizione.getText();
                t.datascadenza = LocalDate.parse(data.getText());
                t.image = pathImmagine[0];
                t.colore_sfondo = coloreSfondo[0];
                t.colore_titolo = coloreTitolo[0];
                t.Stato_todo = stato.isSelected();
                t.ordine = Integer.parseInt(ordine.getText());

                // Solo autore può modificare la lista utenti condivisi
                if (isAutore) {
                    t.utenti_condivisi = Arrays.asList(utentiCondivisi.getText().split(","));
                }

                Controller.aggiornaToDo(t);
                parent.caricaToDo();
                JOptionPane.showMessageDialog(this, "ToDo aggiornato!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
















