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
	
	public boolean isForbidden(IntVar[] neighboor) {
		
		boolean equals;
		
		for(int i = 0 ; i < this.size() ; i++) {
			
			equals = true;
			
			for(int j = 0 ; j < this.get(i).length ; j++) {
				equals &= (this.get(i)[j].value() == neighboor[j].value());
			}
			if(equals) {
				return true;
			}
		}
		
		return false;
	}
	
}
