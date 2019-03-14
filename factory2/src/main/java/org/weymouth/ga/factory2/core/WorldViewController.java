package org.weymouth.ga.factory2.core;

public class WorldViewController {

	// poorman's singleton
	public static final WorldViewController controller = new WorldViewController();

	public static WorldViewController getController() {
		return controller;
	}

	private WorldViewController() {
		// constructor not available to public
	}

	WorldView theWorldView = null;

	public void initialize(World theWorld) {
		while (theWorldView == null) {
			// wait for the theWorld to be registered
			try {
				Thread.sleep(100);
			} catch (InterruptedException ignore) {
			}
		}
		theWorldView.setTheWorld(theWorld);
	}

	public void register(WorldView aw) {
		theWorldView = aw;
	}

	public WorldView getView() {
		return theWorldView;
	}

	public void close() {
		theWorldView.close();
	}

}
