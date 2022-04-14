package com.carlettos.main;

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
import com.carlettos.game.visual.TableroVisual;
import com.carlettos.game.tablero.pieza.clasica.Reina;
import com.carlettos.game.tablero.pieza.nueva.Ariete;
import com.carlettos.game.tablero.pieza.nueva.Arquero;
import com.carlettos.game.tablero.pieza.nueva.Ballesta;
import com.carlettos.game.tablero.pieza.nueva.Brujo;
import com.carlettos.game.tablero.pieza.nueva.Catapulta;
import com.carlettos.game.tablero.pieza.nueva.Cañon;
import com.carlettos.game.tablero.pieza.nueva.Constructor;
import com.carlettos.game.tablero.pieza.nueva.Defensor;
import com.carlettos.game.tablero.pieza.nueva.Hechicero;
import com.carlettos.game.tablero.pieza.nueva.Nave;
import com.carlettos.game.tablero.pieza.nueva.Paladin;
import com.carlettos.game.tablero.pieza.nueva.PeonLoco;
import com.carlettos.game.tablero.pieza.nueva.SuperPeon;
import com.carlettos.game.tablero.pieza.nueva.TorreTesla;

/**
 *
 * @author Carlos
 */
public class Main {

    public static void main(String... a) {
        Jugador negras = new Jugador(Color.NEGRO);
        Jugador blancas = new Jugador(Color.BLANCO);
        Reloj reloj = new Reloj(blancas, negras);
        Tablero tablero = new Tablero(16, 17, reloj);
        
        /*reloj.addEventos(Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 3))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))),
                Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 2))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))),
                Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 4))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))));
        */
        negras.getMano().addCarta(new InvocarCaballo());
        blancas.getMano().addCarta(new InvocarCaballo());
        blancas.cambiarMana(5);
        negras.cambiarMana(5);
        
        tablero.getEscaque(0, 0).setPieza(new Cañon(Color.BLANCO));
        tablero.getEscaque(15, 0).setPieza(new Cañon(Color.BLANCO));        
        tablero.getEscaque(0, 16).setPieza(new Cañon(Color.NEGRO));
        tablero.getEscaque(15, 16).setPieza(new Cañon(Color.NEGRO));
        
        tablero.getEscaque(1, 0).setPieza(new Torre(Color.BLANCO));
        tablero.getEscaque(14, 0).setPieza(new Torre(Color.BLANCO));        
        tablero.getEscaque(1, 16).setPieza(new Torre(Color.NEGRO));
        tablero.getEscaque(14, 16).setPieza(new Torre(Color.NEGRO));
        
        tablero.getEscaque(2, 0).setPieza(new Catapulta(Color.BLANCO));
        tablero.getEscaque(13, 0).setPieza(new Catapulta(Color.BLANCO));        
        tablero.getEscaque(2, 16).setPieza(new Catapulta(Color.NEGRO));
        tablero.getEscaque(13, 16).setPieza(new Catapulta(Color.NEGRO));
        
        tablero.getEscaque(3, 0).setPieza(new Caballo(Color.BLANCO));
        tablero.getEscaque(12, 0).setPieza(new Caballo(Color.BLANCO));        
        tablero.getEscaque(3, 16).setPieza(new Caballo(Color.NEGRO));
        tablero.getEscaque(12, 16).setPieza(new Caballo(Color.NEGRO));
        
        tablero.getEscaque(4, 0).setPieza(new Brujo(Color.BLANCO));
        tablero.getEscaque(11, 0).setPieza(new Brujo(Color.BLANCO));        
        tablero.getEscaque(4, 16).setPieza(new Brujo(Color.NEGRO));
        tablero.getEscaque(11, 16).setPieza(new Brujo(Color.NEGRO));
        
        tablero.getEscaque(5, 0).setPieza(new Alfil(Color.BLANCO));
        tablero.getEscaque(10, 0).setPieza(new Alfil(Color.BLANCO));        
        tablero.getEscaque(5, 16).setPieza(new Alfil(Color.NEGRO));
        tablero.getEscaque(10, 16).setPieza(new Alfil(Color.NEGRO));
        
        tablero.getEscaque(6, 0).setPieza(new Hechicero(Color.BLANCO));
        tablero.getEscaque(7, 0).setPieza(new Reina(Color.BLANCO));        
        tablero.getEscaque(8, 0).setPieza(new Rey(Color.BLANCO));
        tablero.getEscaque(9, 0).setPieza(new Paladin(Color.BLANCO));
        
        tablero.getEscaque(6, 16).setPieza(new Hechicero(Color.NEGRO));
        tablero.getEscaque(7, 16).setPieza(new Reina(Color.NEGRO));        
        tablero.getEscaque(8, 16).setPieza(new Rey(Color.NEGRO));
        tablero.getEscaque(9, 16).setPieza(new Paladin(Color.NEGRO));
        
        tablero.getEscaque(0, 1).setPieza(new Nave(Color.BLANCO));
        tablero.getEscaque(15, 1).setPieza(new Nave(Color.BLANCO));        
        tablero.getEscaque(0, 15).setPieza(new Nave(Color.NEGRO));
        tablero.getEscaque(15, 15).setPieza(new Nave(Color.NEGRO));
        
        tablero.getEscaque(1, 1).setPieza(new TorreTesla(Color.BLANCO));
        tablero.getEscaque(14, 1).setPieza(new TorreTesla(Color.BLANCO));        
        tablero.getEscaque(1, 15).setPieza(new TorreTesla(Color.NEGRO));
        tablero.getEscaque(14, 15).setPieza(new TorreTesla(Color.NEGRO));
        
        tablero.getEscaque(2, 1).setPieza(new Ariete(Color.BLANCO));
        tablero.getEscaque(13, 1).setPieza(new Ariete(Color.BLANCO));        
        tablero.getEscaque(2, 15).setPieza(new Ariete(Color.NEGRO));
        tablero.getEscaque(13, 15).setPieza(new Ariete(Color.NEGRO));
        
        tablero.getEscaque(3, 1).setPieza(new Constructor(Color.BLANCO));
        tablero.getEscaque(12, 1).setPieza(new Constructor(Color.BLANCO));        
        tablero.getEscaque(3, 15).setPieza(new Constructor(Color.NEGRO));
        tablero.getEscaque(12, 15).setPieza(new Constructor(Color.NEGRO));
        
        tablero.getEscaque(4, 1).setPieza(new Peon(Color.BLANCO));
        tablero.getEscaque(11, 1).setPieza(new Peon(Color.BLANCO));        
        tablero.getEscaque(4, 15).setPieza(new Peon(Color.NEGRO));
        tablero.getEscaque(11, 15).setPieza(new Peon(Color.NEGRO));
        
        tablero.getEscaque(5, 1).setPieza(new Peon(Color.BLANCO));
        tablero.getEscaque(10, 1).setPieza(new Peon(Color.BLANCO));        
        tablero.getEscaque(5, 15).setPieza(new Peon(Color.NEGRO));
        tablero.getEscaque(10, 15).setPieza(new Peon(Color.NEGRO));
                
        tablero.getEscaque(6, 1).setPieza(new PeonLoco(Color.BLANCO));
        tablero.getEscaque(9, 1).setPieza(new PeonLoco(Color.BLANCO));        
        tablero.getEscaque(6, 15).setPieza(new PeonLoco(Color.NEGRO));
        tablero.getEscaque(9, 15).setPieza(new PeonLoco(Color.NEGRO));
        
        tablero.getEscaque(7, 1).setPieza(new SuperPeon(Color.BLANCO));
        tablero.getEscaque(8, 1).setPieza(new SuperPeon(Color.BLANCO));        
        tablero.getEscaque(7, 15).setPieza(new SuperPeon(Color.NEGRO));
        tablero.getEscaque(8, 15).setPieza(new SuperPeon(Color.NEGRO));
        
        tablero.getEscaque(0, 2).setPieza(new Ballesta(Color.BLANCO));
        tablero.getEscaque(15, 2).setPieza(new Ballesta(Color.BLANCO));        
        tablero.getEscaque(0, 14).setPieza(new Ballesta(Color.NEGRO));
        tablero.getEscaque(15, 14).setPieza(new Ballesta(Color.NEGRO));
        
        tablero.getEscaque(1, 2).setPieza(new Arquero(Color.BLANCO));
        tablero.getEscaque(14, 2).setPieza(new Arquero(Color.BLANCO));        
        tablero.getEscaque(1, 14).setPieza(new Arquero(Color.NEGRO));
        tablero.getEscaque(14, 14).setPieza(new Arquero(Color.NEGRO));
        
        tablero.getEscaque(2, 2).setPieza(new Arquero(Color.BLANCO));
        tablero.getEscaque(13, 2).setPieza(new Arquero(Color.BLANCO));        
        tablero.getEscaque(2, 14).setPieza(new Arquero(Color.NEGRO));
        tablero.getEscaque(13, 14).setPieza(new Arquero(Color.NEGRO));
        
        tablero.getEscaque(3, 2).setPieza(new Defensor(Color.BLANCO));
        tablero.getEscaque(12, 2).setPieza(new Defensor(Color.BLANCO));        
        tablero.getEscaque(3, 14).setPieza(new Defensor(Color.NEGRO));
        tablero.getEscaque(12, 14).setPieza(new Defensor(Color.NEGRO));
        
        tablero.getEscaque(0, 3).setPieza(new Peon(Color.BLANCO));
        tablero.getEscaque(15, 3).setPieza(new Peon(Color.BLANCO));        
        tablero.getEscaque(0, 13).setPieza(new Peon(Color.NEGRO));
        tablero.getEscaque(15, 13).setPieza(new Peon(Color.NEGRO));
        
        tablero.getEscaque(1, 3).setPieza(new PeonLoco(Color.BLANCO));
        tablero.getEscaque(14, 3).setPieza(new PeonLoco(Color.BLANCO));        
        tablero.getEscaque(1, 13).setPieza(new PeonLoco(Color.NEGRO));
        tablero.getEscaque(14, 13).setPieza(new PeonLoco(Color.NEGRO));
        
        tablero.getEscaque(2, 3).setPieza(new Peon(Color.BLANCO));
        tablero.getEscaque(13, 3).setPieza(new Peon(Color.BLANCO));        
        tablero.getEscaque(2, 13).setPieza(new Peon(Color.NEGRO));
        tablero.getEscaque(13, 13).setPieza(new Peon(Color.NEGRO));
        
        TableroVisual tv = new TableroVisual(tablero);
        tv.mostrar();
    }
}
