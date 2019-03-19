package org.weymouth.ga.factory2.state;

import org.weymouth.ga.factory2.core.Thing;

public class MarkerState implements State {
	
	private final String name;
	private final Thing thing;
	
	public MarkerState(String n, Thing t) {
		name = n;
		thing = t;
	}

	@Override
	public String toDataString() {
		if (thing == null) {
			return "{color: none}";
		}
		return String.format("{color: {r: %d, g: %d, b: %d}, matched: %s}", 
				thing.color.r, thing.color.g, thing.color.b, thing.matched?"true":"false");
	}

	@Override
	public String toNameString() {
		return name;
	}

}
