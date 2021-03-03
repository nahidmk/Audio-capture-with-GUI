package bd.edu.seu;

import javafx.concurrent.Task;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

public class AudioCapture extends Task<Void> {


    LocalDateTime localDateTime = LocalDateTime.now();
    Random random = new Random();
    String file_name =localDateTime.getDayOfMonth() + "_" + localDateTime.getMonth() + "_" + localDateTime.getHour() + "_" +
            localDateTime.getMinute() + "_" + localDateTime.getSecond() + "_" + localDateTime.getYear() + "_" + random.nextInt(10000) + ".wav";

    File wavFile = new File("Recorder/"+file_name);


    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    TargetDataLine line;

    @Override
    protected Void call() throws Exception
    {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");


            AudioSystem.write(ais, fileType, wavFile);

        }
        catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    AudioFormat getAudioFormat()
    {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }


    void finish()
    {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
}
