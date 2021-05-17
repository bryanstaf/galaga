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
    int posBulletX = -1;
    int posBulletY = -1;
    int posX = 0;
    int posY = 0;

    public static void main(String[] args) {
        Client objcli = new Client();
        objcli.iniciar();
    }

    void iniciar() {
        // aqui se inicia el juego offline
        controlador = new GameController();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // 192.168.0.14
                mTcpClient = new TCPClient("25.12.237.194", new TCPClient.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        // if(message.contains("nombredeCliente")) ;
                        clienteRecibe(message);
                    }
                });

                mTcpClient.run();
            }
        }).start();
        // System.out.println(".messageToSend()");
        sc = new Scanner(System.in);
        // Boolean iniciar = true;
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
                    this.posBulletX = controlador.getPosBulletX();
                    this.posBulletY = controlador.getPosBulletY();

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

                    if (this.posBulletX != -1 && this.posBulletY != -1) {
                        String posBullet = String.valueOf(this.posBulletX) + " " + String.valueOf(this.posBulletY);
                        MessageToServer = "posBullet " + posBullet; //MessageToServer posBullet x y;	
                        clienteEnvia(MessageToServer);
                        controlador.setPosBulletX(-1);
                        controlador.setPosBulletY(-1);
                    }
                }
            }
        }
    }

    void clienteRecibe(String llego) {
        if (llego.trim().contains("tuID")) {
            String arrayString[] = llego.split("\\s+");
            this.myID = Integer.parseInt(arrayString[1]);
            this.idknown = true;
            this.controlador.setPlayerID(myID);
        }

        // Jugador id posX posY
        if (llego.trim().contains("Jugador ") && this.controlador.getEscenario() != null) {

            String arrayString[] = llego.split("\\s+");
            int id = Integer.parseInt(arrayString[1]);
            int x = Integer.parseInt(arrayString[2]);
            int y = Integer.parseInt(arrayString[3]);
            this.controlador.setPlayerAtPosition(id, x, y);
        }

        // posBullet posX posY
        if (llego.trim().contains("posBullet ") && this.controlador.getEscenario() != null) {
            String arrayString[] = llego.split("\\s+");
            int x = Integer.parseInt(arrayString[1]);
            int y = Integer.parseInt(arrayString[2]);
            this.controlador.setBulletAtPosition(x, y);
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
