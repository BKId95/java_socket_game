/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CtneWiii
 */
public class TransferHelper {
    private Socket socket;
    private BufferedWriter os ;
    private BufferedReader is;
    public TransferHelper(Socket socket)
    {
        try {
            this.socket = socket;
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
        }
    }
    
    public void Send(String s)
    {
        try {
            os.write(s);
            os.newLine();
            os.flush();
        } catch (IOException ex) {
        }
    }
    public void Send(int i)
    {
        try {
            os.write(i);
            os.flush();
        } catch (IOException ex) {
        }
    }
    
    public String ReceiveString() throws IOException
    {
        return is.readLine();
    }
    public int ReceiveInt() throws IOException
    {
        return is.read();
    }
}
