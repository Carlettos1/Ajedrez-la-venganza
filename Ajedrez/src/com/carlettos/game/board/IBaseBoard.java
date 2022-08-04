package com.carlettos.game.board;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.carlettos.game.board.deathPile.IDeathPile;
import com.carlettos.game.board.shape.Shape;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction;

public interface IBaseBoard {

    /**
     * It gets the size of the board, it can be the area of the shape, but its not
     * necessary.
     */
    int getSize();

    /**
     * It gets the Escaque in the given point.
     *
     * @param point point of the Escaque.
     * @return the Escaque at the point, if it is in the board.
     * @throws IllegalArgumentException if any coordinate is out of the board.
     */
    Escaque getEscaque(Point point);

    /**
     * Sets the piece in the escaque at the given point.
     *
     * @param point point in the board
     * @param piece piece to put
     */
    void setPiece(Point point, Piece piece);

    /**
     * Removes the piece at the given point. It replaces the current piece with a
     * new Empty piece.
     * 
     * Doesn't add the piece to the death pile.
     *
     * @param point position of the piece.
     */
    default void removePieceNoDeath(Point point) {
        this.getEscaque(point).removePiece();
    }

    /**
     * Removes the piece at the given point. It replaces the current piece with a
     * new Empty piece.
     * 
     * Adds the piece to the death pile.
     *
     * @param point position of the piece.
     */
    default void killPiece(Point point) {
        this.getDeathPile().add(this.getPiece(point));
        this.getEscaque(point).removePiece();
    }
    
    IDeathPile getDeathPile();

    /**
     * Gets the piece in the escaque at the given point.
     *
     * @param point point in the board
     */
    Piece getPiece(Point point);

    /**
     * Gets the shape of the board.
     */
    Shape getShape();

    /**
     * It gets every nearby Escaque, the ones that are above, below, right and left
     * of the given Escaque, if they exist on this board.
     *
     * @param escaque center Escaque.
     * @return a List of max 4 elements, that contains all the escaques nearby the
     *         given one.
     */
    default List<Escaque> getNearbyEscaques(Escaque escaque) {
        List<Escaque> escaques = new ArrayList<>(4);
        for (Direction dir : Direction.values()) {
            Point tmp;
            if (!this.getShape().isOutOfBorders(tmp = escaque.getPos().add(dir.toPoint()))) {
                escaques.add(this.getEscaque(tmp));
            }
        }
        return escaques;
    }

    /**
     * Executes the action for each one of the escaques in this board.
     */
    void foreach(Consumer<Escaque> action);

    /**
     * Gets every Escaque that match the given pattern at the given starting point.
     *
     * @param pattern pattern to match.
     * @param start   starting point of the pattern.
     * @return a List containing all the Escaques that matchs the pattern.
     */
    default List<Escaque> getMatchingEscaques(Pattern pattern, Point start) {
        List<Escaque> matches = new ArrayList<>(this.getSize());
        this.foreach(e -> {
            if (pattern.match(this, start, e.getPos()))
                matches.add(e);
        });
        return matches;
    }
}
