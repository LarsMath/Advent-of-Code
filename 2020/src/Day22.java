import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class Day22 {

	public static void main(String[] args) throws FileNotFoundException {

		Deque<Integer> deckA = new ArrayDeque<>();
		Deque<Integer> deckB = new ArrayDeque<>();

		Scanner sc = new Scanner(new File("2020/Input/22.txt"));
		sc.nextLine();

		boolean playerB = false;
		while(sc.hasNext()){
			String line = sc.nextLine();
			if(line.isEmpty()){
				playerB = true;
				sc.nextLine();
				continue;
			}
			if(playerB){
				deckB.add(Integer.parseInt(line));
			} else {
				deckA.add(Integer.parseInt(line));
			}
		}

		System.out.println((new Day22()).recursiveGame(deckA,deckB));

	}

	public int recursiveGame(Deque<Integer> startDeckA, Deque<Integer> startDeckB){
		Deque<Integer> deckA = new ArrayDeque<>(startDeckA);
		Deque<Integer> deckB = new ArrayDeque<>(startDeckB);
		int breakInfinite = 0;
		while(!deckA.isEmpty() && !deckB.isEmpty()){
			int A = deckA.poll();
			int B = deckB.poll();
			if(A <= deckA.size() && B <= deckB.size()){
				Deque<Integer> subDeckA = new ArrayDeque<>((new ArrayList<>(deckA)).subList(0,A));
				Deque<Integer> subDeckB = new ArrayDeque<>((new ArrayList<>(deckB)).subList(0,B));
				if(recursiveGame(subDeckA,subDeckB) > 0){
					deckA.add(A);
					deckA.add(B);
				} else {
					deckB.add(B);
					deckB.add(A);
				}
			} else if(A>B){
				deckA.add(A);
				deckA.add(B);
			} else {
				deckB.add(B);
				deckB.add(A);
			}
			breakInfinite++;
			if(breakInfinite>1000){
				return 1;
			}
		}
		return calculateResult(deckA,deckB);
	}

	public int calculateResult(Deque<Integer> deckA, Deque<Integer> deckB){
		int result = 0;
		while(!deckA.isEmpty() || !deckB.isEmpty()){
			if(!deckA.isEmpty()){
				result += deckA.size() * deckA.poll();
			}
			if(!deckB.isEmpty()){
				result -= deckB.size() * deckB.poll();
			}
		}
		return result;
	}
}
