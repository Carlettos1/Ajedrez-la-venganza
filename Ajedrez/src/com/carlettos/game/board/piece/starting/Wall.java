package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.PieceType;
import java.util.List;

/**
 * TODO: que se utilize
 * @author Carlettos
 */
public class Wall extends Piece {
    public Wall(Color color) {
        super("Muro", "MU", Empty.NO_HABILIDAD, color, PieceType.ESTRUCTURA);
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Point final_) {
        return ActionResult.FAIL;
    }

    @Override
    public List<Tuple<Point, Action>> allAcciones(AbstractBoard tablero, Point seleccionado) {
        return List.of();
    }
}
