package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityPoint;
import com.carlettos.game.gameplay.ability.info.InfoPoint;
import com.carlettos.game.gameplay.pattern.classic.PatternKing;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;
import java.util.ArrayList;
import java.util.List;

public class King extends SimplePiece<PatternKing> {

    //TODO: que no se muera después de comer o moverse
    protected boolean hasUsedTP;
    public static final Ability<King, Point, InfoPoint> ABILITY_KING = new AbilityKing<>();

    public King(Color color) {
        super("king", "k", ABILITY_KING, color, new PatternKing(){}, PieceType.BIOLOGIC, PieceType.IMMUNE, PieceType.HEROIC);
        this.hasUsedTP = false;
    }
    
    public static class AbilityKing<P extends King> extends Ability<P, Point, InfoPoint> implements AbilityPoint {

        public AbilityKing() {
            super("Teletransportación",
                    "Se teletransporta a cualquier casilla en un rango de 5",
                    0,
                    2);
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoPoint info) {
            if (piece.hasUsedTP) {
                return ActionResult.FAIL;
            }

            return ActionResult.fromBoolean(info.getValue().getDistanceTo(start) <= 5);
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoPoint info) {
            piece.hasUsedTP = true;
            board.getEscaque(info.getValue()).setPiece(piece);
            board.getEscaque(start).removePiece();
        }

        @Override
        public Point[] getPossibleValues(AbstractBoard board, Point start) {
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
}
