package bd.edu.seu;

import javafx.concurrent.Task;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AudioCapture extends Task<Void> {


    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private TargetDataLine line1;
    private AudioFormat format;
    private AudInputLine audInputLine;
    private String fileName;

    public AudioCapture(AudioFormat audioFormat, AudInputLine audInputLine, String fileName1) {
        this.format = audioFormat;
        this.audInputLine = audInputLine;
        this.fileName = fileName1;
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
            line1 = AudioSystem.getTargetDataLine(format,audInputLine1.mixer.getMixerInfo());

            line1.open(format);
            line1.start();

            File wavFile = new File("Recorder/"+fileName);
            AudioInputStream ais = new AudioInputStream(line1);
            AudioSystem.write(ais, fileType, wavFile);

        }
        catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    void finish()
    {
        line1.stop();
        line1.close();
    }
}
