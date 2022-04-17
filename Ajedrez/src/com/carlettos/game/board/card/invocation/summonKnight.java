package com.carlettos.game.board.card.invocation;

import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Par;
import com.carlettos.game.board.card.Card;
import com.carlettos.game.board.player.Jugador;
import com.carlettos.game.board.manager.Clock;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.piece.classic.Caballo;
import com.carlettos.game.core.Point;

/**
 *
 * @author Carlos
 */
public class SummonKnight extends Card{

    public SummonKnight() {
        super("Invocar Caballo", "Invoca un caballo en la ubicación elegida al final del turno.", 2);
    }

    @Override
    public Par<Boolean, String> canUsarCarta(Point punto, Board tablero, Clock reloj, Jugador... jugadores) {
        if(jugadores[0].getMana() < this.getCosteMana()){
            return new Par(false, "No hay suficiente maná");
        }
        if(tablero.getEscaque(punto).hasPieza()){
            return new Par(false, "Está ocupada la casilla");
        }
        return new Par(true, "Todo bien");
    }

    @Override
    public void usarCarta(Point punto, Board tablero, Clock reloj, Jugador... jugadores) {
        reloj.addEventos(
                Evento.Builder.start(tablero).with(1, this.nombre, punto).build((turnos, nombre1, punto1, tablero1) -> {
                    tablero1.getEscaque(punto1).setPieza(new Caballo(jugadores[0].getColor()));
                }));
        Jugador jugador = jugadores[0];
        jugador.getMano().quitarCarta(this);
        jugador.cambiarMana(-getCosteMana());
        reloj.movimiento();
    }
}
