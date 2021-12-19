package ajedrez.carlettos.src.carta.invocacion;

import ajedrez.carlettos.src.carta.base.Carta;
import ajedrez.carlettos.src.pieza.piezas.clasica.Caballo;
import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.jugador.Jugador;
import ajedrez.carlettos.src.tablero.reloj.Evento;
import ajedrez.carlettos.src.tablero.reloj.RelojManager;
import ajedrez.carlettos.src.util.Par;
import java.awt.Point;

public class InvocarCaballo extends Carta{

    public InvocarCaballo() {
        super("Invocar Caballo", "Invoca un caballo en la ubicación elegida al final del turno.", 2);
    }

    @Override
    public Par<Boolean, String> canUsarCarta(Point punto, TableroManager tablero, RelojManager reloj, Jugador... jugadores) {
        if(jugadores[0].getMana() < this.getCosteMana()){
            return new Par(false, "No hay suficiente maná");
        }
        if(tablero.getEscaque(punto).hasPieza()){
            return new Par(false, "Está ocupada la casilla");
        }
        return new Par(true, "Todo bien");
    }

    @Override
    public void usarCarta(Point punto, TableroManager tablero, RelojManager reloj, Jugador... jugadores) {
        reloj.addEventos(new Evento(1, "Invocar Caballo", punto, tablero) {
            @Override
            public void accion() {
                tablero.getEscaque(punto).setPieza(new Caballo(jugadores[0].getColor()));
            }
        });
        Jugador jugador = jugadores[0];
        jugador.getMano().quitarCarta(this);
        jugador.cambiarMana(-getCosteMana());
        reloj.movimiento();
    }
}