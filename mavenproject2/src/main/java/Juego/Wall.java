package Juego;

public class Wall extends StageComponent {

    public Wall() {
        setIcono(':');
    }

    public void agregarMuroFila(Stage escenario, Wall muro, int numeroFila) {
        for (int i = 0; i < escenario.getAnchoEscenario(); i++) {
            escenario.setCaracterEnCoordenada(muro, i, numeroFila);
        }
    }

    public void agregarMuroDentroEscenario(Stage escenario, Wall muro, int numeroFila) {
        for (int i = 4; i < escenario.getAnchoEscenario(); i += 4) {
            escenario.setCaracterEnCoordenada(muro, i, numeroFila);
        }
    }

    public void agregarMuroColumna(Stage escenario, Wall muro, int numeroColumna) {
        for (int i = 0; i < escenario.getLargoEscenario(); i++) {
            escenario.setCaracterEnCoordenada(muro, numeroColumna, i);
        }
    }

}
