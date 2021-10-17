package main;

import javax.swing.*;
import java.awt.event.*;

public class Login implements ActionListener {
    JLabel labelUser = new JLabel("Username");
    JLabel labelPassword = new JLabel("Password");
    JTextField textUser = new JTextField(20);
    JPasswordField textPassword = new JPasswordField(20);
    JFrame frame;
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    Icon login = new ImageIcon("login.png");

    String Options[] = { "Login", "Quit" };
    Object[] message = { panel1, panel2 };

    public Login() {
        panel1.add(labelUser);
        panel1.add(textUser);
        panel2.add(labelPassword);
        panel2.add(textPassword);

        textUser.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == textUser) {
            textPassword.requestFocusInWindow();
        }
    }
}
