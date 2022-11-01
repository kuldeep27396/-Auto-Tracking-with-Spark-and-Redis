/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxilocator.gui;

import com.okmich.taxilocator.FlowMediator;
import com.okmich.taxilocator.gui.model.DashboardModel;
import com.okmich.taxilocator.redis.JedisSubsriber;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author datadev
 */
public class AppFrame extends JFrame {

    private JLabel statusLabel;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItemExit;
    private JScrollPane jScrollPane1;
    private JButton jServerConnectBtn;
    private JToolBar jToolBar1;
    private JTable reportTable;

    private boolean isConnected = false;

    private final DashboardModel dashboardModel;
    private final FlowMediator flowMediator;

    /**
     * Creates new form AppFrame
     *
     * @param flowMediator
     */
    public AppFrame(FlowMediator flowMediator) {
        this.flowMediator = flowMediator;
        this.dashboardModel = ((FlowMediatorImpl) flowMediator).getDashboardModel();

        initComponents();
    }

    private void initComponents() {
        jMenuItem1 = new JMenuItem();
        jToolBar1 = new JToolBar();
        jServerConnectBtn = new JButton();
        statusLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        reportTable = new JTable();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItemExit = new JMenuItem();
        jMenu2 = new JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Taxi locator dashboard");
        setPreferredSize(new java.awt.Dimension(800, 600));
//        setResizable(false);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        jToolBar1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jServerConnectBtn.setText("Connect   ");
        jServerConnectBtn.setFocusable(false);
        jServerConnectBtn.setHorizontalAlignment(SwingConstants.RIGHT);
        jServerConnectBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        jServerConnectBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        jServerConnectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleConnectionActionPerformed(e);
            }
        });
        jToolBar1.add(jServerConnectBtn);

        statusLabel.setFont(new java.awt.Font("Ubuntu", 1, 11)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(255, 0, 0));
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        statusLabel.setText("Not Connected");
        statusLabel.setToolTipText("");
        jToolBar1.add(statusLabel);

        getContentPane().add(jToolBar1);

        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        reportTable.setModel(dashboardModel);
        reportTable.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(reportTable);

        getContentPane().add(jScrollPane1);

        jMenu1.setText("File");

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");
        jMenu2.setToolTipText("");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {
        closeServerConnection();
        System.exit(0);
    }

    private void toggleConnectionActionPerformed(java.awt.event.ActionEvent evt) {
        if (!isConnected) {
            this.isConnected = flowMediator.connect();
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setText("Connected");
            jServerConnectBtn.setText("Disconnect");
        } else {
            this.isConnected = !flowMediator.disconnect();
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Not Connected");
            jServerConnectBtn.setText("Connect   ");
        }

    }

    private void closeServerConnection() {
        //unsubscribed to server

    }
}
