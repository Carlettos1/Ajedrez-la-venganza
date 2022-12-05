package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.card.onBoard.CardsOnBoard;
import com.carlettos.game.gameplay.effect.Invulnerability;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;

@SuppressWarnings("unchecked")
public class AbilityPaladin extends Ability<Tuple<AbilityPaladin.PaladinHabilityType, Point>> {
    public static final Pattern DEMONIC_ATTACK_PATTERN = Patterns.CANNON_ATTACK_PATTERN;
    public static final Pattern INVULNERABLE_PATTERN = Patterns.ARCHER_MOVE_PATTERN;
    public static final Pattern REVIVE_PATTERN = Patterns.KING_PATTERN;
    public static final Pattern[] PATTERNS = { DEMONIC_ATTACK_PATTERN, INVULNERABLE_PATTERN, REVIVE_PATTERN };

    static {}

    public AbilityPaladin() {
        super("paladin", Time.lap(8), 2);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        Tuple<PaladinHabilityType, Point> tuple = (Tuple<PaladinHabilityType, Point>) info.getValue();

        switch (tuple.x) {
            case ATTACK -> board.remove(tuple.y, true);
            case INVULNERABILITY -> board.getPiece(tuple.y).getEffectManager().addEffect(new Invulnerability(5));
            case REVIVE -> board.set(tuple.y, board.getDeathPile().getLast());
        }
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isTupleType(PaladinHabilityType.class, Point.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Tuple<PaladinHabilityType, Point> info) {
        Point p = info.y;
        PaladinHabilityType type = info.x;

        if (board.getAll(PATTERNS[type.ordinal()], start).stream().map(e -> e.getPos())
                .anyMatch(pos -> pos.equals(p))) {
            boolean hasPiece = board.get(p).hasPiece();
            boolean isColorEqual = board.get(p).isControlledBy(board.get(start).getPieceColor());
            return switch (type) {
                case ATTACK ->
                    board.getClock().boardContains(CardsOnBoard.ATTACK_TO_DEMONIC) && hasPiece && !isColorEqual;
                case INVULNERABILITY ->
                    board.getClock().boardContains(CardsOnBoard.INVULNERABILITY) && hasPiece && isColorEqual;
                case REVIVE -> board.getClock().boardContains(CardsOnBoard.REVIVE) && !hasPiece;
            };
        } else {
            return false;
        }
    }

    @Override
    public List<Tuple<PaladinHabilityType, Point>> getInfos(AbstractBoard board) {
        List<Tuple<PaladinHabilityType, Point>> values = new ArrayList<>(board.size() * 3);
        for (PaladinHabilityType type : PaladinHabilityType.values()) {
            board.stream().forEach(e -> { values.add(Tuple.of(type, e.getPos())); });
        }
        return values;
    }

    public static enum PaladinHabilityType implements IInfo {
        ATTACK, INVULNERABILITY, REVIVE;
    }
}
