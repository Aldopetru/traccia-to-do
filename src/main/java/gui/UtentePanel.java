package gui;

import controller.Controller;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UtentePanel extends JPanel {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnCrea;
    private DefaultListModel<String> modelUtenti;
    private JList<String> listaUtenti;

    public UtentePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 220)); // beige

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(new Color(245, 245, 220));

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnCrea = new JButton("➕ Crea Utente");

        btnCrea.setBackground(new Color(222, 184, 135));
        btnCrea.setFont(new Font("SansSerif", Font.BOLD, 13));

        form.add(new JLabel("Username *"));
        form.add(txtUsername);
        form.add(new JLabel("Password *"));
        form.add(txtPassword);
        form.add(Box.createVerticalStrut(10));
        form.add(btnCrea);

        modelUtenti = new DefaultListModel<>();
        listaUtenti = new JList<>(modelUtenti);
        JScrollPane scroll = new JScrollPane(listaUtenti);
        scroll.setBorder(BorderFactory.createTitledBorder("Utenti creati"));

        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnCrea.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username e password sono obbligatori.");
                return;
            }

            if (Controller.verificaUtente(user, pass)) {
                JOptionPane.showMessageDialog(this, "Utente già esistente!");
                return;
            }

            Controller.creaUtente(user, pass);
            modelUtenti.addElement(user);
            JOptionPane.showMessageDialog(this, "Utente creato!");
            txtUsername.setText("");
            txtPassword.setText("");
        });

        List<Utente> utenti = Controller.getUtenti();
        for (Utente u : utenti) {
            modelUtenti.addElement(u.username);
        }
    }
}


