package org.weymouth.ga.factory2.state;

import org.weymouth.ga.factory2.core.World;

public class FactoryState {
	
	private final World world;

	public FactoryState(World theWorld) {
		world = theWorld;
	}

	public String toDataOutput() {
		
		return "test data output";
	}

}
