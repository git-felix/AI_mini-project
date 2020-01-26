package search.SearchUtils;

import additional.Location;
import search.GoalTest;
import search.State;

import java.awt.*;


public class PathGoalTest implements GoalTest {
    public Location targetLocation;

    public PathGoalTest(Location targetLocation) {
        this.targetLocation = targetLocation;
    }
    public boolean isGoal(State state) {
        PathState pathState = (PathState)state;
        if(targetLocation.equals(pathState.currentLocation))
            System.out.println("succ");
        return targetLocation.equals(pathState.currentLocation);
    }
}