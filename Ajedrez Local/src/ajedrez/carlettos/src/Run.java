package ajedrez.carlettos.src;

import ajedrez.carlettos.src.carta.invocacion.InvocarCaballo;
import ajedrez.carlettos.src.pieza.piezas.clasica.Alfil;
import ajedrez.carlettos.src.pieza.piezas.clasica.Caballo;
import ajedrez.carlettos.src.pieza.piezas.clasica.Peon;
import ajedrez.carlettos.src.pieza.piezas.clasica.Torre;
import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.jugador.Color;
import ajedrez.carlettos.src.tablero.jugador.Jugador;
import ajedrez.carlettos.src.tablero.reloj.Evento;
import ajedrez.carlettos.src.tablero.reloj.RelojManager;
import ajedrez.carlettos.src.visual.RelojVisual;
import ajedrez.carlettos.src.visual.TableroVisual;
import java.awt.Point;

public class Run {

    public static void main(String... a) {
        Jugador negras = new Jugador(Color.NEGRAS);
        Jugador blancas = new Jugador(Color.BLANCAS);
        TableroManager tablero = new TableroManager(16, 17);
        RelojManager reloj = new RelojManager(blancas, negras);
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
