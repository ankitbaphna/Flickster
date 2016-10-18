package assingment1.bootcamp.com.flickster.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import assingment1.bootcamp.com.flickster.R;
import assingment1.bootcamp.com.flickster.adapter.MovieAdapter;
import assingment1.bootcamp.com.flickster.constants.Constants;
import assingment1.bootcamp.com.flickster.model.Movie;
import assingment1.bootcamp.com.flickster.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_root)
    RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    Toolbar mToolbar;
    private SwipeRefreshLayout swipeContainer;

    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        refreshData();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_root);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        movieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(movieAdapter);
    }

    private void refreshData() {

        Utils utils = new Utils(getApplicationContext());

        if(utils.isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Constants.URL)
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
                            Movie.removeAllMovies();
                            String responseData = response.body().string();
                            JSONObject json = new JSONObject(responseData);

                            final String results = json.getString("results");
                            JSONArray jsonArray = new JSONArray(results);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                final String original_title = jsonArray.getJSONObject(i).getString("original_title");
                                final String overview = jsonArray.getJSONObject(i).getString("overview");
                                final String poster_path = jsonArray.getJSONObject(i).getString("poster_path");
                                //Divide by 2 for better UI representation
                                final double vote_average = jsonArray.getJSONObject(i).getDouble("vote_average") / 2;
                                final String release_date = jsonArray.getJSONObject(i).getString("release_date");
                                final String backdrop_path = jsonArray.getJSONObject(i).getString("backdrop_path");
                                final int vote_count = jsonArray.getJSONObject(i).getInt("vote_count");
                                final long id = jsonArray.getJSONObject(i).getLong("id");
                                Movie movie = new Movie(overview, vote_average, original_title, poster_path,
                                        release_date, vote_count, backdrop_path, id);
                                Movie.addToList(movie);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeContainer.setRefreshing(false);
                                        movieAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            Log.e(Constants.TAG, e.getMessage());
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please connect to internet first", Toast.LENGTH_LONG).show();
        }
    }
}
