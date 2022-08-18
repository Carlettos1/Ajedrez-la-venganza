package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class King extends SimplePiece {
    // TODO: que no se muera después de comer o moverse
    protected boolean usedTP;

    public King(Color color) {
        super("king", Abilities.KING_ABILITY, color, Patterns.KING_PATTERN, IPieceType.BIOLOGIC, IPieceType.IMMUNE,
                IPieceType.HEROIC);
        this.usedTP = false;
    }

    public boolean hasUsedTP() {
        return usedTP;
    }

    public void setUsedTP(boolean usedTP) {
        this.usedTP = usedTP;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
