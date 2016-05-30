package com.bignerdranch.android.hellomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {
    private AudioPlayer mPlayer = new AudioPlayer();
    private Button mPlayButton;
    private Button mStopButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);

        mPlayButton = (Button) view.findViewById(R.id.hellomoon_playButton);
        mStopButton = (Button) view.findViewById(R.id.hellomoon_stopButton);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /* it prevents the MediaPlayer from continuing playback after
           the fragment has been destroyed */
        mPlayer.stop();
    }
}
