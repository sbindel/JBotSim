package examples.fancy.parkcleaning;

import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.ClockListener;
import io.jbotsim.core.event.TopologyListener;

import io.jbotsim.core.Point;
import io.jbotsim.ui.JViewer;

import java.util.Random;

public class RWP implements TopologyListener, ClockListener{
	Topology tp;
	Random r=new Random();
	Point dim;
	boolean selective; // moves only the nodes whose "type" property is "rwp"

	public RWP(Topology tp){
		this(tp, false);
	}
	public RWP(Topology tp, boolean selective){
		this.tp = tp;
		dim = new Point(tp.getWidth(), tp.getHeight());
		this.selective = selective;
		tp.addTopologyListener(this);
		tp.addClockListener(this, 2);
	}
	public void onNodeAdded(Node n) {
		String type=(String)n.getProperty("type");
		if ((type!=null && type.equals("rwp")) || !selective)
            n.setProperty("target", new Point(r.nextInt((int) dim.getX()), r.nextInt((int) dim.getY())));
	}
	public void onNodeRemoved(Node n) {
	}
	public void onClock() {
		for (Node n : tp.getNodes()){
			String type=(String)n.getProperty("type");
			if ((type!=null && type.equals("rwp")) || !selective){
				Point target=(Point)n.getProperty("target");
		        n.setDirection(target);
		    	n.move(5);
		        if(n.distance(target)<5)
		        	n.setProperty("target", new Point(r.nextInt((int) dim.getX()), r.nextInt((int) dim.getY())));
			}
		}
	}
	public static void main(String args[]){
		Topology tp=new Topology();
		new RWP(tp);
		new JViewer(tp);
	}
}