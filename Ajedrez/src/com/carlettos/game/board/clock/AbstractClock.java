package com.carlettos.game.board.clock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.carlettos.game.board.clock.manager.ICardOnBoardManager;
import com.carlettos.game.board.clock.manager.ICentralDeckManager;
import com.carlettos.game.board.clock.manager.IClockListenerManager;
import com.carlettos.game.board.clock.manager.IEventManager;
import com.carlettos.game.board.clock.manager.IPlayerDeckManager;
import com.carlettos.game.board.deck.PlayerDeck;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.annotation.Nullable;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.LogHelper;

/**
 * It represent the chess clock.
 *
 * @author Carlos
 */
public abstract class AbstractClock
        implements IPlayerDeckManager, ICentralDeckManager, IEventManager, ICardOnBoardManager, IClockListenerManager {
    
    /**
     * It is from 0 to infinity.
     */
    protected int laps;
    
    /**
     * It should be from 0 to {@link #getPlayers()} lenght
     */
    protected int turns;
    
    /**
     * It should be from 0 to {@link Player#getMaxMovements()}
     */
    protected int movements;
    protected final Player[] players;
    protected final PlayerDeck[] playerDecks;
    protected final Random RNG;

    public AbstractClock(Player... players) {
        this.laps = 0;
        this.turns = 0;
        this.movements = 0;
        this.players = players;
        this.RNG = new Random();
        this.playerDecks = new PlayerDeck[players.length];
        for (int i = 0; i < players.length; i++) {
            this.playerDecks[i] = new PlayerDeck(this.players[i]);
        }
    }
    
    public synchronized void tick() {
        this.tick(TimeSpan.MOVEMENT);
    }
    
    public synchronized void tick(TimeSpan span) {
        switch (span) {
            case MOVEMENT -> {
                movements++;
                if (this.movements == this.getCurrentlyPlaying().getMaxMovements()) {
                    this.tick(TimeSpan.TURN);
                }
            }
            case TURN -> {
                turns++;
                movements = 0;
                if (this.turns == this.players.length) {
                    this.tick(TimeSpan.LAP);
                }
            }
            case LAP -> {
                laps++;
                turns = 0;
                movements = 0;
            }
        }
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Nullable
    public Player[] getPlayers(Predicate<Player> predicate) {
        ArrayList<Player> possible = new ArrayList<>();
        for (Player p : players) {
            if (predicate.test(p)) {
                possible.add(p);
            }
        }
        if (possible.isEmpty()) {
            LogHelper.LOG.info("No player founded with the given predicate");
            return null;
        }
        return possible.toArray(Player[]::new);
    }

    public Player getRandomPlayer() {
        return getPlayers()[RNG.nextInt(players.length)];
    }

    public Player getRandomPlayer(Predicate<Player> predicate) {
        Player[] possibles = getPlayers(predicate);
        return possibles[RNG.nextInt(possibles.length)];
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
        return getCurrentlyPlaying().equals(player) && player.getMaxMovements() > movements;
    }

    /**
     * It gets which player is currently playing.
     *
     * @return player playing.
     */
    public final Player getCurrentlyPlaying() {
        return players[turns % players.length];
    }

    /**
     * Returns the turn that is being currently playing.
     */
    public final int getTurn() {
        return turns;
    }

    /**
     * Returns the movement that is being currently playing.
     */
    public final int getMovements() {
        return movements;
    }
    
    /**
     * Returns the lap that is currently playing.
     */
    public int getLaps() {
        return laps;
    }
}
