package com.carlettos.game.display.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Carlettos
 */
public class MainMenu extends JFrame {
    private JPanel start;
    private JPanel options;

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
        var op = new JButton("Opciones");
        op.addActionListener(e -> options());
        var ju = new JButton("Jugar");
        ju.addActionListener(e -> play());
        start.add(op);
        start.add(Box.createVerticalGlue());
        start.add(ju);
        start.add(Box.createVerticalGlue());
        start.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        start.setPreferredSize(new Dimension(400, 400));
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
        var returnB = new JButton("Volver");
        returnB.addActionListener(e -> start());
        options.add(Box.createVerticalGlue()); //todo: config
        options.add(new JLabel("ola"));
        options.add(Box.createVerticalGlue());
        options.add(new JLabel("chau"));
        options.add(Box.createVerticalGlue());
        options.add(returnB);
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
        
    }
}
