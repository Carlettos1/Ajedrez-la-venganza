package com.carlettos.game.board;

import com.carlettos.game.board.clock.AbstractClock;
import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.board.clock.listener.ClockEvent;
import com.carlettos.game.board.clock.listener.ClockListener;
import com.carlettos.game.board.deathPile.BasicDeathPile;
import com.carlettos.game.board.deck.Deck;
import com.carlettos.game.board.shape.Rectangle;
import com.carlettos.game.gameplay.card.invocation.SummonKnight;
import com.carlettos.game.gameplay.card.invocation.SummonRook;
import com.carlettos.game.gameplay.card.invocation.SummonWarlock;
import com.carlettos.game.gameplay.card.onBoard.Fire;
import com.carlettos.game.gameplay.card.onBoard.Ice;
import com.carlettos.game.gameplay.card.utility.AddMovement;
import com.carlettos.game.gameplay.piece.classic.Bishop;
import com.carlettos.game.gameplay.piece.classic.King;
import com.carlettos.game.gameplay.piece.classic.Knight;
import com.carlettos.game.gameplay.piece.classic.Pawn;
import com.carlettos.game.gameplay.piece.classic.Queen;
import com.carlettos.game.gameplay.piece.classic.Rook;
import com.carlettos.game.gameplay.piece.starting.Archer;
import com.carlettos.game.gameplay.piece.starting.Ballista;
import com.carlettos.game.gameplay.piece.starting.Builder;
import com.carlettos.game.gameplay.piece.starting.Cannon;
import com.carlettos.game.gameplay.piece.starting.Catapult;
import com.carlettos.game.gameplay.piece.starting.CrazyPawn;
import com.carlettos.game.gameplay.piece.starting.Magician;
import com.carlettos.game.gameplay.piece.starting.Paladin;
import com.carlettos.game.gameplay.piece.starting.Ram;
import com.carlettos.game.gameplay.piece.starting.ShieldBearer;
import com.carlettos.game.gameplay.piece.starting.Ship;
import com.carlettos.game.gameplay.piece.starting.SuperPawn;
import com.carlettos.game.gameplay.piece.starting.TeslaTower;
import com.carlettos.game.gameplay.piece.starting.Warlock;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.DeckHelper;

/**
 * It's the board.
 *
 * @author Carlos
 */
public class SquareBoard extends AbstractBoard {

    public SquareBoard(int columns, int rows, AbstractClock clock) {
        super(new Rectangle(columns, rows), new BasicDeathPile(), clock);
    }

    public static SquareBoard getDefaultInstance() {
        Player black = new Player(Color.BLACK);
        Player white = new Player(Color.WHITE);
        AbstractClock clock = new Clock(white, black);
        SquareBoard board = new SquareBoard(16, 17, clock);

        // adds 1 mana every 7 turns
        clock.addClockListener(new ClockListener() {
            @Override
            public void turnEnded(ClockEvent e) {
                if (e.getSource().getTurn() % 7 == 0) {
                    for (Player player : e.getSource().getPlayers()) {
                        player.changeMana(1);
                    }
                }
            }

            @Override
            public void movementEnded(ClockEvent e) { /* Doesn't use the method */ }
        });

        black.getHand().addCard(new AddMovement());
        white.getHand().addCard(new AddMovement());
        white.getHand().addCard(new SummonRook());
        white.getHand().addCard(new SummonRook());
        white.getHand().addCard(new SummonRook());
        white.getHand().addCard(new SummonRook());
        white.changeMana(5);
        black.changeMana(5);

        Deck.defaultInit(clock.getCentralDeck());

        var blackDeck = clock.getDeckOf(black);
        var whiteDeck = clock.getDeckOf(white);

        DeckHelper.addToAll(SummonWarlock::new, 4, blackDeck, whiteDeck);
        DeckHelper.addToAll(AddMovement::new, 3, blackDeck, whiteDeck);
        DeckHelper.addToAll(SummonKnight::new, 2, blackDeck, whiteDeck);

        whiteDeck.shuffle();
        blackDeck.shuffle();

        white.getHand().addCard(new Ice());
        white.getHand().addCard(new Fire());
        black.getHand().addCard(new Ice());
        black.getHand().addCard(new Fire());

        board.get(new Point(0, 7)).setIsMagic(true);
        board.get(new Point(0, 9)).setIsMagic(true);
        board.get(new Point(15, 7)).setIsMagic(true);
        board.get(new Point(15, 9)).setIsMagic(true);

        board.set(new Point(0, 0), new Cannon(Color.WHITE));
        board.set(new Point(15, 0), new Cannon(Color.WHITE));
        board.set(new Point(0, 16), new Cannon(Color.BLACK));
        board.set(new Point(15, 16), new Cannon(Color.BLACK));

        board.set(new Point(1, 0), new Rook(Color.WHITE));
        board.set(new Point(14, 0), new Rook(Color.WHITE));
        board.set(new Point(1, 16), new Rook(Color.BLACK));
        board.set(new Point(14, 16), new Rook(Color.BLACK));

        board.set(new Point(2, 0), new Catapult(Color.WHITE));
        board.set(new Point(13, 0), new Catapult(Color.WHITE));
        board.set(new Point(2, 16), new Catapult(Color.BLACK));
        board.set(new Point(13, 16), new Catapult(Color.BLACK));

        board.set(new Point(3, 0), new Knight(Color.WHITE));
        board.set(new Point(12, 0), new Knight(Color.WHITE));
        board.set(new Point(3, 16), new Knight(Color.BLACK));
        board.set(new Point(12, 16), new Knight(Color.BLACK));

        board.set(new Point(4, 0), new Warlock(Color.WHITE));
        board.set(new Point(11, 0), new Warlock(Color.WHITE));
        board.set(new Point(4, 16), new Warlock(Color.BLACK));
        board.set(new Point(11, 16), new Warlock(Color.BLACK));

        board.set(new Point(5, 0), new Bishop(Color.WHITE));
        board.set(new Point(10, 0), new Bishop(Color.WHITE));
        board.set(new Point(5, 16), new Bishop(Color.BLACK));
        board.set(new Point(10, 16), new Bishop(Color.BLACK));

        board.set(new Point(6, 0), new Magician(Color.WHITE));
        board.set(new Point(7, 0), new Queen(Color.WHITE));
        board.set(new Point(8, 0), new King(Color.WHITE));
        board.set(new Point(9, 0), new Paladin(Color.WHITE));

        board.set(new Point(6, 16), new Magician(Color.BLACK));
        board.set(new Point(7, 16), new Queen(Color.BLACK));
        board.set(new Point(8, 16), new King(Color.BLACK));
        board.set(new Point(9, 16), new Paladin(Color.BLACK));

        board.set(new Point(0, 1), new Ship(Color.WHITE));
        board.set(new Point(15, 1), new Ship(Color.WHITE));
        board.set(new Point(0, 15), new Ship(Color.BLACK));
        board.set(new Point(15, 15), new Ship(Color.BLACK));

        board.set(new Point(1, 1), new TeslaTower(Color.WHITE));
        board.set(new Point(14, 1), new TeslaTower(Color.WHITE));
        board.set(new Point(1, 15), new TeslaTower(Color.BLACK));
        board.set(new Point(14, 15), new TeslaTower(Color.BLACK));

        board.set(new Point(2, 1), new Ram(Color.WHITE));
        board.set(new Point(13, 1), new Ram(Color.WHITE));
        board.set(new Point(2, 15), new Ram(Color.BLACK));
        board.set(new Point(13, 15), new Ram(Color.BLACK));

        board.set(new Point(3, 1), new Builder(Color.WHITE));
        board.set(new Point(12, 1), new Builder(Color.WHITE));
        board.set(new Point(3, 15), new Builder(Color.BLACK));
        board.set(new Point(12, 15), new Builder(Color.BLACK));

        board.set(new Point(4, 1), new Pawn(Color.WHITE));
        board.set(new Point(11, 1), new Pawn(Color.WHITE));
        board.set(new Point(4, 15), new Pawn(Color.BLACK));
        board.set(new Point(11, 15), new Pawn(Color.BLACK));

        board.set(new Point(5, 1), new Pawn(Color.WHITE));
        board.set(new Point(10, 1), new Pawn(Color.WHITE));
        board.set(new Point(5, 15), new Pawn(Color.BLACK));
        board.set(new Point(10, 15), new Pawn(Color.BLACK));

        board.set(new Point(6, 1), new CrazyPawn(Color.WHITE));
        board.set(new Point(9, 1), new CrazyPawn(Color.WHITE));
        board.set(new Point(6, 15), new CrazyPawn(Color.BLACK));
        board.set(new Point(9, 15), new CrazyPawn(Color.BLACK));

        board.set(new Point(7, 1), new SuperPawn(Color.WHITE));
        board.set(new Point(8, 1), new SuperPawn(Color.WHITE));
        board.set(new Point(7, 15), new SuperPawn(Color.BLACK));
        board.set(new Point(8, 15), new SuperPawn(Color.BLACK));

        board.set(new Point(0, 2), new Ballista(Color.WHITE));
        board.set(new Point(15, 2), new Ballista(Color.WHITE));
        board.set(new Point(0, 14), new Ballista(Color.BLACK));
        board.set(new Point(15, 14), new Ballista(Color.BLACK));

        board.set(new Point(1, 2), new Archer(Color.WHITE));
        board.set(new Point(14, 2), new Archer(Color.WHITE));
        board.set(new Point(1, 14), new Archer(Color.BLACK));
        board.set(new Point(14, 14), new Archer(Color.BLACK));

        board.set(new Point(2, 2), new Archer(Color.WHITE));
        board.set(new Point(13, 2), new Archer(Color.WHITE));
        board.set(new Point(2, 14), new Archer(Color.BLACK));
        board.set(new Point(13, 14), new Archer(Color.BLACK));

        board.set(new Point(3, 2), new ShieldBearer(Color.WHITE));
        board.set(new Point(12, 2), new ShieldBearer(Color.WHITE));
        board.set(new Point(3, 14), new ShieldBearer(Color.BLACK));
        board.set(new Point(12, 14), new ShieldBearer(Color.BLACK));

        board.set(new Point(0, 3), new Pawn(Color.WHITE));
        board.set(new Point(15, 3), new Pawn(Color.WHITE));
        board.set(new Point(0, 13), new Pawn(Color.BLACK));
        board.set(new Point(15, 13), new Pawn(Color.BLACK));

        board.set(new Point(1, 3), new CrazyPawn(Color.WHITE));
        board.set(new Point(14, 3), new CrazyPawn(Color.WHITE));
        board.set(new Point(1, 13), new CrazyPawn(Color.BLACK));
        board.set(new Point(14, 13), new CrazyPawn(Color.BLACK));

        board.set(new Point(2, 3), new Pawn(Color.WHITE));
        board.set(new Point(13, 3), new Pawn(Color.WHITE));
        board.set(new Point(2, 13), new Pawn(Color.BLACK));
        board.set(new Point(13, 13), new Pawn(Color.BLACK));
        return board;
    }
}
