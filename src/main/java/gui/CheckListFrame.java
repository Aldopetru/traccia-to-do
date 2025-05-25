package gui;

import model.Check_list;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckListFrame extends JFrame {
    private DefaultListModel<Check_list> model;
    private JList<Check_list> lista;
    private JButton btnAggiungi, btnModifica, btnElimina, btnCompleta;

    public CheckListFrame(List<Check_list> checklist) {
        setTitle("Checklist del ToDo");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        model = new DefaultListModel<>();
        lista = new JList<>(model);
        checklist.forEach(model::addElement);
        JScrollPane scroll = new JScrollPane(lista);

        btnAggiungi = new JButton("➕ Aggiungi");
        btnModifica = new JButton("✏️ Modifica");
        btnElimina = new JButton("🗑️ Elimina");
        btnCompleta = new JButton("✔️ Completato");

        for (JButton b : new JButton[]{btnAggiungi, btnModifica, btnElimina, btnCompleta}) {
            b.setBackground(sabbia);
            b.setFont(new Font("SansSerif", Font.BOLD, 12));
        }

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(beige);
        btnPanel.add(btnAggiungi);
        btnPanel.add(btnModifica);
        btnPanel.add(btnCompleta);
        btnPanel.add(btnElimina);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(beige);
        main.add(scroll, BorderLayout.CENTER);
        main.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(main);
        setVisible(true);

        btnAggiungi.addActionListener(e -> {
            String voce = JOptionPane.showInputDialog(this, "Inserisci una nuova voce:");
            if (voce != null && !voce.isBlank()) {
                Check_list nuovo = new Check_list(voce, false);
                checklist.add(nuovo);
                model.addElement(nuovo);
            }
        });

        btnModifica.addActionListener(e -> {
            Check_list selezionato = lista.getSelectedValue();
            if (selezionato != null) {
                String nuovoNome = JOptionPane.showInputDialog(this, "Modifica voce:", selezionato.getNome());
                if (nuovoNome != null && !nuovoNome.isBlank()) {
                    selezionato.setNome(nuovoNome);
                    lista.repaint();
                }
            }
        });

        btnCompleta.addActionListener(e -> {
            Check_list selezionato = lista.getSelectedValue();
            if (selezionato != null) {
                selezionato.setStato(true);
                lista.repaint();
            }
        });

        btnElimina.addActionListener(e -> {
            Check_list selezionato = lista.getSelectedValue();
            if (selezionato != null) {
                checklist.remove(selezionato);
                model.removeElement(selezionato);
            }
        });
    }
}

