import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Point2Dhex implements Point {

	private int w;
	private int nw;

	public Point2Dhex(int w, int nw){
		this.w = w;
		this.nw = nw;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Point2Dhex that = (Point2Dhex) o;
		return w == that.w && nw == that.nw;
	}

	@Override
	public int hashCode() {
		return Objects.hash(w, nw);
	}

	@Override
	public Set<Point> getNeighbours() {
		HashSet<Point> neighbours = new HashSet<>();
		neighbours.add(new Point2Dhex(w + 1, nw));
		neighbours.add(new Point2Dhex(w - 1, nw));
		neighbours.add(new Point2Dhex(w, nw + 1));
		neighbours.add(new Point2Dhex(w - 1, nw + 1));
		neighbours.add(new Point2Dhex(w, nw - 1));
		neighbours.add(new Point2Dhex(w + 1, nw - 1));
		return neighbours;
	}
}
