package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.pieza.patron.Patron;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronMoverHechicero;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class Hechicero extends Pieza implements IMover<PatronMoverHechicero> {
    protected final PatronMoverHechicero patronMover;
    public Hechicero(Color color) { //TODO: hacer lo de la habilidad
        super("Hechicero", "HE", Vacia.NO_HABILIDAD, color, Tipo.BIOLOGICA, Tipo.HEROICA, Tipo.INMUNE, Tipo.TRANSPORTABLE);
        patronMover = new PatronMoverHechicero() {};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion) {
            case MOVER -> this.canMover(tablero, inicio, final_, this.patronMover);
            default -> ActionResult.FAIL;
        };
    }
}
