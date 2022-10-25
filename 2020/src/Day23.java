import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day23 {

	public static final int CHAIN_LENGTH = 1000000;

	public static void main(String[] args){

		ArrayList<Integer> chain = new ArrayList<>(Arrays.asList(1,5,7,6,2,3,9,8,4));
		//ArrayList<Integer> chain = new ArrayList<>(Arrays.asList(3,8,9,1,2,5,4,6,7));

		Map<Integer, Integer> directNeighbours = new HashMap<>();

		for(int init = 1; init <= chain.size(); init++){
			if(chain.indexOf(init) == chain.size() - 1){
				directNeighbours.put(init, 10);
				//directNeighbours.put(init, chain.get(0));
			} else{
				directNeighbours.put(init, chain.get(chain.indexOf(init) + 1));
			}
		}
		for(int init = 10; init < CHAIN_LENGTH; init++){
			directNeighbours.put(init, init + 1);
		}
		directNeighbours.put(CHAIN_LENGTH, chain.get(0));

		int nextCup = chain.get(0);
		for(int move = 0; move < 10000000; move++){
			int neighbour1 = directNeighbours.get(nextCup);
			int neighbour2 = directNeighbours.get(neighbour1);
			int neighbour3 = directNeighbours.get(neighbour2);

			int destination = nextCup - 1;
			if(destination == 0){
				destination += CHAIN_LENGTH;
			}
			while(destination == neighbour1 || destination == neighbour2 || destination == neighbour3){
				destination--;
				if(destination == 0){
					destination += CHAIN_LENGTH;
				}
			}
			directNeighbours.put(nextCup, directNeighbours.get(neighbour3));
			directNeighbours.put(neighbour3, directNeighbours.get(destination));
			directNeighbours.put(destination, neighbour1);
			nextCup = directNeighbours.get(nextCup);
		}

		System.out.println(directNeighbours.get(1) + " " + directNeighbours.get(directNeighbours.get(1)));
		System.out.println((long)directNeighbours.get(1) * (long)directNeighbours.get(directNeighbours.get(1)));
	}

}
