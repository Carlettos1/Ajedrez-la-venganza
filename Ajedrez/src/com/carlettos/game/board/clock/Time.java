package com.carlettos.game.board.clock;

import com.carlettos.game.board.clock.manager.IPlayerDeckManager;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.helper.MathHelper;

public record Time(int laps, int turns, int movements) {

    public static final Time ZERO = new Time(0, 0, 0);
    public static final Time A_LAP = new Time(1, 0, 0);
    public static final Time A_TURN = new Time(0, 1, 0);
    public static final Time A_MOVEMENT = new Time(0, 0, 1);

    public static Time lap(int laps) {
        return new TimeBuilder().set(TimeSpan.LAP, laps).build();
    }

    public static Time turn(int turns) {
        return new TimeBuilder().set(TimeSpan.TURN, turns).build();
    }

    public static Time movement(int movement) {
        return new TimeBuilder().set(TimeSpan.MOVEMENT, movement).build();
    }

    public Time add(Time other) {
        return new Time(MathHelper.clamp(0, 256, this.laps + other.laps),
                MathHelper.clamp(0, 256, this.turns + other.turns),
                MathHelper.clamp(0, 256, this.movements + other.movements));
    }

    public Time substract(Time other) {
        return new Time(MathHelper.clamp(0, 256, this.laps - other.laps),
                MathHelper.clamp(0, 256, this.turns - other.turns),
                MathHelper.clamp(0, 256, this.movements - other.movements));
    }

    public boolean isZero() {
        return this.equals(ZERO);
    }

    /**
     * Returns an accurate approximation of the number of movements that is required
     * to this Time to run out (be equal to {@link #ZERO}).
     */
    public int getTotalMovements(IPlayerDeckManager deckManager) {
        return Math.max(this.movements, Math.max(this.getTurnMovements(deckManager), this.getLapMovements(deckManager)));
    }

    public int getTurnMovements(IPlayerDeckManager deckManager) {
        Player[] players = deckManager.getPlayers();
        int turnMovements = 0;
        for (int turn = 0; turn < this.turns; turn++) {
            turnMovements += players[turn % players.length].getMaxMovements();
        }
        return turnMovements;
    }

    public int getLapMovements(IPlayerDeckManager deckManager) {
        int lapMovements = 0;
        for (Player player : deckManager.getPlayers()) {
            lapMovements += player.getMaxMovements();
        }
        return lapMovements * this.laps;
    }

    public int compareTo(Time other, IPlayerDeckManager deckManager) {
        return Integer.compare(this.getTotalMovements(deckManager), other.getTotalMovements(deckManager));
    }

    public static class TimeBuilder {
        public final int[] time = new int[TimeSpan.values().length];

        public TimeBuilder set(TimeSpan span, int quantity) {
            time[span.ordinal()] = quantity;
            return this;
        }

        public TimeBuilder add(TimeSpan span, int quantity) {
            time[span.ordinal()] += quantity;
            return this;
        }

        public Time build() {
            return new Time(time[TimeSpan.LAP.ordinal()], time[TimeSpan.TURN.ordinal()],
                    time[TimeSpan.MOVEMENT.ordinal()]);
        }
    }
}
