package search;

import search.Action;
import search.State;

public class Node
{
	public final Node parent;
	public final Action action;
	public final State state;
	public int depth;
	public int value;
	public int path_cost;
	
	public Node(Node parent, Action action, State state, int dep, int val)
	{
		this.depth = dep;
		this.parent = parent;
		this.action = action;
		this.state = state;
		this.value = val;
		this.path_cost = get_cost_path();
	}
	public int get_cost_path()
	{
		if(this.parent == null)
			return 0;
		else
			return (this.action.cost() + this.parent.path_cost);
	}
}
