public class Day03 {

    public static final char TREE = '#';
    public static final char EMPTY = '.';

    public static void main(String[] args) {

        char[][] map = InputParser.readCharArrays("2020/Input/03.txt", "\n");

        int right1down1 = getHitsFromSlope(map, 1, 1);
        int right3down1 = getHitsFromSlope(map, 3, 1);
        int right5down1 = getHitsFromSlope(map, 5, 1);
        int right7down1 = getHitsFromSlope(map,7, 1);
        int right1down2 = getHitsFromSlope(map, 1, 2);

        int resultA = right3down1;
        long resultB = (long) right1down1 * right3down1 * right5down1 * right7down1 * right1down2;

        System.out.println(resultA);
        System.out.println(resultB);
    }

    private static int getHitsFromSlope(char[][] map, int horizontal, int vertical) {
        int height = map.length;
        int width = map[0].length;
        int totalTreesHit = 0;
        for(int step = 0; step * vertical < height; step++){
            if (map[step * vertical][step * horizontal % width] == TREE){
                totalTreesHit++;
            }
        }
        return totalTreesHit;
    }

}
