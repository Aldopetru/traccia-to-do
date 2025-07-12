package gui;

import controller.Controller;
import model.Bacheca;
import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Finestra principale per la gestione delle Bacheche e la ricerca globale dei ToDo.
 */
public class BachecaFrame extends JFrame {
    private JComboBox<Bacheca.tipoBacheca> comboTipo;
    private JTextArea txtDescrizione;
    private JTextField txtSearchGlobal;
    private JTextField txtDataScadenza;
    private JButton btnCreaBacheca;
    private JButton btnModifica;
    private JButton btnElimina;
    private JButton btnSearchGlobal;
    private JButton btnOggi;
    private JButton btnFiltraData;
    private JButton btnLogout;
    private DefaultListModel<Bacheca> bachecaListModel;
    private JList<Bacheca> bachecaList;
    protected String username;

    /**
     * Costruttore della finestra BachecaFrame.
     *
     * @param username nome utente autenticato
     */
    public BachecaFrame(String username) {
        this.username = username;

        setTitle("🎯 Gestione Bacheche - " + username);
        setSize(850, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(beige);

        // ---------------------------------- TOP: Barra Logout ----------------------------------
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(beige);

        btnLogout = new JButton("🚪 Logout");
        btnLogout.setBackground(new Color(200, 150, 120));
        btnLogout.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLogout.setFocusPainted(false);

        btnLogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logout avvenuto con successo!");
            dispose();
            JFrame login = new JFrame("Login");
            login.setDefaultCloseOperation(EXIT_ON_CLOSE);
            login.setContentPane(new LoginForm(login).getPanel());
            login.setSize(300, 300);
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        });

        topBar.add(btnLogout, BorderLayout.EAST);
        main.add(topBar, BorderLayout.NORTH);

        // ---------------------------------- CENTRO: Sinistra e Destra ----------------------------------
        JPanel centro = new JPanel(new GridLayout(1, 2));
        centro.setBackground(beige);

        // ------------------ SINISTRA: Gestione Bacheche ------------------
        JPanel sinistra = new JPanel(new BorderLayout(10, 10));
        sinistra.setBackground(beige);
        sinistra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

        JPanel creaPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        creaPanel.setBackground(beige);
        creaPanel.setBorder(BorderFactory.createTitledBorder("➕ Crea nuova Bacheca"));

        comboTipo = new JComboBox<>(Bacheca.tipoBacheca.values());
        txtDescrizione = new JTextArea(2, 15);
        txtDescrizione.setLineWrap(true);
        txtDescrizione.setWrapStyleWord(true);
        JScrollPane descrScroll = new JScrollPane(txtDescrizione);

        creaPanel.add(new JLabel("Tipo *"));
        creaPanel.add(comboTipo);
        creaPanel.add(new JLabel("Descrizione"));
        creaPanel.add(descrScroll);

        bachecaListModel = new DefaultListModel<>();
        bachecaList = new JList<>(bachecaListModel);
        bachecaList.setFont(new Font("SansSerif", Font.BOLD, 15));
        JScrollPane listScroll = new JScrollPane(bachecaList);
        listScroll.setPreferredSize(new Dimension(250, 120));
        listScroll.setBorder(BorderFactory.createTitledBorder("📁 Le tue Bacheche"));

        btnCreaBacheca = new JButton("✅ Crea");
        btnModifica = new JButton("✏️ Modifica");
        btnElimina = new JButton("🗑️ Elimina");

        for (JButton b : new JButton[]{btnCreaBacheca, btnModifica, btnElimina}) {
            b.setBackground(sabbia);
            b.setFocusPainted(false);
            b.setFont(new Font("SansSerif", Font.BOLD, 13));
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBackground(beige);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        buttonPanel.add(btnCreaBacheca);
        buttonPanel.add(btnModifica);
        buttonPanel.add(btnElimina);

        sinistra.add(creaPanel, BorderLayout.NORTH);
        sinistra.add(listScroll, BorderLayout.CENTER);
        sinistra.add(buttonPanel, BorderLayout.SOUTH);

        // ------------------ DESTRA: Ricerca ToDo ------------------
        JPanel destra = new JPanel(new GridLayout(6, 1, 10, 10));
        destra.setBackground(beige);
        destra.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 10));

        txtSearchGlobal = new JTextField();
        btnSearchGlobal = new JButton("🔍 Cerca per Titolo");

        btnOggi = new JButton("📅 Scadenza oggi");
        txtDataScadenza = new JTextField();
        btnFiltraData = new JButton("📆 Cerca entro Data");

        for (JButton b : new JButton[]{btnSearchGlobal, btnOggi, btnFiltraData}) {
            b.setBackground(sabbia);
            b.setFocusPainted(false);
            b.setFont(new Font("SansSerif", Font.BOLD, 13));
        }

        destra.add(new JLabel("Titolo ToDo"));
        destra.add(txtSearchGlobal);
        destra.add(btnSearchGlobal);
        destra.add(btnOggi);
        destra.add(txtDataScadenza);
        destra.add(btnFiltraData);

        centro.add(sinistra);
        centro.add(destra);
        main.add(centro, BorderLayout.CENTER);
        setContentPane(main);

        // ------------------ LOGICA ------------------
        Controller.getBachechePerUtente(username).forEach(bachecaListModel::addElement);

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
                String nuova = JOptionPane.showInputDialog(this, "Modifica descrizione:", selezionata.descrizione);
                if (nuova != null && !nuova.isBlank()) {
                    selezionata.descrizione = nuova;
                    Controller.aggiornaDescrizioneBacheca(selezionata.getId(), nuova);
                    bachecaList.repaint();
                    JOptionPane.showMessageDialog(this, "Descrizione aggiornata!");
                }
            }
        });

        btnElimina.addActionListener(e -> {
            Bacheca selezionata = bachecaList.getSelectedValue();
            if (selezionata != null) {
                int conferma = JOptionPane.showConfirmDialog(this, "Confermi eliminazione?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    Controller.eliminaBacheca(username, selezionata);
                    bachecaListModel.removeElement(selezionata);
                }
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

        btnSearchGlobal.addActionListener(e -> {
            String query = txtSearchGlobal.getText().toLowerCase().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci un testo da cercare.");
                return;
            }

            List<ToDo> tutti = Controller.getListaToDo(username);
            List<ToDo> risultati = new ArrayList<>();
            for (ToDo t : tutti) {
                boolean utenteCoinvolto = t.username.equalsIgnoreCase(username)
                        || t.utenti_condivisi.stream().map(String::trim)
                        .map(String::toLowerCase)
                        .toList().contains(username.toLowerCase());
                if (utenteCoinvolto && t.titolo_todo != null && t.titolo_todo.toLowerCase().contains(query)) {
                    risultati.add(t);
                }
            }
            mostraRisultati(risultati, "Risultati per titolo: " + query);
        });

        btnOggi.addActionListener(e -> {
            LocalDate oggi = LocalDate.now();
            List<ToDo> tutti = Controller.getListaToDo(username);
            List<ToDo> risultati = new ArrayList<>();
            for (ToDo t : tutti) {
                if ((t.username.equals(username) || t.utenti_condivisi.contains(username)) &&
                        t.datascadenza != null && oggi.equals(t.datascadenza)) {
                    risultati.add(t);
                }
            }
            mostraRisultati(risultati, "📅 ToDo in scadenza oggi");
        });

        btnFiltraData.addActionListener(e -> {
            try {
                LocalDate limite = LocalDate.parse(txtDataScadenza.getText().trim());
                List<ToDo> tutti = Controller.getListaToDo(username);
                List<ToDo> risultati = new ArrayList<>();
                for (ToDo t : tutti) {
                    if ((t.username.equals(username) || t.utenti_condivisi.contains(username)) &&
                            t.datascadenza != null && !t.datascadenza.isAfter(limite)) {
                        risultati.add(t);
                    }
                }
                mostraRisultati(risultati, "📆 ToDo entro il " + limite);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data non valida. Usa formato: yyyy-MM-dd", "Errore", JOptionPane.WARNING_MESSAGE);
            }
        });

        setVisible(true);
    }

    /**
     * Mostra i risultati della ricerca dei ToDo in una finestra separata.
     *
     * @param risultati lista dei ToDo trovati
     * @param titolo    titolo della finestra
     */
    private void mostraRisultati(List<ToDo> risultati, String titolo) {
        if (risultati.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun ToDo trovato.");
            return;
        }

        DefaultListModel<String> model = new DefaultListModel<>();
        for (ToDo t : risultati) {
            String bachecaNome = (t.getBacheca() != null) ? t.getBacheca().toString() : "Sconosciuta";
            String dataStr = (t.datascadenza != null) ? t.datascadenza.toString() : "N.D.";
            model.addElement(t.titolo_todo + " - Scade: " + dataStr + " (" + bachecaNome + ")");
        }

        JList<String> resultList = new JList<>(model);
        resultList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(resultList);
        scroll.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(this, scroll, titolo, JOptionPane.PLAIN_MESSAGE);
    }
}












