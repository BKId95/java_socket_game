/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import client.Client;
import javax.swing.JFrame;

/**
 *
 * @author CtneWiii
 */
public class GameForm extends JFrame{
    private ShipLocation ol;
    private Client client;
    private int color;
    private String name;
    public GameForm(Client client, int color, String name, ShipLocation ol)
    {
        this.client=client;
        this.color= color;
        this.name= name;
        this.ol = ol;
        init();
        client.remote_Game(this);
    }
    
    private void init()
    {
        setSize(430 , 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new GamePanel(this.getSize().width,this.getSize().height,ol, client));
    }
}
