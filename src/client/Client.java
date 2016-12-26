/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import gamelaucher.LaucherForm;
import gamelaucher.RoomForm;
import server.Bullets;
import server.Enemies;
import server.Explodes;
import com.google.gson.Gson;
import gameplay.GameForm;
import gameplay.ShipLocation;
import gameplay.GamePanel;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author CtneWiii
 */
public class Client implements Runnable{
    private TransferHelper transferHelper;
    private String choosedRoom = null;
    public boolean isIngame = false;
    public Client(Socket socket)
    {
        this.socket = socket;
        transferHelper = new TransferHelper(socket);
    }
    public void remote_roomUI(RoomForm roomForm)
    {
        this.roomForm = roomForm;
    }
    public void remote_lobby(LaucherForm Lobby)
    {
        this.Lobby = Lobby;
    }

    public void remote_Game(GameForm game)
    {
        this.game = game;
    }
    public void remote_inGame(GamePanel ingame)
    {
        this.ingame = ingame;
    }
    public String getchoosedRoom()
    {
        return choosedRoom;
    }
    public void setChoosedRoom(String s)
    {
        choosedRoom = s;
    }
    public TransferHelper getTransferHelper()
    {
        return transferHelper;
    }
    
    public boolean isWaittingServer()
    {
        return waitting;
    }
    
    public void setNotWaitting()
    {
        waitting = false;
    }
    
    public void setWaitting()
    {
        waitting = true;
        while(isWaittingServer())System.out.print("");
    }
    public void setClientName(String s)
    {
        client_name =s;
    }
    public String getName()
    {
        return client_name;
    }
    public void update_room() throws IOException
    {
        int ii = transferHelper.ReceiveInt();
        listRoom.clear();
        for(int i=0;i<ii;i++)
            listRoom.add(transferHelper.ReceiveString());
        setNotWaitting();
    }
    
    public void setEnableJoinButton(boolean active)
    {
        Lobby.setEnableJoinButton(active);
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                String REQUIRE = transferHelper.ReceiveString();
                if(REQUIRE== null) return;
                System.out.println(REQUIRE);
                switch(REQUIRE)
                {
                    case "LOGIN_SUCCESS":
                        ConnectServerFrom.success = true;
                    case "UPDATE_ROOM":
                        update_room();
                        while(Lobby == null) System.out.print("");
                        Lobby.update_list_room();
                        break;
                    case "CREATE_SUCCESS":
                        setNotWaitting();
                        Lobby.openRoomFrame(0,true);
                        break;
                    case "JOIN_SUCCESS":
                        int temp = transferHelper.ReceiveInt();
                        setNotWaitting();
                        Lobby.openRoomFrame(temp,false);
                        break;
                    case "LOGIN_FALSE":
                        ConnectServerFrom.success = false;
                    case "CREATE_FALSE":
                    case "JOIN_FALSE": 
                        setNotWaitting();
                        break;
                    case "JOIN_ROOM":
                        roomForm.setEnemyInfo(transferHelper.ReceiveString());
                        break;
                    case "OUT_ROOM":
                        OUT_ROOM();
                        break;
                    case "CHANGE_COLOR":
                        setNotWaitting();
                        roomForm.setIcon(transferHelper.ReceiveInt());
                        break;
                    case "READY":
                        roomForm.setStartButtonEnable(transferHelper.ReceiveInt());
                        break;
                    case "START":
                        String start_json = transferHelper.ReceiveString();
                        setNotWaitting();
                        START_GAME(start_json);
                        break;
                    case "ANIMATION":
                        String ship= transferHelper.ReceiveString();
                        String bullets= transferHelper.ReceiveString();
                        String enemies= transferHelper.ReceiveString();
                        String explodes = transferHelper.ReceiveString();
                        DO_ANIMATION(ship,bullets,enemies,explodes);
                    default:
                        break;
                }
            } catch (IOException ex) {}
        }
    }
    private void OUT_ROOM()
    {
        if(isIngame== true)
        {
            isIngame = false;
            roomForm.setVisible(true);
            game.dispose();
            game = null;
        }
        roomForm.setEnemyInfo(null);
        roomForm.checkBoss(true);
    }
    
    private void START_GAME(String s)
    {
        Gson gson = new Gson();
        ShipLocation ol = gson.fromJson(s, ShipLocation.class);
        roomForm.PlayGame(ol);
        isIngame = true;
    }
    public void gameover()
    {
        game.dispose();
        roomForm.setVisible(true);
    }
    public void logout()
    {
        if(Lobby != null) Lobby.dispose();
        Lobby = null;
    }
    
    private void DO_ANIMATION(String plane,String bullets,String enemies,String explodes)
    {
        Gson gson = new Gson();
        ShipLocation ol = gson.fromJson(plane, ShipLocation.class);
        Bullets bs = gson.fromJson(bullets,Bullets.class);
        Enemies e = gson.fromJson(enemies,Enemies.class);
        Explodes explo = gson.fromJson(explodes, Explodes.class);
        ingame.animation(ol,bs,e,explo);
    }
    
    public ArrayList listRoom = new ArrayList();
    private LaucherForm Lobby;
    private RoomForm roomForm;
    private boolean waitting = true;
    private Socket socket;
    private String client_name;
    private GameForm game;
    private GamePanel ingame;
}
