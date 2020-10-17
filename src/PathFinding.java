import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PathFinding extends NodeArray {
	private int mySnakeNum;
	private ArrayList<Node>mySnake=new ArrayList<Node>();
	private int mySnakeDir;
	
	private Node startNode;
	private Node apple;
	private Node currentNode;
	private ArrayList<Node> openList = new ArrayList<Node>();
	private ArrayList<Node> closedList = new ArrayList<Node>();
	private ArrayList<Node> path = new ArrayList<Node>();
	
	

	public PathFinding(ArrayList<Node>b,Node s, Node a, int dir,ArrayList<Node> tail) {
		setBoard(b);
		startNode = s;
		apple = a;
		mySnakeDir = dir;
		mySnake=tail;
	}
	
	public PathFinding(Node s, Node a, ArrayList<Node> tail) {
		
		startNode = s;
		apple = a;
		mySnake=tail;
	}

	private double distance(int dx, int dy) {
		double sum1 = Math.pow(currentNode.getX() + dx - apple.getX(), 2);
		double sum2 = Math.pow(currentNode.getY() + dy - apple.getY(), 2);
		return Math.sqrt(sum1 + sum2);
	}

	private boolean findNeighborInList(ArrayList<Node> array, Node node) {

		return array.stream().anyMatch((n) -> (n.getX() == node.getX() && n.getY() == node.getY()));
	}

	private void addNeighborsToOpenList(int target) {
		Node node;

		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x != 0 && y != 0) {// no diagonal movement
					continue;
				}
				node = new Node(7, currentNode.getX() + x, currentNode.getY() + y, 
						currentNode, 
						currentNode.getG() + 1,
						distance(x, y));
				if (x != 0 || y != 0) {// not the same node
					if (nodeExistsAt(currentNode.getX() + x, currentNode.getY() + y)
							&& (getNodeAt(currentNode.getX() + x, currentNode.getY() + y).getValue() == 7
									|| getNodeAt(currentNode.getX() + x, currentNode.getY() + y).getValue() == target)
							&& !findNeighborInList(openList, node) && !findNeighborInList(closedList, node)) {
						// System.err.println(node.toString());
						openList.add(node);
					}
				}
			}
		}

	}

	private Node getSmallest() {
		double min = 9999;
		Node smallest = null;

		for (Node n : openList) {
			if (n.getF() < min) {
				min = n.getF();
				smallest = n;
			}
		}
		return smallest;
	}

	private ArrayList<Node> findPath(Node target) {
		currentNode = startNode;
		closedList.add(currentNode);
		addNeighborsToOpenList(target.getValue());

		while (currentNode.getX() != target.getX() || currentNode.getY() != target.getY()) {
			if (openList.size() == 0) {
				return null;
			}
			currentNode = getSmallest();
			openList.remove(0);
			closedList.add(currentNode);
			addNeighborsToOpenList(target.getValue());
		}
		path.add(0, currentNode);
		while (currentNode.getX() != startNode.getX() || currentNode.getY() != startNode.getY()) {// backtracking to get
																									// the path

			currentNode = currentNode.getParent();
			path.add(0, currentNode);
			setValueOf(currentNode.getX(), currentNode.getY(), 8);
		}
		// setValueOf(startNode.getX(),startNode.getY(),9);
		return path;
	}

	private boolean checkDistance(Node n) {

		double sum1 = Math.pow(n.getX() - apple.getX(), 2);
		double sum2 = Math.pow(n.getY() - apple.getY(), 2);

		double dist = Math.sqrt(sum1 + sum2);

		if (dist > 25) {
			return false;
		}
		return true;

	}

	private int getMove(Node n) {
		setValueOf(n.getX(),n.getY(),9);
		if (startNode.getX() == n.getX()) {// a move in the y
			if (startNode.getY() > n.getY()) {
				return 0;
			} else {
				return 1;
			}
		} else {
			if (startNode.getX() > n.getX()) {
				return 2;
			} else {
				return 3;
			}
		}
	}

	public int getNextMove(int num) {
		clearLists();
		mySnakeNum = num;
		ModMethods modMethods=new ModMethods();
		if(!checkDistance(startNode)) {
			return modMethods.getNextSafeMove( board,mySnake, startNode,mySnakeDir);
		}
		ArrayList<Node> path = findPath(apple);
		if (path == null ) {
			// System.err.println("Getting a safe move");
			return modMethods.getNextSafeMove( board,mySnake, startNode,mySnakeDir);
		}

		Node n = path.get(1);
		// setValueOf(n.getX(),n.getY(),-1);
		return getMove(n);
	}
	
	public int getSafeMove(Node openSpace) {
		clearLists();
		System.err.println("Value "+openSpace.getValue());
		ArrayList<Node> path = findPath(openSpace);
		if(path==null) {
			System.err.println("null");
			return -1;
		}
		setValueOf(openSpace.getX(),openSpace.getY(),-1);
		if(path.size()==1) {
			return getMove(path.get(0));
		}
		return getMove(path.get(1));
	}
	
	private void clearLists() {
		openList.clear();
		closedList.clear();
		path.clear();
	}
	


	

	private int calcOpenSpaces(Node nextNodes) {
		
		int numOpenScore = 0;
		int xNeg = nextNodes.getX();
		int yNeg = nextNodes.getY();
		if(nextNodes.getValue()==mySnakeDir) {
			numOpenScore=numOpenScore+10;//Favors going straight
		}

		for (int x = nextNodes.getX(); x <= nextNodes.getX() +3; x++) {
			for (int y = nextNodes.getY(); y <= nextNodes.getY() + 3; y++) {
				yNeg = nextNodes.getY();

				if (nodeExistsAt(x, y)) {

					if(getValueAt(x,y)==7 ) {
						numOpenScore=numOpenScore+5;
					}
					if (getValueAt(x,y)==8 ) {
						numOpenScore=numOpenScore+5;
					}
					if (getValueAt(x,y)==4 ) {
						numOpenScore=numOpenScore+1;
					}
					if (getValueAt(x,y)==mySnakeNum ) {
						numOpenScore=numOpenScore-15;
					}



				}

				if (nodeExistsAt(xNeg, yNeg)) {

					if(getValueAt(xNeg,yNeg)==7 ) {
						numOpenScore=numOpenScore+5;
					}
					if (getValueAt(xNeg,yNeg)==8 ) {
						numOpenScore=numOpenScore+5;
					}
					if (getValueAt(xNeg,yNeg)==4 ) {
						numOpenScore=numOpenScore+1;
					}
					
					if(getValueAt(xNeg,yNeg)==mySnakeNum ) {
						numOpenScore=numOpenScore-15;
					}

				}

				yNeg = yNeg - 1;
			}
			xNeg = xNeg - 1;
		}

		return numOpenScore;
	}
	
	
	
	
}