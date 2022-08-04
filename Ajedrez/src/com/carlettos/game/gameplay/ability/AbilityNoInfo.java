package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.util.Point;

/**
 * It's the ability wich doesn't uses any value of input
 */
public abstract class AbilityNoInfo extends Ability {
    
    static {
        Info.register(NoInfo.class);
    }

    protected AbilityNoInfo(String key, int cooldown, int manaCost) {
        super(key, cooldown, manaCost);
    }

    @Override
    public final NoInfo[] getValues(AbstractSquareBoard board, Point start) { // TODO: get real values
        return NoInfo.values();
    }
    
    public static enum NoInfo implements IInfo {
        NO_INFO;

        @Override
        public Info toInfo() {
            return Info.getInfo(this);
        }
        
        public static Info getInfo() {
            return Info.getInfo(NO_INFO);
        }
    }
}
