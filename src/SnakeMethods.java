import java.util.ArrayList;

public class SnakeMethods extends NodeArray {
	protected ArrayList<Node> mySnake = new ArrayList();
	private static int mySnakeNum;
	private Node mySnakesecondPos;
	private Node apple;
	private Node start;
	private int appleTime=0;
	private int length;
	
	private ArrayList<Node> enemyPossibleMoves=new ArrayList();

	public SnakeMethods() {
		makeBoard();
	}

	public void setApple(String[] sArr) {
		int x = Integer.parseInt(sArr[0]);
		int y = Integer.parseInt(sArr[1]);
		if(apple!=null) {
			if(x==apple.getX() && y==apple.getY()) {
				appleTime++;
			}else {
				appleTime=0;
			}
		}

		apple = new Node(6, x, y);
		setValueOf(x, y, 6);
	}
	
	public void setLength(int l)
	{
		length=l;
	}
	public void setObstacles(String obs) {
		String[] pairs = obs.split(" ");
		for (String s : pairs) {
			String[] xy = s.split(",");
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);
			setValueOf(x, y, 5);

		}

	}

	public void setMySnakeNum(int num) {
		mySnake.clear();
		enemyPossibleMoves.clear();
		mySnakeNum = num;
		setMySnakeNumNodeArray(num);
	}

	public void setSnake(int num, String co,int l) {
		String[] pairs = co.split(" ");

		for (int i = 0; i < pairs.length - 1; i = i + 1) {

			String[] xy = pairs[i].split(",");
			int x1 = Integer.parseInt(xy[0]);
			int y1 = Integer.parseInt(xy[1]);

			xy = pairs[i + 1].split(",");
			int x2 = Integer.parseInt(xy[0]);
			int y2 = Integer.parseInt(xy[1]);
			if (num == mySnakeNum && i == 0) {
				setLength(l);
				setStart(x1, y1, x2, y2);
			}

			else if (num != mySnakeNum && i == 0) {
				setEnemySnakNextMoves(x1, y1);
			}

			addPoints(num, x1, y1, x2, y2);
		}
	}

	private void setStart(int x, int y, int x2, int y2) {
		start = new Node(mySnakeNum, x, y);
		mySnakesecondPos = new Node(mySnakeNum, x2, y2);

	}

	private void addPoints(int num, int x1, int y1, int x2, int y2) {
		if (x1 == x2) {
			if(y1>y2) {
				for (int i = y1; i >= y2; i--) {
					if (num == mySnakeNum) {
						Node n = new Node(mySnakeNum, x1, i);
						mySnake.add(n);
					}
					setValueOf(x1, i, num);

				}
			}
			else {
				for (int i = y1; i <= y2; i++) {
					if (num == mySnakeNum) {
						Node n = new Node(mySnakeNum, x1, i);
						mySnake.add(n);
					}
					setValueOf(x1, i, num);

				}
			}
			
		} else {
			if(x1>x2) {
				for (int i = x1; i >= x2; i--) {
					if (num == mySnakeNum) {
						Node n = new Node(mySnakeNum, i, y1);
						mySnake.add(n);
					}
					setValueOf(i, y1, num);

				}
			}
			else {
				for (int i = x1; i <= x2; i++) {
					if (num == mySnakeNum) {
						Node n = new Node(mySnakeNum, i, y1);
						mySnake.add(n);
					}
					setValueOf(i, y1, num);

				}
			}
			
		}
	}

	public void setEnemySnakNextMoves(int x, int y) {
		Node node;
		if (nodeExistsAt(x, y - 1)) {
			if (getValueAt(x, y - 1) == 7 || getValueAt(x, y - 1) == 6) {
				setValueOf(x, y - 1, 4);
				node=new Node(x,y-1,4);
				enemyPossibleMoves.add(node);
			}
		}

		if (nodeExistsAt(x, y + 1)) {
			if (getValueAt(x, y + 1) == 7 || getValueAt(x, y + 1) == 6) {
				setValueOf(x, y + 1, 4);
				node=new Node(x,y+1,4);
				enemyPossibleMoves.add(node);
			}
		}

		if (nodeExistsAt(x - 1, y)) {
			if (getValueAt(x - 1, y) == 7 || getValueAt(x - 1, y) == 6) {
				setValueOf(x - 1, y, 4);
				node=new Node(x+1,y,4);
				enemyPossibleMoves.add(node);
			}
		}

		if (nodeExistsAt(x + 1, y)) {
			if (getValueAt(x + 1, y) == 7 || getValueAt(x + 1, y) == 6) {
				setValueOf(x + 1, y, 4);
				node=new Node(x-1,y,4);
				enemyPossibleMoves.add(node);
			}
		}

	}

	public int getMySnakeDir() {
		if (start.getX() == mySnakesecondPos.getX()) {
			if (start.getY() < mySnakesecondPos.getY()) {
				return 0;
			} else {
				return 1;
			}
		} else {
			if (start.getX() < mySnakesecondPos.getX()) {
				return 2;
			} else {
				return 3;
			}
		}
	}

	public int getNextMove() {

		PathFinding pf = new PathFinding(getBoard(), start, apple, getMySnakeDir(), mySnake,appleTime,length,enemyPossibleMoves);
		return pf.getNextMove(mySnakeNum);

	}

	public ArrayList<Node> getBoard() {

		return board;
	}

}
