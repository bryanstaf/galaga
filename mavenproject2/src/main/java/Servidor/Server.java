package Servidor;

import java.util.Scanner;

public class Server {

    TCPServer mTcpServer;
    Scanner sc;
    int cantClientesActivos = 0;

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
                            servidorRecibe(message);
                        }
                    }
                });
                mTcpServer.run();

            }
        }).start();

        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor bandera 01");
        while (!salir.equals("s")) {
            salir = sc.nextLine();
        }

    }

    int contarcliente = 0;
    double rptacli[] = new double[20];
    double sumclient = 0;

    void servidorRecibe(String llego) {
        System.out.println("SERVIDOR -> El mensaje:" + llego + " del cliente");// recibe --> posJugador x y
        if (mTcpServer.getNumeroClients() > cantClientesActivos) {
            cantClientesActivos = mTcpServer.getNumeroClients();
            servidorEnvia(cantClientesActivos);
        }
        if (llego != null && !llego.equals("")) {
            // Para posicion del jugador
            if (llego.trim().contains("posJugador")) {
                String jugador[] = llego.split("\\s+");
                int posX = Integer.parseInt(jugador[1]);
                int posY = Integer.parseInt(jugador[2]);
                int id = Integer.parseInt(jugador[jugador.length - 1]);
                System.out.println("Jugador " + String.valueOf(id) + " " + jugador[1] + " " + jugador[2]);
                servidorEnviaPosJugador("Jugador " + String.valueOf(id) + " " + jugador[1] + " " + jugador[2], id);
                System.err.println("x;" + jugador[1] + "y:" + jugador[2]);
            }
            if (llego.trim().contains("enviameID")) {
                String jugador[] = llego.split("\\s+");
                String id = jugador[jugador.length - 1];
                servidorEnviaID("tuID " + id, Integer.parseInt(id));
            }
            if (llego.trim().contains("posBullet")) {
                String[] bullet = llego.split("\\s+");
                String posBullet = bullet[1] + " " + bullet[2];
                System.out.println(bullet[1] + " " + bullet[2]);
                servidorEnviaPosBullet(posBullet);
                System.err.println("x;" + bullet[1] + "y:" + bullet[2]);
            }

        }
    }

    void servidorEnviaPosJugador(String msg, int id) {
        if (msg != null) {
            mTcpServer.sendMessageToOtherClients(msg, id);
        }
    }

    void servidorEnviaPosBullet(String msg) {
        if (msg != null) {
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServer("posBullet " + msg);
            } else {
                System.out.println("¡No tiene envío!");
            }
        }
    }

    void servidorEnvia(int numClientes) {// El servidor tiene texto de envio
        String envia = String.valueOf(numClientes);
        if (envia != null) {
            System.out.println("Soy Server y envio " + envia);
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServer("numJugadores " + envia);
            } else {
                System.out.println("¡No tiene envío!");
            }
        }
    }

    void servidorEnviaID(String msg, int id) {// El servidor tiene texto de envio
        if (msg != null) {
            System.out.println("Soy Server y envio " + msg);
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServertoClient("numJugadores " + msg, id);
            } else {
                System.out.println("¡No tiene envío!");
            }
        }
    }
}
