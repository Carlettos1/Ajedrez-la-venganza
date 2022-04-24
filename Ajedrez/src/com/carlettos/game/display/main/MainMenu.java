package com.carlettos.game.display.main;

import com.carlettos.game.board.Board;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.util.helper.ConfigHelper;
import com.carlettos.game.util.helper.FileHelper;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Carlettos
 */
public class MainMenu extends JFrame {
    private JPanel start;
    private JPanel options;
    private JPanel play;

    public MainMenu() {
        super("Ajedrez");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        startInit();
        add(start);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void startInit(){
        start = new JPanel();
        start.setLayout(new BoxLayout(start, BoxLayout.PAGE_AXIS));
        var optBttn = new JButton("Opciones");
        optBttn.addActionListener(e -> options());
        var playBttn = new JButton("Jugar");
        playBttn.addActionListener(e -> play());
        start.add(optBttn);
        start.add(Box.createVerticalGlue());
        start.add(playBttn);
        start.add(Box.createVerticalGlue());
        start.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        start.setPreferredSize(new Dimension(400, 400));
    }
    
    private void addOptions() {
        for (var entry : ConfigHelper.getConfigEntries()) {
            options.add(Box.createVerticalGlue());
            var entryPanel = new ConfigEntryPanel(entry);
            options.add(entryPanel);
        }
    }
    
    private void start(){
        this.startInit();
        this.removeAll();
        this.revalidate();
        this.frameInit();
        this.add(start);
        pack();
    }
    
    private void options(){
        options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.PAGE_AXIS));
        var goBack = new JButton("Volver");
        goBack.addActionListener(e -> start());
        var save = new JButton("Save");
        save.addActionListener(e -> {
            for (Component component : options.getComponents()) {
                if(component instanceof ConfigEntryPanel cep){
                    cep.saveConfig();
                }
            }
            if(ConfigEntryPanel.CHANGES) {
                FileHelper.updateHelpers();
                ConfigEntryPanel.CHANGES = false;
            }
        });
        this.addOptions();
        options.add(Box.createVerticalGlue());
        var buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(goBack);
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(save);
        buttonsPanel.add(Box.createHorizontalGlue());
        options.add(buttonsPanel);
        options.add(Box.createVerticalGlue());
        options.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        options.setPreferredSize(new Dimension(400, 400));
        this.removeAll();
        this.revalidate();
        this.frameInit();
        this.add(options);
        this.pack();
    }
    
    private void play(){
        var board = Board.getDefaultInstance();
        play = BoardDisplay.createInstance(board);
        this.removeAll();
        this.revalidate();
        this.frameInit();
        this.add(play);
        this.pack();
    }
}
