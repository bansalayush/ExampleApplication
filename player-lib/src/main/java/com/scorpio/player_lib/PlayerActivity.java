package com.scorpio.player_lib;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by Ayush on 20/06/20.
 */
public class PlayerActivity extends AppCompatActivity {
    private static final String TAG = "PlayerActivity";

    private PlaybackStateListener playbackListener;
    private PlayerView playerView;
    private SimpleExoPlayer player;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private Bundle mArgs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mArgs = savedInstanceState;
        playerView = findViewById(R.id.video_view);
    }

    private void initializePlayer() {
        playbackListener = new PlaybackStateListener();
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector();
        defaultTrackSelector.setParameters(
                defaultTrackSelector.buildUponParameters().setMaxVideoSizeSd());

        player = ExoPlayerFactory.newSimpleInstance(this, defaultTrackSelector);
        player.addListener(playbackListener);
        playerView.setPlayer(player);

        Uri uri = Uri.parse(getString(R.string.media_url_dash));
        MediaSource mediaSource = buildDashMediaSource(uri);

        if (mArgs != null) {
            if (mArgs.containsKey("playWhenReady")) {
                playWhenReady = mArgs.getBoolean("playWhenReady");
            }

            if (mArgs.containsKey("currentWindow")) {
                currentWindow = mArgs.getInt("currentWindow");
            }

            if (mArgs.containsKey("playbackPosition")) {
                playbackPosition = mArgs.getLong("playbackPosition");
            }
        }

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        MediaSource source1 = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        Uri uri2 = Uri.parse(getString(R.string.media_url_mp3));

        MediaSource source2 = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri2);

        return new ConcatenatingMediaSource(source1, source2);
    }

    private MediaSource buildDashMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        DashMediaSource.Factory dashMediaSourceFactory = new DashMediaSource.Factory(dataSourceFactory);
        return dashMediaSourceFactory.createMediaSource(uri);
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        if (Util.SDK_INT > 23) {
//            initializePlayer();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        if (playerView != null)
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.removeListener(playbackListener);
            player.release();
            player = null;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("playWhenReady", playWhenReady);
        outState.putInt("currentWindow", currentWindow);
        outState.putLong("playbackPosition", playbackPosition);
        super.onSaveInstanceState(outState);
    }

    class PlaybackStateListener implements Player.EventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d(TAG, "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }
}
