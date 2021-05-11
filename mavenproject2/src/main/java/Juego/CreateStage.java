/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class CreateStage {
    final int LARGO_ESCENARIO = 60;
    final int ANCHO_ESCENARIO = 15;
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

        // Agrega muro dentro del escenario
        //while (recorrerY < ANCHO_ESCENARIO) {
          //  muro.agregarMuroDentroEscenario(escenario, muro, recorrerY);
            //recorrerY += 2;
        //}
        Nave muroDestructible = new Nave();
        muroDestructible.agregarMuroSuave(escenario, muroDestructible);
        /*
         * while (ANCHO_ESCENARIO %(recorrerX+4) ==0 && recorrerX <60){
         * muro.agregarMuroColumna(escenario, muro, 0);
         * muro.agregarMuroColumna(escenario, muro, escenario.getAnchoEscenario()-1);
         * 
         * recorrerX +=4; }
         */
        boolean a = true;
        // ControladorJuego prueba = new ControladorJuego(p1);
        // prueba.setVisible(true);
        escenario.setCaracterEnCoordenada(p1.getIcono(), x, y);
        escenario.mostrarEscenario();
        // int const x;
        // ,y;
        // p1 = prueba.getCharacter();
        // JOptionPane.showMessageDialog(null, "Termino el metodo run");

    }

}
