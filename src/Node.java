//0-3:Snakes
//4: Possible next moves for the other snakes
//5: obstacles
//6: apple
//7:empty spot
//9:My snakes head
//-1: My Snakes immediate next move
public class Node implements Comparable{
	private int value;
	private int x;
	private int y;
	
	private Node parent;
	private double g;
	private double h;
	private double f;
	
	public Node(int value,int x,int y) {
		this.value=value;
		this.x=x;
		this.y=y;
		
		parent=null;
		g=-1;
		h=-1;
		f=-1;
	}
	
	public Node(int value,int x,int y,Node parent,double g,double h) {
		this.value=value;
		this.x=x;
		this.y=y;
		
		this.parent=parent;
		this.g=g;
		this.h=h;
		f=g+h;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}
	
	public String toString() {
		if(parent==null) {
			return "X: "+ x+ " Y: "+y+ " Value: "+value;
		}
		else {
			return "X: "+ x+ " Y: "+y+ " Value: "+value +" Parent "+parent.x+ ":" +parent.y+ " G: "+g+ " H: "+ h+" F: "+f;
		}
	}
	
	@Override
    public int compareTo(Object o) {
		Node second =(Node) o;
		 return (int) (this.f - second.f);
	
	}
}
