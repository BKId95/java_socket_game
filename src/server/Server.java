/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CtneWiii
 */
public class Server {

    static ListRoomClient listRoomClient = new ListRoomClient();
    public static void main(String[] args) {
        final int port = 7777;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Running...");
            while(true)
            {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort()+" connected");
                TransferHelper transferHelper = new TransferHelper();
                transferHelper.SERVER_ACCEPT_SocketID(socket);
                ServerThread serverThread = new ServerThread(transferHelper, listRoomClient);
                Thread thread = new Thread(serverThread);
                thread.start();
            }
        } catch (IOException ex) {
        }
    }
}
