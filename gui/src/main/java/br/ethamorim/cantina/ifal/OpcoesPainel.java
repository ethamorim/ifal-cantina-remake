package br.ethamorim.cantina.ifal;

import br.ethamorim.cantina.ifal.enums.NavPrincipal;

import javax.swing.*;
import java.awt.*;

public class OpcoesPainel extends JPanel {

    public OpcoesPainel() {
        setLayout(new GridLayout(10, 1));
        setPreferredSize(new Dimension(250, 500));

        JButton historicoBotao = new JButton(NavPrincipal.HISTORICO.getRepr());
        historicoBotao.setHorizontalAlignment(SwingConstants.LEFT);
        add(historicoBotao);

        JButton caixaBotao = new JButton(NavPrincipal.CAIXA.getRepr());
        caixaBotao.setHorizontalAlignment(SwingConstants.LEFT);
        add(caixaBotao);
    }
}
