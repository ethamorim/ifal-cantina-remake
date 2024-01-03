package br.ethamorim.cantina.ifal;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame {

    final JPanel opcoesPainel = new JPanel();
    final SecoesPainel cards = new SecoesPainel();

    private MainFrame() {
        definirConfiguracoesPrincipais();
        registrarComponentes();
        renderizarFrame();
    }

    private void definirConfiguracoesPrincipais() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cantina IFAL");
        setMinimumSize(new Dimension(960, 600));
    }

    private void registrarComponentes() {
        opcoesPainel.setPreferredSize(new Dimension(200, 0));
        opcoesPainel.setBackground(new Color(92, 92, 92));

        Container principalPainel = getContentPane();
        principalPainel.add(opcoesPainel, BorderLayout.LINE_END);
        principalPainel.add(cards, BorderLayout.CENTER);
    }

    private void renderizarFrame() {
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
