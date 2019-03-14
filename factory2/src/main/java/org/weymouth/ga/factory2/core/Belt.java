package org.weymouth.ga.factory2.core;

import java.util.List;

public interface Belt {

	public enum Orientation {
		EAST, WEST, SOUTH, NORTH
	};

	public void add(Thing t);
	public void handoff(Thing t);
	public List<Thing> update();
	
	public boolean hasThings();

	public float x();
	public float y();
	public float height();
	public float width();
	public List<Thing> getThingsCopy();

}
