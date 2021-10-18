package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class AddAccount extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    DecimalFormat format1 = new DecimalFormat("00000");

    JLabel labelAccountid = new JLabel("Account Id");
    JLabel labelFirstname = new JLabel("First Name");
    JLabel labelLastname = new JLabel("Last Name");
    JLabel labelUsername = new JLabel("Username");
    JLabel labelPassword = new JLabel("Password");

    JTextField textAccountid = new JTextField(10);
    JTextField textFirstname = new JTextField(50);
    JTextField textLastname = new JTextField(50);
    JTextField textUsername = new JTextField(50);

    JPasswordField textPassword = new JPasswordField(100);

    Icon iconAdd = new ImageIcon("add.png");
    Icon iconBack = new ImageIcon("exit1.png");

    JButton buttonAdd = new JButton("Add", iconAdd);
    JButton buttonBack = new JButton("Back", iconBack);

    Database database;
    Container container;

    public AddAccount() {
        super("Add Account");

        container = getContentPane();
        container.setLayout(null);

        buttonAdd.setEnabled(false);
        textAccountid.setEditable(false);

        database = new Database(Database.getPort(), Database.getDBname(), Database.getUsername(),
                Database.getPassword());
        database.createStatement();

        this.pack();
        this.setSize(350, 275);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        textFirstname.requestFocusInWindow();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == buttonBack) {
            this.dispose();
        }

        if (ae.getSource() == buttonAdd) {
            String id = textAccountid.getText().trim();
            String firstname = textFirstname.getText().toUpperCase().trim();
            String lastname = textLastname.getText().toUpperCase().trim();
            String username = textUsername.getText().trim();
            String password = new String(textPassword.getPassword());

            String[] columns = {};
            String[] values = { id, firstname, lastname, username, Hash.toMD5(password) };
            database.insert("accounts", columns, values);

            JOptionPane.showMessageDialog(null, "Account Added", "Add Account", JOptionPane.INFORMATION_MESSAGE);
            buttonAdd.setEnabled(false);

            textFirstname.setText("");
            textLastname.setText("");
            textUsername.setText("");
            textPassword.setText("");

            textFirstname.requestFocusInWindow();
        }

        if (ae.getSource() == textFirstname) {
            if (textFirstname.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Enter First Name", "Error", JOptionPane.ERROR_MESSAGE);
                textFirstname.requestFocusInWindow();
            } else {
                textLastname.requestFocusInWindow();
            }
        }

        if (ae.getSource() == textLastname) {
            if (textLastname.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Enter Last Name", "Error", JOptionPane.ERROR_MESSAGE);
                textLastname.requestFocusInWindow();
            } else {
                textUsername.requestFocusInWindow();
            }
        }

        if (ae.getSource() == textUsername) {
            if (textUsername.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Enter Username", "Error", JOptionPane.ERROR_MESSAGE);
                textUsername.requestFocusInWindow();
            } else {
                textPassword.requestFocusInWindow();
            }
        }

        if (ae.getSource() == textPassword) {
            if (textPassword.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Enter Password", "Error", JOptionPane.ERROR_MESSAGE);
                textPassword.requestFocusInWindow();
            } else {
                buttonAdd.requestFocusInWindow();
                buttonAdd.setEnabled(true);
            }
        }
    }

}
