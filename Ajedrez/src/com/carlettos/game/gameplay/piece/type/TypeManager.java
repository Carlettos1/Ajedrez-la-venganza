package com.carlettos.game.gameplay.piece.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TypeManager {
    protected final List<IPieceType> types;

    public TypeManager(IPieceType... types) {
        this.types = new ArrayList<>(List.of(types));
    }

    public boolean isImpenetrable() {
        return this.isType(IPieceType.IMPENETRABLE);
    }

    public boolean isBiologic() {
        return this.isType(IPieceType.BIOLOGIC);
    }

    public boolean isDemonic() {
        return this.isType(IPieceType.DEMONIC);
    }

    public boolean isStructure() {
        return this.isType(IPieceType.STRUCTURE);
    }

    public boolean isHeroic() {
        return this.isType(IPieceType.HEROIC);
    }

    public boolean isImmune() {
        return this.isType(IPieceType.IMMUNE);
    }

    public boolean isTransportable() {
        return this.isType(IPieceType.TRANSPORTABLE);
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
    public boolean addType(IPieceType type) {
        return this.getTypes().add(type);
    }

    /**
     * Removes the type provided from this piece.
     *
     * @param type type to remove.
     * @return FAIL if the piece doesn't contain the type provided, PASS if the
     *         piece has the type and has been removed.
     */
    public boolean removeType(IPieceType type) {
        return this.getTypes().remove(type);
    }

    /**
     * Adds every type provided to this piece.
     *
     * @param types types to add.
     * @return PASS if every type has been added to this piece, FAIL otherwise.
     * @throws NullPointerException if there is any null type provided.
     */
    public boolean addTypes(IPieceType... types) {
        boolean success = true;
        for (IPieceType tipo : types) {
            Objects.requireNonNull(tipo);
            success = Boolean.logicalAnd(success, this.getTypes().add(tipo));
        }
        return (success);
    }

    /**
     * Removes every type provided from this piece.
     *
     * @param types types to remove.
     * @return PASS if every type provided has been removed from this piece, FAIL
     *         otherwise.
     * @throws NullPointerException if there is any null type provided.
     */
    public boolean removeTypes(IPieceType... types) {
        boolean success = true;
        for (IPieceType tipo : types) {
            Objects.requireNonNull(tipo);
            success = Boolean.logicalAnd(success, this.getTypes().remove(tipo));
        }
        return (success);
    }

    public boolean can(PieceTypeData data) {
        boolean combined = true;
        for (IPieceType type : types) {
            combined &= (type.can(data));
        }
        return combined;
    }

    public void on(PieceTypeData data) {
        for (IPieceType type : types) {
            type.on(data);
        }
    }

    public boolean canBe(PieceTypeData data) {
        boolean combined = true;
        for (IPieceType type : types) {
            combined &= (type.canBe(data));
        }
        return combined;
    }

    public void onBe(PieceTypeData data) {
        for (IPieceType type : types) {
            type.onBe(data);
        }
    }
}
