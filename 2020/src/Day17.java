import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day17 {

	public static void main(String[] args){

		long time = System.nanoTime();
		HashSet<Point> actives = new HashSet<>();

		char[][] initialMap = InputParser.readCharArrays("2020/Input/17Test.txt","\n");

		for(int x = 0; x < initialMap.length; x++){
			for(int y = 0; y < initialMap[x].length; y++){
				if(initialMap[x][y] == '#'){
					actives.add(new PointND(4, new int[]{x, y}));
				}
			}
		}

		for(int cycle = 0; cycle < 6; cycle++){
			System.out.println(actives.size());
			actives = newActivePoints(actives);
		}

		System.out.println(actives.size());
		System.out.println((System.nanoTime() - time) / 1000000 + " ms");

	}

	public static HashSet<Point> newActivePoints(HashSet<Point> actives){
		Set<Point> relevantPoints = relevantPoints(actives);
		HashSet<Point> newActives = new HashSet<>();
		for(Point point : relevantPoints){
			int neighbours = getActiveNeighbourCount(point, actives);
			if(actives.contains(point)){
				if(neighbours == 2 || neighbours == 3){
					newActives.add(point);
				}
			} else {
				if(neighbours == 3){
					newActives.add(point);
				}
			}
		}
		return newActives;
	}

	public static int getActiveNeighbourCount(Point point, HashSet<Point> actives){
		int count = 0;
		for(Point neighbour : point.getNeighbours()){
			if(actives.contains(neighbour)){
				count++;
			}
		}
		return count;
		//point.getNeighbours().stream().filter(p -> actives.contains(p)).count();
	}


	public static Set<Point> relevantPoints(HashSet<Point> actives){
		HashSet<Point> relevantPoints = new HashSet<>();
		for(Point point : actives){
			relevantPoints.addAll(point.getNeighbours());
			relevantPoints.add(point);
		}
		return relevantPoints;
	}
}

