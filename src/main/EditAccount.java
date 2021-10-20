package main;

import javax.swing.*;
import java.awt.*;

public class EditAccount extends JFrame {
    private static final long serialVersionUID = 1L;

    JFrame frame;

    JLabel labelAccountid = new JLabel("Account Id");
    JLabel labelFirstname = new JLabel("First Name");
    JLabel labelLastname = new JLabel("Last Name");
    JLabel labelUsername = new JLabel("Username");
    JLabel labelPassword = new JLabel("Password");

    JTextField textAccountid = new JTextField(10);
    JTextField textFirstname = new JTextField(50);
    JTextField textLastname = new JTextField(50);
    JTextField textUsername = new JTextField(50);
    JTextField textPassword = new JTextField(100);
    JTextField[] textFields = { textAccountid, textFirstname, textLastname, textUsername, textPassword };

    JButton buttonSearch = new JButton("Find");
    JButton buttonEdit = new JButton("Edit");
    JButton buttonUpdate = new JButton("Update");
    JButton buttonExit = new JButton("Exit");

    Database database;
    Container container;

    public EditAccount() {
        super("Edit Account");

        container = getContentPane();
        container.setLayout(null);

        for (JTextField textField : textFields) {
            textField.setEditable(false);
        }

        buttonEdit.setEnabled(false);
        buttonUpdate.setEnabled(false);

        this.pack();
        this.setSize(510, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
