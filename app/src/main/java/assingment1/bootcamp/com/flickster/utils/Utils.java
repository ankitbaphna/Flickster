package assingment1.bootcamp.com.flickster.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import assingment1.bootcamp.com.flickster.activity.YoutubePlayerActivity;
import assingment1.bootcamp.com.flickster.constants.Constants;
import assingment1.bootcamp.com.flickster.model.Movie;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by baphna on 10/17/2016.
 */

public class Utils {

    private Context mContext;

    public Utils(Context mContext) {
        this.mContext = mContext;
    }

    public void playTrailer(final int position){
        if(isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            final Movie movie = Movie.getMovieAtPosition(position);
            String reqUrl = String.format(Constants.VIDEO_URL, movie.getId());

            Request request = new Request.Builder()
                    .url(reqUrl)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        try {
                            String responseData = response.body().string();
                            JSONObject json = new JSONObject(responseData);

                            final String results = json.getString("results");
                            JSONArray jsonArray = new JSONArray(results);

                            String yKey = jsonArray.getJSONObject(0).getString("key");
                            Log.d(Constants.TAG, "Filling key " + yKey + " for " + movie.getTitle()
                                    + " at position " + position);
                            Intent youtubeIntent = new Intent(mContext, YoutubePlayerActivity.class);
                            youtubeIntent.putExtra(Constants.VIDEO_TO_PLAY_KEY, yKey);
                            mContext.startActivity(youtubeIntent);

                        } catch (JSONException e) {
                            Log.e(Constants.TAG, "Onclick exception " + e.getMessage());
                            Toast.makeText(mContext, "No trailer available for "
                                    + movie.getTitle(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(mContext, "Please connect to internet first", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
