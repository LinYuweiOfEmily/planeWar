package com.example.lib;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class ServerSocketThread extends Thread{
    private BufferedReader in;
    private PrintWriter pw;
    private String name;
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    private Vector<ServerSocketThread> vector;
    private ObjectInputStream oIn;
    private ObjectOutputStream oOut;

    public ServerSocketThread(Socket socket, Vector<ServerSocketThread> vector){
        this.socket = socket;
        this.vector = vector;
        vector.add(this);
    }

    @Override
    public void run(){
        try {
            this.sendMessage("success match!");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
            String content;

            while ((content = in.readLine()) != null) {
                //4.和客户端通信
                for(int i=0;i<vector.size();i++){
                    ServerSocketThread st = vector.get(i);
                    if(st!=this){
                        st.sendMessage(content);
                    }
                }
            }
            vector.remove(this);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String content) {
        PrintWriter pout = null;
        try{
            System.out.println("message to client:" + content);
            pout = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(),"utf-8")),true);
            pout.println(content);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
