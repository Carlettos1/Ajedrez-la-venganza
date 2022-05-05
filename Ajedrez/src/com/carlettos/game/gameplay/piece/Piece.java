package com.carlettos.game.gameplay.piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.ResourceLocation;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 * It's a piece.
 *
 * @author Carlos
 */
public abstract class Piece {

    /**
     * It's a convenience value to not move the same piece twice in a turn.
     */
    protected boolean moved;

    /**
     * It's the cooldown of the hability of this piece. When its 0, the ability
     * can be used.
     */
    protected int cooldown;

    protected ResourceLocation name;
    public final ResourceLocation notation;
    protected Ability ability;
    protected Color color;
    protected final List<PieceType> types;
    
    /**
     * General constructor.
     *
     * @param name name of the piece.
     * @param notation notation of the piece. In normal chess pieces, it would 
     * be something like P for Pawn, R for Rook, etc.
     * @param ability ability of the piece.
     * @param types types of the piece.
     * @param color color of the piece.
     */
    protected Piece(String name, String notation, Ability ability, Color color, PieceType... types) {
        this.moved = false;
        this.cooldown = 0;
        this.name = new ResourceLocation("piece." + name);
        //todo: remover notation
        this.notation = new ResourceLocation("notation." + notation);
        this.ability = ability;
        this.color = color;
        this.types = Arrays.asList(types);
    }

    /**
     * Its says when the given action can be executed.
     *
     * @param action action to do.
     * @param board board in which the action is trying to occur.
     * @param start point of the piece in the board.
     * @param info info of the action to do, generally it would be an 
     * {@literal Info<Point>} for every action except the ability, which can be
     * any other Info.
     *
     * @return PASS if the action can be performed, FAIL otherwise.
     */
    public abstract ActionResult can(Action action, AbstractBoard board, Point start, Info info);
    
    /**
     * Its excecuted after an action has been performed. Usually its just used
     * to set the moved value to true of this piece, but can be used to other
     * things.
     *
     * @param action action which has been performed.
     * @param board board in which the action happen.
     * @param start starting point of the piece before the action happen.
     * @param info info of the excecuted action.
     * @see Piece#can(Action, AbstractBoard, Point, Info).
     */
    public void postAction(Action action, AbstractBoard board, Point start, Info info){
        this.setIsMoved(true);
    }
    
    /**
     * Returns every action that can be performed by this piece in the given
     * board in the given starting point, with the added info to the action.
     *
     * @param board Board in which the piece is in it.
     * @param start current point of the piece.
     * @return A list with a tuple containing every action-info that can be 
     * performed by this piece.
     */
    public List<Tuple<Action, Info>> getAllActions(AbstractBoard board, Point start) {
        List<Tuple<Action, Info>> actions = new ArrayList<>();
        for (int x = 0; x < board.columns; x++) {
            for (int y = 0; y < board.rows; y++) {
                if (this.can(Action.TAKE, board, start, board.getEscaque(x, y).getPos().toInfo()).isPositive()) {
                    actions.add(new Tuple<>(Action.TAKE, board.getEscaque(x, y).getPos().toInfo()));
                } 
                
                if (this.can(Action.MOVE, board, start, board.getEscaque(x, y).getPos().toInfo()).isPositive()) {
                    actions.add(new Tuple<>(Action.MOVE, board.getEscaque(x, y).getPos().toInfo()));
                } 
                
                if (this.can(Action.ATTACK, board, start, board.getEscaque(x, y).getPos().toInfo()).isPositive()) {
                    actions.add(new Tuple<>(Action.ATTACK, board.getEscaque(x, y).getPos().toInfo()));
                }
            }
        }
        for (Object value : getAbility().getValues(board, start)) {
            if (this.getAbility().canUse(board, this, start, Info.getInfo(value)).isPositive()) {
                actions.add(new Tuple<>(Action.ABILITY, Info.getInfo(value)));
            }
        }
        return actions;
    }
    
    public boolean isType(PieceType type){
        return this.types.contains(type);
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

    public List<PieceType> getTypes() {
        return types;
    }

    /**
     * Adds the type provided to this piece.
     * @param type type to add.
     * @return PASS.
     */
    public ActionResult addType(PieceType type) {
        return ActionResult.fromBoolean(this.getTypes().add(type));
    }

    
    /**
     * Removes the type provided from this piece.
     * @param type type to remove.
     * @return FAIL if the piece doesn't contain the type provided, PASS 
     * if the piece has the type and has been removed.
     */
    public ActionResult removeType(PieceType type) {
        return ActionResult.fromBoolean(this.getTypes().remove(type));
    }

    /**
     * Adds every type provided to this piece.
     * @param types types to add.
     * @return PASS if every type has been added to this piece, FAIL otherwise.
     * @throws NullPointerException if there is any null type provided.
     */
    public ActionResult addTypes(PieceType... types) {
        boolean success = true;
        for (PieceType tipo : types) {
            Objects.requireNonNull(tipo);
            success = Boolean.logicalAnd(success, this.getTypes().add(tipo));
        }
        return ActionResult.fromBoolean(success);
    }

    /**
     * Removes every type provided from this piece.
     * @param types types to remove.
     * @return PASS if every type provided has been removed from this piece,
     * FAIL otherwise.
     * @throws NullPointerException if there is any null type provided.
     */
    public ActionResult removeTypes(PieceType... types) {
        boolean success = true;
        for (PieceType tipo : types) {
            Objects.requireNonNull(tipo);
            success = Boolean.logicalAnd(success, this.getTypes().remove(tipo));
        }
        return ActionResult.fromBoolean(success);
    }
    
    public ResourceLocation getResourceLocation() {
        return name;
    }

    public String getName() {
        return name.getTranslated();
    }
    
    public String getNotation() {
        return notation.getTranslated();
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
    
    public Info toInfo(){
        return Info.getInfo(this);
    }

    @Override
    public String toString() {
        return getName();
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Piece)) {
            return false;
        }
        final Piece other = (Piece) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        if(this.color == Color.GRAY || other.color == Color.GRAY){
            return true;
        }
        return other.color.equals(this.color);
    }
}