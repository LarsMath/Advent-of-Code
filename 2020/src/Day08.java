import java.lang.reflect.Array;
import java.util.ArrayList;

public class Day08 {

	enum Operation{acc, jmp, nop}

	public static void main(String[] args){

		String[] code = InputParser.readStrings("2020/Input/08.txt","\n");
		Console console = new Console(code);
		console.run();
		console.reset();
		console.fixThisRun(false);
	}

	public static class Console {

		private int accumulator;
		private int codePointer;
		private int size;
		private ArrayList<LineOfCode> code;

		public Console(String[] codeInput){
			code = new ArrayList<>();
			for(String codeLine :codeInput){
				codeLine = codeLine.replaceAll("\\+","");
				String[] codeLineParts = codeLine.split(" ");
				code.add(new LineOfCode(Operation.valueOf(codeLineParts[0]), Integer.parseInt(codeLineParts[1])));
			}
			size = code.size();
		}

		public Console(Console console){
			this.codePointer = console.getCodePointer();
			this.accumulator = console.getAccumulator();
			this.code = new ArrayList<>();
			for(LineOfCode codeLine : console.getCode()){
				this.code.add(new LineOfCode(codeLine));
			}
			this.size = code.size();
			LineOfCode alteredLine = this.code.get(codePointer);
			switch (alteredLine.operation){
				case jmp:
					alteredLine.operation = Operation.nop;
					break;
				case nop:
					alteredLine.operation = Operation.jmp;
					break;
				default:
					break;
			}
		}

		public void run(){
			while(codePointer < size && !atInfiniteLoop()){
				runLine(code.get(codePointer), false);
			}
			System.out.println("Infinite Loop found! Accumulator: " + accumulator);

		}

		public void fixThisRun(boolean altered){
			while(codePointer < size && !atInfiniteLoop()){
				runLine(code.get(codePointer), !altered);
			}
			if (codePointer == size) {
				System.out.println("Program terminated succesfully, accumulator: " + accumulator);
			}
		}

		public void runLine(LineOfCode codeLine, boolean branch){
			switch(codeLine.operation){
				case acc:
					accumulator += codeLine.offset;
					codePointer++;
					break;
				case jmp:
					if(branch) {
						(new Console(this)).fixThisRun(true);
					}
					codePointer += codeLine.offset;
					break;
				case nop:
					if(branch){
						(new Console(this)).fixThisRun(true);
					}
					codePointer++;
					break;
			}
			codeLine.executed = true;
		}

		public boolean atInfiniteLoop(){
			return code.get(codePointer).executed;
		}

		public ArrayList<LineOfCode> getCode(){
			return code;
		}

		public int getAccumulator(){
			return accumulator;
		}

		public int getCodePointer(){
			return codePointer;
		}

		public void reset(){
			codePointer = 0;
			accumulator = 0;
			for(LineOfCode codeLine: code){
				codeLine.executed = false;
			}
		}

		public class LineOfCode{

			Operation operation;
			int offset;
			boolean executed;

			public LineOfCode(Operation operation, int offset){
				this.operation = operation;
				this.offset = offset;
			}

			public LineOfCode(LineOfCode codeLine){
				this(codeLine.operation, codeLine.offset);
				this.executed = codeLine.executed;
			}
		}
	}

}
