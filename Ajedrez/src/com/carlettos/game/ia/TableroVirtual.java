package com.carlettos.game.ia;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.propiedad.habilidad.Info;

/**
 *
 * @author Carlettos
 */
public class TableroVirtual extends AbstractTablero {
    private int turno;

    public TableroVirtual(int columnas, int filas, int turno) {
        super(columnas, filas);
        this.turno = turno;
    }
    
    public ActionResult intentar(Accion accion, Point inicio, Point final_, Info info){
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);
        ActionResult ar = escaqueInicio.getPieza().can(accion, this, inicio, final_);
        if(ar.isPositive()){
            switch (accion) {
                case COMER, MOVER -> {
                    escaqueFinal.setPieza(escaqueInicio.getPieza());
                    escaqueInicio.quitarPieza();
                    escaqueFinal.getPieza().postAccion(accion, this, inicio, final_);
                }
                case ATACAR -> {
                    escaqueFinal.quitarPieza();
                    escaqueInicio.getPieza().postAccion(accion, this, inicio, final_);
                }
                    
            }
            return ar;
        }
        if(accion.equals(Accion.HABILIDAD)){
            ar = escaqueInicio.getPieza().getHabilidad().canUsar(this, escaqueInicio.getPieza(), inicio, info);
            if(ar.isPositive()){
                escaqueInicio.getPieza().getHabilidad().usar(this, escaqueInicio.getPieza(), inicio, info);
            }
            
        }
        return ActionResult.FAIL;
    }

    public static TableroVirtual fromTablero(AbstractTablero tablero){
        int t1;
        switch (tablero) {
            case Tablero t -> t1 = t.getReloj().getTurno();
            case TableroVirtual t -> t1 = t.getTurno();
            case default -> throw new IllegalArgumentException("Tablero no es instanceof Tablero");
        }
        TableroVirtual t = new TableroVirtual(tablero.columnas, tablero.filas, t1);
        tablero.copyContentTo(t);
        return t;
    }

    public int getTurno() {
        return turno;
    }
}
