public class Day25 {

	public static void main(String[] args){

		long group = 20201227;

		long keyA =15113849;
		long keyB = 4206373;

		long ID = 7;
		long value =7;
		long found = 0;

		for(int i = 1; i < group;i++){
			if(value == keyA || value == keyB){
				System.out.println(value);

				found = i;
				break;
			}
			value = (value * ID) % group;
		}

		System.out.println(found);

	}
}
