package br.ethamorim.cantina.ifal;

import br.ethamorim.cantina.ifal.enums.NavPrincipal;

import javax.swing.*;
import java.awt.*;

public class SecoesPainel extends JPanel {

    final JPanel historico = new JPanel();
    final JPanel caixa = new JPanel();

    public SecoesPainel() {
        historico.setBackground(new Color(255, 255, 0));

        setLayout(new CardLayout());
        add(historico, NavPrincipal.HISTORICO.getRepr());
        add(caixa, NavPrincipal.CAIXA.getRepr());
    }
}
