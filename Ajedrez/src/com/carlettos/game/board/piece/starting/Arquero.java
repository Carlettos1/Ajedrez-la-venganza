package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.board.piece.Vacia;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.piece.pattern.action.IAtacar;
import com.carlettos.game.board.piece.pattern.starting.PatronArqueroAtacar;
import com.carlettos.game.board.piece.pattern.starting.PatronArqueroMover;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.Tipo;

/**
 *
 * @author Carlettos
 */
public class Arquero extends Pieza implements IMover<PatronArqueroMover>, IAtacar<PatronArqueroAtacar> {
    protected final PatronArqueroMover patronMover;
    protected final PatronArqueroAtacar patronAtacar;
    
    public Arquero(Color color) {
        super("Arquero", "ARQ", Vacia.NO_HABILIDAD, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        patronMover = new PatronArqueroMover() {};
        patronAtacar = new PatronArqueroAtacar() {};
    }

    @Override
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){ //TODO: que el ataque pueda fallar
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case ATACAR -> this.canAtacar(tablero, inicio, final_, patronAtacar);
            default -> ActionResult.FAIL;
        };
    }
}
