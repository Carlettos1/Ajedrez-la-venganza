package ajedrez.carlettos.proyecto;

import ajedrez.carlettos.proyecto.tablero.TableroManager;

public class Run {

    public static void main(String... a) {
        TableroManager tablero = new TableroManager(8, 8);
        System.out.println(tablero);
    }
}
