package com.carlettos.game.display.main;

import com.carlettos.game.core.helper.ConfigHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.awt.Dimension;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Carlettos
 */
public class ConfigEntryPanel extends JPanel {
    public static boolean CHANGES = false;
    protected Map.Entry<String, JsonElement> entry;
    private JLabel property;
    private JTextField value;

    public ConfigEntryPanel(Map.Entry<String, JsonElement> entry) {
        super();
        this.entry = entry;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.updateComponents();
        add(property);
        add(Box.createHorizontalGlue());
        add(value);
    }
    
    private void updateComponents(){
        property = new JLabel(entry.getKey() + ": ");
        value = new JTextField(entry.getValue().toString());
        value.setMaximumSize(new Dimension(100, 20));
    }
    
    public void saveConfig(){
        if(!entry.getValue().toString().equals(value.getText())) {
            ConfigHelper.getInstance().setNumberConfig(entry.getKey(), Double.parseDouble(value.getText()));
            this.entry.setValue(new JsonPrimitive(Double.parseDouble(value.getText())));
            CHANGES = true;
        }
    }
}
