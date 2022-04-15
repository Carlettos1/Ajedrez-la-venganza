package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.board.piece.Vacia;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.board.piece.pattern.starting.PatronHechiceroMover;

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
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, this.patronMover);
            default -> ActionResult.FAIL;
        };
    }
}
