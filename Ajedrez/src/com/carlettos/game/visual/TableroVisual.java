package com.carlettos.game.visual;

import com.carlettos.game.input.MousePieza;
import com.carlettos.game.tablero.manager.Tablero;
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
public class TableroVisual extends JFrame{

    private final EscaqueVisual[][] grid;
    private final JPanel rootPanel;
    private final Tablero tablero;
    private final RelojVisual reloj;
    private final ManoVisual cartas;

    public TableroVisual(Tablero tablero, RelojVisual reloj) throws HeadlessException {
        super("Ajedrez");
        this.grid = new EscaqueVisual[tablero.filas][tablero.columnas];
        this.rootPanel = new JPanel(new BorderLayout());
        this.tablero = tablero;
        this.reloj = reloj;
        this.cartas = new ManoVisual(reloj.getReloj());
        setup();
    }

    protected void setup() {
        reloj.setPreferredSize(new Dimension(tablero.columnas * 45, 200));
        rootPanel.add(reloj, BorderLayout.PAGE_START);
        JPanel panel = new JPanel(new GridLayout(tablero.filas, tablero.columnas));
        for (int y = tablero.filas - 1; y >= 0; y--) {
            for (int x = 0; x < tablero.columnas; x++) {
                EscaqueVisual ev = new EscaqueVisual(tablero.getEscaque(x, y));
                ev.addMouseListener(MousePieza.get());
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

    public Tablero getTablero() {
        return tablero;
    }

    public RelojVisual getRelojVisual() {
        return reloj;
    }

    public ManoVisual getManoVisual() {
        return cartas;
    }

    public void offAll() {
        for (EscaqueVisual[] escaqueVisuals : grid) {
            for (EscaqueVisual escaqueVisual : escaqueVisuals) {
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
     * @see EscaqueVisual
     */
    public EscaqueVisual getEscaqueVisual(Point punto) {
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
     * @see EscaqueVisual
     */
    public EscaqueVisual getEscaqueVisual(int x, int y) {
        return TableroVisual.this.getEscaqueVisual(new Point(x, y));
    }
}
