package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.starting.PatternStructureMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.info.InfoDirection;
import com.carlettos.game.board.property.ability.InfoUse.AbilityDirection;

/**
 *
 * @author Carlettos
 */
public class Ram extends Piece implements IMove<PatternStructureMove> {
    
    public static final Ability<Ram, Direction, InfoDirection> ABILITY_RAM = new AbilityRam<>();
    protected final PatternStructureMove movePattern;
    
    public Ram(Color color) {
        super("Ariete", "AR", ABILITY_RAM, color, PieceType.STRUCTURE);
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
    
    public static class AbilityRam<P extends Piece> extends Ability<P, Direction, InfoDirection> implements AbilityDirection {
        public AbilityRam() {
            super("Carga de Ariete", 
                    "El ariete carga en una dirección hasta alcanzar la primera "
                            + "pieza, luego procede a avanzar, comiendo lo que atraviese, "
                            + "dependiendo de cuanto haya cargado "
                            + "(avanza 1 escaque por cada 5 que carge, +1 de base).", 
                    4, 
                    0, 
                    "La dirección cardinal hacia dónde se mueve el alfil (NESW).");
        }
        
        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoDirection info) {
            return ActionResult.fromBoolean(this.commonCanUse(board, piece));
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoDirection info) {
            int charge = 1;
            board.getEscaque(start).removePiece();
            if(info.isAxis(Direction.Axis.EW)) {
                for(int x = start.x + info.getSign();;x += info.getSign()){
                    if(board.isOutOfBorder(new Point(x, start.y))){
                        board.getEscaque(x - info.getSign(), start.y).setPiece(piece);
                        break;
                    }
                    if(board.getEscaque(x, start.y).hasPiece()) {
                        for (int dx = 0; dx < charge/5 + 1; dx++) {
                            if(!board.isOutOfBorder(new Point(x + dx, start.y))){
                                board.getEscaque(x + dx, start.y).setPiece(piece);
                                board.getEscaque(x + dx - info.getSign(), start.y).removePiece();
                            }
                        }
                        break;
                    } else {
                        charge++;
                    }
                }
            } else {
                for(int y = start.y + info.getSign();;y += info.getSign()){
                    if(board.isOutOfBorder(new Point(start.x, y))){
                        board.getEscaque(start.x, y - info.getSign()).setPiece(piece);
                        break;
                    }
                    if(board.getEscaque(start.x, y).hasPiece()) {
                        for (int dy = 0; dy < charge/5 + 1; dy++) {
                            if(!board.isOutOfBorder(new Point(start.x, y + dy))){
                                board.getEscaque(start.x, y + dy).setPiece(piece);
                                board.getEscaque(start.x, y + dy - info.getSign()).removePiece();
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
    }
}
