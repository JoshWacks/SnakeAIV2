import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PathFinding extends NodeArray {
	private int mySnakeNum;
	private ArrayList<Node>mySnake=new ArrayList<Node>();
	private int mySnakeDir;
	
	private Node startNode;
	private Node apple;
	private int appleTime;
	private Node currentNode;
	private ArrayList<Node> openList = new ArrayList<Node>();
	private ArrayList<Node> closedList = new ArrayList<Node>();
	private ArrayList<Node> path = new ArrayList<Node>();
	
	private int mySnakeLength;
	private ArrayList<Node> enemyPossibleMoves=new ArrayList();

	public PathFinding(ArrayList<Node>b,Node s, Node a, int dir,ArrayList<Node> tail,int time,int l,ArrayList<Node> enemy) {
		setBoard(b);
		startNode = s;
		apple = a;
		mySnakeDir = dir;
		mySnake=tail;
		appleTime=time;
		mySnakeLength=l;
		enemyPossibleMoves=enemy;
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
							&& (
								getNodeAt(currentNode.getX() + x, currentNode.getY() + y).getValue() == 7 ||
							    getNodeAt(currentNode.getX() + x, currentNode.getY() + y).getValue() == target
							   )
							
							&& !findNeighborInList(openList, node)
							&& !findNeighborInList(closedList, node)) {
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

		if (dist > 24 ) {
			return false;
		}
		return true;

	}
	
//	private boolean checkFreeApple() {
//		
//	}

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
		ModMethods modMethods=new ModMethods( board,mySnake, startNode,mySnakeDir,mySnakeLength,enemyPossibleMoves);
		if(appleTime>50 || !checkDistance(startNode)) {
			return modMethods.getNextSafeMove();
		}
		ArrayList<Node> path = findPath(apple);
		if (path == null ) {

			return modMethods.getNextSafeMove( );
		}

		Node n = path.get(1);
		if(modMethods.checkSafeApple(getMove(n))) {
			return getMove(n);
		}
		return modMethods.getNextSafeMove( );
	}
	

	
	private void clearLists() {
		openList.clear();
		closedList.clear();
		path.clear();
	}

	
}