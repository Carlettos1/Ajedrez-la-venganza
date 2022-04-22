package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;

/**
 * TODO: que se utilize
 * @author Carlettos
 */
public class Wall extends Piece {
    public Wall(Color color) {
        super("wall", "MU", Empty.NO_ABILITY, color, PieceType.STRUCTURE);
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return ActionResult.FAIL;
    }
}
