import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.*;
public class Sound {
    private static ArrayList<Sound> sounds = new ArrayList<>();

    private Clip clip;
    private String file;
    //private FloatControl volume;
    private AudioInputStream audioInputStream;
    FloatControl volume;
    //FloatControl gainControl;
    FloatControl gainControl;


    public Sound(String file, boolean loop, int volumeLevel) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        {
            audioInputStream =
                    AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());


            clip = AudioSystem.getClip();
            //FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            clip.open(audioInputStream);
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(volumeLevel);
            if (loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            //clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

        public void play(){
            //gainControl.setValue(-10.0f);
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
        // Setters
        public void setVolume(int volumeLevel){
            float range = volume.getMaximum() - volume.getMinimum(); // Getting the range of volume provided by the system
            float gain = (float)(range * (volumeLevel/100.0)) + volume.getMinimum(); // Calculating the gain
            volume.setValue(gain);
        }

        public boolean checkPlaying(){
            return clip.isActive();
        }
}
