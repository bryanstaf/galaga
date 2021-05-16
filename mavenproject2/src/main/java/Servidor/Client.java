package Servidor;

import Juego.GameController;
import Juego.player;
import java.util.Scanner;

class Client {

    GameController controlador;
    public double Intparcial[] = new double[40];
    TCPClient mTcpClient;
    Scanner sc;
    int myID = 0;
    boolean idknown = false;
    // game variables
    String MessageToServer = "";
    player p;
    int posBomX = -1;
    int posBomY = -1;
    int posX = 0;
    int posY = 0;

    public static void main(String[] args) {
        Client cliente = new Client();
        cliente.iniciar();
    }

    void iniciar() {
        // Aquí se inicia el juego offline
        controlador = new GameController();
        new Thread(new Runnable() {

            @Override
            public void run() {
                mTcpClient = new TCPClient("25.12.237.194", new TCPClient.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        clienteRecibe(message);
                    }
                });

                mTcpClient.run();
            }
        }).start();
        sc = new Scanner(System.in);
        while (true) {
            if (mTcpClient != null) {
                if (!idknown) {
                    clienteEnvia("enviameID");
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                    // System.out.println("Cliente solicita ID");
                } else {
                    p = controlador.getCharacter();
                    this.posBomX = controlador.getPosBomX();
                    this.posBomY = controlador.getPosBomY();

                    if (p != null) {
                        String posJugador = p.getPosJugador();
                        int x = Integer.parseInt(posJugador.split("\\s")[0]);
                        int y = Integer.parseInt(posJugador.split("\\s")[1]);
                        // System.out.println("Cliente enviando la posicion"+(x)+(y));
                        if (x != posX || y != posY) {
                            // System.out.println(posJugador+p.getx());
                            MessageToServer = "posJugador " + posJugador;
                            clienteEnvia(MessageToServer);
                            // escenario = controlador.getEscenario();
                            // escenario.mostrarEscenario();
                            this.posX = x;
                            this.posY = y;
                        }
                    }

                    if (this.posBomX != -1 && this.posBomY != -1) {
                        String posBomba = String.valueOf(this.posBomX) + " " + String.valueOf(this.posBomY);
                        MessageToServer = "posBomba " + posBomba; //MessageToServer posBomba x y;	
                        clienteEnvia(MessageToServer);
                        controlador.setPosBomX(-1);
                        controlador.setPosBomY(-1);
                    }
                }
            }
        }
    }

    void clienteRecibe(String llego) {
        //System.out.println("CLINTE50 El mensaje::" + llego);
        if (llego.trim().contains("tuID")) {
            String arrayString[] = llego.split("\\s+");
            this.myID = Integer.parseInt(arrayString[1]);
            this.idknown = true;
            this.controlador.setPlayerID(myID);
        }

        if (llego.trim().contains("Jugador ") && this.controlador.getEscenario() != null) { // Jugador id posX posY
            String arrayString[] = llego.split("\\s+");
            int id = Integer.parseInt(arrayString[1]);
            int x = Integer.parseInt(arrayString[2]);
            int y = Integer.parseInt(arrayString[3]);
            this.controlador.setPlayerAtPosition(id, x, y);
        }

        if (llego.trim().contains("posBomba ") && this.controlador.getEscenario() != null) { // posBomba posX posY
            String arrayString[] = llego.split("\\s+");
            int x = Integer.parseInt(arrayString[1]);
            int y = Integer.parseInt(arrayString[2]);
            this.controlador.setBombaAtPosition(x, y);
        }
    }

    void clienteEnvia(String envia) {
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }

    double funcion(int fin) {
        double sum = 0;
        for (int j = 0; j <= fin; j++) {
            sum = sum + Math.sin(j * Math.random());
        }
        return sum;
    }
}
