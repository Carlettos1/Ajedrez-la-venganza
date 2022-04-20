package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.starting.PatternStructureMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.InfoCompound;
import com.carlettos.game.board.property.ability.InfoInteger;
import com.carlettos.game.board.property.ability.InfoDirection;
import com.carlettos.game.util.MathHelper;

/**
 *
 * @author Carlettos
 */
public class Catapult extends Piece implements IMove<PatternStructureMove> {

    public static final Ability<Catapult, Tuple<InfoDirection, InfoInteger>, InfoCompound<InfoDirection, InfoInteger>> HABILIDAD_CATAPULTA = new HabilidadCatapulta<>();
    protected final PatternStructureMove patronMover;
    
    public Catapult(Color color) {
        super("Catapulta", "CA", HABILIDAD_CATAPULTA, color, PieceType.STRUCTURE);
        patronMover = new PatternStructureMove() {};
    }
    
    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){
            case MOVE -> this.canMover(tablero, inicio, info, patronMover);
            case ABILITY -> this.getAbility().canUse(tablero, this, inicio, info);
            default -> ActionResult.FAIL;
        };    
    }
    
    public static class HabilidadCatapulta<P extends Piece> extends Ability<P, Tuple<InfoDirection, InfoInteger>, InfoCompound<InfoDirection, InfoInteger>> {
        public HabilidadCatapulta() {
            super("Lanzar Pieza", 
                    "Lanza una pieza en una dirección.", 
                    5, 
                    0, """
                       (NESW) + un número, siguiendo la distribución:
                       7 8 9
                       4 C 6
                       1 2 3.
                       Ej: N 3.""");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoCompound<InfoDirection, InfoInteger> info) {
            if (!this.commonCanUse(tablero, pieza)) {
                return ActionResult.FAIL;
            }
            Point posPieza = switch (info.getY().getValue()) {
                case 1 -> new Point(inicio.x-1, inicio.y-1);
                case 2 -> new Point(inicio.x, inicio.y-1);
                case 3 -> new Point(inicio.x+1, inicio.y-1);
                case 4 -> new Point(inicio.x-1, inicio.y);
                case 6 -> new Point(inicio.x+1, inicio.y);
                case 7 -> new Point(inicio.x-1, inicio.y+1);
                case 8 -> new Point(inicio.x, inicio.y+1);
                case 9 -> new Point(inicio.x+1, inicio.y+1);
                default -> new Point(-1, -1);
            };
            
            if(tablero.isOutOfBorder(posPieza)){
                return ActionResult.FAIL;
            }
            
            int num = info.getY().getValue();
            if(num >= 1 && num <= 9 && num != 5){
                return ActionResult.PASS;
            }
            
            return ActionResult.FAIL;
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoCompound<InfoDirection, InfoInteger> info) {
            Point posPieza = switch (info.getY().getValue()) {
                case 1 -> new Point(inicio.x-1, inicio.y-1);
                case 2 -> new Point(inicio.x, inicio.y-1);
                case 3 -> new Point(inicio.x+1, inicio.y-1);
                case 4 -> new Point(inicio.x-1, inicio.y);
                case 6 -> new Point(inicio.x+1, inicio.y);
                case 7 -> new Point(inicio.x-1, inicio.y+1);
                case 8 -> new Point(inicio.x, inicio.y+1);
                case 9 -> new Point(inicio.x+1, inicio.y+1);
                default -> throw new IllegalArgumentException("Lanzamiento de catapulta inválido");
            };
            if(info.getX().isAxis(Direction.Axis.NS)){
                int x = info.getX().getSign() * 6 + inicio.x;
                x = MathHelper.clamp(0, tablero.columns - 1, x);
                tablero.getEscaque(x, inicio.y).setPiece(tablero.getEscaque(posPieza).getPiece());
                tablero.removePiece(posPieza);
            } else {
                int y = info.getX().getSign() * 6 + inicio.y;
                y = MathHelper.clamp(0, tablero.rows - 1, y);
                tablero.getEscaque(inicio.x, y).setPiece(tablero.getEscaque(posPieza).getPiece());
                tablero.removePiece(posPieza);
            }
            this.commonUse(tablero, pieza);
        }

        @Override
        public InfoCompound<InfoDirection, InfoInteger> getInfo() {
            return new InfoCompound<>(new InfoDirection(Direction.N), new InfoInteger(0));
        }

        @Override
        public Tuple<InfoDirection, InfoInteger>[] getPossibleValues(AbstractBoard tablero, Point point) {
            Direction[] nesw = Direction.values();
            Integer[] nums = {1, 2, 3, 4, 6, 7, 8, 9};
            Tuple<InfoDirection, InfoInteger>[] valores = new Tuple[nesw.length * nums.length];
            for (int i = 0; i < nesw.length; i++) {
                for (int j = 0; j < nums.length; j++) {
                    valores[i * nums.length + j] = new Tuple<>(nesw[i].toInfo(), new InfoInteger(nums[j]));
                }
            }
            return valores;
        }
    }
}
