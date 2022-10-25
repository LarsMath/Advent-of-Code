import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PointND implements Point {

	private int dimensions;
	private List<Integer> coordinates;

	public PointND(int[] coordinates) {
		this(coordinates.length, coordinates);
	}

	public PointND(int dimensions, int[] coordinates) {
		this.dimensions = dimensions;
		this.coordinates = new ArrayList<>();
		for(int i = 0; i < dimensions; i++){
			if(i < coordinates.length){
				(this.coordinates).add(coordinates[i]);
			} else {
				(this.coordinates).add(0);
			}
		}
	}

	public PointND(PointND point, int[] difference){
		List<Integer> newCoordinates = point.getCoordinates();
		this.dimensions = point.getDimensions();
		for(int axis = 0; axis < dimensions && axis < difference.length; axis++){
			newCoordinates.set(axis, newCoordinates.get(axis)+difference[axis]);
		}
		this.coordinates = newCoordinates;
	}

	public int getDimensions(){
		return dimensions;
	}

	public List<Integer> getCoordinates(){
		return coordinates;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PointND pointND = (PointND) o;
		return dimensions == pointND.dimensions &&
				coordinates.equals(pointND.coordinates);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dimensions, coordinates);
	}

	@Override
	public Set<Point> getNeighbours() {
		Set<Point> neighbours = new HashSet<>();
		neighbours.add(this);
		for (int axis = 0; axis < dimensions; axis++) {
			neighbours.addAll(getNeighboursOnAxis(neighbours, axis));
		}
		neighbours.remove(this);
		return neighbours;
	}

	public Set<Point> getNeighboursOnAxis(Set<Point> points, int axis){
		Set<Point> neighbours = new HashSet<>();
		int[] diffPlus = new int[dimensions];
		int[] diffMin = new int[dimensions];
		diffPlus[axis]++;
		diffMin[axis]--;
		for(Point point : points){
			neighbours.add(new PointND((PointND)point, diffPlus));
			neighbours.add(new PointND((PointND)point, diffMin));
		}
		return neighbours;
	}

}
