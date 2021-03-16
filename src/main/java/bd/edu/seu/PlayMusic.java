package bd.edu.seu;

import javafx.scene.control.Alert;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayMusic {

      void play(String file_name1, String file_name2)  {

          System.out.println(file_name1);
          System.out.println(file_name2);

          Callable<Void> callable1 = new Callable<Void>()
          {
              @Override
              public Void call() throws Exception
              {
                  play_1(file_name1);
                  return null;
              }
          };

          Callable<Void> callable2 = new Callable<Void>()
          {
              @Override
              public Void call() throws Exception
              {
                  play_1(file_name2);
                  return null;
              }
          };

          List<Callable<Void>> taskList = new ArrayList<Callable<Void>>();
          taskList.add(callable1);
          taskList.add(callable2);
          ExecutorService executor = Executors.newFixedThreadPool(2);

          try
          {
              executor.invokeAll(taskList);
          }
          catch (InterruptedException ie)
          {
              ie.printStackTrace();
          }


    }

    void play_1(String filename){

        try {
            File path = new File(filename);
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

