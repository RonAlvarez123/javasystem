package main;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class AddAccount extends JFrame {
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

}
