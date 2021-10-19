package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewAccount extends JFrame {
    private static final long serialVersionUID = 1L;

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

        this.pack();
        this.setSize(300, 150);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
