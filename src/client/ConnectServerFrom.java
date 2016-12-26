/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import UIHelper.Button;
import gamelaucher.LaucherForm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author CtneWiii
 */
public class ConnectServerFrom extends JFrame{
    public static boolean success = false;
    public ConnectServerFrom()
    {
        init();
    }

    private void init()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); setResizable(false);
        setSize(300, 500); setLocationRelativeTo(null);

        JLabel lab1 = new JLabel("Start War"); lab1.setBounds(0, 0, 300, 80);
        lab1.setBackground(new Color(255, 47, 23));lab1.setOpaque(true);
        lab1.setForeground(new Color(238, 238, 238));lab1.setFont(new java.awt.Font("Sylfaen", 1, 35));
        lab1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);add(lab1);

        JLabel lab3 = new JLabel("Server");
        lab3.setBounds(40, 140, 70, 12);
        lab3.setFont(new java.awt.Font("Aria", 0, 12));
        add(lab3);
        txtServerName.setBounds(40, 160, 220, 40);
        txtServerName.setFont(new java.awt.Font("Verdana", 0, 20));
        add(txtServerName);

        JLabel lab4 = new JLabel("Port");
        lab4.setBounds(40, 220, 70, 12);
        lab4.setFont(new java.awt.Font("Aria", 0, 12));
        add(lab4);
        txtPort.setBounds(40, 240, 220, 40);
        txtPort.setFont(new java.awt.Font("Verdana", 0, 20));
        add(txtPort);

        JLabel lab5 = new JLabel("UserName");
        lab5.setBounds(40, 300, 70, 12);
        lab5.setFont(new java.awt.Font("Aria", 0, 12));
        add(lab5);
        txtUserName.setBounds(40, 320, 220, 40);
        txtUserName.setFont(new java.awt.Font("Verdana", 0, 20));
        add(txtUserName);
        add(Connectbt);

        Connectbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(txtUserName.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "UserName is empty");
                }
                else ConnectServer();}});
    }

    private void ConnectServer()
    {
        try {
            Socket socket = new Socket(InetAddress.getByName(txtServerName.getText()), Integer.parseInt(txtPort.getText()));
            Client client = new Client(socket);
            Thread thread = new Thread(client);
            thread.start();
            client.getTransferHelper().Send("LOGIN");
            client.getTransferHelper().Send(txtUserName.getText());
            client.setWaitting();
            if(success == true)
            {
                client.setClientName(txtUserName.getText());
                new LaucherForm(client).setVisible(true);
                this.dispose();
            }
            else
                JOptionPane.showMessageDialog(null, "Log in failed");
        } catch (IOException ex) {
            Logger.getLogger(ConnectServerFrom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JTextField txtPort = new JTextField("7777");
    private JTextField txtServerName = new JTextField("127.0.0.1");
    private JTextField txtUserName = new JTextField("");
    private Button Connectbt = new Button("Connect");

    public static void main(String[] args) {
        new ConnectServerFrom().setVisible(true);
    }
}
