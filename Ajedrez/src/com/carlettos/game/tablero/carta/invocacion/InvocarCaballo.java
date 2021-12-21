package com.carlettos.game.tablero.carta.invocacion;

import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.carta.Carta;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.clasica.Caballo;
import java.awt.Point;

/**
 *
 * @author Carlos
 */
public class InvocarCaballo extends Carta{

    public InvocarCaballo() {
        super("Invocar Caballo", "Invoca un caballo en la ubicación elegida al final del turno.", 2);
    }

    @Override
    public Par<Boolean, String> canUsarCarta(Point punto, Tablero tablero, Reloj reloj, Jugador... jugadores) {
        if(jugadores[0].getMana() < this.getCosteMana()){
            return new Par(false, "No hay suficiente maná");
        }
        if(tablero.getEscaque(punto).hasPieza()){
            return new Par(false, "Está ocupada la casilla");
        }
        return new Par(true, "Todo bien");
    }

    @Override
    public void usarCarta(Point punto, Tablero tablero, Reloj reloj, Jugador... jugadores) {
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
