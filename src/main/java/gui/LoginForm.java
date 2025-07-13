package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Classe che rappresenta il modulo di login per l'applicazione ToDo.
 * Consente all'utente di autenticarsi o registrarsi.
 */
public class LoginForm {
    private JPanel panelLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistrati;

    /**
     * Costruisce il modulo di login e registra i listener per login e registrazione.
     *
     * @param frame il frame principale passato per mostrare dialoghi e cambiare schermata
     */
    public LoginForm(JFrame frame) {
        Color beige = new Color(245, 245, 220);
        Color sabbia = new Color(222, 184, 135);

        panelLogin = new JPanel(new GridLayout(0, 1, 10, 10));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panelLogin.setBackground(beige);

        JLabel titolo = new JLabel("Gestore ToDo", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 20));
        panelLogin.add(titolo);

        panelLogin.add(new JLabel("Username *"));
        txtUsername = new JTextField();
        panelLogin.add(txtUsername);

        panelLogin.add(new JLabel("Password *"));
        txtPassword = new JPasswordField();
        panelLogin.add(txtPassword);

        btnLogin = new JButton("➡️ Accedi");
        btnRegistrati = new JButton("📝 Registrati");

        btnLogin.setBackground(sabbia);
        btnRegistrati.setBackground(new Color(200, 200, 150));

        panelLogin.add(btnLogin);
        panelLogin.add(btnRegistrati);

        // Login
        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Inserisci username e password (obbligatori)", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!Controller.verificaUtente(user, pass)) {
                JOptionPane.showMessageDialog(frame, "Username o password errati.", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            frame.dispose();
            new BachecaFrame(user);
        });

        // Registrazione
        btnRegistrati.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Per registrarti, inserisci username e password.", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (Controller.utenteEsiste(user)) {
                    JOptionPane.showMessageDialog(frame, "⚠️ Utente già registrato. Inserisci la password e accedi.", "Utente già esistente", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Controller.creaUtente(user, pass);
                    JOptionPane.showMessageDialog(frame, "✅ Registrazione completata! Ora puoi accedere.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Errore durante la registrazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Restituisce il pannello principale del form di login.
     *
     * @return il pannello di login
     */
    public JPanel getPanel() {
        return panelLogin;
    }

    /**
     * Metodo main per avviare il form di login come applicazione standalone.
     *
     * @param args gli argomenti della riga di comando (non usati)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestore ToDo - Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new LoginForm(frame).getPanel());
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}










