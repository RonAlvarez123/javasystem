package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ViewAccount extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private String colNames[] = { "Account Id", "First Name", "Last Name", "Username", "Password" };

    JLabel labelChoice = new JLabel("Choose Operation to Perform ?");
    DefaultTableModel model = new DefaultTableModel();
    JFrame frame = new JFrame("Database Results");
    JScrollPane scroll = new JScrollPane();
    JTable table = new JTable(model);

    Icon iconView = new ImageIcon("viewer.png");
    Icon iconBack = new ImageIcon("exit1.png");

    JButton buttonView = new JButton("View", iconView);
    JButton buttonExit = new JButton("Exit", iconBack);

    Database database;
    Container container;

    public ViewAccount() {
        super("View Accounts Table");

        container = getContentPane();
        container.setLayout(null);

        labelChoice.setFont(new Font("Arial", Font.BOLD, 15));

        labelChoice.setBounds(40, 5, 250, 50);
        buttonView.setBounds(50, 65, 100, 50);
        buttonExit.setBounds(160, 65, 100, 50);

        container.add(labelChoice);
        container.add(buttonView);
        container.add(buttonExit);

        buttonView.addActionListener(this);
        buttonExit.addActionListener(this);

        this.pack();
        this.setSize(300, 150);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void showTable() {
        frame.setLayout(new BorderLayout());
        model.setColumnIdentifiers(colNames);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        scroll.getViewport().add(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.populateTable();

        frame.add(scroll);
        frame.pack();
        frame.setSize(1300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void populateTable() {
        database = new Database(Database.getPort(), Database.getDBname(), Database.getUsername(),
                Database.getPassword());
        database.createStatement();
        database.all("accounts");

        int i = 0;
        for (; database.hasNext(); i++) {
            model.addRow(new Object[] { database.getStringFromResult("id", false),
                    database.getStringFromResult("firstname", false), database.getStringFromResult("lastname", false),
                    database.getStringFromResult("username", false), database.getStringFromResult("password", false) });
        }

        if (i < 1) {
            JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String message = i == 1 ? " Record Found" : " Records Found";
            JOptionPane.showMessageDialog(null, i + message, "Result", JOptionPane.INFORMATION_MESSAGE);
        }

        database.closeDatabase();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == buttonView) {
            this.showTable();
            this.dispose();
        }

        if (ae.getSource() == buttonExit) {
            this.dispose();
        }
    }
}
