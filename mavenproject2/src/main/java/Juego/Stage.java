package Juego;

public class Stage {

    private final int ancho;
    private final int largo;
    private final char[][] matrizEscenario;
    private boolean userAlive = true;

    public Stage(int ancho, int largo) {
        this.ancho = ancho;
        this.largo = largo;
        this.matrizEscenario = new char[largo][ancho];
    }

    public void iniciarEscenario() {
        for (int i = 0; i < this.largo; i++) {
            for (int j = 0; j < this.ancho; j++) {
                this.matrizEscenario[i][j] = ' ';
            }
        }
    }

    public void mostrarEscenario() {
        for (int i = 0; i < this.largo; i++) {
            for (int j = 0; j < this.ancho; j++) {
                System.out.print(this.matrizEscenario[i][j]);
            }
            System.out.print("\n");
        }
    }

    public void limpiarCoordenaDelEscenario(int x, int y) {
        matrizEscenario[y][x] = ' ';
    }

    public int getAnchoEscenario() {
        return this.ancho;
    }

    public int getLargoEscenario() {
        return this.largo;
    }

    public char getCaracterEnCoordenada(int x, int y) {
        return this.matrizEscenario[y][x];
    }

    public void setCaracterEnCoordenada(char icon, int x, int y) {
        this.matrizEscenario[y][x] = icon;
    }

    public void setCaracterEnCoordenada(StageComponent object, int x, int y) {
        this.matrizEscenario[y][x] = object.getIcono();
    }

    public boolean isUserAlive() {
        return this.userAlive;
    }

    public void setUserAlive(boolean userAlive) {
        this.userAlive = userAlive;
    }
}
