package org.weymouth.ga.factory2.main;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.weymouth.ga.factory2.core.Color;
import org.weymouth.ga.factory2.core.Thing;
import org.weymouth.ga.factory2.core.World;
import org.weymouth.ga.factory2.core.WorldView;
import org.weymouth.ga.factory2.core.WorldViewController;
import org.weymouth.ga.factory2.io.DataFileWriter;
import org.weymouth.ga.factory2.state.FactoryState;

import processing.core.PApplet;

public class ViewWorldSimulation {
	
	public static final String stateRecorderLocation = "StateRecord.txt";

	WorldViewController worldController = WorldViewController.getController();
	World theWorld = new World();
	private final Random random = new Random();
	
	RecorderHelper recorder = new RecorderHelper(stateRecorderLocation);

	public ViewWorldSimulation() {
		PApplet.main(WorldView.class);
		worldController.initialize(theWorld);
	}

	public void driveExampleSimulation() {
		while (!worldController.isReady()) {
			waitForNext();
		}
		recorder.init();
		while(!worldController.isDone()) {
			runInNewThings();
			runAllOut();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignore) {
			}
		}
		worldController.close();
		recorder.close();
	}

	private void runInNewThings() {
		for (int i = 0; i < 60; i++) {
			int r = random.nextInt(256);
			int g = random.nextInt(256);
			int b = random.nextInt(256);
			Thing t = new Thing(new Color(r, g, b));
			theWorld.add(t);
			runForAwhile();
		}
	}

	private void runForAwhile() {
		for (int i = 0; i < 50; i++) {
			@SuppressWarnings("unused")
			List<Thing> overflow = update();
//			if (!overflow.isEmpty()) {
//				for (Thing t : overflow) {
//					System.out.println("Done in the world: " + t);
//				}
//			}
		}
	}

	private void runAllOut() {
		while (theWorld.isLive()) {
			@SuppressWarnings("unused")
			List<Thing> overflow = update();
//			if (!overflow.isEmpty()) {
//				for (Thing t : overflow) {
//					System.out.println("Done in the world: " + t);
//				}
//			}
		}
	}

	private List<Thing> update() {
		waitForNext();
		recorder.record(theWorld);
		return theWorld.update();
	}

	private void waitForNext() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException ignore) {
		}
	}

	private class RecorderHelper {
		
		private static final boolean DEBUG = false;
		private final String filePath;
		private DataFileWriter writer = null;
		
		RecorderHelper(String location) {
			filePath = location;
		}
		
		public void record(World theWorld) {
			if (writer != null) {
				FactoryState state = new FactoryState(theWorld);
				try {
					writer.writeFactoryState(state);
				} catch (IOException e) {
					if (DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

		void init() {
			try {
				writer = new DataFileWriter(filePath);
			} catch (IOException e) {
				writer = null;
				if (DEBUG) {
					e.printStackTrace();
				}
			}
		}

		public void close() {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					if (DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
