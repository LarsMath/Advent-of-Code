import java.util.ArrayList;

public class Day11 {

	public static final char FLOOR = '.';
	public static final char EMPTY = 'L';
	public static final char TAKEN = '#';

	public static void main(String[] args) {

		char[][] room = InputParser.readCharArrays("2020/Input/11.txt", "\n");

		ArrayList<Seat> waitingRoomA = initializeRoomA(makeSeatGrid(room));
		ArrayList<Seat> waitingRoomB = initializeRoomB(makeSeatGrid(room));

		boolean diff = true;
		while (diff) {
			for(Seat seat : waitingRoomA){
				if(seat.getTakenNeighbours() >= 4){
					seat.setNewStatus(EMPTY);
				}
				if(seat.getTakenNeighbours() == 0){
					seat.setNewStatus(TAKEN);
				}
			}
			diff = false;
			for(Seat s : waitingRoomA){
				boolean diffThisSeat = s.update();
				diff = diff || diffThisSeat;
			}
		}
		System.out.println(countTaken(waitingRoomA));

		diff = true;
		while (diff) {
			for(Seat seat : waitingRoomB){
				if(seat.getTakenNeighbours() >= 5){
					seat.setNewStatus(EMPTY);
				}
				if(seat.getTakenNeighbours() == 0){
					seat.setNewStatus(TAKEN);
				}
			}
			diff = false;
			for(Seat s : waitingRoomB){
				boolean diffThisSeat = s.update();
				diff = diff || diffThisSeat;
			}
		}
		System.out.println(countTaken(waitingRoomB));
	}

	public static Seat[][] makeSeatGrid(char[][] room){
		Seat[][] roomSeats = new Seat[room.length + 2][room[0].length + 2];
		ArrayList<Seat> roomList = new ArrayList<>();
		for (int row = 0; row < room.length; row++) {
			for (int col = 0; col < room[row].length; col++) {
				if(room[row][col] == EMPTY){
					Seat seat = new Seat();
					roomSeats[row+1][col+1] = seat;
					roomList.add(seat);
				}
			}
		}
		return roomSeats;
	}

	public static ArrayList<Seat> initializeRoomA(Seat[][] roomSeats){
		ArrayList<Seat> roomList = new ArrayList<>();
		for (int row = 0; row < roomSeats.length; row++) {
			for (int col = 0; col < roomSeats[row].length; col++) {
				Seat seat = roomSeats[row][col];
				if(seat != null){
					roomList.add(seat);
					seat.addNeighbour(roomSeats[row + 1][col + 1]);
					seat.addNeighbour(roomSeats[row + 1][col - 1]);
					seat.addNeighbour(roomSeats[row + 1][col]);
					seat.addNeighbour(roomSeats[row - 1][col + 1]);
					seat.addNeighbour(roomSeats[row - 1][col - 1]);
					seat.addNeighbour(roomSeats[row - 1][col]);
					seat.addNeighbour(roomSeats[row][col + 1]);
					seat.addNeighbour(roomSeats[row][col - 1]);
				}
			}
		}
		return roomList;
	}

	public static ArrayList<Seat> initializeRoomB(Seat[][] roomSeats){
		ArrayList<Seat> roomList = new ArrayList<>();
		for (int row = 0; row < roomSeats.length; row++) {
			for (int col = 0; col < roomSeats[row].length; col++) {
				Seat seat = roomSeats[row][col];
				if(seat != null){
					roomList.add(seat);
					seat.addNeighbour(findNeighbour(roomSeats, row, col, 1, 1));
					seat.addNeighbour(findNeighbour(roomSeats, row, col, 1, -1));
					seat.addNeighbour(findNeighbour(roomSeats, row, col, 1, 0));
					seat.addNeighbour(findNeighbour(roomSeats, row, col, -1, 1));
					seat.addNeighbour(findNeighbour(roomSeats, row, col, -1, -1));
					seat.addNeighbour(findNeighbour(roomSeats, row, col, -1, 0));
					seat.addNeighbour(findNeighbour(roomSeats, row, col, 0, 1));
					seat.addNeighbour(findNeighbour(roomSeats, row, col, 0, -1));
				}
			}
		}
		return roomList;
	}

	public static Seat findNeighbour(Seat[][] seats, int x, int y, int dx, int dy){
		for(int i = 1; 0 <= x + i * dx && x + i * dx < seats.length && 0 <= y + i * dy && y + i * dy < seats[x + i * dx].length ; i++){
			if(seats[x + i * dx][y + i * dy] != null){
				return seats[x + i * dx][y + i * dy];
			}
		}
		return null;
	}

	public static int countTaken(ArrayList<Seat> seatList){
		int count = 0;
		for(Seat s: seatList){
			if(s.status == TAKEN){
				count++;
			}
		}
		return count;
	}

	public static class Seat{

		char status;
		char newStatus;
		ArrayList<Seat> neighbours;

		public Seat(){
			neighbours = new ArrayList<>();
			status = EMPTY;
			newStatus = EMPTY;
		}

		public void addNeighbour(Seat s){
			if (s != null){
				neighbours.add(s);
			}
		}

		public int getTakenNeighbours(){
			int count = 0;
			for(Seat s : neighbours){
				if(s.status == TAKEN){count++;}
			}
			return count;
		}

		public void setNewStatus(char status){
			this.newStatus = status;
		}

		public boolean update(){
			boolean diff = status != newStatus;
			status = newStatus;
			return diff;
		}
	}
}
