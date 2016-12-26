package gamelaucher;

import UIHelper.Button;
import client.Client;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MethodPanel extends JPanel implements ActionListener{
    private static final int width = 200;
    private static final int height = 500;
    private Client client;
    public Button create_button = new Button("CREATE ROOM");
    public Button join_button = new Button("JOIN");

    public MethodPanel(Client client){
        super();
        this.client= client;
        this.setLayout(new GridLayout(0, 1));
        this.setPreferredSize(new Dimension(width, height));
        create_button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        join_button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        this.add(Box.createVerticalStrut(100));
        this.add(create_button);
        this.add(Box.createVerticalStrut(100));
        this.add(join_button);
        this.add(Box.createVerticalStrut(100));
        this.add(Box.createVerticalStrut(100));

        join_button.setEnabled(false);

        create_button.addActionListener(this);
        join_button.addActionListener(this);
    }
    
    private void Create_Room()
    {
        client.getTransferHelper().Send("CREATE_ROOM");
        client.setWaitting();
    }
    
    private void Join_Room(String room)
    {
        if(client.getchoosedRoom()!=null)
            if(client.getchoosedRoom().compareTo("")!=0)
            {
                client.getTransferHelper().Send("JOIN_ROOM");
                client.getTransferHelper().Send(room);
                client.setWaitting();
            }
        System.out.println("JOIN");
    }
    
    public void setJoinButton(boolean isActive)
    {
        join_button.setEnabled(isActive);
    }
    
    public void logout()
    {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object ob = ae.getSource();
        if(ob == create_button)
            Create_Room();
        if(ob == join_button)
            Join_Room(client.getchoosedRoom());
    }
}
