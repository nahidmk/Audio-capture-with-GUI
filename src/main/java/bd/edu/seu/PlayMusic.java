package bd.edu.seu;

import javafx.scene.control.Alert;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class PlayMusic {
      void play(String file_name)  {

          System.out.println("Music Start..");

        try {
            File path = new File(file_name);
            if(path.exists())
            {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }


    }

}

