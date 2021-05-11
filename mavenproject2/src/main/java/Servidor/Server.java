package Servidor;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;

public class Server {

    TCPServer mTcpServer;
    Scanner sc;
    int ClientesActivos = 0;

    private Lock cierreRed;

    public static void main(String[] args) {
        Server objser = new Server();
        objser.iniciar();
    }

    void iniciar() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                mTcpServer = new TCPServer(new TCPServer.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        synchronized (this) {
                            ServidorRecibe(message);
                        }
                    }
                });
                mTcpServer.run();

            }
        }).start();

        /*
         * while(true){ //if(mTcpServer!=null){ ServidorEnvia(12); //} }
         */
        // -----------------
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor bandera 01");
        while (!salir.equals("s")) {
            salir = sc.nextLine();
        }
        // System.out.println("Servidor bandera 02");

    }

    int contarcliente = 0;
    double rptacli[] = new double[20];
    double sumclient = 0;

    void ServidorRecibe(String llego) {
        System.out.println("SERVIDOR40 El mensaje:" + llego + " del cliente");// recibe --> posJugador x y
        if (mTcpServer.getNumeroClients() > ClientesActivos) {
            ClientesActivos = mTcpServer.getNumeroClients();
            ServidorEnvia(ClientesActivos);
        }
        if (llego != null && !llego.equals("")) {
            // Para posicion del jugador
            if (llego.trim().contains("posJugador")) {
                String jugador[] = llego.split("\\s+");
                int posX = Integer.parseInt(jugador[1]);
                int posY = Integer.parseInt(jugador[2]);
                int id = Integer.parseInt(jugador[jugador.length - 1]);
                System.out.println("Jugador " + String.valueOf(id) + " " + jugador[1] + " " + jugador[2]);
                ServidorEnviaPosJugador("Jugador " + String.valueOf(id) + " " + jugador[1] + " " + jugador[2], id); // mandar
                                                                                                                    // -->
                                                                                                                    // Jugador
                                                                                                                    // ID
                                                                                                                    // x
                                                                                                                    // y
                System.err.println("x;" + jugador[1] + "y:" + jugador[2]);
            }
            if (llego.trim().contains("enviameID")) {
                String jugador[] = llego.split("\\s+");
                String id = jugador[jugador.length - 1];
                ServidorEnviaID("tuID " + id, Integer.parseInt(id));
            }
            if (llego.trim().contains("posBomba")) {
        		String bomba[] = llego.split("\\s+");
                String posBomba = bomba[1] + " " + bomba[2];
                System.out.println(bomba[1] + " " + bomba[2]);
                ServidorEnviaPosBomba(posBomba);
                System.err.println("x;" + bomba[1] + "y:" + bomba[2]);
            }

        }
        /*
         * if (llego != null && !llego.equals("")) { if (llego.trim().contains("rpta"))
         * { String arrString[] = llego.split("\\s+"); double data =
         * Double.parseDouble(arrString[1]); if (data > 0) { rptacli[contarcliente] =
         * data; System.out.println("i:" + contarcliente + " rptacli[i]" +
         * rptacli[contarcliente]); System.out.println("Llego el servidor: " + data);
         * System.out.println("Ellos son:" + this.mTcpServer.nrcli);//cuantos clientes
         * son contarcliente = contarcliente + 1; //incremento un cliente
         * 
         * if (contarcliente == this.mTcpServer.nrcli) { for (int i = 0; i <
         * contarcliente; i++) { System.out.println("ya   i:" + i + " rptacli[i]" +
         * rptacli[i]); sumclient = sumclient + rptacli[i];
         * 
         * } System.out.println("LA RESPUESTA TOTAL ES:" + sumclient); contarcliente =
         * 0; sumclient = 0; for (int i = 0; i < contarcliente; i++) { rptacli[i] = 0; }
         * 
         * } } } }
         */
    }

    void ServidorEnviaNumClientes(int numClientes) {
        String envia = String.valueOf(numClientes);
        if (envia != null) {
            System.out.println("Soy Server y envio" + envia);
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServer("numJugadores " + envia);
            } else {
                System.out.println("NO TIENE ENVIO!!!");
            }
        }
    }

    void ServidorEnviaPosJugador(String msg, int id) {
        // Jugador ID x y
        if (msg != null) {
            mTcpServer.sendMessageToOtherClients(msg, id);
        }
    }
    
    void ServidorEnviaPosBomba(String msg) {
        if (msg != null) {
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServer("posBomba " + msg);
            } else {
                System.out.println("NO TIENE ENVIO!!!");
            }
        }
    }

    void ServidorEnvia(int numClientes) {// El servidor tiene texto de envio
        String envia = String.valueOf(numClientes);
        if (envia != null) {
            System.out.println("Soy Server y envio" + envia);
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServer("numJugadores " + envia);
            } else {
                System.out.println("NO TIENE ENVIO!!!");
            }
        }
    }

    void ServidorEnviaID(String msg, int id) {// El servidor tiene texto de envio
        if (msg != null) {
            System.out.println("Soy Server y envio" + msg);
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServertoClient("numJugadores " + msg, id);
            } else {
                System.out.println("NO TIENE ENVIO!!!");
            }
        }
    }

    // posicion "x y"
    /*
     * void ServidorEnvia(String envia) {//El servidor tiene texto de envio if
     * (envia != null) { System.out.println("Soy Server y envio" + envia); if
     * (envia.trim().contains("envio")) { System.out.println("SI TIENE ENVIO!!!");
     * String arrayString[] = envia.split("\\s+"); int data =
     * Integer.parseInt(arrayString[1]); if (mTcpServer != null) {
     * mTcpServer.sendMessageTCPServerRango(envia, data); } } else {
     * System.out.println("NO TIENE ENVIO!!!"); } } }
     */
}
