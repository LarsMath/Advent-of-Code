import java.util.Set;

public interface Point {

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	Set<Point> getNeighbours();
}
