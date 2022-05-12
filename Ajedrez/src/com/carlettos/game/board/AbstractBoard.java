package com.carlettos.game.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.carlettos.game.gameplay.pattern.Pattern;
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
 * Generic board. It doesn't have a clock and doesn't manage turns. It's just
 * a representation of the current board.
 *
 * @author Carlettos
 */
public abstract class AbstractBoard {
    
    protected final Escaque[][] chessBoard;

    /**
     * Column quantity. It's the X in the cartesian plane.
     */
    public final int columns;

    /**
     * Row quantity. It's the Y in the cartesian plane.
     */
    public final int rows;

    /**
     * Construct a new Board with the 2D array being non-null, every Escaque
     * is created with the standard construct, so it'll be buildable, non-magic
     * with the Empty piece and with the given point, starting at (0, 0).
     * 
     * @param columns how many columns it has.
     * @param rows how many rows it has,
     */
    protected AbstractBoard(int columns, int rows) {
        this.chessBoard = new Escaque[rows][columns];
        this.columns = columns;
        this.rows = rows;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                chessBoard[y][x] = new Escaque(new Point(x, y));
            }
        }
    }

    /**
     * Removes the piece at the given x and y. It replaces the curren piece 
     * with a new Empty piece.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public void removePiece(int x, int y) {
        this.removePiece(new Point(x, y));
    }

    /**
     * Removes the piece at the given point. It replaces the curren piece 
     * with a new Empty piece.
     *
     * @param point position of the piece.
     */
    public void removePiece(Point point) {
        this.getEscaque(point).removePiece();
    }

    /**
     * It gets the Escaque in the given point.
     *
     * @param point point of the Escaque.
     * @return the Escaque at the point, if it is in the board.
     * @throws IllegalArgumentException if any coordinate is out of the board.
     */
    public Escaque getEscaque(Point point) {
        if (point.x < 0) {
            throw new IllegalArgumentException("La coordenada x no puede ser negativa");
        }
        if (point.y < 0) {
            throw new IllegalArgumentException("La coordenada y no puede ser negativa");
        }
        if (point.x >= columns) {
            throw new IllegalArgumentException("La coordenada x no puede ser mayor o igual que el número de columnas");
        }
        if (point.y >= rows) {
            throw new IllegalArgumentException("La coordenada y no puede ser mayor o igual que el número de filas");
        }
        return chessBoard[point.y][point.x];
    }

    /**
     * It gets the Escaque in the given coordinates.
     *
     * @param x coord x of the Escaque.
     * @param y coord y of the Escaque.
     * @return the Escaque at the coords, if it is in the board.
     * @throws IllegalArgumentException if any coordinate is out of the board.
     */
    public Escaque getEscaque(int x, int y) {
        return getEscaque(new Point(x, y));
    }
    
    /**
     * It checks that the given point is in the board.
     *
     * @param point point to check.
     * @return true if is inside the board, false other case.
     */
    public boolean isOutOfBorder(Point point){
        if (point.x < 0) {
            return true;
        }
        if (point.y < 0) {
            return true;
        }
        if (point.x >= columns) {
            return true;        
        }
        return point.y >= rows;
    }

    /**
     * It gets every nearby Escaque, the ones that are above, below, right and
     * left of the given Escaque, if they exist on this board.
     *
     * @param escaque center Escaque.
     * @return a List of max 4 elements, that contains all the escaques nearby 
     * the given one.
     */
    public List<Escaque> getNearbyEscaques(Escaque escaque) {
        List<Escaque> escaques = new ArrayList<>(4);

        int x = escaque.getPos().x;
        int y = escaque.getPos().y;

        int tmp;
        if ((tmp = x + 1) < columns) {
            escaques.add(this.getEscaque(tmp, y));
        }
        if ((tmp = x - 1) >= 0) {
            escaques.add(this.getEscaque(tmp, y));
        }
        if ((tmp = y + 1) < rows) {
            escaques.add(this.getEscaque(x, tmp));
        }
        if ((tmp = y - 1) >= 0) {
            escaques.add(this.getEscaque(x, tmp));
        }

        return escaques;
    }
    
    /**
     * Gets every Escaque that match the given pattern at the given starting 
     * point.
     *
     * @param pattern pattern to match.
     * @param start starting point of the pattern.
     * @return a List containing all the Escaques that matchs the pattern.
     */
    public List<Escaque> getMatchingEscaques(Pattern pattern, Point start){
        List<Escaque> matches = new ArrayList<>(rows * columns);
        for (Escaque[] escaques : chessBoard) {
            for (Escaque escaque : escaques) {
                if(pattern.match(this, start, escaque.getPos())){
                    matches.add(escaque);
                }
            }
        }
        return matches;
    }
    
    /**
     * Gets a valoration of the pieces, adding the ones that are the positive 
     * color and substracting the ones that are the negative color, using the 
     * given map. In normal chess, the positive is white and the negative is 
     * black.
     *
     * @param positive color that is positive.
     * @param negative color that is negative.
     * @param map a map that correlates every piece with its value.
     * @return total valoration.
     */
    public int getValoration(Color positive, Color negative, Map<Class<? extends Piece>, Integer> map){
        int valoration = 0;
        for (Escaque[] escaques : chessBoard) {
            for (Escaque escaque : escaques) {
                if (escaque.getPieceColor().equals(positive) && map.containsKey(escaque.getPiece().getClass())) {
                    valoration += map.get(escaque.getPiece().getClass());
                } else if(escaque.getPieceColor().equals(negative) && map.containsKey(escaque.getPiece().getClass())) {
                    valoration -= map.get(escaque.getPiece().getClass());
                }
            }
        }
        return valoration;
    }
    
    /**
     * Copies the content from this board to the other. Escaque by escaque.
     *
     * @param other other board.
     */
    public void copyContentTo(AbstractBoard other){
        if(this.columns != other.columns || this.rows != other.rows){
            throw new IllegalArgumentException("No se puede copiar contenido a un tablero de otras dimensiones");
        }
        for (Escaque[] escaques : this.chessBoard) {
            for (Escaque escaque : escaques) {
                other.getEscaque(escaque.getPos()).setIsBuildable(escaque.isBuildable());
                other.getEscaque(escaque.getPos()).setIsMagic(escaque.isMagic());
                Piece pieza = escaque.getPiece();
                Piece p2 = (switch (pieza) {
                    case Pawn p -> new Pawn(p.getColor());
                    case Bishop p -> new Bishop(p.getColor());
                    case Knight p -> new Knight(p.getColor());
                    case Queen p -> new Queen(p.getColor());
                    case King p -> new King(p.getColor());
                    case Rook p -> new Rook(p.getColor());
                    case Ram p -> new Ram(p.getColor());
                    case Archer p -> new Archer(p.getColor());
                    case Ballista p -> new Ballista(p.getColor());
                    case Warlock p -> new Warlock(p.getColor());
                    case Catapult p -> new Catapult(p.getColor());
                    case Cannon p -> new Cannon(p.getColor());
                    case Builder p -> new Builder(p.getColor());
                    case ShieldBearer p -> new ShieldBearer(p.getColor());
                    case Magician p -> new Magician(p.getColor());
                    case Wall p -> new Wall(p.getColor());
                    case Ship p -> new Ship(p.getColor());
                    case Paladin p -> new Paladin(p.getColor());
                    case CrazyPawn p -> new CrazyPawn(p.getColor());
                    case SuperPawn p -> new SuperPawn(p.getColor());
                    case TeslaTower p -> new TeslaTower(p.getColor());
                    default -> new Empty();
                });
                p2.setIsMoved(pieza.isMoved());
                p2.changeCD(pieza.getCD());
                other.getEscaque(escaque.getPos()).setPiece(p2);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(5 * columns * rows);
        for (int y = rows - 1; y >= 0; y--) {
            for (int x = 0; x < columns; x++) {
                str.append('[')
                        .append(getEscaque(x, y).getPiece().getBaseKey().charAt(0))
                        .append(']');
            }
            str.append('\n');
        }
        return str.toString();
    }
}
