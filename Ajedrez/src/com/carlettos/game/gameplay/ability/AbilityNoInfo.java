package com.carlettos.game.gameplay.ability;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.util.Point;

/**
 * It's the ability wich doesn't uses any value of input
 */
public abstract class AbilityNoInfo extends Ability<AbilityNoInfo.NoInfo> {

    protected AbilityNoInfo(String key, int cooldown, int manaCost) {
        super(key, cooldown, manaCost);
    }

    @Override
    public final void use(AbstractBoard board, Point start, Info info) {
        this.use(board, start);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, NoInfo info) {
        return this.reducedCanUse(board, start);
    }

    public abstract boolean reducedCanUse(AbstractBoard board, Point start);

    public abstract void use(AbstractBoard board, Point start);
    
    @Override
    public boolean checkTypes(Info info) {
        return true;
    }
    
    public List<NoInfo> getInfos(AbstractBoard board){
        return List.of(NoInfo.NO_INFO);
    }

    public static enum NoInfo implements IInfo {
        NO_INFO;

        public static Info getInfo() {
            return NO_INFO.toInfo();
        }
    }
}
