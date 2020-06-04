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
    //FloatControl gainControl;
    FloatControl gainControl;


    public Sound(String file, boolean loop) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        {
            audioInputStream =
                    AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());


            clip = AudioSystem.getClip();
            //FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            clip.open(audioInputStream);
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
        public void setVol(double v){
            float dB = (float) (Math.log(v) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }



        public boolean checkPlaying(){
            return clip.isActive();
        }
}
