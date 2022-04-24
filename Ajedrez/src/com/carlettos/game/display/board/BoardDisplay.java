package com.carlettos.game.display.board;

import com.carlettos.game.input.MousePiece;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.core.Point;
import com.carlettos.game.core.helper.ConfigHelper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public class BoardDisplay extends JPanel {
    private static BoardDisplay INSTANCE;

    private final EscaqueDisplay[][] grid;
    private final Board board;
    private final ClockDisplay clock;
    private final HandDisplay cards;

    private BoardDisplay(Board board) {
        super(new BorderLayout());
        this.grid = new EscaqueDisplay[board.rows][board.columns];
        this.board = board;
        this.clock = new ClockDisplay(board.getClock());
        this.cards = new HandDisplay(board.getClock());
        setup();
    }
    
    public static BoardDisplay createInstance(Board board) {
        if(INSTANCE == null) {
            INSTANCE = new BoardDisplay(board);
        }
        return INSTANCE;
    }
    
    public static BoardDisplay getInstance() {
        if(INSTANCE == null) {
            throw new NullPointerException("BoardDisplay instance not ready yet");
        }
        return INSTANCE;
    }

    protected void setup() {
        clock.setPreferredSize(new Dimension(board.columns * ConfigHelper.get().getInt("escaque_lenght"), ConfigHelper.get().getInt("clock_height")));
        this.add(clock, BorderLayout.PAGE_START);
        JPanel panel = new JPanel(new GridLayout(board.rows, board.columns));
        for (int y = board.rows - 1; y >= 0; y--) {
            for (int x = 0; x < board.columns; x++) {
                EscaqueDisplay ev = new EscaqueDisplay(board.getEscaque(x, y));
                ev.addMouseListener(MousePiece.get());
                grid[y][x] = ev;
                panel.add(ev);
            }
        }
        this.add(panel, BorderLayout.CENTER);
        this.add(cards, BorderLayout.LINE_END);
    }

    public Board getBoard() {
        return board;
    }

    public ClockDisplay getClockDisplay() {
        return clock;
    }

    public HandDisplay getManoVisual() {
        return cards;
    }

    public void offAll() {
        for (EscaqueDisplay[] escaqueVisuals : grid) {
            for (EscaqueDisplay escaqueVisual : escaqueVisuals) {
                escaqueVisual.removeAllActions();
            }
        }
    }
    
    public EscaqueDisplay getEscaqueVisual(Point point) {
        if (point.x < 0) {
            throw new IllegalArgumentException("La coordenada x no puede ser negativa");
        }
        if (point.y < 0) {
            throw new IllegalArgumentException("La coordenada y no puede ser negativa");
        }
        if (point.x >= board.columns) {
            throw new IllegalArgumentException("La coordenada x no puede ser mayor o igual que el número de columnas");
        }
        if (point.y >= board.rows) {
            throw new IllegalArgumentException("La coordenada y no puede ser mayor o igual que el número de filas");
        }
        return grid[point.y][point.x];
    }

    public EscaqueDisplay getEscaqueVisual(int x, int y) {
        return BoardDisplay.this.getEscaqueVisual(new Point(x, y));
    }
}
