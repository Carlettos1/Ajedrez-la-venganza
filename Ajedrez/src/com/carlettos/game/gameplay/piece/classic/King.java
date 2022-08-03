package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.classic.PatternKing;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

public class King extends SimplePiece<PatternKing> {
    // TODO: que no se muera después de comer o moverse
    protected boolean usedTP;

    public King(Color color) {
        super("king", Abilities.KING_ABILITY, color, Patterns.KING_PATTERN, PieceType.BIOLOGIC, PieceType.IMMUNE,
                PieceType.HEROIC);
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
