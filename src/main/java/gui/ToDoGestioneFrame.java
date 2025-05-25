package gui;

import controller.Controller;
import model.Bacheca;
import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ToDoGestioneFrame extends JFrame {
    private DefaultListModel<ToDo> toDoListModel;
    private JList<ToDo> toDoList;
    private JButton btnNuovo, btnModifica, btnElimina, btnChecklist;
    private String username;
    private Bacheca bacheca;

    public ToDoGestioneFrame(String username, Bacheca bacheca) {
        this.username = username;
        this.bacheca = bacheca;

        setTitle("ToDo - " + bacheca.titolo_b);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);
        getContentPane().setBackground(beige);

        toDoListModel = new DefaultListModel<>();
        toDoList = new JList<>(toDoListModel);
        JScrollPane scrollPane = new JScrollPane(toDoList);

        btnNuovo = new JButton("➕ Nuovo");
        btnModifica = new JButton("✏️ Modifica");
        btnElimina = new JButton("🗑️ Elimina");
        btnChecklist = new JButton("🧾 CheckList");

        for (JButton b : new JButton[]{btnNuovo, btnModifica, btnElimina, btnChecklist}) {
            b.setBackground(sabbia);
            b.setFont(new Font("SansSerif", Font.BOLD, 13));
        }

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(beige);
        btnPanel.add(btnNuovo);
        btnPanel.add(btnModifica);
        btnPanel.add(btnElimina);
        btnPanel.add(btnChecklist);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        caricaToDo();

        btnNuovo.addActionListener(e -> new ToDoFrame(username, bacheca, this));

        btnModifica.addActionListener(e -> {
            ToDo selezionato = toDoList.getSelectedValue();
            if (selezionato != null) {
                new ToDoModificaFrame(selezionato);
            }
        });

        btnElimina.addActionListener(e -> {
            ToDo selezionato = toDoList.getSelectedValue();
            if (selezionato != null) {
                int conferma = JOptionPane.showConfirmDialog(this, "Confermi l'eliminazione?", "Attenzione", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    Controller.eliminaToDo(selezionato);
                    caricaToDo();
                }
            }
        });

        btnChecklist.addActionListener(e -> {
            ToDo selezionato = toDoList.getSelectedValue();
            if (selezionato != null) {
                new CheckListFrame(selezionato.getChecklist());
            }
        });

        setVisible(true);
    }

    public void caricaToDo() {
        toDoListModel.clear();
        List<ToDo> lista = controller.Controller.getToDoPerBacheca(bacheca);
        lista.forEach(toDoListModel::addElement);
    }
}

