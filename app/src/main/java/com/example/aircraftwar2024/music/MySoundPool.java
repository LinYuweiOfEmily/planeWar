package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.aircraftwar2024.activity.GameActivity;

import java.util.HashMap;
import java.util.Map;

public class MySoundPool {
    private SoundPool soundPool;
    private Map<Integer,Integer> soundPoolMap = new HashMap<>();
    private Context context;
    private boolean isPlayMusic;
    public MySoundPool(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(20)
                .setAudioAttributes(audioAttributes)
                .build();
        this.context = context;
        GameActivity gameActivity = (GameActivity) context;
        isPlayMusic = gameActivity.isMusicOnOff();
    }
    public void addMusic(Integer id){
        soundPoolMap.put(id,soundPool.load(context,id,1));
    }
    public void playMusic(Integer id){
        if(isPlayMusic){
            soundPool.play(soundPoolMap.get(id),1,1,0,0,1);
        }
    }
    public void stopAllMusic(){
        soundPool = null;
    }
}
