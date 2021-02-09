package ajedrez.carlettos.src.carta.base;

import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.escaque.Escaque;
import ajedrez.carlettos.src.tablero.jugador.Jugador;
import ajedrez.carlettos.src.tablero.reloj.RelojManager;
import ajedrez.carlettos.src.util.Par;
import java.awt.Point;
import java.util.Objects;

/**
 * Es la representación de una carta, posee nombre, descripción, entre otras
 * cosas, sabe cómo utilizarse y cuándo.
 * <p>
 * Todos los métodos deberían sobreescribirse para crear una carta en
 * específico, además de que una misma carta debe tener siempre el mismo nombre
 * y descripción, mientras que, tanto su color como coste de maná, pueden
 * cambiar por diversos efectos que ocurran en el juego.
 *
 * @author Carlos
 *
 * @see Jugador
 */
public abstract class Carta {

    protected final String nombre;
    protected final String descripcion;
    protected int costeMana;

    /**
     *
     * @param nombre nombre de la carta.
     * @param descripcion descripción detallada de la carta.
     * @param costeMana coste de maná de la carta.
     */
    public Carta(String nombre, String descripcion, int costeMana) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costeMana = costeMana;
    }

    /**
     * Comprueba que la carta pueda utilizarse.
     *
     * @param punto punto de referencia.
     * @param tablero tablero en el que ocurre la carta.
     * @param reloj reloj en el que ocurre la carta.
     * @param jugadores jugadores objetivos, el primero siempre debe ser el que
     * utiliza la carta.
     *
     * @return true si puede utilizar la carta, false si no, además de un String
     * en forma de información extra.
     */
    public abstract Par<Boolean, String> canUsarCarta(Point punto, TableroManager tablero, RelojManager reloj, Jugador... jugadores);

    /**
     * Utiliza la carta haciendo las acciones necesarias.
     *
     * @param punto punto de referencia.
     * @param tablero tablero en el que ocurre la carta.
     * @param reloj reloj en el que ocurre la carta.
     * @param jugadores jugadores objetivos, el primero siempre debe ser el que
     * utiliza la carta.
     */
    public abstract void usarCarta(Point punto, TableroManager tablero, RelojManager reloj, Jugador... jugadores);

    /**
     *
     * @param mana cantidad de maná a sumar
     */
    public void cambiarCosteDeMana(int mana) {

    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCosteMana() {
        return costeMana;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + this.costeMana;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carta other = (Carta) obj;
        if (this.costeMana != other.costeMana) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
}
