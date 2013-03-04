
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.sun.management.ThreadMXBean;

import JaCoP.core.*;
import JaCoP.constraints.*;
import JaCoP.search.*;


public class TabuSearch {
	
	private Store store;
	private IntVar[] vars;
	private IntVar[] bestSolution;
	private IntDomain[] domains;
	private TabuList tabuList;
	
	int maxIteration = 1;
	int maxIterationWhithoutBetterSolution = 100;
	int tabuListMaxLength = 50;

	public TabuSearch(Store store, IntVar[] vars) {
		this.store = store;
		this.vars = vars;
		this.domains = new IntDomain[vars.length];
		this.bestSolution = new IntVar[vars.length];
		tabuList = new TabuList(tabuListMaxLength);
	}
	
	
	public void search() {
		
		// Saving domains
		for(int i = 0; i < vars.length; i++) {
			domains[i] = (IntDomain)vars[i].dom().clone();
		}
		
		// Step 1 : generate a random solution
		this.generate_random();
		
		
		// Step 2 : save the best known solution
		bestSolution = vars.clone();
		
		
		// Step 3 : search solution while stopping conditions not met
		int iterations = 0;
		boolean optimalSolutionFound = false;
		int noBetterSolution = 0;
		while(!optimalSolutionFound && (iterations < maxIteration) && (noBetterSolution < maxIterationWhithoutBetterSolution))
		{		
			// Step 3.1 : search candidate solutions
			ArrayList<IntVar[]> candidateSolutions = getCandidateSolutions(vars, domains, tabuList);
		
			// Step 3.2 : select best candidate solution
			IntVar[] bestCurrentSolution = getBestSolution(candidateSolutions);
		
			// Step 3.3 update current solution
			if(getViolatedConstraints(bestCurrentSolution) < getViolatedConstraints(bestSolution)) {
				bestSolution = bestCurrentSolution;
				noBetterSolution = 0;
			}
			else {
				noBetterSolution++;
			}
		
			// Step 3.4 : If current cost is better than cost of best known solution, update best known solution
		
			// Step 3.5 : update tabu list
		
			
			
			// Print the best solution
			if(noBetterSolution == 0) {
				System.out.println("New best solution found");
				for(int i = 0 ; i < bestSolution.length ; i++) {
					System.out.println(bestSolution[i]);
				}
			}
			
			
			// Updating values for stopping conditions
			iterations++;
			System.out.println(iterations);
		}
		
		System.out.println("End of search. Best solution is :");
		for(int i = 0 ; i < bestSolution.length ; i++) {
			System.out.println(bestSolution[i]);
		}
		
	}
	
	
	public void generate_random() {
		
		System.out.println("Generating random solution...");
		
		for(int i = 0 ; i < vars.length ; i++) {
			int randomNum = domains[i].getRandomValue();
			vars[i].setDomain(randomNum, randomNum);
			System.out.println(vars[i]);
		}
		System.out.println("___________");
		
	}
	
	public ArrayList<IntVar[]> getCandidateSolutions(IntVar[] vars, IntDomain[] domains, TabuList tabuList) {
		
		ArrayList<IntVar[]> candidateSolutions = new ArrayList<IntVar[]>();
		
		for(int i = 0 ; i < vars.length ; i++) {
			for(int value = domains[i].min() ; value < domains[i].max(); value++) {
				if(value != vars[i].value()) {
					IntVar[] neighboor = vars.clone();
					neighboor[i].setDomain(value, value);
					System.out.println("\t\t" + neighboor[i]);
					
					if(!tabuList.contains(neighboor)) {
						candidateSolutions.add(neighboor);
					}
				}
			}
		}
		

		System.out.println("Candidate solutions :");
		for(int j = 0 ; j < candidateSolutions.size() ; j++) {
			for(int i = 0 ; i < candidateSolutions.get(j).length ; i++) {

				System.out.println(candidateSolutions.get(j)[i]);
			}
			System.out.println("____");
		}
		
		return null;
	}
	
	
	public IntVar[] getBestSolution(ArrayList<IntVar[]> candidateSolutions) {
		
		// Comtper les contraintes violées de chaque bordel
		
		
		// libérer mémoire 
		
		return null;
	}
	
	public int getViolatedConstraints(IntVar[] candidateSolution) {
		
		return 10;
	}
	

}
