package com.carlettos.game.gameplay.piece;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.effect.EffectManager;
import com.carlettos.game.gameplay.pattern.action.IAttack;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.gameplay.piece.type.TypeManager;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.TypeHelper;
import com.carlettos.game.util.resource.IImageable;
import com.carlettos.game.util.resource.ITranslatable;
import com.carlettos.game.util.resource.ImageResource;
import com.carlettos.game.util.resource.TranslateResource;

/**
 * It's a piece.
 *
 * @author Carlos
 */
public abstract class Piece implements IImageable, ITranslatable, IInfo {

    /**
     * It's a convenience value to not move the same piece twice in a turn.
     */
    protected boolean moved;

    /**
     * It's the cooldown of the ability of this piece. When its 0, the ability can
     * be used.
     */
    protected int cooldown;
    protected final String name;
    protected Ability<?> ability;
    protected Color color;
    protected final ImageResource imageResource;
    protected final TranslateResource translateResource;
    protected final EffectManager effectManager;
    protected final TypeManager typeManager;

    /**
     * General constructor.
     *
     * @param name    key to identify the piece.
     * @param ability ability of the piece.
     * @param color   color of the piece.
     * @param types   types of the piece.
     */
    protected Piece(String name, Ability<?> ability, Color color, IPieceType... types) {
        this.moved = false;
        this.cooldown = 0;
        this.name = name;
        this.ability = ability;
        this.color = color;
        this.imageResource = new ImageResource("piece", name);
        this.translateResource = new TranslateResource("piece", name);
        this.typeManager = new TypeManager(types);
        this.effectManager = new EffectManager(this);
    }

    /**
     * Its says when the given action can be executed.
     *
     * @param action action to do.
     * @param board  board in which the action is trying to occur.
     * @param start  point of the piece in the board.
     * @param info   info of the action to do, generally it would be an
     *               {@literal Info<Point>} for every action except the ability,
     *               which can be any other Info.
     *
     * @return PASS if the action can be performed, FAIL otherwise.
     */
    public boolean can(Action action, AbstractBoard board, Point start, Info info) {
        boolean result = false;
        switch (action) {
            case MOVE -> {
                if (this instanceof IMove move) {
                    result = move.canMove(board, start, info, move.getMovePattern(board, start));
                }
            }
            case TAKE -> {
                if (this instanceof ITake take) {
                    result = take.canTake(board, start, info, take.getTakePattern(board, start));
                }
            }
            case ATTACK -> {
                if (this instanceof IAttack attack) {
                    result = attack.canAttack(board, start, info, attack.getAttackPattern(board, start));
                }
            }
            case ABILITY -> result = this.getAbility().canUse(board, start, info);
        }
        return result;
    }

    /**
     * Its excecuted after an action has been performed. Usually its just used to
     * set the moved value to true of this piece, but can be used to other things.
     *
     * @param action action which has been performed.
     * @param board  board in which the action happen.
     * @param pos    starting point of the piece before the action happen.
     * @param info   info of the excecuted action.
     * @see Piece#can(Action, AbstractBoard, Point, Info).
     */
    public void postAction(Action action, AbstractBoard board, Point pos, Info info) {
        TypeHelper.ActivateTypesOnAction(action, board, pos, info);
        this.setIsMoved(true);
    }

    /**
     * Returns every action that can be performed by this piece in the given board
     * in the given starting point, with the added info to the action.
     *
     * @param board Board in which the piece is in it.
     * @param start current point of the piece.
     * @return A list with a tuple containing every action-info that can be
     *         performed by this piece.
     */
    public List<Tuple<Action, Info>> getAllMovements(AbstractBoard board, Point start) {
        List<Tuple<Action, Info>> movements = new ArrayList<>();
        Action[] actions = { Action.MOVE, Action.ATTACK, Action.TAKE };
        board.stream().forEach(e -> {
            Info info = e.getPos().toInfo();
            for (Action action : actions) {
                if (board.canPiece(action, this, start, info)) {
                    movements.add(Tuple.of(action, info));
                }
            }
        });
        movements.addAll(this.getAbility().getValues(board, start).stream()
                .map(i -> Tuple.of(Action.ABILITY, i.toInfo())).toList());
        return movements;
    }

    /**
     * Adds to the cooldown the given value. It can be negative.
     *
     * @param cooldown number to add to the cooldown. It can be negative.
     */
    public void changeCD(int cooldown) {
        if (this.cooldown + cooldown < 0) {
            this.cooldown = 0;
        } else {
            this.cooldown += cooldown;
        }
    }

    public void setIsMoved(boolean moved) {
        this.moved = moved;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Executed at the end of every turn.
     */
    public final void tick(AbstractBoard board, Point pos) {
        this.setIsMoved(false);
        this.effectManager.tick(board, pos);
        this.innerTick(board, pos);
    }

    protected void innerTick(AbstractBoard board, Point pos) {}

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public TypeManager getTypeManager() {
        return typeManager;
    }

    public Ability<?> getAbility() {
        return ability;
    }

    public int getCD() {
        return cooldown;
    }

    public boolean isMoved() {
        return moved;
    }

    /**
     * Utility method. It must return the same as
     * {@code (new Empty()).equals(this)}.
     *
     * @see Empty
     * @implNote It must be false for every piece, except for the Empty-like pieces,
     *           e.g., true for every piece that is a place holder.
     */
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return this.getTranslated();
    }

    @Override
    public BufferedImage getImage() {
        return imageResource.getImage(this.color);
    }

    @Override
    public String getTranslated() {
        return translateResource.getTranslated();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hash(this.color);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if ((obj == null) || !(obj instanceof Piece)) { return false; }
        final Piece other = (Piece) obj;
        if (!this.name.equals(other.name)) { return false; }
        if (this.color == Color.GRAY || other.color == Color.GRAY) { return true; }
        return other.color.equals(this.color);
    }
}