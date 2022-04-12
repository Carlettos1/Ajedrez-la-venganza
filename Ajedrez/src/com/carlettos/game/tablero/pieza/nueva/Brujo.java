package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronHechiceroMover;

/**
 *
 * @author Carlettos
 */
public class Brujo extends Pieza implements IMover<PatronHechiceroMover> {
    protected final PatronHechiceroMover patronMover;

    public Brujo(Color color) { //TODO: habilidad
        super("Brujo", "B", Vacia.NO_HABILIDAD, color, Tipo.TRANSPORTABLE, Tipo.DEMONIACA, Tipo.INMUNE);
        this.patronMover = new PatronHechiceroMover() {};
    }

    @Override
    public ActionResult can(Accion accion, AbstractTablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, this.patronMover);
            default -> ActionResult.FAIL;
        };
    }
}
