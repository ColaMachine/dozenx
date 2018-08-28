package dozenx.keyrobot.conf;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Configuration {
	private static String xPath="D:/baiduyundownload/anjianjinlin/conf.txt";
	private static ArrayList<String> Dos;
	private static boolean terminated=true;
	public static void init(){
		Dos=new ArrayList<String>();
		
		URL urlToSource=Configuration.class.getProtectionDomain().getCodeSource().getLocation();
	
	String path = urlToSource.toString();
	path= path.replace("file:/", "");
	if(path.indexOf(":") != 1){
		path = File.separator + path;
	}
	Path classPath= Paths.get(path);
	
	System.out.println(classPath.resolve("anjianjinling/conf.txt"));
		try{
			String input;
			BufferedReader in =new BufferedReader(new FileReader(classPath.resolve("anjianjinling/conf.txt").toString()));
			while((input=in.readLine())!=null){
				Dos.add(input);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static String getXPath() {
		return xPath;
	}
	public static void setXPath(String path) {
		xPath = path;
	}
	public static ArrayList<String> getDos() {
		return Dos;
	}
	public static void setDos(ArrayList<String> dos) {
		Dos = dos;
	}
	public static boolean isTerminated() {
		return terminated;
	}
	public static void setTerminated(boolean terminated) {
		Configuration.terminated = terminated;
	}
}
