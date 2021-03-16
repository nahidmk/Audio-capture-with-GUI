package bd.edu.seu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    AudioCapture t;
    AudioCapture1 t1;
    PlayMusic music = new PlayMusic();
    List<AudInputLine> audInputLines = new ArrayList<>();
    Mixer.Info[] mixerInfo;
    Random random = new Random();
    String fileName1;
    String fileName2;

    @Override
    public void start(Stage stage) throws Exception{
        this.RefreshInputs();
        fileName1 = getFileName()+random.nextInt(10)+".wav";
        fileName2 = getFileName()+random.nextInt(1000)+".wav";
        Button start = new Button("Record Start");
        Button stop = new Button("Record Stop");
        Button play = new Button("Play Back");

        start.setDisable(false);
        stop.setDisable(true);
        play.setDisable(true);

        start.setOnAction((ActionEvent event) -> {

            start.setDisable(true);
            stop.setDisable(false);
            play.setDisable(true);
            t  = new AudioCapture(getAudioFormat(),audInputLines.get(getLine1()),fileName1);
            t1 = new AudioCapture1(getAudioFormat(),audInputLines.get(getLine2()),fileName2);
            Thread thread = new Thread(t);
            Thread thread1 = new Thread(t1);
            thread1.start();
            thread.start();
        });
        stop.setOnAction((ActionEvent event)->{
            start.setDisable(false);
            stop.setDisable(true);
            play.setDisable(false);
            t.finish();
            t1.finish();
            t.cancel();
            t1.cancel();
        });

        play.setOnAction((ActionEvent event)->{
            play_();
        });

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.add(start, 0, 1, 1, 1);
        root.add(stop, 1, 1, 1, 1);
        root.add(play, 2, 1, 1, 1);

        Scene scene = new Scene(root, 300, 50);

        stage.setTitle("Audio Recorder");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private int getLine1() {
        if (audInputLines.size()==2){
            return 0;
        }else {
            int value = 0;
            for (int i = 0; i < audInputLines.size(); i++) {
                if (audInputLines.get(i).name.toLowerCase().contains("microphone")) {
                    System.out.println("->" + i);
                    value = i;
                    break;
                }
            }
            return value;
        }
    }

    private int getLine2() {

        if(audInputLines.size()==2){
            return 1;
        }else {
            int value = 0;
            for (int i = 0; i < audInputLines.size(); i++) {
                if (audInputLines.get(i).name.toLowerCase().contains("stereo")) {
                    System.out.println("i->" + i);
                    value = i;
                    break;
                }
            }
            return value;
        }
    }

    public void RefreshInputs() {
        audInputLines.clear();
        mixerInfo = AudioSystem.getMixerInfo();
        Line.Info[] targlines;
        for (Mixer.Info m : mixerInfo) {
            targlines = AudioSystem.getMixer(m).getTargetLineInfo();
            for (Line.Info ln : targlines) {
                AudInputLine tail = new AudInputLine();
                tail.lineInfo = ln;
                tail.mixer = AudioSystem.getMixer(m);
                tail.name = tail.mixer.getMixerInfo().toString();
                audInputLines.add(tail);
            }
        }

        for (int i = 0; i < audInputLines.size(); i++) {
            try {
                if (((DataLine.Info) audInputLines.get(i).lineInfo).getFormats().length < 1) {
                    audInputLines.remove(i);
                    i -= 1;
                }
            } catch (Exception exx) {
                audInputLines.remove(i);
                i -= 1;
            }
        }

    }

    void play_(){
        System.out.println("file1->"+fileName1);
        System.out.println("file2->"+fileName2);
        music.play("Recorder/"+fileName1,"Recorder/"+fileName2);

    }


    AudioFormat getAudioFormat()
    {
        float sampleRate = 48000;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }


    public String getFileName(){
        LocalDateTime localDateTime = LocalDateTime.now();

        String file_name =localDateTime.getDayOfMonth() + "_" + localDateTime.getMonth() + "_" + localDateTime.getHour() + "_" +
                localDateTime.getMinute() + "_" + localDateTime.getSecond() + "_" + localDateTime.getYear() + "_" ;
        return file_name;
    }


    public static void main(String[] args) {

        launch();
    }

}