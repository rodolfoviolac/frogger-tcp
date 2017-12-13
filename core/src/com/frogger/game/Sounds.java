package com.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sounds {

    private static final Music frogJumpMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/jump.wav"));
    private static final Music frogDieMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/outofbounds.wav"));
    private static final Music frogTargetMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/target.wav"));
    private static final Music menuSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/splash2.wav"));
    private static final Music menuLoop = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainloop.wav"));

    public static void frogJump(){
        frogJumpMusic.stop();
        frogJumpMusic.play();
    }

    public static void frogDie(){
        frogDieMusic.stop();
        frogDieMusic.play();
    }

    public static void nextLevel(){
        frogTargetMusic.stop();
        frogTargetMusic.play();
    }

    public static void menuSound(){
        menuSound.play();
    }

    public static void menuLoopStart(){
        menuLoop.play();
        menuLoop.setLooping(true);
    }

    public static void menuLoopStop(){
        menuLoop.stop();
    }

    public static void dispose(){
        frogJumpMusic.dispose();
        frogDieMusic.dispose();
        frogTargetMusic.dispose();
    }
}
