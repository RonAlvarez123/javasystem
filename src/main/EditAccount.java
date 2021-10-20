package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditAccount extends JFrame implements ActionListener {
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

        this.setComponentBounds();
        this.addContainerComponents();

        for (JTextField textField : textFields) {
            textField.setEditable(false);
        }

        buttonSearch.addActionListener(this);
        buttonEdit.addActionListener(this);
        buttonUpdate.addActionListener(this);
        buttonExit.addActionListener(this);

        buttonEdit.setEnabled(false);
        buttonUpdate.setEnabled(false);

        this.pack();
        this.setSize(510, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == buttonSearch) {
            database = new Database(Database.getPort(), Database.getDBname(), Database.getUsername(),
                    Database.getPassword());
            database.createStatement();

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
                buttonEdit.setEnabled(true);
                buttonSearch.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Username Does not Exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (actionEvent.getSource() == buttonEdit) {
            buttonUpdate.setEnabled(true);
            buttonEdit.setEnabled(false);

            textFirstname.setEditable(true);
            textLastname.setEditable(true);
            textUsername.setEditable(true);
        }
        if (actionEvent.getSource() == buttonUpdate) {
            String id = textAccountid.getText().trim();
            String firstname = textFirstname.getText().toUpperCase().trim();
            String lastname = textLastname.getText().toUpperCase().trim();
            String username = textUsername.getText().trim();

            String[] updateFields = { "firstname", "lastname", "username" };
            String[] updateValues = { firstname, lastname, username };
            String[] wheres = { "id" };
            String[] values = { id };
            database.update("accounts", updateFields, updateValues, wheres, values);

            JOptionPane.showMessageDialog(null, "Account Updated", "Update Account", JOptionPane.INFORMATION_MESSAGE);
            buttonSearch.setEnabled(true);
            buttonUpdate.setEnabled(false);

            for (JTextField textField : textFields) {
                textField.setEditable(false);
                textField.setText("");
            }
        }
        if (actionEvent.getSource() == buttonExit) {
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
        buttonEdit.setBounds(130, 175, 110, 50);
        buttonUpdate.setBounds(250, 175, 110, 50);
        buttonExit.setBounds(370, 175, 110, 50);
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
        container.add(buttonEdit);
        container.add(buttonUpdate);
        container.add(buttonExit);
    }
}
