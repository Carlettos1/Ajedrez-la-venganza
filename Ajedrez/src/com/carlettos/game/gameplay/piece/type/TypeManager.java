package com.carlettos.game.gameplay.piece.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.carlettos.game.util.enums.ActionResult;

public class TypeManager {
    protected final List<IPieceType> types;

    public TypeManager(IPieceType... types) {
        this.types = new ArrayList<>(List.of(types));
    }

    public boolean isType(IPieceType type) {
        return this.types.contains(type);
    }

    public List<IPieceType> getTypes() {
        return types;
    }

    /**
     * Adds the type provided to this piece.
     *
     * @param type type to add.
     * @return PASS.
     */
    public ActionResult addType(IPieceType type) {
        return ActionResult.fromBoolean(this.getTypes().add(type));
    }

    /**
     * Removes the type provided from this piece.
     *
     * @param type type to remove.
     * @return FAIL if the piece doesn't contain the type provided, PASS if the
     *         piece has the type and has been removed.
     */
    public ActionResult removeType(IPieceType type) {
        return ActionResult.fromBoolean(this.getTypes().remove(type));
    }

    /**
     * Adds every type provided to this piece.
     *
     * @param types types to add.
     * @return PASS if every type has been added to this piece, FAIL otherwise.
     * @throws NullPointerException if there is any null type provided.
     */
    public ActionResult addTypes(IPieceType... types) {
        boolean success = true;
        for (IPieceType tipo : types) {
            Objects.requireNonNull(tipo);
            success = Boolean.logicalAnd(success, this.getTypes().add(tipo));
        }
        return ActionResult.fromBoolean(success);
    }

    /**
     * Removes every type provided from this piece.
     *
     * @param types types to remove.
     * @return PASS if every type provided has been removed from this piece, FAIL
     *         otherwise.
     * @throws NullPointerException if there is any null type provided.
     */
    public ActionResult removeTypes(IPieceType... types) {
        boolean success = true;
        for (IPieceType tipo : types) {
            Objects.requireNonNull(tipo);
            success = Boolean.logicalAnd(success, this.getTypes().remove(tipo));
        }
        return ActionResult.fromBoolean(success);
    }

    public ActionResult can(PieceTypeData data) {
        ActionResult combined = ActionResult.PASS;
        for (IPieceType type : types) {
            combined = combined.and(type.can(data));
        }
        return combined;
    }

    public void on(PieceTypeData data) {
        for (IPieceType type : types) {
            type.on(data);
        }
    }

    public ActionResult canBe(PieceTypeData data) {
        ActionResult combined = ActionResult.PASS;
        for (IPieceType type : types) {
            combined = combined.and(type.canBe(data));
        }
        return combined;
    }

    public void onBe(PieceTypeData data) {
        for (IPieceType type : types) {
            type.onBe(data);
        }
    }
}
