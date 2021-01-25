package ajedrez.carlettos.proyecto.tablero.jugador;

import ajedrez.carlettos.proyecto.tablero.mano.ManoManager;

/**
 * Es la clase Jugador, en ella se manejan todo lo que un jugador debería 
 * manejar, mana, control de movimientos y su mano.
 * 
 * @author Carlos
 */
public class Jugador {

    private final boolean isBlanco;
    private int movimientosPorTurnos;
    private int mana;
    private final ManoManager mano;

    /**
     * Constructor general.
     *
     * @param isBlanco true si es blanco, false si es negro.
     * @param movimientosPorTurnos cantidad de movimientos que tiene el jugador
     * por cada turno, este número es 1 en el ajedrez normal.
     * @param mana cantidad de maná que tiene el jugador, este número es 0 en el
     * ajedrez normal.
     * @param mano ManoManager del jugador, contiene todas las cartas del
     * jugador, este objeto no tiene cartas en el ajedrez normal.
     *
     * @see ManoManager
     */
    public Jugador(boolean isBlanco, int movimientosPorTurnos, int mana, ManoManager mano) {
        this.isBlanco = isBlanco;
        this.movimientosPorTurnos = movimientosPorTurnos;
        this.mana = mana;
        this.mano = mano;
    }

    /**
     * Constructor básico, inicializa con 1 movimiento por turno y 0 de maná.
     * Para mayor personalización usar el constructor general o usar los métodos
     * correspondientes.
     *
     * @param isBlanco true si es el jugador blanco, false si es el negro.
     * @param mano ManoManager del jugador.
     *
     * @see ManoManager
     */
    public Jugador(boolean isBlanco, ManoManager mano) {
        this(isBlanco, 1, 0, mano);
    }

    /**
     * Este constructor da como resultado un jugador del ajedrez normal.
     *
     * <p>
     * Constructor de ayuda, se puede usar pero no es lo recomendable,
     * inicializa con un ManoManager completamente nuevo, 0 de mana y 1
     * movimiento por turno, para personalizar mejor al jugador, usar el
     * constructor general o usar los métodos correspondientes.
     *
     * @param isBlanco true si es el jugador blanco, false si es el negro.
     *
     * @see ManoManager
     */
    public Jugador(boolean isBlanco) {
        this(isBlanco, 1, 0, new ManoManager());
    }

    public boolean isBlanco() {
        return isBlanco;
    }

    public int getMovimientosPorTurnos() {
        return movimientosPorTurnos;
    }

    public int getMana() {
        return mana;
    }

    public ManoManager getMano() {
        return mano;
    }

    /**
     * Cambia la cantidad de mana del jugador sumándole el valor entregado.
     *
     * @param mana Mana a sumar, puede ser negativo;
     */
    public void cambiarMana(int mana) {
        if (this.mana + mana < 0) {
            this.mana = 0;
        } else {
            this.mana += mana;
        }
    }

    /**
     * Cambia la cantidad de movimientos por turno del jugador, sumándole el
     * valor entregado.
     *
     * @param movimientos Movimientos a sumar, puede ser negativo;
     */
    public void cambiarMovimientos(int movimientos) {
        if (this.movimientosPorTurnos + movimientos < 1) {
            this.movimientosPorTurnos = 1;
        } else {
            movimientosPorTurnos += movimientos;
        }
    }

    /**
     * Función de utilidad gráfica, para saber el color usar jugador.isBlanco();
     *
     * @return String "blanco" si es blanco, "negro" si es negro.
     */
    public String getColor() {
        return isBlanco() ? "Blanco" : "Negro";
    }
}
