package com.carlettos.main;

import com.carlettos.game.board.card.invocation.SummonKnight;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.piece.classic.Bishop;
import com.carlettos.game.board.piece.classic.Knight;
import com.carlettos.game.board.piece.classic.Pawn;
import com.carlettos.game.board.piece.classic.King;
import com.carlettos.game.board.piece.classic.Rook;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.visual.BoardDisplay;
import com.carlettos.game.board.piece.classic.Queen;
import com.carlettos.game.board.piece.starting.Ram;
import com.carlettos.game.board.piece.starting.Archer;
import com.carlettos.game.board.piece.starting.Ballista;
import com.carlettos.game.board.piece.starting.Warlock;
import com.carlettos.game.board.piece.starting.Catapult;
import com.carlettos.game.board.piece.starting.Cannon;
import com.carlettos.game.board.piece.starting.Builder;
import com.carlettos.game.board.piece.starting.ShieldBearer;
import com.carlettos.game.board.piece.starting.Magician;
import com.carlettos.game.board.piece.starting.Ship;
import com.carlettos.game.board.piece.starting.Paladin;
import com.carlettos.game.board.piece.starting.MadPawn;
import com.carlettos.game.board.piece.starting.SuperPawn;
import com.carlettos.game.board.piece.starting.TeslaTower;

/**
 *
 * @author Carlos
 */
public class Main {

    public static void main(String... a) {
        Player negras = new Player(Color.NEGRO);
        Player blancas = new Player(Color.BLANCO);
        Clock reloj = new Clock(blancas, negras);
        Board tablero = new Board(16, 17, reloj);
        
        /*reloj.addEventos(Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 3))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))),
                Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 2))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))),
                Evento.Builder.start(tablero).with(4, "Colocar peón", new Point(3, 4))
                .build((turnos, nombre, punto, tablero1) -> tablero1.getEscaque(punto).setPieza(new Peon(Color.BLANCO))));
        */
        negras.getHand().addCarta(new SummonKnight());
        blancas.getHand().addCarta(new SummonKnight());
        blancas.cambiarMana(5);
        negras.cambiarMana(5);
        
        tablero.getEscaque(0, 0).setPiece(new Cannon(Color.BLANCO));
        tablero.getEscaque(15, 0).setPiece(new Cannon(Color.BLANCO));        
        tablero.getEscaque(0, 16).setPiece(new Cannon(Color.NEGRO));
        tablero.getEscaque(15, 16).setPiece(new Cannon(Color.NEGRO));
        
        tablero.getEscaque(1, 0).setPiece(new Rook(Color.BLANCO));
        tablero.getEscaque(14, 0).setPiece(new Rook(Color.BLANCO));        
        tablero.getEscaque(1, 16).setPiece(new Rook(Color.NEGRO));
        tablero.getEscaque(14, 16).setPiece(new Rook(Color.NEGRO));
        
        tablero.getEscaque(2, 0).setPiece(new Catapult(Color.BLANCO));
        tablero.getEscaque(13, 0).setPiece(new Catapult(Color.BLANCO));        
        tablero.getEscaque(2, 16).setPiece(new Catapult(Color.NEGRO));
        tablero.getEscaque(13, 16).setPiece(new Catapult(Color.NEGRO));
        
        tablero.getEscaque(3, 0).setPiece(new Knight(Color.BLANCO));
        tablero.getEscaque(12, 0).setPiece(new Knight(Color.BLANCO));        
        tablero.getEscaque(3, 16).setPiece(new Knight(Color.NEGRO));
        tablero.getEscaque(12, 16).setPiece(new Knight(Color.NEGRO));
        
        tablero.getEscaque(4, 0).setPiece(new Warlock(Color.BLANCO));
        tablero.getEscaque(11, 0).setPiece(new Warlock(Color.BLANCO));        
        tablero.getEscaque(4, 16).setPiece(new Warlock(Color.NEGRO));
        tablero.getEscaque(11, 16).setPiece(new Warlock(Color.NEGRO));
        
        tablero.getEscaque(5, 0).setPiece(new Bishop(Color.BLANCO));
        tablero.getEscaque(10, 0).setPiece(new Bishop(Color.BLANCO));        
        tablero.getEscaque(5, 16).setPiece(new Bishop(Color.NEGRO));
        tablero.getEscaque(10, 16).setPiece(new Bishop(Color.NEGRO));
        
        tablero.getEscaque(6, 0).setPiece(new Magician(Color.BLANCO));
        tablero.getEscaque(7, 0).setPiece(new Queen(Color.BLANCO));        
        tablero.getEscaque(8, 0).setPiece(new King(Color.BLANCO));
        tablero.getEscaque(9, 0).setPiece(new Paladin(Color.BLANCO));
        
        tablero.getEscaque(6, 16).setPiece(new Magician(Color.NEGRO));
        tablero.getEscaque(7, 16).setPiece(new Queen(Color.NEGRO));        
        tablero.getEscaque(8, 16).setPiece(new King(Color.NEGRO));
        tablero.getEscaque(9, 16).setPiece(new Paladin(Color.NEGRO));
        
        tablero.getEscaque(0, 1).setPiece(new Ship(Color.BLANCO));
        tablero.getEscaque(15, 1).setPiece(new Ship(Color.BLANCO));        
        tablero.getEscaque(0, 15).setPiece(new Ship(Color.NEGRO));
        tablero.getEscaque(15, 15).setPiece(new Ship(Color.NEGRO));
        
        tablero.getEscaque(1, 1).setPiece(new TeslaTower(Color.BLANCO));
        tablero.getEscaque(14, 1).setPiece(new TeslaTower(Color.BLANCO));        
        tablero.getEscaque(1, 15).setPiece(new TeslaTower(Color.NEGRO));
        tablero.getEscaque(14, 15).setPiece(new TeslaTower(Color.NEGRO));
        
        tablero.getEscaque(2, 1).setPiece(new Ram(Color.BLANCO));
        tablero.getEscaque(13, 1).setPiece(new Ram(Color.BLANCO));        
        tablero.getEscaque(2, 15).setPiece(new Ram(Color.NEGRO));
        tablero.getEscaque(13, 15).setPiece(new Ram(Color.NEGRO));
        
        tablero.getEscaque(3, 1).setPiece(new Builder(Color.BLANCO));
        tablero.getEscaque(12, 1).setPiece(new Builder(Color.BLANCO));        
        tablero.getEscaque(3, 15).setPiece(new Builder(Color.NEGRO));
        tablero.getEscaque(12, 15).setPiece(new Builder(Color.NEGRO));
        
        tablero.getEscaque(4, 1).setPiece(new Pawn(Color.BLANCO));
        tablero.getEscaque(11, 1).setPiece(new Pawn(Color.BLANCO));        
        tablero.getEscaque(4, 15).setPiece(new Pawn(Color.NEGRO));
        tablero.getEscaque(11, 15).setPiece(new Pawn(Color.NEGRO));
        
        tablero.getEscaque(5, 1).setPiece(new Pawn(Color.BLANCO));
        tablero.getEscaque(10, 1).setPiece(new Pawn(Color.BLANCO));        
        tablero.getEscaque(5, 15).setPiece(new Pawn(Color.NEGRO));
        tablero.getEscaque(10, 15).setPiece(new Pawn(Color.NEGRO));
                
        tablero.getEscaque(6, 1).setPiece(new MadPawn(Color.BLANCO));
        tablero.getEscaque(9, 1).setPiece(new MadPawn(Color.BLANCO));        
        tablero.getEscaque(6, 15).setPiece(new MadPawn(Color.NEGRO));
        tablero.getEscaque(9, 15).setPiece(new MadPawn(Color.NEGRO));
        
        tablero.getEscaque(7, 1).setPiece(new SuperPawn(Color.BLANCO));
        tablero.getEscaque(8, 1).setPiece(new SuperPawn(Color.BLANCO));        
        tablero.getEscaque(7, 15).setPiece(new SuperPawn(Color.NEGRO));
        tablero.getEscaque(8, 15).setPiece(new SuperPawn(Color.NEGRO));
        
        tablero.getEscaque(0, 2).setPiece(new Ballista(Color.BLANCO));
        tablero.getEscaque(15, 2).setPiece(new Ballista(Color.BLANCO));        
        tablero.getEscaque(0, 14).setPiece(new Ballista(Color.NEGRO));
        tablero.getEscaque(15, 14).setPiece(new Ballista(Color.NEGRO));
        
        tablero.getEscaque(1, 2).setPiece(new Archer(Color.BLANCO));
        tablero.getEscaque(14, 2).setPiece(new Archer(Color.BLANCO));        
        tablero.getEscaque(1, 14).setPiece(new Archer(Color.NEGRO));
        tablero.getEscaque(14, 14).setPiece(new Archer(Color.NEGRO));
        
        tablero.getEscaque(2, 2).setPiece(new Archer(Color.BLANCO));
        tablero.getEscaque(13, 2).setPiece(new Archer(Color.BLANCO));        
        tablero.getEscaque(2, 14).setPiece(new Archer(Color.NEGRO));
        tablero.getEscaque(13, 14).setPiece(new Archer(Color.NEGRO));
        
        tablero.getEscaque(3, 2).setPiece(new ShieldBearer(Color.BLANCO));
        tablero.getEscaque(12, 2).setPiece(new ShieldBearer(Color.BLANCO));        
        tablero.getEscaque(3, 14).setPiece(new ShieldBearer(Color.NEGRO));
        tablero.getEscaque(12, 14).setPiece(new ShieldBearer(Color.NEGRO));
        
        tablero.getEscaque(0, 3).setPiece(new Pawn(Color.BLANCO));
        tablero.getEscaque(15, 3).setPiece(new Pawn(Color.BLANCO));        
        tablero.getEscaque(0, 13).setPiece(new Pawn(Color.NEGRO));
        tablero.getEscaque(15, 13).setPiece(new Pawn(Color.NEGRO));
        
        tablero.getEscaque(1, 3).setPiece(new MadPawn(Color.BLANCO));
        tablero.getEscaque(14, 3).setPiece(new MadPawn(Color.BLANCO));        
        tablero.getEscaque(1, 13).setPiece(new MadPawn(Color.NEGRO));
        tablero.getEscaque(14, 13).setPiece(new MadPawn(Color.NEGRO));
        
        tablero.getEscaque(2, 3).setPiece(new Pawn(Color.BLANCO));
        tablero.getEscaque(13, 3).setPiece(new Pawn(Color.BLANCO));        
        tablero.getEscaque(2, 13).setPiece(new Pawn(Color.NEGRO));
        tablero.getEscaque(13, 13).setPiece(new Pawn(Color.NEGRO));
        
        BoardDisplay tv = new BoardDisplay(tablero);
        tv.mostrar();
    }
}
