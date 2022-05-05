package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.helper.MathHelper;

public class AbilityCatapult extends Ability {
    public AbilityCatapult() {
        super("catapult", 5, 0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece)) {
            return ActionResult.FAIL;
        }
        
        if (!info.isType(Tuple.class)) {
            return ActionResult.FAIL;
        }
        Tuple<?, ?> tuple = (Tuple<?, ?>) info.getValue();
        
        if (!(tuple.x instanceof Direction && tuple.y instanceof Integer)) {
            return ActionResult.FAIL;
        }
        
        return defaultCan(board, start, (Tuple<Direction, Integer>) tuple);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Tuple<?, ?> tuple = (Tuple<?, ?>) info.getValue();
        var dir = (Direction) tuple.x;
        var i = (Integer) tuple.y;
        
        Point posPiece = switch (i) {
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
        if(dir.isAxis(Direction.Axis.EW)){
            int x = dir.getSign() * 6 + start.x;
            x = MathHelper.clamp(0, board.columns - 1, x);
            board.getEscaque(x, start.y).setPiece(board.getEscaque(posPiece).getPiece());
            board.removePiece(posPiece);
        } else {
            int y = dir.getSign() * 6 + start.y;
            y = MathHelper.clamp(0, board.rows - 1, y);
            board.getEscaque(start.x, y).setPiece(board.getEscaque(posPiece).getPiece());
            board.removePiece(posPiece);
        }
        this.commonUse(board, piece);
    }

    @Override
    public Tuple<Direction, Integer>[] getValues(AbstractBoard board, Point start) {
        Direction[] dirs = Direction.values();
        Integer[] nums = {1, 2, 3, 4, 6, 7, 8, 9};
        List<Tuple<Direction, Integer>> valores = new ArrayList<>(dirs.length * nums.length);
        for (int i = 0; i < dirs.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if(defaultCan(board, start, new Tuple<>(dirs[i], nums[j])).isPositive()) {
                    valores.add(new Tuple<>(dirs[i], nums[j]));
                }
            }
        }
        return valores.toArray(Tuple[]::new);
    }
    
    private ActionResult defaultCan(AbstractBoard board, Point start, Tuple<Direction, Integer> tuple){
        Point posPiece = switch (tuple.y) {
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
        
        int num = tuple.y;
        if(num >= 1 && num <= 9 && num != 5){
            return ActionResult.PASS;
        }
        
        return ActionResult.FAIL;
    }
}
