/*
 * Copyright 2008 - 2020, Arnaud Casteigts and the JBotSim contributors <contact@jbotsim.io>
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

package examples.misc.randomwalks;

import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.ClockListener;
import io.jbotsim.core.event.StartListener;
import io.jbotsim.gen.basic.TopologyGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by acasteig on 17/06/15.
 */
public class CentralizedRW4 implements ClockListener, StartListener {
    Random random = new Random();
    Topology tp;
    Node current;

    public CentralizedRW4(Topology tp) {
        this.tp = tp;
        tp.addClockListener(this);
        tp.addStartListener(this);
    }

    @Override
    public void onStart() {
        current = tp.getNodes().get(0);
    }

    @Override
    public void onClock() {
        List<Node> neighbors = current.getNeighbors();
        current = neighbors.get(random.nextInt(neighbors.size()));
        if (current == tp.getNodes().get(tp.getNodes().size()-1))
            finish();
    }

    public void finish(){
        List<Integer> stats = (List<Integer>) tp.getProperty("stats");
        if (stats == null)
            tp.setProperty("stats", stats=new ArrayList<Integer>());
        stats.add(tp.getTime());
        if (stats.size()==100) {
            double total = 0.0;
            for (Integer I : stats)
                total += I;
            System.out.println(tp.getNodes().size() + "\t" + total/100);
            tp.pause();
        }else {
            tp.restart();
        }
    }

    public static void main(String[] args) {
        for (int i=3; i<=10; i++) {
            Topology tp = new Topology();
            TopologyGenerators.generateLine(tp, i);
            new CentralizedRW4(tp);
            tp.setTimeUnit(1);
            tp.start();
        }
    }
}
