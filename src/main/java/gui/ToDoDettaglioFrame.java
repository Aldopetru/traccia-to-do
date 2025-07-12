package gui;

import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

/**
 * Finestra che mostra i dettagli di un oggetto {@link ToDo}.
 */
public class ToDoDettaglioFrame extends JFrame {

    /**
     * Costruttore del frame che mostra i dettagli del ToDo specificato.
     *
     * @param todo l'oggetto {@link ToDo} di cui visualizzare i dettagli
     */
    public ToDoDettaglioFrame(ToDo todo) {
        setTitle("📄 Dettagli ToDo: " + todo.titolo_todo);
        setSize(620, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(245, 245, 220);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 12));
        panel.setBackground(beige);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Titolo:"));
        panel.add(new JLabel(nonVuoto(todo.titolo_todo)));

        panel.add(new JLabel("Autore:"));
        panel.add(new JLabel(nonVuoto(todo.username)));

        panel.add(new JLabel("URL:"));
        panel.add(linkLabel(todo.URL));

        panel.add(new JLabel("Descrizione:"));
        JTextArea descrizioneArea = new JTextArea(nonVuoto(todo.descrizione));
        descrizioneArea.setLineWrap(true);
        descrizioneArea.setWrapStyleWord(true);
        descrizioneArea.setEditable(false);
        descrizioneArea.setBackground(beige);
        JScrollPane descrScroll = new JScrollPane(descrizioneArea);
        panel.add(descrScroll);

        panel.add(new JLabel("Data Scadenza:"));
        panel.add(new JLabel(todo.datascadenza != null ? todo.datascadenza.toString() : "Nessuna data"));

        panel.add(new JLabel("Immagine:"));
        if (todo.image != null && !todo.image.isBlank()) {
            try {
                JPanel imageAndButton = new JPanel();
                imageAndButton.setLayout(new BoxLayout(imageAndButton, BoxLayout.X_AXIS));
                imageAndButton.setBackground(beige);
                imageAndButton.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // alzato leggermente

                ImageIcon icon = new ImageIcon(todo.image);
                Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(img));
                imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // spazio a destra
                imageAndButton.add(imageLabel);

                JButton enlargeBtn = new JButton("🔍 Ingrandisci");
                enlargeBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
                enlargeBtn.setBackground(new Color(222, 184, 135));
                enlargeBtn.addActionListener(e -> mostraImmagine(todo.image));
                imageAndButton.add(enlargeBtn);

                panel.add(imageAndButton);
            } catch (Exception e) {
                panel.add(new JLabel("Errore caricamento immagine"));
            }
        } else {
            panel.add(new JLabel("Nessuna immagine"));
        }


        panel.add(new JLabel("Colore Sfondo:"));
        panel.add(colorBox(todo.colore_sfondo));

        panel.add(new JLabel("Colore Titolo:"));
        panel.add(colorBox(todo.colore_titolo));

        panel.add(new JLabel("Utenti Condivisi:"));
        panel.add(new JLabel(todo.utenti_condivisi != null && !todo.utenti_condivisi.isEmpty()
                ? String.join(", ", todo.utenti_condivisi) : "Nessun utente"));

        panel.add(new JLabel("Ordine:"));
        panel.add(new JLabel(String.valueOf(todo.ordine)));

        panel.add(new JLabel("Stato:"));
        panel.add(new JLabel(todo.Stato_todo ? "✅ Completato" : "⏳ Incompleto"));

        setContentPane(panel);
        setVisible(true);
    }

    /**
     * Restituisce una stringa non vuota, oppure "Nessun dato" se null o vuota.
     *
     * @param s la stringa da controllare
     * @return la stringa o "Nessun dato"
     */
    private String nonVuoto(String s) {
        return (s == null || s.isBlank()) ? "Nessun dato" : s;
    }

    /**
     * Restituisce un'etichetta con un riquadro colorato.
     *
     * @param colorStr codice esadecimale del colore
     * @return JLabel con il colore applicato
     */
    private JLabel colorBox(String colorStr) {
        JPanel colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(30, 20));
        try {
            colorPanel.setBackground(Color.decode(colorStr));
        } catch (Exception e) {
            colorPanel.setBackground(Color.LIGHT_GRAY);
        }
        JLabel label = new JLabel();
        label.setLayout(new BorderLayout());
        label.add(colorPanel, BorderLayout.CENTER);
        return label;
    }

    /**
     * Restituisce un'etichetta cliccabile per aprire un URL nel browser.
     *
     * @param url l'URL da aprire
     * @return JLabel cliccabile con link
     */
    private JLabel linkLabel(String url) {
        if (url == null || url.isBlank()) return new JLabel("Nessun URL");

        JLabel label = new JLabel("<html><a href='#'>" + url + "</a></html>");
        label.setForeground(Color.BLUE.darker());
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Errore apertura URL", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "URL non valido", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return label;
    }

    /**
     * Apre una finestra con l'immagine ingrandita.
     *
     * @param path percorso dell'immagine
     */
    private void mostraImmagine(String path) {
        JFrame frame = new JFrame("🖼️ Immagine Ingrandita");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        try {
            ImageIcon icon = new ImageIcon(path);
            JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(550, 500, Image.SCALE_SMOOTH)));
            frame.add(new JScrollPane(label));
        } catch (Exception e) {
            frame.add(new JLabel("Errore nel caricamento dell'immagine."));
        }

        frame.setVisible(true);
    }
}










