package com.carlettos.game.tablero.manager;

import com.carlettos.game.tablero.manager.event.RelojListener;
import com.carlettos.game.core.Evento;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.event.RelojEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Carlos
 */
public class Reloj {

    private int turno;
    private int movimientos;
    private final List<Jugador> jugadores;
    private final List<Evento> eventos;
    private final List<RelojListener> listeners;

    public Reloj(Jugador... jugadores) {
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
    public boolean canJugar(Jugador jugador) {
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
    public void addEventos(Evento... eventos) {
        this.eventos.addAll(Arrays.asList(eventos));
    }

    /**
     * Méthodo de ayuda para saber de qué jugador es el turno, suponiendo que no
     * existen saltos de turno.
     *
     * @return Jugador cuyo turno es el que debe jugarse ahora.
     */
    public Jugador turnoDe() {
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
    
    public void addListener(RelojListener l){
        this.listeners.add(l);
    }
    
    public void ejecutarListeners(){
        this.listeners.forEach(l -> l.turnoTerminado(new RelojEvent(this)));
    }

    public List<Evento> getEventos() {
        return Collections.unmodifiableList(eventos);
    }
    
    public List<Evento> getEventosOrdenados(){
        eventos.sort((Evento o1, Evento o2) -> o1.compareTo(o2));
        return getEventos();
    }

    public List<Jugador> getJugadores() {
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
