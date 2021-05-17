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
    TCPServerThread[] threadsClientes = new TCPServerThread[10];

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
                threadsClientes[i].sendMessage(message);
            }
        }
    }

    void sendMessageTCPServertoClient(String string, int id) {
        for (int i = 1; i <= this.nroCliente; i++) {
            if (i == id) {
                System.out.println("ENVIANDO ID A JUGADOR " + (i));
                threadsClientes[i].sendMessage("tuID " + String.valueOf(i));
            }
        }
    }

    public void sendMessageTCPServer(String message) {
        System.out.println(String.valueOf(nroCliente));
        for (int i = 1; i <= this.nroCliente; i++) {

            threadsClientes[i].sendMessage(message);
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
    }

    public void sendMessageTCPServerRango(String message, int Rango) {
        int d = (int) ((Rango) / nroCliente);
        int inicio = 0;
        for (int i = 1; i < nroCliente; i++) {
            threadsClientes[i].sendMessage("Evalua " + (inicio + (i - 1) * d) + " " + ((i - 1) * d + d));
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
        threadsClientes[nroCliente].sendMessage("evalua " + (inicio + (d * (nroCliente - 1))) + " " + (Rango));
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
                threadsClientes[nroCliente] = new TCPServerThread(client, this, nroCliente, threadsClientes);
                Thread t = new Thread(threadsClientes[nroCliente]);
                t.start();
                numClientes = nroCliente;
                System.out.println("Nuevo conectado: " + nroCliente + " jugadores conectados");

            }

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public TCPServerThread[] getClients() {
        return threadsClientes;
    }

    public int getNumeroClients() {
        return numClientes;
    }

    public interface OnMessageReceived {

        public void messageReceived(String message);
    }
}
