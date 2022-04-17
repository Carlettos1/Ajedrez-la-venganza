package com.carlettos.game.visual;

import com.carlettos.game.input.MousePiece;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.core.Point;
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
public class BoardDisplay extends JFrame{

    private final EscaqueDisplay[][] grid;
    private final JPanel rootPanel;
    private final Board tablero;
    private final ClockDisplay reloj;
    private final HandDisplay cartas;

    public BoardDisplay(Board tablero) throws HeadlessException {
        super("Ajedrez");
        this.grid = new EscaqueDisplay[tablero.filas][tablero.columnas];
        this.rootPanel = new JPanel(new BorderLayout());
        this.tablero = tablero;
        this.reloj = new ClockDisplay(tablero.getClock());
        this.cartas = new HandDisplay(tablero.getClock());
        setup();
    }

    protected void setup() {
        reloj.setPreferredSize(new Dimension(tablero.columnas * 45, 200));
        rootPanel.add(reloj, BorderLayout.PAGE_START);
        JPanel panel = new JPanel(new GridLayout(tablero.filas, tablero.columnas));
        for (int y = tablero.filas - 1; y >= 0; y--) {
            for (int x = 0; x < tablero.columnas; x++) {
                EscaqueDisplay ev = new EscaqueDisplay(tablero.getEscaque(x, y));
                ev.addMouseListener(MousePiece.get());
                grid[y][x] = ev;
                panel.add(ev);
            }
        }
        rootPanel.add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        rootPanel.add(cartas, BorderLayout.LINE_END);
        add(rootPanel);
    }

    public void mostrar() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Board getBoard() {
        return tablero;
    }

    public ClockDisplay getClockDisplay() {
        return reloj;
    }

    public HandDisplay getManoVisual() {
        return cartas;
    }

    public void offAll() {
        for (EscaqueDisplay[] escaqueVisuals : grid) {
            for (EscaqueDisplay escaqueVisual : escaqueVisuals) {
                escaqueVisual.offAccion();
            }
        }
    }

    /**
     * Da el escaque en la posición especificada.
     *
     * @param punto punto del cuál se quiere sacar el escaque.
     *
     * @return el escaque en la posición solicidada.
     *
     * @throws IllegalArgumentException si alguna coordenada se sale de las
     * dimensiones del tablero.
     *
     * @see EscaqueDisplay
     */
    public EscaqueDisplay getEscaqueVisual(Point punto) {
        if (punto.x < 0) {
            throw new IllegalArgumentException("La coordenada x no puede ser negativa");
        }
        if (punto.y < 0) {
            throw new IllegalArgumentException("La coordenada y no puede ser negativa");
        }
        if (punto.x >= tablero.columnas) {
            throw new IllegalArgumentException("La coordenada x no puede ser mayor o igual que el número de columnas");
        }
        if (punto.y >= tablero.filas) {
            throw new IllegalArgumentException("La coordenada y no puede ser mayor o igual que el número de filas");
        }
        return grid[punto.y][punto.x];
    }

    /**
     * Da el escaque en la posición especificada.
     *
     * @param x coordenada x de la posición.
     * @param y coordenada y de la posición.
     *
     * @return el escaque en la posición solicidada.
     *
     * @throws IllegalArgumentException si alguna coordenada se sale de las
     * dimensiones del tablero.
     *
     * @see EscaqueDisplay
     */
    public EscaqueDisplay getEscaqueVisual(int x, int y) {
        return BoardDisplay.this.getEscaqueVisual(new Point(x, y));
    }
}
