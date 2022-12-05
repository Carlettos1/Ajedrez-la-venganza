package com.carlettos.game.board;

import java.util.List;
import java.util.function.Predicate;

import com.carlettos.game.board.deathPile.IDeathPile;
import com.carlettos.game.board.shape.Shape;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public interface IBaseBoard extends List<Escaque> {

    @Override
    Escaque get(int index);

    /**
     * It gets the Escaque in the given point.
     *
     * @param point point of the Escaque.
     * @return the Escaque at the point, if it is in the board.
     * @throws IllegalArgumentException if any coordinate is out of the board.
     */
    Escaque get(Point point);

    Piece getPiece(int index);

    /**
     * Gets the piece in the escaque at the given point.
     *
     * @param point point in the board
     */
    Piece getPiece(Point point);

    List<Escaque> getAll(Predicate<Escaque> condition);

    /**
     * Gets every Escaque that match the given pattern at the given starting point.
     *
     * @param pattern pattern to match.
     * @param start   starting point of the pattern.
     *
     * @return a List containing all the Escaques that matchs the pattern.
     */
    default List<Escaque> getAll(Pattern pattern, Point start) {
        return this.getAll(pattern.toPredicate(this, start));
    }

    List<Piece> getAllPieces(Predicate<Escaque> condition);

    default List<Piece> getAllPieces(Pattern pattern, Point start) {
        return this.getAllPieces(pattern.toPredicate(this, start));
    }

    @Override
    Escaque set(int index, Escaque escaque);

    void set(int index, Piece piece);

    /**
     * Sets the piece in the escaque at the given point.
     *
     * @param point point in the board
     * @param piece piece to put
     */
    void set(Point point, Piece piece);

    void set(Point point, Escaque escaque);

    @Override
    boolean add(Escaque escaque);

    boolean add(Piece piece);

    @Override
    void add(int index, Escaque escaque);

    void add(int index, Piece piece);

    @Override
    Escaque remove(int index);

    @Override
    boolean remove(Object o);

    boolean remove(Escaque e, boolean death);

    boolean remove(Point p, boolean death);

    boolean remove(Piece p, boolean death);

    /**
     * Safely removes the first piece that matches the given predicate (if any). And
     * safe-adds it to the death pile if chosen to.
     */
    boolean remove(Predicate<Escaque> condition, boolean death);

    /**
     * Removes the piece at the given index. And safe-adds it to the death pile only
     * if chosen to. Returns the escaque which was deleted.
     */
    Escaque simpleRemove(int index, boolean death);

    @Override
    boolean contains(Object o);

    boolean contains(Escaque e);

    boolean contains(Point p);

    boolean contains(Piece p);

    @Override
    int indexOf(Object obj);

    int indexOf(Escaque escaque);

    int indexOf(Piece piece);

    int indexOf(Point point);

    /**
     * Gets the index of the first escaque that satisfies the given condition.
     */
    int indexOf(Predicate<Escaque> condition);

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object obj);

    /**
     * Gets the last piece that satisfies the given predicate.
     *
     * @param condition that must be satisfied.
     * @see #lastIndexOf(Object)
     */
    int lastIndexOf(Predicate<Escaque> condition);

    /**
     * Safe method for adding to a death pile; it only adds the piece if its not an
     * empty one.
     *
     * @param piece to add to the death pile.
     */
    default void addToDeathPile(Piece piece) {
        if (!piece.isEmpty()) {
            this.getDeathPile().add(piece);
        }
    }

    /**
     * Gets the death pile of the board.
     *
     * @return the death pile of the board
     */
    IDeathPile getDeathPile();

    /**
     * Gets the shape of the board.
     *
     * @return the shape of the board
     */
    Shape getShape();

    /**
     * Gets the throw place of a piece in the original point being throwed by a
     * piece in the thrower point.
     *
     * @param thrower  thrower piece position
     * @param original original piece position
     *
     * @return the throw position of the original piece
     */
    default Point getThrowPoint(Point thrower, Point original) {
        List<Escaque> closest = this.getAll(Patterns.KING_PATTERN, original);
        closest.remove(this.get(thrower));
        closest.remove(this.get(original));
        closest.sort(
                (o1, o2) -> Double.compare(o2.getPos().getDistanceTo(thrower), o1.getPos().getDistanceTo(thrower)));
        // closest is orderer from farthest to closest from the thrower
        for (Escaque e : closest) {
            if (!e.hasPiece()) { return e.getPos(); }
        }
        return original;
    }
}
