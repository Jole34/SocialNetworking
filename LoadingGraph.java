package socNet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;


public class LoadingGraph {

	private UndirectedSparseGraph<Integer, String> loadedGraph = new UndirectedSparseGraph<Integer, String> ();
	
	public LoadingGraph(UndirectedSparseGraph<Integer, String> loading){
		this.loadedGraph = loading;
	}
	
	public LoadingGraph() throws IOException {
		this.load("src/CA-GrQc.txt");
	}
	
	public void load(String fileR) throws IOException{
		BufferedReader r =null;
		try {
			File readF = new File(fileR);
			 r = new BufferedReader(new FileReader(readF));  
			List<String> nodes = new ArrayList<>();
			List<String> links = new ArrayList<>();
			
			while (r.ready()) {                        					   //reading elements from file in two simple array lists 
				String a = r.readLine();
				String [] b = a.split("\\s");
				nodes.add(b[0].trim());
				nodes.add(b[1].trim());
				links.add(a);
			}
			

			for (String n: nodes) {										//adding nodes to graph 
				loadedGraph.addVertex(Integer.parseInt(n));
			}
			
			int j = 0;
			for (String l: links) {											//adding links to graph
				loadedGraph.addEdge(l, Integer.parseInt(nodes.get(j)), Integer.parseInt(nodes.get(j+1)));
				j = j+2;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			r.close();
		}
		
	}

	public UndirectedSparseGraph<Integer, String> getLoadedGraph() {
		return loadedGraph;
	}

	public void setLoadedGraph(UndirectedSparseGraph<Integer, String> loadedGraph) {
		this.loadedGraph = loadedGraph;
	}
	
	
}
