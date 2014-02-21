package board;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {

	private String outFile = "logfile.txt";
	
	
	public BadConfigFormatException() {
		
	}
	
	public BadConfigFormatException(String message) throws FileNotFoundException {
		super(message);
		PrintWriter out = new PrintWriter(outFile);
		out.println(getMessage());
		out.close();
	}
	
	public String toString() {
		return "Error: Format of configuration files is incorrect.";
	}
	
}
