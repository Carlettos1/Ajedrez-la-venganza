package com.carlettos.game.board.clock;

import java.util.List;

import com.carlettos.game.board.clock.manager.IBoardCardManager;
import com.carlettos.game.board.clock.manager.ICentralDeckManager;
import com.carlettos.game.board.clock.manager.IClockListenerManager;
import com.carlettos.game.board.clock.manager.IEventManager;
import com.carlettos.game.board.clock.manager.IPlayerDeckManager;
import com.carlettos.game.board.deck.PlayerDeck;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.enums.Color;

/**
 * It represent the chess clock.
 *
 * @author Carlos
 */
public abstract class AbstractClock
        implements IPlayerDeckManager, ICentralDeckManager, IEventManager, IBoardCardManager, IClockListenerManager {
    protected int turn;
    protected int movements;
    protected final Player[] players;
    protected final PlayerDeck[] playerDecks;

    public AbstractClock(Player... players) {
        this.turn = 1;
        this.movements = 0;
        this.players = players;
        this.playerDecks = new PlayerDeck[players.length];
        for (int i = 0; i < players.length; i++) {
            this.playerDecks[i] = new PlayerDeck(this.players[i]);
        }
    }

    /**
     * It has to execute everytime that a player makes a movement.
     */
    public void movement() {
        movements += 1;
    }

    /**
     * Ends the turn and fire up events.
     */
    public void tick() {
        turn++;
        movements = 0;
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public Player getPlayerOfColor(Color color) {
        for (Player player : players) {
            if (player.getColor().equals(color)) { return player; }
        }
        throw new IllegalArgumentException("Doesn't exists a player with color " + color);
    }

    @Override
    public List<PlayerDeck> getPlayerDecks() {
        return List.of(playerDecks);
    }

    @Override
    public PlayerDeck getDeckOf(Player player) {
        for (PlayerDeck playerDeck : playerDecks) {
            if (playerDeck.getOwner().equals(player)) { return playerDeck; }
        }
        throw new IllegalArgumentException("player %s doesn't have a deck".formatted(player));
    }

    /**
     * Verifies if the player can play this turn.
     *
     * @param player the player.
     *
     * @return true if it can, false either case.
     */
    public boolean canPlay(Player player) {
        return turnOf().equals(player) && player.getMaxMovements() > movements;
    }

    /**
     * It gets which player is currently playing.
     *
     * @return player playing.
     */
    public final Player turnOf() {
        return players[(turn - 1) % players.length];
    }

    /**
     * Returns the turn that is being currently playing.
     */
    public final int getTurn() {
        return turn;
    }

    /**
     * Returns the movement that is being currently playing.
     */
    public final int getMovements() {
        return movements;
    }
}
