/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CtneWiii
 */
public class ServerThread implements Runnable{
    private TransferHelper socketID;
    private ListRoomClient room_client_list;
    private boolean playing = false;
    
    public ServerThread(TransferHelper socketID, ListRoomClient room_client_list)
    {
        this.socketID =socketID;
        this.room_client_list = room_client_list;
    }
    
    @Override
    public void run() {
        String REQUIRE;
        while(true)
        {
            try {
                REQUIRE = socketID.RECEIVE_s();
                if(REQUIRE == null) return;
                if(REQUIRE.compareTo("LOGIN")==0){
                        String user_name = socketID.RECEIVE_s();
                        socketID.setName(user_name);
                        room_client_list.addClient_list(user_name, socketID);
                        socketID.SEND("LOGIN_SUCCESS");
                        req_update(socketID);
                }else if(REQUIRE.compareTo("CREATE_ROOM")==0)
                {
                    if(Create_room()==true)
                            req_update_all(0);
                        else req_update_all(1);
                }else if(REQUIRE.compareTo("JOIN_ROOM")==0)
                {
                    Join_in_room(socketID.RECEIVE_s());
                }else if(REQUIRE.compareTo("OUT_ROOM")==0)
                {
                      OUT_ROOM(true);
                }else if(REQUIRE.compareTo("CHANGE_COLOR")==0)
                {
                    CHANGE_COLOR();
                }else if(REQUIRE.compareTo("READY")==0)
                {
                    READY(socketID.RECEIVE_s());
                }else if(REQUIRE.compareTo("START")==0)
                {
                    playing = true;
                    room_client_list.getRoom(socketID).SEND_both("START");
                    room_client_list.getRoom(socketID).SEND_both_location();
                }else if(REQUIRE.compareTo("ACTION")==0)
                {
                    ACTION(socketID.RECEIVE_i());
                }else if(REQUIRE.compareTo("LETGO")==0)
                {
                      LetGo();
                }
                } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    private void Join_in_room(String key)
    {
        RoomManager rm = room_client_list.getRoom(key);
        if(rm==null) return ;
        if(rm.joinGame(socketID)==true)
        {
            room_client_list.ClientToRoom(socketID.getName(), rm);
            socketID.SEND("JOIN_SUCCESS");
            socketID.SEND((rm.getColorPlayer1()+1)%2);
            rm.getEnemy(socketID).SEND("JOIN_ROOM");
            rm.getEnemy(socketID).SEND(socketID.getName());
        }
        else socketID.SEND("JOIN_FALSE");
    }
    
    private boolean Create_room()
    {
        if(room_client_list.getClient(socketID.getName())==null){
            System.out.println("create room error");
            return false;
        }
        RoomManager room = new RoomManager(socketID);
        room_client_list.ClientToRoom(socketID.getName(),room);
        return true;
    }
    
    private void req_update(TransferHelper SOCK)
    {
        String[] hostnames = room_client_list.getAllRoomName();
        Set r_keys = room_client_list.getRoom_list().keySet();
        SOCK.SEND(hostnames.length);
        for (String hostname : hostnames)
            SOCK.SEND(hostname);
    }
    // 0 khi tao phong thanh cong, 1 khi tao phong khong thanh cong, 2 khi out khoi vao
    private void req_update_all(int require) 
    {
        if(require == 0 || require == 2)
        {
            Set keys = room_client_list.getClient_list().keySet();
            for(Iterator i = keys.iterator(); i.hasNext();)
            {
                String key = (String) i.next();
                TransferHelper soc_tem = (TransferHelper) room_client_list.getClient_list().get(key);
                soc_tem.SEND("UPDATE_ROOM");
                req_update(soc_tem);
            }
            if(require == 0)
                socketID.SEND("CREATE_SUCCESS");
        }
        if(require == 1)
            socketID.SEND("CREATE_FALSE");
    }
    private void LOGIN() throws IOException///////////////////////////
    {

    }
    
    private void OUT_ROOM(boolean boo) // true if isConnecting, false if not
    {
        RoomManager rm = room_client_list.getRoom(socketID);
        int mems = rm.outRoom(socketID);
        room_client_list.RoomToClient(socketID,boo);
        if(mems == 1)
            rm.getBoss().SEND("OUT_ROOM");
        req_update_all(2);
    }
    
    private void CHANGE_COLOR()
    {
        RoomManager rm = room_client_list.getRoom(socketID);
        if(rm == null) return;
        rm.setColorforPlayer1((rm.getColorPlayer1()+1)%2);
        rm.SEND_both("CHANGE_COLOR");
        rm.SEND_both_color();
    }
    private void READY(String s)
    {
        int i = Integer.parseInt(s);
        RoomManager rm = room_client_list.getRoom(socketID);
        if(rm == null) System.out.println("null");
        rm.getEnemy(socketID).SEND("READY");
        rm.getEnemy(socketID).SEND(i);
    }
    
    private void ACTION(int ACTION)
    {
        RoomManager rm = room_client_list.getRoom(socketID);
        if(rm == null) return;
        rm.do_action(socketID,ACTION);
    }
    
    private void LetGo()
    {
        RoomManager rm = room_client_list.getRoom(socketID);
        if(rm==null) return;
        rm.startGame();
    }
}
