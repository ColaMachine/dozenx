package dozenx.keyrobot.sound;

import java.applet.AudioClip;
import java.net.MalformedURLException;  
import java.net.URL;  
import javax.swing.JApplet;  
  
public class PlayMusic {  
    public static AudioClip loadSound(String filename) {  
        URL url = null;  
        try {  
            url = new URL("file:" + filename);  
        }   
        catch (MalformedURLException e) {;}  
        return JApplet.newAudioClip(url);  
    }  
    public void play() {  
        AudioClip christmas = loadSound("e://alert.wav");  
        christmas.play();  
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    
    public static void main(String args[]){
    	PlayMusic christmas = new PlayMusic();  
         christmas.play();  
      
         
    }
}  
