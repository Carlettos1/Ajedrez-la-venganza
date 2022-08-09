package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;

public class ImpAbility extends Ability {
    public static final Pattern IMMUNITY_PATTERN = Patterns.KING_PATTERN;
    public ImpAbility() {
        super("imp", 7, 3);
    }
    
    static {
        Info.register(ImpAbilityType.class);
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        return this.commonCanUse(board, piece) && info.isType(ImpAbilityType.class);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        switch ((ImpAbilityType)info.getValue()) {
            case IMMUNITY -> board.getMatchingEscaques(IMMUNITY_PATTERN, start).forEach(e -> e.getPiece().getTypeManager().addType(IPieceType.IMMUNE));
            case TAKE_CARD -> board.getClock().takeFromCentralDeck(board.getClock().turnOf());
        }
        this.commonUse(board, piece);
    }

    @Override
    public ImpAbilityType[] getValues(AbstractSquareBoard board, Point start) {
        return ImpAbilityType.values();
    }
    
    public static enum ImpAbilityType implements IInfo {
        IMMUNITY, TAKE_CARD;

        @Override
        public Info toInfo() {
            return Info.getInfo(this);
        }
    }
}
