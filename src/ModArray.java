import java.util.ArrayList;

public class ModArray {
	protected ArrayList<Node> modBoard=new ArrayList<Node>();
	
	protected void makeBoard() {
		for(int y=0;y<50;y++) {
			for(int x=0;x<50;x++) {
				
				Node node=new Node(7,x,y);
				modBoard.add(node);
			}
		
			
		}
	}
	
	public void resetBoard() {
		
		for(int y=0;y<50;y++) {
			for(int x=0;x<50;x++) {
				
				modBoard.get(50*y+x).setValue(7);
			}
		
			
		}
	}
	
	public void printBoard() {
		for(int y=0;y<50;y++) {
			for(int x=0;x<50;x++) {
				System.err.print(modBoard.get(50*y+x).getValue()+" ");
			}
			System.err.println();
		}
		System.err.println();
	}
	
	protected void setValueOf(int x,int y,int value) {
		modBoard.get((50*y+x)).setValue(value);
	}
	
	protected Node getNodeAt(int x,int y) {
		return modBoard.get((50*y+x));
	}
	
	protected int getValueAt(int x,int y) {
		return modBoard.get((50*y+x)).getValue();
	}
	
	protected boolean nodeExistsAt(int x,int y) {
		if(x<50 && x>=0 && y<50 && y>=0) {

			return true;
		}

		return false;
	}
	
}
