/*
 * This file is part of JBotSim.
 *
 *    JBotSim is free software: you can redistribute it and/or modify it
 *    under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Authors:
 *    Arnaud Casteigts        <arnaud.casteigts@labri.fr>
 */
package io.jbotsim.ui.painting;

import io.jbotsim.core.Node;
import io.jbotsim.ui.JNode;

import java.awt.*;

public class JNodePainter implements NodePainter {
    @Override
    public void paintNode(UIComponent uiComponent, Node node) {
        Graphics2D g2d = (Graphics2D) uiComponent.getComponent();
        JNode jn = (JNode) node.getProperty("jnode");
        int drawSize = jn.getWidth() / 2;
        if (node.getColor() != null) {
            g2d.setColor(new Color(node.getColor().getRGB()));
            g2d.fillOval(drawSize / 2, drawSize / 2, drawSize, drawSize);
        }
    }
}