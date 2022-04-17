package com.carlettos.game.input;

import com.carlettos.game.core.Tuple;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.Clock;
import com.carlettos.game.visual.CardDisplay;
import com.carlettos.game.visual.EscaqueDisplay;
import com.carlettos.game.visual.BoardDisplay;
import java.awt.Component;
import java.awt.Container;
import com.carlettos.game.core.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Listener implementation class.
 * 
 * @author Carlos
 */
public class MouseCard implements MouseListener {
    private static final MouseCard LISTENER = new MouseCard();
    
    private MouseCard(){
    }
    
    public static MouseCard get(){
        return LISTENER;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CardDisplay ecv = (CardDisplay) (e.getSource());
        
        BoardDisplay tablero = ListenerHelper.getTableroVisual(ecv);
        
        Component escaqueVisual = tablero.getComponentAt(e.getXOnScreen() - tablero.getX(), e.getYOnScreen() - tablero.getY());
        while (escaqueVisual instanceof Container) {
            Point componentLocation = getAbsoluteLocation(escaqueVisual);
            escaqueVisual = getComponentAt(escaqueVisual, e.getXOnScreen() - componentLocation.x,
                    e.getYOnScreen() - componentLocation.y);
        }
        
        if (escaqueVisual instanceof EscaqueDisplay) {
            EscaqueDisplay escaque = (EscaqueDisplay) escaqueVisual;
            Clock reloj = tablero.getRelojVisual().getReloj();
            
            List<Player> jugadores = new ArrayList<>();
            reloj.getJugadores().forEach((jugador) -> {
                if (jugador.getColor().equals(ecv.getColor())) {
                    jugadores.add(jugador);
                }
            });
            reloj.getJugadores().forEach((jugador) -> {
                if (!jugador.getColor().equals(ecv.getColor())) {
                    jugadores.add(jugador);
                }
            });
            
            Tuple<Boolean, String> can = ecv.getCarta().canUsarCarta(escaque.getEscaque().getPos(),
                    tablero.getTablero(),
                    reloj,
                    jugadores.toArray(new Player[0]));
            if (can.x) {
                ecv.getCarta().usarCarta(escaque.getEscaque().getPos(),
                        tablero.getTablero(),
                        reloj,
                        jugadores.toArray(new Player[0]));
                tablero.getManoVisual().rehacer();
                tablero.getRelojVisual().repaint();
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
