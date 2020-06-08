//Sound.java
//Jim Ji and Edward Yang
//This class file allows us to load in .wav files from our game folder and manipulate them. (Play, pause, adjust volume)


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Sound {
    private static ArrayList<Sound>sounds = new ArrayList<>(); //arraylist to keep track of all sounds

    private Clip clip; //clip object of the respective sound file
    private String file; //the file name
    private AudioInputStream audioInputStream;
    FloatControl volume;

    //constructor
    public Sound(String file, boolean loop, int volumeLevel) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        {
            audioInputStream =
                    AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());

            clip = AudioSystem.getClip(); //getting the clip

            clip.open(audioInputStream); //opening the clip
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); //calculating the volume level
            setVolume(volumeLevel);
            sounds.add(this);//adding the sound to the arraylist
            if (loop){//if we want the sound to loop
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
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
            //sounds.remove(this);
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
