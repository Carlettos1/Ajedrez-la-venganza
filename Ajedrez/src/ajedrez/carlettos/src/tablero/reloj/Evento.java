package ajedrez.carlettos.src.tablero.reloj;

import ajedrez.carlettos.src.tablero.TableroManager;
import java.awt.Point;

/**
 * Un evento es algo que va a pasar en n cantidad de turnos, con n mayor o igual
 * a 1.
 *
 * @author Carlos
 */
public abstract class Evento implements Comparable<Evento> {

    public int turnos;
    public final String nombre;
    protected Point punto;
    protected final TableroManager tablero;

    /**
     * Contructor del evento, es algo que ocurre luego de n turnos terminados, o
     * sea, considera el turno que se tira el evento.
     *
     * @param turnos turnos que deben pasar para que el evento ocurra.
     * @param nombre nombre del evento.
     * @param punto punto de referencia del evento.
     * @param tablero tablero en el cual ocurre el evento.
     */
    public Evento(int turnos, String nombre, Point punto, TableroManager tablero) {
        if (turnos <= 0) {
            throw new IllegalArgumentException("La cantidad de turnos de un evento no puede ser 0 o negativa");
        }
        this.turnos = turnos;
        this.nombre = nombre;
        this.tablero = tablero;
        this.punto = punto;
    }

    /**TODO: FunctionalInterface
     * Es la acción que se ejecutará una vez el contador de turnos llegue a 0.
     */
    public abstract void accion();

    @Override
    public String toString() {
        return "Evento{" + "turnos=" + turnos + ", nombre=" + nombre + ", punto=" + punto + '}';
    }

    @Override
    public int compareTo(Evento o) {
        return Integer.compare(this.turnos, o.turnos);
    }
    
    public static class EventoIdentidad extends Evento {

        public EventoIdentidad() {
            super(1, null, null, null);
        }

        @Override
        public void accion() {
        }
    }
}
