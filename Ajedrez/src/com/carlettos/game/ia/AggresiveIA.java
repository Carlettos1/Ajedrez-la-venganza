package com.carlettos.game.ia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.enums.Action;

public class AggresiveIA extends AbstractIA {
    protected final Random rng;

    public AggresiveIA(AbstractBoard board, Player player) {
        super(board, player);
        this.rng = new Random((((long) player.hashCode()) << 32) | board.hashCode());
    }

    @Override
    protected void makeAMovement() {
        List<Movement> movements = new ArrayList<>(this.board.size() * this.board.size() * 4);
        List<Movement> aMovs;
        for (Escaque escaque : this.board) {
            movements.addAll(escaque.getPiece().getAllMovements(board, escaque.getPos()).stream()
                    .map(tuple -> new Movement(tuple.x, escaque.getPos(), tuple.y)).toList());
        }
        aMovs = new ArrayList<>(movements.stream().filter(m -> m.getAction() != Action.MOVE).toList());
        while (true) {
            Movement mov;
            if (aMovs.isEmpty()) {
                mov = movements.remove(this.rng.nextInt(movements.size()));
            } else {
                mov = aMovs.remove(this.rng.nextInt(aMovs.size()));
            }
            if (this.board.tryTo(mov.getAction(), mov.getFrom(), mov.getInfo())) {
                break;
            }
        }
    }
}
