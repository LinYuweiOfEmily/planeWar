package com.example.lib;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

public class MyClass {
    public static void main(String[] args){
        new MyClass();
    }
    private Vector<ServerSocketThread> vector = new Vector<>();
    private Queue<Socket> waitingClients = new LinkedList<>();
    public MyClass(){
        try {
            // 1. 创建ServerSocket
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("--Listener Port: 9999--");
            while (true) {
                //2.等待接收请求，这里接收客户端的请求
                Socket client = serverSocket.accept();
                System.out.println("New client connected: " + client.getInetAddress().getHostAddress());
                //3.开启子线程线程处理和客户端的消息传输
                synchronized (waitingClients) {
                    waitingClients.add(client);
                    // 尝试匹配
                    matchClients();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void matchClients() {
        synchronized (waitingClients) {
            if (waitingClients.size() >= 2) {
                Socket client1 = waitingClients.poll();
                Socket client2 = waitingClients.poll();
                ServerSocketThread thread1 = new ServerSocketThread(client1,vector);
                ServerSocketThread thread2 = new ServerSocketThread(client2,vector);
                thread1.start();
                thread2.start();
                System.out.println("Clients matched and handlers started.");
            }
        }
    }
}