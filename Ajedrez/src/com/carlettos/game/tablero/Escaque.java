package com.carlettos.game.tablero;

import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
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

    /**
     * Si el escaque es fuente de magia o no.
     */
    protected boolean isFuenteDeMagia;

    /**
     * Si el escaque admite que se pueda construir encima o no.
     */
    protected boolean isConstruible;

    /**
     * La ubicación, en coordendas cartesianas enteras, del escaque. Para todos
     * los escaques de un tablero, usan el mismo punto de referencia.
     */
    protected final Point localizacion;

    /**
     * La pieza que contiene el escaque.
     */
    protected Pieza pieza;

    /**
     * Constructor general.
     *
     * @param isFuenteDeMagia true si emite magia, false si no.
     * @param isConstruible true si permite construcciones encima, false si no.
     * @param localizacion localización del escaque en coordenadas enteras
     * cartesianas respecto a un punto cualquiera pero común para todos los
     * escaques.
     * @param pieza pieza que tiene el escaque, si no tiene ninguna, usar una
     * pieza Vacia.
     */
    public Escaque(boolean isFuenteDeMagia, boolean isConstruible, Point localizacion, Pieza pieza) {
        this.isFuenteDeMagia = isFuenteDeMagia;
        this.isConstruible = isConstruible;
        this.localizacion = localizacion;
        this.pieza = pieza;
    }

    /**
     * Constructor de uso rápido.
     * 
     * Inicializa al escaque sin fuente de magia, con posibilidad de construir
     * en él, sin ninguna pieza.
     *
     * @param localizacion la localización del escaque en coordendas enteras
     * cartesianas.
     */
    public Escaque(Point localizacion) {
        this(false, true, localizacion, new Vacia());
    }
    
    public boolean isEmpty(){
        return !hasPieza();
    }

    /**
     * @return Color de la pieza en la casilla.
     */
    public Color getColorControlador() {
        if (hasPieza()) {
            return getPieza().getColor();
        }
        return Color.GRIS;
    }

    public boolean isControladoPor(Color color) {
        if (this.hasPieza()) {
            return getPieza().getColor().equals(color);
        }
        return false;
    }

    /**
     * Comprueba si tiene pieza, más específicamente, verifica si es una pieza
     * Vacia.
     *
     * @return true si tiene pieza, false si no.
     *
     * @see Vacia
     */
    public boolean hasPieza() {
        return !pieza.equals(new Vacia());
    }

    public Pieza getPieza() {
        return pieza;
    }

    /**
     * Cambia la pieza actual por la nueva proporcionada.
     *
     * @param pieza nueva pieza a colocar en este escaque.
     *
     * @see Pieza
     */
    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    /**
     * Reemplaza la actual pieza por una vacia, es equivalente a
     * {@code escaque.setPieza(new Vacia());}
     *
     * @see Vacia
     */
    public void quitarPieza() {
        this.pieza = new Vacia();
    }
    
    public Point getLocalizacion() {
        return localizacion;
    }

    public boolean isFuenteDeMagia() {
        return isFuenteDeMagia;
    }

    public boolean isConstruible() {
        return isConstruible;
    }

    public void setIsFuenteDeMagia(boolean isFuenteDeMagia) {
        this.isFuenteDeMagia = isFuenteDeMagia;
    }

    public void setIsConstruible(boolean isConstruible) {
        this.isConstruible = isConstruible;
    }
}
