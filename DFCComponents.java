package socNet;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class DFCComponents {
	
	public DFCComponents()throws IOException {
		this.indetifyComponents();
	}
	
	private UndirectedSparseGraph<Integer, String> loadedGraph;
	private Set<Integer> visited;
	private Set<Set<Integer>> components;
	private Set<Integer> max;
	public void indetifyComponents()throws IOException {
		LoadingGraph loadedGr = new LoadingGraph();
		loadedGraph = loadedGr.getLoadedGraph();
		visited = new HashSet<Integer>();
		components = new HashSet<Set<Integer>>();
		

		
		Iterator<Integer> it = loadedGraph.getVertices().iterator(); 
		while (it.hasNext()) {
			int n = it.next();
					if (!visited.contains(n) ) {
						components.add(indetifyComponent(n));
					}
				}
		}

	public Set<Integer> indetifyComponent (int start)  {
		Set<Integer> comp = new HashSet<Integer>();
		comp.add(start);
		visited.add(start);
		dfs(start, comp);
		return comp;
	}
	
	
	public void dfs(int current, Set<Integer> cmp)  {	
		Collection<Integer> neighbors = loadedGraph.getNeighbors(current);
		for (Integer e: neighbors) {
			if (!visited.contains(e)) {
				cmp.add(e);
				visited.add(e);
				dfs(e, cmp);
				
			}
		}
		max = findBig();
	}
	
	public Set<Integer> findBig(){
		Iterator<Set<Integer>> it = components.iterator();
		if (components.isEmpty()) {
			return Collections.emptySet();
		}
		Set<Integer> max1 = it.next();
		while (it.hasNext()) {
			Set<Integer> s = it.next();
			if (s.size() > max1.size()) {
				max1 = s;
			}
			
		}
		return max1;
	}
	
	public Set<Integer> getBigOne(){
		return this.max;
	}



	public UndirectedSparseGraph<Integer, String> getLoadedGraph() {
		return loadedGraph;
	}

	public void readGrahV() {
		Iterator<Integer> it = loadedGraph.getVertices().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
	public void readGrahE() {
		Iterator<String> it = loadedGraph.getEdges().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
}
