package Juego;

import javax.swing.JOptionPane;

public class player {

    private int dX = 0;
    private int dY = 0;
    private int x = 0;
    private int y = 0;
    private int ancho;
    private int largo;
    private boolean hasBullet = true;
    private boolean isDead = false;
    private int capacidad = 1;
    private char icono = '^';
    private boolean presionado;
    private Stage mapa;

    public player(int x, int y, int ancho, int largo, Stage mapa) {
        this.ancho = ancho;
        this.largo = largo;
        this.x = x;
        this.y = y;
        this.presionado = false;
        this.mapa = mapa;
    }

    public void manUp() {
        this.dX = 0;
        this.dY = -1;
        move();
    }

    public void manDown() {
        this.dX = 0;
        this.dY = 1;
        move();
    }

    public void manLeft() {
        this.dX = -1;
        this.dY = 0;
        move();
    }

    public void manRight() {
        this.dX = 1;
        this.dY = 0;
        move();
    }

    public void move() {
        int xf = x + dX;
        int yf = y + dY;
        if (xf >= 1 && yf >= 1 && xf < ancho && yf < largo) {
            char icon = mapa.getCaracterEnCoordenada(xf, yf);
            if (icon != '#' && icon != '=' && icon != '@') {
                if (mapa.getCaracterEnCoordenada(x, y) != '@') {
                    mapa.limpiarCoordenaDelEscenario(x, y);
                }
                this.x += dX;
                this.y += dY;
                mapa.setCaracterEnCoordenada(this.icono, x, y);
                mapa.mostrarEscenario();
            }
        }
        this.dX = 0;
        this.dY = 0;
        this.presionado = false;
    }

    public boolean isHasBullet() {
        return hasBullet;
    }

    public void setHasBullet(boolean hasBullet) {
        this.hasBullet = hasBullet;
    }

    public void dropBullet(int x, int y, Stage escenario) {

        (new Bullet(x, y, escenario, this)).start();

    }

    public void getBullet() {
        this.hasBullet = true;
    }

    public void killCharacter() {
        this.isDead = true;
    }

    public void setIcono(char icono) {
        this.icono = icono;
    }

    public char getIcono() {
        return icono;
    }

    public int getx() {
        return this.x;
    }

    public int gety() {
        return this.y;
    }

    public int getdX() {
        return this.dX;
    }

    public int getdY() {
        return this.dY;
    }

    public Stage getMapa() {
        return mapa;
    }

    public void setMapa(Stage mapa) {
        this.mapa = mapa;
    }

    public boolean isSelected() {
        return this.presionado;
    }

    public String getPosJugador() {
        return String.valueOf(this.x) + " " + String.valueOf(this.y);
    }

    public boolean getIsDead() {
        return this.isDead;
    }

    public void setIsDead() {
        this.isDead = true;
    }
}
