package com.carlettos.game.gameplay.ability.demonic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction.SubDirection;

public class SpiderAbility extends Ability<SubDirection> {

    public SpiderAbility() {
        super("spider", 12, 1);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(SubDirection.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, SubDirection info) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<SubDirection> getInfos(AbstractBoard board) {
        return List.of(SubDirection.values());
    }
}
