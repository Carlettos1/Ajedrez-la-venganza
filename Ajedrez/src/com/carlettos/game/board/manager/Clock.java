package com.carlettos.game.board.manager;

import com.carlettos.game.core.Event;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.event.ClockEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import com.carlettos.game.board.manager.event.ClockListener;

/**
 *
 * @author Carlos
 */
public class Clock {

    private int turno;
    private int movimientos;
    private final List<Player> jugadores;
    private final List<Event> eventos;
    private final List<ClockListener> listeners;

    public Clock(Player... jugadores) {
        this.movimientos = 0;
        this.turno = 1;
        this.eventos = new ArrayList<>();
        this.jugadores = Arrays.asList(jugadores);
        this.listeners = new ArrayList<>();
    }

    /**
     * Verifica si puede jugar el jugador que es pasado como parámetro, es un
     * méthodo de utilidad. Se entiende por jugar: mover o usar una carta o
     * cualquier cosa que consuma un movimiento por turno.
     *
     * @param jugador jugador que se quiere saber si juega o no.
     *
     * @return true si el jugador puede jugar o no.
     */
    public boolean canJugar(Player jugador) {
        return turnoDe().equals(jugador) && jugador.getMovimientosPorTurnos() > movimientos;
    }

    /**
     * Se debe ejecutar siempre luego de que ocurra un movimiento por parte de
     * cualquier jugador, sólamente para tener un control sobre los movimientos
     * que tiene un jugador.
     */
    public void movimiento() {
        movimientos += 1; //TODO: que sea útil el movimiento()
    }

    /**
     * Añade una lista de eventos al reloj para que los controle.
     *
     * @param eventos Eventos que se quieran añadir al reloj.
     */
    public void addEventos(Event... eventos) {
        this.eventos.addAll(Arrays.asList(eventos));
    }

    /**
     * Méthodo de ayuda para saber de qué jugador es el turno, suponiendo que no
     * existen saltos de turno.
     *
     * @return Jugador cuyo turno es el que debe jugarse ahora.
     */
    public Player turnoDe() {
        return jugadores.get((turno - 1) % jugadores.size());
    }

    /**
     * Termina el turno, añade uno al contador de turnos y comprueba si algún
     * evento es disparado o no.
     */
    public void terminarTurno() {
        turno++;
        movimientos = 0;
        eventos.forEach((evento) -> {
            evento.turnos -= 1;
        });
        eventos.stream().filter((evento) -> {
            return evento.turnos <= 0;
        }).forEach((evento) -> {
            evento.accion();
        });
        eventos.removeIf(evento -> evento.turnos <= 0);
        System.out.println("Juega el jugador: " + turnoDe());
    }
    
    public void addListener(ClockListener l){
        this.listeners.add(l);
    }
    
    public void ejecutarListeners(){
        this.listeners.forEach(l -> l.turnoTerminado(new ClockEvent(this)));
    }

    public List<Event> getEventos() {
        return Collections.unmodifiableList(eventos);
    }
    
    public List<Event> getEventosOrdenados(){
        eventos.sort((Event o1, Event o2) -> o1.compareTo(o2));
        return getEventos();
    }

    public List<Player> getJugadores() {
        return Collections.unmodifiableList(jugadores);
    }

    public int getTurno() {
        return turno;
    }

    public int getMovimientos() {
        return movimientos;
    }

    @Override
    public String toString() {
        return eventos.toString();
    }
}
