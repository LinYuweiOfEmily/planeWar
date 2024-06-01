package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.activity.GameActivity;

public class MyMediaPlayer {
    private MediaPlayer mediaPlayer;
    private Integer id;
    private Context context;
    private boolean isPlayMusic;

    public MyMediaPlayer(Integer id,Context context) {
        this.id = id;
        this.context = context;
        GameActivity gameActivity = (GameActivity) context;
        this.isPlayMusic = gameActivity.isMusicOnOff();
    }

    public void playMusic(){

        if(isPlayMusic){
            mediaPlayer = MediaPlayer.create(context, id);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }
    public void pauseMusic(){
        if(isPlayMusic){
            mediaPlayer.pause();
        }
    }
    public void reStartMusic(){
        if (isPlayMusic){
            int currentPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();

        }
    }
    public void stopMusic(){
        if(isPlayMusic){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
