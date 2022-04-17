package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Vacia;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.piece.pattern.action.IAtacar;
import com.carlettos.game.board.piece.pattern.starting.PatronBallestaAtacar;
import com.carlettos.game.board.piece.pattern.starting.PatronEstructuraMover;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.Tipo;

/**
 *
 * @author Carlettos
 */
public class Ballesta extends Piece implements IMover<PatronEstructuraMover>, IAtacar<PatronBallestaAtacar> {
    protected final PatronEstructuraMover patronMover;
    protected final PatronBallestaAtacar patronAtacar;

    public Ballesta(Color color) {
        super("Ballesta", "BA", Vacia.NO_HABILIDAD, color, Tipo.ESTRUCTURA);
        patronMover = new PatronEstructuraMover() {};
        patronAtacar = new PatronBallestaAtacar() {};
    }

    @Override
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case ATACAR -> this.canAtacar(tablero, inicio, final_, patronAtacar);
            default -> ActionResult.FAIL;
        };
    }
}
