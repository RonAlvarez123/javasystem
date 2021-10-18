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

        this.setComponentBounds();
        this.addContainerComponents();
        this.addActionListenerToElements();

        buttonAdd.setEnabled(false);
        textAccountid.setEditable(false);

        database = new Database(Database.getPort(), Database.getDBname(), Database.getUsername(),
                Database.getPassword());
        database.createStatement();

        textAccountid.setText(String.valueOf(format1.format(this.getLatestAccountId())));

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

            textAccountid.setText(String.valueOf(format1.format(this.getLatestAccountId())));

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

    public int getLatestAccountId() {
        String[] fields = { "IFNULL(max(id),0)" };
        String[] wheres = {};
        String[] values = {};
        String alias = "Id";
        database.select("accounts", fields, wheres, values, alias);
        int id = Integer.parseInt(database.getStringFromResult(alias, true));
        id++;
        return id;
    }

    public void addActionListenerToElements() {
        buttonBack.addActionListener(this);
        buttonAdd.addActionListener(this);
        textFirstname.addActionListener(this);
        textLastname.addActionListener(this);
        textUsername.addActionListener(this);
        textPassword.addActionListener(this);
    }

    private void setComponentBounds() {
        labelAccountid.setBounds(10, 5, 100, 25);
        textAccountid.setBounds(130, 5, 100, 25);
        labelFirstname.setBounds(10, 35, 100, 25);
        textFirstname.setBounds(130, 35, 150, 25);
        labelLastname.setBounds(10, 65, 100, 25);
        textLastname.setBounds(130, 65, 150, 25);
        labelUsername.setBounds(10, 95, 100, 25);
        textUsername.setBounds(130, 95, 150, 25);
        labelPassword.setBounds(10, 125, 100, 25);
        textPassword.setBounds(130, 125, 150, 25);
        buttonAdd.setBounds(60, 175, 110, 50);
        buttonBack.setBounds(180, 175, 110, 50);
    }

    private void addContainerComponents() {
        container.add(labelAccountid);
        container.add(textAccountid);
        container.add(labelFirstname);
        container.add(textFirstname);
        container.add(labelLastname);
        container.add(textLastname);
        container.add(labelUsername);
        container.add(textUsername);
        container.add(labelPassword);
        container.add(textPassword);
        container.add(buttonAdd);
        container.add(buttonBack);
    }
}
