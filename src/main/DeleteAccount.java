package main;

import javax.swing.*;
import java.awt.*;

public class DeleteAccount extends JFrame {
    private static final long serialVersionUID = 1L;

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

    Icon iconDelete = new ImageIcon("delete.png");
    Icon iconSearch = new ImageIcon("search.png");
    Icon iconBack = new ImageIcon("exit1.png");

    JButton buttonDelete = new JButton("Delete", iconDelete);
    JButton buttonSearch = new JButton("Find", iconSearch);
    JButton buttonExit = new JButton("Exit", iconBack);

    Database database;
    Container container;

    public DeleteAccount() {
        super("Delete Account");

        container = getContentPane();
        container.setLayout(null);

        for (JTextField textField : textFields) {
            textField.setEditable(false);
        }

        buttonDelete.setEnabled(false);

        this.pack();
        this.setSize(500, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
