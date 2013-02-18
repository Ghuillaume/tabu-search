
import JaCoP.core.*;
import JaCoP.constraints.*;
import JaCoP.search.*;


public class TabuSearch {
	
	private Store store;
	private IntVar[] vars;

	public TabuSearch(Store store, IntVar[] vars) {
		this.store = store;
		this.vars = vars;
	}
	
	
	public void search() {
		
		// Step 1 : generate a random solution
		this.generate_random();
		
		// Step 2 : save the best known solution
		
		
		// Step 3 : search solution while stopping conditions not met
		
		
			// Step 3.1 : search candidate solutions
		
			// Step 3.2 : select best candidate solution
		
			// Step 3.3 update current solution
		
			// Step 3.4 : If current cost is better than cost of best known solution, update best known solution
		
			// Step 3.5 : update tabu list
		
		
		// end while
	}
	
	
	public void getBestSolution() {
		
	}
	
	
	public IntVar[] generate_random() {
		
		System.out.println("Generating random solution...");
		
		for(int i = 0 ; i < vars.length ; i++) {
			System.out.println(vars[i].toStringFull());
			
			int randomNum = vars[i].dom().MinInt + (int)(Math.random()*vars[i].dom().MaxInt); 
			
		}
		
		
		return vars;
	}

}
