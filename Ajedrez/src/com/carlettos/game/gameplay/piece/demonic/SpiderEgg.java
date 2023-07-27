package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;

public class SpiderEgg extends Piece {
    private int counter = 5;
    
    public SpiderEgg(Color color) {
        super("spider_egg", Abilities.NO_ABILITY, color, IPieceType.DEMONIC, IPieceType.BIOLOGIC);
    }
    
    @Override
    public boolean can(Action action, AbstractBoard board, Point start, Info info) {
        return false;
    }
    
    @Override
    protected void innerTick(AbstractBoard board, Point pos) {
        if (counter == 0) {
            board.set(pos, new Spider(this.getColor()));
        }
        --counter;
    }
}
