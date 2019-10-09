
public class Node 
{
	
	private boolean isStart; //Is this the starting node?
	private boolean isGoal; //Is this the goal node?
	private boolean isObstacle; //Is this an obstacle?
	private float gCost; //How far away this node is from the starting node.
	private float hCost; //How far away this node is from the goal node.
	private float fCost; //g+h cost
	private int[] position = new int[2]; //Position of the node.
	private Node parent; //Parent of this node.

	public boolean isStart() 
	{
		return isStart;
	}

	public void setStart(boolean isStart) 
	{
		if (isGoal)
		{
			throw new NodeException("Goal node cannot be start node.");
		}
		this.isStart = isStart;
	}
	
	public boolean isGoal()
	{
		return isGoal;
	}
	
	public void setGoal(boolean isGoal)
	{
		if (isStart)
		{
			throw new NodeException("Start node cannot be goal node.");
		}
		this.isGoal = isGoal;
	}

	public boolean isObstacle() 
	{
		return isObstacle;
	}

	public void setObstacle(boolean isObstacle) 
	{
		if (isStart && isObstacle)
		{
			throw new NodeException("A starting node cannot be an obstacle.");
		}
		this.isObstacle = isObstacle;
	}

	public float getgCost() 
	{
		return gCost;
	}

	public void setgCost(float gCost) 
	{
		this.gCost = gCost;
	}

	public float gethCost() 
	{
		return hCost;
	}

	public void sethCost(float hCost) 
	{
		this.hCost = hCost;
	}

	public float getfCost() 
	{
		return fCost;
	}
	
	public void setfCost(float fCost)
	{
		this.fCost = fCost;
	}

	public int[] getPosition() 
	{
		return position;
	}
	
	public int getX()
	{
		return position[0];
	}
	
	public int getY()
	{
		return position[1];
	}

	public void setPosition(int[] position) 
	{
		this.position = position;
	}
	
	public void setX(int x)
	{
		position[0] = x;
	}
	
	public void setY(int y)
	{
		position[1] = y;
	}
	
	public Node getParent() 
	{
		return parent;
	}

	public void setParent(Node parent) 
	{
		this.parent = parent;
	}

	public boolean equals(Node otherNode)
	{
		if (position[0] == otherNode.getX() && position[1] == otherNode.getY())
		{
			return true;
		}
		
		return false;
	}
}

