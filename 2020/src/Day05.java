public class Day05 {

	public static void main(String[] args){

		String[] boardingPasses = InputParser.readStrings("2020/Input/05.txt", "\n");
		boolean[] checkedIn = new boolean[1024];

		int resultA = 0;

		for (String boardingPass : boardingPasses){
			int boardingID = FBLRToInt(boardingPass);
			if (boardingID > resultA) {
				resultA = boardingID;
			}
			checkedIn[boardingID] = true;
		}

		System.out.println(resultA);
		for (int i = 1; i < 1023; i++){
			if (!checkedIn[i] && checkedIn[i-1] && checkedIn[i+1]){
				System.out.println(i);
			}
		}
	}

	public static int FBLRToInt(String boardingPass){
		boardingPass = boardingPass.replace('F','0').replace('B','1').replace('R','1').replace('L','0');
		return Integer.parseInt(boardingPass, 2);
	}

}
