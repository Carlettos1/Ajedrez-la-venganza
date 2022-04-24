package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Empty;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 * TODO: que se utilize
 * @author Carlettos
 */
public class Wall extends Piece {
    public Wall(Color color) {
        super("wall", "wal", Empty.NO_ABILITY, color, PieceType.STRUCTURE);
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return ActionResult.FAIL;
    }
}
