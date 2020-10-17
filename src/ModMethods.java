import java.util.ArrayList;
import java.util.Random;

public class ModMethods  {
	private int dir;
	private static int mySnakeNum;
	
	private NodeArray nodeArray;	
	
	private State intialState;
	
	public ModMethods() {

			this.nodeArray=new NodeArray();
	}
	
	public int getNextSafeMove(ArrayList<Node>game, ArrayList<Node> tail,Node currentStartNode,int dir) {
		intialState=new State(game,null,tail,currentStartNode);//root state has null as parent
		
		
		mySnakeNum=nodeArray.getMySnakeNum();

		

		this.dir=dir;
		return checkMoves();

	}
	
private ArrayList<Integer> getASafeMove(State currentState) {
		
		int startX = currentState.getStartNode().getX();
		int startY = currentState.getStartNode().getY();
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();

		if (startY - 1 >= 0) {

			if (currentState.getValueAt(startX, startY - 1) == 7 || currentState.getValueAt(startX, startY - 1) == 8) {
				possibleMoves.add(0,0);
			}
					if( currentState.getValueAt(startX, startY - 1) == 4 ) {

				
				possibleMoves.add(0);

			}
		}

		
		if (startY + 1 < 50) {
			if (currentState.getValueAt(startX, startY + 1) == 7 || currentState.getValueAt(startX, startY + 1) == 8) {
				possibleMoves.add(0,1);
			}
					if( currentState.getValueAt(startX, startY + 1) == 4 ) {
				
				possibleMoves.add(1);
			}
		}
		

		if (startX - 1 >= 0) {

			if (currentState.getValueAt(startX - 1, startY) == 7|| currentState.getValueAt(startX - 1, startY) == 8) {
				possibleMoves.add(0, 2);
			}

			if(currentState.getValueAt(startX - 1, startY) == 4 ) {
				
				possibleMoves.add(2);

				
				
			}
		}

		if (startX + 1 < 50) {

			if (currentState.getValueAt(startX + 1, startY) == 7|| currentState.getValueAt(startX + 1, startY) == 8) {
				possibleMoves.add(0, 3);
			}
					if( currentState.getValueAt(startX + 1, startY) == 4 ) {

				
				possibleMoves.add(3);
			
			}

		}
		
//			for(int k=0;k<possibleMoves.size();k++) {
//				int i=possibleMoves.get(k);
//				if(i==dir) {
//					int temp=i;
//				   possibleMoves.remove(possibleMoves.indexOf(i));
//				   possibleMoves.add(0, temp);
//				}
//			}
		
		
		return possibleMoves;
	}
	
	private int checkMoves() {
		intialState.setPossibleMoves(getASafeMove(intialState));
		
		if(intialState.getPossibleMoves().isEmpty()) {
			return 5;
		}

		for(int i:intialState.getPossibleMoves()) {
			checkMoveRec(i,intialState);
			System.err.println("Move "+i);

			return i;
			
		}
		
		return 5;
	}
	
	private void checkMoveRec(int move,State state) {
		State nextState=new State(state.getBoardState(),state,state.getTailState(),state.getStartNode());
		moveSnake(move,nextState);
		
		
	}
	
	

	private void moveSnake(int move,State state) {
		
		Node head=state.getTailState().get(0);
		head.setPrevX(head.getX());
		head.setPrevY(head.getY());
		
		System.err.println("FirstX: "+ head.getX()+"FirstY: "+" "+head.getY());
		switch(move) {
		case 0:
			head.setY(head.getY()-1);

			
			state.setValueOf(head.getX(),head.getY(), mySnakeNum);
			break;
		case 1:
			head.setY(head.getY()+1);

			state.setValueOf(head.getX(),head.getY(), mySnakeNum);
			break;
		case 2:

			head.setX(head.getX()-1);
			state.setValueOf(head.getX(),head.getY(), mySnakeNum);
			break;
		case 3:

			head.setX(head.getX()+1);
			state.setValueOf(head.getX(),head.getY(), mySnakeNum);
			
			
		}
		state.setStartNode(head);

		moveTail(state);//we have completed 1 move here
		
	}
	
	private void moveTail(State state) {
		
		Node current;
		Node prev;
		for(int i=1;i<state.getTailState().size();i++) {
			prev=state.getTailState().get(i-1);
			current=state.getTailState().get(i);
			
			current.setPrevX(current.getX());
			current.setPrevY(current.getY());
			
			current.setX(prev.getPrevX());//here we move forward
			current.setY(prev.getPrevY());
			
			if(i==(state.getTailState().size()-1)) {
				state.setValueOf(current.getPrevX(),current.getPrevY(),7);
			}
			else {
				state.setValueOf(current.getX(),current.getY(),mySnakeNum);
			}
			
		}
	
		
		
		
		
		
	}
	
	
	
	
	
}
