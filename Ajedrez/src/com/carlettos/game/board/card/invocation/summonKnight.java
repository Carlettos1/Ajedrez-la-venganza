package com.carlettos.game.board.card.invocation;

import com.carlettos.game.core.Event;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.board.card.Card;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.Clock;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.piece.classic.Knight;
import com.carlettos.game.core.Point;

/**
 * It represents a card that summons a knight.
 * @author Carlos
 */
public class SummonKnight extends Card{

    public SummonKnight() {
        super("Invocar Caballo", "Invoca un caballo en la ubicación elegida al final del turno.", 2);
    }

    @Override
    public Tuple<Boolean, String> canUsarCarta(Point punto, Board tablero, Clock reloj, Player... jugadores) {
        if(jugadores[0].getMana() < this.getCosteMana()){
            return new Tuple(false, "No hay suficiente maná");
        }
        if(tablero.getEscaque(punto).hasPieza()){
            return new Tuple(false, "Está ocupada la casilla");
        }
        return new Tuple(true, "Todo bien");
    }

    @Override
    public void usarCarta(Point punto, Board tablero, Clock reloj, Player... jugadores) {
        reloj.addEventos(
                Event.Builder.start(tablero).with(1, this.nombre, punto).build((turnos, nombre1, punto1, tablero1) -> {
                    tablero1.getEscaque(punto1).setPieza(new Knight(jugadores[0].getColor()));
                }));
        Player jugador = jugadores[0];
        jugador.getMano().quitarCarta(this);
        jugador.cambiarMana(-getCosteMana());
        reloj.movimiento();
    }
}
