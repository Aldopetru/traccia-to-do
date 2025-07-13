package gui;

import controller.Controller;
import model.Bacheca;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Pannello per la creazione di un nuovo ToDo associato a una bacheca.
 */
public class ToDoPanel extends JPanel {

    private JTextField txtTitoloToDo;
    private JTextField txtURL;
    private JTextField txtData;
    private JTextField txtImmagine;
    private JTextField txtUtentiCondivisi;
    private JTextField txtOrdine;
    private JTextArea txtDescrizioneToDo;
    private JCheckBox checkStato;
    private JButton btnCreaToDo;
    private JButton btnColoreSfondo;
    private JButton btnColoreTitolo;
    private String username;
    private Bacheca bacheca;

    /**
     * Costruttore del pannello per la creazione di un nuovo ToDo.
     *
     * @param username nome utente che crea il ToDo
     * @param bacheca  bacheca alla quale associare il ToDo
     */
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
        txtUtentiCondivisi = new JTextField(20);
        txtOrdine = new JTextField(5);
        checkStato = new JCheckBox("Completato");

        // Pulsanti colore
        btnColoreSfondo = new JButton("Scegli colore");
        btnColoreTitolo = new JButton("Scegli colore");
        btnColoreSfondo.setBackground(Color.WHITE);
        btnColoreTitolo.setBackground(Color.BLACK);
        btnColoreSfondo.setPreferredSize(new Dimension(120, 25));
        btnColoreTitolo.setPreferredSize(new Dimension(120, 25));

        // Selettori colore
        btnColoreSfondo.addActionListener(e -> {
            Color scelto = JColorChooser.showDialog(this, "Scegli colore sfondo", btnColoreSfondo.getBackground());
            if (scelto != null) btnColoreSfondo.setBackground(scelto);
        });

        btnColoreTitolo.addActionListener(e -> {
            Color scelto = JColorChooser.showDialog(this, "Scegli colore titolo", btnColoreTitolo.getBackground());
            if (scelto != null) btnColoreTitolo.setBackground(scelto);
        });

        btnCreaToDo = new JButton("📝 Crea ToDo");
        btnCreaToDo.setBackground(new Color(222, 184, 135));
        btnCreaToDo.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCreaToDo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Costruzione interfaccia
        add(new JLabel("Titolo *")); add(txtTitoloToDo);
        add(new JLabel("URL")); add(txtURL);
        add(new JLabel("Descrizione *")); add(new JScrollPane(txtDescrizioneToDo));
        add(new JLabel("Data Scadenza (yyyy-MM-dd) *")); add(txtData);
        add(new JLabel("Immagine")); add(txtImmagine);

        JPanel panelSfondo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSfondo.setBackground(new Color(245, 245, 220));
        panelSfondo.add(new JLabel("Colore sfondo")); panelSfondo.add(btnColoreSfondo);
        add(panelSfondo);

        JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitolo.setBackground(new Color(245, 245, 220));
        panelTitolo.add(new JLabel("Colore titolo")); panelTitolo.add(btnColoreTitolo);
        add(panelTitolo);

        add(checkStato);
        add(new JLabel("Utenti Condivisi (virgola)")); add(txtUtentiCondivisi);
        add(new JLabel("Ordine *")); add(txtOrdine);
        add(Box.createVerticalStrut(10));
        add(btnCreaToDo);

        // Azione creazione ToDo
        btnCreaToDo.addActionListener(e -> {
            try {
                if (txtTitoloToDo.getText().isEmpty() || txtDescrizioneToDo.getText().isEmpty()
                        || txtData.getText().isEmpty() || txtOrdine.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "I campi * sono obbligatori.", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String coloreSfondo = "#" + Integer.toHexString(btnColoreSfondo.getBackground().getRGB()).substring(2);
                String coloreTitolo = "#" + Integer.toHexString(btnColoreTitolo.getBackground().getRGB()).substring(2);

                Controller.creaToDo(
                        txtTitoloToDo.getText(),
                        txtURL.getText(),
                        txtDescrizioneToDo.getText(),
                        LocalDate.parse(txtData.getText()),
                        txtImmagine.getText(),
                        coloreSfondo,
                        coloreTitolo,
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

    /**
     * Pulisce tutti i campi del form dopo la creazione del ToDo.
     */
    private void pulisciCampi() {
        txtTitoloToDo.setText("");
        txtURL.setText("");
        txtDescrizioneToDo.setText("");
        txtData.setText("");
        txtImmagine.setText("");
        txtUtentiCondivisi.setText("");
        txtOrdine.setText("");
        checkStato.setSelected(false);
        btnColoreSfondo.setBackground(Color.WHITE);
        btnColoreTitolo.setBackground(Color.BLACK);
    }
}


