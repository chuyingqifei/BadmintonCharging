package InputSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.*;

public class BadmintonOrderInput {
	public BadmintonOrderInput(){
		
	}

	public ArrayList<String> loadOrderFromFile(String fileName) throws IOException{
		ArrayList<String> input = new ArrayList<String>();		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			while(br.ready()){
				input.add(br.readLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br != null)
				br.close();
		}
		return input;
	}
}
