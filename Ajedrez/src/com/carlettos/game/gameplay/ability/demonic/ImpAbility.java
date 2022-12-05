package com.carlettos.game.gameplay.ability.demonic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;

public class ImpAbility extends Ability<ImpAbility.ImpAbilityType> {
    public static final Pattern IMMUNITY_PATTERN = Patterns.KING_PATTERN;

    public ImpAbility() {
        super("imp", Time.lap(7), 3);
    }

    static {}

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        switch ((ImpAbilityType) info.getValue()) {
            case IMMUNITY -> board.getAll(IMMUNITY_PATTERN, start)
                    .forEach(e -> e.getPiece().getTypeManager().addType(IPieceType.IMMUNE));
            case TAKE_CARD -> board.getClock().takeFromCentralDeck(board.getClock().turnOf());
        }
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(ImpAbilityType.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, ImpAbilityType info) {
        return true;
    }

    @Override
    public List<ImpAbilityType> getInfos(AbstractBoard board) {
        return List.of(ImpAbilityType.values());
    }

    public static enum ImpAbilityType implements IInfo {
        IMMUNITY, TAKE_CARD;
    }
}
