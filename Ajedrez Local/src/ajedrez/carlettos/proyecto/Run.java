package ajedrez.carlettos.proyecto;

import ajedrez.carlettos.proyecto.pieza.piezas.clasica.Peon;
import ajedrez.carlettos.proyecto.tablero.TableroManager;
import ajedrez.carlettos.proyecto.tablero.escaque.Escaque;
import ajedrez.carlettos.proyecto.tablero.jugador.Color;
import ajedrez.carlettos.proyecto.tablero.jugador.Jugador;
import ajedrez.carlettos.proyecto.tablero.reloj.Evento;
import ajedrez.carlettos.proyecto.tablero.reloj.RelojManager;
import ajedrez.carlettos.proyecto.util.ActionResult;
import ajedrez.carlettos.proyecto.util.Par;
import java.awt.Point;
import java.util.Scanner;

public class Run {

    public static void main(String... a) {
        Jugador negras = new Jugador(Color.NEGRAS);
        Jugador blancas = new Jugador(Color.BLANCAS);
        TableroManager tablero = new TableroManager(8, 8);
        RelojManager reloj = new RelojManager(negras, blancas);
        for (int i = 0; i < 8; i++) {
            tablero.getEscaque(i, 1).setPieza(new Peon(Color.BLANCAS));
            tablero.getEscaque(i, 6).setPieza(new Peon(Color.NEGRAS));
        }
        reloj.addEventos(new Evento(4, new Point(3, 3), tablero) {
            @Override
            public void accion() {
                tablero.getEscaque(punto).setPieza(new Peon(Color.BLANCAS));
            }
        });
        aVer(tablero, reloj);
    }

    public static void aVer(TableroManager tablero, RelojManager reloj) {
        Scanner input = new Scanner(System.in);
        String str;
        while (true) {
            System.out.println(reloj);
            System.out.println(tablero);
            str = input.nextLine().toLowerCase();
            if (str.contains("-")) {
                int x1 = Integer.parseInt(str.charAt(0) + "") - 1;
                int y1 = Integer.parseInt(str.charAt(1) + "") - 1;
                int x2 = Integer.parseInt(str.charAt(3) + "") - 1;
                int y2 = Integer.parseInt(str.charAt(4) + "") - 1;
                
                Point p1 = new Point(x1, y1);
                Point p2 = new Point(x2, y2);
                
                Par<ActionResult, String> mover = tablero.moverPieza(p1, p2);
                
                if(mover.x.equals(ActionResult.PASS)){
                    System.out.println(mover.y);
                    reloj.terminarTurno();
                    continue;
                }
                
                Par<ActionResult, String> comer = tablero.comerPieza(p1, p2);
                
                if(comer.x.equals(ActionResult.PASS)){
                    System.out.println(comer.y);
                    reloj.terminarTurno();
                    continue;
                }
                
                System.out.println("jugada ilegal");
            } else if (!str.contains("exit")) {
                int x = Integer.parseInt(str.charAt(0) + "") - 1;
                int y = Integer.parseInt(str.charAt(1) + "") - 1;
                
                System.out.println(tablero.getEscaque(x, y).getPieza().allAcciones(tablero, new Point(x, y)));
            } else if (str.equals("exit")) {
                System.out.println("F");
                break;
            }
        }
    }
}
