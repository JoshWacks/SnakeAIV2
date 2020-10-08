import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PathFinding extends NodeArray {
	private int mySnakeNum;
	private ArrayList<Node>mySnake=new ArrayList();
	private int mySnakeDir;
	
	private Node startNode;
	private Node apple;
	private Node currentNode;
	private ArrayList<Node> openList = new ArrayList<Node>();
	private ArrayList<Node> closedList = new ArrayList<Node>();
	private ArrayList<Node> path = new ArrayList<Node>();
	
	

	public PathFinding(Node s, Node a, int dir,ArrayList<Node> tail) {

		startNode = s;
		apple = a;
		mySnakeDir = dir;
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

	private void addNeighborsToOpenList() {
		Node node;

		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x != 0 && y != 0) {// no diagonal movement
					continue;
				}
				node = new Node(7, currentNode.getX() + x, currentNode.getY() + y, currentNode, currentNode.getG() + 1,
						distance(x, y));
				if (x != 0 || y != 0) {// not the same node
					if (nodeExistsAt(currentNode.getX() + x, currentNode.getY() + y)
							&& (getNodeAt(currentNode.getX() + x, currentNode.getY() + y).getValue() == 7
									|| getNodeAt(currentNode.getX() + x, currentNode.getY() + y).getValue() == 6)
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

	private ArrayList<Node> findPath() {
		currentNode = startNode;

		closedList.add(currentNode);
		addNeighborsToOpenList();

		while (currentNode.getX() != apple.getX() || currentNode.getY() != apple.getY()) {
			if (openList.size() == 0) {
				return null;
			}
			currentNode = getSmallest();
			openList.remove(0);
			closedList.add(currentNode);
			addNeighborsToOpenList();
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

		if (dist > 30) {
			return false;
		}
		return true;

	}

	private int getMove(Node n) {
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
		mySnakeNum = num;
		ArrayList<Node> path = findPath();
		if (path == null || !checkDistance(startNode)) {
			// System.err.println("Getting a safe move");
			return getASafeMove(board,startNode);
		}

		Node n = path.get(1);
		// setValueOf(n.getX(),n.getY(),-1);
		return getMove(n);
	}
	
//	private boolean beginCheck() {
//		NodeArray nodeArray=new NodeArray(board);
//		
//		
//	}

	private int getASafeMove(ArrayList<Node> currentBoard,Node currentStartNode) {
		int startX = currentStartNode.getX();
		int startY = currentStartNode.getY();
		int maxMove = 5;
		ArrayList<Node> possibleMoves = new ArrayList<Node>();

		if (startY - 1 >= 0) {

			if (getValueAt(startX, startY - 1) == 7 || getValueAt(startX, startY - 1) == 6
					|| getValueAt(startX, startY - 1) == 4 || getValueAt(startX, startY - 1) == 8) {

				Node node = new Node(0, startX, startY - 1);
				possibleMoves.add(node);
				// return 0;
			}

		}

		if (startY + 1 < 50) {

			if (getValueAt(startX, startY + 1) == 7 || getValueAt(startX, startY + 1) == 6
					|| getValueAt(startX, startY + 1) == 4 || getValueAt(startX, startY + 1) == 8) {
				Node node = new Node(1, startX, startY + 1);
				possibleMoves.add(node);

				// return 1;

			}
		}

		if (startX - 1 >= 0) {

			if (getValueAt(startX - 1, startY) == 7 || getValueAt(startX - 1, startY) == 6
					|| getValueAt(startX - 1, startY) == 4 || getValueAt(startX - 1, startY) == 8) {
				Node node = new Node(2, startX - 1, startY);
				possibleMoves.add(node);

				// return 2;
			}
		}

		if (startX + 1 < 50) {

			if (getValueAt(startX + 1, startY) == 7 || getValueAt(startX + 1, startY) == 6
					|| getValueAt(startX + 1, startY) == 4 || getValueAt(startX + 1, startY) == 8) {

				Node node = new Node(3, startX + 1, startY);
				possibleMoves.add(node);
				// return 3;
			}

		}
		if (possibleMoves.size() == 0) {
			System.err.println("No safe Move");
			return 5;
		}

		else {

			int max = -9999999;

			for (Node n : possibleMoves) {

				// System.err.println(n.getValue()+" "+calcOpenSpaces(n));
				if (max < calcOpenSpaces(n)) {

					max = calcOpenSpaces(n);
					maxMove = n.getValue();
				}
			}
		}
		if (maxMove == 5) {
			System.err.println("No safe Move");
		}

		return maxMove;
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
	
	private void checkNextMoves(NodeArray nodeArray,Node nextMove,int pass) {
		int move=nextMove.getValue();

		Node head;
		switch(move) {
			case 0: 
				head=mySnake.get(0);
				head.setX(head.getX());
				head.setY(head.getY()-1);
				mySnake.remove(0);
				mySnake.add(0,head);
				nodeArray.setValueOf(head.getX(), head.getY(), mySnakeNum);
				
			case 1:
				head=mySnake.get(0);
				head.setX(head.getX());
				head.setY(head.getY()+1);
				mySnake.remove(0);
				mySnake.add(0,head);
				nodeArray.setValueOf(head.getX(), head.getY(), mySnakeNum);
				
			case 2:
				head=mySnake.get(0);
				head.setX(head.getX()-1);
				head.setY(head.getY());
				mySnake.remove(0);
				mySnake.add(0,head);
				nodeArray.setValueOf(head.getX(), head.getY(), mySnakeNum);
				
			case 3:
				head=mySnake.get(0);
				head.setX(head.getX()+1);
				head.setY(head.getY());
				mySnake.remove(0);
				mySnake.add(0,head);
				nodeArray.setValueOf(head.getX(), head.getY(), mySnakeNum);
		}
		
		moveTail(nodeArray);
		
		

	}
	
	private void moveTail(NodeArray nodeArray) {
		for(int i=1;i<mySnake.size();i++) {
			Node next=mySnake.get(i-1);
			Node current=mySnake.get(i);
			current.setX(next.getX());
			current.setY(next.getY());
			nodeArray.setValueOf(current.getX(), current.getY(), mySnakeNum);
		}
		Node last=mySnake.get(mySnake.size()-1);
		nodeArray.setValueOf(last.getX(), last.getY(), 7);
	}
	
	
}