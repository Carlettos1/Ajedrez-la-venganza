package com.carlettos.game.tablero.manager;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.Escaque;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: reloj y manos?
 * Esta clase sólo controlará el trablero, cualquier otra funcionalidad que no
 * sea la de controlar a sus piezas, o proveer métodos de utilidad sobre si
 * mismo, no es de su responsabilidad, por lo tanto, no tendrá conocimiento de
 * jugadores, relojes o barajas.
 *
 * @author Carlos
 *
 * @see Escaque
 * @see Estructura
 * @see Pieza
 */
public class Tablero {

    //TODO: poder hacer tableros de cualquier tipo de formas.
    private final Escaque[][] tableroAjedrez;

    /**
     * Cantidad de columnas del tablero. Está relacionada con el número x en el
     * plano cartesiano.
     */
    public final int columnas;

    /**
     * Cantidad de filas del tablero. Está relacionada con el número y en el
     * plano cartesiano.
     */
    public final int filas;

    public Tablero(int columnas, int filas) {
        this.tableroAjedrez = new Escaque[filas][columnas];
        this.columnas = columnas;
        this.filas = filas;
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                tableroAjedrez[y][x] = new Escaque(false, true,
                        new Point(x, y), new Vacia());
            }
        }
    }

    /**
     * Come la pieza en la posición x2, y2 con la pieza en la posición x1, y1.
     *
     * @param x1 coordenada x del punto de la pieza que come.
     * @param y1 coordenada y del punto de la pieza que come.
     * @param x2 coordenada x del punto de la pieza que es comida.
     * @param y2 coordenada y del punto de la pieza que es comida.
     *
     * @return un Par, conteniendo el resultado de la acción y un String con
     * información extra.
     */
    public Par<ActionResult, String> comerPieza(int x1, int y1, int x2, int y2) {
        return comerPieza(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Come la pieza en la posición final_ con la pieza en la posición inicial.
     *
     * @param inicio posición de la pieza que come.
     * @param final_ posición de la pieza que es comida.
     *
     * @return un Par, conteniendo el resultado de la acción y un String con
     * información extra.
     */
    public Par<ActionResult, String> comerPieza(Point inicio, Point final_) {
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPieza().canComer(this, inicio, final_);
        if (can) {
            escaqueFinal.setPieza(escaqueInicio.getPieza());
            escaqueInicio.quitarPieza();
            return new Par(ActionResult.PASS, "Comió");
        } else {
            return new Par(ActionResult.FAIL, "No puede comer");
        }
    }

    /**
     * Mueve la pieza en la posición x1, y1 a la posición x2, y2.
     *
     * @param x1 coordenada x del punto de la pieza.
     * @param y1 coordenada y del punto de la pieza.
     * @param x2 coordenada x del punto al que la pieza se mueve.
     * @param y2 coordenada y del punto al que la pieza se mueve.
     *
     * @return un Par, conteniendo el resultado de la acción y un String con
     * información extra.
     */
    public Par<ActionResult, String> moverPieza(int x1, int y1, int x2, int y2) {
        return moverPieza(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Mueve la pieza en la posición inicial a la posición final_.
     *
     * @param inicio posición de la pieza.
     * @param final_ posición al que la pieza se mueve.
     *
     * @return un Par, conteniendo el resultado de la acción y un String con
     * información extra.
     */
    public Par<ActionResult, String> moverPieza(Point inicio, Point final_) {
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPieza().canMover(this, inicio, final_);
        if (can) {
            escaqueFinal.setPieza(escaqueInicio.getPieza());
            escaqueInicio.quitarPieza();
            return new Par<>(ActionResult.PASS, "Movió");
        } else {
            return new Par(ActionResult.FAIL, "No puede mover");
        }
    }

    /**
     * Usa la habilidad de la pieza en el punto indicado, hacia el punto que
     * debe especificarse.
     *
     * @param inicio punto en el que la habilidad es lanzada.
     * @param final_ punto hacia donde la habilidad se lanza.
     * @param informacionExtra String que contiene la información de la
     * habilidad, por ejemplo, hacia dónde se va a aplicar la habilidad de la
     * torre (NESW).
     *
     * @return un par que contiene un ActionResult y String a forma de ayuda
     * para dar más información.
     *
     * @see ActionResult
     */
    public Par<ActionResult, String> usarHabilidadPieza(Point inicio, Point final_, String informacionExtra) {
        Escaque escaque = getEscaque(inicio);
        Par<Boolean, String> can = escaque.getPieza().canUsarHabilidad(this, inicio, final_, informacionExtra);
        if (can.x) {
            escaque.getPieza().habilidad(this, inicio, final_, informacionExtra);
            return new Par(ActionResult.PASS, "Usó la habilidad");
        } else {
            return new Par(ActionResult.FAIL, can.y);
        }
    }

    /**
     * Usa la habilidad de la pieza en el punto indicado, hacia el punto que
     * debe especificarse. Preferir usar el otro método.
     *
     * @param x1 coordenada x del punto en el que se lanza la habilidad.
     * @param y1 coordenada y del punto en el que se lanza la habilidad.
     * @param x2 coordenada x del punto al cual va dirigida la habilidad.
     * @param y2 coordenada y del punto al cual va dirigida la habilidad.
     * @param informacionExtra String que contiene la información de la
     * habilidad, por ejemplo, hacia dónde se va a aplicar la habilidad de la
     * torre (NESW).
     *
     * @return un par que contiene un ActionResult y String a forma de ayuda
     * para dar más información.
     *
     * @see ActionResult
     */
    public Par<ActionResult, String> usarHabilidadPieza(int x1, int y1, int x2, int y2, String informacionExtra) {
        return usarHabilidadPieza(new Point(x1, y1), new Point(x2, y2), informacionExtra);
    }

    /**
     * Usa la habilidad de la pieza en el punto indicado, hacia el punto que
     * debe especificarse.
     *
     * @param inicio punto en el que la habilidad es lanzada.
     * @param final_ punto hacia donde la habilidad se lanza.
     * @param informacionExtra String que contiene la información de la
     * habilidad, por ejemplo, hacia dónde se va a aplicar la habilidad de la
     * torre (NESW).
     *
     * @return un par que contiene un ActionResult y String a forma de ayuda
     * para dar más información.
     *
     * @see ActionResult
     */
    //TODO:
    public Par<ActionResult, String> usarHabilidadEstructura(Point inicio, Point final_, String informacionExtra) {/*
        Escaque escaque = getEscaque(inicio);
        Par<Boolean, String> can = escaque.getEstructura().canUsarHabilidad(this, inicio, final_, informacionExtra);
        if (can.x) {
            escaque.getEstructura().habilidad(this, inicio, final_, informacionExtra);
            return new Par(ActionResult.PASS, "Usó la habilidad");
        } else {
            return new Par(ActionResult.FAIL, can.y);
        }*/
        return new Par(ActionResult.FAIL, "");
    }

    /**
     * Usa la habilidad de la pieza en el punto indicado, hacia el punto que
     * debe especificarse. Preferir usar el otro método.
     *
     * @param x1 coordenada x del punto en el que se lanza la habilidad.
     * @param y1 coordenada y del punto en el que se lanza la habilidad.
     * @param x2 coordenada x del punto al cual va dirigida la habilidad.
     * @param y2 coordenada y del punto al cual va dirigida la habilidad.
     * @param informacionExtra String que contiene la información de la
     * habilidad, por ejemplo, hacia dónde se va a aplicar la habilidad de la
     * torre (NESW).
     *
     * @return un par que contiene un ActionResult y String a forma de ayuda
     * para dar más información.
     *
     * @see ActionResult
     */
    public Par<ActionResult, String> usarHabilidadEstructura(int x1, int y1, int x2, int y2, String informacionExtra) {
        return usarHabilidadEstructura(new Point(x1, y1), new Point(x2, y2), informacionExtra);
    }

    //TODO: movimiento() útil.
    private void movimiento() {
    }

    public void quitarEntidad(int x, int y) {
        this.quitarEntidad(new Point(x, y));
    }

    public void quitarEntidad(Point punto) {
        this.getEscaque(punto).quitarTodo();
    }

    /**
     * Da el escaque ne la posición especificada.
     *
     * @param punto punto del cuál se quiere sacar el escaque.
     *
     * @return el escaque en la posición solicidada.
     *
     * @throws IllegalArgumentException si alguna coordenada se sale de las
     * dimensiones del tablero.
     *
     * @see Escaque
     */
    public Escaque getEscaque(Point punto) {
        if (punto.x < 0) {
            throw new IllegalArgumentException("La coordenada x no puede ser negativa");
        }
        if (punto.y < 0) {
            throw new IllegalArgumentException("La coordenada y no puede ser negativa");
        }
        if (punto.x >= columnas) {
            throw new IllegalArgumentException("La coordenada x no puede ser mayor o igual que el número de columnas");
        }
        if (punto.y >= filas) {
            throw new IllegalArgumentException("La coordenada y no puede ser mayor o igual que el número de filas");
        }
        return tableroAjedrez[punto.y][punto.x];
    }

    /**
     * Da el escaque ne la posición especificada.
     *
     * @param x coordenada x de la posición.
     * @param y coordenada y de la posición.
     *
     * @return el escaque en la posición solicidada.
     *
     * @throws IllegalArgumentException si alguna coordenada se sale de las
     * dimensiones del tablero.
     *
     * @see Escaque
     */
    public Escaque getEscaque(int x, int y) {
        return getEscaque(new Point(x, y));
    }

    public List<Escaque> getEscaquesCercanos(Escaque escaque) {
        List<Escaque> escaques = new ArrayList<>();

        int x = escaque.getLocalizacion().x;
        int y = escaque.getLocalizacion().y;

        int tmp;
        if ((tmp = x + 1) < columnas) {
            escaques.add(this.getEscaque(tmp, y));
        }
        if ((tmp = x - 1) >= 0) {
            escaques.add(this.getEscaque(tmp, y));
        }
        if ((tmp = y + 1) < filas) {
            escaques.add(this.getEscaque(x, tmp));
        }
        if ((tmp = y - 1) >= 0) {
            escaques.add(this.getEscaque(x, tmp));
        }

        return escaques;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(5 * columnas * filas);
        for (int y = filas - 1; y >= 0; y--) {
            for (int x = 0; x < columnas; x++) {
                str.append("[")
                        .append(getEscaque(x, y).getPieza().abreviacion)
                        .append("]");
            }
            str.append('\n');
        }
        return str.toString();
    }
}
