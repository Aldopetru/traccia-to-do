package gui;

import controller.Controller;
import model.Bacheca;
import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

/**
 * Finestra principale per la gestione dei ToDo associati a una specifica bacheca.
 */
public class ToDoGestioneFrame extends JFrame {
    private DefaultListModel<ToDo> toDoListModel;
    private JList<ToDo> toDoList;
    private JButton btnNuovo;
    private JButton btnModifica;
    private JButton btnElimina;
    private JButton btnChecklist;
    private JButton btnSposta;
    private JButton btnDettagli;
    private JButton btnCondivisi;
    private String username;
    private Bacheca bacheca;
    private boolean visualizzaCondivisi = false;

    /**
     * Costruttore della finestra per la gestione dei ToDo.
     *
     * @param username nome utente
     * @param bacheca  bacheca di riferimento
     */
    public ToDoGestioneFrame(String username, Bacheca bacheca) {
        this.username = username;
        this.bacheca = bacheca;

        setTitle("ToDo - " + bacheca.titolo_b);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(250, 250, 240);
        Color sabbia = new Color(210, 180, 140);
        getContentPane().setBackground(beige);

        // Lista e rendering personalizzato
        toDoListModel = new DefaultListModel<>();
        toDoList = new JList<>(toDoListModel);
        toDoList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        toDoList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ToDo t) {
                    String testo = (t.Stato_todo ? "✅ " : "") + t.titolo_todo + " (" + t.datascadenza + ")";
                    label.setText(testo);
                    try {
                        if (t.datascadenza.isBefore(java.time.LocalDate.now()) && !t.Stato_todo) {
                            label.setForeground(Color.RED);
                        } else {
                            label.setForeground(Color.decode(t.colore_titolo));
                        }
                        label.setBackground(Color.decode(t.colore_sfondo));
                        if (isSelected) {
                            label.setBackground(label.getBackground().darker());
                        }
                    } catch (Exception ignored) {
                        label.setForeground(Color.BLACK);
                        label.setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
                    }
                }
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(toDoList);

        // Pulsanti
        btnNuovo = new JButton("➕ Nuovo");
        btnModifica = new JButton("✏️ Modifica");
        btnElimina = new JButton("🗑️ Elimina");
        btnChecklist = new JButton("📋 Checklist");
        btnSposta = new JButton("🔁 Sposta");
        btnDettagli = new JButton("ℹ️ Dettagli");
        btnCondivisi = new JButton("👥 ToDo Condivisi con Me");

        for (JButton b : new JButton[]{btnNuovo, btnModifica, btnElimina, btnChecklist, btnSposta, btnDettagli, btnCondivisi}) {
            b.setBackground(sabbia);
            b.setFont(new Font("SansSerif", Font.BOLD, 13));
            b.setFocusPainted(false);
        }

        JPanel btnPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        btnPanel.setBackground(beige);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnPanel.add(btnNuovo);
        btnPanel.add(btnModifica);
        btnPanel.add(btnDettagli);
        btnPanel.add(btnChecklist);
        btnPanel.add(btnSposta);
        btnPanel.add(btnElimina);
        btnPanel.add(btnCondivisi);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Eventi
        toDoList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    ToDo selezionato = toDoList.getSelectedValue();
                    if (selezionato != null) {
                        new ToDoDettaglioFrame(selezionato);
                    }
                }
            }
        });

        btnNuovo.addActionListener(e -> new ToDoFrame(username, bacheca, this));

        btnModifica.addActionListener(e -> {
            ToDo t = toDoList.getSelectedValue();
            if (t != null) {
                new ToDoModificaFrame(t, this);
            }
        });

        btnDettagli.addActionListener(e -> {
            ToDo t = toDoList.getSelectedValue();
            if (t != null) {
                new ToDoDettaglioFrame(t);
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un ToDo per vedere i dettagli.");
            }
        });

        btnElimina.addActionListener(e -> {
            ToDo t = toDoList.getSelectedValue();
            if (t != null) {
                int conferma = JOptionPane.showConfirmDialog(this, "Confermi l'eliminazione?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    Controller.eliminaToDo(t);
                    caricaToDo();
                }
            }
        });

        btnChecklist.addActionListener(e -> {
            ToDo t = toDoList.getSelectedValue();
            if (t != null) {
                new CheckListFrame(t, this);
            }
        });

        btnSposta.addActionListener(e -> {
            ToDo t = toDoList.getSelectedValue();
            if (t != null) {
                List<Bacheca> bacheche = Controller.getBachechePerUtente(username);
                Bacheca nuova = (Bacheca) JOptionPane.showInputDialog(this, "Sposta in:", "Sposta ToDo",
                        JOptionPane.PLAIN_MESSAGE, null, bacheche.toArray(), t.getBacheca());

                if (nuova != null && !nuova.equals(t.getBacheca())) {
                    Controller.spostaToDo(t, nuova);
                    caricaToDo();
                }
            }
        });

        btnCondivisi.addActionListener(e -> {
            visualizzaCondivisi = !visualizzaCondivisi;
            btnCondivisi.setText(visualizzaCondivisi ? "👁️ Vedi Tutti" : "👥 ToDo Condivisi con Me");
            caricaToDo();
        });

        caricaToDo();
        setVisible(true);
    }

    /**
     * Restituisce l'username dell'utente attualmente loggato.
     *
     * @return lo username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Carica i ToDo dalla bacheca o quelli condivisi, in base al filtro attivo.
     */
    public void caricaToDo() {
        toDoListModel.clear();
        List<ToDo> todos;

        if (visualizzaCondivisi) {
            todos = Controller.getListaToDo(username);
            todos.removeIf(t -> {
                if (t.getBacheca() == null) return true;
                boolean nonCondiviso = !t.utenti_condivisi.contains(username);
                boolean tipoDiverso = t.getBacheca().tipo != bacheca.tipo;
                return nonCondiviso || tipoDiverso;
            });

        } else {
            todos = Controller.getToDoPerBacheca(bacheca, username);
        }

        todos.sort(Comparator.comparingInt(t -> t.ordine));
        todos.forEach(toDoListModel::addElement);
    }
}




