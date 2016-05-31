package com.bignerdranch.android.hellomoon;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonAudioFragment extends Fragment {
    private MediaPlayer mPlayer;
    private Button mPlayButton;
    private Button mStopButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon_audio, container, false);

        mPlayButton = (Button) view.findViewById(R.id.hellomoon_playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mPlayer != null) && mPlayer.isPlaying()) {
                    mPlayer.pause();
                    mPlayButton.setText(R.string.hellomoon_resume);
                } else {
                    playAudio();
                    mPlayButton.setText(R.string.hellomoon_pause);
                }
            }
        });
        mStopButton = (Button) view.findViewById(R.id.hellomoon_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAudioPlayback();
                mPlayButton.setText(R.string.hellomoon_play);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /* it prevents the MediaPlayer from continuing playback after
           the fragment has been destroyed */
        stopAudioPlayback();
    }

    private void playAudio() {
        if (mPlayer == null) {

            mPlayer = MediaPlayer.create(getActivity(), R.raw.one_small_step);

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                /* Calling stopAudioPlayback() when the file has finished playing releases your hold
           on the MediaPlayer instance as soon as you no longer need it */
                    stopAudioPlayback();

                    mPlayButton.setText(R.string.hellomoon_play);
                }
            });
        }

        mPlayer.start();
    }

    private void stopAudioPlayback() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
