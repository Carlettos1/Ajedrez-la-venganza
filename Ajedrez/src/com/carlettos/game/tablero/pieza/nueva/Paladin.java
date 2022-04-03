package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronReina;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class Paladin extends Pieza implements IMover<PatronReina>, IComer<PatronReina> {
    protected final PatronReina patron;

    public Paladin(Color color) { //TODO: Habilidad
        super("Paladin", "PA", Vacia.NO_HABILIDAD, color, Tipo.HEROICA, Tipo.INMUNE);
        patron = new PatronReina(){};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patron);
            case COMER -> this.canComer(tablero, inicio, final_, patron);
            default -> ActionResult.FAIL;
        };
    }
}
