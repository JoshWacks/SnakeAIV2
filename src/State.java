import java.util.ArrayList;

public class State {
	private ArrayList<Node> boardState = new ArrayList<Node>();
	private State parent;

	private ArrayList<Node> tailState = new ArrayList<Node>();
	private Node startNode;

	private ArrayList<Integer> possibleMoves;
	
	private ArrayList<State>children=null;
	private int moveToMakeState;

	public State(ArrayList<Node> board, State parent, ArrayList<Node> tail, Node start) {
		this.parent = parent;
		copyBoard(board);

		copyTail(tail);
		copyStart(start);
	}

	private void copyBoard(ArrayList<Node> b) {
		Node newNode;
		for (int i = 0; i < b.size(); i++) {
			Node node = b.get(i);
			newNode = new Node(node.getValue(), node.getX(), node.getY());
			boardState.add(newNode);
		}
	}

	private void copyTail(ArrayList<Node> t) {
		Node newNode;
		for (int i = 0; i < t.size(); i++) {
			Node node = t.get(i);
			newNode = new Node(node.getValue(), node.getX(), node.getY());
			tailState.add(newNode);
		}
	}

	private void copyStart(Node s) {
		startNode = new Node(s.getValue(), s.getX(), s.getY());
	}

	public void printBoard() {
		for (int y = 0; y < 50; y++) {
			for (int x = 0; x < 50; x++) {
				System.err.print(boardState.get(50 * y + x).getValue() + " ");
			}
			System.err.println();
		}
		System.err.println();
	}

	public ArrayList<Node> getBoardState() {
		return boardState;
	}

	public void setBoardState(ArrayList<Node> boardState) {
		this.boardState = boardState;
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public ArrayList<Node> getTailState() {
		return tailState;
	}

	public void setTailState(ArrayList<Node> tailState) {
		this.tailState = tailState;
	}

	public ArrayList<Integer> getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(ArrayList<Integer> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	
	public ArrayList<State> getChildren() {
		return children;
	}
	
	

	public int getMoveToMakeState() {
		return moveToMakeState;
	}

	public void setMoveToMakeState(int moveToMakeState) {
		this.moveToMakeState = moveToMakeState;
	}

	public void setChildren(ArrayList<State> children) {
		this.children = children;
	}

	public void setValueOf(int x, int y, int value) {
		// System.err.println("X "+x+" Y "+y);
		// System.err.println("index "+(50*y+x));
		boardState.get((50 * y + x)).setValue(value);
	}

	public Node getNodeAt(int x, int y) {
		return boardState.get((50 * y + x));
	}

	public int getValueAt(int x, int y) {
		return boardState.get((50 * y + x)).getValue();
	}

	public boolean nodeExistsAt(int x, int y) {
		if (x < 50 && x >= 0 && y < 50 && y >= 0) {

			return true;
		}

		return false;
	}

}
