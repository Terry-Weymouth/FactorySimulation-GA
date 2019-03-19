package org.weymouth.ga.factory2.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.weymouth.ga.factory2.state.FactoryState;

public class DataFileWriter {
	
	private final String fileLocation;
	private final PrintWriter out;
	
	public DataFileWriter(String path) throws IOException {
		fileLocation = path;
		FileWriter fw = new FileWriter(fileLocation, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    out = new PrintWriter(bw);
	    writeHeaderString("Recording Facory Status from Simulation");
	}
	
	public void writeFactoryState(FactoryState d) throws IOException {
	    out.println(d.toDataOutput());
	}

	public void writeHeaderString(String string) throws IOException {
		out.println("--- " + string + " ---");
	}
	
	public void close() throws IOException {
		writeHeaderString("Closing Recording");
		out.close();
	}

}
