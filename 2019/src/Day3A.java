import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Day3A {

    // Check only crossings within Grid Radius
    public final static int GRID_RADIUS = 300;
    public final static int GRID_WIDTH = 2 * GRID_RADIUS + 1;


    /**
     * Turn a string of commands separated by commas into a grid
     * @param commandList a line of commands of the form ([U,D,L,R][int]) separated by commas
     * @return the grid with a 1 if wire was at specific location
     */
    public static int[][] stringToGrid(String commandList){
        String[] commands = commandList.split(",");
        int[][] grid = new int[GRID_WIDTH][GRID_WIDTH];
        int headX = GRID_RADIUS;
        int headY = GRID_RADIUS;
        for(int i = 0; i < commands.length; i++){
            char direction = commands[i].charAt(0);
            for(int j = 0; j < Integer.parseInt(commands[i].substring(1)); j++){
                if (direction == 'U'){
                    headY++;
                } else if (direction == 'D') {
                    headY--;
                } else if (direction == 'L'){
                    headX--;
                } else {
                    headX++;
                }
                //Only check points close enough
                if(headX < GRID_WIDTH && headX >= 0 && headY < GRID_WIDTH && headY >=0){
                    grid[headX][headY] = 1;
                }
            }
        }
        return grid;
    }

    /**
     * Returns elementwise multiplication of matrices
     * @param grid1
     * @param grid2
     * @requires Grids are square of dimensions GRID_WIDTH
     * @return grid1 * grid2
     */
    public static int[][] elementWiseMultiplication(int[][] grid1, int[][] grid2){
        int[][] grid = new int[GRID_WIDTH][GRID_WIDTH];
        for(int i = 0; i < GRID_WIDTH; i++){
            for(int j = 0; j < GRID_WIDTH; j++){
                grid[i][j] = grid1[i][j] * grid2[i][j];
            }
        }
        return grid;
    }

    public static ArrayList<Integer> findDistanceNonZeros(int[][] grid){
        ArrayList<Integer> results = new ArrayList<Integer>();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if (grid[i][j] != 0) {
                    results.add(abs(i - GRID_RADIUS) + abs(j - GRID_RADIUS));
                }
            }
        }
        return results;
    }

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("2019/03.txt");
        Scanner sc = new Scanner(file);
        String wire1CommandList = sc.nextLine();
        String wire2CommandList = sc.nextLine();

        int[][] crossingGrid = elementWiseMultiplication(stringToGrid(wire1CommandList), stringToGrid(wire2CommandList));

        ArrayList<Integer> results = findDistanceNonZeros(crossingGrid);

        System.out.println(results.toString());

    }
}
