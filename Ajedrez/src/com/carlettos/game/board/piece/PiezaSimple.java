package com.carlettos.game.board.piece;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Patron;
import com.carlettos.game.board.piece.pattern.action.IComer;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.property.ability.Ability;

/**
 *
 * @author Carlos
 */
public abstract class PiezaSimple<P extends Patron> extends Piece implements IComer<P>, IMover<P>{
    protected final P patron;

    public PiezaSimple(String nombre, String abreviacion, Ability habilidad, Color color, P patron, Tipo... tipos) {
        super(nombre, abreviacion, habilidad, color, tipos);
        this.patron = patron;
    }

    /**
     * @@inheritDoc
     */
    @Override
    public final ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch (accion) {
            case COMER -> this.canComer(tablero, inicio, final_, patron);
            case MOVER -> this.canMover(tablero, inicio, final_, patron);
            default -> ActionResult.FAIL;
        };
    }
}
