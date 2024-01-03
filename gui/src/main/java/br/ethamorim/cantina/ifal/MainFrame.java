package br.ethamorim.cantina.ifal;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame {

    MainFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cantina IFAL");
        setMinimumSize(new Dimension(600, 400));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
            UIManager.setLookAndFeel(new FlatLightLaf());
            new MainFrame();
        } catch (UnsupportedLookAndFeelException e) {
            Logger.getLogger(UnsupportedLookAndFeelException.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
