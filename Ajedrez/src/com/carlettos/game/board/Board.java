package com.carlettos.game.board;

import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.board.clock.listener.ClockEvent;
import com.carlettos.game.board.clock.listener.ClockListener;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.card.invocation.SummonKnight;
import com.carlettos.game.gameplay.card.invocation.SummonWarlock;
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
import java.util.ArrayList;
import java.util.List;

/**
 * It's the board.
 *
 * @author Carlos
 */
public class Board extends AbstractBoard {
    protected final Clock clock;

    public Board(int columns, int rows, Clock clock) {
        super(columns, rows);
        this.clock = clock;
    }
    
    /**
     * Tries to do an {@code Action}. In case of an action that needs other 
     * point, use Point::toInfo.
     *
     * @param action action to do.
     * @param start start point.
     * @param info information about the action.
     * @return FAIL if it didn't do the action, PASS if the action has been 
     * done.
     */
    public ActionResult tryTo(Action action, Point start, Info info){
        Escaque startEsq = getEscaque(start);
        if (!canPlay(startEsq.getPiece())) {
            return ActionResult.FAIL;
        }
        Escaque endEsq = null;
        if(action == Action.ATTACK || action == Action.MOVE || action == Action.TAKE){
            try {
                endEsq = getEscaque((Point) info.getValue());
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Info no es Info<Point> para " + action + ", es: " + info.getClass());
            }
        }
        ActionResult can = getEscaque(start).getPiece().can(action, this, start, info);
        if(can.isPositive()) {
            switch (action) {
                case ATTACK -> {
                    endEsq.removePiece();
                }
                case MOVE, TAKE -> {
                    endEsq.setPiece(startEsq.getPiece());
                    startEsq.removePiece();
                }
                case ABILITY -> {
                    startEsq.getPiece().getAbility().use(this, startEsq.getPiece(), start, info);
                }
            }
            endEsq.getPiece().postAction(action, this, start, info);
            movement();
        }
        return can;
    }

    /**
     * It notifies the clock that a movement had happen.
     */
    public void movement() {
        this.clock.movement();
        if(getClock().getMovements() >= getClock().turnOf().getMaxMovements()){
            for (Escaque[] escaques : chessBoard) {
                for (Escaque escaque : escaques) {
                    escaque.getPiece().setIsMoved(false);
                }
            }
            getClock().endTurn();
            try {
                var bd = BoardDisplay.getInstance();
                bd.getManoVisual().redo();
                bd.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Checks if the given piece can play, doesn't check if the piece has moved.
     *
     * @param piece piece to check.
     * @return true if the color of the piece can play, false other case.
     */
    public boolean canPlay(Piece piece){
        return canPlay(piece.getColor());
    }
    
    /**
     * Checks if the given color can play.
     *
     * @param color color to check.
     * @return true if can play, false other case.
     */
    public boolean canPlay(Color color){
        return getClock().turnOf().getColor().equals(color) && getClock().canPlay(getClock().turnOf());
    }

    public Clock getClock() {
        return clock;
    }
    
    /**
     * It gets all the pieces of the given color.
     *
     * @param color color of the pieces to get.
     * @return a List with all the pieces of the given color.
     */
    public List<Piece> getPiecesOf(Color color){
        List<Piece> piezas = new ArrayList<>();
        for (Escaque[] escaques : chessBoard) {
            for (Escaque escaque : escaques) {
                if(escaque.isControlledBy(color)) {
                    piezas.add(escaque.getPiece());
                }
            }
        }
        return piezas;
    }
    
    public static Board getDefaultInstance(){
        Player black = new Player(Color.BLACK);
        Player white = new Player(Color.WHITE);
        Clock clock = new Clock(white, black);
        Board board = new Board(16, 17, clock);
        
        //adds 1 mana every 7 turns
        clock.addListener(new ClockListener() {
            @Override
            public void onEndTurn(ClockEvent e) {
                if (e.getSource().getTurn() % 7 == 0) {
                    for (Player player : e.getSource().getPlayers()) {
                        player.changeMana(1);
                    }
                }
            }
        });
        
        black.getHand().addCard(new AddMovement());
        white.getHand().addCard(new AddMovement());
        white.changeMana(5);
        black.changeMana(5);
        
        Deck.defaultInit(clock.getDeck());
        
        var blackDeck = clock.getDeckOf(black);
        var whiteDeck = clock.getDeckOf(white);
        
        DeckHelper.addToAll(() -> new SummonWarlock(), 4, blackDeck, whiteDeck);
        DeckHelper.addToAll(() -> new AddMovement(), 3, blackDeck, whiteDeck);
        DeckHelper.addToAll(() -> new SummonKnight(), 2, blackDeck, whiteDeck);
        
        whiteDeck.shuffle();
        blackDeck.shuffle();
        
        board.getEscaque(0, 0).setPiece(new Cannon(Color.WHITE));
        board.getEscaque(15, 0).setPiece(new Cannon(Color.WHITE));        
        board.getEscaque(0, 16).setPiece(new Cannon(Color.BLACK));
        board.getEscaque(15, 16).setPiece(new Cannon(Color.BLACK));
        
        board.getEscaque(1, 0).setPiece(new Rook(Color.WHITE));
        board.getEscaque(14, 0).setPiece(new Rook(Color.WHITE));        
        board.getEscaque(1, 16).setPiece(new Rook(Color.BLACK));
        board.getEscaque(14, 16).setPiece(new Rook(Color.BLACK));
        
        board.getEscaque(2, 0).setPiece(new Catapult(Color.WHITE));
        board.getEscaque(13, 0).setPiece(new Catapult(Color.WHITE));        
        board.getEscaque(2, 16).setPiece(new Catapult(Color.BLACK));
        board.getEscaque(13, 16).setPiece(new Catapult(Color.BLACK));
        
        board.getEscaque(3, 0).setPiece(new Knight(Color.WHITE));
        board.getEscaque(12, 0).setPiece(new Knight(Color.WHITE));        
        board.getEscaque(3, 16).setPiece(new Knight(Color.BLACK));
        board.getEscaque(12, 16).setPiece(new Knight(Color.BLACK));
        
        board.getEscaque(4, 0).setPiece(new Warlock(Color.WHITE));
        board.getEscaque(11, 0).setPiece(new Warlock(Color.WHITE));        
        board.getEscaque(4, 16).setPiece(new Warlock(Color.BLACK));
        board.getEscaque(11, 16).setPiece(new Warlock(Color.BLACK));
        
        board.getEscaque(5, 0).setPiece(new Bishop(Color.WHITE));
        board.getEscaque(10, 0).setPiece(new Bishop(Color.WHITE));        
        board.getEscaque(5, 16).setPiece(new Bishop(Color.BLACK));
        board.getEscaque(10, 16).setPiece(new Bishop(Color.BLACK));
        
        board.getEscaque(6, 0).setPiece(new Magician(Color.WHITE));
        board.getEscaque(7, 0).setPiece(new Queen(Color.WHITE));        
        board.getEscaque(8, 0).setPiece(new King(Color.WHITE));
        board.getEscaque(9, 0).setPiece(new Paladin(Color.WHITE));
        
        board.getEscaque(6, 16).setPiece(new Magician(Color.BLACK));
        board.getEscaque(7, 16).setPiece(new Queen(Color.BLACK));        
        board.getEscaque(8, 16).setPiece(new King(Color.BLACK));
        board.getEscaque(9, 16).setPiece(new Paladin(Color.BLACK));
        
        board.getEscaque(0, 1).setPiece(new Ship(Color.WHITE));
        board.getEscaque(15, 1).setPiece(new Ship(Color.WHITE));        
        board.getEscaque(0, 15).setPiece(new Ship(Color.BLACK));
        board.getEscaque(15, 15).setPiece(new Ship(Color.BLACK));
        
        board.getEscaque(1, 1).setPiece(new TeslaTower(Color.WHITE));
        board.getEscaque(14, 1).setPiece(new TeslaTower(Color.WHITE));        
        board.getEscaque(1, 15).setPiece(new TeslaTower(Color.BLACK));
        board.getEscaque(14, 15).setPiece(new TeslaTower(Color.BLACK));
        
        board.getEscaque(2, 1).setPiece(new Ram(Color.WHITE));
        board.getEscaque(13, 1).setPiece(new Ram(Color.WHITE));        
        board.getEscaque(2, 15).setPiece(new Ram(Color.BLACK));
        board.getEscaque(13, 15).setPiece(new Ram(Color.BLACK));
        
        board.getEscaque(3, 1).setPiece(new Builder(Color.WHITE));
        board.getEscaque(12, 1).setPiece(new Builder(Color.WHITE));        
        board.getEscaque(3, 15).setPiece(new Builder(Color.BLACK));
        board.getEscaque(12, 15).setPiece(new Builder(Color.BLACK));
        
        board.getEscaque(4, 1).setPiece(new Pawn(Color.WHITE));
        board.getEscaque(11, 1).setPiece(new Pawn(Color.WHITE));        
        board.getEscaque(4, 15).setPiece(new Pawn(Color.BLACK));
        board.getEscaque(11, 15).setPiece(new Pawn(Color.BLACK));
        
        board.getEscaque(5, 1).setPiece(new Pawn(Color.WHITE));
        board.getEscaque(10, 1).setPiece(new Pawn(Color.WHITE));        
        board.getEscaque(5, 15).setPiece(new Pawn(Color.BLACK));
        board.getEscaque(10, 15).setPiece(new Pawn(Color.BLACK));
                
        board.getEscaque(6, 1).setPiece(new CrazyPawn(Color.WHITE));
        board.getEscaque(9, 1).setPiece(new CrazyPawn(Color.WHITE));        
        board.getEscaque(6, 15).setPiece(new CrazyPawn(Color.BLACK));
        board.getEscaque(9, 15).setPiece(new CrazyPawn(Color.BLACK));
        
        board.getEscaque(7, 1).setPiece(new SuperPawn(Color.WHITE));
        board.getEscaque(8, 1).setPiece(new SuperPawn(Color.WHITE));        
        board.getEscaque(7, 15).setPiece(new SuperPawn(Color.BLACK));
        board.getEscaque(8, 15).setPiece(new SuperPawn(Color.BLACK));
        
        board.getEscaque(0, 2).setPiece(new Ballista(Color.WHITE));
        board.getEscaque(15, 2).setPiece(new Ballista(Color.WHITE));        
        board.getEscaque(0, 14).setPiece(new Ballista(Color.BLACK));
        board.getEscaque(15, 14).setPiece(new Ballista(Color.BLACK));
        
        board.getEscaque(1, 2).setPiece(new Archer(Color.WHITE));
        board.getEscaque(14, 2).setPiece(new Archer(Color.WHITE));        
        board.getEscaque(1, 14).setPiece(new Archer(Color.BLACK));
        board.getEscaque(14, 14).setPiece(new Archer(Color.BLACK));
        
        board.getEscaque(2, 2).setPiece(new Archer(Color.WHITE));
        board.getEscaque(13, 2).setPiece(new Archer(Color.WHITE));        
        board.getEscaque(2, 14).setPiece(new Archer(Color.BLACK));
        board.getEscaque(13, 14).setPiece(new Archer(Color.BLACK));
        
        board.getEscaque(3, 2).setPiece(new ShieldBearer(Color.WHITE));
        board.getEscaque(12, 2).setPiece(new ShieldBearer(Color.WHITE));        
        board.getEscaque(3, 14).setPiece(new ShieldBearer(Color.BLACK));
        board.getEscaque(12, 14).setPiece(new ShieldBearer(Color.BLACK));
        
        board.getEscaque(0, 3).setPiece(new Pawn(Color.WHITE));
        board.getEscaque(15, 3).setPiece(new Pawn(Color.WHITE));        
        board.getEscaque(0, 13).setPiece(new Pawn(Color.BLACK));
        board.getEscaque(15, 13).setPiece(new Pawn(Color.BLACK));
        
        board.getEscaque(1, 3).setPiece(new CrazyPawn(Color.WHITE));
        board.getEscaque(14, 3).setPiece(new CrazyPawn(Color.WHITE));        
        board.getEscaque(1, 13).setPiece(new CrazyPawn(Color.BLACK));
        board.getEscaque(14, 13).setPiece(new CrazyPawn(Color.BLACK));
        
        board.getEscaque(2, 3).setPiece(new Pawn(Color.WHITE));
        board.getEscaque(13, 3).setPiece(new Pawn(Color.WHITE));        
        board.getEscaque(2, 13).setPiece(new Pawn(Color.BLACK));
        board.getEscaque(13, 13).setPiece(new Pawn(Color.BLACK));
        return board;
    }
}
