package gamelaucher;

import UIHelper.Button;
import client.Client;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

public class RoomButton extends Button {
	private String text;
    private Client client;
	private EtchedBorder border = new EtchedBorder(EtchedBorder.RAISED, new Color(102, 153, 51), new Color(102, 153, 102));

    public RoomButton(String text, Client client){
            super(text);
            this.client = client;
            this.text = text;
            this.setPreferredSize(new Dimension(50, 100));

            this.setFont(new java.awt.Font("Verdana", 1, 15));

            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    RoomListPanel.getInstance(client).refresh();
                    setBorder(border);
                    setBackground(new java.awt.Color(0xFA0012));
                    client.setChoosedRoom(text);
                    if(text!=null)
                        if(text.compareTo("")!=0)
                            client.setEnableJoinButton(true);
                }
            });
	}

	public void unChoose(){
	    this.setBackground(new java.awt.Color(0x5858FA));
    }
}