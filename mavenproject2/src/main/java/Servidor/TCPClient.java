package Servidor;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

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
            System.out.println("TCP Client: Conectando...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                System.out.println("TCP Client: Sent.");
                System.out.println("TCP Client: Done.");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (mRun) {
                    // try {
                    servermsj = in.readLine();
                    if (servermsj != null && mMessageListener != null) {
                        mMessageListener.messageReceived(servermsj);
                    }
                }
            } catch (Exception e) {
                System.out.println("TCP Server: Error " + e);

            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("TCP" + "C: Error" + e);
        }
    }

    public interface OnMessageReceived {

        public void messageReceived(String message);
    }
}
