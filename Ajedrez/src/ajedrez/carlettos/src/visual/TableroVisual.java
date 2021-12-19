package ajedrez.carlettos.src.visual;

import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.util.MousePieza;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TableroVisual extends JFrame {

    private final EscaqueVisual[][] grid;
    private final JPanel rootPanel;
    private final TableroManager tablero;
    private final RelojVisual reloj;
    private final CartasVisual cartas;

    public TableroVisual(TableroManager tablero, RelojVisual reloj) throws HeadlessException {
        super("Ajedrez");
        this.grid = new EscaqueVisual[tablero.filas][tablero.columnas];
        this.rootPanel = new JPanel(new BorderLayout());
        this.tablero = tablero;
        this.reloj = reloj;
        this.cartas = new CartasVisual(reloj.getReloj());
        setup();
    }

    protected void setup() {
        reloj.setPreferredSize(new Dimension(tablero.columnas * 45, 200));
        rootPanel.add(reloj, BorderLayout.PAGE_START);
        JPanel panel = new JPanel(new GridLayout(tablero.filas, tablero.columnas));
        for (int y = tablero.filas - 1; y >= 0; y--) {
            for (int x = 0; x < tablero.columnas; x++) {
                EscaqueVisual ev = new EscaqueVisual(tablero.getEscaque(x, y));
                ev.addMouseListener(MousePieza.LISTENER);
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

    public TableroManager getTablero() {
        return tablero;
    }

    public RelojVisual getReloj() {
        return reloj;
    }

    public CartasVisual getCartas() {
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
    public EscaqueVisual getEscaque(Point punto) {
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
    public EscaqueVisual getEscaque(int x, int y) {
        return getEscaque(new Point(x, y));
    }
}
