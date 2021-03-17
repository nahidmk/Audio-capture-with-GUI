package bd.edu.seu;

import javafx.concurrent.Task;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AudioCapture1 extends Task<Void> {


    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private TargetDataLine line2;
    private AudioFormat format;
    private AudInputLine audInputLine;
    private String fileName;
    public AudioCapture1(AudioFormat audioFormat, AudInputLine audInputLine, String fileName2) {
        this.format = audioFormat;
        this.audInputLine = audInputLine;
        this.fileName = fileName2;
    }

    @Override
    protected Void call() throws Exception
    {
        try {
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            AudInputLine audInputLine1 = audInputLine;
            line2 = AudioSystem.getTargetDataLine(format,audInputLine1.mixer.getMixerInfo());

            line2.open(format);
            line2.start();
            File wavFile = new File("Recorder/"+fileName);
            System.out.println("Start capturing from Mixer...");
            AudioInputStream ais = new AudioInputStream(line2);
            System.out.println("Start recording from Mixer...");

            AudioSystem.write(ais, fileType, wavFile);

        }
        catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }



    void finish()
    {
        line2.stop();
        line2.close();
        System.out.println("Finish recording from Mixer...");
    }
}
