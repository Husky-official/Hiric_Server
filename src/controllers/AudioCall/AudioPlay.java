//MUHIRE PATRICK
package controllers.AudioCall;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static jdk.jfr.internal.consumer.EventLog.stop;

public class AudioPlay {
    //    TO STORE CURRENT POSITION
    Long currentFrame;
    Clip clip;
    //    Current status of clip
    String status;

    AudioInputStream audioInputStream;
    static String filePath;

    private void gotoChoice(int c) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        switch (c)
        {
            case 1:
                pause();
                break;
            case 2:
                resumeAudio();
                break;
            case 3:
                restart();
                break;
            case 4:
                stop();
                break;
            case 5:
                System.out.println("Enter time (" + 0 +
                        ", " + clip.getMicrosecondLength() + ")");
                Scanner sc = new Scanner(System.in);
                long c1 = sc.nextLong();
                jump(c1);
                break;

        }

    }

    public AudioPlay() throws
            IOException,
            UnsupportedAudioFileException,
            LineUnavailableException {
        audioInputStream= AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip=AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play() {
        //start the clip
        clip.start();

        status = "play";
    }
    public void pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }
    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+
                    "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }
    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }
    public void jump(long c) throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        if (c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void main(String[] args){
    try{
        filePath="";
        AudioPlay audioPlay=new AudioPlay();
        audioPlay.play();
        Scanner sc=new Scanner(System.in);
        while (true)
        {
            System.out.println("1. pause");
            System.out.println("2. resume");
            System.out.println("3. restart");
            System.out.println("4. stop");
            System.out.println("5. Jump to specific time");
            int c = sc.nextInt();
            audioPlay.gotoChoice(c);
            if (c == 4)
                break;
        }
        sc.close();
    }catch (Exception e){
        System.out.println("Error with playing sound");
        e.printStackTrace();
    }
    }
}
