package assingment1.bootcamp.com.flickster.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import assingment1.bootcamp.com.flickster.R;
import assingment1.bootcamp.com.flickster.model.Movie;
import assingment1.bootcamp.com.flickster.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.text_view_title)
    TextView textViewTitle;

    @BindView(R.id.text_view_summary)
    TextView textViewSummary;

    @BindView(R.id.rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.text_view_vote_count)
    TextView textViewVotes;

    @BindView(R.id.text_view_release_date)
    TextView textViewReleaseDate;

    @BindView(R.id.image_view_movie)
    ImageView imageViewMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        textViewTitle = (TextView) findViewById(R.id.text_view_title);
        textViewSummary = (TextView) findViewById(R.id.text_view_summary);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        textViewVotes = (TextView) findViewById(R.id.text_view_vote_count);
        textViewReleaseDate = (TextView) findViewById(R.id.text_view_release_date);
        imageViewMovie = (ImageView) findViewById(R.id.image_view_movie);

        final int position = getIntent().getIntExtra("Position", 0);

        imageViewMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils utils = new Utils(getApplicationContext());
                utils.playTrailer(position);
            }
        });

        Movie movie = Movie.getMovieAtPosition(position);

        textViewTitle.setText(movie.getTitle());
        textViewReleaseDate.setText("Released Date: " + movie.getReleaseDate());
        textViewSummary.setText(movie.getOverview());
        textViewVotes.setText("Votes: " + movie.getVoteCount());
        ratingBar.setRating((float) movie.getRating());

        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT
                && movie.getRating() < 2.5) {

            Picasso.with(getApplicationContext()).load(movie.getPosterPath())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(imageViewMovie);

        } else if( orientation == Configuration.ORIENTATION_LANDSCAPE
                || movie.getRating() > 2.5){

            Picasso.with(getApplicationContext()).load(movie.getBackdropPath())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(imageViewMovie);
        }


    }
}
