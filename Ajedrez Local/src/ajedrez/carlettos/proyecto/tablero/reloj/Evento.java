package ajedrez.carlettos.proyecto.tablero.reloj;

import ajedrez.carlettos.proyecto.tablero.TableroManager;
import java.awt.Point;

/**
 * Un evento es algo que va a pasar en n cantidad de turnos, con n mayor o igual
 * a 1.
 *
 * @author Carlos
 */
public abstract class Evento {

    public int turnos;
    protected Point punto;
    protected final TableroManager tablero;

    /**
     * Contructor del evento, es algo que ocurre luego de n turnos terminados, o
     * sea, considera el turno que se tira el evento.
     *
     * @param turnos turnos que deben pasar para que el evento ocurra.
     * @param punto punto de referencia del evento.
     * @param tablero tablero en el cual ocurre el evento.
     */
    public Evento(int turnos, Point punto, TableroManager tablero) {
        if (turnos <= 0) {
            throw new IllegalArgumentException("La cantidad de turnos de un evento no puede ser 0 o negativa");
        }
        this.turnos = turnos;
        this.tablero = tablero;
        this.punto = punto;
    }

    /**
     * Es la acción que se ejecutará una vez el contador de turnos llegue a 0.
     */
    public abstract void accion();

    @Override
    public String toString() {
        return "Evento{" + "turnos=" + turnos + ", punto=" + punto + '}';
    }
}
