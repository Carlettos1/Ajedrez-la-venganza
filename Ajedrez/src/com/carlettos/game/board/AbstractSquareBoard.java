package com.carlettos.game.board;

import java.util.Map;
import java.util.function.Consumer;

import com.carlettos.game.board.shape.Shape;
import com.carlettos.game.board.shape.Square;
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
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

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
        this.chessBoard = new Escaque[shape.y][shape.x];
        for (int y = 0; y < shape.y; y++) {
            for (int x = 0; x < shape.x; x++) {
                chessBoard[y][x] = new Escaque(new Point(x, y));
            }
        }
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
                other.getEscaque(escaque.getPos()).setPiece(p2);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(5 * this.getSize());
        for (int y = shape.y - 1; y >= 0; y--) {
            for (int x = 0; x < shape.x; x++) {
                str.append('[').append(getEscaque(new Point(x, y)).getPiece().getBaseKey().charAt(0)).append(']');
            }
            str.append('\n');
        }
        return str.toString();
    }
}
