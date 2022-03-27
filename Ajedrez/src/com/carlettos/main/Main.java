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
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.pieza.clasica.Reina;
import com.carlettos.game.tablero.pieza.nueva.Cañon;

/**
 *
 * @author Carlos
 */
public class Main {

    public static void main(String... a) {
        Jugador negras = new Jugador(Color.NEGRO);
        Jugador blancas = new Jugador(Color.BLANCO);
        Tablero tablero = new Tablero(16, 17);
        Reloj reloj = new Reloj(blancas, negras);
        for (int i = 0; i < 8; i++) {
            tablero.getEscaque(i, 1).setPieza(new Peon(Color.BLANCO));
            tablero.getEscaque(i, 6).setPieza(new Peon(Color.NEGRO));
        }
        reloj.addEventos(Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 3))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))),
                Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 2))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))),
                Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 4))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))));
        
        tablero.getEscaque(5, 4).setPieza(new Caballo(Color.BLANCO));
        tablero.getEscaque(3, 4).setPieza(new Alfil(Color.BLANCO));
        
        tablero.getEscaque(9, 6).setPieza(new Torre(Color.BLANCO));
        tablero.getEscaque(10, 6).setPieza(new Torre(Color.BLANCO));
        tablero.getEscaque(11, 6).setPieza(new Torre(Color.BLANCO));
        tablero.getEscaque(9, 10).setPieza(new Torre(Color.NEGRO));
        tablero.getEscaque(10, 11).setPieza(new Torre(Color.NEGRO));
        tablero.getEscaque(11, 10).setPieza(new Torre(Color.NEGRO));
        
        tablero.getEscaque(0, 8).setPieza(new Rey(Color.NEGRO));
        tablero.getEscaque(0, 11).setPieza(new Rey(Color.BLANCO));
        
        tablero.getEscaque(0, 0).setPieza(new Reina(Color.NEGRO));
        tablero.getEscaque(15, 16).setPieza(new Reina(Color.BLANCO));
        
        tablero.getEscaque(14, 16).setPieza(new Cañon(Color.BLANCO));
        
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
