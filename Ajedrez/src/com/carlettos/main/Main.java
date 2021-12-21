package com.carlettos.main;

import com.carlettos.game.core.Evento;
import com.carlettos.game.tablero.carta.invocacion.InvocarCaballo;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.clasica.Alfil;
import com.carlettos.game.tablero.pieza.clasica.Caballo;
import com.carlettos.game.tablero.pieza.clasica.Peon;
import com.carlettos.game.tablero.pieza.clasica.Rey;
import com.carlettos.game.tablero.pieza.clasica.Torre;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.visual.RelojVisual;
import com.carlettos.game.visual.TableroVisual;
import java.awt.Point;

/**
 *
 * @author Carlos
 */
public class Main {

    public static void main(String... a) {
        Jugador negras = new Jugador(Color.NEGRAS);
        Jugador blancas = new Jugador(Color.BLANCAS);
        Tablero tablero = new Tablero(16, 17);
        Reloj reloj = new Reloj(blancas, negras);
        for (int i = 0; i < 8; i++) {
            tablero.getEscaque(i, 1).setPieza(new Peon(Color.BLANCAS));
            tablero.getEscaque(i, 6).setPieza(new Peon(Color.NEGRAS));
        }
        reloj.addEventos(new Evento(4, "Colocar peón", new Point(3, 3), tablero) {
            @Override
            public void accion() {
                tablero.getEscaque(punto).setPieza(new Peon(Color.BLANCAS));
            }
        });
        reloj.addEventos(new Evento(4, "Colocar peón", new Point(3, 2), tablero) {
            @Override
            public void accion() {
                tablero.getEscaque(punto).setPieza(new Peon(Color.BLANCAS));
            }
        });
        reloj.addEventos(new Evento(4, "Colocar peón", new Point(3, 4), tablero) {
            @Override
            public void accion() {
                tablero.getEscaque(punto).setPieza(new Peon(Color.BLANCAS));
            }
        });
        tablero.getEscaque(5, 4).setPieza(new Caballo(Color.BLANCAS));
        tablero.getEscaque(3, 4).setPieza(new Alfil(Color.BLANCAS));
        
        tablero.getEscaque(9, 6).setPieza(new Torre(Color.BLANCAS));
        tablero.getEscaque(10, 6).setPieza(new Torre(Color.BLANCAS));
        tablero.getEscaque(11, 6).setPieza(new Torre(Color.BLANCAS));
        tablero.getEscaque(9, 10).setPieza(new Torre(Color.NEGRAS));
        tablero.getEscaque(10, 11).setPieza(new Torre(Color.NEGRAS));
        tablero.getEscaque(11, 10).setPieza(new Torre(Color.NEGRAS));
        
        tablero.getEscaque(0, 8).setPieza(new Rey(Color.NEGRAS));
        tablero.getEscaque(0, 11).setPieza(new Rey(Color.BLANCAS));
        
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        negras.getMano().addCarta(new InvocarCaballo());
        blancas.getMano().addCarta(new InvocarCaballo());
        blancas.getMano().addCarta(new InvocarCaballo());
        blancas.getMano().addCarta(new InvocarCaballo());
        blancas.getMano().addCarta(new InvocarCaballo());
        blancas.getMano().addCarta(new InvocarCaballo());
        blancas.getMano().addCarta(new InvocarCaballo());
        blancas.getMano().addCarta(new InvocarCaballo());
        blancas.cambiarMana(5);
        negras.cambiarMana(5);
        
        TableroVisual tv = new TableroVisual(tablero, new RelojVisual(reloj));
        tv.mostrar();
    }
}
