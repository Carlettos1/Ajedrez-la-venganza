package com.carlettos.game.display.board;

import com.carlettos.game.core.Action;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.core.helper.ConfigHelper;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class EscaqueDisplay extends Component {

    private final Escaque escaque;
    private final boolean isEven;
    private final List<Action> actions;

    public EscaqueDisplay(Escaque escaque) {
        this.actions = new ArrayList<>();
        this.escaque = escaque;
        isEven = (escaque.getPos().x + escaque.getPos().y) % 2 == 0;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ConfigHelper.get().getInt("escaque_lenght"), ConfigHelper.get().getInt("escaque_lenght"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor((isEven ? Color.values()[ConfigHelper.get().getInt("color_2_id")]
                : Color.values()[ConfigHelper.get().getInt("color_1_id")]).getAWT());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor((!isEven ? Color.values()[ConfigHelper.get().getInt("color_2_id")] 
                : Color.values()[ConfigHelper.get().getInt("color_1_id")]).getAWT());
        
        if(escaque.hasPiece()){ //todo: registrar imagenes
            Image img = Toolkit.getDefaultToolkit().getImage("./src/com/carlettos/resources/textures/" +
                    escaque.getPiece().getName().toLowerCase() + "_" + escaque.getPieceColor().name().toLowerCase() + ".png");
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
        
        if(this.actions.isEmpty()) {
            return;
        }

        double w = ConfigHelper.get().getDouble("percentage_" + this.actions.size()) * getWidth();
        double h = ConfigHelper.get().getDouble("percentage_" + this.actions.size()) * getHeight();
        double x = (getWidth() - w) / 2D;
        double y = (getHeight() - h) / 2D;
        switch (this.actions.size()) {
            case 1:
                g.setColor(actions.get(0).getColor().getAWT());
                g.fillOval((int) x, (int) y, (int) w, (int) h);
                break;
            case 2:
                g.setColor(actions.get(0).getColor().getAWT());
                g.fillOval((int) (x / 2D), (int) (y / 2D),
                        (int) (w), (int) (h));
                
                g.setColor(actions.get(1).getColor().getAWT());
                g.fillOval((int) (x * 1.5D), (int) (y * 1.5D),
                        (int) (w), (int) (h));
                break;
            case 3:
                x = (getWidth() - w) / 2D;
                y = (getHeight() - h) / 2D;
                
                g.setColor(actions.get(0).getColor().getAWT());
                g.fillOval((int) (x), (int) (y / 2D),
                        (int) (w), (int) (h));
                
                g.setColor(actions.get(1).getColor().getAWT());
                g.fillOval((int) (x * 1.5D), (int) (y * 1.5D),
                        (int) (w), (int) (h));
                
                g.setColor(actions.get(2).getColor().getAWT());
                g.fillOval((int) (x / 2D), (int) (y * 1.5D),
                        (int) (w), (int) (h));
                break;
            case 4:
                x = (getWidth() - w) / 2D;
                y = (getHeight() - h) / 2D;
                
                g.setColor(actions.get(0).getColor().getAWT());
                g.fillOval((int) (x / 3D), (int) (y / 3D),
                        (int) (w), (int) (h));
                
                g.setColor(actions.get(1).getColor().getAWT());
                g.fillOval((int) (x * 5/3D), (int) (y / 3D),
                        (int) (w), (int) (h));
                
                g.setColor(actions.get(2).getColor().getAWT());
                g.fillOval((int) (x / 3D), (int) (y * 5/3D),
                        (int) (w), (int) (h));
                
                g.setColor(actions.get(3).getColor().getAWT());
                g.fillOval((int) (x * 5/3D), (int) (y * 5/3D),
                        (int) (w), (int) (h));
                break;
            default:
                System.err.println("Cantidad de acciones: " + this.actions.size() + ", no manejada");
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
