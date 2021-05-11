package Juego;

import javax.swing.JOptionPane;

public class Enemy {
    int posIniX = 0;
    int posIniY = 0;
    int x = 0;
    int y = 0;
    int ancho;
    int largo;
    boolean going = true;
    long born_time;
    boolean isDead = false;
    private char icono = 'e';

    public Enemy(int x, int y, int ancho, int largo, long born_time) {
        this.ancho = ancho;
        this.largo = largo;
        this.x = this.posIniX = x;
        this.y = this.posIniY = y;
        this.born_time = born_time;
    }

    public void kill_enemigo() {
        this.isDead = true;
    }

    public void setIcono(char icono) {
        this.icono = icono;
    }

    public char getIcono() {
        return this.icono;
    }

    public int getx() {
        return this.x;
    }

    public int gety() {
        return this.y;
    }

    public void setx() {
        if (this.going) {
            this.x += 1;
        } else {
            this.x -= 1;
        }
    }

    public void sety() {
        if (this.going) {
            this.y += 1;
        } else {
            this.y -= 1;
        }
    }

    public void setBornTime(long born_time) {
        this.born_time = born_time;
    }

    public long getBornTime() {
        return this.born_time;
    }

    public void haChocado() {
        this.going = !this.going;
    }
}
