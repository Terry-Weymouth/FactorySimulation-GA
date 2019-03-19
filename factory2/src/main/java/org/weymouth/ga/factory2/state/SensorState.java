package org.weymouth.ga.factory2.state;

import org.weymouth.ga.factory2.core.Thing;

public class SensorState implements State {
	
	private final String name;
	private Thing thing;
	
	public SensorState(String n, Thing t) {
		name = n;
		thing = t;
	}

	@Override
	public String toDataString() {
		if (thing == null) {
			return "{color: none}";
		}
		return String.format("{color: {r: %d, g: %d, b: %d}}", 
				thing.color.r, thing.color.g, thing.color.b);
	}

	@Override
	public String toNameString() {
		return name;
	}

}
