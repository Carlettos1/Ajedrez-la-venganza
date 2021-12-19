package com.carlettos.game.tablero;

import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.pieza.Pieza;
import java.awt.Point;

/**
 * Escaque, o sea, cada casilla de las 64 de un tablero de ajedrez o damas,
 * generalmente poseen un color negro o blanco que se omite en esta clase ya que
 * sólo es algo gráfico.
 * <p>
 * También tienen propiedades como la de emitir magia.
 *
 * @author Carlos
 */
public class Escaque {

    protected boolean isFuenteDeMagia;
    protected boolean isConstruible;
    protected final Point localizacion;
    protected Pieza pieza;

    public Escaque(boolean isFuenteDeMagia, boolean isConstruible, Point localizacion, Pieza pieza) {
        this.isFuenteDeMagia = isFuenteDeMagia;
        this.isConstruible = isConstruible;
        this.localizacion = localizacion;
        this.pieza = pieza;
    }

    public boolean isIsFuenteDeMagia() {
        return isFuenteDeMagia;
    }

    public void setIsFuenteDeMagia(boolean isFuenteDeMagia) {
        this.isFuenteDeMagia = isFuenteDeMagia;
    }

    public boolean isIsConstruible() {
        return isConstruible;
    }

    public void setIsConstruible(boolean isConstruible) {
        this.isConstruible = isConstruible;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Point getLocalizacion() {
        return localizacion;
    }

    public void quitarPieza() {
        this.pieza = new Vacia();
    }

    public void quitarTodo() {
        this.pieza = new Vacia();
    }
}
