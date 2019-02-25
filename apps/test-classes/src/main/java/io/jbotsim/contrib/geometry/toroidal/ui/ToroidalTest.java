/*
 * Copyright 2008 - 2019, Arnaud Casteigts and the JBotSim contributors <contact@jbotsim.io>
 *
 *
 * This file is part of JBotSim.
 *
 * JBotSim is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JBotSim is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JBotSim.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.jbotsim.contrib.geometry.toroidal.ui;

import io.jbotsim.core.Topology;
import io.jbotsim.contrib.geometry.toroidal.ToroidalLinkResolver;
import io.jbotsim.ui.JViewer;

/**
 * Created by acasteig on 30/08/16.
 */
public class ToroidalTest {
    public static void main(String[] args) {
        Topology tp = new Topology();
        tp.setLinkResolver(new ToroidalLinkResolver());
        JViewer jv = new JViewer(tp);
        jv.getJTopology().setDefaultLinkPainter(new ToroidalLinkPainter());
        for (int i = 0; i < 10; i++)
            tp.addNode(-1, -1);
    }
}
