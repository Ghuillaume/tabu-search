import java.util.ArrayList;

import ExamplesJaCoP.BabySitting;
import JaCoP.core.*;
import JaCoP.constraints.*;
import JaCoP.search.*;


public class Projet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int numberQ = 4;

		// Creating constraint store
		Store store = new Store();
		ArrayList<Var> vars = new ArrayList<Var>();

		// I-th queen variable represents the placement
		// of a queen in i-th column
		// There are n columns so there are n variables
		IntVar queens[] = new IntVar[numberQ];

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

		
		TabuSearch tabusearch = new TabuSearch(store, queens);
		tabusearch.search();

	}

}
