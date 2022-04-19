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
import com.carlettos.game.board.property.ability.InfoNESW;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadNESW;

/**
 *
 * @author Carlettos
 */
public class Ram extends Piece implements IMove<PatternStructureMove> {
    
    public static final Ability<Ram, Direction, InfoNESW> HABILIDAD_ARIETE = new HabilidadAriete<>();
    protected final PatternStructureMove patronMover;
    
    public Ram(Color color) {
        super("Ariete", "AR", HABILIDAD_ARIETE, color, PieceType.ESTRUCTURA);
        patronMover = new PatternStructureMove() {};
    }
    
    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, info, patronMover);
            case HABILIDAD -> this.getAbility().canUse(tablero, this, inicio, info);
            default -> ActionResult.FAIL;
        };
    }
    
    public static class HabilidadAriete<P extends Piece> extends Ability<P, Direction, InfoNESW> implements HabilidadNESW{
        public HabilidadAriete() {
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
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoNESW info) {
            if (!this.commonCanUse(tablero, pieza)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoNESW info) {
            int carga = 1;
            tablero.getEscaque(inicio).removePiece();
            if(info.isAxis(Direction.Axis.EW)) {
                for(int x = inicio.x + info.getSign();;x += info.getSign()){
                    if(tablero.isOutOfBorder(new Point(x, inicio.y))){
                        tablero.getEscaque(x - info.getSign(), inicio.y).setPiece(pieza);
                        break;
                    }
                    if(tablero.getEscaque(x, inicio.y).hasPiece()) {
                        for (int dx = 0; dx < carga/5 + 1; dx++) {
                            if(!tablero.isOutOfBorder(new Point(x + dx, inicio.y))){
                                tablero.getEscaque(x + dx, inicio.y).setPiece(pieza);
                                tablero.getEscaque(x + dx - info.getSign(), inicio.y).removePiece();
                            }
                        }
                        break;
                    } else {
                        carga++;
                    }
                }
            } else {
                for(int y = inicio.y + info.getSign();;y += info.getSign()){
                    if(tablero.isOutOfBorder(new Point(inicio.x, y))){
                        tablero.getEscaque(inicio.x, y - info.getSign()).setPiece(pieza);
                        break;
                    }
                    if(tablero.getEscaque(inicio.x, y).hasPiece()) {
                        for (int dy = 0; dy < carga/5 + 1; dy++) {
                            if(!tablero.isOutOfBorder(new Point(inicio.x, y + dy))){
                                tablero.getEscaque(inicio.x, y + dy).setPiece(pieza);
                                tablero.getEscaque(inicio.x, y + dy - info.getSign()).removePiece();
                            }
                        }
                        break;
                    } else {
                        carga++;
                    }
                }
            }
            this.commonUse(tablero, pieza);
        }
    }
}
