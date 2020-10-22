import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ModMethods {
	private int dir;
	private static int mySnakeNum;

	private NodeArray nodeArray;

	private State initialState;
	private int mySnakeLength;
	private ArrayList<Node> enemyPossibleMoves=new ArrayList();
	// visualDebug vBug=new visualDebug();

	
	public ModMethods(ArrayList<Node> game, ArrayList<Node> tail, Node currentStartNode, int dir, int l,ArrayList<Node> enemy) {
		this.nodeArray = new NodeArray();
		initialState = new State(game, null, tail, currentStartNode);// root state has null as parent

		mySnakeNum = nodeArray.getMySnakeNum();

		this.dir = dir;
		mySnakeLength=l;
		enemyPossibleMoves=enemy;
;

	}

	public int getNextSafeMove() {

		return checkMoves();

	}

	private ArrayList<Integer> getASafeMove(State currentState) {

		int startX = currentState.getStartNode().getX();
		int startY = currentState.getStartNode().getY();
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();

		if (startY - 1 >= 0) {

			if (currentState.getValueAt(startX, startY - 1) == 7 || currentState.getValueAt(startX, startY - 1) == 7) {
				possibleMoves.add( 0);
			}
		}

		if (startY + 1 < 50) {
			if (currentState.getValueAt(startX, startY + 1) == 7 || currentState.getValueAt(startX, startY + 1) == 7) {
				possibleMoves.add(1);
			}

		}

		if (startX - 1 >= 0) {

			if (currentState.getValueAt(startX - 1, startY) == 7 || currentState.getValueAt(startX - 1, startY) == 7) {
				possibleMoves.add(2);
			}


		}

		if (startX + 1 < 50) {

			if (currentState.getValueAt(startX + 1, startY) == 7 || currentState.getValueAt(startX + 1, startY) == 7) {
				possibleMoves.add(3);
			}


		}


		return possibleMoves;
	}
	
	private ArrayList<Node> getASafeNode(State currentState) {

		int startX = currentState.getStartNode().getX();
		int startY = currentState.getStartNode().getY();
		ArrayList<Node> possibleMoves = new ArrayList<Node>();

		if (startY - 1 >= 0) {

			if (currentState.getValueAt(startX, startY - 1) == 7 || currentState.getValueAt(startX, startY - 1) == 7) {
				possibleMoves.add( currentState.getNodeAt(startX, startY-1));
			}
		}

		if (startY + 1 < 50) {
			if (currentState.getValueAt(startX, startY + 1) == 7 || currentState.getValueAt(startX, startY + 1) == 7) {
				possibleMoves.add( currentState.getNodeAt(startX, startY+1));
			}

		}

		if (startX - 1 >= 0) {

			if (currentState.getValueAt(startX - 1, startY) == 7 || currentState.getValueAt(startX - 1, startY) == 7) {
				possibleMoves.add( currentState.getNodeAt(startX-1, startY));
			}


		}

		if (startX + 1 < 50) {

			if (currentState.getValueAt(startX + 1, startY) == 7 || currentState.getValueAt(startX + 1, startY) == 7) {
				possibleMoves.add( currentState.getNodeAt(startX+1, startY));
			}


		}


		return possibleMoves;
	}
	
	private int safe(State currentState) {

		int startX = currentState.getStartNode().getX();
		int startY = currentState.getStartNode().getY();
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();

		if (startY - 1 >= 0) {

			if (currentState.getValueAt(startX, startY - 1) == 7 || currentState.getValueAt(startX, startY - 1) == 4|| currentState.getValueAt(startX, startY - 1) == 6) {
				possibleMoves.add( 0);
			}
		}

		if (startY + 1 < 50) {
			if (currentState.getValueAt(startX, startY + 1) == 7 || currentState.getValueAt(startX, startY + 1) == 4|| currentState.getValueAt(startX, startY + 1) == 6) {
				possibleMoves.add(1);
			}

		}

		if (startX - 1 >= 0) {

			if (currentState.getValueAt(startX - 1, startY) == 7 || currentState.getValueAt(startX - 1, startY) == 4|| currentState.getValueAt(startX - 1, startY) == 6) {
				possibleMoves.add(2);
			}


		}

		if (startX + 1 < 50) {

			if (currentState.getValueAt(startX + 1, startY) == 7 || currentState.getValueAt(startX + 1, startY) == 4 || currentState.getValueAt(startX + 1, startY) == 6) {
				possibleMoves.add(3);
			}


		}

		if(possibleMoves.size()>0) {
			return possibleMoves.get(0);
			
		}
		else return 5;

	}
	


	private int checkMoves() {
		//activatePossibleMoves(intialState);
		initialState.setPossibleMoves(getASafeMove(initialState));
		
		if(initialState.getPossibleMoves().size()==0) {
			initialState.printBoard();
			System.err.println("51");
			return safe(initialState);
		}
		int maxNumMoves=0;
		int move=5;
		int mostImmediateSpaces=0;
		
		for(int i:initialState.getPossibleMoves()) {
			int temp=openNodes(i);
			
			if(maxNumMoves<temp) {
				maxNumMoves=temp;
				move=i;
				
			}

//			if(temp==maxNumMoves) {
//				int immSpaces=getImmediateSpaces(i);
//				
//				if(mostImmediateSpaces<immSpaces) {
//					mostImmediateSpaces=immSpaces;
//					move=i;
//				}
//			}
		}
		if(move==5) {
			System.err.println("52");
			return initialState.getPossibleMoves().get(0);
		}
		
		return move;
	}
	
	private int openNodes(int move) {
		State nextState=new State(initialState.getBoardState(),initialState,initialState.getTailState(),initialState.getStartNode());
		moveSnake(move,nextState);
		
		ArrayList<Node>visited=new ArrayList<Node>();
		Queue<Node> queue=new LinkedList<>();
		queue.add(nextState.getStartNode());
		Node currentNode;
		

		while(!queue.isEmpty()) {
			
			
			currentNode=queue.remove();
			//System.err.println(currentNode.toString());
			if(!visited.contains(currentNode)) {
				visited.add(currentNode);
				nextState.setValueOf(currentNode.getX(), currentNode.getY(), mySnakeNum);
				nextState.setStartNode(currentNode);
				for(Node n:getASafeNode(nextState)) {
					queue.add(n);
				}
				
			}
			
			if(visited.size()>1000) {
				break;
			}
			

			
		}
		

		return visited.size();
		
	}
	
	private int getImmediateSpaces(int move) {
		State nextState=new State(initialState.getBoardState(),initialState,initialState.getTailState(),initialState.getStartNode());
		moveSnake(move,nextState);
		return getASafeMove(nextState).size();
	}
	
	
	public boolean checkSafeApple(int move) {
		//System.err.println(move);
		int openSpaces=openNodes(move);
		
		if(openSpaces>(mySnakeLength*4)){
			return true;
		}
		return false;
	}
	

	
	
//	private ArrayList<Integer> immOpenSpace(State state){
//		int startX = currentState.getStartNode().getX();
//		int startY = currentState.getStartNode().getY();
//		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
//
//		
//		
//	}
		
//		for(int move:intialState.getPossibleMoves()) {
//			State nextState=new State(intialState.getBoardState(),intialState,intialState.getTailState(),)
//		}
////		setChildren(intialState);
////		for(State child:intialState.getChildren()) {
////			setChildren(child);
////			for(State childTwo:child.getChildren()) {
////				setChildren(childTwo);
////			}
////		}
////		
////		int maxNumMoves=-100;
////		int move=5;
////		
////		for(int m:intialState.getPossibleMoves()) {
////			int temp=checkMoveRec(0,m,intialState,0);
////			if(maxNumMoves<temp) {
////				maxNumMoves=temp;
////				move=m;
////			}
////		}
////		return move;
//		return checkMoveRec();

//	}
	
//	private int getOpenSpaces(State state) {
//		int score=0;
//		for(int i=-5;i<=5;i++) {
//			for(int k=-5;k<=5;k++) {
//				if(state.nodeExistsAt(i, k)) {
//					if(state.getValueAt(i, k)==7) {
//						score++;
//					}else {
//						score--;
//					}
//				}
//				else {
//					score--;
//				}
//			}
//		}
//	}
//	
//	private void setChildren(State state) {
//
//		
//		state.setPossibleMoves(getASafeMove(state));
//		ArrayList<State>children=new ArrayList<State>();
//		for(int move:state.getPossibleMoves()) {
//			State childState=new State(state.getBoardState(),state,state.getTailState(),state.getStartNode());
//			childState.setMoveToMakeState(move);
//			moveSnake(move,childState);
//			children.add(childState);
//		}
//		state.setChildren(children);
//		
//
//	}
//
//	private int checkMoveRec( ) {
//		int maxNumMoves=-100;
//		int move=5;
//		for(State s:intialState.getChildren() ) {
//			if(s.getChildren().size()>maxNumMoves) {
//				maxNumMoves=s.getChildren().size();
//				move=s.getMoveToMakeState();
//			}
//		}
//		return move;
//		
//	}
//
	private void moveSnake(int move, State state) {

		Node head = state.getTailState().get(0);
		head.setPrevX(head.getX());
		head.setPrevY(head.getY());

		// System.err.println("FirstX: "+ head.getX()+"FirstY: "+" "+head.getY());
		switch (move) {
		case 0:
			head.setY(head.getY() - 1);

			state.setValueOf(head.getX(), head.getY(), mySnakeNum);
			break;
		case 1:
			head.setY(head.getY() + 1);

			state.setValueOf(head.getX(), head.getY(), mySnakeNum);
			break;
		case 2:

			head.setX(head.getX() - 1);
			state.setValueOf(head.getX(), head.getY(), mySnakeNum);
			break;
		case 3:

			head.setX(head.getX() + 1);
			state.setValueOf(head.getX(), head.getY(), mySnakeNum);

		}
		state.setStartNode(head);

		moveTail(state);// we have completed 1 move here

	}

	private void moveTail(State state) {

		Node current;
		Node prev;
		for (int i = 1; i < state.getTailState().size(); i++) {
			prev = state.getTailState().get(i - 1);
			current = state.getTailState().get(i);


			current.setPrevX(current.getX());
			current.setPrevY(current.getY());

			current.setX(prev.getPrevX());// here we move forward
			current.setY(prev.getPrevY());


		}
		Node last = state.getTailState().get(state.getTailState().size() - 1);
		state.setValueOf(last.getPrevX(), last.getPrevY(), 7);

	}

//	  public void vDebug(ArrayList<Node>board)
//	    {
//	    	
//	    	NodeArray playArea=new NodeArray();
//	    	playArea.setBoard(board);
//	    	int row = 50;
//	    	int col = 50;
//	    	imgView v = new imgView(playArea,row,col);
//	        vBug.update(v.getImage());
//	    }
//	

}
