import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.io.StringReader;



public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SyntaxException 
	 */
	public static void main(String[] args) throws IOException, SyntaxException {
		

		PrintWriter pw = new PrintWriter("./out2");
		File file = new File ("../LS3x3");
		FileReader f = new FileReader(file);


		Parser parser = new Parser(f);
		Dsl dsl = parser.Parse();
		
		String s = dsl.toString();
		pw.println(s);
	
		Solver solver = new Solver(dsl);
		
		int i = 1;
		while(solver.getNextSolution()){
			pw.println("Solution:" + i);
			pw.println(solver.toString());
			pw.println("must: " + solver.mustConstraints.toString());
			pw.println("not: " + solver.notConstraints.toString());
			i++;
		}
		pw.close();
	  } 
	}

