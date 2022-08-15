package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.card.onBoard.CardsOnBoard;
import com.carlettos.game.gameplay.effect.Invulnerability;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;

@SuppressWarnings("unchecked")
public class AbilityPaladin extends Ability {
    public static final Pattern DEMONIC_ATTACK_PATTERN = Patterns.CANNON_ATTACK_PATTERN;
    public static final Pattern INVULNERABLE_PATTERN = Patterns.ARCHER_MOVE_PATTERN;
    public static final Pattern REVIVE_PATTERN = Patterns.KING_PATTERN;
    public static final Pattern[] PATTERNS = { DEMONIC_ATTACK_PATTERN, INVULNERABLE_PATTERN, REVIVE_PATTERN };

    static {
        Info.register(PaladinHabilityType.class);
    }

    public AbilityPaladin() {
        super("paladin", 8, 2);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!info.isTupleType(PaladinHabilityType.class, Point.class) || !this.commonCanUse(board, piece)) {
            return false;
        }
        Tuple<PaladinHabilityType, Point> tuple = (Tuple<PaladinHabilityType, Point>) info.getValue();
        boolean result = false;

        if (board.getAll(PATTERNS[tuple.x.ordinal()], start).stream().map(e -> e.getPos()).anyMatch(tuple.y::equals)) {
            boolean hasPiece = board.get(tuple.y).hasPiece();
            boolean isEqualColor = board.get(tuple.y).isControlledBy(piece.getColor());
            result = switch (tuple.x) {
                case ATTACK ->
                    board.getClock().boardContains(CardsOnBoard.ATTACK_TO_DEMONIC) && hasPiece && !isEqualColor;
                case INVULNERABILITY ->
                    board.getClock().boardContains(CardsOnBoard.INVULNERABILITY) && hasPiece && isEqualColor;
                case REVIVE -> board.getClock().boardContains(CardsOnBoard.REVIVE) && !hasPiece;
            };
        }
        return (result);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Tuple<PaladinHabilityType, Point> tuple = (Tuple<PaladinHabilityType, Point>) info.getValue();

        switch (tuple.x) {
            case ATTACK -> board.remove(tuple.y, true);
            case INVULNERABILITY -> board.getPiece(tuple.y).getEffectManager().addEffect(new Invulnerability(5));
            case REVIVE -> board.set(tuple.y, board.getDeathPile().getLast());
        }
    }

    @Override
    public Tuple<PaladinHabilityType, Point>[] getValues(AbstractBoard board, Point start) {
        if (board.getPiece(start) != null) {
            List<Tuple<PaladinHabilityType, Point>> list = new ArrayList<>(30);
            Piece piece = board.getPiece(start);

            for (PaladinHabilityType type : PaladinHabilityType.values()) {
                board.getAll(PATTERNS[type.ordinal()], start).forEach(e -> {
                    boolean hasPiece = e.hasPiece();
                    boolean isEqualColor = e.isControlledBy(piece.getColor());
                    boolean result = switch (type) {
                        case ATTACK ->
                            board.getClock().boardContains(CardsOnBoard.ATTACK_TO_DEMONIC) && hasPiece && !isEqualColor;
                        case INVULNERABILITY ->
                            board.getClock().boardContains(CardsOnBoard.INVULNERABILITY) && hasPiece && isEqualColor;
                        case REVIVE -> board.getClock().boardContains(CardsOnBoard.REVIVE) && !hasPiece;
                    };
                    if (result) {
                        list.add(Tuple.of(type, e.getPos()));
                    }
                });
            }
            return list.toArray(Tuple[]::new);
        }
        return new Tuple[] {};
    }

    public static enum PaladinHabilityType implements IInfo {
        ATTACK, INVULNERABILITY, REVIVE;
    }
}
