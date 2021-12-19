package com.carlettos.main;

import ajedrez.carlettos.src.pieza.piezas.clasica.Peon;
import ajedrez.carlettos.src.tablero.jugador.Color;
import ajedrez.carlettos.src.tablero.jugador.Jugador;
import ajedrez.carlettos.src.tablero.reloj.Evento;
import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.tablero.manager.Tablero;
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
        blancas.cambiarMana(5);
        negras.cambiarMana(5);
        
        TableroVisual tv = new TableroVisual(tablero, new RelojVisual(reloj));
        tv.mostrar();
    }
}
