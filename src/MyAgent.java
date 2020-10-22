import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import za.ac.wits.snake.DevelopmentAgent;

public class MyAgent extends DevelopmentAgent {
	SnakeMethods snakeMethods=new SnakeMethods();
	
	//visualDebug vBug=new visualDebug();
	
    public static void main(String args[]) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
        	
        	
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);

            while (true) {
            	snakeMethods.resetBoard();
            	
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }

                String apple = line;
                String[] applePos=apple.split(" ");
                snakeMethods.setApple(applePos);
                
                String s="";
                int nObstacles = 3;
                for (int obstacle = 0; obstacle < nObstacles; obstacle++) {
                    String obs = br.readLine();
                    String obsArr[]=obs.split(" ");
                    for(String pair:obsArr) {
                    	String[] xy=pair.split(",");
                    	
                    	s=s+xy[0]+","+xy[1]+" ";
                    	
                    }
                }
                snakeMethods.setObstacles(s);
                
                int mySnakeNum = Integer.parseInt(br.readLine());
                snakeMethods.setMySnakeNum(mySnakeNum);
                String[] snakeLine;
                String coOrds;
                for (int i = 0; i < nSnakes; i++) {
                    snakeLine = br.readLine().split(" ");
                    coOrds="";
                    if(snakeLine[0].equals("alive")) {//only should do something with the info if it is alive
//	                    if (i == mySnakeNum) {
                    		int length=Integer.parseInt(snakeLine[1]);
	                    	for(int k=3;k<snakeLine.length;k++) {
	                    		String xy=snakeLine[k];
	                    		coOrds=coOrds+xy+" ";
	                    	}
	                    	snakeMethods.setSnake(i,coOrds,length);
//	                    }
                    }

                }
                Timer myTimer=new Timer();

                myTimer.start();
                int move = snakeMethods.getNextMove();
              //  vDebug(snakeMethods.getBoard());
                System.out.println(move);
                myTimer.stop();
                if( myTimer.getTime()>30) {
                	  System.err.println(" Time : " + myTimer.getTime());
                }
              
               // snakeMethods.printBoard();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void vDebug(ArrayList<Node>board)
//    {
//    	
//    	NodeArray playArea=new NodeArray();
//    	playArea.setBoard(board);
//    	int row = 50;
//    	int col = 50;
//    	imgView v = new imgView(playArea,row,col);
//        vBug.update(v.getImage());
//    }
    
}