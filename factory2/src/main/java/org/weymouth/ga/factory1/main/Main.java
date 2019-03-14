package org.weymouth.ga.factory1.main;

public class Main {

	public static void main(String[] args) {
		(new Main()).exec();
	}

	private void exec() {
		ViewWorldSimulation s = new ViewWorldSimulation();
		s.driveExampleSimulation();
	}

}
