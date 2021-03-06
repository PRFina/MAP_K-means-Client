package app;

import actions.Action;
import actions.DiscoverAction;
import actions.ReadAction;
import actions.ServerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class model a client application.
 * <p>
 * <p>
 * The main responsibility is to handle
 * UI interaction with user trough event-based paradigm.
 * </P>
 */
public class KMeans {

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) {
        KMeans test = new KMeans();
        test.init();
    }

    /**
     * Build and compose the UI components hierarchy.
     * Load configuration settings and establish server connection
     */
    public void init() {

        try {

            System.out.print("Init configuration... ");
            AppConfig settings = new AppConfig("settings.properties");
            System.out.println("Done!");

            System.out.print("Init connection... ");
            Socket socket = new Socket(settings.getSetting("server_address"), Integer.parseInt(settings.getSetting("server_port")));
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Done!");

            System.out.print("Init UI components... ");
            JFrame mainWindow = new JFrame("KMiner");
            TabbedPane tabbedPane = new TabbedPane();
            mainWindow.getContentPane().add(tabbedPane);

            mainWindow.setSize(800, 600);
            mainWindow.setVisible(true);

            mainWindow.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    try {
                        socket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            });
            System.out.println("Done!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection refused, check your connection or " +
                    "try later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            e.printStackTrace();
        }


    }

    /**
     * Model a multi-tab Panel
     */
    private class TabbedPane extends JTabbedPane {

        private JPanelCluster panelDB;
        private JPanelCluster panelFile;

        TabbedPane() {

            // DB Panel
            panelDB = new JPanelCluster("Mine", evt -> {

                String tableName = panelDB.getTableTextValue();
                String nClusters = panelDB.getKTextValue();

                if (tableName.isEmpty() || nClusters.isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Please insert table name and clusters number",
                            "Input Warning",
                            JOptionPane.WARNING_MESSAGE);

                } else {

                    Action a = new DiscoverAction(in, out,
                            tableName,
                            nClusters);

                    try {
                        panelDB.setClusterOutput(a.execute());
                    } catch (ServerException e) {
                        JOptionPane.showMessageDialog(null,
                                e.getMessage(),
                                "Server error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }


            });

            // FILE Panel
            panelFile = new JPanelCluster("Store from File", evt -> {

                String tableName = panelFile.getTableTextValue();
                String nClusters = panelFile.getKTextValue();

                if (tableName.isEmpty() || nClusters.isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Please insert table name and clusters number",
                            "Input Warning",
                            JOptionPane.WARNING_MESSAGE);

                } else {

                    Action a = new ReadAction(in, out, tableName, nClusters);

                    try {
                        panelFile.setClusterOutput(a.execute());
                    } catch (ServerException e) {
                        JOptionPane.showMessageDialog(null,
                                e.getMessage(),
                                "Server error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }


            });

            addTab("DB", null, panelDB, null);
            panelDB.setLayout(new BoxLayout(panelDB, BoxLayout.PAGE_AXIS));

            addTab("File", null, panelFile, null);
            panelFile.setLayout(new BoxLayout(panelFile, BoxLayout.PAGE_AXIS));
        }

        /**
         * Model a single tab of a tabbed panel
         */
        private class JPanelCluster extends JPanel {
            private JTextField tableText;
            private JTextField kText;
            private JTextArea clusterOutput;
            private JButton executeButton;

            JPanelCluster(String buttonName, ActionListener actionListener) {

                // TOP Area
                JPanel upPanel = new JPanel();
                add(upPanel);
                upPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

                JPanel tablePanel = new JPanel();
                upPanel.add(tablePanel);

                JLabel tableName = new JLabel("Table Name");
                tablePanel.add(tableName);

                tableText = new JTextField();
                tablePanel.add(tableText);
                tableText.setColumns(15);

                JPanel kPanel = new JPanel();
                upPanel.add(kPanel);

                JLabel kCluster = new JLabel("k Cluster");
                kPanel.add(kCluster);

                kText = new JTextField();
                kPanel.add(kText);
                kText.setColumns(5);


                // CENTER Area
                JPanel centerPanel = new JPanel();
                add(centerPanel);
                centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));


                clusterOutput = new JTextArea();
                clusterOutput.setRows(25);
                clusterOutput.setEditable(false);

                JScrollPane scrollArea = new JScrollPane(clusterOutput);
                scrollArea.setLayout(new ScrollPaneLayout());
                centerPanel.add(scrollArea);


                // BOTTOM Area
                JPanel downPanel = new JPanel();
                add(downPanel);

                executeButton = new JButton(buttonName);
                downPanel.add(executeButton);
                executeButton.addActionListener(actionListener);
            }

            public void setClusterOutput(String content) {
                this.clusterOutput.setText(content);
            }

            public String getTableTextValue() {
                return tableText.getText();
            }

            public String getKTextValue() {
                return kText.getText();
            }
        }

    }
}  
