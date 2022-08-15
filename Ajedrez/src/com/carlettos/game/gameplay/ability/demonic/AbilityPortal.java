package com.carlettos.game.gameplay.ability.demonic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;

public class AbilityPortal extends Ability {

    public AbilityPortal() {
        super("portal", 0, 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean checkTypes(Info info) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, IInfo info) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List getInfos(AbstractBoard board) {
        // TODO Auto-generated method stub
        return null;
    }
}
