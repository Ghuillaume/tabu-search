import java.util.LinkedList;

import JaCoP.core.*;
import JaCoP.constraints.*;
import JaCoP.search.*;


public class TabuList extends LinkedList<IntVar[]> {
	
	private int maxSize = -1;

	public TabuList(int maxSize) {
		super();
		this.maxSize = maxSize;
	}
	
	public void insert(IntVar[] toAdd) {
		if(this.size() >= maxSize && maxSize > 0) {
			this.removeFirst();
		}
		this.addLast(toAdd);
	}
	
}
