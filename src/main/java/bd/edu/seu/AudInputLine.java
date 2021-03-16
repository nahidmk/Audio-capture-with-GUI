package bd.edu.seu;

import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

class AudInputLine {
    public Mixer mixer;
    public Line.Info lineInfo;
    public String name;

    public String toString() {
        return name;
    }
}