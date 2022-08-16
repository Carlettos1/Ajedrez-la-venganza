package com.carlettos.game.ia;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.helper.LogHelper;

public abstract class AbstractIA implements Runnable {
    protected final AbstractBoard board;
    protected final Player player;
    
    public AbstractIA(AbstractBoard board, Player player) {
        LogHelper.LOG.info("Starting IA for player " + player.toString());
        this.board = board;
        this.player = player;
    }
    
    protected boolean canMakeAMovement() {
        if (board.canPlay(player.getColor())) {
            return true;
        }
        return false;
    }
    
    protected abstract void makeAMovement();
    
    @Override
    public void run() {
        LogHelper.LOG.info("IA %s has started running".formatted(this.player.toString()));
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (this.canMakeAMovement()) {
                this.makeAMovement();
                BoardDisplay.getInstance().repaint();
            }
        }
    }
}
