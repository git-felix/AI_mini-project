package search;


import additional.Cell_Status;
import additional.Location;
import search.SearchUtils.PathState;

import java.util.Set;

public interface GoalTest
{
	public boolean isGoal(State state);
}


class PathGoalTest implements GoalTest {
	protected final Set<Location> locationsToVisit;
	protected final Location targetLocation;

	public PathGoalTest(Set<Location> citiesToVisit, Location targetLocation) {
		this.locationsToVisit = citiesToVisit;
		this.targetLocation = targetLocation;
	}
	public boolean isGoal(State state) {
		PathState pathState = (PathState)state;
		return locationsToVisit.equals(pathState.visitedLocations) && targetLocation.equals(pathState.currentLocation);
	}
}