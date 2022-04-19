package com.carlettos.game.board.manager.clock.event;

import com.carlettos.game.board.manager.Board;
import com.carlettos.game.core.Point;

/**
 * The info of an event.
 *
 * @author Carlettos
 */
public class EventInfo {
    protected int turns;
    protected final String name;
    protected Point point;
    protected final Board board;

    /**
     * It creates all the info for the event to work.
     *
     * @param turns time in turns to the event to happen.
     * @param name name of the event.
     * @param point point of refference.
     * @param board board in which will happen.
     */
    public EventInfo(int turns, String name, Point point, Board board) {
        if (turns <= 0) {
            throw new IllegalArgumentException("La cantidad de turnos de un evento no puede ser 0 o negativa");
        }
        this.turns = turns;
        this.name = name;
        this.board = board;
        this.point = point;
    }
    
    /**
     * Reduces the turns by 1.
     */
    public void reduceTurn(){
        this.turns--;
    }

    public int getTurns() {
        return turns;
    }

    public String getName() {
        return name;
    }

    public Point getPoint() {
        return point;
    }

    public Board getBoard() {
        return board;
    }
    
    public static EventInfo of(Board board, int turns, String name, Point point){
        return new EventInfo(turns, name, point, board);
    }
    
    public static EventInfo of(Board board, int turns, String name){
        return of(board, turns, name, new Point(0, 0));
    }
    
    public static EventInfo of(Board board, int turns, Point point){
        return of(board, turns, "Generic event", point);
    }
    
    public static EventInfo of(Board board, String name, Point point){
        return of(board, 1, name, point);
    }
    
    public static EventInfo of(Board board, int turns){
        return of(board, turns, "Generic event", new Point(0, 0));
    }
    
    public static EventInfo of(Board board, String name){
        return of(board, 1, name, new Point(0, 0));
    }
    
    public static EventInfo of(Board board, Point point){
        return of(board, 1, "Generic event", point);
    }
    
    public static EventInfo of(Board board){
        return of(board, 1, "Generic event", new Point(0, 0));
    }
}
