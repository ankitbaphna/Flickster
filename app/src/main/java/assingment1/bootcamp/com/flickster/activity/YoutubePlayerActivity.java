package assingment1.bootcamp.com.flickster.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import assingment1.bootcamp.com.flickster.R;
import assingment1.bootcamp.com.flickster.constants.Constants;

public class YoutubePlayerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        final String key = getIntent().getStringExtra(Constants.VIDEO_TO_PLAY_KEY);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);
        Log.d(Constants.TAG, "youtube key is " + key);

        youTubePlayerView.initialize("AIzaSyB4sTK_1tns8zMWpOce6kk6W0DXO2kOcJ8",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        Log.d(Constants.TAG, "youtube loaded");
                        youTubePlayer.loadVideo(key);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
