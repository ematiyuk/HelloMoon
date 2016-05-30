package com.bignerdranch.android.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
    private MediaPlayer mPlayer;

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void play(Context context) {
        /* it prevents the creation of multiple instances of MediaPlayer
           if the user clicks the Play button twice */
        stop();

        mPlayer = MediaPlayer.create(context, R.raw.one_small_step);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                /* Calling stop() when the file has finished playing releases your hold
           on the MediaPlayer instance as soon as you no longer need it */
                stop();
            }
        });

        mPlayer.start();
    }
}
