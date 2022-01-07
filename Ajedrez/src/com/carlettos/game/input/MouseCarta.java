package com.carlettos.game.input;

import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Reloj;
import com.carlettos.game.visual.CartaVisual;
import com.carlettos.game.visual.EscaqueVisual;
import com.carlettos.game.visual.TableroVisual;
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
public class MouseCarta implements MouseListener {
    private static final MouseCarta LISTENER = new MouseCarta();
    
    private MouseCarta(){
    }
    
    public static MouseCarta get(){
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
        CartaVisual ecv = (CartaVisual) (e.getSource());
        
        TableroVisual tablero = ListenerHelper.getTableroVisual(ecv);
        
        Component escaqueVisual = tablero.getComponentAt(e.getXOnScreen() - tablero.getX(), e.getYOnScreen() - tablero.getY());
        while (escaqueVisual instanceof Container) {
            Point componentLocation = getAbsoluteLocation(escaqueVisual);
            escaqueVisual = getComponentAt(escaqueVisual, e.getXOnScreen() - componentLocation.x,
                    e.getYOnScreen() - componentLocation.y);
        }
        
        if (escaqueVisual instanceof EscaqueVisual) {
            EscaqueVisual escaque = (EscaqueVisual) escaqueVisual;
            Reloj reloj = tablero.getRelojVisual().getReloj();
            
            List<Jugador> jugadores = new ArrayList<>();
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
            
            Par<Boolean, String> can = ecv.getCarta().canUsarCarta(escaque.getEscaque().getLocalizacion(),
                    tablero.getTablero(),
                    reloj,
                    jugadores.toArray(new Jugador[0]));
            if (can.x) {
                ecv.getCarta().usarCarta(escaque.getEscaque().getLocalizacion(),
                        tablero.getTablero(),
                        reloj,
                        jugadores.toArray(new Jugador[0]));
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
