package socNet;

import java.io.IOException;
import java.util.Set;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class MainClass {
			
	public static void main (String[]   args) throws IOException {
	DFCComponents workingGraph = new DFCComponents();
	UndirectedSparseGraph<Integer, String> loaded = workingGraph.getLoadedGraph();
	workingGraph.readGrahV();
	workingGraph.readGrahE();
	Set<Integer> bigOne = workingGraph.getBigOne();
	
	
	Metrics<Integer, String> metric = new Metrics<Integer, String>(bigOne, loaded);
	metric.getLoadedGraph();
	metric.readGrahV();
	metric.readGrahE();
	
	}
}
