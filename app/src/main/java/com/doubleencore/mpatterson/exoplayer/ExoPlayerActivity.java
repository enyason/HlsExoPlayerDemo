package com.doubleencore.mpatterson.exoplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.doubleencore.mpatterson.R;
import com.doubleencore.mpatterson.controls.ControlsContainer;
import com.doubleencore.mpatterson.controls.VideoControlsView;
import com.doubleencore.mpatterson.interfaces.IEndedListener;

/**
 * Created by michael on 9/2/15.
 */
public class ExoPlayerActivity extends Activity implements IEndedListener {
    private static final String TAG = ExoPlayerActivity.class.getSimpleName();

    public static final String EXTRA_URL = "extra_url";

    private VideoPlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        mPlayerView = (VideoPlayerView) findViewById(R.id.player_view);
        VideoControlsView controlsView = new VideoControlsView(this);
        controlsView.setControlsListener(mPlayerView);
        ControlsContainer controlsContainer = (ControlsContainer) findViewById(R.id.controls_container);
        controlsContainer.addView(controlsView);
        mPlayerView.setPlayerListener(controlsView);
        mPlayerView.setEndedListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_URL)) {
            String url = intent.getStringExtra(EXTRA_URL);
            mPlayerView.play(url);
        } else {
            showError();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.preparePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.destroyPlayer();
    }

    private void showError() {
        Toast.makeText(this, R.string.video_player_error, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onEnded() {
        finish();
    }
}
