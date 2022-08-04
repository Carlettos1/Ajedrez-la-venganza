package com.carlettos.game.gameplay.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.effect.EffectManager;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.gameplay.piece.type.TypeManager;
import com.carlettos.game.util.IResourceKey;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.ResourceLocation;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.TypeHelper;

/**
 * It's a piece.
 *
 * @author Carlos
 */
public abstract class Piece implements IResourceKey, IInfo {

    /**
     * It's a convenience value to not move the same piece twice in a turn.
     */
    protected boolean moved;

    /**
     * It's the cooldown of the hability of this piece. When its 0, the ability can
     * be used.
     */
    protected int cooldown;
    protected final String key;
    protected final ResourceLocation name;
    protected Ability ability;
    protected Color color;
    protected final EffectManager effectManager;
    protected final TypeManager typeManager;

    /**
     * General constructor.
     *
     * @param key     key to identify the piece.
     * @param ability ability of the piece.
     * @param types   types of the piece.
     * @param color   color of the piece.
     */
    protected Piece(String key, Ability ability, Color color, IPieceType... types) {
        this.moved = false;
        this.cooldown = 0;
        this.key = key;
        this.ability = ability;
        this.color = color;
        this.name = new ResourceLocation("piece.".concat(key));
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
    public abstract ActionResult can(Action action, AbstractSquareBoard board, Point start, Info info);

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
    public void postAction(Action action, AbstractSquareBoard board, Point pos, Info info) {
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
    public List<Tuple<Action, Info>> getAllActions(AbstractSquareBoard board, Point start) {
        List<Tuple<Action, Info>> actions = new ArrayList<>();
        board.foreach(e -> {
            if (this.can(Action.TAKE, board, start, e.getPos().toInfo()).isPositive()) {
                actions.add(new Tuple<>(Action.TAKE, e.getPos().toInfo()));
            }

            if (this.can(Action.MOVE, board, start, e.getPos().toInfo()).isPositive()) {
                actions.add(new Tuple<>(Action.MOVE, e.getPos().toInfo()));
            }

            if (this.can(Action.ATTACK, board, start, e.getPos().toInfo()).isPositive()) {
                actions.add(new Tuple<>(Action.ATTACK, e.getPos().toInfo()));
            }
        });
        for (Object value : getAbility().getValues(board, start)) {
            if (this.getAbility().canUse(board, this, start, Info.getInfo(value)).isPositive()) {
                actions.add(new Tuple<>(Action.ABILITY, Info.getInfo(value)));
            }
        }
        return actions;
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
    public void tick(AbstractSquareBoard board, Point pos) {
        this.effectManager.tick(board, pos);
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public TypeManager getTypeManager() {
        return typeManager;
    }

    @Override
    public String getBaseKey() {
        return key;
    }

    // TODO: hacer esto con todos?
    public ResourceLocation getName() {
        return name;
    }

    public Ability getAbility() {
        return ability;
    }

    public int getCD() {
        return cooldown;
    }

    public boolean isMoved() {
        return moved;
    }

    @Override
    public Info toInfo() {
        return Info.getInfo(this);
    }

    @Override
    public String toString() {
        return getName().getTranslated();
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