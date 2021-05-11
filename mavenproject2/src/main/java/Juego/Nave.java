package Juego;

import java.lang.reflect.Array;
import java.util.Random;

public class Nave extends StageComponent {

    private int numeroMuroD = 14;

    public Nave() {
        setIcono('=');
    }

    public void agregarMuroSuave(Stage escenario, Nave muroDestructible) {
        int[][] x = { { 8, 5 },{ 12, 5 },{ 16, 5 },{ 20, 5 },{ 24, 5 },{ 28, 5 },{ 32, 5 },{ 36, 5 },{ 8, 2 },{ 12, 2 },{ 16, 2 },{ 20, 2 },{ 24, 2 },{ 28, 2 },{ 32, 2 },{ 36, 2 },};
        for (int i = 0; i < x.length; i++) {
            escenario.setCaracterEnCoordenada(muroDestructible, x[i][0], x[i][1]);
        }
    }
}
