package OutputSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Debuger {
	private static boolean DebugInfoOn;
	private static String logFileName;
	private static Debuger debuger;
	static {
		DebugInfoOn = true;
		logFileName = "log.txt";
		File file =new File(logFileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        debuger = new Debuger();
	}
	private  Debuger(){	
	}
	public static Debuger getDebuger(){
		return debuger;
	}
	
	public void printDebugInto(String info){
		if(DebugInfoOn){
			System.out.println(info);
		}
		appendLog(info);
	}
	
	public void appendLog(String log){
		if(logFileName.length() == 0 || logFileName.equals("")){
			return;
		}
		FileWriter writer = null; 
		try {     
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(logFileName, true); 
            writer.write(log+"\n");     
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {
	    	try {     
	            if(writer != null){  
	                writer.close();     
	            }  
	        } catch (IOException e) {     
	            e.printStackTrace();     
	        }     
        }
	}
}
