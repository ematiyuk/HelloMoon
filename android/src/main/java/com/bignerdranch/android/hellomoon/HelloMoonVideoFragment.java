package com.bignerdranch.android.hellomoon;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class HelloMoonVideoFragment extends Fragment {
    private static final String KEY_POSITION = "position";

    private VideoView mVideoView;
    private int position = 0;
    private MediaController mMediaControls;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon_video, container, false);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(KEY_POSITION, 0);
        }

        // set the media controllers buttons
        if (mMediaControls == null) {
            mMediaControls = new MediaController(getActivity());
            mMediaControls.setAnchorView(view);
        }

        mVideoView = (VideoView) view.findViewById(R.id.hellomoon_video_view);

        // create a progress bar while the video file is loading
        mProgressDialog = new ProgressDialog(getActivity());
        // set a title for the progress bar
        mProgressDialog.setTitle(R.string.app_name);
        // set a message for the progress bar
        mProgressDialog.setMessage(getResources().getString(R.string.hellomoon_loading));
        // set the progress bar not cancelable on users' touch
        mProgressDialog.setCancelable(false);
        // show the progress bar
        mProgressDialog.show();

        try {
            // set the media controller in the VideoView
            mVideoView.setMediaController(mMediaControls);

            // set the uri of the video to be played
            mVideoView.setVideoURI(Uri.parse("android.resource://"
                    + getActivity().getPackageName()
                    + "/" + R.raw.apollo_17_stroll));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        mVideoView.requestFocus();
        // set an setOnPreparedListener in order to know when the video file is ready for playback
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                mProgressDialog.dismiss();
                // if we have a position on savedInstanceState, the video playback should start from here
                mVideoView.seekTo(position);
                if (position == 0) {
                    mVideoView.start();
                } else {
                    // if we come from a resumed activity, video playback will be paused
                    mVideoView.pause();
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // use onSaveInstanceState in order to store the video playback position for orientation change
        outState.putInt(KEY_POSITION, mVideoView.getCurrentPosition());
        mVideoView.pause();
    }
}
