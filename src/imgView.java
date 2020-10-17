import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class imgView {
	
	int x;
	int y;
	NodeArray playArea;
	BufferedImage img;
	Node apple;

	List<Node> path = null;
	Node next = null;
	
	int mySnakeNum;
	
	public imgView(NodeArray data,int row,int col)
	{
		playArea = data;
		x = col;
		y = row;

		img = new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
		
		
	}
	
	BufferedImage getImage()
	{
		
		
		updateImage();
		return img;
	}
	
	
	public void updateImage()
	{
		File f = null;
		
		for(int i=0;i<y;i++)
		{
			for(int j =0;j<x;j++)
			{
				
				Node n = playArea.getNodeAt(i, j);


				
				switch(n.getValue()) {
				case -1:
					img.setRGB(i,j,Color.PINK.getRGB());
					break;
				case 0:
					img.setRGB(i,j,Color.red.getRGB());
					break;
					
				case 1:
					img.setRGB(i,j,Color.black.getRGB());
					break;
					
				case 2:
					img.setRGB(i,j,Color.blue.getRGB());
					break;
					
				case 3:
					img.setRGB(i,j,Color.magenta.getRGB());
					break;
					
				case 4:
					img.setRGB(i,j,Color.cyan.getRGB());
					break;
					
				case 5:
					img.setRGB(i,j,Color.orange.getRGB());
					break;
					
				case 6:
					img.setRGB(i,j,Color.gray.getRGB());
					break;
					
//				case 7:
//					img.setRGB(i,j,Color.white.getRGB());
//					break;
					
				case 8:
					img.setRGB(i,j,Color.green.getRGB());
					break;
					
				case 9:
					img.setRGB(i,j,Color.YELLOW.getRGB());
					break;
				}
			
			}
		}
		
	}
	/*
	public void displayApple(Vertex v)
	{
		img.setRGB(v.vertexNumber%50, (v.vertexNumber - v.vertexNumber%50)/50 ,Color.red.getRGB());
	}
	
	public void displaySnakes()
	{
		int[] colours = {Color.red.getRGB(),Color.green.getRGB(),Color.blue.getRGB(),Color.yellow.getRGB()};
		ArrayList<Node> body;
		
		for(int i = 0;i<snakes.size();i++)
		{
			body = snakes.get(i).body;
			
			for(int j = 0;j<body.size();j++)
			{
				Node v = body.get(j);
				img.setRGB(v.x,v.y,colours[i]);
			}
		}
	}
	*/
	
//	public void displayPath()
//	{
//		int c = Color.pink.getRGB();
//		
//		for(Node v : path)
//		{
//			if(v.type == 9 || v.type == 7) {
//				continue;
//			}
//			img.setRGB(v.vertexNumber%50, (v.vertexNumber - v.vertexNumber%50)/50 ,c);
//		}
//	}
//	
//	
//	public void displayNext()
//	{
//		int c = Color.orange.getRGB();
//		img.setRGB(next.vertexNumber%50, (next.vertexNumber - next.vertexNumber%50)/50,c);
//	}
//	
	
	

}