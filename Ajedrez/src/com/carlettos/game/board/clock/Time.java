package com.carlettos.game.board.clock;

import com.carlettos.game.util.helper.MathHelper;

public record Time(int laps, int turns, int movements) {

    public static final Time ZERO = new Time(0, 0, 0);

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

    public static class TimeBuilder {
        public final int[] time = new int[TimeSpan.values().length];

        public TimeBuilder set(TimeSpan span, int quantity) {
            time[span.ordinal()] = quantity;
            return this;
        }

        public Time build() {
            return new Time(time[TimeSpan.LAP.ordinal()], time[TimeSpan.TURN.ordinal()],
                    time[TimeSpan.MOVEMENT.ordinal()]);
        }
    }
}
