import java.util.HashSet;

public class PathCalculator {
	
	float linearMoveCost = 1f;
	float diagonalMoveCost = 1.4f;
	int[][] adjacentMatrix = 
		{
			{-1, -1}, {-1, 0}, {-1, 1},
			{0, -1}, {0, 0}, {0, 1},
			{1, -1}, {1, 0}, {1, 1}
		};
	
	private Node start, finish;
	private Node[][] grid;
	
	public PathCalculator(Node[][] grid, Node start, Node finish)
	{
		this.start = start;
		this.finish = finish;
		this.grid = grid;
	}
	
	/**
	 * SetMoveCost(float linear, float diagonal)
	 * 
	 * @param linear
	 * @param diagonal
	 * 
	 * @return void
	 * 
	 * @deprecated
	 */
	public void SetMoveCost(float linear, float diagonal)
	{
		linearMoveCost = linear;
		diagonalMoveCost = diagonal;
	}
	
	/**
	 * Calculate(void)
	 * 
	 * Will calculate the path from start to finish node and return the node with a parent chain revealing the path.
	 * 
	 * @return Node with path or null if no path.
	 */
	public Node Calculate()
	{
		HashSet<Node> openNodes = new HashSet<Node>();
		HashSet<Node> closedNodes = new HashSet<Node>();
		
		openNodes.add(start);
		
		while(!openNodes.isEmpty())
		{
			Node current = null;
			for (Node node : openNodes) 
			{
				if (current == null)
				{
					current = node;
					continue;
				}
				
				if (node.getfCost() < current.getfCost())
				{
					current = node;
					continue;
				}
				
				if (node.getfCost() == current.getfCost() && node.gethCost() < current.gethCost())
				{
					current = node;
					continue;
				}
			}
			
			openNodes.remove(current);
			closedNodes.add(current);
			if (current.equals(finish))
			{
				return current;
			}
			
			for (int i = 0; i < adjacentMatrix.length; i++)
			{
				int neighbourX = current.getX()+adjacentMatrix[i][0];
				int neighbourY = current.getY()+adjacentMatrix[i][1];
				
				if (neighbourX < 0 || neighbourX >= grid.length || neighbourY < 0 || neighbourY >= grid.length) //Bounds check.
    			{
    				continue;
    			}
				
				Node neighbour = grid[neighbourX][neighbourY];
				if (neighbour.equals(current))
				{
					continue;
				}
				
				if (neighbour.isObstacle() || closedNodes.contains(neighbour))
    			{
    				continue;
    			}
				
				/*
				float moveCost = linearMoveCost;
    			if (i % 2 == 0) //Is the neighbour diagonal?
    			{
    				moveCost = diagonalMoveCost;
    			}
    			*/
				
				if (!openNodes.contains(neighbour) || neighbour.getgCost() > 
				current.getgCost()+getNodeDistance(neighbour, current))
				{
					neighbour.setgCost(current.getgCost()+getNodeDistance(neighbour, current));
					neighbour.setfCost(neighbour.getgCost()+neighbour.gethCost());
					neighbour.setParent(current);
					if (!openNodes.contains(neighbour))
					{
						openNodes.add(neighbour);
					}
				}
			}
			
			
		}
		
		return null;
	}
	
	/**
	 * getNodeDistance(Node node1, Node node2)
	 * 
	 * Returns the Euclidean distance between two nodes.
	 * @param node1
	 * @param node2
	 * @return Distance between two nodes.
	 */
	public static float getNodeDistance(Node node1, Node node2)
    {
    	float xDistance = Math.abs(node1.getX() - node2.getX());
    	float yDistance = Math.abs(node1.getY() - node2.getY());
    	
    	float xSqr = (float) Math.pow(xDistance, 2);
    	float ySqr = (float) Math.pow(yDistance, 2);
    	return (float) Math.sqrt(xSqr+ySqr);
    }

}
