public class Day12 {

	public static void main(String[] args){

		String[] input = InputParser.readStrings("2020/Input/12.txt", "\n");

		int vertical = 0;
		int horizontal = 0;
		int direction = 0;

		for(String line : input){
			char command = line.charAt(0);
			int value = Integer.parseInt(line.substring(1));
			switch (command) {
				case 'N':
					vertical += value;
					break;
				case 'E':
					horizontal += value;
					break;
				case 'S':
					vertical -= value;
					break;
				case 'W':
					horizontal -= value;
					break;
				case 'L':
					direction = (direction + value) % 360;
					break;
				case 'R':
					direction = (360 + direction - value) % 360;
					break;
				case 'F':
					switch(direction){
						case 90:
							vertical += value;
							break;
						case 0:
							horizontal += value;
							break;
						case 270:
							vertical -= value;
							break;
						case 180:
							horizontal -= value;
							break;
					}
					break;
			}
		}
		System.out.println(Math.abs(horizontal)+ Math.abs(vertical));

		vertical = 0;
		horizontal = 0;
		int waypointVertical = 1;
		int waypointHorizontal = 10;

		for(String line : input){
			char command = line.charAt(0);
			int value = Integer.parseInt(line.substring(1));
			int v = waypointVertical;
			int h = waypointHorizontal;
			switch (command) {
				case 'N':
					waypointVertical += value;
					break;
				case 'E':
					waypointHorizontal += value;
					break;
				case 'S':
					waypointVertical -= value;
					break;
				case 'W':
					waypointHorizontal -= value;
					break;
				case 'L':
					switch (value){
						case 90:
							waypointVertical = h;
							waypointHorizontal = - v;
							break;
						case 180:
							waypointVertical = - v;
							waypointHorizontal = - h;
							break;
						case 270:
							waypointVertical = - h;
							waypointHorizontal = v;
							break;
					}
					break;
				case 'R':
					switch (value){
						case 90:
							waypointVertical = - h;
							waypointHorizontal = v;
							break;
						case 180:
							waypointVertical = - v;
							waypointHorizontal = - h;
							break;
						case 270:
							waypointVertical = h;
							waypointHorizontal = - v;
							break;
					}
					break;
				case 'F':
					vertical += waypointVertical * value;
					horizontal += waypointHorizontal * value;
			}
		}
		System.out.println(Math.abs(horizontal)+ Math.abs(vertical));
	}

}
