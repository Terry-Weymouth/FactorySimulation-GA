package org.weymouth.ga.factory2.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CornerBelt implements Belt {
	
	private static final double DELTA_THETA = Math.PI/180.0;

	private String name = null;
	
	private final Random random = new Random();
	private final Location start;
	private final double length;
	private final float graphicsX, graphicsY, graphicsWidth, graphicsHeight;
	private final Orientation orientation;
	private double width = 100.0;
	private double speed = 1.0; 
	protected List<Thing> objects = new ArrayList<Thing>();
	protected Belt next = null;

	public CornerBelt(Location startLocation, double l, Belt.Orientation o) {
		orientation = o;
		start = startLocation;
		length = l;
		if (orientation.equals(Belt.Orientation.EAST)) {
			graphicsX = (float) start.x;
			graphicsY = (float) (start.y - (width / 2.0));
			graphicsHeight = (float) width;
			graphicsWidth = (float) length;
		} else {
			graphicsX = (float) (start.x - (width / 2.0));
			graphicsY = (float) start.y;
			graphicsHeight = (float) length;
			graphicsWidth = (float) width;
		}
	}
	
	public void linkUp(Belt nextBelt) {
		next = nextBelt;
	}

	public void setSpeed(double s) {
		speed = s;
	}

	public void add(Thing t) {
		double jiggle = random.nextGaussian();
		while (jiggle > 1.0 || jiggle < -1.0) {
			jiggle = random.nextGaussian();
		}
		jiggle = (width / 2.0 - 10.0) * jiggle;
		double x = start.x;
		double y = start.y;
		if (orientation.equals(Belt.Orientation.EAST)) {
			y += jiggle;
		} else {
			x += jiggle;
		}
		t.x = x;
		t.y = y;
		objects.add(t);
	}

	public void handoff(Thing t) {
		objects.add(t);
	}

	public List<Thing> update() {
		List<Thing> goneThings = new ArrayList<Thing>();
		for (Thing t : objects) {
			double xx = t.x - start.x;
			double yy = - (t.y - start.y - 50.0);
			double r = Math.sqrt(xx*xx + yy*yy);
			double theta = Math.acos(yy/r) + DELTA_THETA * speed;
			double xp = r * Math.sin(theta);
			double yp = r * Math.cos(theta);
			t.x += (xp - xx);
			t.y += (yy - yp);
			if (isOffBelt(t)) {
				goneThings.add(t);
			}
		}
		objects.removeAll(goneThings);
		if (next != null) {
			for (Thing t: goneThings) {
				next.handoff(t);
			}
			goneThings = new ArrayList<Thing>();
		}
		return goneThings;
	}

	private boolean isOffBelt(Thing t) {
		return t.y > (start.y + 50.0);
	}

	public boolean hasThings() {
		return !objects.isEmpty();
	}

	public float x() {
		return graphicsX;
	}

	public float y() {
		return graphicsY;
	}

	public float height() {
		return graphicsHeight;
	}

	public float width() {
		return graphicsWidth;
	}

	public List<Thing> getThingsCopy() {
		return new ArrayList<Thing>(objects);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
