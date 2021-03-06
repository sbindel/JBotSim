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

package io.jbotsim.contrib.obstacle.example;

import io.jbotsim.core.Node;
import io.jbotsim.core.Point;
import io.jbotsim.contrib.obstacle.core.Obstacle;
import io.jbotsim.contrib.obstacle.core.ObstacleListener;
import io.jbotsim.contrib.obstacle.core.ObstacleManager;
import java.util.List;
import java.util.Random;

/**
 * Created by acasteig on 16/03/17.
 */
public class ObstacleNode extends Node implements ObstacleListener{

    private static Random r = new Random();

    public ObstacleNode() {
        setProperty("target", new Point(r.nextInt(800), r.nextInt(600)));
        setSensingRange(25);
    }

    @Override
    public void onStart() {
        setIcon("/io/jbotsim/contrib/obstacle/example/cleaner.png");
        ObstacleManager.addObstacleListener(this, getTopology());
    }

    @Override
    public void onStop() {
        ObstacleManager.removeObstacleListener(this, getTopology());
    }

    @Override
    public void onDetectedObstacles(List<Obstacle> obstacles) {
        Point p = obstacles.get(0).pointAtMinimumDistance(this);
        Point tmp = new Point(this.getX()+Math.cos(this.getDirection()), this.getY() + Math.sin(this.getDirection()),0);
        double distance = p.distance(tmp);

        for (Obstacle o : obstacles){
            Point ptmp = o.pointAtMinimumDistance(this);
            if (ptmp.distance(tmp) < distance){
                p = ptmp;
                distance = p.distance(tmp);
            }
        }
        Point tmp2;
        Point n = new Point(this.getX(),this.getY(),this.getZ());
        if(p.distance(tmp) < this.getSensingRange() && p.distance(tmp) < p.distance(n)){
            do {
                setProperty("target", new Point(r.nextInt(800), r.nextInt(600)));
                Point target = (Point)getProperty("target");
                double direction = Math.atan2(target.getX() - this.getX(), - (target.getY() - this.getY())) - Math.PI/2;
                tmp2 = new Point(this.getX() + Math.cos(direction), this.getY() + Math.sin(direction),0);
            }
            while(p.distance(tmp2) < p.distance(n));
        }
    }

    @Override
    public void onClock() {
        Point target = (Point)getProperty("target");
        setDirection(target);
        move();
        if (distance(target) < 15)
            setProperty("target", new Point(r.nextInt(800), r.nextInt(600)));
    }

}
