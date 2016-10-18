package assingment1.bootcamp.com.flickster.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import assingment1.bootcamp.com.flickster.R;
import assingment1.bootcamp.com.flickster.activity.DetailsActivity;
import assingment1.bootcamp.com.flickster.activity.MovieActivity;
import assingment1.bootcamp.com.flickster.constants.Constants;
import assingment1.bootcamp.com.flickster.model.Movie;
import assingment1.bootcamp.com.flickster.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static assingment1.bootcamp.com.flickster.model.Movie.getMovieAtPosition;

/**
 * Created by baphna on 10/15/2016.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context mContext;


    public MovieAdapter(final Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType ==  Constants.POPULAR)
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_popular,  parent, false);
        else
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {

        if( getMovieAtPosition(position).getRating() > 2.5)
            return Constants.POPULAR;

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie movie = getMovieAtPosition(position);

        if(!(holder.getItemViewType() == Constants.POPULAR)){
            holder.textViewMovieName.setText(movie.getTitle());
            holder.textViewMovieSummary.setText(movie.getOverview());
        }

        holder.ratingBar.setRating((float) movie.getRating());

        int orientation = mContext.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT
                && holder.getItemViewType() != Constants.POPULAR) {

            Picasso.with(mContext).load(movie.getPosterPath())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.imageViewMovie);
        } else if( orientation == Configuration.ORIENTATION_LANDSCAPE
                || holder.getItemViewType() == Constants.POPULAR){

            Picasso.with(mContext).load(movie.getBackdropPath())
                    .transform(new RoundedCornersTransformation(10,10))
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.imageViewMovie);
        }
    }

    @Override
    public int getItemCount() {
        return Movie.getAllMovies().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.text_view_movie_name)
        TextView textViewMovieName;
        @BindView(R.id.image_view_movie)
        ImageView imageViewMovie;

        TextView textViewMovieSummary;
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            textViewMovieName = (TextView) itemView.findViewById(R.id.text_view_movie_name);
            imageViewMovie = (ImageView) itemView.findViewById(R.id.image_view_movie);
            textViewMovieSummary = (TextView) itemView.findViewById(R.id.text_view_summary);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position

            double postedRating = Movie.getMovieAtPosition(position).getRating();
            if(postedRating > 2.5){
                Utils utils = new Utils(mContext);
                utils.playTrailer(position);
            } else {

                if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it

                    Intent detailIntent = new Intent(mContext, DetailsActivity.class);
                    detailIntent.putExtra("Position", position);

                    Pair<View, String> imagePair = Pair.create((View) imageViewMovie, "tImage");
                    //Pair<View, String> titlePair = Pair.create((View) textViewMovieName, "tTitle");
                    //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((MovieActivity)mContext, imagePair, titlePair);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((MovieActivity) mContext, imagePair);
                    ActivityCompat.startActivity((MovieActivity) mContext, detailIntent, options.toBundle());
                }
            }
        }
    }


}
