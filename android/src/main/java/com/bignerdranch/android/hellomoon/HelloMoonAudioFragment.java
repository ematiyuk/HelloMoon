package com.bignerdranch.android.hellomoon;

import android.content.Intent;
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
    private Button mGoToVideoButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon_audio, container, false);

        mGoToVideoButton = (Button) view.findViewById(R.id.hellomoon_gotoVideoButton);
        mGoToVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HelloMoonVideoActivity.class));
            }
        });

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
            }
        });

        if (mPlayer == null) {
            mPlayButton.setText(R.string.hellomoon_play);
        } else if (mPlayer.isPlaying()) {
            mPlayButton.setText(R.string.hellomoon_pause);
        } else {
            mPlayButton.setText(R.string.hellomoon_resume);
        }

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
                }
            });
        }

        mPlayer.start();
    }

    private void stopAudioPlayback() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;

            mPlayButton.setText(R.string.hellomoon_play);
        }
    }
}
