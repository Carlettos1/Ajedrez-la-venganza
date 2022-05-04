package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;

public class AbilityRam extends Ability {
    public AbilityRam() {
        super("Carga de Ariete", 
                "El ariete carga en una dirección hasta alcanzar la primera "
                        + "pieza, luego procede a avanzar, comiendo lo que atraviese, "
                        + "dependiendo de cuanto haya cargado "
                        + "(avanza 1 escaque por cada 5 que carge, +1 de base).", 
                4, 
                0);
    }
    
    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece) && info.isType(Direction.class));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var dir = (Direction) info.getValue();
        int charge = 1;
        board.getEscaque(start).removePiece();
        if(dir.isAxis(Direction.Axis.EW)) {
            for(int x = start.x + dir.getSign();;x += dir.getSign()){
                if(board.isOutOfBorder(new Point(x, start.y))){
                    board.getEscaque(x - dir.getSign(), start.y).setPiece(piece);
                    break;
                }
                if(board.getEscaque(x, start.y).hasPiece()) {
                    for (int dx = 0; dx < charge/5 + 1; dx++) {
                        if(!board.isOutOfBorder(new Point(x + dx, start.y))){
                            board.getEscaque(x + dx, start.y).setPiece(piece);
                            board.getEscaque(x + dx - dir.getSign(), start.y).removePiece();
                        }
                    }
                    break;
                } else {
                    charge++;
                }
            }
        } else {
            for(int y = start.y + dir.getSign();;y += dir.getSign()){
                if(board.isOutOfBorder(new Point(start.x, y))){
                    board.getEscaque(start.x, y - dir.getSign()).setPiece(piece);
                    break;
                }
                if(board.getEscaque(start.x, y).hasPiece()) {
                    for (int dy = 0; dy < charge/5 + 1; dy++) {
                        if(!board.isOutOfBorder(new Point(start.x, y + dy))){
                            board.getEscaque(start.x, y + dy).setPiece(piece);
                            board.getEscaque(start.x, y + dy - dir.getSign()).removePiece();
                        }
                    }
                    break;
                } else {
                    charge++;
                }
            }
        }
        this.commonUse(board, piece);
    }

    @Override
    public Direction[] getValues(AbstractBoard board, Point start) {
        return Direction.values();
    }
}
