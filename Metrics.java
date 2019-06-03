package socNet;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Metrics<T, V> {
	
	
	private UndirectedSparseGraph<T, V> loadedGraph = new UndirectedSparseGraph<T, V>();
	private Set<T> nodes = new HashSet<T>();
	private Map<T, Integer> degree = new HashMap<>();
	private Map<T, Double> betwenness = new HashMap<>();;
	private Map<T, Double> closeness = new HashMap<>();;
	private Map<T, Double> eigenvector = new HashMap<>();;
	private double [] pc = new double [6]; // PearsonsCorrelation
	private double [] sc = new double [6]; //SpearmansCorrelation
	
	
	public Metrics (Set<T> nodes, UndirectedSparseGraph<T, V> loaded){
		this.nodes  = nodes;
		this.loadedGraph = loaded;
		setComponent();
		setDegree();
		setBetwenness();
		setCloseness();
		setEigenvector();
		correlation();
		
	}
	
	public  void setComponent() {
		Iterator<T> it = loadedGraph.getVertices().iterator();
		List<T> remove = new ArrayList<>();
		while (it.hasNext()) {
			T t = it.next();
			if (!nodes.contains(t)) {
				remove.add(t);
			}
		}
		for (T t: remove) {
		loadedGraph.removeVertex(t);
		}
		
	
		
	}
	
	public void readGrahV() {
		Iterator<T> it = loadedGraph.getVertices().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
	public void readGrahE() {
		Iterator<V> it = loadedGraph.getEdges().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	public UndirectedSparseGraph<T, V> getLoadedGraph() {
		return loadedGraph;
	}

	public void setDegree() {
		Iterator<T> it = loadedGraph.getVertices().iterator();
		while (it.hasNext()) {
			degree.put(it.next() , loadedGraph.degree(it.next()));
		}
		
	}
	
	public void setBetwenness() {
		BetweennessCentrality<T, V> bc = new BetweennessCentrality<>(loadedGraph);
		Iterator<T> it = loadedGraph.getVertices().iterator();
		while (it.hasNext()) {
			betwenness.put(it.next(), bc.getVertexScore(it.next()));
		}
		
	}
	public void setCloseness() {
		ClosenessCentrality<T, V> cc = new ClosenessCentrality<>(loadedGraph);
		Iterator<T> it = loadedGraph.getVertices().iterator();
		while (it.hasNext()) {
			closeness.put(it.next(), cc.getVertexScore(it.next()));
		}
		
	}
	
	public void setEigenvector() {
		EigenvectorCentrality<T, V> ec = new EigenvectorCentrality<>(loadedGraph);
		Iterator<T> it = loadedGraph.getVertices().iterator();
		while (it.hasNext()) {
			eigenvector.put(it.next(), ec.getVertexScore(it.next()));
		}
	}
	
	
	public void correlation() {
		
		double[] deg  = new double [degree.entrySet().size()];
		double [] bet = new double [betwenness.entrySet().size()];
		double [] clos = new double [closeness.entrySet().size()];
		double [] ev =new double [eigenvector.entrySet().size()];
		int j = 0;
		for (Map.Entry<T, Integer> d: degree.entrySet()) {
			deg[j++]= d.getValue();
		}
		int k=0;
		for (Map.Entry<T, Double> d: betwenness.entrySet()) {
			bet[k++]= d.getValue();
		}
		int l=0;
		for (Map.Entry<T, Double> d: closeness.entrySet()) {
			clos[l++] = d.getValue();
			
		}
		int h=0;
		for (Map.Entry<T, Double> d: eigenvector.entrySet()) {
			ev[h++]=d.getValue();
		}
		
		
		
		PearsonsCorrelation pers = new PearsonsCorrelation();
		double pc1 = pers.correlation(deg, bet);
		double pc2 = pers.correlation(deg, clos);
		double pc3 = pers.correlation(deg, ev);
		double pc4 = pers.correlation(bet,clos);
		double pc5 = pers.correlation(bet, ev);
		double pc6 = pers.correlation(clos, ev);
		double [] arrayPC = {pc1,pc2,pc3,pc4,pc5,pc5,pc6};
		setPc(arrayPC);
		
		
		
		
		SpearmansCorrelation sperc = new SpearmansCorrelation();
		double sc1 = sperc.correlation(deg, bet);
		double sc2 = sperc.correlation(deg, clos);
		double sc3 = sperc.correlation(deg, ev);
		double sc4 = sperc.correlation(bet,clos);
		double sc5 = sperc.correlation(bet, ev);
		double sc6 = sperc.correlation(clos, ev);
		double [] arraySC = {sc1,sc2,sc3,sc4,sc5,sc5,sc6};	
		setSc(arraySC);
		System.out.println(sc1);
	}
	
	
	
	public Set<T> getNodes() {
		return nodes;
	}
	
	public Map<T, Integer> getDegree() {
		return degree;
	}
	
	public Map<T, Double> getBetwenness() {
		return betwenness;
	}
	
	public Map<T, Double> getCloseness() {
		return closeness;
	}
	
	public Map<T, Double> getEigenvector() {
		return eigenvector;
	}

	public double [] getPc() {
		return pc;
	}

	public void setPc(double [] pc) {
		this.pc = pc;
	}

	public double [] getSc() {
		return sc;
	}

	public void setSc(double [] sc) {
		this.sc = sc;
	}
	
}
