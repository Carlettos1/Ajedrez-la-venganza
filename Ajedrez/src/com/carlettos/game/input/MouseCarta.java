package com.carlettos.game.input;

import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.visual.CartaVisual;
import com.carlettos.game.visual.EscaqueVisual;
import com.carlettos.game.visual.TableroVisual;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MouseCarta implements MouseListener {

    public static final MouseListener LISTENER = new MouseCarta();

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CartaVisual ecv = (CartaVisual) (e.getSource());
        Container container = ecv;
        while (container.getParent() != null) {
            container = container.getParent();
        }
        Component component = container.getComponentAt(e.getXOnScreen() - container.getX(), e.getYOnScreen() - container.getY());
        while (component instanceof Container) {
            Point componentLocation = getAbsoluteLocation(component);
            component = getComponentAt(component, e.getXOnScreen() - componentLocation.x,
                    e.getYOnScreen() - componentLocation.y);
        }
        if (component instanceof EscaqueVisual) {
            EscaqueVisual escaque = (EscaqueVisual) component;
            TableroVisual tablero = (TableroVisual) container;
            Reloj reloj = tablero.getReloj().getReloj();
            List<Jugador> jugadores = new ArrayList<>();
            reloj.getJugadores().forEach((jugador) -> {
                if (jugador.getColor().getColor().equals(ecv.getColor())) {
                    jugadores.add(jugador);
                }
            });
            reloj.getJugadores().forEach((jugador) -> {
                if (!jugador.getColor().getColor().equals(ecv.getColor())) {
                    jugadores.add(jugador);
                }
            });
            Par<Boolean, String> can = ecv.getCarta().canUsarCarta(escaque.getEscaque().getLocalizacion(),
                    tablero.getTablero(),
                    reloj,
                    jugadores.toArray(new Jugador[0]));
            if (can.x) {
                ecv.getCarta().usarCarta(escaque.getEscaque().getLocalizacion(),
                        tablero.getTablero(),
                        reloj,
                        jugadores.toArray(new Jugador[0]));
                tablero.getCartas().rehacer();
                tablero.getReloj().repaint();
            } else {
                System.out.println(can.y);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private static Component getComponentAt(Component component, Point point) {
        return getComponentAt(component, point.x, point.y);
    }

    private static Component getComponentAt(Component component, int x, int y) {
        if (!(component instanceof Container)) {
            return component;
        }
        Container container = (Container) component;
        for (Component comp : container.getComponents()) {
            if (comp instanceof Container) {
                if (((Container) (comp)).getComponents().length == 0) {
                    continue;
                }
            }
            if (comp.contains(x - comp.getX(), y - comp.getY())) {
                return comp;
            }
        }
        return null;
    }

    private static Point getAbsoluteLocation(Component component) {
        int x = component.getX(), y = component.getY();
        Container container = component.getParent();
        while (container.getParent() != null) {
            x += container.getX();
            y += container.getY();
            container = container.getParent();
        }
        return new Point(x + container.getX(), y + container.getY());
    }
}
