import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Sound {
    private static ArrayList<Sound> sounds = new ArrayList<>();

    private Clip clip;
    private String file;
    //private FloatControl volume;
    private AudioInputStream audioInputStream;


    public Sound(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        {
            audioInputStream =
                    AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());


            clip = AudioSystem.getClip();


            clip.open(audioInputStream);

            //clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

        public void play(){
            clip.setMicrosecondPosition(0);
            clip.start();
        }
        public void resume(){
            clip.start();
        }
        public void stop(){
            clip.stop();
        }
        public void closeSound(){
            clip.close();
            sounds.remove(this);
        }
}
