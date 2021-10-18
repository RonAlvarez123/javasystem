package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JMenuBar menuBar = new JMenuBar();;

    JMenu menuFile = new JMenu("File");
    JMenu menuAccounts = new JMenu("Accounts");
    JMenu menuView = new JMenu("View");

    JMenuItem menuItemQuit = new JMenuItem("Quit");
    JMenuItem menuItemAdd = new JMenuItem("Add Account");
    JMenuItem menuItemEdit = new JMenuItem("Edit Account");
    JMenuItem menuItemUpdate = new JMenuItem("Update Account");
    JMenuItem menuItemDelete = new JMenuItem("Delete Account");
    JMenuItem menuItemTable = new JMenuItem("Accounts Table");

    JPanel panel;
    JButton buttonQuit = new JButton(new ImageIcon(new ImageIcon("src/img/sign-out-icon.jpg").getImage()
            .getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
    CardLayout cardLayout = new CardLayout();

    Database database;
    Login login;
    AddAccount addAccount;
    DeleteAccount deleteAccount;
    ViewAccount viewAccount;
    EditAccount editAccount;

    public MainMenu() {
        super("Java System");

        database = new Database(Database.getPort(), Database.getDBname(), Database.getUsername(),
                Database.getPassword());
        database.createStatement();
        database.setupAccountsTable();

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            public Dimension getPreferredSize() {
                return new Dimension(1366, 100);
            }
        };
        panel.setLayout(cardLayout);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.out.println("WindowCLosing Event");
                System.exit(0);
            }
        });

        this.setLayout(new BorderLayout());
        this.setActionsForMenuItems();
        this.addActionListenerToMenuItems();
        this.addMenuItemToMenus();

        menuBar.add(menuFile);
        menuBar.add(menuAccounts);
        menuBar.add(menuView);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.add(buttonQuit);

        buttonQuit.setToolTipText("Close Application");
        buttonQuit.addActionListener(this);

        this.add(panel, BorderLayout.NORTH);
        this.setJMenuBar(menuBar);
        this.add(toolbar, BorderLayout.NORTH);

        this.pack();
        this.setSize(1366, 768);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        login = new Login();
    }

    private void addMenuItemToMenus() {
        menuFile.add(menuItemQuit);
        menuAccounts.add(menuItemAdd);
        menuAccounts.add(menuItemEdit);
        menuAccounts.add(menuItemUpdate);
        menuAccounts.add(menuItemDelete);
        menuView.add(menuItemTable);
    }

    private void setActionsForMenuItems() {
        Action quitApp = this.createAction("Quit");
        Action addAccnt = this.createAction("Add");
        Action editAccnt = this.createAction("Edit");
        Action updateAccnt = this.createAction("Update");
        Action deleteAccnt = this.createAction("Delete");

        quitApp.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        addAccnt.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        editAccnt.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        updateAccnt.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
        deleteAccnt.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));

        menuItemQuit.setAction(quitApp);
        menuItemAdd.setAction(addAccnt);
        menuItemEdit.setAction(editAccnt);
        menuItemUpdate.setAction(updateAccnt);
        menuItemDelete.setAction(deleteAccnt);
    }

    private Action createAction(String value) {
        Action action = new AbstractAction(value) {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent ae) {

            }
        };

        return action;
    }

    private void addActionListenerToMenuItems() {
        menuItemQuit.addActionListener(this);
        menuItemAdd.addActionListener(this);
        menuItemEdit.addActionListener(this);
        menuItemUpdate.addActionListener(this);
        menuItemDelete.addActionListener(this);
        menuItemTable.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == menuItemQuit || ae.getSource() == buttonQuit) {
            System.exit(0);
        }

        if (ae.getSource() == menuItemAdd) {
            addAccount = new AddAccount();
        }

        if (ae.getSource() == menuItemEdit) {
            editAccount = new EditAccount();
        }

        if (ae.getSource() == menuItemDelete) {
            deleteAccount = new DeleteAccount();
        }

        if (ae.getSource() == menuItemTable) {
            viewAccount = new ViewAccount();
        }
    }
}
