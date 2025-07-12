package gui;

import controller.Controller;
import model.ToDo;
import model.Check_list;

import javax.swing.*;
import java.awt.*;

/**
 * Finestra per la gestione della checklist associata a un ToDo.
 * Permette di aggiungere, modificare, completare o eliminare voci della checklist.
 */
public class CheckListFrame extends JFrame {
    private DefaultListModel<Check_list> model;
    private JList<Check_list> lista;
    private JButton btnAggiungi;
    private JButton btnModifica;
    private JButton btnElimina;
    private JButton btnCompleta;

    private ToDo todo;
    private ToDoGestioneFrame parent;

    /**
     * Costruttore della finestra per la gestione della checklist.
     *
     * @param todo   ToDo a cui è associata la checklist
     * @param parent finestra padre da aggiornare al completamento
     */
    public CheckListFrame(ToDo todo, ToDoGestioneFrame parent) {
        this.todo = todo;
        this.parent = parent;

        setTitle("📋 Checklist - " + todo.titolo_todo);
        setSize(480, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(beige);

        model = new DefaultListModel<>();
        lista = new JList<>(model);
        lista.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(lista);

        todo.getChecklist().forEach(model::addElement);

        // Bottoni
        btnAggiungi = new JButton("➕ Aggiungi");
        btnModifica = new JButton("✏️ Modifica");
        btnCompleta = new JButton("✔️ Completato");
        btnElimina = new JButton("🗑️ Elimina");

        JButton[] bottoni = {btnAggiungi, btnModifica, btnCompleta, btnElimina};
        JPanel bottoniPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        bottoniPanel.setBackground(beige);
        for (JButton b : bottoni) {
            b.setBackground(sabbia);
            b.setFont(new Font("SansSerif", Font.BOLD, 13));
            bottoniPanel.add(b);
        }

        bottoniPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottoniPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);

        // === Azioni ===

        // Aggiunge una voce alla checklist
        btnAggiungi.addActionListener(e -> {
            String voce = JOptionPane.showInputDialog(this, "Nuova voce checklist:");
            if (voce != null && !voce.isBlank()) {
                Check_list nuovo = new Check_list(voce, false);
                todo.getChecklist().add(nuovo);
                model.addElement(nuovo);
            }
        });

        // Modifica la voce selezionata
        btnModifica.addActionListener(e -> {
            Check_list selezionato = lista.getSelectedValue();
            if (selezionato != null) {
                String nuovaVoce = JOptionPane.showInputDialog(this, "Modifica voce:", selezionato.getNome());
                if (nuovaVoce != null && !nuovaVoce.isBlank()) {
                    selezionato.setNome(nuovaVoce);
                    lista.repaint();
                }
            }
        });

        // Segna come completata la voce selezionata e aggiorna stato ToDo se necessario
        btnCompleta.addActionListener(e -> {
            Check_list selezionato = lista.getSelectedValue();
            if (selezionato != null && !selezionato.isStato()) {
                Controller.completaCheck(selezionato);
                lista.repaint();

                if (todo.getChecklist().stream().allMatch(Check_list::isStato)) {
                    todo.Stato_todo = true;
                    Controller.aggiornaToDo(todo);
                    JOptionPane.showMessageDialog(this, "✅ Tutte le voci completate. Il ToDo è stato marcato come completato.");
                    if (parent != null) parent.caricaToDo();
                }
            }
        });

        // Elimina la voce selezionata
        btnElimina.addActionListener(e -> {
            Check_list selezionato = lista.getSelectedValue();
            if (selezionato != null) {
                todo.getChecklist().remove(selezionato);
                model.removeElement(selezionato);
            }
        });
    }
}




