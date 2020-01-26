package search.SearchUtils;
import additional.Location;
import search.Action;

public class Step implements Action {
    public Location sourceLocation;
    public Location targetLocation;
    public int length;

    public Step(Location sourceCity, Location targetCity, int length) {
        this.sourceLocation = sourceCity;
        this.targetLocation = targetCity;
        this.length = 1; // length;
    }
    public int cost() {
        return length;
    }
}