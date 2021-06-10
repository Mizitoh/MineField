package br.com.mike.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.mike.cm.exception.ExplosionException;

public class Field {

	private final int line;
	private final int column;
	
	private boolean isOpen = false;
	private boolean mined = false;
	private boolean marked = false;
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	//auto relation
	private List<Field> neighbors = new ArrayList<Field>();
	
		public Field(int line, int column) {
		this.line = line;
		this.column = column;
	} 
	
		public boolean addNeighbor(Field neighbor) {
		boolean lineDifferent = line != neighbor.line;
		boolean columnDifferent = column != neighbor.column;
		//if it's diagonal the difference is 2, if it is not, is 1.
		boolean diagonal = lineDifferent && columnDifferent;
		
		int distanceLine = Math.abs(line - neighbor.line);
		int distanceColumn = Math.abs(column - neighbor.column);
		int distanceGeneral = distanceLine + distanceColumn;
		
		if (distanceGeneral == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if (distanceGeneral == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		} else {
		return false;
		}
	}
		
		public void changeMark() {
			if(!isOpen) {
				marked = !marked;
			}
		}
		
		public boolean open() {
			if(!isOpen && !marked) {
				isOpen = true;
				if(mined) {
					throw new ExplosionException();
				}
				
				if (neighborhoodSafe()) {
					neighbors.forEach(n -> n.open());
				}
				return true;
			}else {
				return false;
			}
		}
		
		public boolean neighborhoodSafe() {
			return neighbors.stream().noneMatch(n -> n.mined);
		}
		
		public boolean isMarked() {
			return marked;
		}
		
		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}

		public void hasMine() {
			mined = true;
		}
		
		public boolean isItMined() {
			return mined;
		}
		
		public boolean isItOpen() {
			return isOpen;
		}
		
		public boolean isItClosed() {
			return !isOpen;
		}
		

		boolean objectiveSuccess() {
			boolean unraveled = !mined && isOpen;
			boolean isProtected = mined && marked;
			return unraveled || isProtected;
		}
		
		public long minesAtNeighborhood() {
			return neighbors.stream().filter(n ->n.mined).count();
		}
		
		void reestart() {
			isOpen = false;
			mined = false;
			marked = false;
		}
		
		public String toString() {
			if (marked) {
				return "X";
			} else if (isOpen && mined) {
				return  "*";
			} else if (isOpen && minesAtNeighborhood() > 0) {
				return Long.toString(minesAtNeighborhood());
			}else if(isOpen) {
				return " ";
			} else {
				return "?";
			}
		}
}
