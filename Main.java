import java.util.Random;

import stdlib.StdDraw;
import stdlib.StdRandom;

public class Main {

    
    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
    	 int N = a.length;
         StdDraw.setXscale(-1, N);;
         StdDraw.setYscale(-1, N);
         StdDraw.setPenColor(StdDraw.BLACK);
         for (int i = 0; i < N; i++)
             for (int j = 0; j < N; j++)
                 if (a[i][j] == which)
                 	StdDraw.square(j, N-i-1, .5);
                 else StdDraw.filledSquare(j, N-i-1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int start[], int goal[]) 
    {
    	int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
            	if (i == start[0] && j == start[1]) 
            	{
            		StdDraw.setPenColor(StdDraw.GREEN);
            		StdDraw.filledSquare(j, N-i-1, .5);
            		StdDraw.setPenColor();
            	}
            	else if(i == goal[0] && j == goal[1])
            	{
            		StdDraw.setPenColor(StdDraw.RED);
            		StdDraw.filledSquare(j, N-i-1, .5);
            		StdDraw.setPenColor();
            	}
            	else if (a[i][j] == which)
                {
                	StdDraw.square(j, N-i-1, .5);
                }
                else StdDraw.filledSquare(j, N-i-1, .5);
            }
        }    }
    
    public static String revealPath(Node node, int gridsize, int start[]) 
    {
        StdDraw.setXscale(-1, gridsize);
        StdDraw.setYscale(-1, gridsize);
        StdDraw.setPenColor(StdDraw.YELLOW);
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("GoalNode{X%s, Y:%s} -> ", node.getX(), node.getY()));
        node = node.getParent();
        while (node != null)
        {
        	if (node.getX() == start[0] && node.getY() == start[1])
        	{
        		sb.append(String.format("StartNode{X%s, Y:%s}", node.getX(), node.getY()));
        		if (node.getParent() != null)
        		{
        			System.out.println("Pls no.. have mercy");
        		}
        		break;
        	}
        	
        	StdDraw.filledSquare(node.getY(), gridsize-node.getX()-1, .5);
        	sb.append(String.format("{X%s, Y:%s} -> ", node.getX(), node.getY()));
        	node = node.getParent();
        }
        
        return sb.toString();
    }
    
    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) 
    {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }
    
	public static void main(String[] args) 
	{
		Random rand = new Random();
    	double freeAreaDensity = 0.5;
    	int gridSize = 10;
    	int[] goal = {rand.nextInt(gridSize), rand.nextInt(gridSize)};
    	int[] start = {rand.nextInt(gridSize), rand.nextInt(gridSize)};
    	boolean[][] randomlyGenMatrix = random(gridSize, freeAreaDensity);
    	Node[][] nodeGrid = new Node[gridSize][gridSize];
    	
    	randomlyGenMatrix[goal[0]][goal[1]] = false;
    	randomlyGenMatrix[start[0]][start[1]] = false;
    	show(randomlyGenMatrix, false, start, goal);
    	
    	//Initialise Node Grid
    	Node startNode = null;
    	Node endNode = null;
    	for (int i = 0; i < gridSize; i++)
    	{
    		for (int j = 0; j < gridSize; j++)
    		{
    			Node node = new Node();
    			node.setX(i);
    			node.setY(j);
    			if (i == start[0] && j == start[1])
    			{
    				node.setStart(true);
    				startNode = node;
    			}
    			else if (i == goal[0] && j == goal[1])
    			{
    				node.setGoal(true);
    				endNode = node;
    			}
    			else if (randomlyGenMatrix[i][j])
    			{
    				node.setObstacle(true);
    			}
    			
    			nodeGrid[i][j] = node;
    		}
    	}
    	
    	for (int i = 0; i < gridSize; i++)
    	{
    		for (int j = 0; j < gridSize; j++)
    		{
    			nodeGrid[i][j].setgCost(PathCalculator.getNodeDistance(nodeGrid[i][j], startNode));
    			nodeGrid[i][j].sethCost(PathCalculator.getNodeDistance(nodeGrid[i][j], endNode));
    			nodeGrid[i][j].setfCost(nodeGrid[i][j].getgCost()+nodeGrid[i][j].gethCost());
    		}
    	}
    	
    	PathCalculator pc = new PathCalculator(nodeGrid, startNode, endNode);
    	Node pathNode = pc.Calculate();
    	
    	if (pathNode == null)
    	{
    		System.out.println("No Path!");
    		return;
    	}
    	
    	System.out.println(revealPath(pathNode, gridSize, start));
	}

}
