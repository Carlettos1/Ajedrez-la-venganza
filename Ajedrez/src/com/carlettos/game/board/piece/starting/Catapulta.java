package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Par;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.action.IMover;
import com.carlettos.game.board.piece.pattern.starting.PatronEstructuraMover;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.board.property.ability.InfoCompuesta;
import com.carlettos.game.board.property.ability.InfoInteger;
import com.carlettos.game.board.property.ability.InfoNESW;
import com.carlettos.game.util.MathHelper;

/**
 *
 * @author Carlettos
 */
public class Catapulta extends Piece implements IMover<PatronEstructuraMover> {

    public static final Ability<Catapulta, Par<InfoNESW, InfoInteger>, InfoCompuesta<InfoNESW, InfoInteger>> HABILIDAD_CATAPULTA = new HabilidadCatapulta<>();
    protected final PatronEstructuraMover patronMover;
    
    public Catapulta(Color color) {
        super("Catapulta", "CA", HABILIDAD_CATAPULTA, color, Tipo.ESTRUCTURA);
        patronMover = new PatronEstructuraMover() {};
    }
    
    @Override
    public ActionResult can(Accion accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            default -> ActionResult.FAIL;
        };    
    }
    
    public static class HabilidadCatapulta<P extends Piece> extends Ability<P, Par<InfoNESW, InfoInteger>, InfoCompuesta<InfoNESW, InfoInteger>> {
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
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoCompuesta<InfoNESW, InfoInteger> info) {
            if (!this.commonCanUsar(tablero, pieza)) {
                return ActionResult.FAIL;
            }
            Point posPieza = switch (info.getY().getValor()) {
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
            
            int num = info.getY().getValor();
            if(num >= 1 && num <= 9 && num != 5){
                return ActionResult.PASS;
            }
            
            return ActionResult.FAIL;
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoCompuesta<InfoNESW, InfoInteger> info) {
            Point posPieza = switch (info.getY().getValor()) {
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
                x = MathHelper.clamp(0, tablero.columnas - 1, x);
                tablero.getEscaque(x, inicio.y).setPieza(tablero.getEscaque(posPieza).getPieza());
                tablero.quitarPieza(posPieza);
            } else {
                int y = info.getX().getSign() * 6 + inicio.y;
                y = MathHelper.clamp(0, tablero.filas - 1, y);
                tablero.getEscaque(inicio.x, y).setPieza(tablero.getEscaque(posPieza).getPieza());
                tablero.quitarPieza(posPieza);
            }
            this.commonUsar(tablero, pieza);
        }

        @Override
        public InfoCompuesta<InfoNESW, InfoInteger> getInfoHabilidad() {
            return new InfoCompuesta<>(new InfoNESW(Direction.N), new InfoInteger(0));
        }

        @Override
        public Par<InfoNESW, InfoInteger>[] getAllValoresPosibles(AbstractBoard tablero, Point point) {
            Direction[] nesw = Direction.values();
            Integer[] nums = {1, 2, 3, 4, 6, 7, 8, 9};
            Par<InfoNESW, InfoInteger>[] valores = new Par[nesw.length * nums.length];
            for (int i = 0; i < nesw.length; i++) {
                for (int j = 0; j < nums.length; j++) {
                    valores[i * nums.length + j] = new Par<>(nesw[i].toInfo(), new InfoInteger(nums[j]));
                }
            }
            return valores;
        }
    }
}
