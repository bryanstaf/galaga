package Servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    private String servermsj;
    public String SERVERIP;
    public static final int SERVERPORT = 4444;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    public TCPClient(String ip, OnMessageReceived listener) {
        SERVERIP = ip;
        mMessageListener = listener;
    }

    public void sendMessage(String message) {
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }

    public void stopClient() {
        mRun = false;
    }

    public void run() {
        mRun = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            System.out.println("TCP Client" + "C: Conectando...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                System.out.println("TCP Client" + "C: Sent.");
                System.out.println("TCP Client" + "C: Done.");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // (new Thread() {
                // public void run() {
                while (mRun) {
                    // try {
                    servermsj = in.readLine();
                    // } catch (IOException ex) {
                    // System.out.println("Cliente.run()");
                    // Logger.getLogger(TCPClient50.class.getName()).log(Level.SEVERE, null, ex);
                    //System.out.println("Servidor.TCPClient50.run()");
                    if (servermsj != null && mMessageListener != null) {
                        mMessageListener.messageReceived(servermsj);
                    }

                    // servermsj = null;
                }
                // }
                // }).start();

                /*
                 * (new Thread() { public void run() { while (mRun) {
                 * 
                 * if(mMessageListener != null){ mMessageListener.messageToSend(); } //servermsj
                 * = null; } } }).start();
                 */
                /*
                 * while (mRun) { servermsj = in.readLine();
                 * 
                 * if (servermsj != null && mMessageListener != null) {
                 * mMessageListener.messageReceived(servermsj); } if(mMessageListener != null){
                 * mMessageListener.messageToSend(); } //servermsj = null; }
                 */
            } catch (Exception e) {
                System.out.println("TCP" + "S: Error" + e);

            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("TCP" + "C: Error" + e);
        }
    }

    public interface OnMessageReceived {
        public void messageReceived(String message);
        // public void messageToSend();
    }
}