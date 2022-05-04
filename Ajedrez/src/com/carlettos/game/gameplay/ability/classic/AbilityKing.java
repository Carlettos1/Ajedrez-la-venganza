package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.King;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityKing extends Ability{
    public AbilityKing() {
        super("Teletransportación",
                "Se teletransporta a cualquier casilla en un rango de 5",
                0,
                2);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!(piece instanceof King)) {
            return ActionResult.FAIL;
        }
        
        if (!info.isType(Point.class)) {
            return ActionResult.FAIL;
        }
        
        var king = (King) piece;
        var point = (Point) info.getValue();
        
        if (king.isUsedTP()) {
            return ActionResult.FAIL;
        }

        return ActionResult.fromBoolean(point.getDistanceTo(start) <= 5);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var king = (King) piece;
        var point = (Point) info.getValue();
        
        king.setUsedTP(true);
        board.getEscaque(point).setPiece(piece);
        board.getEscaque(start).removePiece();
    }

    @Override
    public Point[] getValues(AbstractBoard board, Point start) {
        List<Point> values = new ArrayList<>();
        for (int x = 0; x < board.columns; x++) {
            for (int y = 0; y < board.rows; y++) {
                if(new Point(x, y).getDistanceTo(start) <= 5 && !board.getEscaque(x, y).hasPiece()){
                    values.add(new Point(x, y));
                }
            }
        }
        return values.toArray(Point[]::new);
    }
}
