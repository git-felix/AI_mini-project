package search.frontier;
import search.*;

public interface Frontier 
{
    public void add_node( Node newNode);
    public void clear();
    public boolean is_empty();
    public Node remove_node();
    public void update_max();
    public int get_max_capacity();
}




