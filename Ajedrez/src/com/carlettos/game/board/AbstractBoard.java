package com.carlettos.game.board;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import com.carlettos.game.board.clock.AbstractClock;
import com.carlettos.game.board.deathPile.IDeathPile;
import com.carlettos.game.board.shape.Shape;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.NoInfoAbility.NoInfo;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.effect.EffectManager;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.helper.LogHelper;

public abstract class AbstractBoard extends AbstractList<Escaque> implements IClockUse, IBaseBoard {

    /**
     * Every escaque that is managed by this board, ordered by it's y coordinate and
     * then by it's x coord.
     */
    protected final Escaque[] chessBoard;

    /**
     * The shape of the board.
     */
    protected final Shape shape;

    /**
     * The death pile of the board, used to revive dead pieces.
     */
    protected final IDeathPile deathPile;

    /**
     * The clock that manages the turns of the players that play in this board. Also
     * manages other thigs like decks on the board, hands, player ones and events.
     */
    protected final AbstractClock clock;

    protected AbstractBoard(Shape shape, IDeathPile deathPile, AbstractClock clock) {
        this.shape = shape;
        this.deathPile = deathPile;
        this.clock = clock;
        this.chessBoard = new Escaque[shape.area()];
        for (int i = 0; i < shape.area(); i++) {
            this.chessBoard[i] = new Escaque(shape.getAllPointsInside()[i]);
        }
    }

    @Override
    public int size() {
        return this.getShape().area();
    }

    @Override
    public boolean isEmpty() {
        for (Escaque escaque : this.chessBoard) {
            if (escaque.hasPiece()) { return false; }
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean contains(Object o) {
        LogHelper.LOG.info("Using deprecated AbstractCollection method, it should be avoided");
        return this.contains((Escaque) o);
    }

    @Override
    public boolean contains(Escaque e) {
        return this.indexOf(e) != -1;
    }

    @Override
    public boolean contains(Point p) {
        return this.getShape().contains(p);
    }

    @Override
    public boolean contains(Piece p) {
        return this.indexOf(p) != -1;
    }

    @Override
    public Escaque[] toArray() {
        return Arrays.copyOf(this.chessBoard, this.size());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size())
            return (T[]) Arrays.copyOf(this.chessBoard, size(), a.getClass());
        System.arraycopy(this.chessBoard, 0, a, 0, size());
        if (a.length > size())
            a[size()] = null;
        return a;
    }

    /**
     * Adds all the properties from the given escaque to the first escaque that has
     * no piece.
     *
     * {@inheritDoc}
     */
    @Override
    public boolean add(Escaque escaque) {
        this.add(this.indexOf(escaque), escaque);
        return true;
    }

    /**
     * Adds the given piece to the first empty space.
     */
    @Override
    public boolean add(Piece piece) {
        this.add(this.indexOf(piece), piece);
        return true;
    }

    @Override
    public void add(int index, Escaque escaque) {
        this.set(index, escaque);
    }

    @Override
    public void add(int index, Piece piece) {
        this.set(index, piece);
    }

    /**
     * Removes the piece in the given escaque and adds it to the death pile.
     *
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public boolean remove(Object o) {
        LogHelper.LOG.info("Using deprecated AbstractCollection method, it should be avoided");
        return this.remove((Escaque) o, true);
    }

    /**
     * Removes the piece in the given index and adds it to the death pile.
     *
     * {@inheritDoc}
     */
    @Override
    public Escaque remove(int index) {
        return this.simpleRemove(index, true);
    }

    @Override
    public boolean remove(Escaque e, boolean death) {
        return this.remove(t -> t.equals(e), death);
    }

    @Override
    public boolean remove(Point p, boolean death) {
        return this.remove(t -> t.getPos().equals(p), death);
    }

    @Override
    public boolean remove(Piece p, boolean death) {
        return this.remove(t -> t.getPiece() == p, death);
    }

    @Override
    public boolean remove(Predicate<Escaque> condition, boolean death) {
        int index = this.indexOf(condition);
        if (index == -1) {
            return false;
        } else {
            this.simpleRemove(index, death);
            return true;
        }
    }

    @Override
    public Escaque simpleRemove(int index, boolean death) {
        if (death) {
            this.get(index).getPiece().onDeath(this);
            this.addToDeathPile(this.get(index).getPiece());
        }
        this.get(index).removePiece();
        return this.get(index);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (var o : c) {
            if (this.remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (var e : this.chessBoard) {
            if (!c.contains(e)) {
                e.removeProperties();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        LogHelper.LOG.warning("Clearing the board %s" + Arrays.toString(chessBoard));
        for (Escaque escaque : chessBoard) {
            escaque.removeProperties();
        }
    }

    @Override
    public Escaque get(int index) {
        return this.chessBoard[index];
    }

    @Override
    public Escaque get(Point point) {
        return this.get(this.indexOf(point));
    }

    @Override
    public List<Escaque> getAll(Predicate<Escaque> condition) {
        ArrayList<Escaque> matches = new ArrayList<>(this.size());
        for (var e : this.chessBoard) {
            if (condition.test(e)) {
                matches.add(e);
            }
        }
        matches.trimToSize();
        return matches;
    }

    @Override
    public Piece getPiece(int index) {
        return this.get(index).getPiece();
    }

    @Override
    public Piece getPiece(Point point) {
        return this.get(this.indexOf(point)).getPiece();
    }

    @Override
    public List<Piece> getAllPieces(Predicate<Escaque> condition) {
        return this.getAll(condition).stream().map(e -> e.getPiece()).toList();
    }

    @Override
    public Escaque set(int index, Escaque escaque) {
        this.get(index).copyProperties(escaque);
        return escaque;
    }

    @Override
    public void set(int index, Piece piece) {
        this.get(index).setPiece(piece);
    }

    @Override
    public void set(Point point, Escaque escaque) {
        this.set(this.indexOf(point), escaque);
    }

    @Override
    public void set(Point point, Piece piece) {
        this.set(this.indexOf(point), piece);
    }

    @Override
    @Deprecated
    public int indexOf(Object obj) {
        LogHelper.LOG.info("Using deprecated AbstractCollection method, it should be avoided");
        return this.indexOf((Escaque) obj);
    }

    @Override
    public int indexOf(Escaque escaque) {
        return this.indexOf(e -> e.equals(escaque));
    }

    @Override
    public int indexOf(Piece piece) {
        return this.indexOf(e -> e.getPiece().equals(piece));
    }

    @Override
    public int indexOf(Point point) {
        return this.indexOf(e -> e.getPos().equals(point));
    }

    @Override
    public int indexOf(Predicate<Escaque> condition) {
        for (int i = 0; i < this.chessBoard.length; i++) {
            Escaque escaque = this.get(i);
            if (condition.test(escaque)) { return i; }
        }
        return -1;
    }

    @Override
    @Deprecated
    public int lastIndexOf(Object obj) {
        LogHelper.LOG.info("Using deprecated AbstractCollection method, it should be avoided");
        return this.lastIndexOf(t -> t.equals(obj));
    }

    @Override
    public int lastIndexOf(Predicate<Escaque> condition) {
        for (int i = this.chessBoard.length; i >= 0; i--) {
            Escaque escaque = this.get(i);
            if (condition.test(escaque)) { return i; }
        }
        return -1;
    }

    @Override
    public IDeathPile getDeathPile() {
        return this.deathPile;
    }

    @Override
    public Shape getShape() {
        return this.shape;
    }

    @Override
    public AbstractClock getClock() {
        return this.clock;
    }

    @Override
    public void tick() {
        this.stream().forEach(e -> e.getPiece().tick(this, e.getPos()));
        this.getClock().tick();
    }

    /**
     * Shorthand to not bypass the clock.
     *
     * @param action action to do.
     * @param pos    point of the piece that is making the action.
     * @param info   information about the action.
     * @return true if the action has been done, false other case.
     * @see #tryTo(Action, Point, Info, boolean)
     */
    public synchronized boolean tryTo(Action action, Point pos, Info info) {
        return tryTo(action, pos, info, false);
    }

    /**
     * Tries to do an {@link Action}. An info argument is passed so it can be used
     * for every action. {@link Action#MOVE}, {@link Action#ATTACK} and
     * {@link Action#TAKE} uses a point info {@link Point#toInfo()}.
     * {@link Action#ABILITY} can use any other info, i.e., {@link NoInfo} or
     * {@link Piece}. Any info that may be used must extend {@link IInfo} and be
     * registered using {@link Info#register(Class)}. If an info needs 2 or more
     * params, {@link Tuple#of(Object, Object)} it may be used plain, or in a tree
     * of Tuples.
     *
     * <p>
     * Checks whenever the piece in the starting position can play. After that, if
     * the given info matches the requirements of the given action is explicitly for
     * {@link Action#MOVE}, {@link Action#ATTACK} and {@link Action#TAKE}, as they
     * always needs an Point info. In case of {@link Action#ABILITY} the
     * {@link Ability} of the piece must check if the given info is enough or not.
     *
     * <p>
     * Then, it checks 1. if the piece can make the action
     * {@link Piece#can(Action, AbstractSquareBoard, Point, Info)} (here is when the
     * {@link Ability} checks the info); 2. if the piece types can make the action
     * and if the piece at the info point (if any) can recieve the action 3. if the
     * piece effects at the info point can be actioned
     * ({@link EffectManager#canBe(Action, AbstractSquareBoard, Point)}).
     *
     * <p>
     * If all the conditions above are satisfied, then makes the action. After the
     * action is done, excecutes
     * {@link Piece#onAction(Action, AbstractSquareBoard, Point, Info)} and
     * {@link EffectManager#onBe(Action, AbstractSquareBoard, Point)}. If the clock
     * is not bypassed, also excecutes {@link #movement()}
     *
     *
     * @param action action to do.
     * @param pos    point of the piece that is making the action.
     * @param info   information about the action.
     * @param bypass if bypasses the notification and some of the turn
     *               functionalities of the clock.
     * @return true if the action has been done, false other case.
     */
    public synchronized boolean tryTo(Action action, Point pos, Info info, boolean bypass) {
        if (!this.contains(pos)) { return false; }
        var piece = this.getPiece(pos);
        if (!this.canPlay(piece) && !bypass) { return false; }

        if (action.needsInfoPoint() && !info.isType(Point.class)) {
            LogHelper.LOG.severe("Info needs to be info Point for " + action + ", and it's: " + info.getValue()
                    + " of class" + info.getValue().getClass());
            return false;
        }
        boolean can = piece.canAction(action, this, pos, info);
        if (can) {
            piece.onAction(action, this, pos, info);
            action.actuate(this, pos, info);
            if (!bypass) {
                this.movement();
            }
        }
        return can;
    }

    /**
     * Gets all the escaques that are between the point provided and the end of the
     * board. It doesn't goes throught a preestablish direction, instead uses a
     * {@link Function} to get the next escaque, so it can be a direction, a
     * secuence, or an advanced pattern (like an spiral). It stops whenever the next
     * escaque is not included by this board or whenever the condition is met.
     *
     * <p>
     * The condition is a {@link Predicate} that serves as a way to stop early the
     * ray. The last escaque that passes the condition may or not be included, it
     * depends on the value of the given boolean.
     *
     * <p>
     * The max paramenter is the max number of iterations that will occur in this
     * cast. It's the max size that the returning list will be. It can be -1, in
     * that case there will not be a max number of iterations, and the cast may go
     * forever.
     *
     * @param from      {@link Point} in which will start the ray.
     * @param max       number of max iterations that will be performed, it can be
     *                  -1 to set no limit.
     * @param inclusive whenever the last escaque hitted will be included in the
     *                  return value.
     * @param function  function to get the next escaque, starting from from.
     * @param condition aditional condition, whenever is met, the ray will stop.
     *
     * @return a trimmed {@link ArrayList} with all the escaques that were hit by
     *         this rayCast
     *
     * @author Carlettos
     */
    public List<Escaque> rayCast(Point from, int max, boolean inclusive, Function<Point, Point> function,
            Predicate<Escaque> condition) {
        if (!this.contains(from)) { return List.of(); }
        ArrayList<Escaque> ray = new ArrayList<>(max == -1 ? this.size() : max);
        Escaque current = this.get(from);
        do {
            Point next = function.apply(current.getPos());
            if (!this.contains(next)) {
                break;
            }
            ray.add(this.get(next));
            current = this.get(next);
        } while (!condition.test(current) && --max != -1);
        if (!inclusive && !ray.isEmpty()) {
            ray.remove(ray.size() - 1);
        }
        ray.trimToSize();
        return ray;
    }

    // FIXME: sometimes the inclusive is not working as expected. if you want to
    // stay in fron of a piece, you could end up 1 escaque away of the border of the
    // board.
    public List<Escaque> rayCast(Point from, int max, boolean inclusive, Direction dir, Predicate<Escaque> condition) {
        return this.rayCast(from, max, inclusive, e -> e.add(dir.toPoint()), condition);
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
    public Map<Color, Integer> getValoration(Map<Class<? extends Piece>, Integer> map) {
        Map<Color, Integer> valoration = new EnumMap<>(Color.class);
        this.stream().forEach(e -> {
            if (map.containsKey(e.getPiece().getClass())) {
                Color color = e.getPieceColor();
                int val = map.get(e.getPiece().getClass());
                if (valoration.containsKey(color)) {
                    val += valoration.get(color);
                }
                valoration.put(color, val);
            }
        });
        return valoration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(chessBoard);
        result = prime * result + Objects.hash(deathPile, shape);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || (getClass() != obj.getClass()))
            return false;
        AbstractBoard other = (AbstractBoard) obj;
        return Arrays.equals(chessBoard, other.chessBoard) && Objects.equals(deathPile, other.deathPile)
                && Objects.equals(shape, other.shape);
    }

    @Override
    public String toString() {
        return "AbstractBoard [chessBoard=" + Arrays.deepToString(chessBoard) + ",\n shape=" + shape + ",\n deathPile="
                + deathPile + "]";
    }
}
