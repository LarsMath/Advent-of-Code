import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Point4D implements Point {

	private int w;
	private int x;
	private int y;
	private int z;

	public Point4D(int w, int x, int y, int z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Point4D point = (Point4D) o;
		return w == point.w &&
				x == point.x &&
				y == point.y &&
				z == point.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public Set<Point> getNeighbours() {
		HashSet<Point> neighbours = new HashSet<>();
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					for (int dw = -1; dw <= 1; dw++) {
						if (!(dw == 0 && dx == 0 && dy == 0 && dz == 0)) {
							neighbours.add(new Point4D(w + dw, x + dx, y + dy, z + dz));
						}
					}
				}
			}
		}
		return neighbours;
	}
}
