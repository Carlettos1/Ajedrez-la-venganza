package com.carlettos.game.display.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.display.listeners.MousePiece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.helper.ConfigHelper;

/**
 *
 * @author Carlos
 */
public class BoardDisplay extends JPanel {
    private static final long serialVersionUID = 7741137468814114783L;
    private static BoardDisplay instance;
    private final EscaqueDisplay[][] grid;
    private final transient SquareBoard board;
    private final ClockDisplay clock;
    private final HandDisplay cards;

    private BoardDisplay(SquareBoard board) {
        super(new BorderLayout());
        this.grid = new EscaqueDisplay[board.getShape().getBoundingRectangle().y][board.getShape()
                .getBoundingRectangle().x];
        this.board = board;
        this.clock = new ClockDisplay(board.getClock());
        this.cards = new HandDisplay(board.getClock());
        setup();
    }

    public static BoardDisplay createInstance(SquareBoard board) {
        if (instance == null) {
            instance = new BoardDisplay(board);
        }
        return instance;
    }

    public static BoardDisplay getInstance() {
        if (instance == null) { throw new NullPointerException("BoardDisplay instance not ready yet"); }
        return instance;
    }

    protected void setup() {
        clock.setPreferredSize(
                new Dimension(board.getShape().getBoundingRectangle().x * ConfigHelper.getEscaqueLength(),
                        ConfigHelper.getClockHeight()));
        this.add(clock, BorderLayout.PAGE_START);
        JPanel panel = new JPanel(
                new GridLayout(board.getShape().getBoundingRectangle().y, board.getShape().getBoundingRectangle().x));
        for (int y = board.getShape().getBoundingRectangle().y - 1; y >= 0; y--) {
            for (int x = 0; x < board.getShape().getBoundingRectangle().x; x++) {
                EscaqueDisplay ev = new EscaqueDisplay(board.get(new Point(x, y)));
                ev.addMouseListener(MousePiece.get());
                grid[y][x] = ev;
                panel.add(ev);
            }
        }
        this.add(panel, BorderLayout.CENTER);
        this.add(cards, BorderLayout.LINE_END);
    }

    public SquareBoard getBoard() {
        return board;
    }

    public ClockDisplay getClockDisplay() {
        return clock;
    }

    public HandDisplay getHandDisplay() {
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
        if (point.x < 0) { throw new IllegalArgumentException("La coordenada x no puede ser negativa"); }
        if (point.y < 0) { throw new IllegalArgumentException("La coordenada y no puede ser negativa"); }
        if (point.x >= board.getShape().getBoundingRectangle().x) {
            throw new IllegalArgumentException("La coordenada x no puede ser mayor o igual que el número de columnas");
        }
        if (point.y >= board.getShape().getBoundingRectangle().y) {
            throw new IllegalArgumentException("La coordenada y no puede ser mayor o igual que el número de filas");
        }
        return grid[point.y][point.x];
    }

    public EscaqueDisplay getEscaqueVisual(int x, int y) {
        return BoardDisplay.this.getEscaqueVisual(new Point(x, y));
    }
}
