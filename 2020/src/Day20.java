import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day20 {

	public static final int PIECE_SIZE = 10;
	public static final int INNER_SIZE = PIECE_SIZE - 2;
	public static final int PUZZLE_SIZE = 12;

	public static void main(String[] args) throws FileNotFoundException {
		(new Day20()).puzzle();
	}

	public void puzzle() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("2020/Input/20.txt"));

		Set<PuzzlePiece> pieces = new HashSet<>();
		ArrayList<Integer> connectors = new ArrayList<>();
		Set<Integer> borders = new HashSet<>();
		PuzzlePiece[][] puzzle = new PuzzlePiece[PUZZLE_SIZE][PUZZLE_SIZE];

		while (sc.hasNext()) {
			int ID = Integer.parseInt(sc.nextLine().substring(5, 9));
			char[][] shape = new char[PIECE_SIZE][PIECE_SIZE];
			for (int i = 0; i < PIECE_SIZE; i++) {
				shape[i] = sc.nextLine().replace('#', '1').replace('.', '0').toCharArray();
			}
			sc.nextLine();
			PuzzlePiece piece = new PuzzlePiece(ID, shape);
			pieces.add(piece);
			connectors.addAll(piece.getPossibilities());
		}
		for (int connector : connectors) {
			int count = 0;
			for (int otherConnector : connectors) {
				if (connector == otherConnector) {
					count++;
				}
			}
			if (count == 1) {
				borders.add(connector);
			}
			//System.out.println(count);
		}

		PuzzlePiece firstPiece = null;

		long resultA = 1;
		for (PuzzlePiece piece : pieces) {
			if (piece.matches(borders) == 4) {
				resultA *= piece.ID;
				firstPiece = piece;
			}
		}
		System.out.println(resultA);

		firstPiece.firstCorner(borders);
		puzzle[0][0] = firstPiece;
		pieces.remove(firstPiece);

		for (int i = 0; i < PUZZLE_SIZE; i++) {
			for (int j = 0; j < PUZZLE_SIZE; j++) {
				PuzzlePiece toRemove = null;
				if (j != 0) {
					int toConnect = puzzle[i][j - 1].getSide(1);
					for (PuzzlePiece piece : pieces) {
						if (piece.hasConnector(toConnect)) {
							piece.orient(toConnect, 3);
							puzzle[i][j] = piece;
							toRemove = piece;
							break;
						}
					}
				} else if (i != 0) {
					int toConnect = puzzle[i - 1][j].getSide(2);
					for (PuzzlePiece piece : pieces) {
						if (piece.hasConnector(toConnect)) {
							piece.orient(toConnect, 0);
							puzzle[i][j] = piece;
							pieces.remove(piece);
							toRemove = piece;
							break;
						}
					}
				}
				if (toRemove != null) {
					pieces.remove(toRemove);
				}
			}
		}
		int ones = 0;
		int[][] sea = new int[PUZZLE_SIZE * INNER_SIZE][PUZZLE_SIZE * INNER_SIZE];
		for (int i = 0; i < PUZZLE_SIZE; i++) {
			for (int j = 0; j < PUZZLE_SIZE; j++) {
				char[][] pieceShape = puzzle[i][j].shape();
				for (int k = 0; k < INNER_SIZE; k++) {
					for (int l = 0; l < INNER_SIZE; l++) {
						sea[INNER_SIZE*i + k][INNER_SIZE*j + l] = pieceShape[k][l] - '0';
						if(pieceShape[k][l] - '0' == 1){
							ones++;
						}
					}
				}
			}
		}

		int seaMonsters = 0;
		//printSea(sea);
		seaMonsters += findSeaMonster(sea);
		sea = turn(sea);
		//printSea(sea);
		seaMonsters += findSeaMonster(sea);
		sea = turn(sea);
		//printSea(sea);
		seaMonsters += findSeaMonster(sea);
		sea = turn(sea);
		//printSea(sea);
		seaMonsters += findSeaMonster(sea);
		sea = flip(sea);
		//printSea(sea);
		seaMonsters += findSeaMonster(sea);
		sea = turn(sea);
		//printSea(sea);
		seaMonsters += findSeaMonster(sea);
		sea = turn(sea);
		//printSea(sea);
		seaMonsters += findSeaMonster(sea);
		sea = turn(sea);
		//printSea(sea);
		seaMonsters += findSeaMonster(sea);
		System.out.println(seaMonsters);
		System.out.println(ones);
		System.out.println(ones - 15 * seaMonsters);
	}

	public void printSea(int[][] sea){
		for(int[] line : sea){
			for(int point : line){
				System.out.print(point);
			}
			System.out.println();
		}
		System.out.println();
	}

	public int findSeaMonster(int[][] sea){
		int seaMonsters = 0;
		for (int i = 0; i < PUZZLE_SIZE * INNER_SIZE; i++) {
			for (int j = 0; j < PUZZLE_SIZE * INNER_SIZE; j++) {
				if (i + 2 < PUZZLE_SIZE * INNER_SIZE && j + 19 < PUZZLE_SIZE * INNER_SIZE) {
					if ((sea[i][j + 18] * sea[i + 1][j] * sea[i + 1][j + 5] * sea[i + 1][j + 6] * sea[i + 1][j + 11] * sea[i + 1][j + 12]
							* sea[i + 1][j + 17] * sea[i + 1][j + 18] * sea[i + 1][j + 19] * sea[i + 2][j + 1] * sea[i + 2][j + 4] * sea[i + 2][j + 7]
							* sea[i + 2][j + 10] * sea[i + 2][j + 13] * sea[i + 2][j + 16]) > 0) {
						seaMonsters++;
					}
				}
			}
		}
		return seaMonsters;
	}

	public int[][] turn(int[][] shape){
		int[][] sea = new int[PUZZLE_SIZE * INNER_SIZE][PUZZLE_SIZE * INNER_SIZE];
		for (int i = 0; i < PUZZLE_SIZE * INNER_SIZE; i++) {
			for (int j = 0; j < PUZZLE_SIZE * INNER_SIZE; j++) {
				sea[i][j] = shape[PUZZLE_SIZE * INNER_SIZE - 1 - j][i];
			}
		}
		return sea;
	}

	public int[][] flip(int[][] shape){
		int[][] sea = new int[PUZZLE_SIZE * INNER_SIZE][PUZZLE_SIZE * INNER_SIZE];
		for (int i = 0; i < PUZZLE_SIZE * INNER_SIZE; i++) {
			for (int j = 0; j <PUZZLE_SIZE * INNER_SIZE; j++) {
				sea[i][j] = shape[i][PUZZLE_SIZE * INNER_SIZE - 1 -j];
			}
		}
		return sea;
	}


	public class PuzzlePiece{

		int ID;

		int orientation;
		boolean reversed;


		int north;
		int east;
		int south;
		int west;
		int reversedNorth;
		int reversedEast;
		int reversedSouth;
		int reversedWest;

		ArrayList<Integer> sides, reversedSides, allSides;

		char[][] shape;

		public PuzzlePiece(int ID, char[][] shape){
			this.ID = ID;
			for(int i = 0; i < shape.length; i++){
				north += (int)Math.pow(2,i) * (shape[0][i] - '0');
				east += (int)Math.pow(2,i) * (shape[i][9] - '0');
				south += (int)Math.pow(2,i) * (shape[9][9-i] - '0');
				west += (int)Math.pow(2,i) * (shape[9-i][0] - '0');
				reversedNorth += (int)Math.pow(2,i) * (shape[0][9-i] - '0');
				reversedEast += (int)Math.pow(2,i) * (shape[9-i][9] - '0');
				reversedSouth += (int)Math.pow(2,i) * (shape[9][i] - '0');
				reversedWest += (int)Math.pow(2,i) * (shape[i][0] - '0');
			}
			reversed = false;
			sides = new ArrayList<>();
			reversedSides = new ArrayList<>();
			allSides = new ArrayList<>();
			sides.add(north);
			sides.add(east);
			sides.add(south);
			sides.add(west);
			reversedSides.add(reversedNorth);
			reversedSides.add(reversedWest);
			reversedSides.add(reversedSouth);
			reversedSides.add(reversedEast);
			allSides.addAll(sides);
			allSides.addAll(reversedSides);
			this.shape = shape;
		}

		public char[][] shape(){
			char[][] insides = new char[8][8];
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if(!reversed){
						switch(orientation){
							case(0):
								insides[i][j] = shape[i+1][j+1];
								break;
							case(1):
								insides[i][j] = shape[j+1][8-i];
								break;
							case(2):
								insides[i][j] = shape[8-i][8-j];
								break;
							case(3):
								insides[i][j] = shape[8-j][i+1];
								break;
						}
					} else {
						switch(orientation) {
							case (0):
								insides[i][j] = shape[i+1][8-j];
								break;
							case (1):
								insides[i][j] = shape[j+1][i+1];
								break;
							case (2):
								insides[i][j] = shape[8-i][j+1];
								break;
							case (3):
								insides[i][j] = shape[8-j][8-i];
								break;
						}
					}
				}
			}
			return insides;
		}

		public int getSide(int side){
			if(reversed){
				return reversedSides.get((orientation + side) % 4);
			} else {
				return sides.get((orientation + side) % 4);
			}
		}

		public void orient(int connector, int side){
			if(reversedSides.contains(connector)){
				reversed = false;
				orientation = ( 2* (reversedSides.indexOf(connector) % 2) + 4 + reversedSides.indexOf(connector) - side) % 4;
			} else {
				reversed = true;
				orientation = ( 2* (sides.indexOf(connector) % 2) + 4 + sides.indexOf(connector) - side) % 4;
			}
		}

		public void firstCorner(Set<Integer> toMatch) {
			for (int i = 0; i < 4; i++){
				if (toMatch.contains(sides.get(i)) && toMatch.contains(sides.get((i + 1) % 4))) {
					orientation = i + 1 % 4;
				}
			}
		}

		public int matches(Set<Integer> toMatch) {
			int count = 0;
			for (int ownConnector : allSides) {
				if (toMatch.contains(ownConnector)) {
					count++;
				}
			}
			return count;
		}

		public ArrayList<Integer> getPossibilities(){
			return allSides;
		}

		public boolean hasConnector(int connector){
			return allSides.contains(connector);
		}
	}
}
