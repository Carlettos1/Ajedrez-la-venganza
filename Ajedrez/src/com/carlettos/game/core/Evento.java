package com.carlettos.game.core;

import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.tablero.manager.Tablero;
import java.awt.Point;

/**
 * Un evento es algo que va a pasar en n cantidad de turnos, con n >= 1
 *
 * @author Carlos
 * 
 * @see Reloj
 */
public abstract class Evento implements Comparable<Evento> {

    public int turnos;
    public final String nombre;
    protected Point punto;
    protected final Tablero tablero;

    /**
     * Contructor del evento, es algo que ocurre luego de n turnos terminados, o
     * sea, considera el turno en el que se tira el evento.
     *
     * @param turnos turnos que deben pasar para que el evento ocurra.
     * @param nombre nombre del evento.
     * @param punto punto de referencia del evento.
     * @param tablero tablero en el cual ocurre el evento.
     */
    private Evento(int turnos, String nombre, Point punto, Tablero tablero) {
        if (turnos <= 0) {
            throw new IllegalArgumentException("La cantidad de turnos de un evento no puede ser 0 o negativa");
        }
        this.turnos = turnos;
        this.nombre = nombre;
        this.tablero = tablero;
        this.punto = punto;
    }

    /**
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
    
    public static final class Builder {
        private final Tablero tablero;
        private int turnos;
        private String nombre;
        private Point punto;
        
        public static Builder start(Tablero tablero){
            return new Builder(tablero);
        }

        private Builder(Tablero tablero) {
            this.tablero = tablero;
            this.turnos = 10;
            this.nombre = "Evento";
            this.punto = new Point(0, 0);
        }
        
        public Builder with(int turnos, String nombre, Point punto){
            this.turnos = turnos;
            this.nombre = nombre;
            this.punto = punto;
            return this;
        }
        
        public Builder with(int turnos, String nombre){
            this.turnos = turnos;
            this.nombre = nombre;
            return this;
        }
        
        public Builder with(String nombre, Point punto){
            this.nombre = nombre;
            this.punto = punto;
            return this;
        }
        
        public Builder with(int turnos, Point punto){
            this.turnos = turnos;
            this.punto = punto;
            return this;
        }
        
        public Builder with(int turnos){
            this.turnos = turnos;
            return this;
        }
        
        public Builder with(String nombre){
            this.nombre = nombre;
            return this;
        }
        
        public Builder with(Point punto){
            this.punto = punto;
            return this;
        }
        
        public Evento build(Operator operator){
            return new Evento(this.turnos, this.nombre, this.punto, this.tablero) {
                @Override
                public void accion() {
                    operator.operar(turnos, nombre, punto, tablero);
                }
            };
        }
    }
    
    @FunctionalInterface
    public static interface Operator{
        public void operar(int turnos, String nombre, Point punto, Tablero tablero);
    }
}
