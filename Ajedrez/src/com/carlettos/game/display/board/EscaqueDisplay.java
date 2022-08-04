package com.carlettos.game.display.board;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.carlettos.game.board.Escaque;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.helper.ConfigHelper;
import com.carlettos.game.util.helper.ImageHelper;
import com.carlettos.game.util.helper.LogHelper;

/**
 *
 * @author Carlos
 */
public class EscaqueDisplay extends JComponent {
    private static final long serialVersionUID = -8856530496715601233L;
    // todo: transient?
    private final transient Escaque escaque;
    private final boolean isEven;
    private final List<Action> actions;

    public EscaqueDisplay(Escaque escaque) {
        this.actions = new ArrayList<>();
        this.escaque = escaque;
        isEven = (escaque.getPos().x + escaque.getPos().y) % 2 == 0;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ConfigHelper.getEscaqueLength(), ConfigHelper.getEscaqueLength());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor((isEven ? ConfigHelper.getColor2() : ConfigHelper.getColor1()).getAWT());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor((!isEven ? ConfigHelper.getColor2() : ConfigHelper.getColor1()).getAWT());

        if (escaque.hasPiece()) {
            StringBuilder tooltipText = new StringBuilder(escaque.getPiece().toString());
            g.drawImage(ImageHelper.getImage(escaque.getPiece()), 0, 0, getWidth(), getHeight(), this);

            // TODO: externalizar a configuración los .1
            int q = 0;
            for (var effect : escaque.getPiece().getEffectManager().getEffects()) {
                g.drawImage(ImageHelper.getImage(effect, "effect\\"), (int) (.3 * getWidth() * q), 0,
                        (int) (.3 * getWidth()), (int) (.3 * getHeight()), this);
                tooltipText.append('(').append(effect.getName()).append(')');
                q++;
            }

            this.setToolTipText(tooltipText.toString());
        }

        if (this.actions.isEmpty()) { return; }

        double w = ConfigHelper.getPercentage(this.actions.size()) * getWidth();
        double h = ConfigHelper.getPercentage(this.actions.size()) * getHeight();
        double x = (getWidth() - w) / 2D;
        double y = (getHeight() - h) / 2D;
        switch (this.actions.size()) {
            case 1:
                g.setColor(actions.get(0).getColor().getAWT());
                g.fillOval((int) x, (int) y, (int) w, (int) h);
                break;
            case 2:
                g.setColor(actions.get(0).getColor().getAWT());
                g.fillOval((int) (x / 2D), (int) (y / 2D), (int) (w), (int) (h));

                g.setColor(actions.get(1).getColor().getAWT());
                g.fillOval((int) (x * 1.5D), (int) (y * 1.5D), (int) (w), (int) (h));
                break;
            case 3:
                x = (getWidth() - w) / 2D;
                y = (getHeight() - h) / 2D;

                g.setColor(actions.get(0).getColor().getAWT());
                g.fillOval((int) (x), (int) (y / 2D), (int) (w), (int) (h));

                g.setColor(actions.get(1).getColor().getAWT());
                g.fillOval((int) (x * 1.5D), (int) (y * 1.5D), (int) (w), (int) (h));

                g.setColor(actions.get(2).getColor().getAWT());
                g.fillOval((int) (x / 2D), (int) (y * 1.5D), (int) (w), (int) (h));
                break;
            case 4:
                x = (getWidth() - w) / 2D;
                y = (getHeight() - h) / 2D;

                g.setColor(actions.get(0).getColor().getAWT());
                g.fillOval((int) (x / 3D), (int) (y / 3D), (int) (w), (int) (h));

                g.setColor(actions.get(1).getColor().getAWT());
                g.fillOval((int) (x * 5 / 3D), (int) (y / 3D), (int) (w), (int) (h));

                g.setColor(actions.get(2).getColor().getAWT());
                g.fillOval((int) (x / 3D), (int) (y * 5 / 3D), (int) (w), (int) (h));

                g.setColor(actions.get(3).getColor().getAWT());
                g.fillOval((int) (x * 5 / 3D), (int) (y * 5 / 3D), (int) (w), (int) (h));
                break;
            default:
                LogHelper.LOG.severe(() -> "Cantidad de acciones: " + this.actions.size() + ", no manejada");
        }
    }

    public void setAction(Action action) {
        this.actions.add(action);
        repaint();
    }

    public void removeAction(Action action) {
        if (this.actions.contains(action)) {
            this.actions.remove(action);
            repaint();
        }
    }

    public void removeAllActions() {
        this.actions.clear();
        repaint();
    }

    public Escaque getEscaque() {
        return escaque;
    }
}
