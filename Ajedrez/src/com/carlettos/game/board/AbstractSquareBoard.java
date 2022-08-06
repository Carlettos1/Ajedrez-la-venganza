package com.carlettos.game.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.carlettos.game.board.deathPile.BasicDeathPile;
import com.carlettos.game.board.deathPile.IDeathPile;
import com.carlettos.game.board.shape.Shape;
import com.carlettos.game.board.shape.Square;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Empty;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.Bishop;
import com.carlettos.game.gameplay.piece.classic.King;
import com.carlettos.game.gameplay.piece.classic.Knight;
import com.carlettos.game.gameplay.piece.classic.Pawn;
import com.carlettos.game.gameplay.piece.classic.Queen;
import com.carlettos.game.gameplay.piece.classic.Rook;
import com.carlettos.game.gameplay.piece.starting.Archer;
import com.carlettos.game.gameplay.piece.starting.Ballista;
import com.carlettos.game.gameplay.piece.starting.Builder;
import com.carlettos.game.gameplay.piece.starting.Cannon;
import com.carlettos.game.gameplay.piece.starting.Catapult;
import com.carlettos.game.gameplay.piece.starting.CrazyPawn;
import com.carlettos.game.gameplay.piece.starting.Magician;
import com.carlettos.game.gameplay.piece.starting.Paladin;
import com.carlettos.game.gameplay.piece.starting.Ram;
import com.carlettos.game.gameplay.piece.starting.ShieldBearer;
import com.carlettos.game.gameplay.piece.starting.Ship;
import com.carlettos.game.gameplay.piece.starting.SuperPawn;
import com.carlettos.game.gameplay.piece.starting.TeslaTower;
import com.carlettos.game.gameplay.piece.starting.Wall;
import com.carlettos.game.gameplay.piece.starting.Warlock;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.helper.TypeHelper;

/**
 * Generic board. It doesn't have a clock and doesn't manage turns. It's just a
 * representation of the current board.
 *
 * @author Carlettos
 */
public abstract class AbstractSquareBoard implements IClockUse, IBaseBoard {

    protected final Escaque[][] chessBoard;

    /**
     * Shape of the Board.
     */
    public final Square shape;

    /**
     * Death pile of the Board.
     */
    protected final BasicDeathPile deathPile;

    /**
     * Construct a new Board with the 2D array being non-null, every Escaque is
     * created with the standard construct, so it'll be buildable, non-magic with
     * the Empty piece and with the given point, starting at (0, 0).
     *
     * @param columns how many columns it has.
     * @param rows    how many rows it has,
     */
    protected AbstractSquareBoard(int columns, int rows) {
        this(new Square(columns, rows));
    }

    /**
     * Construct a new Board with the 2D array being non-null, every Escaque is
     * created with the standard construct, so it'll be buildable, non-magic with
     * the Empty piece and with the given point, starting at (0, 0).
     *
     * @param shape the shape of the board.
     */
    protected AbstractSquareBoard(Square shape) {
        this.shape = shape;
        this.deathPile = new BasicDeathPile();
        this.chessBoard = new Escaque[shape.y][shape.x];
        for (int y = 0; y < shape.y; y++) {
            for (int x = 0; x < shape.x; x++) {
                chessBoard[y][x] = new Escaque(new Point(x, y));
            }
        }
    }

    /**
     * Tries to do an {@code Action}. In case of an action that needs other point,
     * use Point::toInfo.
     *
     * @param action action to do.
     * @param pos    start point.
     * @param info   information about the action.
     * @return FAIL if it didn't do the action, PASS if the action has been done.
     */
    public boolean tryTo(Action action, Point pos, Info info) {
        return tryTo(action, pos, info, false);
    }

    /**
     * Tries to do an {@code Action}. In case of an action that needs other point,
     * use Point::toInfo.
     *
     * @param action action to do.
     * @param pos    start point.
     * @param info   information about the action.
     * @param bypass if bypasses the notification to the clock.
     * @return FAIL if it didn't do the action, PASS if the action has been done.
     */
    public boolean tryTo(Action action, Point pos, Info info, boolean bypass) {
        var piece = getPiece(pos);
        if (!canPlay(piece) && !bypass) { return false; }
        if (action.needsInfoPoint() && !info.isType(Point.class)) {
            throw new IllegalArgumentException("Info no es Info<Point> para " + action + ", es: " + info.getClass());
        }
        // TODO: maybe move to piece the usage of TypeHelper
        boolean can = getPiece(pos).can(action, this, pos, info)
                && (TypeHelper.checkIfTypesCan(action, this, pos, info))
                && (getPiece(pos).getEffectManager().canBe(action, this, pos));
        if (can) {
            switch (action) {
                case ATTACK -> killPiece((Point) info.getValue());
                case MOVE -> {
                    setPiece((Point) info.getValue(), piece);
                    removePieceNoDeath(pos);
                }
                case TAKE -> {
                    setPiece((Point) info.getValue(), piece);
                    killPiece(pos);
                }
                case ABILITY -> getPiece(pos).getAbility().use(this, piece, pos, info);
                default -> throw new IllegalArgumentException("Action not expected");
            }
            piece.postAction(action, this, pos, info);
            getPiece(pos).getEffectManager().onBe(action, this, pos);
            if (!bypass) {movement();}
        }
        return can;
    }

    /**
     * It gets the basic death pile of this board.
     */
    @Override
    public IDeathPile getDeathPile() {
        return deathPile;
    }

    /**
     * It gets the Escaque in the given point.
     *
     * @param point point of the Escaque.
     * @return the Escaque at the point, if it is in the board.
     * @throws IllegalArgumentException if any coordinate is out of the board.
     */
    @Override
    public Escaque getEscaque(Point point) {
        if (this.shape.isOutOfBorders(point)) {
            throw new IllegalArgumentException(
                    "Trying to acces to %s on a %s shape".formatted(point, shape.getDimensions()));
        }
        return chessBoard[point.y][point.x];
    }

    @Override
    public int getSize() {
        return getShape().getArea();
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public void setPiece(Point point, Piece piece) {
        this.getEscaque(point).setPiece(piece);
    }

    @Override
    public Piece getPiece(Point point) {
        return this.getEscaque(point).getPiece();
    }

    @Override
    public void foreach(Consumer<Escaque> action) {
        for (int x = 0; x < this.shape.x; x++) {
            for (int y = 0; y < this.shape.y; y++) {
                action.accept(getEscaque(new Point(x, y)));
            }
        }
    }

    /**
     * Gets a valoration of the pieces, adding the ones that are the positive color
     * and substracting the ones that are the negative color, using the given map.
     * In normal chess, the positive is white and the negative is black.
     *
     * @param positive color that is positive.
     * @param negative color that is negative.
     * @param map      a map that correlates every piece with its value.
     * @return total valoration.
     */
    public int getValoration(Color positive, Color negative, Map<Class<? extends Piece>, Integer> map) {
        int valoration = 0;
        for (Escaque[] escaques : chessBoard) {
            for (Escaque escaque : escaques) {
                if (escaque.getPieceColor().equals(positive) && map.containsKey(escaque.getPiece().getClass())) {
                    valoration += map.get(escaque.getPiece().getClass());
                } else if (escaque.getPieceColor().equals(negative) && map.containsKey(escaque.getPiece().getClass())) {
                    valoration -= map.get(escaque.getPiece().getClass());
                }
            }
        }
        return valoration;
    }

    /**
     * XXX: remove this thing Copies the content from this board to the other.
     * Escaque by escaque.
     *
     * @param other other board.
     */
    public void copyContentTo(AbstractSquareBoard other) {
        if (!this.getShape().equals(other.getShape())) {
            throw new IllegalArgumentException(
                    "Cannot put a %s shape in a %s shape, as they are not equals".formatted(other.shape, this.shape));
        }
        for (Escaque[] escaques : this.chessBoard) {
            for (Escaque escaque : escaques) {
                other.getEscaque(escaque.getPos()).setIsBuildable(escaque.isBuildable());
                other.getEscaque(escaque.getPos()).setIsMagic(escaque.isMagic());
                Piece p = escaque.getPiece();
                Piece p2;
                if (p instanceof Pawn) {
                    p2 = new Pawn(p.getColor());
                } else if (p instanceof Bishop) {
                    p2 = new Bishop(p.getColor());
                } else if (p instanceof Knight) {
                    p2 = new Knight(p.getColor());
                } else if (p instanceof Queen) {
                    p2 = new Queen(p.getColor());
                } else if (p instanceof King) {
                    p2 = new King(p.getColor());
                } else if (p instanceof Rook) {
                    p2 = new Rook(p.getColor());
                } else if (p instanceof Ram) {
                    p2 = new Ram(p.getColor());
                } else if (p instanceof Archer) {
                    p2 = new Archer(p.getColor());
                } else if (p instanceof Ballista) {
                    p2 = new Ballista(p.getColor());
                } else if (p instanceof Warlock) {
                    p2 = new Warlock(p.getColor());
                } else if (p instanceof Catapult) {
                    p2 = new Catapult(p.getColor());
                } else if (p instanceof Cannon) {
                    p2 = new Cannon(p.getColor());
                } else if (p instanceof Builder) {
                    p2 = new Builder(p.getColor());
                } else if (p instanceof ShieldBearer) {
                    p2 = new ShieldBearer(p.getColor());
                } else if (p instanceof Magician) {
                    p2 = new Magician(p.getColor());
                } else if (p instanceof Wall) {
                    p2 = new Wall(p.getColor());
                } else if (p instanceof Ship) {
                    p2 = new Ship(p.getColor());
                } else if (p instanceof Paladin) {
                    p2 = new Paladin(p.getColor());
                } else if (p instanceof CrazyPawn) {
                    p2 = new CrazyPawn(p.getColor());
                } else if (p instanceof SuperPawn) {
                    p2 = new SuperPawn(p.getColor());
                } else if (p instanceof TeslaTower) {
                    p2 = new TeslaTower(p.getColor());
                } else {
                    p2 = new Empty();
                }
                p2.setIsMoved(p.isMoved());
                p2.changeCD(p.getCD());
                other.setPiece(escaque.getPos(), p2);
            }
        }
    }

    /**
     * Traces a line betweem from and the end of the board in the given direction,
     * returning every escaque that is in the way.
     *
     * @param from      from which point is the ray casted
     * @param inclusive whenever the returned list contains or not he starting point
     * @param dir       direction of the ray cast
     */
    public List<Escaque> rayCast(Point from, boolean inclusive, Direction dir) {
        if (this.shape.isOutOfBorders(from)) { return List.of(); }
        List<Escaque> list = new ArrayList<>();
        Point current = from;
        if (inclusive) {
            list.add(getEscaque(current));
        }
        while (!this.shape.isOutOfBorders(current = current.add(dir.toPoint()))) {
            list.add(getEscaque(current));
        }
        return list;
    }

    /**
     * Traces a line between from and the end of the board in the given direction,
     * capped at the given cap, doesn't includes the starting point and can be
     * bloqued by PieceType.IMPENETRABLE. If its bloqued, the last piece will be of
     * the PieceType.IMPENETRABLE type
     *
     * @param from starting pos of the go
     * @param dir  direction of the go
     * @paran cap number of max escaques
     * @paran canBeBlocked whenever this go can be blocked or not.
     *
     * @return the escaques encountered
     */
    public List<Escaque> rayCast(Point from, Direction dir, int cap, boolean canBeBlocked) {
        List<Escaque> ray = rayCast(from, false, dir);
        if (canBeBlocked && ray.stream().anyMatch(this::isImpenetrableAt)) {
            List<Escaque> tmp = new ArrayList<>(ray.size());
            for (Escaque escaque : ray) {
                tmp.add(escaque);
                if (this.isImpenetrableAt(escaque)) {
                    break;
                }
            }
            ray = tmp;
        }

        if (cap == -1 || cap >= ray.size()) {
            return ray;
        } else {
            return ray.subList(0, cap);
        }
    }

    public List<Escaque> rayCast(Point from, Direction dir, int cap) {
        return this.rayCast(from, dir, cap, true);
    }

    public List<Escaque> rayCast(Point from, Direction dir) {
        return this.rayCast(from, dir, -1, true);
    }

    public boolean isImpenetrable(Point position) {
        return getPiece(position).getTypeManager().isType(IPieceType.IMPENETRABLE);
    }

    protected boolean isImpenetrableAt(Escaque escaque) {
        return isImpenetrable(escaque.getPos());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(5 * this.getSize());
        for (int y = shape.y - 1; y >= 0; y--) {
            for (int x = 0; x < shape.x; x++) {
                str.append('[').append(getPiece(new Point(x, y)).toString().charAt(0)).append(']');
            }
            str.append('\n');
        }
        return str.toString();
    }
}
