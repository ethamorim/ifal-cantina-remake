package br.ethamorim.cantina.ifal;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame {

    final OpcoesPainel opcoesPainel = new OpcoesPainel();
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
        EventQueue.invokeLater(() -> {
            try {
                FlatLightLaf.setup();
                UIManager.setLookAndFeel(new FlatLightLaf());
                new MainFrame();
            } catch (UnsupportedLookAndFeelException e) {
                Logger.getLogger(UnsupportedLookAndFeelException.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }
}
