import java.util.ArrayList;

public class NodeArray {
	protected static ArrayList<Node> board=new ArrayList<Node>();
	
	public NodeArray() {
		
	}
	
//	public NodeArray(ArrayList<Node> b) {
//		board=b;
//	}
//	
	protected void makeBoard() {
		for(int y=0;y<50;y++) {
			for(int x=0;x<50;x++) {
				
				Node node=new Node(7,x,y);
				board.add(node);
			}
		
			
		}
	}
	
	public void resetBoard() {
		
		for(int y=0;y<50;y++) {
			for(int x=0;x<50;x++) {
				
				board.get(50*y+x).setValue(7);
			}
		
			
		}
	}
	
	public void printBoard() {
		for(int y=0;y<50;y++) {
			for(int x=0;x<50;x++) {
				System.err.print(board.get(50*y+x).getValue()+" ");
			}
			System.err.println();
		}
		System.err.println();
	}
	
	protected void setValueOf(int x,int y,int value) {
		board.get((50*y+x)).setValue(value);
	}
	
	protected Node getNodeAt(int x,int y) {
		return board.get((50*y+x));
	}
	
	protected int getValueAt(int x,int y) {
		return board.get((50*y+x)).getValue();
	}
	
	protected boolean nodeExistsAt(int x,int y) {
		if(x<50 && x>=0 && y<50 && y>=0) {

			return true;
		}

		return false;
	}
	

	
	
}
