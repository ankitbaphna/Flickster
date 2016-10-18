package assingment1.bootcamp.com.flickster.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baphna on 10/15/2016.
 */

public class Movie {

    private static ArrayList<Movie> moviesList = new ArrayList<>();

    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private List<Integer> genreId;
    private long id;
    private String title;
    private String language;
    private float popularity;
    private int voteCount;
    private boolean video;
    private double rating;
    private String backdropPath;

    private String youTubeKey;

    /**
     *
     * @param overview
     * @param rating
     * @param title
     * @param posterPath
     * @param releaseDate
     * @param voteCount
     */
    public Movie(String overview, double rating, String title, String posterPath,
                 String releaseDate, int voteCount, String backdropPath, long id) {
        this.overview = overview;
        this.rating = rating;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
        this.id = id;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w1280/%s", backdropPath);
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Integer> getGenreId() {
        return genreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public long getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genreId=" + genreId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", rating=" + rating +
                ", backdropPath='" + backdropPath + '\'' +
                '}';
    }

    public String getYouTubeKey() {
        return youTubeKey;
    }

    public void setYouTubeKey(String youTubeKey) {
        this.youTubeKey = youTubeKey;
    }

    public static void addToList(final Movie movie){
        moviesList.add(movie);
    }

    public static Movie getMovieAtPosition(final int position){
        return moviesList.get(position);
    }

    public static ArrayList<Movie> getAllMovies(){
        return moviesList;
    }

    public static void removeAllMovies() {
        moviesList.clear();
    }
}
