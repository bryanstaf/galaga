package Juego;

public class Stage {

    private int ancho, largo;
    private char[][] matrizEscenario;
    private boolean userAlive = true;
    
    public Stage(int ancho, int largo) {
        this.ancho = ancho;
        this.largo = largo;
        this.matrizEscenario = new char[largo][ancho];
    }

    public void iniciarEscenario() {
        for (int i = 0; i < largo; i++) {
            for (int j = 0; j < ancho; j++) {
                matrizEscenario[i][j] = ' ';
            }
        }
    }

    public void mostrarEscenario() {

        for (int i = 0; i < largo; i++) {
            for (int j = 0; j < ancho; j++) {
                System.out.print(matrizEscenario[i][j]);
            }
            System.out.print("\n");
        }
    }

    public void limpiarCoordenaDelEscenario(int x, int y) {
        matrizEscenario[y][x] = ' ';
    }

    public int getAnchoEscenario() {
        return ancho;
    }

    public int getLargoEscenario() {
        return largo;
    }

    public char getCaracterEnCoordenada(int x, int y) {
        return matrizEscenario[y][x];
    }

    public void setCaracterEnCoordenada(char icon, int x, int y) {
        matrizEscenario[y][x] = icon;
    }

    public void setCaracterEnCoordenada(StageComponent object, int x, int y) {
        matrizEscenario[y][x] = object.getIcono();
    }

	public boolean isUserAlive() {
		return userAlive;
	}

	public void setUserAlive(boolean userAlive) {
		this.userAlive = userAlive;
	}
}
