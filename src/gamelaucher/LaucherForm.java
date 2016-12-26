package gamelaucher;

import client.Client;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

public class LaucherForm extends JFrame {

    private JPanel contentPane;
    private RoomListPanel roomListPanel;
    private MethodPanel methodPanel;
    private Client client;

    public LaucherForm(Client client) {
        this.client = client;
        init();
        this.client.remote_lobby(this);
    }
    private void init()
    {
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        roomListPanel = RoomListPanel.getInstance(client);
        contentPane.add(roomListPanel);
        
        methodPanel = new MethodPanel(client);
        contentPane.add(methodPanel);
        this.pack();
    }
    
    public void update_list_room()
    {
        roomListPanel.removeAllRoom();
        roomListPanel.addRooms(client.listRoom);
    }
    
    public void setEnableJoinButton(boolean active)
    {
        methodPanel.setJoinButton(active);
    }
    
    public void openRoomFrame(int i,boolean isBoss)
    {
        new RoomForm(client,i,isBoss).setVisible(true);
        client.remote_lobby(null);
        this.dispose();
    }
}
