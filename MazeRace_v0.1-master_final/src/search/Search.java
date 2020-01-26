package search;
import java.util.Map;


public interface Search
{
    Node findStrategy(State intialState, GoalTest test);
    int get_states_generated();
}