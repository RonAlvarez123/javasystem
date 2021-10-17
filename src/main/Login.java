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
        this.showLogin();
    }

    public void showLogin() {
        Database database = new Database(Database.getPort(), Database.getDBname(), Database.getUsername(),
                Database.getPassword());

        while (true) {
            int result = JOptionPane.showOptionDialog(frame, message, "Login", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, login, Options, Options[0]);

            if (result == JOptionPane.CLOSED_OPTION) {
                if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Exit?", "Confirm",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }

            if (result == 1) {
                JOptionPane.showMessageDialog(frame, "Program Closed! Goodbye!", "Goodbye",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            if (result == 0) {
                String username = textUser.getText();
                String password = Hash.toMD5(new String(textPassword.getPassword()));

                String[] countWheres = { "username" };
                String[] countValues = { username };

                if (database.count("accounts", countWheres, countValues) == 0) {
                    JOptionPane.showMessageDialog(frame, "User Not Found !", "Warning", JOptionPane.WARNING_MESSAGE);
                    textUser.requestFocusInWindow();
                } else {
                    String[] fields = { "password" };
                    String[] wheres = { "username" };
                    String[] values = { username };
                    database.select("accounts", fields, wheres, values, "");

                    String dbPassword = database.getStringFromResult("password", true);
                    // System.out.println("db: " + dbPassword);
                    // System.out.println("pw: " + password);

                    if (dbPassword.equals(password)) {
                        System.out.println("Match");
                        JOptionPane.showMessageDialog(frame, "Welcome to my World, " + username + ".", "Welcome",
                                JOptionPane.INFORMATION_MESSAGE);
                        database.closeDatabase();
                        break;
                    } else {
                        System.out.println("Did not match");
                        JOptionPane.showMessageDialog(frame, "Incorrect Password Please Try Again !", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        textPassword.requestFocusInWindow();
                    }
                }
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == textUser) {
            textPassword.requestFocusInWindow();
        }
    }
}
