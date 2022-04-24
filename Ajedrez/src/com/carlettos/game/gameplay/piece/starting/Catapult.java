package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityTuple;
import com.carlettos.game.gameplay.ability.info.InfoTuple;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternStructureMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.PieceType;
import com.carlettos.game.util.helper.MathHelper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlettos
 */
public class Catapult extends Piece implements IMove<PatternStructureMove> {

    public static final Ability<Catapult, Tuple<Direction, Integer>, InfoTuple<Direction, Integer>> ABILITY_CATAPULT = new AbilityCatapult<>();
    protected final PatternStructureMove movePattern;
    
    public Catapult(Color color) {
        super("catapult", "ca", ABILITY_CATAPULT, color, PieceType.STRUCTURE);
        movePattern = new PatternStructureMove() {};
    }
    
    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){
            case MOVE -> this.canMove(board, start, info, movePattern);
            case ABILITY -> this.getAbility().canUse(board, this, start, info);
            default -> ActionResult.FAIL;
        };    
    }
    
    public static class AbilityCatapult<P extends Piece> extends Ability<P, Tuple<Direction, Integer>, InfoTuple<Direction, Integer>> implements AbilityTuple<Direction, Integer> {
        public AbilityCatapult() {
            super("Lanzar Pieza", 
                    "Lanza una pieza en una dirección.", 
                    5, 
                    0);
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoTuple<Direction, Integer> info) {
            if (!this.commonCanUse(board, piece)) {
                return ActionResult.FAIL;
            }
            return defaultCan(board, start, info);
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoTuple<Direction, Integer> info) {
            Point posPiece = switch (info.getB()) {
                case 1 -> new Point(start.x-1, start.y-1);
                case 2 -> new Point(start.x, start.y-1);
                case 3 -> new Point(start.x+1, start.y-1);
                case 4 -> new Point(start.x-1, start.y);
                case 6 -> new Point(start.x+1, start.y);
                case 7 -> new Point(start.x-1, start.y+1);
                case 8 -> new Point(start.x, start.y+1);
                case 9 -> new Point(start.x+1, start.y+1);
                default -> throw new IllegalArgumentException("Lanzamiento de catapulta inválido");
            };
            if(info.getA().isAxis(Direction.Axis.NS)){
                int x = info.getA().getSign() * 6 + start.x;
                x = MathHelper.clamp(0, board.columns - 1, x);
                board.getEscaque(x, start.y).setPiece(board.getEscaque(posPiece).getPiece());
                board.removePiece(posPiece);
            } else {
                int y = info.getA().getSign() * 6 + start.y;
                y = MathHelper.clamp(0, board.rows - 1, y);
                board.getEscaque(start.x, y).setPiece(board.getEscaque(posPiece).getPiece());
                board.removePiece(posPiece);
            }
            this.commonUse(board, piece);
        }

        @Override
        public Tuple<Direction, Integer>[] getPossibleValues(AbstractBoard board, Point start) {
            Direction[] dirs = Direction.values();
            Integer[] nums = {1, 2, 3, 4, 6, 7, 8, 9};
            List<Tuple<Direction, Integer>> valores = new ArrayList<>(dirs.length * nums.length);
            for (int i = 0; i < dirs.length; i++) {
                for (int j = 0; j < nums.length; j++) {
                    if(defaultCan(board, start, new InfoTuple<>(dirs[i], nums[j])).isPositive()) {
                        valores.add(new Tuple<>(dirs[i], nums[j]));
                    }
                }
            }
            return valores.toArray(Tuple[]::new);
        }
        
        private ActionResult defaultCan(AbstractBoard board, Point start, InfoTuple<Direction, Integer> info){
            Point posPiece = switch (info.getB()) {
                case 1 -> new Point(start.x-1, start.y-1);
                case 2 -> new Point(start.x, start.y-1);
                case 3 -> new Point(start.x+1, start.y-1);
                case 4 -> new Point(start.x-1, start.y);
                case 6 -> new Point(start.x+1, start.y);
                case 7 -> new Point(start.x-1, start.y+1);
                case 8 -> new Point(start.x, start.y+1);
                case 9 -> new Point(start.x+1, start.y+1);
                default -> new Point(-1, -1);
            };
            
            if(board.isOutOfBorder(posPiece)){
                return ActionResult.FAIL;
            }
            
            int num = info.getB();
            if(num >= 1 && num <= 9 && num != 5){
                return ActionResult.PASS;
            }
            
            return ActionResult.FAIL;
        }
    }
}
