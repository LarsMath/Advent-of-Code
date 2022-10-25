import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Day24 {

	public static void main(String[] args){
		HashSet<Point> actives = new HashSet<>();

		String[] input = InputParser.readStrings("2020/Input/24.txt", "\n");

		for(String route : input){
			int w = 0;
			int nw = 0;
			char[] commands = route.toCharArray();
			for(int i = 0; i < commands.length; i++){
				switch(commands[i]) {
					case 'n':
						switch (commands[i + 1]) {
							case 'e':
								w--;
								nw++;
								break;
							case 'w':
								nw++;
								break;
						}
						i++;
						break;
					case 's':
						switch (commands[i + 1]) {
							case 'e':
								nw--;
								break;
							case 'w':
								nw--;
								w++;
								break;
						}
						i++;
						break;
					case 'w':
						w++;
						break;
					case 'e':
						w--;
						break;
				}
			}
			Point2Dhex destination = new Point2Dhex(w, nw);
			if(actives.contains(destination)){
				actives.remove(destination);
			} else{
				actives.add(destination);
			}
		}
		System.out.println(actives.size());

		for(int cycle = 0; cycle < 100; cycle++){
			System.out.println(actives.size());
			actives = newActivePoints(actives);
		}

		System.out.println(actives.size());
	}

	public static HashSet<Point> newActivePoints(HashSet<Point> actives){
		Set<Point> relevantPoints = Day17.relevantPoints(actives);
		HashSet<Point> newActives = new HashSet<>();
		for(Point point : relevantPoints){
			int neighbours = Day17.getActiveNeighbourCount(point, actives);
			if(actives.contains(point)){
				if(neighbours == 1 || neighbours == 2){
					newActives.add(point);
				}
			} else {
				if(neighbours == 2){
					newActives.add(point);
				}
			}
		}
		return newActives;
	}

}
