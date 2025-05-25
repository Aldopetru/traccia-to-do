package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class LoginForm {
    private JPanel panelLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginForm(JFrame frame) {
        panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        panelLogin.setBackground(new Color(245, 245, 220)); // beige
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitolo = new JLabel("LOGIN");
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnLogin = new JButton("Accedi");
        btnLogin.setBackground(new Color(222, 184, 135)); // sabbia

        panelLogin.add(lblTitolo);
        panelLogin.add(Box.createVerticalStrut(10));
        panelLogin.add(new JLabel("Username *"));
        panelLogin.add(txtUsername);
        panelLogin.add(new JLabel("Password *"));
        panelLogin.add(txtPassword);
        panelLogin.add(Box.createVerticalStrut(10));
        panelLogin.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Inserisci username e password (obbligatori)", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!Controller.verificaUtente(user, pass)) {
                controller.Controller.creaUtente(user, pass);
                JOptionPane.showMessageDialog(frame, "Nuovo utente creato!");
            }

            frame.dispose();
            new BachecaFrame(user); // Vai alla schermata bacheche
        });
    }

    public JPanel getPanel() {
        return panelLogin;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new LoginForm(frame).getPanel());
            frame.setSize(300, 220);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}




