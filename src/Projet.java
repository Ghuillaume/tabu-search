import java.util.ArrayList;

import ExamplesJaCoP.BabySitting;
import JaCoP.core.*;
import JaCoP.constraints.*;
import JaCoP.search.*;


public class Projet {

	public static void initializeProblem(Store store, ArrayList<Var> vars, IntVar[] queens, int numberQ) {
		// I-th queen variable represents the placement
				// of a queen in i-th column
				// There are n columns so there are n variables

				// Each queen variable has a domain from 1 to numberQ
				// Value of queen variable represents the row
				for (int i = 0; i < numberQ; i++) {
					queens[i] = new IntVar(store, "Q" + (i + 1), 1, numberQ);
					vars.add(queens[i]);
				}
				// Queens from different columns can not be placed
				// in the same row, therefore the values
				// must be different
				for (int i = 0; i < queens.length; i++)
					for (int j = i - 1; j >= 0; j--)
						store.impose(new XneqY(queens[i], queens[j]));

				// Notice that j index starts from i+1
				for (int i = 0; i < queens.length; i++) {
					for (int j = i + 1; j < queens.length; j++) {

						// Temporary variable denotes the chessboard
						// field in j-th column which is checked by
						// i-th column queen
						// If temporarty variable has value outside
						// range 1..numberQ then i-th column queen
						// does not check any field in j-th column

						// Checking diagonals like this \
						// Note that C constant is positive
						IntVar temporary = new IntVar(store, -2 * numberQ, 2 * numberQ);
						store.impose(new XplusCeqZ(queens[j], j - i, temporary));
						store.impose(new XneqY(queens[i], temporary));

						// Checking diagonals like this /
						// Note that C constant is negative
						temporary = new IntVar(store, -2 * numberQ, 2 * numberQ);
						store.impose(new XplusCeqZ(queens[j], -(j - i), temporary));
						store.impose(new XneqY(queens[i], temporary));

					}
				}
	}
	
	public static boolean completeSearch(Store store, IntVar[] Q) {
		DepthFirstSearch<IntVar> search = new DepthFirstSearch<IntVar>();

		search.getSolutionListener().searchAll(true);
		search.getSolutionListener().recordSolutions(true);

		SelectChoicePoint<IntVar> select =
				new SimpleSelect<IntVar>(Q, new SmallestDomain<IntVar>(), new IndomainMedian<IntVar>());

		boolean result = search.labeling(store, select);

		for (int i=1; i<=search.getSolutionListener().solutionsNo(); i++){
			System.out.print("Solution " + i + ": [");
			for (int j=0; j<search.getSolution(i).length; j++) {
				if (j!=0) System.out.print(", ");
			    System.out.print(search.getSolution(i)[j]);
			}
			System.out.println("]");
		}
		
		return result;
	}
	
	
	public static void main(String[] args) {
		
		int numberQ = 10;

		// Creating constraint store
		Store store = new Store();
		ArrayList<Var> vars = new ArrayList<Var>();
		IntVar queens[] = new IntVar[numberQ];

		initializeProblem(store, vars, queens, numberQ);
		

		

		System.out.println("\n\nCOMPLETE SEARCH :\n=============================");
		long cs_begin = System.currentTimeMillis();
		completeSearch(store, queens);
		long cs_end = System.currentTimeMillis();
		float cs_time = ((float) (cs_end-cs_begin)) / 1000f;
		

		store = new Store();
		vars = new ArrayList<Var>();
		queens = new IntVar[numberQ];

		initializeProblem(store, vars, queens, numberQ);		
		

		System.out.println("\n\nTABU SEARCH :\n=============================");
		TabuSearch tabusearch = new TabuSearch(queens);
		long ts_begin = System.currentTimeMillis();
		tabusearch.search();
		long ts_end = System.currentTimeMillis();
		float ts_time = ((float) (ts_end-ts_begin)) / 1000f;

		
		System.out.println("\n\nComparaison des temps d'exécution (s) :");
		System.out.println("CompleteSearch : " + cs_time);
		System.out.println("TabuSearch : " + ts_time);
		

	}
	
	

}
