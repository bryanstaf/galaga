package Juego;

public class CreateStage {

    public final int LARGO_ESCENARIO = 60;
    public final int ANCHO_ESCENARIO = 15;
    int recorrerY = 2;
    Stage escenario = new Stage(LARGO_ESCENARIO, ANCHO_ESCENARIO);
    player p1;
    int x = 0, y = 0;

    public CreateStage(int x, int y) {
        this.x = x;
        this.y = y;
        p1 = new player(x, y, LARGO_ESCENARIO - 1, ANCHO_ESCENARIO - 1, escenario);
    }

    public player getJugador() {
        return this.p1;
    }

    public Stage getEscenario() {
        return this.escenario;
    }

    public void iniciar() {
        escenario.iniciarEscenario();

        Wall muro = new Wall();

        muro.agregarMuroFila(escenario, muro, 0);
        muro.agregarMuroFila(escenario, muro, escenario.getLargoEscenario() - 1);
        muro.agregarMuroColumna(escenario, muro, 0);
        muro.agregarMuroColumna(escenario, muro, escenario.getAnchoEscenario() - 1);

        Nave muroDestructible = new Nave();
        muroDestructible.agregarMuroSuave(escenario, muroDestructible);

        boolean a = true;
        escenario.setCaracterEnCoordenada(p1.getIcono(), x, y);
        escenario.mostrarEscenario();
    }
}
