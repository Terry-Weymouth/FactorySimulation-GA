package org.weymouth.ga.factory2.state;

import java.util.List;

import org.weymouth.ga.factory2.core.World;

public class FactoryState {
	
	private final World world;

	public FactoryState(World theWorld) {
		world = theWorld;
	}

	public String toDataOutput() {
		List<State> stateList = world.allStates();
		String ret = "{factory-state: {";
		for (State s: stateList) {
			ret += s.toNameString() + ": " + s.toDataString() + ", ";
		}
		ret = ret.substring(0, ret.length()-2);
		ret += "}}";
		return ret;
	}

}
