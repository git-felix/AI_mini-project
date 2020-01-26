package search.SearchUtils;

import additional.Cell_Status;
import additional.Location;
import maze.Wyrm;
import search.Action;
import search.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


public class PathState implements State {
    public Set<Location> visitedLocations;
    public Location currentLocation = null;

    public PathState(Location startLocation) {
        this.visitedLocations = Collections.emptySet();
        this.currentLocation = startLocation;
    }
    public PathState(Set<Location> visitedLocations, Location currentLocation) {
        this.visitedLocations = visitedLocations;
        this.currentLocation = currentLocation;
    }
    public ArrayList<Step> getApplicableActions() {
        ArrayList<Location> dest = Wyrm.get_desired_cells(currentLocation, Cell_Status.PATH);
        ArrayList<Step> tempSet = new ArrayList<Step>();
        if (dest != null) {
            for (int i = 0; i < dest.size(); ++i ) {
                Step tempStep = new Step(currentLocation, dest.get(i), 1);
                tempSet.add(tempStep);
            }
        }
        return tempSet;

    }

    public State getActionResult(Action action) {
        Step step = (Step)action;
        Set<Location> newVisitedLocations = new LinkedHashSet<Location>(visitedLocations);
        newVisitedLocations.add(step.targetLocation);
        return new PathState(newVisitedLocations, step.targetLocation);
    }

    public Location get_current_Location()
    {
        return currentLocation;
    }

    public Set<Location> get_visited_Locations()
    {
        return visitedLocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PathState)) return false;

        PathState pathState = (PathState) o;

        return visitedLocations.equals(pathState.visitedLocations);
    }

    @Override
    public int hashCode() {
        return visitedLocations.hashCode();
    }

    //
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((currentLocation == null) ? 0 : currentLocation.hashCode());
//        result = prime * result + ((visitedLocations == null) ? 0 : visitedLocations.hashCode());
//        return result;
//    }
//
//
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        PathState other = (PathState) obj;
//        if (currentLocation == null) {
//            if (other.currentLocation != null)
//                return false;
//        } else if (!currentLocation.equals(other.currentLocation))
//            return false;
//        if (visitedLocations == null) {
//            if (other.visitedLocations != null)
//                return false;
//        } else if (!visitedLocations.equals(other.visitedLocations))
//            return false;
//        return true;
//    }


}
