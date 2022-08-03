package com.carlettos.game.board;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.clock.AbstractClock;
import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.board.clock.listener.ClockEvent;
import com.carlettos.game.board.clock.listener.ClockListener;
import com.carlettos.game.board.deck.Deck;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.card.invocation.SummonKnight;
import com.carlettos.game.gameplay.card.invocation.SummonWarlock;
import com.carlettos.game.gameplay.card.upgrade.Fire;
import com.carlettos.game.gameplay.card.upgrade.Ice;
import com.carlettos.game.gameplay.card.utility.AddMovement;
import com.carlettos.game.gameplay.piece.Piece;
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
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.DeckHelper;

/**
 * It's the board.
 *
 * @author Carlos
 */
public class SquareBoard extends AbstractSquareBoard {
    protected final AbstractClock clock;

    public SquareBoard(int columns, int rows, AbstractClock clock) {
        super(columns, rows);
        this.clock = clock;
    }

    /**
     * Tries to do an {@code Action}. In case of an action that needs other point,
     * use Point::toInfo.
     *
     * @param action action to do.
     * @param start  start point.
     * @param info   information about the action.
     * @return FAIL if it didn't do the action, PASS if the action has been done.
     */
    public ActionResult tryTo(Action action, Point start, Info info) {
        var startEsq = getEscaque(start);
        var piece = startEsq.getPiece();
        if (!canPlay(piece)) { return ActionResult.FAIL; }
        if ((action == Action.ATTACK || action == Action.MOVE || action == Action.TAKE)
                && !(info.getValue() instanceof Point)) {
            throw new IllegalArgumentException("Info no es Info<Point> para " + action + ", es: " + info.getClass());
        }
        ActionResult can = getEscaque(start).getPiece().can(action, this, start, info);
        if (can.isPositive()) {
            switch (action) {
                case ATTACK -> getEscaque((Point) info.getValue()).removePiece();
                case MOVE, TAKE -> {
                    getEscaque((Point) info.getValue()).setPiece(piece);
                    startEsq.removePiece();
                }
                case ABILITY -> startEsq.getPiece().getAbility().use(this, piece, start, info);
                default -> throw new IllegalArgumentException("Action not expected");
            }
            piece.postAction(action, this, start, info);
            movement();
        }
        return can;
    }

    @Override
    public void tick() {
        this.foreach(e -> {
            e.getPiece().setIsMoved(false);
            e.getPiece().tick(this, e.getPos());
        });
        getClock().tick();

        // TODO: repaint on tick
        try {
            var bd = BoardDisplay.getInstance();
            bd.getManoVisual().redo();
            bd.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AbstractClock getClock() {
        return clock;
    }

    /**
     * It gets all the pieces of the given color.
     *
     * @param color color of the pieces to get.
     * @return a List with all the pieces of the given color.
     */
    public List<Piece> getPiecesOf(Color color) {
        List<Piece> piezas = new ArrayList<>();
        for (Escaque[] escaques : chessBoard) {
            for (Escaque escaque : escaques) {
                if (escaque.isControlledBy(color)) {
                    piezas.add(escaque.getPiece());
                }
            }
        }
        return piezas;
    }

    public static SquareBoard getDefaultInstance() {
        Player black = new Player(Color.BLACK);
        Player white = new Player(Color.WHITE);
        AbstractClock clock = new Clock(white, black);
        SquareBoard board = new SquareBoard(16, 17, clock);

        // adds 1 mana every 7 turns
        clock.addListener(new ClockListener() {
            @Override
            public void onEndTurn(ClockEvent e) {
                if (e.getSource().getTurn() % 7 == 0) {
                    for (Player player : e.getSource().getPlayers()) {
                        player.changeMana(1);
                    }
                }
            }

            @Override
            public void onEndMovement(ClockEvent e) { /* Doesn't use the method */ }
        });

        black.getHand().addCard(new AddMovement());
        white.getHand().addCard(new AddMovement());
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

        board.setPiece(new Point(0, 0), new Cannon(Color.WHITE));
        board.setPiece(new Point(15, 0), new Cannon(Color.WHITE));
        board.setPiece(new Point(0, 16), new Cannon(Color.BLACK));
        board.setPiece(new Point(15, 16), new Cannon(Color.BLACK));

        board.setPiece(new Point(1, 0), new Rook(Color.WHITE));
        board.setPiece(new Point(14, 0), new Rook(Color.WHITE));
        board.setPiece(new Point(1, 16), new Rook(Color.BLACK));
        board.setPiece(new Point(14, 16), new Rook(Color.BLACK));

        board.setPiece(new Point(2, 0), new Catapult(Color.WHITE));
        board.setPiece(new Point(13, 0), new Catapult(Color.WHITE));
        board.setPiece(new Point(2, 16), new Catapult(Color.BLACK));
        board.setPiece(new Point(13, 16), new Catapult(Color.BLACK));

        board.setPiece(new Point(3, 0), new Knight(Color.WHITE));
        board.setPiece(new Point(12, 0), new Knight(Color.WHITE));
        board.setPiece(new Point(3, 16), new Knight(Color.BLACK));
        board.setPiece(new Point(12, 16), new Knight(Color.BLACK));

        board.setPiece(new Point(4, 0), new Warlock(Color.WHITE));
        board.setPiece(new Point(11, 0), new Warlock(Color.WHITE));
        board.setPiece(new Point(4, 16), new Warlock(Color.BLACK));
        board.setPiece(new Point(11, 16), new Warlock(Color.BLACK));

        board.setPiece(new Point(5, 0), new Bishop(Color.WHITE));
        board.setPiece(new Point(10, 0), new Bishop(Color.WHITE));
        board.setPiece(new Point(5, 16), new Bishop(Color.BLACK));
        board.setPiece(new Point(10, 16), new Bishop(Color.BLACK));

        board.setPiece(new Point(6, 0), new Magician(Color.WHITE));
        board.setPiece(new Point(7, 0), new Queen(Color.WHITE));
        board.setPiece(new Point(8, 0), new King(Color.WHITE));
        board.setPiece(new Point(9, 0), new Paladin(Color.WHITE));

        board.setPiece(new Point(6, 16), new Magician(Color.BLACK));
        board.setPiece(new Point(7, 16), new Queen(Color.BLACK));
        board.setPiece(new Point(8, 16), new King(Color.BLACK));
        board.setPiece(new Point(9, 16), new Paladin(Color.BLACK));

        board.setPiece(new Point(0, 1), new Ship(Color.WHITE));
        board.setPiece(new Point(15, 1), new Ship(Color.WHITE));
        board.setPiece(new Point(0, 15), new Ship(Color.BLACK));
        board.setPiece(new Point(15, 15), new Ship(Color.BLACK));

        board.setPiece(new Point(1, 1), new TeslaTower(Color.WHITE));
        board.setPiece(new Point(14, 1), new TeslaTower(Color.WHITE));
        board.setPiece(new Point(1, 15), new TeslaTower(Color.BLACK));
        board.setPiece(new Point(14, 15), new TeslaTower(Color.BLACK));

        board.setPiece(new Point(2, 1), new Ram(Color.WHITE));
        board.setPiece(new Point(13, 1), new Ram(Color.WHITE));
        board.setPiece(new Point(2, 15), new Ram(Color.BLACK));
        board.setPiece(new Point(13, 15), new Ram(Color.BLACK));

        board.setPiece(new Point(3, 1), new Builder(Color.WHITE));
        board.setPiece(new Point(12, 1), new Builder(Color.WHITE));
        board.setPiece(new Point(3, 15), new Builder(Color.BLACK));
        board.setPiece(new Point(12, 15), new Builder(Color.BLACK));

        board.setPiece(new Point(4, 1), new Pawn(Color.WHITE));
        board.setPiece(new Point(11, 1), new Pawn(Color.WHITE));
        board.setPiece(new Point(4, 15), new Pawn(Color.BLACK));
        board.setPiece(new Point(11, 15), new Pawn(Color.BLACK));

        board.setPiece(new Point(5, 1), new Pawn(Color.WHITE));
        board.setPiece(new Point(10, 1), new Pawn(Color.WHITE));
        board.setPiece(new Point(5, 15), new Pawn(Color.BLACK));
        board.setPiece(new Point(10, 15), new Pawn(Color.BLACK));

        board.setPiece(new Point(6, 1), new CrazyPawn(Color.WHITE));
        board.setPiece(new Point(9, 1), new CrazyPawn(Color.WHITE));
        board.setPiece(new Point(6, 15), new CrazyPawn(Color.BLACK));
        board.setPiece(new Point(9, 15), new CrazyPawn(Color.BLACK));

        board.setPiece(new Point(7, 1), new SuperPawn(Color.WHITE));
        board.setPiece(new Point(8, 1), new SuperPawn(Color.WHITE));
        board.setPiece(new Point(7, 15), new SuperPawn(Color.BLACK));
        board.setPiece(new Point(8, 15), new SuperPawn(Color.BLACK));

        board.setPiece(new Point(0, 2), new Ballista(Color.WHITE));
        board.setPiece(new Point(15, 2), new Ballista(Color.WHITE));
        board.setPiece(new Point(0, 14), new Ballista(Color.BLACK));
        board.setPiece(new Point(15, 14), new Ballista(Color.BLACK));

        board.setPiece(new Point(1, 2), new Archer(Color.WHITE));
        board.setPiece(new Point(14, 2), new Archer(Color.WHITE));
        board.setPiece(new Point(1, 14), new Archer(Color.BLACK));
        board.setPiece(new Point(14, 14), new Archer(Color.BLACK));

        board.setPiece(new Point(2, 2), new Archer(Color.WHITE));
        board.setPiece(new Point(13, 2), new Archer(Color.WHITE));
        board.setPiece(new Point(2, 14), new Archer(Color.BLACK));
        board.setPiece(new Point(13, 14), new Archer(Color.BLACK));

        board.setPiece(new Point(3, 2), new ShieldBearer(Color.WHITE));
        board.setPiece(new Point(12, 2), new ShieldBearer(Color.WHITE));
        board.setPiece(new Point(3, 14), new ShieldBearer(Color.BLACK));
        board.setPiece(new Point(12, 14), new ShieldBearer(Color.BLACK));

        board.setPiece(new Point(0, 3), new Pawn(Color.WHITE));
        board.setPiece(new Point(15, 3), new Pawn(Color.WHITE));
        board.setPiece(new Point(0, 13), new Pawn(Color.BLACK));
        board.setPiece(new Point(15, 13), new Pawn(Color.BLACK));

        board.setPiece(new Point(1, 3), new CrazyPawn(Color.WHITE));
        board.setPiece(new Point(14, 3), new CrazyPawn(Color.WHITE));
        board.setPiece(new Point(1, 13), new CrazyPawn(Color.BLACK));
        board.setPiece(new Point(14, 13), new CrazyPawn(Color.BLACK));

        board.setPiece(new Point(2, 3), new Pawn(Color.WHITE));
        board.setPiece(new Point(13, 3), new Pawn(Color.WHITE));
        board.setPiece(new Point(2, 13), new Pawn(Color.BLACK));
        board.setPiece(new Point(13, 13), new Pawn(Color.BLACK));
        return board;
    }
}