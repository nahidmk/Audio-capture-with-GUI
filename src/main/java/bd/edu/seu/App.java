package bd.edu.seu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    AudioCapture t;
    PlayMusic music = new PlayMusic();


    @Override
    public void start(Stage stage) throws Exception{
        Button start = new Button("Record Start");
        Button stop = new Button("Record Stop");
        Button play = new Button("Play Back");


        start.setDisable(false);
        stop.setDisable(true);
        play.setDisable(false);

        start.setOnAction((ActionEvent event) -> {

            start.setDisable(true);
            stop.setDisable(false);
            play.setDisable(true);
            t  = new AudioCapture();
            Thread thread = new Thread(t);
            thread.start();
        });
        stop.setOnAction((ActionEvent event)->{
            start.setDisable(false);
            stop.setDisable(true);
            play.setDisable(false);
            t.finish();
            t.cancel();
        });

        play.setOnAction((ActionEvent event)->{
            play_();
//            music.play("3_MARCH_8_36_12_2021_3219.wav");
        });



        GridPane root = new GridPane();
        root.add(start,2,0);
        root.add(stop,4,0);
        root.add(play,6,0);

        Scene scene = new Scene(root, 350, 70);

        stage.setTitle("Audio Recorder");
        stage.setScene(scene);
        stage.show();
    }


    void play_(){
        Path parentFolder = Paths.get("Recorder");

        Optional<File> mostRecentFileOrFolder =
                Arrays
                        .stream(parentFolder.toFile().listFiles())
                        .max(
                                (f1, f2) -> Long.compare(f1.lastModified(),
                                        f2.lastModified()));

        if (mostRecentFileOrFolder.isPresent()) {
            File mostRecent = mostRecentFileOrFolder.get();


            music.play(mostRecent.getPath());


        } else {
            System.out.println("folder is empty!");
        }

    }


    public static void main(String[] args) {
        launch();
    }

}