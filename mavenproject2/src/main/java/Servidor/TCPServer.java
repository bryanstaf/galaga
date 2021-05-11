package Servidor;

import java.io.BufferedReader;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private String message;

    int nrcli = 0;
    int numClientes = 0;

    public static final int SERVERPORT = 4444;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    TCPServerThread[] sendclis = new TCPServerThread[10];

    PrintWriter mOut;
    BufferedReader in;

    ServerSocket serverSocket;

    // el constructor pide una interface OnMessageReceived
    public TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }

    public OnMessageReceived getMessageListener() {///// ¨
        return this.messageListener;
    }

    public void sendMessageToOtherClients(String message, int id) {
        for (int i = 1; i <= numClientes; i++) {
            if (i != id) {
                System.out.println("Enviando pos a otros jugadores ...");
                sendclis[i].sendMessage(message);
            }
        }
    }

    void sendMessageTCPServertoClient(String string, int id) {
        for (int i = 1; i <= nrcli; i++) {
            if (i == id) {
                System.out.println("ENVIANDO ID A JUGADOR " + (i));
                sendclis[i].sendMessage("tuID " + String.valueOf(i));
            }
        }
    }

    public void sendMessageTCPServer(String message) {
        System.out.println(String.valueOf(nrcli));
        for (int i = 1; i <= nrcli; i++) {

            sendclis[i].sendMessage(message);
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
    }

    public void sendMessageTCPServerRango(String message, int Rango) {
        int d = (int) ((Rango) / nrcli);
        int inicio = 0;
        for (int i = 1; i < nrcli; i++) {
            // System.out.println("i:" + ((i-1) * d + 1) + "(i+d):" + ((i-1) * d + d));
            sendclis[i].sendMessage("evalua " + (inicio + (i - 1) * d) + " " + ((i - 1) * d + d));
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
        // System.out.println("i:" + ((d * (nrcli - 1))+1) + "(N):" + (Rango));
        sendclis[nrcli].sendMessage("evalua " + (inicio + (d * (nrcli - 1))) + " " + (Rango));
        System.out.println("ENVIANDO A JUGADOR " + (nrcli));
    }

    public void run() {
        running = true;
        try {
            System.out.println("TCP Server" + "S : Connecting...");
            serverSocket = new ServerSocket(SERVERPORT);

            while (running) {
                Socket client = serverSocket.accept();
                System.out.println("TCP Server" + "S: Receiving...");
                nrcli++;
                System.out.println("Engendrado " + nrcli);
                sendclis[nrcli] = new TCPServerThread(client, this, nrcli, sendclis);
                Thread t = new Thread(sendclis[nrcli]);
                t.start();
                numClientes = nrcli;
                System.out.println("Nuevo conectado:" + nrcli + " jugadores conectados");

            }

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        } finally {

        }
    }

    public TCPServerThread[] getClients() {
        return sendclis;
    }

    public int getNumeroClients() {
        return numClientes;
    }

    public interface OnMessageReceived {
        public void messageReceived(String message);
        // public void messageToSend();
    }
}
