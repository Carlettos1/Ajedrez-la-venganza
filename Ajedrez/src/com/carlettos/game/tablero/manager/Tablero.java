package com.carlettos.game.tablero.manager;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.core.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: reloj y manos?
 * Esta clase sólo controlará el trablero, cualquier otra funcionalidad que no
 * sea la de controlar a sus piezas, o proveer méthodos de utilidad sobre si
 * mismo, no es de su responsabilidad, por lo tanto, no tendrá conocimiento de
 * jugadores, relojes o barajas.
 *
 * @author Carlos
 *
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
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult comerPieza(int x1, int y1, int x2, int y2) {
        return intentarComerPieza(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Come la pieza en la posición final_ con la pieza en la posición inicial.
     *
     * @param inicio posición de la pieza que come.
     * @param final_ posición de la pieza que es comida.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult intentarComerPieza(Point inicio, Point final_) {
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPieza().can(Accion.COMER, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.setPieza(escaqueInicio.getPieza());
            escaqueInicio.quitarPieza();
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    /**
     * Mueve la pieza en la posición x1, y1 a la posición x2, y2.
     *
     * @param x1 coordenada x del punto de la pieza.
     * @param y1 coordenada y del punto de la pieza.
     * @param x2 coordenada x del punto al que la pieza se mueve.
     * @param y2 coordenada y del punto al que la pieza se mueve.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult moverPieza(int x1, int y1, int x2, int y2) {
        return intentarMoverPieza(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Mueve la pieza en la posición inicial a la posición final_.
     *
     * @param inicio posición de la pieza.
     * @param final_ posición al que la pieza se mueve.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult intentarMoverPieza(Point inicio, Point final_) {
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPieza().can(Accion.MOVER, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.setPieza(escaqueInicio.getPieza());
            escaqueInicio.quitarPieza();
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    /**
     * Ataca a la pieza en la posición final_.
     *
     * @param inicio posición de la pieza.
     * @param final_ posición al que la ataca.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult intentarAtacarPieza(Point inicio, Point final_) {
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPieza().can(Accion.ATACAR, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.quitarPieza();
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
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
    public ActionResult usarHabilidadPieza(Point inicio, Point final_, String informacionExtra) {
        Escaque escaque = getEscaque(inicio);
        ActionResult ar = escaque.getPieza().getHabilidad().canUsar(this, escaque.getPieza(), inicio, final_, informacionExtra);
        if (ar.isPositive()) {
            escaque.getPieza().getHabilidad().usar(this, escaque.getPieza(), inicio, final_, informacionExtra);
        }
        return ar;
    }

    /**
     * Usa la habilidad de la pieza en el punto indicado, hacia el punto que
     * debe especificarse. Preferir usar el otro méthodo.
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
    public ActionResult usarHabilidadPieza(int x1, int y1, int x2, int y2, String informacionExtra) {
        return usarHabilidadPieza(new Point(x1, y1), new Point(x2, y2), informacionExtra);
    }

    //TODO: movimiento() útil.
    private void movimiento() {
    }

    public void quitarEntidad(int x, int y) {
        this.quitarEntidad(new Point(x, y));
    }

    public void quitarEntidad(Point punto) {
        this.getEscaque(punto).quitarPieza();
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
