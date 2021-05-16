package Servidor;

import java.io.BufferedReader;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private String message;

    int nroCliente = 0;
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
        for (int i = 1; i <= nroCliente; i++) {
            if (i == id) {
                System.out.println("ENVIANDO ID A JUGADOR " + (i));
                sendclis[i].sendMessage("tuID " + String.valueOf(i));
            }
        }
    }

    public void sendMessageTCPServer(String message) {
        System.out.println(String.valueOf(nroCliente));
        for (int i = 1; i <= nroCliente; i++) {

            sendclis[i].sendMessage(message);
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
    }

    public void sendMessageTCPServerRango(String message, int Rango) {
        int d = (int) ((Rango) / nroCliente);
        int inicio = 0;
        for (int i = 1; i < nroCliente; i++) {
            // System.out.println("i:" + ((i-1) * d + 1) + "(i+d):" + ((i-1) * d + d));
            sendclis[i].sendMessage("evalua " + (inicio + (i - 1) * d) + " " + ((i - 1) * d + d));
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
        // System.out.println("i:" + ((d * (nroCliente - 1))+1) + "(N):" + (Rango));
        sendclis[nroCliente].sendMessage("evalua " + (inicio + (d * (nroCliente - 1))) + " " + (Rango));
        System.out.println("ENVIANDO A JUGADOR " + (nroCliente));
    }

    public void run() {
        running = true;
        try {
            System.out.println("TCP Server : Connecting...");
            serverSocket = new ServerSocket(SERVERPORT);

            while (running) {
                Socket client = serverSocket.accept();
                System.out.println("TCP Server: Receiving...");
                nroCliente++;
                System.out.println("Engendrado: " + nroCliente);
                sendclis[nroCliente] = new TCPServerThread(client, this, nroCliente, sendclis);
                for (int i = 0; i < sendclis.length; i++) {
                    System.out.println("sendclis: [i] = " + i + ", nroCliente = " + nroCliente + ", sendclis[i] = " + sendclis[i] +  ", sendclis[nroCliente] = " + sendclis[nroCliente]);
                }
                Thread t = new Thread(sendclis[nroCliente]);
                t.start();
                numClientes = nroCliente;
                System.out.println("Nuevo conectado: " + nroCliente + " jugadores conectados");

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
