package com.carlettos.game.tablero.manager;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Info;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase sólo controlará el trablero, cualquier otra funcionalidad que no
 * sea la de controlar a sus piezas, o proveer méthodos de utilidad sobre si
 * mismo, no es de su responsabilidad, por lo tanto, no tendrá conocimiento de
 * jugadores, relojes o barajas.
 *
 * @author Carlos
 *
 * @see Pieza
 */
public class Tablero extends AbstractTablero {
    /**
     * Reloj vinculado a este tablero.
     */
    protected final Reloj reloj;

    public Tablero(int columnas, int filas, Reloj reloj) {
        super(columnas, filas);
        this.reloj = reloj;
    }

    /**
     * Come la pieza en la posición final_ con la pieza en la posición inicial.
     *
     * @param inicio posición de la pieza que come.
     * @param final_ posición de la pieza que es comida.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult intentarComerPieza(Point inicio, Point final_) {
        if(!canMoverPieza(getEscaque(inicio).getPieza())){
            return ActionResult.FAIL;
        }
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPieza().can(Accion.COMER, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.setPieza(escaqueInicio.getPieza());
            escaqueInicio.quitarPieza();
            escaqueFinal.getPieza().postAccion(Accion.COMER, this, inicio, final_);
            movimiento();
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    /**
     * Mueve la pieza en la posición inicial a la posición final_.
     *
     * @param inicio posición de la pieza.
     * @param final_ posición al que la pieza se mueve.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult intentarMoverPieza(Point inicio, Point final_) {
        if(!canMoverPieza(getEscaque(inicio).getPieza())){
            return ActionResult.FAIL;
        }
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPieza().can(Accion.MOVER, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.setPieza(escaqueInicio.getPieza());
            escaqueInicio.quitarPieza();
            escaqueFinal.getPieza().postAccion(Accion.MOVER, this, inicio, final_);
            movimiento();
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
        if(!canMoverPieza(getEscaque(inicio).getPieza())){
            return ActionResult.FAIL;
        }
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPieza().can(Accion.ATACAR, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.quitarPieza();
            escaqueFinal.getPieza().postAccion(Accion.ATACAR, this, inicio, final_);
            movimiento();
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
     * @param info String que contiene la información de la
     * habilidad, por ejemplo, hacia dónde se va a aplicar la habilidad de la
     * torre (NESW).
     *
     * @return un par que contiene un ActionResult y String a forma de ayuda
     * para dar más información.
     *
     * @see ActionResult
     */
    public ActionResult intentarHabilidadPieza(Point inicio, Info info) {
        if(!canMoverPieza(getEscaque(inicio).getPieza())){
            return ActionResult.FAIL;
        }
        Escaque escaque = getEscaque(inicio);
        ActionResult ar = escaque.getPieza().getHabilidad().canUsar(this, escaque.getPieza(), inicio, info);
        if (ar.isPositive()) {
            escaque.getPieza().getHabilidad().usar(this, escaque.getPieza(), inicio, info);
            movimiento();
        }
        return ar;
    }

    public void movimiento() {
        this.reloj.movimiento();
        if(getReloj().getMovimientos() >= getReloj().turnoDe().getMovimientosPorTurnos()){
            for (Escaque[] escaques : tableroAjedrez) {
                for (Escaque escaque : escaques) {
                    escaque.getPieza().setSeHaMovidoEsteTurno(false);
                }
            }
            getReloj().terminarTurno();
        }
    }
    
    public boolean canMoverPieza(Pieza pieza){
        return getReloj().turnoDe().getColor().equals(pieza.getColor()) && getReloj().canJugar(getReloj().turnoDe());
    }

    public Reloj getReloj() {
        return reloj;
    }
    
    public List<Pieza> getAllPiezasOfColor(Color color){
        List<Pieza> piezas = new ArrayList<>();
        for (Escaque[] escaques : tableroAjedrez) {
            for (Escaque escaque : escaques) {
                if(escaque.isControladoPor(color)){
                    piezas.add(escaque.getPieza());
                }
            }
        }
        return piezas;
    }
    
    public Tablero copy(){
        Tablero copy = new Tablero(columnas, filas, reloj.copy());
        for (Escaque[] escaques : tableroAjedrez) {
            for (Escaque escaque : escaques) {
                copy.getEscaque(escaque.getPos()).setIsConstruible(escaque.isConstruible());
                copy.getEscaque(escaque.getPos()).setIsFuenteDeMagia(escaque.isFuenteDeMagia());
                try {
                    copy.getEscaque(escaque.getPos()).setPieza(escaque.getPieza().clone());
                } catch (CloneNotSupportedException ex) {
                    System.out.println("no ha clonao");
                }
            }
        }
        return copy;
    }
}
