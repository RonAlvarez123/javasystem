package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteAccount extends JFrame implements ActionListener {
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

        this.setComponentBounds();
        this.addContainerComponents();

        for (JTextField textField : textFields) {
            textField.setEditable(false);
        }

        buttonSearch.addActionListener(this);
        buttonDelete.addActionListener(this);
        buttonExit.addActionListener(this);

        buttonDelete.setEnabled(false);

        this.pack();
        this.setSize(500, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == buttonSearch) {

            String username = JOptionPane.showInputDialog(null, "Enter Username: ", "Message",
                    JOptionPane.QUESTION_MESSAGE);
            String[] fields = {};
            String[] wheres = { "username" };
            String[] values = { username };

            database.select("accounts", fields, wheres, values, "");

            if (database.hasNext()) {
                for (int i = 0; i < textFields.length; i++) {
                    textFields[i].setText(database.getStringFromResultInt(i + 1, false));
                }
                buttonDelete.setEnabled(true);
                buttonSearch.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Username Does not Exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (evt.getSource() == buttonDelete) {

            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the record?", "Confirm",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                String[] wheres = { "id" };
                String[] values = { textAccountid.getText() };
                database.delete("accounts", wheres, values);
                JOptionPane.showMessageDialog(null, "Account Deleted", "Message", JOptionPane.INFORMATION_MESSAGE);

                buttonDelete.setEnabled(false);
                buttonSearch.setEnabled(true);

                for (JTextField textField : textFields) {
                    textField.setText("");
                }
            }
        }

        if (evt.getSource() == buttonExit) {
            this.dispose();
        }
    }

    private void setComponentBounds() {
        labelAccountid.setBounds(10, 5, 100, 25);
        textAccountid.setBounds(130, 5, 75, 25);
        labelFirstname.setBounds(10, 35, 100, 25);
        textFirstname.setBounds(130, 35, 150, 25);
        labelLastname.setBounds(10, 65, 100, 25);
        textLastname.setBounds(130, 65, 150, 25);
        labelUsername.setBounds(10, 95, 100, 25);
        textUsername.setBounds(130, 95, 150, 25);
        labelPassword.setBounds(10, 125, 100, 25);
        textPassword.setBounds(130, 125, 300, 25);
        buttonSearch.setBounds(10, 175, 110, 50);
        buttonDelete.setBounds(130, 175, 110, 50);
        buttonExit.setBounds(250, 175, 110, 50);
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
        container.add(buttonSearch);
        container.add(buttonDelete);
        container.add(buttonExit);
    }
}
