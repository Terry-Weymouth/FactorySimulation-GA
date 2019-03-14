package org.weymouth.ga.factory1.core;

import java.util.ArrayList;
import java.util.List;

public class SorterBelt extends Belt {

	List<Thing> sortedOut = new ArrayList<Thing>();
	List<ThingOnBelt> runoff = new ArrayList<ThingOnBelt>();

	public SorterBelt(Location startLocation, double l, Orientation o) {
		super(startLocation, l, o);
	}

	@Override
	public List<Thing> update() {
		for (ThingOnBelt holder : objects) {
			if (isSortedOut(holder)) {
				sortedOut.add(holder.thing);
				runoff.add(holder);
			}
		}
		objects.removeAll(runoff);
		return super.update();
	}

	private boolean isSortedOut(ThingOnBelt holder) {
		Color c = holder.thing.color;
		return c.g < 128;
	}

	public List<Thing> dumpSortedOut() {
		List<Thing> dump = sortedOut;
		sortedOut = new ArrayList<Thing>();
		return dump;
	}
}
