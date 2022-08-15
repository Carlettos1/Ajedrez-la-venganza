package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class AbilityPortal extends Ability {

    public AbilityPortal() {
        super("portal", 0, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        // TODO Auto-generated method stub

    }

    @Override
    public IInfo[] getValues(AbstractBoard board, Point start) {
        // TODO Auto-generated method stub
        return null;
    }
}
